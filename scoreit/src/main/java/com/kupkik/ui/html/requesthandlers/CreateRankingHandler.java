package com.kupkik.ui.html.requesthandlers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.kupkik.applicationcore.ApplicationCoreFacade;
import com.kupkik.messages.MessageError;
import com.kupkik.messages.MessageHandlerEnum;
import com.kupkik.model.UserWithPassword;
import com.kupkik.model.ranking.WinLooseRanking;
import com.kupkik.ui.html.HtmlRequestProcessor;
import com.kupkik.ui.html.IHtmlRequestHandler;

public class CreateRankingHandler implements IHtmlRequestHandler
{

    @Override
    public String performActionAndGetNextView( final HttpServletRequest pRequest, final HttpSession pSession,
            final ApplicationCoreFacade pApplicationCoreFacade )
    {
    	UserWithPassword currentUser = (UserWithPassword) pRequest.getAttribute("currentUser");

        if( currentUser.getName().equals(HtmlRequestProcessor.GUEST_USER.getName()) )
        {
            pRequest.setAttribute(MessageHandlerEnum.ERROR.toString(), MessageError.STATISTICS_NOT_LOGGED_IN);
            return "NewSeasonView";
        }
        
       
    	// the chosen season-name
        Key seasonKey  = KeyFactory.stringToKey(pRequest.getParameter("seasonKey"));
        
       WinLooseRanking winLooseRanking = ApplicationCoreFacade.getWinLoosRanking(seasonKey);
 
       pRequest.setAttribute("winLooseRanking",winLooseRanking);
       
       
        
        return "RankingView";
    }
}
