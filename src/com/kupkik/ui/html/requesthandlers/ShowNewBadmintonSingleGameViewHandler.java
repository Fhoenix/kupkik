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
import com.kupkik.ui.html.HtmlRequestProcessor;
import com.kupkik.ui.html.IHtmlRequestHandler;

public class ShowNewBadmintonSingleGameViewHandler implements IHtmlRequestHandler {


    @Override
    public String performActionAndGetNextView( final HttpServletRequest pRequest, final HttpSession pSession,
            final ApplicationCoreFacade pApplicationCoreFacade )
    {
    	

        UserWithPassword currentUser = (UserWithPassword) pRequest.getSession().getAttribute("currentUser");
        List<UserWithPassword> users = pApplicationCoreFacade.getAllUsers();
        Collections.sort(users, new UserComparator());
        pRequest.setAttribute("users", users);
        
        List<MatchDay> matchDays = pApplicationCoreFacade.getAllMatchDaysOfUser(currentUser.getName());
        Collections.sort(matchDays, new MatchDayComparator());
        pRequest.setAttribute("matchDays", matchDays);

        return null;
    }

    private class MatchDayComparator
            implements Comparator<MatchDay>
    {
        @Override
        public int compare( MatchDay matchDay1, MatchDay matchDay2 )
        {
            return matchDay1.getName().compareTo(matchDay2.getName());
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
