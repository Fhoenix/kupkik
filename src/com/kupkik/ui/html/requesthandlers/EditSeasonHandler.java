package com.kupkik.ui.html.requesthandlers;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.KeyFactory;
import com.kupkik.applicationcore.ApplicationCoreFacade;
import com.kupkik.model.Season;
import com.kupkik.model.User;
import com.kupkik.model.UserWithPassword;
import com.kupkik.ui.html.IHtmlRequestHandler;
import com.kupkik.ui.html.comperators.ComparatorUser;

public class EditSeasonHandler implements IHtmlRequestHandler
{

    @Override
    public String performActionAndGetNextView( final HttpServletRequest pRequest, final HttpSession pSession,
            final ApplicationCoreFacade pApplicationCoreFacade )
    {
    
    	UserWithPassword currentUser = (UserWithPassword) pRequest.getSession().getAttribute("currentUser");
    	List<UserWithPassword> users = pApplicationCoreFacade.getAllUsers();
		Collections.sort(users, new ComparatorUser());
		
	
    	
    	Iterator<UserWithPassword> iterator = users.iterator();
		   while(iterator.hasNext()){
	        	User user = iterator.next();
	        	if(user.getKey().equals(currentUser.getKey())){
	        		iterator.remove();
	        	}
	        }
	
		pRequest.setAttribute("users", users);
    	
    	String seasonKey = pRequest.getParameter("seasonKey");
    	Season season = pApplicationCoreFacade.getSeasonsByKey(KeyFactory.stringToKey(seasonKey));

    	List<User> editUsers = season.getEditUsers();
    	
    	Iterator<User> iteratorEditUsers = editUsers.iterator();
		   while(iteratorEditUsers.hasNext()){
	        	User user = iteratorEditUsers.next();
	        	if(user.getKey().equals(currentUser.getKey())){
	        		iteratorEditUsers.remove();
	        	}
	        }
		   
    	pRequest.setAttribute("seasonEditUsers",editUsers );
    	pRequest.setAttribute("seasonKey", seasonKey);
    	pRequest.setAttribute("seasonName", season.getName());
    	pRequest.setAttribute("seasonGameType", season.getGameType());
    	
    	
    	
    	
    	
    	
        return "EditSeasonView";
    }
}
