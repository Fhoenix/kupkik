package com.kupkik.html.controller.businesslogic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kupkik.applicationcore.ApplicationCoreFacade;
import com.kupkik.html.controller.IBusinessLogicController;

public class ShowMainViewController
        implements IBusinessLogicController
{

    @Override
    public String performActionAndGetNextView( final HttpServletRequest pRequest, final HttpSession pSession,
            final ApplicationCoreFacade pApplicationCoreFacade )
    {
        return "MainView";
    }

}
