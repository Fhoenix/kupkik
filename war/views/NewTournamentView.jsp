<%@page import="com.google.appengine.api.datastore.KeyFactory"%>
<%@ page import="com.kupkik.model.*" %>
<%@ page import="com.kupkik.ui.html.view.*" %>
<%@ page import="java.util.List" %>
<% 
	ViewHelper viewHelper = (ViewHelper)request.getAttribute("viewHelper");  
	List<Season> seasons = (List<Season>)request.getAttribute("seasons"); 
%> 

<%=

viewHelper.createHtmlBegin("Create Tournament")  %> 

        <H1 ALIGN="CENTER">Turnier Erstellung</H1>
        
        <form action="/" method="post">
            <input type="hidden" name="action" value="CreateTournament">
            <select name="seasonKey">
            <% for (Season item : seasons) 
            {
            	
            	out.println("	 <option value=\"" +KeyFactory.keyToString(item.getKey()) + "\">"+ item.getName()+"</option>");
            	
             } %>	
			</select>
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