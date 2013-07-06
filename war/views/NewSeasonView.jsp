<%@ page import="com.kupkik.model.*" %>
<%@ page import="com.kupkik.ui.html.view.*" %>
<%@ page import="java.util.List" %>

<% 
	ViewHelper viewHelper = (ViewHelper)request.getAttribute("viewHelper");  
    List<Season> seasons = (List<Season>)request.getAttribute("seasons"); 
    
   
%> 



<%= viewHelper.createHtmlBegin("Saison Erstellung")  %> 
						
        <H1 ALIGN="CENTER">Saison Erstellung</H1>
        
        <form action="/" method="post">
        
            <input type="hidden" name="action" value="CreateSeason">
       
			
            Name:
            <br>
            <input name="name" type="text" size="50" maxlength="50">
            <br>
            <input type="submit" value=" Absenden ">
        </form>
        
        <br>
        <br>
        <a href="/">zur&uuml;ck zur Hauptseite</a>
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