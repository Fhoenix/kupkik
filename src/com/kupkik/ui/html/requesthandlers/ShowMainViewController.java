package com.kupkik.ui.html.requesthandlers;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kupkik.applicationcore.ApplicationCoreFacade;
import com.kupkik.model.User;
import com.kupkik.ui.html.IHtmlRequestHandler;

public class ShowMainViewController
        implements IHtmlRequestHandler
{

    @Override
    public String performActionAndGetNextView( final HttpServletRequest pRequest, final HttpSession pSession,
            final ApplicationCoreFacade pApplicationCoreFacade )
    {
        List<User> users = pApplicationCoreFacade.getAllUsers();
        Collections.sort(users, new UserComparator());
        
        pRequest.setAttribute("users", users);

        return "MainView";
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
