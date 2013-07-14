package com.kupkik.ui.html.requesthandlers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kupkik.applicationcore.ApplicationCoreFacade;
import com.kupkik.applicationcore.ApplicationCoreFacade.CreateSeasonAnswer;
import com.kupkik.applicationcore.ApplicationCoreFacade.CreateTournamentAnswer;
import com.kupkik.messages.HandlerMessages;
import com.kupkik.messages.MessageError;
import com.kupkik.messages.MessageSuccess;
import com.kupkik.model.UserWithPassword;
import com.kupkik.ui.html.HtmlRequestProcessor;
import com.kupkik.ui.html.IHtmlRequestHandler;

public class CreateSeasonHandler
        implements IHtmlRequestHandler
{

    @Override
    public String performActionAndGetNextView( final HttpServletRequest pRequest, final HttpSession pSession,
            final ApplicationCoreFacade pApplicationCoreFacade )
    {
        // the chosen season-name
        String seasonName = pRequest.getParameter("name");

        // is user logged in?

        UserWithPassword currentUser = (UserWithPassword) pSession.getAttribute("currentUser");

        if( currentUser.getName().equals(HtmlRequestProcessor.GUEST_USER.getName()) )
        {
            pRequest.setAttribute(HandlerMessages.ERROR.toString(), MessageError.SEASON_USER_NOT_LOGGED_IN);
            return "NewSeasonView";
        }

        // try to create tournament

        CreateSeasonAnswer createSeasonAnswer = pApplicationCoreFacade.createSeason(seasonName, currentUser.getName(),
                currentUser.getPasswordMd5());

        if( createSeasonAnswer == CreateSeasonAnswer.NAME_INVALID )
        {
            pRequest.setAttribute(HandlerMessages.ERROR.toString(), "Der Name der Saison muss zwischen " + 
        ApplicationCoreFacade.MIN_SEASON_NAME_SIZE
                    + " und " + ApplicationCoreFacade.MAX_SEASON_NAME_SIZE
                    + " Zeichen lang sein und darf nur Buchstaben, Zahlen, Leerzeichen " + " oder Unterstriche enthalten!");
            return "NewSeasonView";
        }
        if( createSeasonAnswer == CreateSeasonAnswer.SEASON_ALREADY_EXISTS )
        {
            pRequest.setAttribute(HandlerMessages.ERROR.toString(), MessageError.SEASON_NAME_ALREADY_EXISTS);
            return "NewSeasonView";
        }
        if( createSeasonAnswer == CreateSeasonAnswer.USER_CREDENTIALS_INVALID )
        {
            pRequest.setAttribute(HandlerMessages.ERROR.toString(), MessageError.SEASON_NO_SUFFICIENT_RIGHTS_TO_CREATE_SEASON);
            return "NewSeasonView";
        }

        // everything is OK
        pRequest.setAttribute(HandlerMessages.SUCCESS.toString(), MessageSuccess.SEASON_SUCCESSFULLY_ADDED);

        return "NewSeasonView";
    }
}
