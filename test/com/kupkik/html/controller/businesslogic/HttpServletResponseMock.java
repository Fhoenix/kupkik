package com.kupkik.html.controller.businesslogic;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class HttpServletResponseMock
        implements HttpServletResponse
{

    @Override
    public void flushBuffer() throws IOException
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public int getBufferSize()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public String getCharacterEncoding()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public String getContentType()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public Locale getLocale()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public PrintWriter getWriter() throws IOException
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public boolean isCommitted()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public void reset()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public void resetBuffer()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public void setBufferSize( int pArg0 )
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public void setCharacterEncoding( String pArg0 )
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public void setContentLength( int pArg0 )
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public void setContentType( String pArg0 )
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public void setLocale( Locale pArg0 )
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public void addCookie( Cookie pArg0 )
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public void addDateHeader( String pArg0, long pArg1 )
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public void addHeader( String pArg0, String pArg1 )
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public void addIntHeader( String pArg0, int pArg1 )
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public boolean containsHeader( String pArg0 )
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public String encodeRedirectURL( String pArg0 )
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public String encodeRedirectUrl( String pArg0 )
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public String encodeURL( String pArg0 )
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public String encodeUrl( String pArg0 )
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public void sendError( int pArg0 ) throws IOException
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public void sendError( int pArg0, String pArg1 ) throws IOException
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public void sendRedirect( String pArg0 ) throws IOException
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public void setDateHeader( String pArg0, long pArg1 )
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public void setHeader( String pArg0, String pArg1 )
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public void setIntHeader( String pArg0, int pArg1 )
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public void setStatus( int pArg0 )
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public void setStatus( int pArg0, String pArg1 )
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

}
