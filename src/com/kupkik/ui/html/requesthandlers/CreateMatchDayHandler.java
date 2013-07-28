package com.kupkik.ui.html.requesthandlers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import com.kupkik.applicationcore.ApplicationCoreFacade;
import com.kupkik.applicationcore.ApplicationCoreFacade.CreateMatchDayAnswer;
import com.kupkik.messages.HandlerMessagesEnum;
import com.kupkik.messages.MessageError;
import com.kupkik.messages.MessageSuccess;
import com.kupkik.model.UserWithPassword;
import com.kupkik.ui.html.HtmlRequestProcessor;
import com.kupkik.ui.html.IHtmlRequestHandler;

public class CreateMatchDayHandler implements IHtmlRequestHandler
{

    @Override
    public String performActionAndGetNextView( final HttpServletRequest pRequest, final HttpSession pSession,
            final ApplicationCoreFacade pApplicationCoreFacade )
    {
 
    	  UserWithPassword currentUser = (UserWithPassword) pRequest.getSession().getAttribute("currentUser");
          if( currentUser.getName().equals(HtmlRequestProcessor.GUEST_USER.getName()) )
          {
              pRequest.setAttribute(HandlerMessagesEnum.ERROR.toString(), MessageError.MATCHDAY_NO_SUFFICIENT_RIGHTS_TO_CREATE_SEASON);
              return "NewSeasonView";
          }
          
    	// the chosen MatchDay-name
        String matchDayName = pRequest.getParameter("name");
        Key seasonKey = KeyFactory.stringToKey(pRequest.getParameter("seasonKey"));

 
        // try to create MatchDay

        CreateMatchDayAnswer createMatchDayAnswer = pApplicationCoreFacade.createMatchDay(matchDayName, seasonKey);

        if( createMatchDayAnswer == CreateMatchDayAnswer.MATCHDAY_NAME_INVALID )
        {
            pRequest.setAttribute(HandlerMessagesEnum.ERROR.toString(), MessageError.MATCHDAY_NAME_INVALID );
            return "NewMatchDayView";
        }
        if( createMatchDayAnswer == CreateMatchDayAnswer.MATCHDAY_ALREADY_EXISTS )
        {
            pRequest.setAttribute(HandlerMessagesEnum.ERROR.toString(), MessageError.MATCHDAY_WITH_THAT_NAME_EXISTS);
            return "NewMatchDayView";
        }
        if( createMatchDayAnswer == CreateMatchDayAnswer.MATCHDAY_USER_CREDENTIALS_INVALID )
        {
            pRequest.setAttribute(HandlerMessagesEnum.ERROR.toString(), MessageError.MATCHDAY_NO_SUFFICIENT_RIGHTS_TO_CREATE_SEASON);
            return "NewMatchDayView";
        }
        if( createMatchDayAnswer == CreateMatchDayAnswer.MATCHDAY_SEASON_DOES_NOT_EXIST )
        {
            pRequest.setAttribute(HandlerMessagesEnum.ERROR.toString(), MessageError.MATCHDAY_SEASON_DOES_NOT_EXIST);
            return "NewMatchDayView";
        }else{
        	 pRequest.setAttribute(HandlerMessagesEnum.SUCCESS.toString(), MessageSuccess.MATCHDAY_SUCCESSFULLY_ADDED);
             return "NewMatchDayView";
        }


        // everything is OK

        //return "MainView";
    }
}
