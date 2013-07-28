package com.kupkik.ui.html.requesthandlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kupkik.applicationcore.ApplicationCoreFacade;
import com.kupkik.model.Season;
import com.kupkik.model.MatchDay;
import com.kupkik.model.User;
import com.kupkik.model.UserWithPassword;
import com.kupkik.model.game.Game;
import com.kupkik.ui.html.HtmlRequestProcessor;
import com.kupkik.ui.html.IHtmlRequestHandler;

public class ShowNewMatchDayViewHandler implements IHtmlRequestHandler
{

    
    private class SeasonsComparator implements Comparator<Season>{

		@Override
		public int compare(Season season1, Season season2) {
			
			return season1.getName().compareTo(season2.getName());
		}
    	
    }

	@Override
	public String performActionAndGetNextView(HttpServletRequest pRequest,
			HttpSession pSession, ApplicationCoreFacade pApplicationCoreFacade) {
    	
		UserWithPassword currentUser = (UserWithPassword) pRequest.getSession().getAttribute("currentUser");
        List<Season> seasons = pApplicationCoreFacade.getAllSeasonsForUser(currentUser);
        Collections.sort(seasons, new SeasonsComparator());
        
        if (seasons != null){
        	pRequest.setAttribute("seasons", seasons);
        }else{
        	pRequest.setAttribute("seasons", new ArrayList<Season>());
        }
        
        

        
        return null;
	}

}
