<%@page import="com.kupkik.model.ranking.WinLooseRows"%>
<%@page import="com.kupkik.model.ranking.TypedWinLooseRanking"%>
<%@page import="com.kupkik.model.ranking.WinLooseRanking"%>
<%@page import="com.kupkik.model.game.Game"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="com.sun.org.apache.xerces.internal.impl.xpath.regex.Match"%>
<%@ page import="com.kupkik.model.*"%>
<%@ page import="com.kupkik.ui.html.view.*"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.kupkik.messages.MessageError"%>
<%@ page import="java.util.Arrays"%>
<%@ page import="com.google.appengine.api.datastore.KeyFactory"%>
<%@ page import="com.google.appengine.api.datastore.Key"%>
<%@ page import="com.kupkik.messages.MessageHandlerEnum"%>
<%@ page import="com.google.appengine.repackaged.com.google.common.base.StringUtil"%>

<%
	ViewHelper viewHelper = (ViewHelper) request.getAttribute("viewHelper");
	List<Season> seasons = (List<Season>) request.getAttribute("seasons");
	List<User> users = (List<User>) request.getAttribute("users");
	ArrayList<User> editUsers = (ArrayList<User>) request.getAttribute("seasonEditUsers");
	String seasonKey = (String) request.getAttribute("seasonKey");
	String seasonName = (String) request.getAttribute("seasonName");
	String seasonType = (String) request.getAttribute("seasonGameType");
%>

<%=viewHelper.createHtmlBegin("Edite Season")%>
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
<div class="row">
	<div class="col-lg-12">
		<h3>Edit My Seasons</h3>
				<% 	
					if(!seasons.isEmpty()){
						%>
		<form action="/app/" method="post">
			<input class="form-control" type="hidden" name="action"
				value="EditSeason">

			<div class="row">
				<div class="col-lg-2">Choose a Season</div>
				<div class="col-lg-8">
					<select class="form-control" name="seasonKey">
						<%
							for (Season item : seasons) {

								out.println("	 <option value=\""
										+ KeyFactory.keyToString(item.getKey()) + "\">"
										+ item.getName() + ", "+ item.getGameType()+"</option>");

							}
						%>
						
						
					</select>
					
					

				</div>
				
				<div class="col-lg-2">
					<input class="btn btn-default" type="submit" value="Edit Season">
				</div>
			</div>
		</form>
		<%
		}else{
				out.println("<div class=\"alert alert-danger\">"+MessageError.COMMON_NO_SEASONS_AVAILABLE+"</div>");
		}
		%>
		<% 	if(users != null && editUsers != null && seasonKey != null){  %>
		<h3><% out.println(seasonName + " - " + seasonType); %> </h3>
		<form action="/app/" method="post">
			<input class="form-control" type="hidden" name="action"
				value="UpdateSeason">
		 	<fieldset>
					
				<input name="seasonKey" id="seasonKey" type="hidden" value="<% 	out.println(seasonKey);%>">
						
						<div class="form-group">
						<label >Users that can score games</label>
								<%
							for (User item : users) {
								
									String name = item.getSurname() +", "+ item.getFirstname();
									
								if (viewHelper.checkUserEquality(editUsers, item)){
									out.println(viewHelper.createCheckBox("usersToEdit", KeyFactory.keyToString(item.getKey()), name, true));
								}else{
									out.println(viewHelper.createCheckBox("usersToEdit", KeyFactory.keyToString(item.getKey()), name, false));
								}
							}

								
							%>
						
				
						
						
						</div>
						
						<input class="btn btn-info form-control" type="submit"
							value=" Absenden ">
					</fieldset>
					
				</form>	
				<% } %>
					
	</div>
</div>




<%=viewHelper.createHtmlEnd()%>
