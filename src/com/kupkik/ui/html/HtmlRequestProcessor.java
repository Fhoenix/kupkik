package com.kupkik.ui.html;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kupkik.applicationcore.ApplicationCoreFacade;
import com.kupkik.model.UserWithPassword;
import com.kupkik.ui.html.view.ViewHelper;

/**
 * This class handles the application-logic for processing a HTML-request. This
 * logic is independent from the logic which handles a specific request (this is
 * done by a HtmlRequestHandler). As a consequence it could be used for all
 * kinds of HTML-applications. So only the flow of data through the application
 * is handled here, not how the data is used.
 */
public class HtmlRequestProcessor
{
    private ApplicationCoreFacade        mApplicationCoreFacade;
    private HttpServletRequest           mRequest;
    private HttpServletResponse          mResponse;
    private ServletContext               mServletContext;
    public final static UserWithPassword GUEST_USER = new UserWithPassword("guest", "none");

    private static final Logger          sLogger    = Logger.getLogger(HtmlStarterServlet.class.getName());

    public HtmlRequestProcessor(final HttpServletRequest pRequest, final HttpServletResponse pResponse,
            final ServletContext pServletContext, final ApplicationCoreFacade pApplicationCoreFacade)
    {
        mRequest = pRequest;
        mResponse = pResponse;
        mServletContext = pServletContext;
        mApplicationCoreFacade = pApplicationCoreFacade;
    }

    /**
     * This methods handles every client request. It does the exception-handling
     * and leaves the actual application-logic to another method.
     */
    public void handleClientRequest() throws IOException
    {
        try
        {
            checkPreconditions();
            doHandleClientRequest();
        }
        catch( Exception e )
        {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String stacktrace = sw.toString();

            final String errorMsg = "An error occured: \n" + e.getMessage() + "\n\nStacktrace: \n" + stacktrace;
            sLogger.severe(errorMsg);
            mResponse.getWriter().println(errorMsg.replace("\n", "<br>"));
        }
    }

    /**
     * Checks the preconditions.
     */
    private void checkPreconditions() throws Exception
    {
        // this is the action which is to be performed
        String action = mRequest.getParameter("action");
        sLogger.info("action: " + action);

        // this is the view which is to be shown
        String nextView = mRequest.getParameter("showView");
        sLogger.info("showView: " + nextView);

        // there can not be an action and a view, because if there is an action,
        // the appropriate business-controller should determine the next view
        if( (action != null) && (nextView != null) )
        {
            throw new RuntimeException("Its not allowed to have 'action' and 'showView' parameters. action: " + action + ", showView: "
                    + nextView);
        }
    }

    /**
     * Here the application-logic is performed for handling a request from the
     * client.
     */
    private void doHandleClientRequest() throws Exception
    {
        // lets see who is logged in
        checkUserLogin();

        makeViewHelperClassAvailableToViews();

        // this is the action which is to be performed
        String action = mRequest.getParameter("action");

        // first, load the request-handler which handles the action
        IHtmlRequestHandler requestHandler = loadRequestHandlerForAction(action);

        // the view, which is to be shown
        String nextViewName = mRequest.getParameter("showView");

        // if there is neither action nor showView, then show the main view
        if( (action == null) && (nextViewName == null) )
        {
            nextViewName = "MainView";
        }

        // now let the request-handler handle the action and determine which
        // view to show next
        if( requestHandler != null )
        {
            nextViewName = requestHandler.performActionAndGetNextView(mRequest, mRequest.getSession(), mApplicationCoreFacade);
        }

        // if there is a handler for preparing the view, load it and let
        // prepare the data
        IHtmlRequestHandler viewPreparationHandler = loadHandlerForPreparingView(nextViewName);
        if( viewPreparationHandler != null )
        {
            viewPreparationHandler.performActionAndGetNextView(mRequest, mRequest.getSession(), mApplicationCoreFacade);
        }

        // show the view
        showView(nextViewName);
    }

    private void makeViewHelperClassAvailableToViews()
    {
        ViewHelper viewHelper = new ViewHelper(mRequest.getSession());

        mRequest.setAttribute("viewHelper", viewHelper);
    }

    /**
     * Check if the user is logged in. If the user is not logged in, then he
     * will be set to the guest-user.
     */
    private void checkUserLogin()
    {
        HttpSession session = mRequest.getSession();
        UserWithPassword currentUser = (UserWithPassword) session.getAttribute("currentUser");

        // user is not logged in => set him to guest
        if( currentUser == null )
        {
            currentUser = GUEST_USER;
            session.setAttribute("currentUser", currentUser);
        }

        // make user known to view
        mRequest.setAttribute("currentUser", currentUser);
    }

    /**
     * Looks for a handler which prepares the data for a view. Such handlers are
     * optional. They follow this naming convention: name = "Show" + name of
     * view.
     * 
     * @param pNextViewName
     *            the name of the view, for which a handler is to be found
     * @return the handler, which prepares data for a view or 'null', if no such
     *         handler exists
     */
    private IHtmlRequestHandler loadHandlerForPreparingView( final String pNextViewName ) throws Exception
    {
        final String nameForHandlerWhichPreparesView = "Show" + pNextViewName;

        IHtmlRequestHandler requestHandler = loadRequestHandlerByName(nameForHandlerWhichPreparesView);

        return requestHandler;
    }

    /**
     * loads the request-handler which handles the action
     * 
     * @param pAction
     *            the action for which the request-handler should be loaded
     * @return the request-handler which handles the action or null, if no
     *         request-handler is needed (because only a view should be
     *         displayed)
     */
    private IHtmlRequestHandler loadRequestHandlerForAction( final String pAction ) throws Exception
    {
        // no action has been given
        if( pAction == null )
        {
            return null;
        }

        IHtmlRequestHandler requestHandler = loadRequestHandlerByName(pAction);

        if( requestHandler != null )
        {
            return requestHandler;
        }

        // an action was specified, but no request-handler has been found which
        // handles this action => show error
        throw new RuntimeException("No request-handler found for action: " + pAction);
    }

    /**
     * loads a request-handler by its name
     * 
     * @param pNameOfRequestHandler
     *            the name of the request-handler, which is to be loaded
     * @return the request-handler or 'null', if no request-handler with that
     *         name exists
     */
    private IHtmlRequestHandler loadRequestHandlerByName( final String pNameOfRequestHandler ) throws Exception
    {
        final String pathToCodeBase = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        final File requestHandlersDirectory = new File(pathToCodeBase + "com/kupkik/ui/html/requesthandlers");

        final ClassLoader classLoader = HtmlStarterServlet.class.getClassLoader();

        for( File currentFile : requestHandlersDirectory.listFiles() )
        {
            String className = currentFile.getName().substring(0, currentFile.getName().length() - 6);
            String fullClassName = "com.kupkik.ui.html.requesthandlers." + className;
            String nameOfRequestHandler = className.replaceAll("Handler$", "");

            if( nameOfRequestHandler.equals(pNameOfRequestHandler) )
            {

                final Class htmlRequestHandlerClass = classLoader.loadClass(fullClassName);
                final IHtmlRequestHandler htmlRequestHandler = (IHtmlRequestHandler) htmlRequestHandlerClass.newInstance();
                sLogger.info("Found request-handler with name: " + pNameOfRequestHandler);
                return htmlRequestHandler;
            }
        }

        sLogger.info("Found no request-handler with name: " + pNameOfRequestHandler);
        return null;
    }

    /**
     * Shows a view.
     * 
     * @param pViewName
     *            The name of the view, which is to be shown.
     */
    private void showView( final String pViewName ) throws Exception
    {
        sLogger.info("Showing view: " + pViewName);

        // view does not exist => show error
        if( !doesViewExist(pViewName) )
        {
            throw new RuntimeException("View does not exist: " + pViewName);
        }

        RequestDispatcher rd = mServletContext.getRequestDispatcher("/views/" + pViewName + ".jsp");
        rd.forward(mRequest, mResponse);
    }

    /**
     * Does a view exist?
     * 
     * @param pViewName
     *            The name of the view in question.
     * @return 'true' if the view does exist, 'false' otherwise
     */
    private boolean doesViewExist( final String pViewName ) throws Exception
    {
        final String pathToCodeBase = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        final File classesDirectory = new File(pathToCodeBase);
        File viewsDirectory = classesDirectory.getParentFile().getParentFile();
        viewsDirectory = new File(viewsDirectory.getAbsolutePath() + "/views/");
        for( File currentFile : viewsDirectory.listFiles() )
        {
            if( currentFile.isDirectory() )
            {
                continue;
            }
            if( currentFile.getName().equals(pViewName + ".jsp") )
            {
                return true;
            }
        }

        return false;
    }
}
