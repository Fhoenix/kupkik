<%@ page import="com.google.appengine.repackaged.com.google.common.base.StringUtil"%>
<%@ page import="com.kupkik.messages.HandlerMessagesEnum"%>
<%@ page import="com.kupkik.model.*" %>
<%@ page import="com.kupkik.ui.html.view.*" %>
<%@ page import="com.kupkik.messages.HandlerMessagesEnum"%>
<%@ page import="com.sun.corba.se.impl.protocol.giopmsgheaders.MessageHandler"%>


<%
	ViewHelper viewHelper = (ViewHelper)request.getAttribute("viewHelper");
%> 


<%=viewHelper.createHtmlBegin("Saison Erstellung")%> 
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

	<div class="row">
		<form action="/" method="post">
			<input type="hidden" name="action" value="CreateSeason">
	        <h1>Create New Season</h1>
	        <div class="row">
				<div class="col-lg-12">&nbsp;</div>
			</div>
			<div class="row">
				<div class="col-lg-8"><input class="form-control" placeholder="Season Name" name="name" type="text" value="<% 
				if (!StringUtil.isEmptyOrWhitespace((String)request.getAttribute("name"))){
					out.print((String)request.getAttribute("name"));
				}
				%>"></div>
				<div class="col-lg-4">Create a new season (e.g. Badminton 2013)</div>
			</div>
			
			<div class="row">
				<div class="col-lg-1"><input type="checkbox" name="gameType" value="BadmintonSingle"></div>
				<div class="col-lg-11">Badminton Single</div>
			</div>
			<div class="row">
				<div class="col-lg-1"><input type="checkbox" name="gameType" value="BadmintonDouble"></div>
				<div class="col-lg-11">Badminton Double</div>
			</div>

			<div class="row">
				<div class="col-lg-2"><input class="btn btn-default" type="submit" value=" Absenden "></div>
				<div class="col-lg-5">&nbsp;</div>
				<div class="col-lg-5">&nbsp;</div>
			</div>
		</form>
	</div>

<%= viewHelper.createHtmlEnd()  %> 