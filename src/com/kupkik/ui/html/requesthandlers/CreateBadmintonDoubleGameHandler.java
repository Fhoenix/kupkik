package com.kupkik.ui.html.requesthandlers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.kupkik.applicationcore.ApplicationCoreFacade;
import com.kupkik.applicationcore.answers.CreateGameAnswer;

import com.kupkik.messages.MessageHandlerEnum;
import com.kupkik.messages.MessageError;
import com.kupkik.messages.MessageSuccess;
import com.kupkik.model.UserWithPassword;
import com.kupkik.persistence.GameTypStore;
import com.kupkik.ui.html.HtmlRequestProcessor;
import com.kupkik.ui.html.IHtmlRequestHandler;


public class CreateBadmintonDoubleGameHandler  implements IHtmlRequestHandler{

	@Override
	public String performActionAndGetNextView(HttpServletRequest pRequest,
			HttpSession pSession, ApplicationCoreFacade pApplicationCoreFacade) {


		UserWithPassword currentUser = (UserWithPassword) pSession.getAttribute("currentUser");

		if( currentUser.getName().equals(HtmlRequestProcessor.GUEST_USER.getName()) )
		{
			pRequest.setAttribute(MessageHandlerEnum.ERROR.toString(), MessageError.BADMINTON_DOUBLE_NOT_LOGGED_IN );
			return "NewBadmintonDoubleGameView";
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
		
		
		
		

		
		
		String resultOne = pRequest.getParameter("resultOne");
		String resultTwo = pRequest.getParameter("resultTwo");
		
		String seasonKey = pRequest.getParameter("seasonKey");
		

		Date date = new Date();


		CreateGameAnswer createGameAnswer = pApplicationCoreFacade.createBadmintonDoubleGame(KeyFactory.stringToKey(seasonKey), 
				team1, 
				team2, 
				resultOne,
				resultTwo,
				date, GameTypStore.BADMINTON_DOUBLE_GAME.toString());

        if( createGameAnswer == CreateGameAnswer.GAME_NOK )
        {
            pRequest.setAttribute(MessageHandlerEnum.ERROR.toString(), MessageError.BADMINTON_DOUBLE_ERROR_WHILE_CREATING_GAME);
            return "NewBadmintonDoubleGameView";
        }else{
        	pRequest.setAttribute(MessageHandlerEnum.SUCCESS.toString(), MessageSuccess.BADMINTON_DOUBLE_SUCCESSFULLY_ADDED);
        }


		return "NewBadmintonDoubleGameView";
	}

}
