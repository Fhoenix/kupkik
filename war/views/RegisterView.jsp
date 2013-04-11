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

<%= viewHelper.createHtmlBegin("Registrieren", false)  %> 

        <H1 ALIGN="CENTER">Registrieren</H1>
        
        <form action="/" method="post">
            <input type="hidden" name="action" value="RegisterUser">
            Name:
            <br>
            <input name="user_name" type="text" size="50" maxlength="50" value="<%=initializeUserName%>">
            <br>
            <br>
            Passwort:
            <br>
            <input name="password1" type="password" size="12" maxlength="12">
            <br>
            Passwort wiederholen:
            <br>
            <input name="password2" type="password" size="12" maxlength="12">
            <input type="submit" value=" Absenden ">
            <br>
            <br>
            Hinweis: Das Passwort wird nicht im Klartext in der Datenbank gespeichert.
        </form>
        
        <br>
        <br>
        <a href="/">zur&uuml;ck zur Hauptseite</a>
        <br>
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