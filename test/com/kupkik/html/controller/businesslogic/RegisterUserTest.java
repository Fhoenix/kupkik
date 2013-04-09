package com.kupkik.html.controller.businesslogic;

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
import org.junit.Test;

import com.kupkik.applicationcore.ApplicationCoreFacade;
import com.kupkik.applicationcore.ApplicationCoreFacade.SaveUserAnswer;
import com.kupkik.html.controller.ApplicationLogic;
import com.kupkik.html.view.ViewHelper;
import com.kupkik.model.UserWithPassword;
import com.kupkik.utils.CredentialsUtils;

public class RegisterUserTest
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
        HttpSessionMock httpSessionMock = new HttpSessionMock(attributesForSession);

        HashMap<String, String> parametersForRequest = new HashMap<>();
        parametersForRequest.put("action", "RegisterUser");
        parametersForRequest.put("showView", null);
        parametersForRequest.put("user_name", userName);
        parametersForRequest.put("password1", password1);
        parametersForRequest.put("password2", password2);
        HttpServletRequestMock httpServletRequestMock = new HttpServletRequestMock(httpSessionMock, parametersForRequest);

        HttpServletResponseMock httpServletResponseMock = new HttpServletResponseMock();

        ServletContext servletContextMock = mock(ServletContext.class);
        RequestDispatcher requestDispatcherMock = mock(RequestDispatcher.class);
        when(servletContextMock.getRequestDispatcher(anyString())).thenReturn(requestDispatcherMock);

        ApplicationCoreFacade applicationCoreFacadeFacadeMock = mock(ApplicationCoreFacade.class);
        when(applicationCoreFacadeFacadeMock.saveNewUser(userName, password1)).thenReturn(SaveUserAnswer.OK);

        // run the test

        ApplicationLogic applicationLogic = new ApplicationLogic(httpServletRequestMock, httpServletResponseMock, servletContextMock,
                applicationCoreFacadeFacadeMock);
        applicationLogic.handleClientRequest();

        // check the results

        // view which should have been called
        verify(servletContextMock).getRequestDispatcher("/views/MainView.jsp");
        // a view should only have been showed once and with our parameters
        verify(requestDispatcherMock, times(1)).forward(httpServletRequestMock, httpServletResponseMock);
        // a view should only have been showed once with any parameters
        verify(requestDispatcherMock, times(1)).forward(any(HttpServletRequest.class), any(HttpServletResponse.class));
        // only one attribute of the session should have been set
        Assert.assertEquals(1, httpSessionMock.getAttributsSet().size());
        // check that the expected current user has been set in the session
        UserWithPassword setCurrentUser = (UserWithPassword) httpSessionMock.getAttributsSet().get("currentUser");
        Assert.assertEquals(userName, setCurrentUser.getName());
        Assert.assertEquals(md5Password, setCurrentUser.getPasswordMd5());
        // only two attributes of the http-request should have been set
        Assert.assertEquals(2, httpServletRequestMock.getAttributsSet().size());
        // check that the expected current user has been set in the http-request
        setCurrentUser = (UserWithPassword) httpServletRequestMock.getAttributsSet().get("currentUser");
        Assert.assertEquals(userName, setCurrentUser.getName());
        Assert.assertEquals(CredentialsUtils.getMd5HashForText(password1), setCurrentUser.getPasswordMd5());
        // check that the view-helper has been set in the http-request
        ViewHelper viewHelper = (ViewHelper) httpServletRequestMock.getAttributsSet().get("viewHelper");
        Assert.assertNotNull(viewHelper);
        // only one user should have been saved
        verify(applicationCoreFacadeFacadeMock, times(1)).saveNewUser(anyString(), anyString());
        // only the user by the client should have been saved
        verify(applicationCoreFacadeFacadeMock, times(1)).saveNewUser(userName, password1);

    }
}
