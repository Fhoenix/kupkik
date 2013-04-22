<%@ page import="com.kupkik.model.*" %>
<%@ page import="com.kupkik.ui.html.view.*" %>

<% ViewHelper viewHelper = (ViewHelper)request.getAttribute("viewHelper");  %> 
<%= viewHelper.createHtmlBegin("Login", false)  %> 

        <H1 ALIGN="CENTER">Login</H1>
        
        <form action="/" method="post">
            <input type="hidden" name="action" value="login_user">
            Name:
            <br>
            <input name="user_name" type="text" size="50" maxlength="50">
            <br>
            Passwort:
            <br>
            <input name="password" type="password" size="12" maxlength="12">
            <br>
            <input type="submit" value=" Absenden ">
            <br>
            Hinweis: Das Passwort wird nicht im Klartext in der Datenbank gespeichert.
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
    	<b>Fehler: </b><%= viewHelper.convertTextForHtml((String)request.getAttribute("errorMessage")) %>
<% 
	}
 %> 

<%= viewHelper.createHtmlEnd()  %> 