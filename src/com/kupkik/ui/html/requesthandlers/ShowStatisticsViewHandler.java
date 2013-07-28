package com.kupkik.ui.html.requesthandlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import com.kupkik.applicationcore.ApplicationCoreFacade;
import com.kupkik.messages.HandlerMessagesEnum;
import com.kupkik.messages.MessageError;
import com.kupkik.model.Season;

import com.kupkik.model.User;
import com.kupkik.model.UserWithPassword;

import com.kupkik.ui.html.IHtmlRequestHandler;


public class ShowStatisticsViewHandler implements IHtmlRequestHandler
{

	@Override
	public String performActionAndGetNextView( final HttpServletRequest pRequest, final HttpSession pSession,
			final ApplicationCoreFacade pApplicationCoreFacade )
	{


		UserWithPassword currentUser = (UserWithPassword) pRequest.getSession().getAttribute("currentUser");
		
		
		List<UserWithPassword> users = pApplicationCoreFacade.getAllUsers();
		Collections.sort(users, new UserComparator());
		pRequest.setAttribute("users", users);


		List<Season> seasons = pApplicationCoreFacade.getAllSeasons();
		Collections.sort(seasons, new SeasonsComparator());

		if (seasons != null){
			pRequest.setAttribute("seasons", seasons);
		}else{
			pRequest.setAttribute("seasons", new ArrayList<Season>());
			pRequest.setAttribute(HandlerMessagesEnum.ERROR.toString(), MessageError.SEASON_NO_SEASONS_AVAILABLE);
		}
		return null;
	}

	private class UserComparator
	implements Comparator<User>
	{
		@Override
		public int compare( User pUser1, User pUser2 )
		{
			return pUser1.getName().compareTo(pUser2.getName());
		}
	}
	private class SeasonsComparator implements Comparator<Season>{

		@Override
		public int compare(Season season1, Season season2) {

			return season1.getName().compareTo(season2.getName());
		}

	}

}
