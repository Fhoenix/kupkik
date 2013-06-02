package com.kupkik.ui.html.view;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;

import com.kupkik.model.UserWithPassword;
import com.kupkik.ui.html.HtmlRequestProcessor;

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

		pHtmlContent.append(" <div class=\"navbar\">");
		pHtmlContent.append("  <div class=\"navbar-inner\">");
		pHtmlContent.append("    <a class=\"brand\" href=\"#\">KUPKIK - ");

		UserWithPassword currentUser = (UserWithPassword) mSession.getAttribute("currentUser");
		pHtmlContent.append(currentUser.getName());

		pHtmlContent.append(" </a>");
		pHtmlContent.append(" 		<ul class=\"nav\">");
		pHtmlContent.append(" 	      <li class=\"active\"><a href=\"#\">Home</a></li>");


		if( currentUser.getPasswordMd5().equals(HtmlRequestProcessor.GUEST_USER.getPasswordMd5()) )
		{
			pHtmlContent.append(" <li><a href=\"/?showView=LoginView\">Login</a> </li>");
		}
		else
		{
			pHtmlContent.append(" <li><a href=\"/?action=Logoff\">Logoff</a> </li>");
		}


		pHtmlContent.append(" </ul>");
		pHtmlContent.append(" </div>");
		pHtmlContent.append(" </div>");

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
		//BootStrap Include
		htmlBegin.append("		<link href=\"/res/bootstrap/css/bootstrap.min.css\" rel=\"stylesheet\" media=\"screen\">");
		htmlBegin.append("   </head>\n");
		htmlBegin.append("  <body>\n");
		htmlBegin.append("		<script src=\"http://code.jquery.com/jquery.js\"></script>");
		htmlBegin.append("		<script src=\"js/bootstrap.min.js\"></script>");
		//Open the Bootstrap Container
		htmlBegin.append("		<div class=\"container\">");

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
		//Bootstrap Container Closed
		htmlEnd.append("  </div>\n");
		htmlEnd.append("  </body>\n");
		htmlEnd.append("</html>\n");

		return htmlEnd.toString();
	}

	/**
	 * Creates a SuccessBar showing the percentage a user won in green, and the percentage lost in red.
	 * Percentage lost is calculated.
	 * 
	 * @param percentageWon Hand in the percentage a user won (e.g. 54.34).
	 * @return Returns the entire bar with bootstrap classes.
	 */
	public String createSuccessBar(double percentageWon){
		final StringBuilder htmlSuccessBar = new StringBuilder();
		double percentageLost = 100.00 - percentageWon;

		htmlSuccessBar.append("<div class=\"progress\">");
		htmlSuccessBar.append("		<div class=\"bar bar-success\" style=\"width:"+ percentageWon +"%\">"+ percentageWon +"</div>");
		htmlSuccessBar.append("		<div class=\"bar bar-danger\" style=\"width:"+ percentageLost +"%\">"+ percentageLost +"</div>");
		htmlSuccessBar.append("</div>");

		return htmlSuccessBar.toString();
	}
}
