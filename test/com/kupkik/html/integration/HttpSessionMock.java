package com.kupkik.html.integration;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

public class HttpSessionMock
        implements HttpSession
{
    private Map<String, Object> mAttributesGet;
    private HashMap<String, Object> mAttributsSet = new HashMap<String, Object>();

    public HttpSessionMock(final Map<String, Object> pAttributesGet)
    {
        mAttributesGet = pAttributesGet;
    }

    public Map<String, Object> getAttributsSet()
    {
        return mAttributsSet;
    }

    @Override
    public Object getAttribute( String pArg0 )
    {
        if( !mAttributesGet.containsKey(pArg0))
        {
            throw new RuntimeException("Attribute was not supposed to be used: " + pArg0);
        }
        return mAttributesGet.get(pArg0);
    }

    @Override
    public void setAttribute( String pArg0, Object pArg1 )
    {
        mAttributsSet.put(pArg0, pArg1);
    }

    @Override
    public Enumeration getAttributeNames()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public long getCreationTime()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public String getId()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public long getLastAccessedTime()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public int getMaxInactiveInterval()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public ServletContext getServletContext()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public HttpSessionContext getSessionContext()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public Object getValue( String pArg0 )
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public String[] getValueNames()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public void invalidate()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public boolean isNew()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public void putValue( String pArg0, Object pArg1 )
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public void removeAttribute( String pArg0 )
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public void removeValue( String pArg0 )
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public void setMaxInactiveInterval( int pArg0 )
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

}
