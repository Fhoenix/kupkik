package com.kupkik.ui.html.requesthandlers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kupkik.applicationcore.ApplicationCoreFacade;
import com.kupkik.applicationcore.ApplicationCoreFacade.CreateTournamentAnswer;
import com.kupkik.model.UserWithPassword;
import com.kupkik.ui.html.HtmlRequestProcessor;
import com.kupkik.ui.html.IHtmlRequestHandler;

public class CreateTournamentHandler
        implements IHtmlRequestHandler
{

    @Override
    public String performActionAndGetNextView( final HttpServletRequest pRequest, final HttpSession pSession,
            final ApplicationCoreFacade pApplicationCoreFacade )
    {
        // the chosen tournament-name
        String tournamentName = pRequest.getParameter("name");

        // is user logged in?

        UserWithPassword currentUser = (UserWithPassword) pSession.getAttribute("currentUser");

        if( currentUser.getName().equals(HtmlRequestProcessor.GUEST_USER.getName()) )
        {
            pRequest.setAttribute("errorMessage", "Nur eingeloggte Nutzer können Turniere anlegen!");
            return "NewTournamentView";
        }

        // try to create tournament

        CreateTournamentAnswer createTournamentAnswer = pApplicationCoreFacade.createTournament(tournamentName, currentUser.getName(),
                currentUser.getPasswordMd5());

        if( createTournamentAnswer == CreateTournamentAnswer.NAME_INVALID )
        {
            pRequest.setAttribute("errorMessage", "Der Name des Turniers muss zwischen " + ApplicationCoreFacade.MIN_TOURNAMENT_NAME_SIZE
                    + " und " + ApplicationCoreFacade.MAX_TOURNAMENT_NAME_SIZE
                    + " Zeichen lang sein und darf nur Buchstaben, Zahlen, Leerzeichen " + " oder Unterstriche enthalten!");
            return "NewTournamentView";
        }
        if( createTournamentAnswer == CreateTournamentAnswer.TOURNAMENT_ALREADY_EXISTS )
        {
            pRequest.setAttribute("errorMessage", "Es existiert bereits ein Turnier mit diesem Namen!");
            return "NewTournamentView";
        }
        if( createTournamentAnswer == CreateTournamentAnswer.USER_CREDENTIALS_INVALID )
        {
            pRequest.setAttribute("errorMessage", "Sie sind nicht dazu berechtigt ein Turnier anzulegen.");
            return "NewTournamentView";
        }

        // everything is OK

        return "MainView";
    }
}
