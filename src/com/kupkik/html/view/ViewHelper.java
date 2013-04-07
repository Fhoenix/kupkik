package com.kupkik.html.view;

import javax.servlet.http.HttpSession;

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
     * Creates the beginning of a HTML-site.
     * 
     * @return The content of a HTML-site which contains the beginning of this
     *         site.
     */
    public String createHtmlBegin()
    {
        final StringBuilder htmlBegin = new StringBuilder();
        htmlBegin.append("<html>\n");
        htmlBegin.append("   <head>\n");
        htmlBegin.append("      <title>kupkik</title>\n");
        htmlBegin.append("      <meta name=\"robots\" content=\"noindex\">\n");
        htmlBegin.append("   </head>\n");
        htmlBegin.append("<body>\n");

        createMenuBar(htmlBegin);

        return htmlBegin.toString();
    }
}
