package com.kupkik.ui.html.requesthandlers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mortbay.util.StringUtil;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.kupkik.applicationcore.ApplicationCoreFacade;
import com.kupkik.applicationcore.ApplicationCoreFacade.CreateSeasonAnswer;
import com.kupkik.applicationcore.ApplicationCoreFacade.CreateMatchDayAnswer;
import com.kupkik.messages.HandlerMessagesEnum;
import com.kupkik.messages.MessageError;
import com.kupkik.model.DisplaySkillGraph;
import com.kupkik.model.Season;
import com.kupkik.model.UserWithPassword;
import com.kupkik.persistence.EntityNameStore;
import com.kupkik.persistence.common.PFCommonGetter;
import com.kupkik.ui.html.HtmlRequestProcessor;
import com.kupkik.ui.html.IHtmlRequestHandler;

public class CreateStatisticsHandler implements IHtmlRequestHandler
{

    @Override
    public String performActionAndGetNextView( final HttpServletRequest pRequest, final HttpSession pSession,
            final ApplicationCoreFacade pApplicationCoreFacade )
    {
    	UserWithPassword currentUser = (UserWithPassword) pRequest.getAttribute("currentUser");

        if( currentUser.getName().equals(HtmlRequestProcessor.GUEST_USER.getName()) )
        {
            pRequest.setAttribute(HandlerMessagesEnum.ERROR.toString(), MessageError.STATISTICS_NOT_LOGGED_IN);
            return "NewSeasonView";
        }
        
       
        
        Key userKey= currentUser.getKey();
        if(!pRequest.getParameter("userprofile").toString().isEmpty() ){
        	  userKey = KeyFactory.stringToKey(pRequest.getParameter("userprofile"));
        }
    	// the chosen season-name
        Key seasonKey  = KeyFactory.stringToKey(pRequest.getParameter("seasonKey"));
    	
        
        
        pRequest.setAttribute("selectedUser", PFCommonGetter.getUserByKey(userKey));
        
     
        Season seasonsByKey = ApplicationCoreFacade.getSeasonsByKey(seasonKey);
        
        String gameType = seasonsByKey.getGameType();

			if (gameType.equals(EntityNameStore.BADMINTON_SINGLE_GAME)){
				  DisplaySkillGraph allGamesInSeasonBadmintonSingle = ApplicationCoreFacade.getAllBadmintonSingleGamesInSeason(seasonKey,userKey);
			        pRequest.setAttribute("displaySkillGraph",allGamesInSeasonBadmintonSingle);
			}
			if(gameType.equals(EntityNameStore.BADMINTON_DOUBLE_GAME)){
			     DisplaySkillGraph allGamesInSeasonBadmintonDouble = ApplicationCoreFacade.getAllBadmintonDoubleGamesInSeason(seasonKey,userKey);
			        pRequest.setAttribute("displaySkillGraphDouble",allGamesInSeasonBadmintonDouble);
			}
			if(gameType.equals(EntityNameStore.KICKER_GAME)){
			     DisplaySkillGraph allGamesInSeasonKicker = ApplicationCoreFacade.getAllKickerGamesInSeason(seasonKey,userKey);
			        pRequest.setAttribute("displaySkillGraphKicker",allGamesInSeasonKicker);
			}
		
        
        return "StatisticsView";
    }
}
