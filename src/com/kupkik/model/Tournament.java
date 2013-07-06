package com.kupkik.model;

import com.google.appengine.api.datastore.Key;

public class Tournament
{
	private String name;
	private Key key;
	private Key parentKey;


	public Tournament(final String pName, final Key pKey, final Key pParentKey)
	{
		name = pName;
		key = pKey;
		parentKey = pParentKey;
		
	}



	public Key getParentKey() {
		return parentKey;
	}



	public void setParentKey(Key parentKey) {
		this.parentKey = parentKey;
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
