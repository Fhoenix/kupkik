package com.kupkik.ui.html.requesthandlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import com.kupkik.applicationcore.ApplicationCoreFacade;
import com.kupkik.messages.MessageHandlerEnum;
import com.kupkik.messages.MessageError;
import com.kupkik.model.Season;

import com.kupkik.model.User;
import com.kupkik.model.UserWithPassword;

import com.kupkik.ui.html.IHtmlRequestHandler;
import com.kupkik.ui.html.comperators.ComparatorSeason;
import com.kupkik.ui.html.comperators.ComparatorUser;


public class ShowEditUserViewHandler implements IHtmlRequestHandler
{

	@Override
	public String performActionAndGetNextView( final HttpServletRequest pRequest, final HttpSession pSession,
			final ApplicationCoreFacade pApplicationCoreFacade )
	{

		
		return null;
	}



}
