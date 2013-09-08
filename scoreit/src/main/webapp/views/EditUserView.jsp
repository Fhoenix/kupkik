<%@ page import="com.kupkik.model.*" %>
<%@ page import="com.kupkik.ui.html.view.*" %>
<%@ page import="com.google.appengine.repackaged.com.google.common.base.StringUtil"%>
<%@ page import="com.kupkik.messages.MessageHandlerEnum"%>
<%@ page import="com.kupkik.messages.MessageUpdateUserPassword"%>

<%
	ViewHelper viewHelper = (ViewHelper)request.getAttribute("viewHelper");  
%> 

<%=viewHelper.createHtmlBegin("Reset Password")%> 

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



<form action="/app/" method="post">
	<input type="hidden" name="action" value="EditUser">

	<div class="row">
		<div class="col-lg-6">
			<h3><% out.println(""+MessageUpdateUserPassword.HEADLINE_FORM); %></h3>

			<div class="row">
				<div class="col-lg-12">&nbsp;</div>
			</div>

			<div class="row">
					<div class="col-lg-4">Old Password</div>
					<div class="col-lg-8"><input class="form-control" placeholder="Old Password" name="oldPassword" type="password" size="12" maxlength="12"></div>
			</div>
			
			<div class="row">
					<div class="col-lg-4">New Password</div>
					<div class="col-lg-8"><input class="form-control" placeholder="New Password" name="password1" type="password" size="12" maxlength="12"></div>
			</div>

			<div class="row">
				<div class="col-lg-4">Confirm New Password</div>
				<div class="col-lg-8"><input class="form-control" placeholder="Confirm New Password" name="password2" type="password" size="12" maxlength="12"></div>
			</div>
			
			<div class="row">
				<div class="col-lg-12"> <input class="btn btn-default" type="submit" value=" Absenden "></div>
			</div>
		</div>
		<div class="col-lg-6">
			<h3><% out.println(""+MessageUpdateUserPassword.HEADLINE_DESCRIPTION); %></h3>
			<% out.println(""+MessageUpdateUserPassword.DESCRIPTION); %>


		</div>
	</div>



</form>



<%= viewHelper.createHtmlEnd()  %> 