package com.kupkik.model;

public class Tournament
{
    private String name;

    public Tournament(final String pName)
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
