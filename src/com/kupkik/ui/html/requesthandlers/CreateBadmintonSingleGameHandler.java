package com.kupkik.ui.html.requesthandlers;

import java.util.Date;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.kupkik.applicationcore.ApplicationCoreFacade;
import com.kupkik.applicationcore.ApplicationCoreFacade.CreateGameAnswer;

import com.kupkik.messages.HandlerMessagesEnum;
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
			pRequest.setAttribute(HandlerMessagesEnum.ERROR.toString(), "Nur eingeloggte Nutzer können Spiele anlegen!");
			return "NewBadmintonSingleGameView";
		}

	

		
		
		 Key playerOne = KeyFactory.stringToKey(pRequest.getParameter("playerOne"));
		 Key playerTwo = KeyFactory.stringToKey(pRequest.getParameter("playerTwo"));
		
		int resultOne = Integer.parseInt(pRequest.getParameter("resultOne"));
		int resultTwo = Integer.parseInt(pRequest.getParameter("resultTwo"));
		String pMatchDayKey = pRequest.getParameter("matchDayKey");
		

		Date date = new Date();


		CreateGameAnswer createGameAnswer = pApplicationCoreFacade.createBadmintonSingleGame(KeyFactory.stringToKey(pMatchDayKey), 
				playerOne, 
				playerTwo, 
				resultOne,
				resultTwo,
				date);

        if( createGameAnswer == CreateGameAnswer.NOK )
        {
            pRequest.setAttribute(HandlerMessagesEnum.ERROR.toString(), "Das Spiel konnte nicht angelegt werden!");
            return "NewBadmintonSingleGameView";
        }else{
        	pRequest.setAttribute(HandlerMessagesEnum.SUCCESS.toString(), "Das Spiel wurde angelegt!");
        }


		return "NewBadmintonSingleGameView";
	}

}
