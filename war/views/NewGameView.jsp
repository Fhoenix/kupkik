<%@ page import="com.kupkik.model.*" %>
<%@ page import="com.kupkik.ui.html.view.*" %>
<%@ page import="java.util.List" %>

<% 
	ViewHelper viewHelper = (ViewHelper)request.getAttribute("viewHelper");  
	List<User> users = (List<User>)request.getAttribute("users"); 
	List<Tournament> tournaments = (List<Tournament>)request.getAttribute("tournaments");  
%> 

<%= viewHelper.createHtmlBegin("Turnier Erstellung")  %> 

        <H1 ALIGN="CENTER">Siel Erstellung</H1>
        
        <form action="/" method="post">
            <input type="hidden" name="action" value="CreateBadmintonSingleGame">
            <br />
                      Turnier
            <select name="tournamentName">
            <% for (Tournament item : tournaments) 
            {
            	
            	out.println("	 <option value=\"" +item.getName() + "\">"+ item.getName()+"</option>");
            	
             } %>	
			</select>
			
			
            <br />
            Spieler 1:
            <select name="playerOne">
            <% for (User item : users) 
            {
            	
            	out.println("	 <option value=\"" +item.getName() + "\">"+ item.getName()+"</option>");
            	
             } %>	
			</select>
            <br />
			Spieler 2:
            <select name="playerTwo">
            <% for (User item : users) 
            {
            
            	out.println("	 <option value=\"" +item.getName() + "\">"+ item.getName()+"</option>");
            	
             } %>	
			</select>

            <br />
			 Ergebnis:
            <input name="resultOne" type="text" size="50" maxlength="50">
            <br />
             Ergebnis2:
            
            
       
            <input name="resultTwo" type="text" size="50" maxlength="50">
            <br>
            
            
            <br />
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