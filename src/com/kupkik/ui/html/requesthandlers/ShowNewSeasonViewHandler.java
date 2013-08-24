package com.kupkik.ui.html.requesthandlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kupkik.applicationcore.ApplicationCoreFacade;
import com.kupkik.model.Season;
import com.kupkik.model.User;
import com.kupkik.model.UserWithPassword;
import com.kupkik.model.game.Game;
import com.kupkik.ui.html.HtmlRequestProcessor;
import com.kupkik.ui.html.IHtmlRequestHandler;
import com.kupkik.ui.html.comperators.ComparatorSeason;
import com.kupkik.ui.html.comperators.ComparatorUser;


public class ShowNewSeasonViewHandler implements IHtmlRequestHandler
{

    @Override
    public String performActionAndGetNextView( final HttpServletRequest pRequest, final HttpSession pSession,
            final ApplicationCoreFacade pApplicationCoreFacade )
    {
    	
        UserWithPassword currentUser = (UserWithPassword) pRequest.getSession().getAttribute("currentUser");
        
        List<Season> seasons = pApplicationCoreFacade.getAllSeasonsForUser(currentUser.getKey());
        Collections.sort(seasons, new ComparatorSeason());
        
        if (seasons != null){
        	pRequest.setAttribute("seasons", seasons);
        }else{
        	pRequest.setAttribute("seasons", new ArrayList<Season>());
        }
        
        List<UserWithPassword> users = pApplicationCoreFacade.getAllUsers();
        
        //Remove current User from the list
        Iterator<UserWithPassword> iterator = users.iterator();
        while(iterator.hasNext()){
        	UserWithPassword user = iterator.next();
        	if(user.getKey().equals(currentUser.getKey())){
        		iterator.remove();
        	}
        }
    
		Collections.sort(users, new ComparatorUser());
		pRequest.setAttribute("users", users);

        
        return null;
    }



}
