package com.kupkik.ui.html.requesthandlers;

import java.util.Date;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.kupkik.applicationcore.ApplicationCoreFacade;
import com.kupkik.applicationcore.ApplicationCoreFacade.CreateBadmintonSingleGameAnswer;
import com.kupkik.applicationcore.ApplicationCoreFacade.CreateGameAnswer;

import com.kupkik.messages.HandlerMessagesEnum;
import com.kupkik.messages.MessageError;
import com.kupkik.messages.MessageSuccess;
import com.kupkik.model.UserWithPassword;
import com.kupkik.ui.html.HtmlRequestProcessor;
import com.kupkik.ui.html.IHtmlRequestHandler;


public class CreateBadmintonSingleGameHandler  implements IHtmlRequestHandler{

	@Override
	public String performActionAndGetNextView(HttpServletRequest pRequest,
			HttpSession pSession, ApplicationCoreFacade pApplicationCoreFacade) {


		UserWithPassword currentUser = (UserWithPassword) pSession.getAttribute("currentUser");

		if( currentUser.getName().equals(HtmlRequestProcessor.GUEST_USER.getName()) )
		{
			pRequest.setAttribute(HandlerMessagesEnum.ERROR.toString(), MessageError.BADMINTON_SINGLE_USER_NOT_LOGGED_IN);
			return "NewBadmintonSingleGameView";
		}

		Key playerOne = KeyFactory.stringToKey(pRequest.getParameter("playerOne"));
		Key playerTwo = KeyFactory.stringToKey(pRequest.getParameter("playerTwo"));

		if (pRequest.getParameter("resultOne").toString().isEmpty() || pRequest.getParameter("resultTwo").toString().isEmpty() ){
			pRequest.setAttribute(HandlerMessagesEnum.ERROR.toString(), MessageError.BADMINTON_RESULT_INVALID);
			return "NewBadmintonSingleGameView";		
		}
		
		int resultOne = Integer.parseInt(pRequest.getParameter("resultOne"));
		int resultTwo = Integer.parseInt(pRequest.getParameter("resultTwo"));
		String pMatchDayKey = pRequest.getParameter("matchDayKey");


		Date date = new Date();


		CreateBadmintonSingleGameAnswer createBadmintonSingleAnswer = pApplicationCoreFacade.createBadmintonSingleGame(KeyFactory.stringToKey(pMatchDayKey), playerOne, 
				playerTwo, 
				resultOne,
				resultTwo,
				date);

		if( createBadmintonSingleAnswer == CreateBadmintonSingleGameAnswer.BADMINTON_SINGLE_MIN_POINTS_NOT_REACHED ){
			pRequest.setAttribute(HandlerMessagesEnum.ERROR.toString(), MessageError.BADMINTON_SINGLE_MIN_POINTS_NOT_REACHED);
			return "NewBadmintonSingleGameView";
		}else if(createBadmintonSingleAnswer == CreateBadmintonSingleGameAnswer.BADMINTON_SINGLE_RESULTS_INVALID ){
			pRequest.setAttribute(HandlerMessagesEnum.ERROR.toString(), MessageError.BADMINTON_RESULT_INVALID);
			return "NewBadmintonSingleGameView";
		}else if(createBadmintonSingleAnswer == CreateBadmintonSingleGameAnswer.BADMINTON_SINGLE_USER_EQUAL_EACH_OTHER ){
			pRequest.setAttribute(HandlerMessagesEnum.ERROR.toString(), MessageError.BADMINTON_SINGLE_USER_EQUALS_EACH_OTHER);
			return "NewBadmintonSingleGameView";
		}else{
			pRequest.setAttribute(HandlerMessagesEnum.SUCCESS.toString(), MessageSuccess.BADMINTON_SINGLE_SUCCESSFULLY_ADDED);
			return "NewBadmintonSingleGameView";
		}


	}

}
