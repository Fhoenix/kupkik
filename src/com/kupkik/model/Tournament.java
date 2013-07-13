package com.kupkik.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.appengine.api.datastore.Key;
import com.kupkik.model.game.IGame;

public class Tournament
{
	private String name;
	private Key key;
	private Key parentKey;
	private List<IGame> games = new ArrayList<IGame>();
	private int gamesWon;
	private int gamesPlayed;



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
	public List<IGame> getGames() {
		return Collections.unmodifiableList(games);
	}
	
	
	
	public void setGames(List<IGame> games) {
		this.games = games;
	}



	public int getGamesWon() {
		return gamesWon;
	}



	public void setGamesWon(int gamesWon) {
		this.gamesWon = gamesWon;
	}



	public int getGamesPlayed() {
		return gamesPlayed;
	}



	public void setGamesPlayed(int gamesPlayed) {
		this.gamesPlayed = gamesPlayed;
	}
	
}
