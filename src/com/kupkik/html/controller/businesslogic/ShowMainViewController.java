package com.kupkik.html.controller.businesslogic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kupkik.html.controller.IBusinessLogicController;
import com.kupkik.persistence.IPersistenceFacade;

public class ShowMainViewController
        implements IBusinessLogicController
{

    @Override
    public String performActionAndGetNextView( final HttpServletRequest pRequest, final HttpSession pSession,
            final IPersistenceFacade pPersistenceFacade )
    {
        return "MainView";
    }

}
