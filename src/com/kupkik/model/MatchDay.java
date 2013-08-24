package com.kupkik.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.appengine.api.datastore.Key;
import com.kupkik.model.game.Game;


public class MatchDay
{
	private String name;
	private Key parentKey;
	private List<Game> games = new ArrayList<Game>();
	private int gamesWon;
	private int gamesPlayed;



	public MatchDay(final String pName, final Key pParentKey)
	{
		name = pName;
		parentKey = pParentKey;

	}



	public Key getParentKey() {
		return parentKey;
	}



	public void setParentKey(Key parentKey) {
		this.parentKey = parentKey;
	}





	public String getName()
	{
		return name;
	}

	public void setName( final String pName )
	{
		name = pName;
	}
	public List<Game> getGames() {
		return Collections.unmodifiableList(games);
	}



	public void setGames(List<Game> games) {
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



	public String getPictogramPath() {
		// TODO Auto-generated method stub
		return null;
	}

}