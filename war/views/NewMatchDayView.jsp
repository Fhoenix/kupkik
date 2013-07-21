<%@ page import="com.google.appengine.api.datastore.KeyFactory"%>
<%@ page import="com.kupkik.model.*" %>
<%@ page import="com.kupkik.ui.html.view.*" %>
<%@ page import="java.util.List" %>
<% 
	ViewHelper viewHelper = (ViewHelper)request.getAttribute("viewHelper");  
	List<Season> seasons = (List<Season>)request.getAttribute("seasons"); 
%> 

<%= viewHelper.createHtmlBegin("Create MatchDay")  %> 

      
      
      
   <div class="row-fluid">
		<div class="span6">
			<h1>New MatchDay</h1>
 			<form action="/" method="post">
 			 <input type="hidden" name="action" value="CreateMatchDay">
				<div class="row-fluid">
					<div class="span12">&nbsp;</div>
				</div>
	
				<div class="row-fluid">
					<div class="span4"><%=viewHelper.createLabel("Select a Season", "label",
					"seasonKey")%>	</div>
					<div class="span8">  
						<select id="seasonKey" name="seasonKey">
            				<% for (Season item : seasons){
            					out.println("	 <option value=\"" +KeyFactory.keyToString(item.getKey()) + "\">"+ item.getName()+"</option>");
       						 } %>	
						</select>
					</div>
				</div>
				
				<div class="row-fluid">
					<div class="span12"> <input class="fillLayout"  placeholder="MatchDay Name" name="name" type="text"></div>
				</div>
				
				<div class="row-fluid">
					<div class="span12"><input type="submit" value="Absenden"></div>
				</div>
				  
			 </form>
		</div>	
		<div class="span6">
			<h1>Whats a MatchDay</h1>
			
					Beschreibung Turnier Beschreibung Turnier
					Beschreibung Turnier Beschreibung Turnier Beschreibung Turnier
					Beschreibung Turnier Beschreibung Turnier
					Beschreibung Turnier Beschreibung Turnier Beschreibung Turnier
					Beschreibung Turnier Beschreibung Turnier
					Beschreibung Turnier Beschreibung Turnier Beschreibung Turnier
					Beschreibung Turnier Beschreibung Turnier
					Beschreibung Turnier Beschreibung Turnier Beschreibung Turnier
					Beschreibung Turnier Beschreibung Turnier
					Beschreibung Turnier Beschreibung Turnier Beschreibung Turnier
					Beschreibung Turnier Beschreibung Turnier
					Beschreibung Turnier Beschreibung Turnier Beschreibung Turnier
					Beschreibung Turnier Beschreibung Turnier
					Beschreibung Turnier Beschreibung Turnier Beschreibung Turnier
					v
					Beschreibung Turnier Beschreibung Turnier
					Beschreibung Turnier Beschreibung Turnier Beschreibung Turnier
	
			


		</div>
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