<%@page import="com.kupkik.model.game.BadmintonSingle"%>
<%@ page import="com.kupkik.model.*" %>
<%@ page import="com.kupkik.ui.html.view.*" %>
<%@ page import="java.util.List" %>

<% 
	ViewHelper viewHelper = (ViewHelper)request.getAttribute("viewHelper");  
List<User> users = (List<User>)request.getAttribute("users");  
	List<Tournament> tournaments = (List<Tournament>)request.getAttribute("tournaments");  
	List<BadmintonSingle> badmintonSingle = (List<BadmintonSingle>)request.getAttribute("badmintonSingle");  
	
%> 
<%= viewHelper.createHtmlBegin("SpielErstellen")  %> 
<%= viewHelper.createMainSiteIntroArea() %>


<%= viewHelper.newsThumbnails(tournaments, badmintonSingle) %>   
<%= viewHelper.createHtmlEnd()  %> 