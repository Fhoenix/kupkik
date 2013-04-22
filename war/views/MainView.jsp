<%@ page import="com.kupkik.model.*" %>
<%@ page import="com.kupkik.ui.html.view.*" %>
<%@ page import="java.util.List" %>

<% 
	ViewHelper viewHelper = (ViewHelper)request.getAttribute("viewHelper");  
	List<User> users = (List<User>)request.getAttribute("users");  
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
        
<%= viewHelper.createHtmlEnd()  %> 