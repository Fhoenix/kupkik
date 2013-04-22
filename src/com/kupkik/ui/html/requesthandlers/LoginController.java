package com.kupkik.ui.html.requesthandlers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kupkik.applicationcore.ApplicationCoreFacade;
import com.kupkik.model.UserWithPassword;
import com.kupkik.ui.html.IHtmlRequestHandler;
import com.kupkik.utils.CredentialsUtils;

public class LoginController
        implements IHtmlRequestHandler
{

    @Override
    public String performActionAndGetNextView( HttpServletRequest pRequest, HttpSession pSession,
            ApplicationCoreFacade pApplicationCoreFacade )
    {
        // the chosen user-name
        String userName = pRequest.getParameter("user_name");
        // the chosen password
        String password = pRequest.getParameter("password");
        String md5HashForPassword = CredentialsUtils.getMd5HashForText(password);

        // does the user exist?

        boolean doesUserExist = pApplicationCoreFacade.doesUserExistWithNameAndMd5Password(userName, md5HashForPassword);

        if( !doesUserExist )
        {
            // user does not exist

            pRequest.setAttribute("errorMessage", "Es existiert kein Nutzer mit diesem Namen und Passwort.");
            return "LoginView";
        }

        // everything is OK

        // log the user in

        UserWithPassword currentUser = new UserWithPassword(userName, md5HashForPassword);
        pSession.setAttribute("currentUser", currentUser);
        pRequest.setAttribute("currentUser", currentUser);

        // show the main view

        return "MainView";
    }

}
