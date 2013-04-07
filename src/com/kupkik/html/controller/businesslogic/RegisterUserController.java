package com.kupkik.html.controller.businesslogic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kupkik.html.controller.IBusinessLogicController;

public class RegisterUserController
        implements IBusinessLogicController
{

    @Override
    public String performActionAndGetNextView( HttpServletRequest pRequest, HttpSession pSession )
    {

        // the chosen user-name
        String userName = pRequest.getParameter("user_name");
        // the chosen password
        String password1 = pRequest.getParameter("password1");
        // the chosen password, for controlling the password
        String password2 = pRequest.getParameter("password2");

        String errorMessage = CredentialsValidator.areCredentialsValid(userName, password1, password2);

        if( errorMessage != null )
        {
            pRequest.setAttribute("errorMessage", errorMessage);
            return "RegisterView";
        }

        return "RegisterView";
    }
}
