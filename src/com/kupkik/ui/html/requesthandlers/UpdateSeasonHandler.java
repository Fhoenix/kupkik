package com.kupkik.ui.html.requesthandlers;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mortbay.util.StringUtil;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.kupkik.applicationcore.ApplicationCoreFacade;
import com.kupkik.applicationcore.answers.CreateGameAnswer;
import com.kupkik.applicationcore.answers.CreateSeasonAnswer;
import com.kupkik.messages.MessageHandlerEnum;
import com.kupkik.messages.MessageError;
import com.kupkik.messages.MessageSuccess;
import com.kupkik.model.DisplaySkillGraph;
import com.kupkik.model.Season;
import com.kupkik.model.UserWithPassword;
import com.kupkik.model.ranking.TypedWinLooseRanking;
import com.kupkik.model.ranking.WinLooseRanking;
import com.kupkik.persistence.EntityNameStore;
import com.kupkik.persistence.GameTypStore;
import com.kupkik.persistence.common.PFCommonGetter;
import com.kupkik.ui.html.HtmlRequestProcessor;
import com.kupkik.ui.html.IHtmlRequestHandler;
import com.kupkik.ui.html.comperators.ComparatorUser;

public class UpdateSeasonHandler implements IHtmlRequestHandler
{

    @Override
    public String performActionAndGetNextView( final HttpServletRequest pRequest, final HttpSession pSession,
            final ApplicationCoreFacade pApplicationCoreFacade )
    {
    
    	UserWithPassword currentUser = (UserWithPassword) pRequest.getSession().getAttribute("currentUser");
    	
	     String[] users = pRequest.getParameterValues("usersToEdit");
	     if(users == null){
	        	users = new String[0];
	      }
    	// the chosen season-name
        Key seasonKey  = KeyFactory.stringToKey(pRequest.getParameter("seasonKey"));
        List<String> usersToEditSeason =  UtilHelper.convertStringArrayToListArray(users);
        usersToEditSeason.add(KeyFactory.keyToString(currentUser.getKey()));
        
    	  CreateSeasonAnswer createSeasonAnswer = pApplicationCoreFacade.updateSeason(usersToEditSeason,seasonKey);
    	  if(createSeasonAnswer == CreateSeasonAnswer.SEASON_OK){
    		  pRequest.setAttribute(MessageHandlerEnum.SUCCESS.toString(), MessageSuccess.SEASON_SUCCESSFULLY_UPDATED);
    	  }else{
    		  pRequest.setAttribute(MessageHandlerEnum.ERROR.toString(), MessageError.SEASON_UPDATE_NOT_SUCCESSFUL);
    	  }
        return "EditSeasonView";
    }
}
