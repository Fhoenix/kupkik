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
import com.kupkik.ui.html.comperators.ComparatorMatchDay;
import com.kupkik.ui.html.comperators.ComparatorUser;

public class ShowNewBadmintonDoubleGameViewHandler implements IHtmlRequestHandler {


    @Override
    public String performActionAndGetNextView( final HttpServletRequest pRequest, final HttpSession pSession,
            final ApplicationCoreFacade pApplicationCoreFacade )
    {

        UserWithPassword currentUser = (UserWithPassword) pRequest.getSession().getAttribute("currentUser");
        List<UserWithPassword> users = pApplicationCoreFacade.getAllUsers();
        Collections.sort(users, new ComparatorUser());
        pRequest.setAttribute("users", users);
        
        List<MatchDay> matchDay = pApplicationCoreFacade.getAllMatchDaysOfUser(currentUser.getKey());
        Collections.sort(matchDay, new ComparatorMatchDay());
        pRequest.setAttribute("matchDays", matchDay);

        return null;
    }



}
