package com.kupkik.html.integration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class HttpServletRequestMock
        implements HttpServletRequest
{
    private HttpSession             mHttpSession;
    private Map<String, String>     mParametersGet;
    private HashMap<String, Object> mAttributsSet = new HashMap<String, Object>();

    public HttpServletRequestMock(final HttpSession pHttpSession, final Map<String, String> pParameters)
    {
        mHttpSession = pHttpSession;
        mParametersGet = pParameters;
    }

    public Map<String, Object> getAttributsSet()
    {
        return mAttributsSet;
    }

    @Override
    public void setAttribute( String pArg0, Object pArg1 )
    {
        mAttributsSet.put(pArg0, pArg1);
    }

    @Override
    public HttpSession getSession()
    {
        return mHttpSession;
    }

    @Override
    public String getParameter( String pArg0 )
    {
        if( !mParametersGet.containsKey(pArg0))
        {
            throw new RuntimeException("Parameter was not supposed to be used: " + pArg0);
        }
        return mParametersGet.get(pArg0);
    }

    @Override
    public Object getAttribute( String pArg0 )
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public Enumeration getAttributeNames()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public String getCharacterEncoding()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public int getContentLength()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public String getContentType()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public ServletInputStream getInputStream() throws IOException
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public String getLocalAddr()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public String getLocalName()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public int getLocalPort()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public Locale getLocale()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public Enumeration getLocales()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public Map getParameterMap()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public Enumeration getParameterNames()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public String[] getParameterValues( String pArg0 )
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public String getProtocol()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public BufferedReader getReader() throws IOException
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public String getRealPath( String pArg0 )
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public String getRemoteAddr()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public String getRemoteHost()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public int getRemotePort()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public RequestDispatcher getRequestDispatcher( String pArg0 )
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public String getScheme()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public String getServerName()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public int getServerPort()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public boolean isSecure()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public void removeAttribute( String pArg0 )
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public void setCharacterEncoding( String pArg0 ) throws UnsupportedEncodingException
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public String getAuthType()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public String getContextPath()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public Cookie[] getCookies()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public long getDateHeader( String pArg0 )
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public String getHeader( String pArg0 )
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public Enumeration getHeaderNames()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public Enumeration getHeaders( String pArg0 )
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public int getIntHeader( String pArg0 )
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public String getMethod()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public String getPathInfo()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public String getPathTranslated()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public String getQueryString()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public String getRemoteUser()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public String getRequestURI()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public StringBuffer getRequestURL()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public String getRequestedSessionId()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public String getServletPath()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public HttpSession getSession( boolean pArg0 )
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public Principal getUserPrincipal()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public boolean isRequestedSessionIdFromCookie()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public boolean isRequestedSessionIdFromURL()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public boolean isRequestedSessionIdFromUrl()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public boolean isRequestedSessionIdValid()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public boolean isUserInRole( String pArg0 )
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

}
