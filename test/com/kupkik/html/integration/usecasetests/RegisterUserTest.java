package com.kupkik.html.integration.usecasetests;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;

import com.kupkik.html.integration.UseCaseTestParent;
import com.kupkik.model.UserWithPassword;
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

        HashMap<String, String> attributesForSession = new HashMap<>();
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
        // only one attribute of the session should have been set
        Assert.assertEquals(1, mHttpSessionMock.getAttributsSet().size());
        // check that the expected current user has been set in the session
        UserWithPassword setCurrentUser = (UserWithPassword) mHttpSessionMock.getAttributsSet().get("currentUser");
        Assert.assertEquals(userName, setCurrentUser.getName());
        Assert.assertEquals(md5Password, setCurrentUser.getPasswordMd5());
        // only two attributes of the http-request should have been set
        Assert.assertEquals(2, mHttpServletRequestMock.getAttributsSet().size());
        // check that the expected current user has been set in the http-request
        setCurrentUser = (UserWithPassword) mHttpServletRequestMock.getAttributsSet().get("currentUser");
        Assert.assertEquals(userName, setCurrentUser.getName());
        Assert.assertEquals(CredentialsUtils.getMd5HashForText(password1), setCurrentUser.getPasswordMd5());
        // only one user should have been saved
        verify(mPersistenceFacadeMock, times(1)).saveNewUser(anyString(), anyString());
        // only the user by the client should have been saved
        verify(mPersistenceFacadeMock, times(1)).saveNewUser(userName, md5Password);

    }
}
