<%@ page import="com.google.appengine.api.datastore.KeyFactory"%>
<%@ page import="com.kupkik.model.*"%>
<%@ page import="com.kupkik.ui.html.view.*"%>
<%@ page import="java.util.List"%>
<%@ page import="com.google.appengine.repackaged.com.google.common.base.StringUtil"%>
<%@ page import="com.kupkik.messages.HandlerMessagesEnum"%>
<%@ page import="com.sun.corba.se.impl.protocol.giopmsgheaders.MessageHandler"%>


<%
	ViewHelper viewHelper = (ViewHelper) request
			.getAttribute("viewHelper");
	List<User> users = (List<User>) request.getAttribute("users");
	List<Tournament> tournaments = (List<Tournament>) request
			.getAttribute("tournaments");
%>

<%=viewHelper.createHtmlBegin("Turnier Erstellung")%>

<%
 	if(!StringUtil.isEmptyOrWhitespace((String)request.getAttribute(HandlerMessagesEnum.ERROR.toString())))
 	{
 %> 
    	<div class="alert alert-error"><%=viewHelper.convertTextForHtml((String)request.getAttribute(HandlerMessagesEnum.ERROR.toString()))%> </div>
<%
	}else if(!StringUtil.isEmptyOrWhitespace((String)request.getAttribute(HandlerMessagesEnum.SUCCESS.toString()))) {
%>
		<div class="alert alert-success"><%=viewHelper.convertTextForHtml((String)request.getAttribute(HandlerMessagesEnum.SUCCESS.toString()))%> </div>
<% 		
	}
 %> 


<form action="/" method="post">
	<input type="hidden" name="action" value="CreateBadmintonDoubleGame">

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
					<select id="tournamentKey" name="tournamentKey">
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
				<div class="span2">Team 1</div>
				<div class="span3">

					<select id="teamOne-One" name="teamOne-One">

						<%
							for (User item : users) {
								out.println("	 <option value=\"" + item.getName() + "\">"
										+ item.getSurname() + ", "+ item.getFirstname()+"</option>");
							}
						%>
					</select>


				</div>
					<div class="span3">

					<select id="teamOne-Two" name="teamOne-Two">

						<%
							for (User item : users) {
								out.println("	 <option value=\"" + item.getName() + "\">"
										+ item.getSurname() + ", "+ item.getFirstname()+"</option>");
							}
						%>
					</select>


				</div>
				
				<div class="span4">
					<input class="fillLayout"  id="resultOne" placeholder="Result" name="resultOne" type="text">
				</div>
			</div>




			<div class="row-fluid">
				<div class="span2">Player 2</div>
				<div class="span3">

					<select id="teamTwo-One" style="width: 100%;" name="teamTwo-One">

						<%
							for (User item : users) {
								out.println("	 <option value=\"" + item.getName() + "\">"
										+ item.getSurname() + ", "+ item.getFirstname()+"</option>");
							}
						%>
					</select>

				</div>
					<div class="span3">

					<select id="teamTwo-Two" style="width: 100%;" name="teamTwo-Two">

						<%
							for (User item : users) {
								out.println("	 <option value=\"" + item.getName() + "\">"
										+ item.getSurname() + ", "+ item.getFirstname()+"</option>");
							}
						%>
					</select>

				</div>
			
				<div class="span4">
					<input class="fillLayout" id="resultTwo" placeholder="Result" name="resultTwo" type="text">

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



</form>


<%=viewHelper.createHtmlEnd()%>
