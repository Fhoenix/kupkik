<%@page import="com.kupkik.model.ranking.WinLooseRows"%>
<%@page import="com.kupkik.model.ranking.TypedWinLooseRanking"%>
<%@page import="com.kupkik.model.ranking.WinLooseRanking"%>
<%@page import="com.kupkik.model.game.Game"%>
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

	WinLooseRanking winLooseRanking = (WinLooseRanking) request
			.getAttribute("winLooseRanking");
	
%>

<%=viewHelper.createHtmlBegin("Ranking Factory")%>
<div class="row-fluid">
	<div class="span12">
		<h1>Ranking Factory</h1>
		<form action="/" method="post">
			<input class="fillLayout" type="hidden" name="action"
				value="CreateRanking">

			<div class="row-fluid">
				<div class="span2">Choose a Season</div>
				<div class="span8">
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
				
				<div class="span2">
					<input type="submit" value="Create Ranking">
				</div>
			</div>



		</form>


<%
	if (winLooseRanking != null) {
		
		
		List<TypedWinLooseRanking> typed = winLooseRanking.getOverallRanking();
			StringBuilder rankingTable = new StringBuilder();
		for(TypedWinLooseRanking item : typed){
			
			rankingTable.append("<h1>"+item.getGameType() + "</h1>");
			rankingTable.append("<table class=\"table table-striped\">");
			rankingTable.append("<thead> <th>Name</th> <th>Total Games</th> <th>Total Games Participated</th> <th>Games Won</th><th>Win Loose Rate</th></thead>");

			List<WinLooseRows> rank = item.getRanking();
			for(WinLooseRows row: rank){
				rankingTable.append("<tr>");
				rankingTable.append("<td>"+ row.getUser().getSurname()+", "+ row.getUser().getFirstname()  +"</td>");
				rankingTable.append("<td>"+ row.getTotalNumberOfGames() +"</td>");
				rankingTable.append("<td>"+ row.getGamesPlayed() +"</td>");
				rankingTable.append("<td>"+ row.getGamesWon() +"</td>");
				rankingTable.append("<td>"+ viewHelper.createSuccessBar(row.getGamesWon(), row.getGamesPlayed()) +"</td>");
				rankingTable.append("</tr>");
			}
			rankingTable.append("</table>");
		}
		out.println(rankingTable.toString());
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
