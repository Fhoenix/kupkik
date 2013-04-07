package com.kupkik.html.controller.businesslogic;

import javax.servlet.http.HttpServletRequest;

import com.kupkik.html.controller.IBusinessLogicController;

public class TestController
        implements IBusinessLogicController
{

    @Override
    public String performActionAndGetNextView( final HttpServletRequest pRequest )
    {
        return "TestView";
    }

}
