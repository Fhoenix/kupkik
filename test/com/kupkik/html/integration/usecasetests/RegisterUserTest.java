package com.kupkik.html.integration.usecasetests;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;

import com.kupkik.html.integration.UseCaseTestParent;
import com.kupkik.ui.html.HtmlRequestProcessor;
import com.kupkik.utils.CredentialsUtils;

public class RegisterUserTest
        extends UseCaseTestParent
{
    @Test
    public void registerUserOk() throws Exception
    {
        // prepare the test

        final String userName = "usul";
        final String password1 = "qwert";
        final String password2 = "qwert";
        final String md5Password = CredentialsUtils.getMd5HashForText(password1);

        HashMap<String, Object> attributesForSession = new HashMap<>();
        attributesForSession.put("currentUser", null);

        HashMap<String, String> parametersForRequest = new HashMap<>();
        parametersForRequest.put("action", "RegisterUser");
        parametersForRequest.put("showView", null);
        parametersForRequest.put("user_name", userName);
        parametersForRequest.put("password1", password1);
        parametersForRequest.put("password2", password2);

        when(mPersistenceFacadeMock.doesUserExistWithName(anyString())).thenReturn(false);

        // run the test

        simulateUserRequest(attributesForSession, parametersForRequest);

        // check the results
        
        checkThatViewWasShown("/views/MainView.jsp");
        // check that only our user has been set in session
        checkThatOnlyUserHasBeenSetInSessionAndSameUserHasBeenSetInHttpRequest(userName, md5Password);
        // only two attributes of the http-request should have been set
        Assert.assertEquals(2, mHttpServletRequestMock.getAttributsSet().size());
        // only one user should have been saved
        verify(mPersistenceFacadeMock, times(1)).saveNewUser(anyString(), anyString());
        // only the user by the client should have been saved
        verify(mPersistenceFacadeMock, times(1)).saveNewUser(userName, md5Password);
        // check that no error message has been set in the http-request
        String errorMessage = (String) mHttpServletRequestMock.getAttributsSet().get("errorMessage");
        Assert.assertNull(errorMessage);
    }
    
    @Test
    public void registerUserAlreadyExists() throws Exception
    {
        // prepare the test

        final String userName = "usul";
        final String password1 = "qwert";
        final String password2 = "qwert";

        HashMap<String, Object> attributesForSession = new HashMap<>();
        attributesForSession.put("currentUser", null);

        HashMap<String, String> parametersForRequest = new HashMap<>();
        parametersForRequest.put("action", "RegisterUser");
        parametersForRequest.put("showView", null);
        parametersForRequest.put("user_name", userName);
        parametersForRequest.put("password1", password1);
        parametersForRequest.put("password2", password2);

        when(mPersistenceFacadeMock.doesUserExistWithName(anyString())).thenReturn(true);

        // run the test

        simulateUserRequest(attributesForSession, parametersForRequest);

        // check the results
        
        checkThatViewWasShown("/views/RegisterView.jsp");
        // check that only the guest-user has been set in session
        checkThatOnlyUserHasBeenSetInSessionAndSameUserHasBeenSetInHttpRequest(HtmlRequestProcessor.GUEST_USER.getName(), 
                HtmlRequestProcessor.GUEST_USER.getPasswordMd5());
        // only three attributes of the http-request should have been set
        Assert.assertEquals(3, mHttpServletRequestMock.getAttributsSet().size());
        // check that an error message has been set in the http-request
        String errorMessage = (String) mHttpServletRequestMock.getAttributsSet().get("errorMessage");
        Assert.assertTrue(errorMessage.length() > 1);
        // nothing should have been saved
        checkForNoPersistanceToDatabase();
    }
    
    @Test
    public void registerUserNameInvalid() throws Exception
    {
        // prepare the test

        final String userName = " ";
        final String password1 = "qwert";
        final String password2 = "qwert";

        HashMap<String, Object> attributesForSession = new HashMap<>();
        attributesForSession.put("currentUser", null);

        HashMap<String, String> parametersForRequest = new HashMap<>();
        parametersForRequest.put("action", "RegisterUser");
        parametersForRequest.put("showView", null);
        parametersForRequest.put("user_name", userName);
        parametersForRequest.put("password1", password1);
        parametersForRequest.put("password2", password2);

        when(mPersistenceFacadeMock.doesUserExistWithName(anyString())).thenReturn(false);

        // run the test

        simulateUserRequest(attributesForSession, parametersForRequest);

        // check the results
        
        checkThatViewWasShown("/views/RegisterView.jsp");
        // check that only the guest-user has been set in session
        checkThatOnlyUserHasBeenSetInSessionAndSameUserHasBeenSetInHttpRequest(HtmlRequestProcessor.GUEST_USER.getName(), 
                HtmlRequestProcessor.GUEST_USER.getPasswordMd5());
        // only three attributes of the http-request should have been set
        Assert.assertEquals(3, mHttpServletRequestMock.getAttributsSet().size());
        // check that an error message has been set in the http-request
        String errorMessage = (String) mHttpServletRequestMock.getAttributsSet().get("errorMessage");
        Assert.assertTrue(errorMessage.length() > 1);
        // nothing should have been saved
        checkForNoPersistanceToDatabase();
    }
    
    @Test
    public void registerPasswordInvalid() throws Exception
    {
        // prepare the test

        final String userName = "usul";
        final String password1 = "a";
        final String password2 = "a";

        HashMap<String, Object> attributesForSession = new HashMap<>();
        attributesForSession.put("currentUser", null);

        HashMap<String, String> parametersForRequest = new HashMap<>();
        parametersForRequest.put("action", "RegisterUser");
        parametersForRequest.put("showView", null);
        parametersForRequest.put("user_name", userName);
        parametersForRequest.put("password1", password1);
        parametersForRequest.put("password2", password2);

        when(mPersistenceFacadeMock.doesUserExistWithName(anyString())).thenReturn(false);

        // run the test

        simulateUserRequest(attributesForSession, parametersForRequest);

        // check the results
        
        checkThatViewWasShown("/views/RegisterView.jsp");
        // check that only the guest-user has been set in session
        checkThatOnlyUserHasBeenSetInSessionAndSameUserHasBeenSetInHttpRequest(HtmlRequestProcessor.GUEST_USER.getName(), 
                HtmlRequestProcessor.GUEST_USER.getPasswordMd5());
        // only three attributes of the http-request should have been set
        Assert.assertEquals(3, mHttpServletRequestMock.getAttributsSet().size());
        // check that an error message has been set in the http-request
        String errorMessage = (String) mHttpServletRequestMock.getAttributsSet().get("errorMessage");
        Assert.assertTrue(errorMessage.length() > 1);
        // nothing should have been saved
        checkForNoPersistanceToDatabase();
    }
    
    @Test
    public void registerPasswordsNotEqual() throws Exception
    {
        // prepare the test

        final String userName = "usul";
        final String password1 = "qwert";
        final String password2 = "dwert";

        HashMap<String, Object> attributesForSession = new HashMap<>();
        attributesForSession.put("currentUser", null);

        HashMap<String, String> parametersForRequest = new HashMap<>();
        parametersForRequest.put("action", "RegisterUser");
        parametersForRequest.put("showView", null);
        parametersForRequest.put("user_name", userName);
        parametersForRequest.put("password1", password1);
        parametersForRequest.put("password2", password2);

        when(mPersistenceFacadeMock.doesUserExistWithName(anyString())).thenReturn(false);

        // run the test

        simulateUserRequest(attributesForSession, parametersForRequest);

        // check the results
        
        checkThatViewWasShown("/views/RegisterView.jsp");
        // check that only the guest-user has been set in session
        checkThatOnlyUserHasBeenSetInSessionAndSameUserHasBeenSetInHttpRequest(HtmlRequestProcessor.GUEST_USER.getName(), 
                HtmlRequestProcessor.GUEST_USER.getPasswordMd5());
        // only three attributes of the http-request should have been set
        Assert.assertEquals(3, mHttpServletRequestMock.getAttributsSet().size());
        // check that an error message has been set in the http-request
        String errorMessage = (String) mHttpServletRequestMock.getAttributsSet().get("errorMessage");
        Assert.assertTrue(errorMessage.length() > 1);
        // nothing should have been saved
        checkForNoPersistanceToDatabase();
    }
}
