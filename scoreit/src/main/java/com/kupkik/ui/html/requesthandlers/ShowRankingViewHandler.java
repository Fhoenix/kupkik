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
import com.kupkik.ui.html.comperators.ComparatorUser;


public class ShowRankingViewHandler implements IHtmlRequestHandler
{

	@Override
	public String performActionAndGetNextView( final HttpServletRequest pRequest, final HttpSession pSession,
			final ApplicationCoreFacade pApplicationCoreFacade )
	{


		UserWithPassword currentUser = (UserWithPassword) pRequest.getSession().getAttribute("currentUser");
		
		
		List<UserWithPassword> users = pApplicationCoreFacade.getAllUsers();
		Collections.sort(users, new ComparatorUser());
		pRequest.setAttribute("users", users);


		List<Season> seasons = pApplicationCoreFacade.getAllSeasons();
		Collections.sort(seasons, new ComparatorSeason());

		if (seasons != null){
			pRequest.setAttribute("seasons", seasons);
		}else{
			pRequest.setAttribute("seasons", new ArrayList<Season>());
			pRequest.setAttribute(MessageHandlerEnum.ERROR.toString(), MessageError.SEASON_NO_SEASONS_AVAILABLE);
		}
		return null;
	}



}
