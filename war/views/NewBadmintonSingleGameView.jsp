<%@ page import="com.google.appengine.api.datastore.KeyFactory"%>
<%@ page import="com.kupkik.model.*"%>
<%@ page import="com.kupkik.ui.html.view.*"%>
<%@ page import="java.util.List"%>
<%@ page import="com.google.appengine.repackaged.com.google.common.base.StringUtil"%>
<%@ page import="com.kupkik.messages.MessageHandlerEnum"%>
<%@ page import="com.kupkik.messages.MessageError"%>
<%@ page import="com.kupkik.messages.MessageBadmintonSingle"%>


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
	<input type="hidden" name="action" value="CreateBadmintonSingleGame">

	<div class="row">
		<div class="col-lg-6">
		
		
			<h1><% out.println(""+MessageBadmintonSingle.HEADLINE_FORM); %></h1>
				<% 	
					if(!seasons.isEmpty()){
						%>
				<fieldset>
					<legend></legend>
					
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
							<label>Player 1</label>
							<select class="form-control" id="playerOne" name="playerOne">

							<%
							for (User item : users) {
								out.println("	 <option value=\"" + KeyFactory.keyToString(item.getKey())+ "\">"
										+ item.getSurname() + ", "+ item.getFirstname()+"</option>");
							}
							%>
							</select>
							
							<input class="form-control"  id="resultOne" placeholder="Result" name="resultOne" type="number">
							
					</div>
					
					
					<div class="form-group">
							<label>Player 2</label>
							<select class="form-control" id="playerTwo" style="width: 100%;" name="playerTwo">

							<%
							for (User item : users) {
								out.println("	 <option value=\"" + KeyFactory.keyToString(item.getKey())+ "\">"
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
		<h1><% out.println(MessageBadmintonSingle.HEADLINE_DESCRIPTION); %></h1>
			<% out.println(MessageBadmintonSingle.DESCRIPTION); %>
			


		</div>
		</div>



</form>

<%=viewHelper.createHtmlEnd()%>
