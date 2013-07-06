package com.kupkik.ui.html.requesthandlers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kupkik.applicationcore.ApplicationCoreFacade;
import com.kupkik.applicationcore.ApplicationCoreFacade.CreateSeasonAnswer;
import com.kupkik.applicationcore.ApplicationCoreFacade.CreateTournamentAnswer;
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
            pRequest.setAttribute("errorMessage", "Nur eingeloggte Nutzer k?nnen Turniere anlegen!");
            return "NewSeasonView";
        }

        // try to create tournament

        CreateSeasonAnswer createSeasonAnswer = pApplicationCoreFacade.createSeason(seasonName, currentUser.getName(),
                currentUser.getPasswordMd5());

        if( createSeasonAnswer == CreateSeasonAnswer.NAME_INVALID )
        {
            pRequest.setAttribute("errorMessage", "Der Name der Saison muss zwischen " + 
        ApplicationCoreFacade.MIN_SEASON_NAME_SIZE
                    + " und " + ApplicationCoreFacade.MAX_SEASON_NAME_SIZE
                    + " Zeichen lang sein und darf nur Buchstaben, Zahlen, Leerzeichen " + " oder Unterstriche enthalten!");
            return "NewSeasonView";
        }
        if( createSeasonAnswer == CreateSeasonAnswer.SEASON_ALREADY_EXISTS )
        {
            pRequest.setAttribute("errorMessage", "Es existiert bereits ein Saison mit diesem Namen!");
            return "NewSeasonView";
        }
        if( createSeasonAnswer == CreateSeasonAnswer.USER_CREDENTIALS_INVALID )
        {
            pRequest.setAttribute("errorMessage", "Sie sind nicht dazu berechtigt eine Saison anzulegen.");
            return "NewSeasonView";
        }

        // everything is OK

        return "MainView";
    }
}
