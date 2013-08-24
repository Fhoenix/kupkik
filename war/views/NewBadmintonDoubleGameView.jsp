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
	List<Season> seasons = (List<Season>) request.getAttribute("seasons");
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
	<input type="hidden" name="action" value="CreateBadmintonDoubleGame">

	<div class="row">
		<div class="col-lg-6">
	<h1>Score Badminton Double</h1>
				<fieldset>
						
					
				
					<div class="form-group">
							<label>Season</label>
								<select class="form-control" id="seasonKey" name="seasonKey">

					<%
							for (Season item : seasons) {
								out.println("	 <option value=\"" + KeyFactory.keyToString(item.getKey())+ "\">"
										+ item.getName()+"</option>");
							}
						%>
					</select>
					
					
					</div>
			
			
			
			
			
		

		

			

			


			<div class="form-group">
							<label>Team 1</label>
								<select class="form-control" id="teamOne-One" name="teamOne-One">

					<%
							for (User item : users) {
								out.println("	 <option value=\"" + KeyFactory.keyToString(item.getKey())+ "\">"
										+ item.getSurname() + ", "+ item.getFirstname()+"</option>");
							}
						%>
					</select>
					<select class="form-control" id="teamOne-Two" name="teamOne-Two">

						<%
							for (User item : users) {
								out.println("	 <option value=\"" + KeyFactory.keyToString(item.getKey()) + "\">"
										+ item.getSurname() + ", "+ item.getFirstname()+"</option>");
							}
						%>
					</select>
					<input class="form-control" class="fillLayout"  id="resultOne" placeholder="Result" name="resultOne" type="text">
					</div>
					
					
					
			
			<div class="form-group">
			<label>Team 2</label>
			<select class="form-control" id="teamTwo-One" style="width: 100%;" name="teamTwo-One">

						<%
							for (User item : users) {
								out.println("	 <option value=\"" + KeyFactory.keyToString(item.getKey()) + "\">"
										+ item.getSurname() + ", "+ item.getFirstname()+"</option>");
							}
						%>
			</select>
			
			<select class="form-control" id="teamTwo-Two" style="width: 100%;" name="teamTwo-Two">

						<%
							for (User item : users) {
								out.println("	 <option value=\"" + KeyFactory.keyToString(item.getKey()) + "\">"
										+ item.getSurname() + ", "+ item.getFirstname()+"</option>");
							}
						%>
					</select>
				<input class="form-control" id="resultTwo" placeholder="Result" name="resultTwo" type="text">
			</div>
				
			<div class="form-group">
			
				<input class="btn btn-info form-control" type="submit" value=" Absenden">
			</div>
					






			

					
			</fieldset>
		</div>
		<div class="col-lg-6">
			<h1>Badminton Double</h1>
			
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
