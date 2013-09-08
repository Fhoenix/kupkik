package com.kupkik.ui.html.requesthandlers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kupkik.applicationcore.ApplicationCoreFacade;
import com.kupkik.applicationcore.answers.SaveUserAnswer;
import com.kupkik.messages.MessageError;
import com.kupkik.messages.MessageHandlerEnum;
import com.kupkik.messages.MessageSuccess;
import com.kupkik.model.UserWithPassword;
import com.kupkik.ui.html.IHtmlRequestHandler;
import com.kupkik.utils.CredentialsUtils;

public class EditUserHandler implements IHtmlRequestHandler
{

	@Override
	public String performActionAndGetNextView( final HttpServletRequest pRequest, final HttpSession pSession,
			final ApplicationCoreFacade pApplicationCoreFacade )
	{

		String errorMessage = null;
		UserWithPassword currentUser = (UserWithPassword) pRequest.getSession().getAttribute("currentUser");

		String password1 = pRequest.getParameter("password1");
		// the chosen password, for controlling the password
		String password2 = pRequest.getParameter("password2");
		String oldPassword = pRequest.getParameter("oldPassword");
		
		String md5HashForText = CredentialsUtils.getMd5HashForText(oldPassword);
		if (!md5HashForText.equals(currentUser.getPasswordMd5())){
			errorMessage = MessageError.REGISTER_PASSWORD_MISMATCH;
		}


		// are the passwords equal?

		if( !password1.equals(password2) ){
			errorMessage = MessageError.REGISTER_PASSWORD_NOT_EUQUAL;
		}
		
		
		if( errorMessage == null ) {
			SaveUserAnswer setNewPassword = pApplicationCoreFacade.setNewPassword(currentUser.getKey(), password1);
			if(setNewPassword.equals(SaveUserAnswer.USER_PASSWORD_INVALID)){
				pRequest.setAttribute(MessageHandlerEnum.ERROR.toString(), "PASSWORD INVALID");
			}
			pRequest.setAttribute(MessageHandlerEnum.SUCCESS.toString(), MessageSuccess.USER_PASSWORD_SUCCESSFULLY_UPDATED);
		}else{
			pRequest.setAttribute(MessageHandlerEnum.ERROR.toString(), errorMessage);
		}

		





		return "EditUserView";
	}
}
