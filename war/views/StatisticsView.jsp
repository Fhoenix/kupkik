<%@page import="com.kupkik.model.game.IGame"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="com.sun.org.apache.xerces.internal.impl.xpath.regex.Match"%>
<%@ page import="com.kupkik.model.*"%>
<%@ page import="com.kupkik.ui.html.view.*"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Arrays"%>
<%@ page import="com.google.appengine.api.datastore.KeyFactory"%>

<%
	ViewHelper viewHelper = (ViewHelper) request
			.getAttribute("viewHelper");
	List<Season> seasons = (List<Season>) request
			.getAttribute("seasons");

	List<User> users = (List<User>) request.getAttribute("users");

	DisplaySkillGraph displaySkillGraph = (DisplaySkillGraph) request
			.getAttribute("displaySkillGraph");

	DisplaySkillGraph displaySkillGraphDouble = (DisplaySkillGraph) request
			.getAttribute("displaySkillGraphDouble");
%>

<%=viewHelper.createHtmlBegin("My Profile")%>
<div class="row-fluid">
	<div class="span12">
		<h1>Profile Crawler</h1>
		<form action="/" method="post">
			<input class="fillLayout" type="hidden" name="action"
				value="CreateStatistics">

			<div class="row-fluid">
				<div class="span2">Choose a Season</div>
				<div class="span3">
					<select name="seasonKey">
						<%
							for (Season item : seasons) {

								out.println("	 <option value=\""
										+ KeyFactory.keyToString(item.getKey()) + "\">"
										+ item.getName() + "</option>");

							}
						%>
					</select>

				</div>
				<div class="span2">Choose a Player</div>
				<div class="span3">
					<select id="userprofile" name="userprofile">

						<%
							for (User item : users) {
								out.println("	 <option value=\"" +KeyFactory.keyToString(item.getKey())+ "\">"
										+ item.getSurname() + ", " + item.getFirstname()
										+ "</option>");
							}
						%>
					</select>
				</div>
				<div class="span2">
					<input type="submit" value="Crawl Profile">
				</div>
			</div>



		</form>



		<%
				UserWithPassword selectedUser =	(UserWithPassword) request.getAttribute("selectedUser");
			if (displaySkillGraph != null) {
		%>
		<div class="row-fluid">
			<div class="span12"><h2>Profile <%= selectedUser.getSurname() +", "+selectedUser.getFirstname() %></h2></div>
		</div>
		<div class="row-fluid">
			<div class="span12"><h3>Badminton Single Games</h3></div>
		</div>
		<%
			StringBuilder skillTable = new StringBuilder();
				skillTable.append("<table class=\"table table-striped\">");
				skillTable
						.append("<thead> <th style=\"width:30%\"> Description</th> <th style=\"width:70%\"> Value </th>  </thead>");
				skillTable.append("<tr> <td>WL-RATE (WIN-LOOSE)</td><td>");
				skillTable.append(viewHelper.createSuccessBar(
						displaySkillGraph.getTotalNumberWon(),
						displaySkillGraph.getTotalNumberPlayed()));
				skillTable.append("</td> </tr>");

				skillTable.append("<tr> <td>Participation Rate</td><td>");
				skillTable.append(viewHelper.createSuccessBar(
						displaySkillGraph.getTotalNumberPlayed(),
						displaySkillGraph.getTotalNumberGames()));
				skillTable.append("</td> </tr>");

				skillTable.append("<tr> <td>Games Won</td><td>");
				skillTable.append(displaySkillGraph.getTotalNumberWon());
				skillTable.append("</td> </tr>");

				skillTable.append("<tr> <td>Games Played</td><td>");
				skillTable.append(displaySkillGraph.getTotalNumberPlayed());
				skillTable.append("</td> </tr>");

				skillTable.append("<tr> <td>Games Lost</td><td>");
				skillTable.append(displaySkillGraph.getTotalNumberLost());
				skillTable.append("</td> </tr>");

				skillTable.append("<tr> <td>Games Total</td><td>");
				skillTable.append(displaySkillGraph.getTotalNumberGames());
				skillTable.append("</td> </tr>");

				skillTable.append("</table>");

				out.println("<div class=\"row-fluid\">");
				out.println("<div class=\"span12\">");
				out.println(skillTable.toString());
				out.println("</div>");

				out.println("</div>");

				out.println("<div class=\"row-fluid\">");
				out.println("<div  class=\"span12\">");
				out.println("<div id=\"SkillGraphCanvasSingle\" style=\"width:100%\">");

				out.println(viewHelper.createLineChart(displaySkillGraph
						.getMatchDayNames(), displaySkillGraph
						.getWinRateInPercentForEachMatchDay(),
						displaySkillGraph
								.getLooseRateInPercentForEachMatchDay(),
						"BadmintonsingleCanvas", "SkillGraphCanvasSingle"));
				out.println("</div>");
				out.println("</div>");

				out.println("</div>");
				
				
				
				List<MatchDay> matchDays = displaySkillGraph.getMatchDays();
				out.println(viewHelper.printAllGamesForMatchDays(matchDays,selectedUser) );
				
			}
		
		
		%>


		<%
			if (displaySkillGraphDouble != null) {
		%>
		<div class="row-fluid">
			<div class="span12"><h3>Badminton Double Games</h3></div>
		</div>
		<%
			StringBuilder skillTable = new StringBuilder();
			skillTable.append("<table class=\"table table-striped\">");
		skillTable
				.append("<thead> <th style=\"width:30%\"> Description</th> <th style=\"width:70%\"> Value </th>  </thead>");
		skillTable.append("<tr> <td>WL-RATE (WIN-LOOSE)</td><td>");
				skillTable.append(viewHelper.createSuccessBar(
						displaySkillGraphDouble.getTotalNumberWon(),
						displaySkillGraphDouble.getTotalNumberPlayed()));
				skillTable.append("</td> </tr>");

				skillTable.append("<tr> <td>Participation Rate</td><td>");
				skillTable.append(viewHelper.createSuccessBar(
						displaySkillGraphDouble.getTotalNumberPlayed(),
						displaySkillGraphDouble.getTotalNumberGames()));
				skillTable.append("</td> </tr>");

				skillTable.append("<tr> <td>Games Won</td><td>");
				skillTable.append(displaySkillGraphDouble.getTotalNumberWon());
				skillTable.append("</td> </tr>");

				skillTable.append("<tr> <td>Games Played</td><td>");
				skillTable.append(displaySkillGraphDouble
						.getTotalNumberPlayed());
				skillTable.append("</td> </tr>");

				skillTable.append("<tr> <td>Games Lost</td><td>");
				skillTable.append(displaySkillGraphDouble.getTotalNumberLost());
				skillTable.append("</td> </tr>");

				skillTable.append("<tr> <td>Games Total</td><td>");
				skillTable
						.append(displaySkillGraphDouble.getTotalNumberGames());
				skillTable.append("</td> </tr>");

				skillTable.append("</table>");

				out.println("<div class=\"row-fluid\">");
				out.println("<div class=\"span12\">");
				out.println(skillTable.toString());
				out.println("</div>");

				out.println("</div>");

				out.println("<div class=\"row-fluid\">");
				out.println("<div  class=\"span12\">");
				out.println("<div id=\"SkillGraphCanvasDouble\" style=\"width:100%\">");

				out.println(viewHelper.createLineChart(displaySkillGraphDouble
						.getMatchDayNames(), displaySkillGraphDouble
						.getWinRateInPercentForEachMatchDay(),
						displaySkillGraphDouble
								.getLooseRateInPercentForEachMatchDay(),
						"BadmintondoubleCanvas", "SkillGraphCanvasDouble"));
				out.println("</div>");
				out.println("</div>");

				out.println("</div>");
				
				
				
				List<MatchDay> matchDays = displaySkillGraphDouble.getMatchDays();
				out.println(viewHelper.printAllGamesForMatchDays(matchDays, selectedUser));
			}
		%>

	</div>
</div>
<%
	if (request.getAttribute("errorMessage") != null) {
%>
<p class="text-error">
	Fehler:
	<%=viewHelper.convertTextForHtml((String) request
						.getAttribute("errorMessage"))%></p>
<%
	}
%>

<%=viewHelper.createHtmlEnd()%>
