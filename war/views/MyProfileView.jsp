<%@ page import="com.kupkik.model.*"%>
<%@ page import="com.kupkik.ui.html.view.*"%>
<%@ page import="java.util.List"%>
<%@page import="com.google.appengine.api.datastore.KeyFactory"%>

<%
	ViewHelper viewHelper = (ViewHelper) request
			.getAttribute("viewHelper");
	List<Season> seasons = (List<Season>) request
			.getAttribute("seasons");
	DisplaySkillGraph displaySkillGraph = (DisplaySkillGraph) request
			.getAttribute("displaySkillGraph");
%>

<%=viewHelper.createHtmlBegin("My Profile")%>
<div class="row-fluid">
		<div class="span12">
			<h1>Profile Crawler</h1>
	<form action="/" method="post">
		<input type="hidden" name="action" value="CreateMyProfile">

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
				<div class="span3">SEASON CHOOSER DROPDOWN</div>
				<div class="span2">
					<input type="submit" value="Crawl Profile">
				</div>
			</div>


	
	</form>


	<div class="row-fluid">
		<div class="span12">&nbsp;</div>
	</div>
<%
	if (displaySkillGraph != null) {


		StringBuilder skillTable = new StringBuilder();
		skillTable.append("<table class=\"table table-striped\">");
		skillTable.append("<thead> <th style=\"width:30%\"> Description</th> <th style=\"width:70%\"> Value </th>  </thead>");
		skillTable.append("<tr> <td>WL-RATE (WIN-LOOSE)</td><td>");
		skillTable.append(viewHelper.createSuccessBar(displaySkillGraph.getGamesWon(), displaySkillGraph.getGamesPlayed()));
		skillTable.append("</td> </tr>");
		
		skillTable.append("<tr> <td>Participation Rate</td><td>");
		skillTable.append(viewHelper.createSuccessBar(displaySkillGraph.getGamesPlayed(), displaySkillGraph.getBadmintonSingleGames().size()));
		skillTable.append("</td> </tr>");

		skillTable.append("<tr> <td>Games Won</td><td>");
		skillTable.append(displaySkillGraph.getGamesWon());
		skillTable.append("</td> </tr>");
		
		skillTable.append("<tr> <td>Games Played</td><td>");
		skillTable.append(displaySkillGraph.getGamesPlayed());
		skillTable.append("</td> </tr>");
		
		skillTable.append("<tr> <td>Games Lost</td><td>");
		skillTable.append(displaySkillGraph.getGamesLost());
		skillTable.append("</td> </tr>");
		
		skillTable.append("<tr> <td>Games Total</td><td>");
		skillTable.append(displaySkillGraph.getBadmintonSingleGames().size());
		skillTable.append("</td> </tr>");
		
		skillTable.append("</table>");
		
		out.println("<div class=\"row-fluid\">");
			out.println("<div class=\"span8\">");
			out.println(skillTable.toString());
			out.println("</div>");
			
			out.println("<div class=\"span4\">");
			out.println("</div>");
		out.println("</div>");	


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
