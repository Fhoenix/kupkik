package com.kupkik.ui.html.requesthandlers;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.KeyFactory;
import com.kupkik.applicationcore.ApplicationCoreFacade;
import com.kupkik.applicationcore.ApplicationCoreFacade.CreateGameAnswer;
import com.kupkik.model.Tournament;
import com.kupkik.model.User;
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
			pRequest.setAttribute("errorMessage", "Nur eingeloggte Nutzer können Spiele anlegen!");
			return "NewTournamentView";
		}

		String playerOne = pRequest.getParameter("playerOne");
		String playerTwo = pRequest.getParameter("playerTwo");
		int resultOne = Integer.parseInt(pRequest.getParameter("resultOne"));
		int resultTwo = Integer.parseInt(pRequest.getParameter("resultTwo"));
		String pTournamentKey = pRequest.getParameter("tournamentKey");
		

		Date date = new Date();


		CreateGameAnswer createTournamentAnswer = pApplicationCoreFacade.createBadmintonSingleGame(KeyFactory.stringToKey(pTournamentKey), 
				playerOne, 
				playerTwo, 
				resultOne,
				resultTwo,
				date);

        if( createTournamentAnswer == CreateGameAnswer.NOK )
        {
            pRequest.setAttribute("errorMessage", "Das Spiel konnte nicht angelegt werden!");
            return "NewTournamentView";
        }


		return "MainView";
	}

}
