package com.kupkik.ui.html.requesthandlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kupkik.applicationcore.ApplicationCoreFacade;
import com.kupkik.messages.MessageError;
import com.kupkik.messages.MessageHandlerEnum;
import com.kupkik.model.Season;
import com.kupkik.model.UserWithPassword;
import com.kupkik.ui.html.IHtmlRequestHandler;
import com.kupkik.ui.html.comperators.ComparatorSeason;


public class ShowEditSeasonViewHandler implements IHtmlRequestHandler
{

	@Override
	public String performActionAndGetNextView( final HttpServletRequest pRequest, final HttpSession pSession,
			final ApplicationCoreFacade pApplicationCoreFacade )
	{


		UserWithPassword currentUser = (UserWithPassword) pRequest.getSession().getAttribute("currentUser");


		List<Season> mySeasons = pApplicationCoreFacade.getAllCreatedSeasonsForUser(currentUser.getKey());
		Collections.sort(mySeasons, new ComparatorSeason());

		if (mySeasons != null){
			pRequest.setAttribute("seasons", mySeasons);
		}else{
			pRequest.setAttribute("seasons", new ArrayList<Season>());
			pRequest.setAttribute(MessageHandlerEnum.ERROR.toString(), MessageError.SEASON_NO_SEASONS_AVAILABLE);
		}
		return null;
	}



}
