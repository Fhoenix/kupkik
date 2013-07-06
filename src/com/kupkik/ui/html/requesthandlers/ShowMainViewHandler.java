package com.kupkik.ui.html.requesthandlers;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kupkik.applicationcore.ApplicationCoreFacade;
import com.kupkik.model.Tournament;
import com.kupkik.model.User;
import com.kupkik.model.UserWithPassword;
import com.kupkik.model.game.BadmintonSingle;
import com.kupkik.ui.html.IHtmlRequestHandler;

public class ShowMainViewHandler
        implements IHtmlRequestHandler
{

    @Override
    public String performActionAndGetNextView( final HttpServletRequest pRequest, final HttpSession pSession,
            final ApplicationCoreFacade pApplicationCoreFacade )
    {
        List<UserWithPassword> users = pApplicationCoreFacade.getAllUsers();
        Collections.sort(users, new UserComparator());
        pRequest.setAttribute("users", users);
        
        List<Tournament> tournaments = pApplicationCoreFacade.getAllTournaments();
        Collections.sort(tournaments, new TournamentComparator());
        pRequest.setAttribute("tournaments", tournaments);

        List<BadmintonSingle> badmintonSingle = pApplicationCoreFacade.getLatestBadmintonSingleGames(5);
       
        pRequest.setAttribute("badmintonSingle", badmintonSingle);
        
        return null;
    }

    private class TournamentComparator
            implements Comparator<Tournament>
    {
        @Override
        public int compare( Tournament pTournament1, Tournament pTournament2 )
        {
            return pTournament1.getName().compareTo(pTournament2.getName());
        }
    }

    private class UserComparator
            implements Comparator<User>
    {
        @Override
        public int compare( User pUser1, User pUser2 )
        {
            return pUser1.getName().compareTo(pUser2.getName());
        }
    }

}
