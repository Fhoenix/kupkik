<%@ page import="com.google.appengine.repackaged.com.google.common.base.StringUtil"%>
<%@ page import="com.kupkik.messages.MessageHandlerEnum"%>
<%@ page import="com.kupkik.model.*"%>
<%@ page import="com.kupkik.ui.html.view.*"%>
<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory"%>
<%@ page import="com.kupkik.messages.MessageHandlerEnum"%>



<%
	ViewHelper viewHelper = (ViewHelper)request.getAttribute("viewHelper");
List<User> users = (List<User>) request.getAttribute("users");
%>


<%=viewHelper.createHtmlBegin("Saison Erstellung")%>
<%
 	if(!StringUtil.isEmptyOrWhitespace((String)request.getAttribute(MessageHandlerEnum.ERROR.toString())))
 	{
 %>
<div class="alert alert-danger"><%=viewHelper.convertTextForHtml((String)request.getAttribute(MessageHandlerEnum.ERROR.toString()))%>
</div>
<%
	}else if(!StringUtil.isEmptyOrWhitespace((String)request.getAttribute(MessageHandlerEnum.SUCCESS.toString()))) {
%>
<div class="alert alert-success"><%=viewHelper.convertTextForHtml((String)request.getAttribute(MessageHandlerEnum.SUCCESS.toString()))%>
</div>
<% 		
	}
 %>


	<div class="col-lg-12">
		<form action="/" method="post">
			<input type="hidden" name="action" value="CreateSeason">

			<div class="row">
				<div class="col-lg-6">
						<h1>Create a Season</h1>
					<fieldset>
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
							<div class="radio">
								<label> <input type="radio" name="gameType"
									value="BadmintonSingle">Badminton Single
								</label>
							</div>
							<div class="radio">
								<label> <input type="radio" name="gameType"
									value="BadmintonDouble">Badminton Double
								</label>
							</div>
								<div class="radio">
								<label> <input type="radio" name="gameType"
									value="Kicker">Kicker
								</label>
							</div>
						</div>
						
						<div class="form-group">
						<label >Users that can score games</label>
						
								<%
							for (User item : users) {
								String name = item.getSurname() +", "+ item.getFirstname();
								out.println(viewHelper.createCheckBox("users", KeyFactory.keyToString(item.getKey()), name));
							}
							%>
						
				
						
						
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
