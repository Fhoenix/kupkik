<%@ page import="com.kupkik.model.game.Game"%>
<%@ page import="com.kupkik.model.*" %>
<%@ page import="com.kupkik.ui.html.view.*" %>
<%@ page import="java.util.List" %>

<%
	ViewHelper viewHelper = (ViewHelper)request.getAttribute("viewHelper");  
	List<User> users = (List<User>)request.getAttribute("users");  
	List<MatchDay> matchDays = (List<MatchDay>)request.getAttribute("matchDays");  
	List<Game> badmintonSingle = (List<Game>)request.getAttribute("badmintonSingle");
%> 
<%= viewHelper.createHtmlBegin("SpielErstellen")  %> 
<%= viewHelper.createMainSiteIntroArea() %>


<%= viewHelper.newsThumbnails(matchDays, badmintonSingle) %>   
<%= viewHelper.createHtmlEnd()  %> 