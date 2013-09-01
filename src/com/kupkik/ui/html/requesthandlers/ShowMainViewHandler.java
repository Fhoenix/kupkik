package com.kupkik.ui.html.requesthandlers;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kupkik.applicationcore.ApplicationCoreFacade;
import com.kupkik.model.MatchDay;
import com.kupkik.model.User;
import com.kupkik.model.UserWithPassword;
import com.kupkik.model.game.Game;
import com.kupkik.ui.html.IHtmlRequestHandler;
import com.kupkik.ui.html.comperators.ComparatorMatchDay;
import com.kupkik.ui.html.comperators.ComparatorUser;

public class ShowMainViewHandler
        implements IHtmlRequestHandler
{

    @Override
    public String performActionAndGetNextView( final HttpServletRequest pRequest, final HttpSession pSession,
            final ApplicationCoreFacade pApplicationCoreFacade )
    {
        List<UserWithPassword> users = pApplicationCoreFacade.getAllUsers();
        Collections.sort(users, new ComparatorUser());
        pRequest.setAttribute("users", users);
        
        List<MatchDay> tournaments = pApplicationCoreFacade.getLatestMatchDays(5);
        Collections.sort(tournaments, new ComparatorMatchDay());
        pRequest.setAttribute("matchDays", tournaments);

        List<Game> games = pApplicationCoreFacade.getLatestGames(5);
       
        pRequest.setAttribute("latestGames", games);
        
        return null;
    }

 

}
