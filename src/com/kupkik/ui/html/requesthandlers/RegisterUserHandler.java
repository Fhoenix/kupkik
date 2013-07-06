package com.kupkik.ui.html.requesthandlers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kupkik.applicationcore.ApplicationCoreFacade;
import com.kupkik.applicationcore.ApplicationCoreFacade.SaveUserAnswer;
import com.kupkik.model.UserWithPassword;
import com.kupkik.ui.html.IHtmlRequestHandler;
import com.kupkik.utils.CredentialsUtils;

public class RegisterUserHandler
        implements IHtmlRequestHandler
{

    @Override
    public String performActionAndGetNextView( final HttpServletRequest pRequest, final HttpSession pSession,
            final ApplicationCoreFacade pApplicationCoreFacade )
    {
        // the chosen user-name
        String userName = pRequest.getParameter("user_name");
        // the chosen password
        String password1 = pRequest.getParameter("password1");
        // the chosen password, for controlling the password
        String password2 = pRequest.getParameter("password2");
        String errorMessage = null;

        // are the passwords equal?

        if( !password1.equals(password2) )
        {
            errorMessage = "Die Passw�rter sind nicht gleich!";
        }

        // try to save the user

        if( errorMessage == null )
        {
            SaveUserAnswer saveUserAnswer = pApplicationCoreFacade.saveNewUser(userName, password1);

            if( saveUserAnswer == SaveUserAnswer.PASSWORD_INVALID )
            {
                errorMessage = "Das Passwort muss mindestens " + ApplicationCoreFacade.MIN_PASSWORD_SIZE + " Zeichen lang sein!";
            }
            else if( saveUserAnswer == SaveUserAnswer.USER_ALREADY_EXISTS )
            {
                errorMessage = "Es existiert bereits ein Benutzer mit dem Namen " + userName;
            }
            else if( saveUserAnswer == SaveUserAnswer.USER_NAME_INVALID )
            {
                errorMessage = "Der Benutzername muss mindestens " + ApplicationCoreFacade.MIN_USER_NAME_SIZE + " und darf maximal "
                        + ApplicationCoreFacade.MAX_USER_NAME_SIZE + " Zeichen lang sein!";
            }
        }

        // has an error occured? => back to registering-site

        if( errorMessage != null )
        {
            pRequest.setAttribute("errorMessage", errorMessage);
            return "RegisterView";
        }

        // everything is OK

        // log the user in

        String md5HashForPassword = CredentialsUtils.getMd5HashForText(password1);
       
        UserWithPassword userWithPasswordByName = pApplicationCoreFacade.getUserWithPasswordByName(userName);
        UserWithPassword currentUser = new UserWithPassword(userWithPasswordByName.getName(), 
        		userWithPasswordByName.getPasswordMd5(),
        		userWithPasswordByName.getKey());
        
        pSession.setAttribute("currentUser", currentUser);
        pRequest.setAttribute("currentUser", currentUser);

        // show the main view

        return "MainView";
    }
}
