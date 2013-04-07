package com.kupkik.controller;

import javax.servlet.http.HttpServletRequest;

/**
 * A controller which handles business-logic.
 */
public interface IBusinessLogicController
{
    /**
     * Handle the action and determine which view to show.
     * 
     * @param request
     *            The request sent by the client.
     * @return The name of the view to show next.
     */
    String performActionAndGetNextView( final HttpServletRequest request );
}
