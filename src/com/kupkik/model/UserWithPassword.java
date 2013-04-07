package com.kupkik.model;

import java.io.Serializable;

/**
 * a user including the password (md5)
 */
public class UserWithPassword
        extends User
        implements Serializable
{
    private String mPasswordMd5;

    public UserWithPassword(String pName, String pPasswordMd5)
    {
        super(pName);
        mPasswordMd5 = pPasswordMd5;
    }

    public String getPasswordMd5()
    {
        return mPasswordMd5;
    }

    public void setPasswordMd5( String pPasswordMd5 )
    {
        mPasswordMd5 = pPasswordMd5;
    }

}
