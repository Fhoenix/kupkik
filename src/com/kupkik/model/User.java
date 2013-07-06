package com.kupkik.model;

import java.io.Serializable;

import com.google.appengine.api.datastore.Key;

/**
 * a user; serializable because its also used a session
 */
public class User
        implements Serializable
{
    private String name;
    private Key key;

    public User(String pName, Key key)
    {
        name = pName;
        this.key = key;
    }

    public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getName()
    {
        return name;
    }

    public void setName( final String pName )
    {
        name = pName;
    }

}
