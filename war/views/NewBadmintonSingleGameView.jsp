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
	List<MatchDay> matchDays = (List<MatchDay>) request
	.getAttribute("matchDays");
%>

<%=viewHelper.createHtmlBegin("Turnier Erstellung")%>

<%
	if(!StringUtil.isEmptyOrWhitespace((String)request.getAttribute(HandlerMessagesEnum.ERROR.toString())))
 	{
%> 
    	<div class="alert alert-danger"><%=viewHelper.convertTextForHtml((String)request.getAttribute(HandlerMessagesEnum.ERROR.toString()))%> </div>
<%
	}else if(!StringUtil.isEmptyOrWhitespace((String)request.getAttribute(HandlerMessagesEnum.SUCCESS.toString()))) {
%>
		<div class="alert alert-success"><%=viewHelper.convertTextForHtml((String)request.getAttribute(HandlerMessagesEnum.SUCCESS.toString()))%> </div>
<%
	}
%> 


<form action="/" method="post">
	<input type="hidden" name="action" value="CreateBadmintonSingleGame">

	<div class="row">
		<div class="col-lg-6">
			<h1>ScoreIT</h1>

			<div class="row">
				<div class="col-lg-12">&nbsp;</div>
			</div>

			<div class="row">
				<div class="col-lg-4"><%=viewHelper.createLabel("Matchday Name", "label",
					"matchDayKey")%></div>
				<div class="col-lg-8">
					<select class="form-control" id="matchDayKey" name="matchDayKey">
						<%
							for (MatchDay item : matchDays) {
												out.println("	 <option value=\""
														+ KeyFactory.keyToString(item.getKey()) + "\">"
														+ item.getName() + " | "
														+ item.getParentKey().getName() + "</option>");
											}
						%>
					</select>
				</div>
			</div>

			<div class="row">
				<div class="col-lg-12">&nbsp;</div>
			</div>


			<div class="row">
				<div class="col-lg-2">Player 1</div>
				<div class="col-lg-6">

					<select class="form-control" id="playerOne" name="playerOne">

						<%
							for (User item : users) {
								out.println("	 <option value=\"" + KeyFactory.keyToString(item.getKey())+ "\">"
										+ item.getSurname() + ", "+ item.getFirstname()+"</option>");
							}
						%>
					</select>


				</div>
				
				<div class="col-lg-4">
					<input class="form-control"  id="resultOne" placeholder="Result" name="resultOne" type="number">
				</div>
			</div>




			<div class="row">
				<div class="col-lg-2">Player 2</div>
				<div class="col-lg-6">

					<select class="form-control" id="playerTwo" style="width: 100%;" name="playerTwo">

						<%
							for (User item : users) {
								out.println("	 <option value=\"" + KeyFactory.keyToString(item.getKey())+ "\">"
										+ item.getSurname() + ", "+ item.getFirstname()+"</option>");
							}
						%>
					</select>

				</div>
			
				<div class="col-lg-4">
					<input class="form-control" id="resultTwo" placeholder="Result" name="resultTwo" type="number">

				</div>
			</div>
			<div class="row">
				<div class="col-lg-12">
					<div class="row">
						<div class="col-lg-6">
							<input class="btn btn-default" type="submit" value=" Absenden">
						</div>
						<div class="col-lg-6">
							&nbsp;
						</div>
					</div>
					<div class="col-lg-6"></div>
				</div>
			</div>
		</div>
		<div class="col-lg-6">
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
