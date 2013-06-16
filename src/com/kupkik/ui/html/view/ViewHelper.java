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
	
	/**Text shown at the main Page */
	private final String tournamentText ="<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus molestie ante id velit fermentum, id accumsan.</p>";

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
		
		//Start the LiveGridRow
		pHtmlContent.append(" <div class=\"row\">");
		
		StringBuilder content = new StringBuilder();
		
		content.append(" <div class=\"navbar\">");
		content.append("  <div class=\"navbar-inner\">");
		content.append("    <a class=\"brand\" href=\"#\">KUPKIK - ");

		UserWithPassword currentUser = (UserWithPassword) mSession.getAttribute("currentUser");
		content.append(currentUser.getName());

		content.append(" </a>");
		content.append(" 		<ul class=\"nav\">");
		content.append(" 	      <li class=\"active\"><a href=\"#\">Home</a></li>");


		if( currentUser.getPasswordMd5().equals(HtmlRequestProcessor.GUEST_USER.getPasswordMd5()) ){
			content.append(" <li><a href=\"/?showView=LoginView\">Login</a> </li>");
		}
		else{
			content.append(" <li><a href=\"/?action=Logoff\">Logoff</a> </li>");
		}
		content.append(" </ul>");
		content.append(" </div>");
		content.append(" </div>");
		
		pHtmlContent.append(createLiveGridSpan(12, content.toString()));
		
		//Live Grid End
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
	 * Creates the beginning of a HTML-site. It includes the Navigation Bar.
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
		
		//Responsive Design
		htmlBegin.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
		htmlBegin.append("<link href=\"/res/bootstrap/css/bootstrap-responsive.css\" rel=\"stylesheet\">");
		
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
	
	/** Creates a live Grid
	 * 
	 */
	private String createLiveGridSpan(int columns, String content){
		final StringBuilder liveGridSpan = new StringBuilder();
		liveGridSpan.append("<div class=\"span"+columns+"\">");
		liveGridSpan.append(content);
		liveGridSpan.append("</div>");
		return liveGridSpan.toString();
	}
	
	
	/** Creates the "Create you Tournament" Section for the Main Website
	 * 
	 */
	public String createMainSiteIntroArea(){
		
		
		StringBuilder mainSiteIntroArea = new StringBuilder();
		//Start the LiveGridRow
		mainSiteIntroArea.append("<div class=\"row-fluid\">");
			mainSiteIntroArea.append("<div class=\"span12\">");
			
				mainSiteIntroArea.append("<div class=\"span6\">");
					mainSiteIntroArea.append("<img src=\"/res/images/logo.png\" />");
				mainSiteIntroArea.append("</div>");
			
				mainSiteIntroArea.append("<div class=\"span6\">");
					mainSiteIntroArea.append("<h2>Score-IT</h2>");
					
					mainSiteIntroArea.append(tournamentText);
					mainSiteIntroArea.append("<div class=\"row-fluid\">");
						mainSiteIntroArea.append("<div class=\"span6\">");
							mainSiteIntroArea.append("<button class=\"btn btn-large btn-primary\" type=\"button\">Register</button>");
						mainSiteIntroArea.append("</div>");
						mainSiteIntroArea.append("<div class=\"span6\">");
							mainSiteIntroArea.append("<a href=\"/?showView=NewTournamentView\"><button class=\"btn btn-large\" type=\"button\">Create Tournament</button></a>");
						mainSiteIntroArea.append("</div>");
					
					mainSiteIntroArea.append("</div>");
				mainSiteIntroArea.append("</div>");
			mainSiteIntroArea.append("</div>");
		mainSiteIntroArea.append("</div>");	
	return mainSiteIntroArea.toString();
	}
	
	/**Creates the three thumbnails at the MainPage	 */
public String newsThumbnails(){
		
		
		StringBuilder newsThumbnails = new StringBuilder();
		//Start the LiveGridRow
		
		
		newsThumbnails.append("<div class=\"row-fluid\">");
			
		
		
		
			newsThumbnails.append("<ul class=\"thumbnails\">");
				newsThumbnails.append("<li class=\"span4\">");
					newsThumbnails.append("<div class=\"thumbnail\">");
						newsThumbnails.append("<img src=\"/res/images/logo.png\" style=\"width: 300px; height: 200px;\" />");
						newsThumbnails.append("<div class=\"caption\">");
						newsThumbnails.append("<h3>Latest Tournaments</h3>");
						newsThumbnails.append("<table class=\"table table-striped\">");
						newsThumbnails.append("<tr> <td> Tournament 1</td> </tr>");
						newsThumbnails.append("<tr> <td> Tournament 2</td> </tr>");
						newsThumbnails.append("<tr> <td> Tournament 3</td> </tr>");
						newsThumbnails.append("</table>");
					newsThumbnails.append("</div>");
				newsThumbnails.append("</div>");
			newsThumbnails.append("</li>");
			
			
			
			
			newsThumbnails.append("<li class=\"span4\">");
			newsThumbnails.append("<div class=\"thumbnail\">");
				newsThumbnails.append("<img src=\"/res/images/logo.png\" style=\"width: 300px; height: 200px;\" />");
				newsThumbnails.append("<div class=\"caption\">");
				newsThumbnails.append("<h3>Latest Games</h3>");
				newsThumbnails.append("<table class=\"table table-striped\">");
				newsThumbnails.append("<thead> <th> TEAM 1</th> <th> TEAM 2</th>  <th> RESULT</th> <th> RESULT</th>  </thead>");
				newsThumbnails.append("<tr> <td> TEAM 1</td> <td> TEAM 2</td>  <td> RESULT</td> <td> RESULT</td>  </tr>");
				newsThumbnails.append("<tr> <td> TEAM 1</td> <td> TEAM 2</td>  <td> RESULT</td> <td> RESULT</td>  </tr>");
				newsThumbnails.append("<tr> <td> TEAM 1</td> <td> TEAM 2</td>  <td> RESULT</td> <td> RESULT</td>  </tr>");
				newsThumbnails.append("</table>");
			newsThumbnails.append("</div>");
		newsThumbnails.append("</div>");
	newsThumbnails.append("</li>");
	
	newsThumbnails.append("<li class=\"span4\">");
	newsThumbnails.append("<div class=\"thumbnail\">");
		newsThumbnails.append("<img src=\"/res/images/logo.png\" style=\"width: 300px; height: 200px;\" />");
		newsThumbnails.append("<div class=\"caption\">");
		newsThumbnails.append("<h3>Whats Up Next</h3>");
		newsThumbnails.append("<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus molest </p>");
	newsThumbnails.append("</div>");
newsThumbnails.append("</div>");
newsThumbnails.append("</li>");
			
		
			newsThumbnails.append("</ul>");
		newsThumbnails.append("</div>");	
	return newsThumbnails.toString();
	}
	
	
	/** Creates the Footer Information for the Website
	 * 
	 */
	
	public String createFooter(){
		StringBuilder footer = new StringBuilder();
		footer.append("<div class=\"row-fluid footer\">");
		footer.append("<div class=\"span12\">");
		footer.append("SCORE IT - CREATE PLAY SHARE");
		footer.append("</div>");
		footer.append("</div>");
		return footer.toString();
	}
	
	
	/**
	 * Creates the end of a HTML-site.
	 * 
	 * @return The content of a HTML-site which contains the end of this site.
	 */
	public String createHtmlEnd()
	{
		final StringBuilder htmlEnd = new StringBuilder();
		htmlEnd.append(createFooter());
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
