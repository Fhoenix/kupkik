package com.kupkik.html.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kupkik.applicationcore.ApplicationCoreFacade;

/**
 * A controller which handles business-logic.
 */
public interface IBusinessLogicController
{
    /**
     * Handle the action and determine which view to show.
     * 
     * @param pRequest
     *            the request sent by the client
     * @param pSession
     *            the current session
     * @param pApplicationCoreFacade
     *            the access to the application-core
     * @return The name of the view to show next.
     */
    String performActionAndGetNextView( final HttpServletRequest pRequest, final HttpSession pSession,
            final ApplicationCoreFacade pApplicationCoreFacade );
}
