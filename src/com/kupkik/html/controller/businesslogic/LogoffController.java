package com.kupkik.html.controller.businesslogic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kupkik.html.controller.ApplicationLogic;
import com.kupkik.html.controller.IBusinessLogicController;
import com.kupkik.model.UserWithPassword;
import com.kupkik.persistence.IPersistenceFacade;

public class LogoffController
        implements IBusinessLogicController
{

    @Override
    public String performActionAndGetNextView( final HttpServletRequest pRequest, final HttpSession pSession,
            final IPersistenceFacade pPersistenceFacade )
    {
        UserWithPassword guestUser = ApplicationLogic.GUEST_USER;
        pSession.setAttribute("currentUser", guestUser);
        pRequest.setAttribute("currentUser", guestUser);

        return "MainView";
    }

}
