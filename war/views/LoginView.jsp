<%@ page import="com.kupkik.model.*" %>
<%@ page import="com.kupkik.ui.html.view.*" %>

<% 
    ViewHelper viewHelper = (ViewHelper)request.getAttribute("viewHelper");  

    String initializeUserName = "";
    if(request.getParameter("user_name") != null)
    {
	    initializeUserName = (String)request.getParameter("user_name");
    }
%> 

<%= viewHelper.createHtmlBegin("Login")  %> 

        <H1 ALIGN="CENTER">Login</H1>
        
        <form action="/" method="post">
            <input type="hidden" name="action" value="Login">
            Name:
            <br>
            <input name="user_name" type="text" size="50" maxlength="50" value="<%=initializeUserName%>">
            <br>
            Passwort:
            <br>
            <input name="password" type="password" size="12" maxlength="12">
            <br>
            <input type="submit" value=" Absenden ">
        </form>
        
        <br>
        <br>
        <a href="/">zur&uuml;ck zur Hauptseite</a>
        <br>
        <br>
        Noch nicht registriert?
        <br>
        <a href="/?showView=RegisterView">Hier registrieren!</a>
        <br>

<% 
	if(request.getAttribute("errorMessage") != null)
	{
 %> 
    	<p class="text-error">Fehler: <%= viewHelper.convertTextForHtml((String)request.getAttribute("errorMessage")) %></p>
<% 
	}
 %> 

<%= viewHelper.createHtmlEnd()  %> 