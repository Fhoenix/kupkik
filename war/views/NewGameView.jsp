<%@page import="com.google.appengine.api.datastore.KeyFactory"%>
<%@ page import="com.kupkik.model.*"%>
<%@ page import="com.kupkik.ui.html.view.*"%>
<%@ page import="java.util.List"%>

<%
	ViewHelper viewHelper = (ViewHelper) request
			.getAttribute("viewHelper");
	List<User> users = (List<User>) request.getAttribute("users");
	List<Tournament> tournaments = (List<Tournament>) request
			.getAttribute("tournaments");
%>

<%=viewHelper.createHtmlBegin("Turnier Erstellung")%>
<form action="/" method="post">
	<input type="hidden" name="action" value="CreateBadmintonSingleGame">
	<div class="container-fluid">
	<div class="row-fluid">
		<div class="span6">
			<h1>ScoreIT</h1>

			<div class="row-fluid">
				<div class="span12">&nbsp;</div>
			</div>

			<div class="row-fluid">
				<div class="span4"><%=viewHelper.createLabel("Tournament Name", "label",
					"tournamentKey")%></div>
				<div class="span8">
					<select id="tournamentKey" style="width: 100%;"
						name="tournamentKey">
						<%
							for (Tournament item : tournaments) {
								out.println("	 <option value=\""
										+ KeyFactory.keyToString(item.getKey()) + "\">"
										+ item.getName() + " | "
										+ item.getParentKey().getName() + "</option>");
							}
						%>
					</select>
				</div>
			</div>

			<div class="row-fluid">
				<div class="span12">&nbsp;</div>
			</div>


			<div class="row-fluid">
				<div class="span2">Player 1</div>
				<div class="span6">

					<select id="playerOne" style="width: 100%;" name="playerOne">

						<%
							for (User item : users) {
								out.println("	 <option value=\"" + item.getName() + "\">"
										+ item.getName() + "</option>");
							}
						%>
					</select>


				</div>
				<div class="span2">Result</div>
				<div class="span2">
					<input id="resultOne" style="width: 80%;" name="resultOne"
						type="text" size="20" maxlength="20">
				</div>
			</div>




			<div class="row-fluid">
				<div class="span2">Player 2</div>
				<div class="span6">

					<select id="playerTwo" style="width: 100%;" name="playerTwo">

						<%
							for (User item : users) {
								out.println("	 <option value=\"" + item.getName() + "\">"
										+ item.getName() + "</option>");
							}
						%>
					</select>

				</div>
				<div class="span2">Result</div>
				<div class="span2">
					<input id="resultTwo" name="resultTwo" type="text"
						style="width: 80%;" maxlength="20">

				</div>
			</div>
			<div class="row-fluid">
				<div class="span12">
					<div class="row-fluid">
						<div class="span6">
							<input type="submit" value=" Absenden">
						</div>
						<div class="span6">
							&nbsp;
						</div>
					</div>
					<div class="span6"></div>
				</div>
			</div>
		</div>
		<div class="span6">
			<h1>Badminton Single</h1>
			
Beschreibung Turnier Beschreibung Turnier
					Beschreibung Turnier Beschreibung Turnier Beschreibung Turnier
					Beschreibung Turnier Beschreibung Turnier
					Beschreibung Turnier Beschreibung Turnier Beschreibung Turnier
					Beschreibung Turnier Beschreibung Turnier
					Beschreibung Turnier Beschreibung Turnier Beschreibung Turnier
					Beschreibung Turnier Beschreibung Turnier
					Beschreibung Turnier Beschreibung Turnier Beschreibung Turnier
					Beschreibung Turnier Beschreibung Turnier
					Beschreibung Turnier Beschreibung Turnier Beschreibung Turnier
					Beschreibung Turnier Beschreibung Turnier
					Beschreibung Turnier Beschreibung Turnier Beschreibung Turnier
					Beschreibung Turnier Beschreibung Turnier
					Beschreibung Turnier Beschreibung Turnier Beschreibung Turnier
					v
					Beschreibung Turnier Beschreibung Turnier
					Beschreibung Turnier Beschreibung Turnier Beschreibung Turnier
	
			


		</div>
		</div>
	</div>


</form>





<%
	if (request.getAttribute("errorMessage") != null) {
%>
<b>Fehler: </b><%=viewHelper.convertTextForHtml((String) request
						.getAttribute("errorMessage"))%>
<%
	}
%>

<%=viewHelper.createHtmlEnd()%>
