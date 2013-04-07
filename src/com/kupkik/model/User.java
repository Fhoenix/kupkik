package com.kupkik.model;

import java.io.Serializable;

/**
 * a user
 */
public class User implements Serializable
{
    private String name;

    public User(String pName)
    {
        name = pName;
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
