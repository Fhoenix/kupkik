package com.kupkik.ui.html.requesthandlers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import com.kupkik.applicationcore.ApplicationCoreFacade;
import com.kupkik.applicationcore.ApplicationCoreFacade.CreateTournamentAnswer;
import com.kupkik.messages.HandlerMessagesEnum;
import com.kupkik.messages.MessageError;
import com.kupkik.model.UserWithPassword;
import com.kupkik.ui.html.HtmlRequestProcessor;
import com.kupkik.ui.html.IHtmlRequestHandler;

public class CreateTournamentHandler implements IHtmlRequestHandler
{

    @Override
    public String performActionAndGetNextView( final HttpServletRequest pRequest, final HttpSession pSession,
            final ApplicationCoreFacade pApplicationCoreFacade )
    {
 
    	  UserWithPassword currentUser = (UserWithPassword) pRequest.getSession().getAttribute("currentUser");
          if( currentUser.getName().equals(HtmlRequestProcessor.GUEST_USER.getName()) )
          {
              pRequest.setAttribute(HandlerMessagesEnum.ERROR.toString(), MessageError.TOURNAMENT_NO_SUFFICIENT_RIGHTS_TO_CREATE_SEASON);
              return "NewSeasonView";
          }
          
    	// the chosen tournament-name
        String tournamentName = pRequest.getParameter("name");
        Key seasonKey = KeyFactory.stringToKey(pRequest.getParameter("seasonKey"));

 
        // try to create tournament

        CreateTournamentAnswer createTournamentAnswer = pApplicationCoreFacade.createTournament(tournamentName, seasonKey);

        if( createTournamentAnswer == CreateTournamentAnswer.NAME_INVALID )
        {
            pRequest.setAttribute(HandlerMessagesEnum.ERROR.toString(), MessageError.TOURNAMENT_NAME_INVALID );
            return "NewTournamentView";
        }
        if( createTournamentAnswer == CreateTournamentAnswer.TOURNAMENT_ALREADY_EXISTS )
        {
            pRequest.setAttribute(HandlerMessagesEnum.ERROR.toString(), MessageError.TOURNAMENT_WITH_THAT_NAME_EXISTS);
            return "NewTournamentView";
        }
        if( createTournamentAnswer == CreateTournamentAnswer.USER_CREDENTIALS_INVALID )
        {
            pRequest.setAttribute(HandlerMessagesEnum.ERROR.toString(), MessageError.TOURNAMENT_NO_SUFFICIENT_RIGHTS_TO_CREATE_SEASON);
            return "NewTournamentView";
        }
        if( createTournamentAnswer == CreateTournamentAnswer.SEASON_DOES_NOT_EXIST )
        {
            pRequest.setAttribute(HandlerMessagesEnum.ERROR.toString(), MessageError.TOURNAMENT_SEASON_DOES_NOT_EXIST);
            return "NewTournamentView";
        }else{
        	 pRequest.setAttribute(HandlerMessagesEnum.SUCCESS.toString(), MessageError.TOURNAMENT_SUCCESSFULLY_ADDED);
             return "NewTournamentView";
        }


        // everything is OK

        //return "MainView";
    }
}
