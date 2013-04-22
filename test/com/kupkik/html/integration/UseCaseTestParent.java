package com.kupkik.html.integration;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Before;

import com.google.appengine.api.datastore.DatastoreService;
import com.kupkik.applicationcore.ApplicationCoreFacade;
import com.kupkik.model.UserWithPassword;
import com.kupkik.persistence.PersistenceFacade;
import com.kupkik.ui.html.HtmlRequestProcessor;
import com.kupkik.ui.html.view.ViewHelper;

public class UseCaseTestParent
{
    private ApplicationCoreFacade     mApplicationCoreFacade;
    protected PersistenceFacade       mPersistenceFacadeMock;
    protected ServletContext          mServletContextMock;
    protected HttpServletResponseMock mHttpServletResponseMock;
    protected HttpServletRequestMock  mHttpServletRequestMock;
    protected HttpSessionMock         mHttpSessionMock;
    protected RequestDispatcher       mRequestDispatcherMock;
    protected DatastoreService        mDatastoreServiceMock;

    @Before
    public void init()
    {
        mServletContextMock = mock(ServletContext.class);
        mRequestDispatcherMock = mock(RequestDispatcher.class);
        when(mServletContextMock.getRequestDispatcher(anyString())).thenReturn(mRequestDispatcherMock);

        mHttpServletResponseMock = new HttpServletResponseMock();

        mDatastoreServiceMock = new DatastoreMock();

        mPersistenceFacadeMock = mock(PersistenceFacade.class);

        mApplicationCoreFacade = new ApplicationCoreFacade(mPersistenceFacadeMock);
    }

    /**
     * Simulates a user request.
     * 
     * @param pAttributesForSession
     *            The attributes, that the session-mock should return, when
     *            asked.
     * @param pParametersForRequest
     *            The parameters, that the http-request-mock should return, when
     *            asked.
     * @throws Exception
     */
    protected void simulateUserRequest( final HashMap<String, Object> pAttributesForSession,
            final HashMap<String, String> pParametersForRequest ) throws Exception
    {
        mHttpSessionMock = new HttpSessionMock(pAttributesForSession);

        mHttpServletRequestMock = new HttpServletRequestMock(mHttpSessionMock, pParametersForRequest);

        HtmlRequestProcessor htmlRequestProcessor = new HtmlRequestProcessor(mHttpServletRequestMock, mHttpServletResponseMock,
                mServletContextMock, mApplicationCoreFacade);
        htmlRequestProcessor.handleClientRequest();
    }

    protected void checkThatViewWasShown( final String pViewPath ) throws Exception
    {
        // check that the view-helper has been set in the http-request
        ViewHelper viewHelper = (ViewHelper) mHttpServletRequestMock.getAttributsSet().get("viewHelper");
        Assert.assertNotNull(viewHelper);
        
        // view which should have been called
        verify(mServletContextMock).getRequestDispatcher(pViewPath);
        // a view should only have been showed once and with our parameters
        verify(mRequestDispatcherMock, times(1)).forward(mHttpServletRequestMock, mHttpServletResponseMock);
        // a view should only have been showed once with any parameters
        verify(mRequestDispatcherMock, times(1)).forward(any(HttpServletRequest.class), any(HttpServletResponse.class));
    }
    
    protected void checkThatOnlyUserHasBeenSetInSessionAndSameUserHasBeenSetInHttpRequest(final String pExpectedName, final String pExpectedMd5Password)
    {
        // only one attribute of the session should have been set
        Assert.assertEquals(1, mHttpSessionMock.getAttributsSet().size());
        // check that the expected current user has been set in the session
        UserWithPassword setCurrentUser = (UserWithPassword) mHttpSessionMock.getAttributsSet().get("currentUser");
        Assert.assertEquals(pExpectedName, setCurrentUser.getName());
        Assert.assertEquals(pExpectedMd5Password, setCurrentUser.getPasswordMd5());
        // check that the expected current user has been set in the http-request
        setCurrentUser = (UserWithPassword) mHttpServletRequestMock.getAttributsSet().get("currentUser");
        Assert.assertEquals(pExpectedName, setCurrentUser.getName());
        Assert.assertEquals(pExpectedMd5Password, setCurrentUser.getPasswordMd5());
    }
    
    protected void checkForNoPersistanceToDatabase()
    {
        // no user should have been saved
        verify(mPersistenceFacadeMock, times(0)).saveNewUser(anyString(), anyString());
    }
}
