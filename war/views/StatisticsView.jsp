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

	DisplaySkillGraph displaySkillGraph = (DisplaySkillGraph) request
			.getAttribute("displaySkillGraph");

	DisplaySkillGraph displaySkillGraphDouble = (DisplaySkillGraph) request
			.getAttribute("displaySkillGraphDouble");
	
	DisplaySkillGraph displaySkillGraphKicker = (DisplaySkillGraph) request
			.getAttribute("displaySkillGraphKicker");
%>

<%=viewHelper.createHtmlBegin("Statistics Factory")%>
<div class="row">
	<div class="col-lg-12">
		<h1>View Statistics</h1>
		<form action="/" method="post">
			<input class="form-control" type="hidden" name="action"
				value="CreateStatistics">

			<div class="row">
				<div class="col-lg-2">Choose a Season</div>
				<div class="col-lg-3">
					<select class="form-control" name="seasonKey">
						<%
							for (Season item : seasons) {

								out.println("	 <option value=\""
										+ KeyFactory.keyToString(item.getKey()) + "\">"
										+ item.getName() + "</option>");

							}
						%>
					</select>

				</div>
				<div class="col-lg-2">Choose a Player</div>
				<div class="col-lg-3">
					<select class="form-control" id="userprofile" name="userprofile">

						<%
							for (User item : users) {
								out.println("	 <option value=\"" +KeyFactory.keyToString(item.getKey())+ "\">"
										+ item.getSurname() + ", " + item.getFirstname()
										+ "</option>");
							}
						%>
					</select>
				</div>
				<div class="col-lg-2">
					<input class="btn btn-default" type="submit" value="Create Statistics">
				</div>
			</div>



		</form>



		<%
				UserWithPassword selectedUser =	(UserWithPassword) request.getAttribute("selectedUser");
			if (displaySkillGraph != null) {
		%>
		<div class="row">
			<div class="col-lg-12"><h2>Profile <%= selectedUser.getSurname() +", "+selectedUser.getFirstname() %></h2></div>
		</div>
		<div class="row">
			<div class="col-lg-12"><h3>Badminton Single Games</h3></div>
		</div>
		<%
		out.println(viewHelper.createStatistics(displaySkillGraph, "SkillGraphCanvasSingle", "BadmintonsingleCanvas", selectedUser));
			}
		%>


		<%
			if (displaySkillGraphDouble != null) {
		%>
		<div class="row">
			<div class="col-lg-12"><h3>Badminton Double Games</h3></div>
		</div>
		<%
		out.println(viewHelper.createStatistics(displaySkillGraphDouble, "SkillGraphCanvasDouble", "BadmintondoubleCanvas", selectedUser));
			}
		%>


<%
			if (displaySkillGraphKicker != null) {
		%>
		<div class="row">
			<div class="col-lg-12"><h3>Kicker Games</h3></div>
		</div>
		<%
		out.println(viewHelper.createStatistics(displaySkillGraphKicker, "SkillGraphCanvasKicker", "KickerCanvas", selectedUser));
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
