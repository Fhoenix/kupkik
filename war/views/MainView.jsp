<%@ page import="com.kupkik.model.*" %>
<%@ page import="com.kupkik.ui.html.view.*" %>
<%@ page import="java.util.List" %>

<% 
	ViewHelper viewHelper = (ViewHelper)request.getAttribute("viewHelper");  
	List<User> users = (List<User>)request.getAttribute("users");  
	List<Tournament> tournaments = (List<Tournament>)request.getAttribute("tournaments");  
%> 
<%= viewHelper.createHtmlBegin()  %> 
<%= viewHelper.createMainSiteIntroArea() %>


<%= viewHelper.newsThumbnails() %>   
<%= viewHelper.createHtmlEnd()  %> 