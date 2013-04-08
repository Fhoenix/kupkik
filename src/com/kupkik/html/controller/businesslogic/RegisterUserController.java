package com.kupkik.html.controller.businesslogic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kupkik.html.controller.IBusinessLogicController;
import com.kupkik.model.User;
import com.kupkik.model.UserWithPassword;
import com.kupkik.persistence.IPersistenceFacade;

public class RegisterUserController
        implements IBusinessLogicController
{

    @Override
    public String performActionAndGetNextView( final HttpServletRequest pRequest, final HttpSession pSession,
            final IPersistenceFacade pPersistenceFacade )
    {
        // the chosen user-name
        String userName = pRequest.getParameter("user_name");
        // the chosen password
        String password1 = pRequest.getParameter("password1");
        // the chosen password, for controlling the password
        String password2 = pRequest.getParameter("password2");
        String errorMessage = null;

        // does the user exist already?

        if( pPersistenceFacade.doesUserAlreadyExist(new User(userName)) )
        {
            errorMessage = "Es existiert bereits ein Benutzer mit dem Namen " + userName + ".";
        }

        // check credentials

        if( errorMessage == null )
        {
            errorMessage = CredentialsUtils.areCredentialsValid(userName, password1, password2);
        }

        // has an error occured? => back to registering-site
        if( errorMessage != null )
        {
            pRequest.setAttribute("errorMessage", errorMessage);
            return "RegisterView";
        }

        // everything is OK
        
        // persist the user

        String md5HashForPassword = CredentialsUtils.getMd5HashForText(password1);
        UserWithPassword currentUser = new UserWithPassword(userName, md5HashForPassword);
        pPersistenceFacade.saveNewUser(currentUser);

        // log the user in

        pSession.setAttribute("currentUser", currentUser);
        pRequest.setAttribute("currentUser", currentUser);

        // show the main view

        return "MainView";
    }
}
