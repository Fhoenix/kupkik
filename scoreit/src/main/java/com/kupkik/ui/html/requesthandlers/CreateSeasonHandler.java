package com.kupkik.ui.html.requesthandlers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.KeyFactory;
import com.kupkik.applicationcore.ApplicationCoreFacade;
import com.kupkik.applicationcore.answers.CreateSeasonAnswer;
import com.kupkik.messages.MessageError;
import com.kupkik.messages.MessageHandlerEnum;
import com.kupkik.messages.MessageSuccess;
import com.kupkik.model.UserWithPassword;
import com.kupkik.persistence.GameTypStore;
import com.kupkik.ui.html.HtmlRequestProcessor;
import com.kupkik.ui.html.IHtmlRequestHandler;


public class CreateSeasonHandler implements IHtmlRequestHandler
{

    @Override
    public String performActionAndGetNextView( final HttpServletRequest pRequest, final HttpSession pSession,
            final ApplicationCoreFacade pApplicationCoreFacade )
    {
        // the chosen season-name
        String seasonName = pRequest.getParameter("name");
        String gameType = pRequest.getParameter("gameType");
        String[] users = pRequest.getParameterValues("users");
        
    	UserWithPassword currentUser = (UserWithPassword) pRequest.getAttribute("currentUser");
    	
        if (gameType == null){
            pRequest.setAttribute(MessageHandlerEnum.ERROR.toString(), MessageError.SEASON_NEEDS_GAMETYPES);
            return "NewSeasonView";
        }

       
        if( currentUser.getName().equals(HtmlRequestProcessor.GUEST_USER.getName()) )
        {
            pRequest.setAttribute(MessageHandlerEnum.ERROR.toString(), MessageError.SEASON_USER_NOT_LOGGED_IN);
            return "NewSeasonView";
        }
        
        
        if(users == null){
        	users = new String[0];
        }
        List<String> usersToEditSeason =  UtilHelper.convertStringArrayToListArray(users);
        usersToEditSeason.add(KeyFactory.keyToString(currentUser.getKey()));
   
        // try to create MatchDay
        CreateSeasonAnswer createSeasonAnswer = pApplicationCoreFacade.createSeason(seasonName, currentUser.getName(),
        		currentUser.getPasswordMd5(), gameType, usersToEditSeason);

        if( createSeasonAnswer == CreateSeasonAnswer.SEASON_NAME_INVALID ){
            pRequest.setAttribute(MessageHandlerEnum.ERROR.toString(), MessageError.SEASON_NAME_INVALID);
            return "NewSeasonView";
        }
        if( createSeasonAnswer == CreateSeasonAnswer.SEASON_ALREADY_EXISTS ){
            pRequest.setAttribute(MessageHandlerEnum.ERROR.toString(), MessageError.SEASON_NAME_ALREADY_EXISTS);
            return "NewSeasonView";
        }
        if( createSeasonAnswer == CreateSeasonAnswer.SEASON_USER_CREDENTIALS_INVALID ){
            pRequest.setAttribute(MessageHandlerEnum.ERROR.toString(), MessageError.SEASON_NO_SUFFICIENT_RIGHTS_TO_CREATE_SEASON);
            return "NewSeasonView";
        }

        // everything is OK
        pRequest.setAttribute(MessageHandlerEnum.SUCCESS.toString(), MessageSuccess.SEASON_SUCCESSFULLY_ADDED);
        if(gameType.equals(GameTypStore.BADMINTON_DOUBLE_GAME.toString())){
        	return "NewBadmintonDoubleGameView";
        }else if(gameType.equals(GameTypStore.BADMINTON_SINGLE_GAME.toString())){
        	return "NewBadmintonSingleGameView";
        }else if(gameType.equals(GameTypStore.KICKER_GAME.toString())){
        	return "NewKickerGameView";
        }
        	
        	
        return "NewSeasonView";
    }
}
