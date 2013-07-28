<%@ page import="com.google.appengine.api.datastore.KeyFactory"%>
<%@ page import="com.kupkik.model.*" %>
<%@ page import="com.kupkik.ui.html.view.*" %>
<%@ page import="java.util.List" %>
<%@ page import="com.kupkik.messages.HandlerMessagesEnum"%>
<%@ page import="com.sun.corba.se.impl.protocol.giopmsgheaders.MessageHandler"%>
<%@ page import="com.google.appengine.repackaged.com.google.common.base.StringUtil"%>
<% 
	ViewHelper viewHelper = (ViewHelper)request.getAttribute("viewHelper");  
	List<Season> seasons = (List<Season>)request.getAttribute("seasons"); 
%> 

<%= viewHelper.createHtmlBegin("Create MatchDay")  %> 

   <%
 	if(!StringUtil.isEmptyOrWhitespace((String)request.getAttribute(HandlerMessagesEnum.ERROR.toString())))
 	{
 %> 
    	<div class="alert alert-danger"><%=viewHelper.convertTextForHtml((String)request.getAttribute(HandlerMessagesEnum.ERROR.toString()))%> </div>
<%
	}else if(!StringUtil.isEmptyOrWhitespace((String)request.getAttribute(HandlerMessagesEnum.SUCCESS.toString()))) {
%>
		<div class="alert alert-success"><%=viewHelper.convertTextForHtml((String)request.getAttribute(HandlerMessagesEnum.SUCCESS.toString()))%> </div>
<% 		
	}
 %>    
      
      
   <div class="row">
		<div class="col-lg-6">
			<h1>New MatchDay</h1>
 			<form action="/" method="post">
 			 <input type="hidden" name="action" value="CreateMatchDay">
				<div class="row">
					<div class="col-lg-12">&nbsp;</div>
				</div>
	
				<div class="row">
					<div class="col-lg-4"><%=viewHelper.createLabel("Select a Season", "label",
					"seasonKey")%>	</div>
					<div class="col-lg-8">  
						<select class="form-control" id="seasonKey" name="seasonKey">
            				<% for (Season item : seasons){
            					out.println("	 <option value=\"" +KeyFactory.keyToString(item.getKey()) + "\">"+ item.getName()+"</option>");
       						 } %>	
						</select>
					</div>
				</div>
				
				<div class="row">
					<div class="col-lg-12"> <input class="form-control"  placeholder="MatchDay Name" name="name" type="text"></div>
				</div>
				
				<div class="row">
					<div class="col-lg-12"><input class="btn btn-default" type="submit" value="Absenden"></div>
				</div>
				  
			 </form>
		</div>	
		<div class="col-lg-6">
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