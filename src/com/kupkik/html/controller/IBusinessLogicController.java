package com.kupkik.html.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * A controller which handles business-logic.
 */
public interface IBusinessLogicController
{
    /**
     * Handle the action and determine which view to show.
     * 
     * @param pRequest
     *            The request sent by the client.
     * @param pSession
     *            the current session
     * @return The name of the view to show next.
     */
    String performActionAndGetNextView( final HttpServletRequest pRequest, final HttpSession pSession );
}
