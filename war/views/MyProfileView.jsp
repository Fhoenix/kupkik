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
	<form action="/" method="post">
		<input type="hidden" name="action" value="CreateMyProfile">
		<div class="span12">
			<h1>Profile Crawler</h1>

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


		</div>
	</form>
</div>

<div class="row-fluid">
	<div class="span12">&nbsp;</div>
</div>
<%
	if (displaySkillGraph != null) {


		out.println("<div class=\"row-fluid\">");
			out.println("<div class=\"span8\">");
			out.println("<div class=\"row-fluid\">");
				out.println("<div class=\"span4\">WL-RATE (WIN-LOOSE)</div>");
				out.println("<div class=\"span8\">"+ viewHelper.createSuccessBar(displaySkillGraph.getGamesWon(), displaySkillGraph.getGamesPlayed())+"</div>");
			out.println("</div>");
			out.println("<div class=\"row-fluid\">");
				out.println("<div class=\"span4\">Participation Rate</div>");
				out.println("<div class=\"span8\">"+ viewHelper.createSuccessBar(displaySkillGraph.getGamesPlayed(), displaySkillGraph.getBadmintonSingleGames().size())+"</div>");
			out.println("</div>");
		
			out.println("<div class=\"row-fluid\">");
				out.println("<div class=\"span4\">Total Games in that Season</div>");
				out.println("<div class=\"span8\">"+displaySkillGraph.getBadmintonSingleGames().size()+"</div>");
			out.println("</div>");
		out.println("</div>");
		out.println("<div class=\"span8\">&nbsp;");
		out.println("</div>");		
		out.println("</div>");

	}
%>
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
