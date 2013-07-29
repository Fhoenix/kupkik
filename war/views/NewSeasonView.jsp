<%@ page
	import="com.google.appengine.repackaged.com.google.common.base.StringUtil"%>
<%@ page import="com.kupkik.messages.HandlerMessagesEnum"%>
<%@ page import="com.kupkik.model.*"%>
<%@ page import="com.kupkik.ui.html.view.*"%>
<%@ page import="com.kupkik.messages.HandlerMessagesEnum"%>
<%@ page
	import="com.sun.corba.se.impl.protocol.giopmsgheaders.MessageHandler"%>


<%
	ViewHelper viewHelper = (ViewHelper)request.getAttribute("viewHelper");
%>


<%=viewHelper.createHtmlBegin("Saison Erstellung")%>
<%
 	if(!StringUtil.isEmptyOrWhitespace((String)request.getAttribute(HandlerMessagesEnum.ERROR.toString())))
 	{
 %>
<div class="alert alert-danger"><%=viewHelper.convertTextForHtml((String)request.getAttribute(HandlerMessagesEnum.ERROR.toString()))%>
</div>
<%
	}else if(!StringUtil.isEmptyOrWhitespace((String)request.getAttribute(HandlerMessagesEnum.SUCCESS.toString()))) {
%>
<div class="alert alert-success"><%=viewHelper.convertTextForHtml((String)request.getAttribute(HandlerMessagesEnum.SUCCESS.toString()))%>
</div>
<% 		
	}
 %>


	<div class="col-lg-12">
		<form action="/" method="post">
			<input type="hidden" name="action" value="CreateSeason">

			<div class="row">
				<div class="col-lg-6">
					<fieldset>
						<legend>Create a Season</legend>
						<div class="form-group">
							<label>Season Name</label> <input
								class="form-control" placeholder="Season Name" name="name"
								type="text"
								value="<% 
					if (!StringUtil.isEmptyOrWhitespace((String)request.getAttribute("name"))){
						out.print((String)request.getAttribute("name"));
					}
					%>">
						</div>
						<div class="form-group">
						<label >Gametypes for Season</label>
							<div class="checkbox">
								<label> <input type="checkbox" name="gameType"
									value="BadmintonSingle">Badminton Single
								</label>
							</div>
							<div class="checkbox">
								<label> <input type="checkbox" name="gameType"
									value="BadmintonDouble">Badminton Double
								</label>
							</div>
						</div>
						<input class="btn btn-info form-control" type="submit"
							value=" Absenden ">
					</fieldset>
				</div>
				<div class="col-lg-6">
					<h3>Whats a Season?</h3>
					<p>WHATS A SEASON WHATS A SEASON WHATS A SEASON WHATS A SEASON
						WHATS A SEASON WHATS A SEASON WHATS A SEASON WHATS A SEASON WHATS
						A SEASON WHATS A SEASON WHATS A SEASON</p>
				</div>
			</div>


		</form>
	</div>


<%= viewHelper.createHtmlEnd()  %>
