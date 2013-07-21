package com.kupkik.ui.html.view;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;

import com.kupkik.messages.MessageCommon;
import com.kupkik.messages.MessagesFooter;
import com.kupkik.messages.MessagesHome;
import com.kupkik.model.MatchDay;
import com.kupkik.model.UserWithPassword;
import com.kupkik.model.game.BadmintonSingle;
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
	private void createMenuBar( final StringBuilder pHtmlContent)
	{
		UserWithPassword currentUser = (UserWithPassword) mSession.getAttribute("currentUser");
		StringBuilder content = new StringBuilder();
	

		//Start the LiveGridRow
		pHtmlContent.append("<div class=\"row\">");

		content.append("<div class=\"navbar\">");
		content.append("	<div class=\"navbar-inner\">");
		content.append("		<div class=\"container\">");
		content.append(" 			<a class=\"btn btn-navbar\" data-toggle=\"collapse\" data-target=\".navbar-responsive-collapse\">");
		content.append(" 				<span class=\"icon-bar\"></span>");
		content.append(" 				<span class=\"icon-bar\"></span>");
		content.append("  				<span class=\"icon-bar\"></span>");
		content.append("  			</a>");
		
		if( !currentUser.getPasswordMd5().equals(HtmlRequestProcessor.GUEST_USER.getPasswordMd5()) ){
			content.append("		<a class=\"brand\" href=\"#\"><img src=\"/res/images/logo_icon.png\" alt=\"logo\" />");
			content.append(currentUser.getFirstname());
		}else{
			content.append("    	<a class=\"brand\" href=\"#\"><img src=\"/res/images/logo_icon.png\" alt=\"logo\" />");
		}
		content.append(" 			</a>");
		content.append("		<div class=\"nav-collapse navbar-responsive-collapse collapse\" style=\"height: 0px;\">");
		content.append(" 			<ul class=\"nav\">");
		content.append(" 	      		<li class=\"active\"><a href=\"/\">Home</a></li>");
		if( currentUser.getPasswordMd5().equals(HtmlRequestProcessor.GUEST_USER.getPasswordMd5()) ){
			content.append("			<li><a href=\"/?showView=RegisterView\">Register Here!</a></li>");
			content.append(" 		</ul>");
			content.append("		<form class=\"navbar-form pull-right\" action=\"/\" method=\"post\">");
			content.append("			<input type=\"hidden\" name=\"action\" value=\"Login\">");
			content.append("			<input class=\"span2\" placeholder=\"Your E-Mail\" name=\"user_name\" type=\"email\" size=\"50\" maxlength=\"50\">");
			content.append("			<input class=\"span2\" placeholder=\"Password\" name=\"password\" type=\"password\" size=\"12\" maxlength=\"12\">");
			content.append("			<input class=\"btn\" type=\"submit\" value=\"Login\">");
			content.append("		</form>");
		}else{
			content.append("			<li class=\"dropdown\">");
			content.append("				<a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\">"+ MessageCommon.PROJECT_NAME +"<b class=\"caret\"></b></a>");
			content.append("				<ul class=\"dropdown-menu\">");
		
			
			content.append(" 					<li><a href=\"/?showView=NewSeasonView\">Create Season</a> </li>");
			content.append(" 					<li><a href=\"/?showView=NewMatchDayView\">Create MatchDay</a> </li>");
			content.append("					<li class=\"dropdown-submenu\">");
			content.append("   					<a tabindex=\"-1\" href=\"#\">Score Game</a>");
			content.append("						<ul class=\"dropdown-menu\">");
			content.append("							<li><a href=\"/?showView=NewBadmintonSingleGameView\">Badminton Single</a> </li>");
			content.append("							<li><a href=\"/?showView=NewBadmintonDoubleGameView\">Badminton Double</a> </li>");
			content.append("						</ul>");
			content.append("					</li>");
			
			content.append("				</ul>");
			content.append("			</li>");
			content.append("			<li class=\"dropdown\">");
			content.append("				<a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\">My Profile<b class=\"caret\"></b></a>");
			content.append("				<ul class=\"dropdown-menu\">");
			content.append(" 					<li><a href=\"/?showView=MyProfileView\">MyProfile</a> </li>");
			content.append("				</ul>");
			content.append("			</li>");
			content.append(" 			<li><a href=\"/?action=Logoff\">Logout</a> </li>");
			content.append(" 		</ul>");
		}
		content.append(" 		</div>");
		content.append(" 		</div>");
		content.append(" 	</div>");
		content.append("</div>");
		pHtmlContent.append(createLiveGridSpan(12, content.toString()));
		//Live Grid End
		pHtmlContent.append(" </div>");
		pHtmlContent.append("	<div class=\"container-fluid\">");
	}

	/**
	 * @see #createHtmlBegin(boolean)
	 */
	public String createHtmlBegin()
	{
		return createHtmlBegin("");
	}

	public String createLabel(String labelText, String labelClass, String labelFor){
		StringBuilder label = new StringBuilder();
		label.append("<label class=");
		label.append("\"");
		label.append(labelClass);
		label.append("\" ");
		label.append("for=\"");
		label.append(labelFor);
		label.append("\">");
		label.append(labelText);
		label.append("</label>");
		return label.toString();
	}
	/**
	 * @see #createHtmlBegin(String, boolean)
	 */
	public String createHtmlBegin( final String pTitle)
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
	public String createHtmlBegin( final String pTitle, final boolean pDoShowMenubar)
	{
		final StringBuilder htmlBegin = new StringBuilder();
		htmlBegin.append("<!DOCTYPE HTML><html>\n");
		htmlBegin.append("   <head>\n");
		if( pTitle.equals("") ){
			htmlBegin.append("      <title>"+ MessageCommon.PROJECT_NAME + "</title>\n");
		}else{
			htmlBegin.append("      <title>"+ MessageCommon.PROJECT_NAME + " " + pTitle + "</title>\n");
		}

		htmlBegin.append("      <meta name=\"robots\" content=\"noindex\">\n");
		//BootStrap Include
		htmlBegin.append("		<link href=\"/res/bootstrap/css/bootstrap.min.css\" rel=\"stylesheet\" media=\"screen\">");


		//Responsive Design
		htmlBegin.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
		htmlBegin.append("<link href=\"/res/bootstrap/css/bootstrap-responsive.css\" rel=\"stylesheet\">");
		htmlBegin.append("<link href=\"/res/css/custom.css\" rel=\"stylesheet\">");
		htmlBegin.append("   </head>\n");
		htmlBegin.append("  <body>\n");
		htmlBegin.append("		<script src=\"http://code.jquery.com/jquery.js\"></script>");
		htmlBegin.append("		<script src=\"/res/bootstrap/js/bootstrap.min.js\"></script>");
		htmlBegin.append("		<script src=\"/res/chartjs/Chart.js\"></script>");
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


	public String createFormLayout(String content, String description){
		StringBuilder result = new StringBuilder();
		result.append("<div class=\"row-fluid\">");
		result.append(createLiveGridSpan(9, content));
		result.append(createLiveGridSpan(3, description));
		result.append("</div>");

		return result.toString();
	}

	/** Creates the "Create you MatchDay" Section for the Main Website
	 * 
	 */
	public String createMainSiteIntroArea(){
		UserWithPassword currentUser = (UserWithPassword) mSession.getAttribute("currentUser");
		StringBuilder mainSiteIntroArea = new StringBuilder();
		//Start the LiveGridRow
		mainSiteIntroArea.append("<div class=\"row-fluid\">");
		mainSiteIntroArea.append("<div class=\"span12\">");
		mainSiteIntroArea.append("<div class=\"span6\">");
		mainSiteIntroArea.append("<img src=\"/res/images/logo.png\" />");
		mainSiteIntroArea.append("</div>");
		mainSiteIntroArea.append("<div class=\"span6\">");
		mainSiteIntroArea.append("<h2>"+ MessageCommon.PROJECT_NAME + "</h2>");
		
		if( currentUser.getPasswordMd5().equals(HtmlRequestProcessor.GUEST_USER.getPasswordMd5()) ){
			mainSiteIntroArea.append(MessagesHome.WELCOME_GUEST_USER);
			mainSiteIntroArea.append("<div class=\"row-fluid\">");
			mainSiteIntroArea.append("<div class=\"span12\">");
			mainSiteIntroArea.append("<a href=\"?showView=RegisterView\"><button class=\"btn btn-large btn-primary\" type=\"button\">Register</button></a>");
			mainSiteIntroArea.append("</div>");
		}else{
			mainSiteIntroArea.append("Hi ");
			mainSiteIntroArea.append(currentUser.getFirstname());
			mainSiteIntroArea.append(", <br />");
			mainSiteIntroArea.append(MessagesHome.WELCOME_REGISTERED_USER);
			mainSiteIntroArea.append("<div class=\"row-fluid\">");
			mainSiteIntroArea.append("<div class=\"span12\">");
			mainSiteIntroArea.append("<a href=\"?showView=NewSeasonView\"><button class=\"btn btn-large btn-primary\" type=\"button\">Create Your First Season</button></a>");
			mainSiteIntroArea.append("</div>");
			
		}
		mainSiteIntroArea.append("</div>");
		mainSiteIntroArea.append("</div>");
		mainSiteIntroArea.append("</div>");
		mainSiteIntroArea.append("</div>");	
		return mainSiteIntroArea.toString();
	}

	/**Creates the three thumbnails at the MainPage	
	 * @param matchDays The List of matchDays to be displayed
	 */
	public String newsThumbnails(List<MatchDay> matchDays, List<BadmintonSingle> badmintonSingle){
		StringBuilder newsThumbnails = new StringBuilder();

		//Start the LiveGridRow
		newsThumbnails.append("<div class=\"row-fluid\">");
		newsThumbnails.append("<ul class=\"thumbnails\">");
		newsThumbnails.append("<li class=\"span4\">");
		newsThumbnails.append("<div class=\"thumbnail\">");
		newsThumbnails.append("<img src=\"/res/images/logo.png\" style=\"width: 300px; height: 200px;\" />");
		newsThumbnails.append("<div class=\"caption\">");
		newsThumbnails.append("<h3>"+ MessagesHome.NEWS_BOXES_1 +"</h3>");
		newsThumbnails.append("<table class=\"table table-striped\">");
		for (MatchDay item : matchDays){
			newsThumbnails.append("<tr> <td>");
			newsThumbnails.append(item.getName());
			newsThumbnails.append("</td> </tr>");							
		}
		newsThumbnails.append("</table>");
		newsThumbnails.append("</div>");
		newsThumbnails.append("</div>");
		newsThumbnails.append("</li>");

		newsThumbnails.append("<li class=\"span8\">");
		newsThumbnails.append("<div class=\"thumbnail\">");
		newsThumbnails.append("<img src=\"/res/images/logo.png\" style=\"width: 300px; height: 200px;\" />");
		newsThumbnails.append("<div class=\"caption\">");
		newsThumbnails.append("<h3>" + MessagesHome.NEWS_BOXES_2 +"</h3>");
		newsThumbnails.append("<table class=\"table table-striped\">");
		newsThumbnails.append("<thead> <th> TEAM 1</th> <th> TEAM 2</th>  <th> RESULT</th> <th> RESULT</th>  </thead>");
		for (BadmintonSingle item : badmintonSingle){

			newsThumbnails.append("<tr> <td>");
			newsThumbnails.append(item.getPlayerOne().getSurname()+", "+ item.getPlayerOne().getFirstname());

			newsThumbnails.append("</td> <td>");
			newsThumbnails.append(item.getPlayerTwo().getSurname()+", "+ item.getPlayerTwo().getFirstname());

			newsThumbnails.append("</td> <td>");
			newsThumbnails.append(item.getResultOne());

			newsThumbnails.append("</td> <td>");
			newsThumbnails.append(item.getResultTwo());

			newsThumbnails.append("</td> </tr>");							
		}

		newsThumbnails.append("</table>");
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
		footer.append("<div class=\"row-fluid footer custom_footer\">");
		footer.append("<div class=\"span12\">");
		footer.append("<div class=\"container-fluid\">");
			footer.append("<div class=\"row-fluid\">");
			
				footer.append("<div class=\"span1\">");
				footer.append("		<img src=\"/res/images/logo_icon.png\" alt=\"logo\" />");
				footer.append("</div>");	
				
				footer.append("<div style=\"vertical-align: middle;\"class=\"span11\">");
				footer.append( MessagesFooter.FOOTER_SIGNITURE);
				footer.append("</div>");	
			footer.append("</div>");
		footer.append("</div>");
		footer.append("</div>");
		footer.append("</div>");

		return footer.toString();
	}


	/**
	 * Creates the end of a HTML-site.
	 * 
	 * @return The content of a HTML-site which contains the end of this site.
	 */
	public String createHtmlEnd(){
		
		final StringBuilder htmlEnd = new StringBuilder();
		htmlEnd.append("</div>");
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
*/
	public String createSuccessBar(int gamesWon, int gamesPlayed){
		final StringBuilder htmlSuccessBar = new StringBuilder();
		
		double percentageWon = (100.00 / gamesPlayed) *  gamesWon;
		double percentageLost = 100.00 - percentageWon;
		DecimalFormat decimalFormat = new DecimalFormat("##.##");
		htmlSuccessBar.append("<div class=\"progress\">");
		htmlSuccessBar.append("		<div class=\"bar bar-success\" style=\"width:"+ decimalFormat.format(percentageWon) +"%\">"+ decimalFormat.format(percentageWon) +"%</div>");
		htmlSuccessBar.append("		<div class=\"bar bar-danger\" style=\"width:"+ decimalFormat.format(percentageLost) +"%\">"+ decimalFormat.format(percentageLost) +"%</div>");
		htmlSuccessBar.append("</div>");

		return htmlSuccessBar.toString();
	}
	
	public String createLineChart(String[] games, String[] winRateInPercent, String[] looseRateInPercent, String canvasId, String divId ){
		StringBuilder lineChart = new StringBuilder();
	
		lineChart.append("<canvas id=\""+ canvasId +"\" ></canvas>");
		lineChart.append("<script>");
		
		lineChart.append("(function() {");
		lineChart.append("    var canvas = document.getElementById('"+canvasId +"'),");
		lineChart.append("            context = canvas.getContext('2d');");
		    // resize the canvas to fill browser window dynamically
		lineChart.append(" window.addEventListener('resize', resizeCanvas, false);");
		lineChart.append("   function resizeCanvas() {");
		lineChart.append("  var pixels = $('#"+divId+"').width(); ");
		lineChart.append("           canvas.width = pixels;");
		lineChart.append("canvas.height = 400;");
		lineChart.append("drawStuff(); ");
		lineChart.append("    }");
		lineChart.append("   resizeCanvas();");
		lineChart.append("    function drawStuff() {");
		lineChart.append("var ctx = document.getElementById(\""+canvasId+"\").getContext(\"2d\");");
		lineChart.append("var ctx = $(\"#"+ canvasId +"\").get(0).getContext(\"2d\");");
		lineChart.append("var data = {");
		lineChart.append("		labels :"+Arrays.asList(games)+" ,");

		lineChart.append("			datasets : [");
		
		
		lineChart.append("				{");
		lineChart.append("					fillColor : \"rgba(220,80,75,0.8)\",");
		lineChart.append("					strokeColor : \"rgba(220,220,220,1)\",");
		lineChart.append("					pointColor : \"rgba(220,80,75,1)\",");
		lineChart.append("				pointStrokeColor : \"#fff\",");
		lineChart.append("				data : "+ Arrays.asList(looseRateInPercent) +"" );
		lineChart.append("			},");
		
		lineChart.append("				{");
		lineChart.append("					fillColor : \"rgba(87,169,87,0.8)\",");
		lineChart.append("					strokeColor : \"rgba(220,220,220,1)\",");
		lineChart.append("					pointColor : \"rgba(153,204,0,1)\",");
		lineChart.append("				pointStrokeColor : \"#fff\",");
		lineChart.append("				data : "+ Arrays.asList(winRateInPercent) +"" );
		lineChart.append("			}");
		
		lineChart.append("		]");
		lineChart.append("	};");
				
		lineChart.append("var options = {scaleOverlay : true, scaleOverride : true, scaleSteps : 10, scaleStepWidth : 10 ,scaleStartValue : 0, scaleShowLabels: true, scaleFontSize: 14, bezierCurve: false} ;");
				
		lineChart.append("var myNewChart = new Chart(ctx).Bar(data, options);");
		lineChart.append("     }");  
		lineChart.append(" 	})();");
		lineChart.append("</script>");
		
		
		return lineChart.toString();
	}
}
