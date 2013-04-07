package com.kupkik.html.controller.businesslogic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kupkik.html.controller.IBusinessLogicController;
import com.kupkik.model.UserWithPassword;

public class RegisterUserController
        implements IBusinessLogicController
{

    @Override
    public String performActionAndGetNextView( final HttpServletRequest pRequest, final HttpSession pSession )
    {

        // the chosen user-name
        String userName = pRequest.getParameter("user_name");
        // the chosen password
        String password1 = pRequest.getParameter("password1");
        // the chosen password, for controlling the password
        String password2 = pRequest.getParameter("password2");

        String errorMessage = CredentialsUtils.areCredentialsValid(userName, password1, password2);

        // an error has occured
        if( errorMessage != null )
        {
            pRequest.setAttribute("errorMessage", errorMessage);
            return "RegisterView";
        }

        // everything is OK

        // log the user in

        String md5HashForPassword = CredentialsUtils.getMd5HashForText(password1);
        UserWithPassword currentUser = new UserWithPassword(userName, md5HashForPassword);
        pSession.setAttribute("currentUser", currentUser);
        pRequest.setAttribute("currentUser", currentUser);

        // show the main view

        return "MainView";
    }
}
