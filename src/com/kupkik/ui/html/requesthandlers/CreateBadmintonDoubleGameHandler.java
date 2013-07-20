package com.kupkik.ui.html.requesthandlers;

import java.util.Date;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.KeyFactory;
import com.kupkik.applicationcore.ApplicationCoreFacade;
import com.kupkik.applicationcore.ApplicationCoreFacade.CreateGameAnswer;

import com.kupkik.messages.HandlerMessagesEnum;
import com.kupkik.model.UserWithPassword;
import com.kupkik.ui.html.HtmlRequestProcessor;
import com.kupkik.ui.html.IHtmlRequestHandler;


public class CreateBadmintonDoubleGameHandler  implements IHtmlRequestHandler{

	@Override
	public String performActionAndGetNextView(HttpServletRequest pRequest,
			HttpSession pSession, ApplicationCoreFacade pApplicationCoreFacade) {


		UserWithPassword currentUser = (UserWithPassword) pSession.getAttribute("currentUser");

		if( currentUser.getName().equals(HtmlRequestProcessor.GUEST_USER.getName()) )
		{
			pRequest.setAttribute(HandlerMessagesEnum.ERROR.toString(), "Nur eingeloggte Nutzer können Spiele anlegen!");
			return "NewBadmintonDoubleGameView";
		}

		
		String[] team1 = new String[]{pRequest.getParameter("teamOne-One"), pRequest.getParameter("teamOne-Two")};
		String[] team2 = new String[]{pRequest.getParameter("teamTwo-One"),pRequest.getParameter("teamTwo-Two")};
		int resultOne = Integer.parseInt(pRequest.getParameter("resultOne"));
		int resultTwo = Integer.parseInt(pRequest.getParameter("resultTwo"));
		String pTournamentKey = pRequest.getParameter("tournamentKey");
		

		Date date = new Date();


		CreateGameAnswer createTournamentAnswer = pApplicationCoreFacade.createBadmintonDoubleGame(KeyFactory.stringToKey(pTournamentKey), 
				team1, 
				team2, 
				resultOne,
				resultTwo,
				date);

        if( createTournamentAnswer == CreateGameAnswer.NOK )
        {
            pRequest.setAttribute(HandlerMessagesEnum.ERROR.toString(), "Das Spiel konnte nicht angelegt werden!");
            return "NewBadmintonDoubleGameView";
        }else{
        	pRequest.setAttribute(HandlerMessagesEnum.SUCCESS.toString(), "Das Spiel wurde angelegt!");
        }


		return "NewBadmintonDoubleGameView";
	}

}
