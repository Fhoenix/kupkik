<%@ page import="com.google.appengine.api.datastore.KeyFactory"%>
<%@ page import="com.kupkik.model.*"%>
<%@ page import="com.kupkik.ui.html.view.*"%>
<%@ page import="java.util.List"%>
<%@ page import="com.google.appengine.repackaged.com.google.common.base.StringUtil"%>
<%@ page import="com.kupkik.messages.MessageHandlerEnum"%>
<%@ page import="com.kupkik.messages.MessageError"%>
<%@ page import="com.kupkik.messages.MessageBadmintonDouble"%>


<%
	ViewHelper viewHelper = (ViewHelper) request
	.getAttribute("viewHelper");
	List<User> users = (List<User>) request.getAttribute("users");
	List<Season> seasons = (List<Season>) request.getAttribute("seasons");
%>

<%=viewHelper.createHtmlBegin("Turnier Erstellung")%>

<%
	if(!StringUtil.isEmptyOrWhitespace((String)request.getAttribute(MessageHandlerEnum.ERROR.toString())))
 	{
%> 
    	<div class="alert alert-danger"><%=viewHelper.convertTextForHtml((String)request.getAttribute(MessageHandlerEnum.ERROR.toString()))%> </div>
<%
	}else if(!StringUtil.isEmptyOrWhitespace((String)request.getAttribute(MessageHandlerEnum.SUCCESS.toString()))) {
%>
		<div class="alert alert-success"><%=viewHelper.convertTextForHtml((String)request.getAttribute(MessageHandlerEnum.SUCCESS.toString()))%> </div>
<%
	}
%> 

<form action="/" method="post">
	<input type="hidden" name="action" value="CreateBadmintonDoubleGame">

	<div class="row">
		<div class="col-lg-6">
	<h3><% out.println(""+MessageBadmintonDouble.HEADLINE_FORM); %></h3>
					<% 	
					if(!seasons.isEmpty()){
						
					
						%>
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
					<select class="form-control" id="teamOne-Two" name="teamOne-Two" >

						<%
							for (User item : users) {
								out.println("	 <option value=\"" + KeyFactory.keyToString(item.getKey()) + "\">"
										+ item.getSurname() + ", "+ item.getFirstname()+"</option>");
							}
						%>
					</select>
					<input class="form-control" class="fillLayout"  id="resultOne" placeholder="Result" name="resultOne" type="number">
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
				<input class="form-control" id="resultTwo" placeholder="Result" name="resultTwo" type="number">
			</div>
				
			<div class="form-group">
			
				<input class="btn btn-info form-control" type="submit" value=" Absenden">
			</div>
					
			</fieldset>
			<%
					}else{
						out.println("<div class=\"alert alert-danger\">"+MessageError.COMMON_NO_SEASONS_AVAILABLE+"</div>");
					}
					
			%>
		</div>
		<div class="col-lg-6">
			<h3><% out.println(MessageBadmintonDouble.HEADLINE_DESCRIPTION);  %></h3>
			<% out.println(MessageBadmintonDouble.DESCRIPTION); %>
		</div>
		</div>



</form>

<%=viewHelper.createHtmlEnd()%>
