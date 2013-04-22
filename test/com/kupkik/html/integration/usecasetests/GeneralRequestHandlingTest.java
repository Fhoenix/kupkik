package com.kupkik.html.integration.usecasetests;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.Assert;

import org.junit.Test;

import com.kupkik.html.integration.UseCaseTestParent;
import com.kupkik.ui.html.HtmlRequestProcessor;

public class GeneralRequestHandlingTest
        extends UseCaseTestParent
{
    @Test
    public void showMainViewIfNoParameters() throws Exception
    {
        // prepare the test

        HashMap<String, Object> attributesForSession = new HashMap<>();
        attributesForSession.put("currentUser", null);

        HashMap<String, String> parametersForRequest = new HashMap<>();
        parametersForRequest.put("action", null);
        parametersForRequest.put("showView", null);

        // run the test

        simulateUserRequest(attributesForSession, parametersForRequest);

        // check the results

        checkThatViewWasShown("/views/MainView.jsp");
        // check that only the guest-user has been set in session
        checkThatOnlyUserHasBeenSetInSessionAndSameUserHasBeenSetInHttpRequest(HtmlRequestProcessor.GUEST_USER.getName(), 
                HtmlRequestProcessor.GUEST_USER.getPasswordMd5());
        // nothing should have been saved
        checkForNoPersistanceToDatabase();
    }
    
    @Test
    public void invalidRequestBecauseBothActionAndShowviewParameterSent() throws Exception
    {
        // prepare the test

        HashMap<String, Object> attributesForSession = new HashMap<>();
        attributesForSession.put("currentUser", null);

        HashMap<String, String> parametersForRequest = new HashMap<>();
        parametersForRequest.put("action", "RegisterUser");
        parametersForRequest.put("showView", "RegisterView");

        // run the test

        simulateUserRequest(attributesForSession, parametersForRequest);

        // check the results

        // no attribute of the session should have been set
        Assert.assertEquals(0, mHttpSessionMock.getAttributsSet().size());
        // no attributes of the http-request should have been set
        Assert.assertEquals(0, mHttpServletRequestMock.getAttributsSet().size());
        // the error should have been written
        Assert.assertTrue(mHttpServletResponseMock.hasBeenWritten());
        // nothing should have been saved
        checkForNoPersistanceToDatabase();
        // no view should have been showed
        verify(mRequestDispatcherMock, times(0)).forward(any(HttpServletRequest.class), any(HttpServletResponse.class));
    }

    @Test
    public void invalidRequestBecauseActionDoesNotExists() throws Exception
    {
        // prepare the test

        HashMap<String, Object> attributesForSession = new HashMap<>();
        attributesForSession.put("currentUser", null);

        HashMap<String, String> parametersForRequest = new HashMap<>();
        parametersForRequest.put("action", "notExistingAction");
        parametersForRequest.put("showView", null);

        // run the test

        simulateUserRequest(attributesForSession, parametersForRequest);

        // check the results

        // the error should have been written
        Assert.assertTrue(mHttpServletResponseMock.hasBeenWritten());
        // nothing should have been saved
        checkForNoPersistanceToDatabase();
        // no view should have been showed
        verify(mRequestDispatcherMock, times(0)).forward(any(HttpServletRequest.class), any(HttpServletResponse.class));
    }

    @Test
    public void invalidRequestBecauseViewDoesNotExists() throws Exception
    {
        // prepare the test

        HashMap<String, Object> attributesForSession = new HashMap<>();
        attributesForSession.put("currentUser", null);

        HashMap<String, String> parametersForRequest = new HashMap<>();
        parametersForRequest.put("action", null);
        parametersForRequest.put("showView", "notExistingVew");

        // run the test

        simulateUserRequest(attributesForSession, parametersForRequest);

        // check the results

        // the error should have been written
        Assert.assertTrue(mHttpServletResponseMock.hasBeenWritten());
        // nothing should have been saved
        checkForNoPersistanceToDatabase();
        // no view should have been showed
        verify(mRequestDispatcherMock, times(0)).forward(any(HttpServletRequest.class), any(HttpServletResponse.class));
    }
}
