package com.kupkik.ui.html.view;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;

import com.kupkik.messages.MessageCommon;
import com.kupkik.messages.MessageFooter;
import com.kupkik.messages.MessageHome;
import com.kupkik.model.DisplaySkillGraph;
import com.kupkik.model.MatchDay;
import com.kupkik.model.User;
import com.kupkik.model.UserWithPassword;
import com.kupkik.model.game.Game;
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

	public String createCheckBox(String name, String value, String valueToShow, boolean checked){
		StringBuilder checkBox = new StringBuilder();
		
		checkBox.append("<div class=\"checkbox\">");
		checkBox.append("<label> <input type=\"checkbox\" name=\""+name+"\" ");
		if(checked){
			checkBox.append("	value=\""+ value + "\" checked>"+ valueToShow+"");
		}else{
			checkBox.append("	value=\""+ value + "\">"+ valueToShow+"");
		}
		checkBox.append("</label>");
		checkBox.append("</div>");
		return  checkBox.toString();
	}
	
	public  boolean checkUserEquality(List<User> editUsers, User user){
		for (User editUser : editUsers){
			if(editUser.getKey().equals(user.getKey())){
				return true;
			}
		}
		return false;
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

		content.append("<div class=\"navbar custom_navbar\">");
	
		content.append("		<div class=\"container\">");
		content.append(" 			<button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\".navbar-responsive-collapse\">");
		content.append(" 				<span class=\"icon-bar\"></span>");
		content.append(" 				<span class=\"icon-bar\"></span>");
		content.append("  				<span class=\"icon-bar\"></span>");
		content.append("  			</button>");

		if( !currentUser.getPasswordMd5().equals(HtmlRequestProcessor.GUEST_USER.getPasswordMd5()) ){
			content.append("		<a class=\"navbar-brand\" href=\"#\"><img src=\"/res/images/logo_icon.png\" alt=\"logo\" />");
			content.append(" " + currentUser.getFirstname());
		}else{
			content.append("    	<a class=\"navbar-brand\" href=\"#\"><img src=\"/res/images/logo_icon.png\" alt=\"logo\" />");
		}
		content.append(" 			</a>");
		content.append("		<div class=\"nav-collapse navbar-responsive-collapse collapse\" style=\"height: 0px;\">");
		content.append(" 			<ul class=\"nav navbar-nav\">");
		content.append(" 	      		<li class=\"active\"><a href=\"/\">HOME</a></li>");
		if( currentUser.getPasswordMd5().equals(HtmlRequestProcessor.GUEST_USER.getPasswordMd5()) ){
			content.append("			<li><a href=\"/?showView=RegisterView\">REGISTER HERE!</a></li>");
			content.append(" 		</ul>");
			content.append("		<form class=\"form-inline pull-right paddingFormIntro\" action=\"/\" method=\"post\" >");
			content.append("			<input type=\"hidden\" name=\"action\" value=\"Login\">");
			content.append("			<input class=\"form-control\" style=\"width:200px\" placeholder=\"Your E-Mail\" name=\"user_name\" type=\"email\" size=\"50\" maxlength=\"50\">");
			content.append("			<input class=\"form-control\" style=\"width:200px\" placeholder=\"Password\" name=\"password\" type=\"password\" size=\"12\" maxlength=\"12\">");
			content.append("			<input class=\"btn btn-default\" type=\"submit\" value=\"Login\">");
			content.append("		</form>");
		}else{
			content.append("			<li class=\"dropdown\">");
			content.append("				<a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\">"+ MessageCommon.PROJECT_NAME +"<b class=\"caret\"></b></a>");
			content.append("				<ul class=\"dropdown-menu\">");
			content.append(" 					<li><a href=\"/?showView=NewSeasonView\">Create Season</a> </li>");
			content.append("					<li><a href=\"/?showView=NewBadmintonSingleGameView\">Badminton Single</a> </li>");
			content.append("					<li><a href=\"/?showView=NewBadmintonDoubleGameView\">Badminton Double</a> </li>");
			content.append("					<li><a href=\"/?showView=NewKickerGameView\">Kicker</a> </li>");
			content.append("				</ul>");
			content.append("			</li>");
			content.append("			<li class=\"dropdown\">");
			content.append("				<a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\">STATISTICS<b class=\"caret\"></b></a>");
			content.append("				<ul class=\"dropdown-menu\">");
			content.append(" 					<li><a href=\"/?showView=StatisticsView\">Statistics</a> </li>");
			content.append(" 					<li><a href=\"/?showView=RankingView\">Ranking</a> </li>");
			content.append("				</ul>");
			content.append("			</li>");
			
			content.append("			<li class=\"dropdown\">");
			content.append("				<a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\">EDIT<b class=\"caret\"></b></a>");
			content.append("				<ul class=\"dropdown-menu\">");
			content.append(" 					<li><a href=\"/?showView=EditSeasonView\">Edit My Seasons</a> </li>");
			content.append(" 					<li><a href=\"/?showView=EditUserView\">Reset Password</a> </li>");
			content.append("				</ul>");
			content.append("			</li>");
			
			content.append(" 			<li><a href=\"/?action=Logoff\">LOGOUT</a> </li>");
			content.append(" 		</ul>");
		}
		content.append(" 		</div>");
		content.append(" 		</div>");
	
		content.append("</div>");
		pHtmlContent.append(createLiveGridSpan(12, content.toString()));
		//Live Grid End
	
	
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

		htmlBegin.append("<div class=\"col-lg-12\">");
		htmlBegin.append("<div class=\"col-lg-12\">");
		return htmlBegin.toString();
	}

	/** Creates a live Grid
	 * 
	 */
	private String createLiveGridSpan(int columns, String content){
		final StringBuilder liveGridSpan = new StringBuilder();
		liveGridSpan.append("<div class=\"col-lg-"+columns+"\">");
		liveGridSpan.append(content);
		liveGridSpan.append("</div>");
		return liveGridSpan.toString();
	}


	public String createFormLayout(String content, String description){
		StringBuilder result = new StringBuilder();
		result.append("<div class=\"row\">");
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
		mainSiteIntroArea.append("<div class=\"row\">");
		mainSiteIntroArea.append("<div class=\"col-lg-12\">");
		mainSiteIntroArea.append("<div class=\"row\">");
		mainSiteIntroArea.append("<div class=\"col-lg-4\">");
		mainSiteIntroArea.append("<img src=\"/res/images/logo.png\" style=\"width:100%\"/>");
		mainSiteIntroArea.append("</div>");
		mainSiteIntroArea.append("<div class=\"col-lg-8\">");
		mainSiteIntroArea.append("<div class=\"thumbnail\">");
		mainSiteIntroArea.append("<div class=\"caption\">");
		//mainSiteIntroArea.append("<h3>"+ MessageCommon.PROJECT_NAME + "</h3>");

		if( currentUser.getPasswordMd5().equals(HtmlRequestProcessor.GUEST_USER.getPasswordMd5()) ){
			mainSiteIntroArea.append(MessageHome.WELCOME_GUEST_USER);
			mainSiteIntroArea.append("<div class=\"row\">");
			mainSiteIntroArea.append("<div class=\"col-lg-12\">");
			mainSiteIntroArea.append("<a href=\"?showView=RegisterView\"><button class=\"btn btn-large btn-primary\" type=\"button\">Register</button></a>");
			mainSiteIntroArea.append("</div>");
		}else{
			mainSiteIntroArea.append("<h3>Hi ");
			mainSiteIntroArea.append(currentUser.getFirstname());
			mainSiteIntroArea.append(",</h3> <br />");
			mainSiteIntroArea.append(MessageHome.WELCOME_REGISTERED_USER);
			mainSiteIntroArea.append("<div class=\"row\">");
			mainSiteIntroArea.append("<div class=\"col-lg-12\">");
			mainSiteIntroArea.append("<a href=\"?showView=NewSeasonView\"><button class=\"btn btn-info form-control\" type=\"button\">Create a Season</button></a>");
			mainSiteIntroArea.append("</div>");

		}
		mainSiteIntroArea.append("</div>");
		mainSiteIntroArea.append("</div>");
		//Spacer Block
		
		mainSiteIntroArea.append("</div>");
		mainSiteIntroArea.append("</div>");

		
		mainSiteIntroArea.append("</div>");
		mainSiteIntroArea.append("</div>");
		mainSiteIntroArea.append("</div>");
		
		mainSiteIntroArea.append("<div class=\"row\"><div class=\"col-lg-12\">&nbsp;</div></div>");
		return mainSiteIntroArea.toString();
	}

	/**Creates the three thumbnails at the MainPage	
	 * @param matchDays The List of matchDays to be displayed
	 */
	public String newsThumbnails(List<MatchDay> matchDays, List<Game> badmintonSingle){
		StringBuilder newsThumbnails = new StringBuilder();

		//Start the LiveGridRow
		newsThumbnails.append("<div class=\"row\">");
			newsThumbnails.append("<div class=\"col-lg-4\">");
				newsThumbnails.append("<div class=\"thumbnail\">");
						newsThumbnails.append("<div class=\"caption\">");
							newsThumbnails.append("<h3>"+ MessageHome.NEWS_BOXES_1 +"</h3>");
							newsThumbnails.append("<table class=\"table table-striped\">");
							for (MatchDay item : matchDays){
							newsThumbnails.append("<tr> <td>");
							newsThumbnails.append("<img src=\""+item.getPictogramPath()+"\" />");
							newsThumbnails.append("</td> <td>");
							newsThumbnails.append(item.getName());
							newsThumbnails.append("</td> </tr>");							
							}
							newsThumbnails.append("</table>");
						newsThumbnails.append("</div>");
				newsThumbnails.append("</div>");
			newsThumbnails.append("</div>");

			newsThumbnails.append("<div class=\"col-lg-8\">");
				newsThumbnails.append("<div class=\"thumbnail\">");
					newsThumbnails.append("<div class=\"caption\">");
						newsThumbnails.append("<h3>" + MessageHome.NEWS_BOXES_2 +"</h3>");
						newsThumbnails.append("<table class=\"table table-striped\">");
						newsThumbnails.append("<thead> <th> &nbsp; </th> <th> TEAM 1</th> <th> TEAM 2</th>  <th> RESULT</th> <th> RESULT</th>  </thead>");
			for (Game item : badmintonSingle){
	
						newsThumbnails.append("<tr> <td>");
						newsThumbnails.append("<img src=\""+item.getPictogramPath()+"\" />");
						
						newsThumbnails.append("</td> <td>");
						
						newsThumbnails.append(teamToString(item.getTeamOne()));
			
						newsThumbnails.append("</td> <td>");
						newsThumbnails.append(teamToString(item.getTeamTwo()));
			
						newsThumbnails.append("</td> <td>");
						newsThumbnails.append(item.getResultOne());
			
						newsThumbnails.append("</td> <td>");
						newsThumbnails.append(item.getResultTwo());
			
						newsThumbnails.append("</td> </tr>");							
			}
	
						newsThumbnails.append("</table>");
					newsThumbnails.append("</div>");
				newsThumbnails.append("</div>");
			newsThumbnails.append("</div>");
		newsThumbnails.append("</div>");
		
		
		return newsThumbnails.toString();
	}


	/** Creates the Footer Information for the Website
	 * 
	 */

	public String createFooter(){
		StringBuilder footer = new StringBuilder();
		footer.append("<div class=\"col-lg-12\">");
		footer.append("<div class=\"row footer custom_footer\">");
		footer.append("<div style=\"vertical-align: middle; text-align:center;\">");
		footer.append( MessageFooter.FOOTER_SIGNITURE);
		footer.append("</div>");	
		footer.append("</div>");
	
		footer.append("</div>");
		footer.append("</div>");
		footer.append("</div>");
		
		footer.append(" </div>");

		return footer.toString();
	}


	/**
	 * Creates the end of a HTML-site.
	 * 
	 * @return The content of a HTML-site which contains the end of this site.
	 */
	public String createHtmlEnd(){

		final StringBuilder htmlEnd = new StringBuilder();
		htmlEnd.append("  </div>\n");
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
		if(gamesPlayed > 0){
		double percentageWon = (100.00 / gamesPlayed) *  gamesWon;
		double percentageLost = 100.00 - percentageWon;
		DecimalFormat decimalFormat = new DecimalFormat("##.##");
		htmlSuccessBar.append("<div class=\"progress\">");
		htmlSuccessBar.append("		<div class=\"progress-bar progress-bar-success\" style=\"width:"+ decimalFormat.format(percentageWon) +"%\">"+ decimalFormat.format(percentageWon) +"%</div>");
		htmlSuccessBar.append("		<div class=\"progress-bar progress-bar-danger\" style=\"width:"+ decimalFormat.format(percentageLost) +"%\">"+ decimalFormat.format(percentageLost) +"%</div>");
		htmlSuccessBar.append("</div>");
		}else{
			htmlSuccessBar.append("No Games Played");
		}

		return htmlSuccessBar.toString();
	}

	
	/**
	 * Creates a SuccessBar showing the percentage a user won in green, and the percentage lost in red.
	 * Percentage lost is calculated.
	 */
	public String createParticipationBar(int gamesParticipated, int totalGames){
		
		final StringBuilder htmlSuccessBar = new StringBuilder();
		if(totalGames > 0){
		double percentageWon = (100.00 / totalGames) *  gamesParticipated;
		double percentageLost = 100.00 - percentageWon;
		DecimalFormat decimalFormat = new DecimalFormat("##.##");
		htmlSuccessBar.append("<div class=\"progress\">");
		htmlSuccessBar.append("		<div class=\"progress-bar progress-bar-info\" style=\"width:"+ decimalFormat.format(percentageWon) +"%\">"+ decimalFormat.format(percentageWon) +"%</div>");
		htmlSuccessBar.append("		<div class=\"progress-bar \" style=\"width:"+ decimalFormat.format(percentageLost) +"%\">"+ decimalFormat.format(percentageLost) +"%</div>");
		htmlSuccessBar.append("</div>");
		}else{
			htmlSuccessBar.append("No Games Played");
		}

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

	public  String printAllGamesForMatchDays(List<MatchDay> matchDays, UserWithPassword selectedUser){
		StringBuilder result = new StringBuilder();
		
		result.append("<div class=\"row\">");
		result.append("<div class=\"col-lg-12\">");
		result.append("<h3>Games Played Grouped by MatchDays</h3>");
		result.append("</div>");
		result.append("</div>");
	
		
		
		
		
		
		for (MatchDay  matchDay: matchDays){
			result.append("<div class=\"row\">");
			result.append("<div class=\"col-lg-12\">");
		
			result.append("<table class=\"table table-striped\">");
			result.append("<thead> <th colspan=\"2\" style=\"width:50%\"><div>" + matchDay.getName() + " | Games Played: " +  matchDay.getGamesPlayed()  + " Games Won: "+ matchDay.getGamesWon() + "</div></th> <th colspan=\"2\" style=\"width:50%\"> "+ this.createSuccessBar(matchDay.getGamesWon(), matchDay.getGamesPlayed()) + " </th>  </thead>");
			
			

			List<Game> iGames = matchDay.getGames();
			if (iGames.size() == 0){
				result.append("<tr> <td colspan=\"4\">No Games Played</td></tr>");
			
			}else{
				for( Game iGame : iGames){
					
					
					if(iGame.getResultOne() > iGame.getResultTwo() ){
						result.append("<tr>");
						result.append("<td><div class=\"gamewon\">"+ teamToString(iGame.getTeamOne(), selectedUser)+"</div></td>");
						result.append("<td>"+ iGame.getResultOne()+"</td>");
						result.append("<td>"+ iGame.getResultTwo() +"</td>");
						result.append("<td>"+ teamToString(iGame.getTeamTwo(), selectedUser) +"</td>");
						result.append("</tr>");
					}else{
						result.append("<tr>");
						result.append("<td>"+ teamToString(iGame.getTeamOne(), selectedUser)+"</td>");
						result.append("<td>"+ iGame.getResultOne()+"</td>");
						result.append("<td>"+ iGame.getResultTwo() +"</td>");
						result.append("<td><div class=\"gamewon\">"+ teamToString(iGame.getTeamTwo(), selectedUser) +"</div></td>");
						result.append("</tr>");
					}
				}
			
				
			}
			
			result.append("</table>");
			result.append("</div>");
			
		
			
			result.append("<div class=\"row\">");
			result.append("<div class=\"col-lg-12\">&nbsp;</div>");
			result.append("</div>");
		}
		
		return result.toString();
	}

	private String teamToString(List<User> team, UserWithPassword selectedUser) {
		StringBuilder teamString = new StringBuilder();
		
		boolean currentUserContained = false;
		Iterator<User> teamOneIterator = team.iterator();
		
		while(teamOneIterator.hasNext()){
			
			User next = teamOneIterator.next();
			if (next.getKey().equals(selectedUser.getKey())){
				currentUserContained= true;
			}
			teamString.append(next.getSurname());
			teamString.append(", ");
			teamString.append(next.getFirstname());
			
			if (teamOneIterator.hasNext()){
				teamString.append(" | ");
			}
			
		}
		if(currentUserContained){
			return "<strong>"+teamString.toString()+"</strong>";
		}
		return teamString.toString();
	}
	
	private String teamToString(List<User> team) {
		StringBuilder teamString = new StringBuilder();
		
		Iterator<User> teamOneIterator = team.iterator();
		
		while(teamOneIterator.hasNext()){
			
			User next = teamOneIterator.next();
		
			teamString.append(next.getSurname());
			teamString.append(", ");
			teamString.append(next.getFirstname());
			
			if (teamOneIterator.hasNext()){
				teamString.append(" | ");
			}
			
		}
		
		return teamString.toString();
	}
	
	public String createStatistics(	DisplaySkillGraph dataSkillGraph, String canvasId, String canvasName, UserWithPassword selectedUser){
		StringBuilder skillTable = new StringBuilder();
		skillTable.append("<table class=\"table table-striped\">");
	skillTable
			.append("<thead> <th style=\"width:30%\"> Description</th> <th style=\"width:70%\"> Value </th>  </thead>");
	skillTable.append("<tr> <td>WL-RATE (WIN-LOOSE)</td><td>");
			skillTable.append(createSuccessBar(
					dataSkillGraph.getTotalNumberWon(),
					dataSkillGraph.getTotalNumberPlayed()));
			skillTable.append("</td> </tr>");

			skillTable.append("<tr> <td>Participation Rate</td><td>");
			skillTable.append(createSuccessBar(
					dataSkillGraph.getTotalNumberPlayed(),
					dataSkillGraph.getTotalNumberGames()));
			skillTable.append("</td> </tr>");

			skillTable.append("<tr> <td>Games Won</td><td>");
			skillTable.append(dataSkillGraph.getTotalNumberWon());
			skillTable.append("</td> </tr>");

			skillTable.append("<tr> <td>Games Played</td><td>");
			skillTable.append(dataSkillGraph
					.getTotalNumberPlayed());
			skillTable.append("</td> </tr>");

			skillTable.append("<tr> <td>Games Lost</td><td>");
			skillTable.append(dataSkillGraph.getTotalNumberLost());
			skillTable.append("</td> </tr>");

			skillTable.append("<tr> <td>Games Total</td><td>");
			skillTable
					.append(dataSkillGraph.getTotalNumberGames());
			skillTable.append("</td> </tr>");

			skillTable.append("</table>");

			
			StringBuilder skillCanvas = new StringBuilder();
			skillCanvas.append("<div class=\"row\">");
			skillCanvas.append("<div class=\"col-lg-12\">");
			skillCanvas.append(skillTable.toString());
			skillCanvas.append("</div>");

			skillCanvas.append("</div>");

			skillCanvas.append("<div class=\"row\">");
			skillCanvas.append("<div  class=\"col-lg-12\">");
			skillCanvas.append("<div id=\""+canvasId+"\" style=\"width:100%\">");

			skillCanvas.append(createLineChart(dataSkillGraph
					.getMatchDayNames(), dataSkillGraph
					.getWinRateInPercentForEachMatchDay(),
					dataSkillGraph
							.getLooseRateInPercentForEachMatchDay(),
							canvasName, canvasId));
			skillCanvas.append("</div>");
			skillCanvas.append("</div>");

			skillCanvas.append("</div>");
			
			
			
			List<MatchDay> matchDays = dataSkillGraph.getMatchDays();
			skillCanvas.append(printAllGamesForMatchDays(matchDays, selectedUser));
	
			return skillCanvas.toString();
	}

}
