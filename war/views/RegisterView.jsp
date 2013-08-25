<%@ page import="com.kupkik.model.*" %>
<%@ page import="com.kupkik.ui.html.view.*" %>
<%@ page import="com.google.appengine.repackaged.com.google.common.base.StringUtil"%>
<%@ page import="com.kupkik.messages.MessageHandlerEnum"%>
<%@ page import="com.kupkik.messages.MessageRegister"%>

<%
	ViewHelper viewHelper = (ViewHelper)request.getAttribute("viewHelper");  

    String initializeUserName = "";
    if(request.getParameter("user_name") != null)
    {
	    initializeUserName = (String)request.getParameter("user_name");
    }
%> 

<%=viewHelper.createHtmlBegin("Registrieren")%> 

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
	<input type="hidden" name="action" value="RegisterUser">

	<div class="row">
		<div class="col-lg-6">
			<h1><% out.println(""+MessageRegister.HEADLINE_FORM); %></h1>

			<div class="row">
				<div class="col-lg-12">&nbsp;</div>
			</div>

			<div class="row">
				<div class="col-lg-4">E-Mail</div>
				<div class="col-lg-8">  <input class="form-control" placeholder="email@example.com" name="user_name" type="email" size="50" maxlength="50" value="<%=initializeUserName%>"></div>
			</div>
			<div class="row">
				<div class="col-lg-4">Firstname</div>
				<div class="col-lg-8">  <input class="form-control" placeholder="Your Firstname" name="firstname" type="text" size="50" maxlength="50"></div>
			</div>
			<div class="row">
				<div class="col-lg-4">Lastname</div>
				<div class="col-lg-8">  <input class="form-control" placeholder="Your Lastname" name="surname" type="text" size="50" maxlength="50"></div>
			</div>

			<div class="row">
					<div class="col-lg-4">Password</div>
					<div class="col-lg-8"><input class="form-control" placeholder="Please Enter Your Password here" name="password1" type="password" size="12" maxlength="12"></div>
			</div>

			<div class="row">
				<div class="col-lg-4">Password repeat</div>
				<div class="col-lg-8"><input class="form-control" placeholder="Confirm It" name="password2" type="password" size="12" maxlength="12"></div>
			</div>
			
			<div class="row">
				<div class="col-lg-12"> <input class="btn btn-default" type="submit" value=" Absenden "></div>
			</div>
		</div>
		<div class="col-lg-6">
			<h1><% out.println(""+MessageRegister.HEADLINE_DESCRIPTION); %></h1>
			<% out.println(""+MessageRegister.DESCRIPTION); %>


		</div>
	</div>



</form>



<%= viewHelper.createHtmlEnd()  %> 