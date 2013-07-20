package com.kupkik.model;

import java.io.Serializable;

import com.google.appengine.api.datastore.Key;

/**
 * a user including the password (md5); serializable because its also used a
 * session
 */
public class UserWithPassword
        extends User
        implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -5976571227535896875L;
	private String mPasswordMd5;
   

    public UserWithPassword(String pName, String pPasswordMd5, Key key, String firstname, String surname)
    {
        super(pName, key,firstname,surname);
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
