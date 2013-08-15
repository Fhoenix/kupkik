package com.kupkik.ui.html.requesthandlers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.kupkik.applicationcore.ApplicationCoreFacade;
import com.kupkik.applicationcore.ApplicationCoreFacade.CreateGameAnswer;

import com.kupkik.messages.HandlerMessagesEnum;
import com.kupkik.messages.MessageError;
import com.kupkik.messages.MessageSuccess;
import com.kupkik.model.UserWithPassword;
import com.kupkik.persistence.EntityNameStore;
import com.kupkik.ui.html.HtmlRequestProcessor;
import com.kupkik.ui.html.IHtmlRequestHandler;


public class CreateKickerGameHandler  implements IHtmlRequestHandler{

	@Override
	public String performActionAndGetNextView(HttpServletRequest pRequest,
			HttpSession pSession, ApplicationCoreFacade pApplicationCoreFacade) {


		UserWithPassword currentUser = (UserWithPassword) pSession.getAttribute("currentUser");

		if( currentUser.getName().equals(HtmlRequestProcessor.GUEST_USER.getName()) )
		{
			pRequest.setAttribute(HandlerMessagesEnum.ERROR.toString(), MessageError.BADMINTON_DOUBLE_NOT_LOGGED_IN );
			return "NewKickerGameView";
		}

		 Key team11 = KeyFactory.stringToKey(pRequest.getParameter("teamOne-One"));
		 Key team12 = KeyFactory.stringToKey(pRequest.getParameter("teamOne-Two"));
		 Key team21 = KeyFactory.stringToKey(pRequest.getParameter("teamTwo-One"));
		 Key team22 = KeyFactory.stringToKey(pRequest.getParameter("teamTwo-Two"));
		 
		List<Key> team1 = new ArrayList<Key>();
		team1.add(team11);
		team1.add(team12);
		
		
		List<Key> team2 = new ArrayList<Key>();
		team2.add(team21);
		team2.add(team22);
		
		
		
		
		int resultOne = Integer.parseInt(pRequest.getParameter("resultOne"));
		int resultTwo = Integer.parseInt(pRequest.getParameter("resultTwo"));
		String pMatchDayKey = pRequest.getParameter("matchDayKey");
		

		Date date = new Date();


		CreateGameAnswer createGameAnswer = pApplicationCoreFacade.createKickerGame(KeyFactory.stringToKey(pMatchDayKey), 
				team1, 
				team2, 
				resultOne,
				resultTwo,
				date,
				EntityNameStore.KICKER_GAME.toString());

        if( createGameAnswer == CreateGameAnswer.GAME_NOK )
        {
            pRequest.setAttribute(HandlerMessagesEnum.ERROR.toString(), MessageError.BADMINTON_DOUBLE_ERROR_WHILE_CREATING_GAME);
            return "NewKickerGameView";
        }else{
        	pRequest.setAttribute(HandlerMessagesEnum.SUCCESS.toString(), MessageSuccess.BADMINTON_DOUBLE_SUCCESSFULLY_ADDED);
        }


		return "NewKickerGameView";
	}

}
