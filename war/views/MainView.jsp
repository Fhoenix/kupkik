<%@ page import="com.kupkik.model.*" %>
<%@ page import="com.kupkik.ui.html.view.*" %>
<%@ page import="java.util.List" %>

<% 
	ViewHelper viewHelper = (ViewHelper)request.getAttribute("viewHelper");  
	List<User> users = (List<User>)request.getAttribute("users");  
	List<Tournament> tournaments = (List<Tournament>)request.getAttribute("tournaments");  
%> 
<%= viewHelper.createHtmlBegin()  %> 

        <H1 ALIGN="CENTER">kupkik</H1>
	
	        <div style="float:right; width:200px;">
		        <p>Users:</p>
		        <%        
		        for(User currentUser : users)
		        {
		            out.println("<br>" + currentUser.getName());
		        }
		        %>
		        <br>
	        </div>
	        
	    <a href="/?showView=NewTournamentView">Erstellung eines neues Turniers</a>
		<br>
		
		<%        
		for(Tournament currentTournament : tournaments)
		{
			out.println("<br>" + currentTournament.getName());
		}
		%>
		<h1>Test Componenten</h1>
		
		<% out.println(viewHelper.createSuccessBar(44.5)); %>
        
<%= viewHelper.createHtmlEnd()  %> 