package com.kupkik.ui.html.requesthandlers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mortbay.util.StringUtil;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.kupkik.applicationcore.ApplicationCoreFacade;
import com.kupkik.applicationcore.ApplicationCoreFacade.CreateSeasonAnswer;
import com.kupkik.applicationcore.ApplicationCoreFacade.CreateMatchDayAnswer;
import com.kupkik.model.DisplaySkillGraph;
import com.kupkik.model.UserWithPassword;
import com.kupkik.persistence.common.PFCommonGetter;
import com.kupkik.ui.html.HtmlRequestProcessor;
import com.kupkik.ui.html.IHtmlRequestHandler;

public class CreateMyProfileHandler implements IHtmlRequestHandler
{

    @Override
    public String performActionAndGetNextView( final HttpServletRequest pRequest, final HttpSession pSession,
            final ApplicationCoreFacade pApplicationCoreFacade )
    {
    	UserWithPassword currentUser = (UserWithPassword) pRequest.getAttribute("currentUser");

        if( currentUser.getName().equals(HtmlRequestProcessor.GUEST_USER.getName()) )
        {
            pRequest.setAttribute("errorMessage", "Nur eingeloggte Nutzer k?nnen Turniere anlegen!");
            return "NewSeasonView";
        }
        
       
        
        Key userKey= currentUser.getKey();
        if(!pRequest.getParameter("userprofile").toString().isEmpty() ){
        	  userKey = KeyFactory.stringToKey(pRequest.getParameter("userprofile"));
        }
    	// the chosen season-name
        Key seasonKey  = KeyFactory.stringToKey(pRequest.getParameter("seasonKey"));
    	
        
        
        pRequest.setAttribute("selectedUser", PFCommonGetter.getUserByKey(userKey));
        
        DisplaySkillGraph allGamesInSeasonBadmintonSingle = ApplicationCoreFacade.getAllBadmintonSingleGamesInSeason(seasonKey,userKey);
        pRequest.setAttribute("displaySkillGraph",allGamesInSeasonBadmintonSingle);

        DisplaySkillGraph allGamesInSeasonBadmintonDouble = ApplicationCoreFacade.getAllBadmintonDoubleGamesInSeason(seasonKey,userKey);
        pRequest.setAttribute("displaySkillGraphDouble",allGamesInSeasonBadmintonDouble);

        
        return "MyProfileView";
    }
}
