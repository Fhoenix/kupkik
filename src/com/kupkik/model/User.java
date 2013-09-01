package com.kupkik.model;

import java.io.Serializable;

import com.google.appengine.api.datastore.Key;

/**
 * a user; serializable because its also used a session
 */
public class User
        implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 5790554928089421666L;
	private String name;
	private String firstname;
	private String surname;
    private Key key;

    public User(String pName, Key key, String firstname, String surname)
    {
        name = pName;
        this.key = key;
        this.firstname = firstname;
        this.surname = surname;
    }

    public String getFirstname() {
		return firstname;
	}

	public String getSurname() {
		return surname;
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
    
    
    @Override
    public boolean equals(Object obj) {
    	return this.getKey() == ((User) obj).getKey();
    	
    }

}
