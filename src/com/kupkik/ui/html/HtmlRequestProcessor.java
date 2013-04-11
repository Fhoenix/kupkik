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
        sLogger.info("showView: " + action);

        // there can not be an action and a view, because if there is an action,
        // the appropriate business-controller should determine the next view
        if( (action != null) && (nextView != null) )
        {
            showErrorView("Its not allowed to have 'action' and 'showView' parameters. action: " + action + ", showView: " + nextView);
        }
    }

    /**
     * Shows the error view.
     * 
     * @param pErrorMessage
     *            The error-message to be displayed.
     */
    private void showErrorView( final String pErrorMessage ) throws Exception
    {
        sLogger.warning("Error view shown, because of error: " + pErrorMessage);
        mRequest.setAttribute("errorMsg", pErrorMessage);

        RequestDispatcher rd = mServletContext.getRequestDispatcher("/views/ErrorView.jsp");
        rd.forward(mRequest, mResponse);

        // after showing the error, stop
        System.exit(0);
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

        // first, load the business-controllers which handles the action
        IHtmlRequestHandler requestHandler = loadRequestHandlerForAction(action);

        // the view, which is to be shown
        String nextViewName = mRequest.getParameter("showView");

        // now let the request-handler handle the action and determine which
        // view to show next
        if( requestHandler != null )
        {
            nextViewName = requestHandler.performActionAndGetNextView(mRequest, mRequest.getSession(), mApplicationCoreFacade);
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
     * Loads the request-handler which handles the action
     * 
     * @param pAction
     *            the action for which the request-handler should be loaded
     * @return the request-handler which handles the action or null, if no
     *         request-handler is needed (because only a view should be
     *         displayed)
     */
    private IHtmlRequestHandler loadRequestHandlerForAction( final String pAction ) throws Exception
    {
        // the action to look for
        String action = pAction;

        // no action has been given
        if( action == null )
        {
            // this is the view which is to be shown
            String nextView = mRequest.getParameter("showView");

            // no action or view has been given, show the main-view (by loading
            // the request-handler which prepares the data for the main-view)
            if( nextView == null )
            {
                action = "ShowMainView";
            }
            // no action has been given, but there is a view to be shown => try
            // to load a request-handler, which prepares data for this view
            // (conventions: the names of these request-handlers are "Show" +
            // name of
            // view)
            else
            {
                action = "Show" + action;
            }
        }

        final String pathToCodeBase = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        final File requestHandlersDirectory = new File(pathToCodeBase + "com/kupkik/ui/html/requesthandlers");

        final ClassLoader classLoader = HtmlStarterServlet.class.getClassLoader();

        for( File currentFile : requestHandlersDirectory.listFiles() )
        {
            String className = currentFile.getName().substring(0, currentFile.getName().length() - 6);
            String fullClassName = "com.kupkik.ui.html.requesthandlers." + className;
            String handledActionByThisController = className.replaceAll("Controller$", "");

            if( handledActionByThisController.equals(action) )
            {

                final Class htmlRequestHandlerClass = classLoader.loadClass(fullClassName);
                final IHtmlRequestHandler htmlRequestHandler = (IHtmlRequestHandler) htmlRequestHandlerClass.newInstance();
                sLogger.info("Found controller for action: " + action);
                return htmlRequestHandler;
            }
        }

        sLogger.info("No controller found for action: " + action);

        // an action was specified, but no controller has been found which
        // handles this action => show error
        if( pAction != null )
        {
            showErrorView("No controller found for action: " + pAction);
        }
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
            showErrorView("View does not exist: " + pViewName);
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
