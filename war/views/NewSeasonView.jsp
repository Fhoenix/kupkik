<%@ page import="com.kupkik.model.*" %>
<%@ page import="com.kupkik.ui.html.view.*" %>


<% 
	ViewHelper viewHelper = (ViewHelper)request.getAttribute("viewHelper");  
   
%> 



<%= viewHelper.createHtmlBegin("Saison Erstellung")  %> 
	<div class="row-fluid">
	<form action="/" method="post">
	<input type="hidden" name="action" value="CreateSeason">
		<div class="span12">
        	<H1 ALIGN="CENTER">CREATE NEW SEASON</H1>
        	<div class="row-fluid">
				<div class="span12">&nbsp;</div>
			</div>
			<div class="row-fluid">
				<div class="span2">SEASON NAME</div>
				<div class="span10"><input name="name" type="text" size="50" style="width: 80%;" maxlength="50"></div>
			</div>
			<div class="row-fluid">
				<div class="span2"><input type="submit" style="width: 100%;" value=" Absenden "></div>
				<div class="span5">Create a new season (e.g. Badminton 2013)</div>
				<div class="span5"><a href="/">zur&uuml;ck zur Hauptseite</a></div>
			</div>
		
		</div>
	</form>
	</div>

						
        
        

            
    
        
        <br>
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