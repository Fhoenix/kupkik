package com.kupkik.html.controller;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet handles requests for HTML sites. It instantiates the controller
 * for the application logic, which does the real work. So all this servlet does
 * is starting the process and wiring classes together (dependency injection).
 * It is configured in the web.xml, that this servlet handles those types of
 * requests. Currently, only HTML is handled, so its the only servlet configured
 * in the web.xml.
 */
@SuppressWarnings("serial")
public class StarterServlet
        extends HttpServlet
{
    /**
     * This methods handles every client GET-request for HTML. It leaves the
     * actual application-logic to another method.
     */
    @Override
    public void doGet( final HttpServletRequest pRequest, final HttpServletResponse pResponse ) throws IOException
    {
        startRequestHandling(pRequest, pResponse);
    }

    /**
     * This methods handles every client POST-request for HTML. It leaves the
     * actual application-logic to another method.
     */
    @Override
    protected void doPost( final HttpServletRequest pRequest, final HttpServletResponse pResponse ) throws IOException
    {
        startRequestHandling(pRequest, pResponse);
    }

    /**
     * Start the request-handling.
     */
    private void startRequestHandling( final HttpServletRequest pRequest, final HttpServletResponse pResponse ) throws IOException
    {
        ApplicationLogic applicationLogic = new ApplicationLogic(pRequest, pResponse, getServletContext());
        applicationLogic.handleClientRequest();
    }
}
