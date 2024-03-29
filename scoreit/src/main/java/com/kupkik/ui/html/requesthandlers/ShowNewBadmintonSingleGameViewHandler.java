package com.kupkik.ui.html.requesthandlers;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kupkik.applicationcore.ApplicationCoreFacade;
import com.kupkik.model.Season;
import com.kupkik.model.UserWithPassword;
import com.kupkik.persistence.GameTypStore;
import com.kupkik.ui.html.IHtmlRequestHandler;
import com.kupkik.ui.html.comperators.ComparatorSeason;
import com.kupkik.ui.html.comperators.ComparatorUser;

public class ShowNewBadmintonSingleGameViewHandler implements IHtmlRequestHandler {


    @Override
    public String performActionAndGetNextView( final HttpServletRequest pRequest, final HttpSession pSession,
            final ApplicationCoreFacade pApplicationCoreFacade )
    {
    	

        UserWithPassword currentUser = (UserWithPassword) pRequest.getSession().getAttribute("currentUser");
        List<UserWithPassword> users = pApplicationCoreFacade.getAllUsers();
        Collections.sort(users, new ComparatorUser());
        pRequest.setAttribute("users", users);
        
        List<Season> season = pApplicationCoreFacade.getAllSeasonsForUserAndGameType(currentUser.getKey(),GameTypStore.BADMINTON_SINGLE_GAME.toString());
        Collections.sort(season, new ComparatorSeason());
        pRequest.setAttribute("seasons", season);

        return null;
    }





}
