<%@ page import="com.kupkik.model.*" %>
<%@ page import="com.kupkik.ui.html.view.*" %>


<% 
	ViewHelper viewHelper = (ViewHelper)request.getAttribute("viewHelper");  
   
%> 



<%= viewHelper.createHtmlBegin("Saison Erstellung")  %> 
	<div class="row-fluid">
		<form action="/" method="post">
			<input type="hidden" name="action" value="CreateSeason">
	        <h1>CREATE NEW SEASON</h1>
	        <div class="row-fluid">
				<div class="span12">&nbsp;</div>
			</div>
			<div class="row-fluid">
				<div class="span2">SEASON NAME</div>
				<div class="span6"><input name="name" type="text" size="50" style="width: 80%;" maxlength="50"></div>
				<div class="span4">Create a new season (e.g. Badminton 2013)</div>
			</div>
			<div class="row-fluid">
				<div class="span2"><input type="submit" style="width: 100%;" value=" Absenden "></div>
				<div class="span5">&nbsp;</div>
				<div class="span5">&nbsp;</div>
			</div>
		</form>
	</div>
<% 
	if(request.getAttribute("errorMessage") != null)
	{
 %> 
    	<b>Fehler: </b><%= viewHelper.convertTextForHtml((String)request.getAttribute("errorMessage")) %>
<% 
	}
 %> 

<%= viewHelper.createHtmlEnd()  %> 