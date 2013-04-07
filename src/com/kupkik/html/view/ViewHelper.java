package com.kupkik.html.view;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;

import com.kupkik.model.UserWithPassword;

/**
 * Offers helper methods for all views.
 */
public class ViewHelper
{
    private HttpSession mSession;

    public ViewHelper(final HttpSession pSession)
    {
        mSession = pSession;
    }

    /**
     * Converts text, so that it can be displayd in a html-site.
     * 
     * @param pText
     *            the text which should be shown inside a html-site
     * @return a text converted for HTML
     */
    public String convertTextForHtml( final String pText )
    {
        return StringEscapeUtils.escapeHtml(pText);
    }

    /**
     * Creates the menu-bar on top of the view.
     * 
     * @param pHtmlContent
     *            The content of the HTML-site, where the menu-bar should be
     *            added to.
     */
    private void createMenuBar( final StringBuilder pHtmlContent )
    {
        pHtmlContent.append("   <table border=\"0\" width=\"100%\">\n");
        pHtmlContent.append("       <colgroup>\n");
        pHtmlContent.append("           <col width=\"1*\">\n");
        pHtmlContent.append("           <col width=\"1*\">\n");
        pHtmlContent.append("           <col width=\"1*\">\n");
        pHtmlContent.append("       </colgroup>\n");
        pHtmlContent.append("       <tr>\n");
        pHtmlContent.append("           <td>\n");

        UserWithPassword currentUser = (UserWithPassword) mSession.getAttribute("currentUser");
        if( currentUser.getPasswordMd5().equals("none") )
        {
            pHtmlContent.append("<i>");
        }
        pHtmlContent.append(currentUser.getName());
        if( currentUser.getPasswordMd5().equals("none") )
        {
            pHtmlContent.append("</i>");
        }

        pHtmlContent.append("           </td>\n");
        pHtmlContent.append("           <td>\n");

        if( currentUser.getPasswordMd5().equals("none") )
        {
            pHtmlContent.append("<a href=\"/?showView=LoginView\">Login</a>");
        }
        else
        {
            pHtmlContent.append("<a href=\"/?showView=LogoffView\">Logoffn</a>");
        }

        pHtmlContent.append("           </td>\n");
        pHtmlContent.append("           <td>\n");
        pHtmlContent.append("           </td>\n");
        pHtmlContent.append("       </tr>\n");
        pHtmlContent.append("   </table>\n");
    }

    /**
     * @see #createHtmlBegin(boolean)
     */
    public String createHtmlBegin()
    {
        return createHtmlBegin("");
    }

    /**
     * @see #createHtmlBegin(String, boolean)
     */
    public String createHtmlBegin( final String pTitle )
    {
        return createHtmlBegin(pTitle, true);
    }

    /**
     * Creates the beginning of a HTML-site.
     * 
     * @param pTitle
     *            the title of the page
     * @param pDoShowMenubar
     *            Should the menu-bar be shown?
     * @return The content of a HTML-site which contains the beginning of this
     *         site.
     */
    public String createHtmlBegin( final String pTitle, final boolean pDoShowMenubar )
    {
        final StringBuilder htmlBegin = new StringBuilder();
        htmlBegin.append("<html>\n");
        htmlBegin.append("   <head>\n");

        if( pTitle.equals("") )
        {
            htmlBegin.append("      <title>kupkik</title>\n");
        }
        else
        {
            htmlBegin.append("      <title>kupkik | " + pTitle + "</title>\n");
        }

        htmlBegin.append("      <meta name=\"robots\" content=\"noindex\">\n");
        htmlBegin.append("   </head>\n");
        htmlBegin.append("  <body>\n");

        if( pDoShowMenubar )
        {
            createMenuBar(htmlBegin);
        }

        return htmlBegin.toString();
    }

    /**
     * Creates the end of a HTML-site.
     * 
     * @return The content of a HTML-site which contains the end of this site.
     */
    public String createHtmlEnd()
    {
        final StringBuilder htmlEnd = new StringBuilder();

        htmlEnd.append("  </body>\n");
        htmlEnd.append("</html>\n");

        return htmlEnd.toString();
    }
}
