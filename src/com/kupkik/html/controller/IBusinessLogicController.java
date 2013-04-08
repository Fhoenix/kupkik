package com.kupkik.html.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kupkik.persistence.IPersistenceFacade;

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
     * @param PersistenceFacade
     *            the access to the database
     * @return The name of the view to show next.
     */
    String performActionAndGetNextView( final HttpServletRequest pRequest, final HttpSession pSession,
            final IPersistenceFacade pPersistenceFacade );
}
