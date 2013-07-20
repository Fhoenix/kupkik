<%@ page import="com.kupkik.model.*" %>
<%@ page import="com.kupkik.ui.html.view.*" %>
<%@ page import="com.google.appengine.repackaged.com.google.common.base.StringUtil"%>
<%@ page import="com.kupkik.messages.HandlerMessagesEnum"%>
<%@ page import="com.sun.corba.se.impl.protocol.giopmsgheaders.MessageHandler"%>

<% 
    ViewHelper viewHelper = (ViewHelper)request.getAttribute("viewHelper");  

    String initializeUserName = "";
    if(request.getParameter("user_name") != null)
    {
	    initializeUserName = (String)request.getParameter("user_name");
    }
%> 

<%= viewHelper.createHtmlBegin("Registrieren")  %> 

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
	<input type="hidden" name="action" value="RegisterUser">

	<div class="row-fluid">
		<div class="span6">
			<h1>Register</h1>

			<div class="row-fluid">
				<div class="span12">&nbsp;</div>
			</div>

			<div class="row-fluid">
				<div class="span4">E-Mail</div>
				<div class="span8">  <input class="fillLayout" placeholder="email@example.com" name="user_name" type="email" size="50" maxlength="50" value="<%=initializeUserName%>"></div>
			</div>
			<div class="row-fluid">
				<div class="span4">Firstname</div>
				<div class="span8">  <input class="fillLayout" placeholder="Your Firstname" name="firstname" type="text" size="50" maxlength="50"></div>
			</div>
			<div class="row-fluid">
				<div class="span4">Lastname</div>
				<div class="span8">  <input class="fillLayout" placeholder="Your Lastname" name="surname" type="text" size="50" maxlength="50"></div>
			</div>

			<div class="row-fluid">
					<div class="span4">Password</div>
					<div class="span8"><input class="fillLayout" placeholder="Please Enter Your Password here" name="password1" type="password" size="12" maxlength="12"></div>
			</div>

			<div class="row-fluid">
				<div class="span4">Password repeat</div>
				<div class="span8"><input class="fillLayout" placeholder="Confirm It" name="password2" type="password" size="12" maxlength="12"></div>
			</div>
			
			<div class="row-fluid">
				<div class="span12"> <input type="submit" value=" Absenden "></div>
			</div>
		</div>
		<div class="span6">
			<h1>REGISTER USER </h1>
			
			REGISTER USER REGISTER USER REGISTER USER REGISTER USER 
			REGISTER USER REGISTER USER REGISTER USER REGISTER USER
			REGISTER USER REGISTER USER REGISTER USER REGISTER USER 
			REGISTER USER REGISTER USER REGISTER USER REGISTER USER 
			REGISTER USER 
			


		</div>
	</div>



</form>



<%= viewHelper.createHtmlEnd()  %> 