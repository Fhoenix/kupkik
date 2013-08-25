package com.kupkik.ui.html.requesthandlers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kupkik.applicationcore.ApplicationCoreFacade;
import com.kupkik.applicationcore.answers.SaveUserAnswer;
import com.kupkik.messages.MessageHandlerEnum;
import com.kupkik.messages.MessageError;
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
        // Firstname
        String firstname = pRequest.getParameter("firstname");
        // Lastname
        String lastname = pRequest.getParameter("surname");
        
        // the chosen password
        String password1 = pRequest.getParameter("password1");
        // the chosen password, for controlling the password
        String password2 = pRequest.getParameter("password2");
        String errorMessage = null;

        // are the passwords equal?

        if( !password1.equals(password2) )
        {
            errorMessage = MessageError.REGISTER_PASSWORD_NOT_EUQUAL;
        }

        // try to save the user

        if( errorMessage == null )
        {
            SaveUserAnswer saveUserAnswer = pApplicationCoreFacade.saveNewUser(userName, password1, firstname, lastname);
            if( saveUserAnswer == SaveUserAnswer.USER_PASSWORD_INVALID ){
                errorMessage = MessageError.REGISTER_PASSWORD_LENGTH_INVALID;
            }else if( saveUserAnswer == SaveUserAnswer.USER_ALREADY_EXISTS ){
                errorMessage = MessageError.REGISTER_USER_ALREADY_EXISTS + userName;
            }else if( saveUserAnswer == SaveUserAnswer.USER_NAME_TOO_LONG ){
                errorMessage = MessageError.REGISTER_USER_NAME_TOO_LONG;
            }else if( saveUserAnswer == SaveUserAnswer.USER_NAME_TOO_SHORT ){
                errorMessage = MessageError.REGISTER_USER_NAME_TOO_SHORT;
            }else if( saveUserAnswer == SaveUserAnswer.USER_NAME_USES_INVALID_CHARACTERS){
                errorMessage = MessageError.REGISTER_USERNAME_INVALID_CHARACTER + userName;
            }else if( saveUserAnswer == SaveUserAnswer.USER_FIRSTNAME_INVALID ){
            	errorMessage = MessageError.REGISTER_FIRSTNAME_INVALID;
            }else if( saveUserAnswer == SaveUserAnswer.USER_SURNAME_INVALID ){
            	errorMessage =  MessageError.REGISTER_SURNAME_INVALID;
            }
        }

        // has an error occured? => back to registering-site

        if( errorMessage != null )
        {
            pRequest.setAttribute(MessageHandlerEnum.ERROR.toString(), errorMessage);
            return "RegisterView";
        }

        // everything is OK

        // log the user in

        String md5HashForPassword = CredentialsUtils.getMd5HashForText(password1);
       
        UserWithPassword userWithPasswordByName = ApplicationCoreFacade.getUserWithPasswordByName(userName);
        if(userWithPasswordByName != null){
        UserWithPassword currentUser = new UserWithPassword(userWithPasswordByName.getName(), 
        		userWithPasswordByName.getPasswordMd5(),
        		userWithPasswordByName.getKey(),userWithPasswordByName.getFirstname(), userWithPasswordByName.getSurname());
        
        pSession.setAttribute("currentUser", currentUser);
        pRequest.setAttribute("currentUser", currentUser);
        }
        
        // show the main view

        return "MainView";
    }
}
