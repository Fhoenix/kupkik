package com.kupkik.html.controller.businesslogic;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.anyString;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;

import com.kupkik.html.controller.ApplicationLogic;

public class RegisterUserTest
{
    @Test
    public void registerUserOk() throws Exception
    {
        // prepare the test

        final String userName = "usul";
        final String password1 = "qwert";
        final String password2 = "qwert";

        HttpServletRequest httpServletRequestMock = mock(HttpServletRequest.class);
        HttpServletResponse httpServletResponseMock = mock(HttpServletResponse.class);
        HttpSession httpSessionMock = mock(HttpSession.class);
        RequestDispatcher requestDispatcherMock = mock(RequestDispatcher.class);
        ServletContext servletContextMock = mock(ServletContext.class);

        when(httpServletRequestMock.getSession()).thenReturn(httpSessionMock);
        when(servletContextMock.getRequestDispatcher(anyString())).thenReturn(requestDispatcherMock);
        when(httpServletRequestMock.getParameter("action")).thenReturn("RegisterUser");
        when(httpServletRequestMock.getParameter("user_name")).thenReturn(userName);
        when(httpServletRequestMock.getParameter("password1")).thenReturn(password1);
        when(httpServletRequestMock.getParameter("password2")).thenReturn(password2);

        // run the test

        ApplicationLogic applicationLogic = new ApplicationLogic(httpServletRequestMock, httpServletResponseMock, servletContextMock);
        applicationLogic.handleClientRequest();

        // check the results
    }
}
