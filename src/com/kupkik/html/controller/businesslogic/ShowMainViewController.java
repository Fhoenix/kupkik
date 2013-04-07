package com.kupkik.html.controller.businesslogic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kupkik.html.controller.IBusinessLogicController;

public class ShowMainViewController
        implements IBusinessLogicController
{

    @Override
    public String performActionAndGetNextView( HttpServletRequest pRequest, HttpSession pSession )
    {
        return "MainView";
    }

}
