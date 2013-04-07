package com.kupkik.controller.businesslogic;

import javax.servlet.http.HttpServletRequest;

import com.kupkik.controller.IBusinessLogicController;

public class ShowMainViewController
        implements IBusinessLogicController
{

    @Override
    public String performActionAndGetNextView( final HttpServletRequest pRequest )
    {
        return "MainView";
    }

}
