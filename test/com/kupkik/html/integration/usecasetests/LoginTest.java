package com.kupkik.html.integration.usecasetests;

import static org.mockito.Mockito.when;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;

import com.kupkik.html.integration.UseCaseTestParent;
import com.kupkik.ui.html.HtmlRequestProcessor;
import com.kupkik.utils.CredentialsUtils;

public class LoginTest
        extends UseCaseTestParent
{
    @Test
    public void loginUserOk() throws Exception
    {
        // prepare the test

        final String userName = "usul";
        final String password = "qwert";
        final String md5Password = CredentialsUtils.getMd5HashForText(password);

        HashMap<String, Object> attributesForSession = new HashMap<>();
        attributesForSession.put("currentUser", null);

        HashMap<String, String> parametersForRequest = new HashMap<>();
        parametersForRequest.put("action", "Login");
        parametersForRequest.put("showView", null);
        parametersForRequest.put("user_name", userName);
        parametersForRequest.put("password", password);

        when(mPersistenceFacadeMock.doesUserExistWithNameAndMd5Password("usul", md5Password)).thenReturn(true);

        // run the test

        simulateUserRequest(attributesForSession, parametersForRequest);

        // check the results

        checkThatViewWasShown("/views/MainView.jsp");
        // check that only our user has been set in session
        checkThatOnlyUserHasBeenSetInSessionAndSameUserHasBeenSetInHttpRequest(userName, md5Password);
        // nothing should have been saved
        checkForNoPersistanceToDatabase();
    }

    @Test
    public void loginUserWrongCredentials() throws Exception
    {
        // prepare the test

        final String userName = "usul";
        final String password = "qwert";
        final String md5Password = CredentialsUtils.getMd5HashForText(password);

        HashMap<String, Object> attributesForSession = new HashMap<>();
        attributesForSession.put("currentUser", null);

        HashMap<String, String> parametersForRequest = new HashMap<>();
        parametersForRequest.put("action", "Login");
        parametersForRequest.put("showView", null);
        parametersForRequest.put("user_name", userName);
        parametersForRequest.put("password", password);

        when(mPersistenceFacadeMock.doesUserExistWithNameAndMd5Password("usul", md5Password)).thenReturn(false);

        // run the test

        simulateUserRequest(attributesForSession, parametersForRequest);

        // check the results

        checkThatViewWasShown("/views/LoginView.jsp");
        // check that only the guest-user has been set in session
        checkThatOnlyUserHasBeenSetInSessionAndSameUserHasBeenSetInHttpRequest(HtmlRequestProcessor.GUEST_USER.getName(), 
                HtmlRequestProcessor.GUEST_USER.getPasswordMd5());
        // nothing should have been saved
        checkForNoPersistanceToDatabase();
        // check that an error message has been set in the http-request
        String errorMessage = (String) mHttpServletRequestMock.getAttributsSet().get("errorMessage");
        Assert.assertTrue(errorMessage.length() > 1);
    }
}
