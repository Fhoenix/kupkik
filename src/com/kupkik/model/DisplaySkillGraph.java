package com.kupkik.model;


import java.util.List;

import com.kupkik.model.game.BadmintonSingle;

public class DisplaySkillGraph {

	private int gamesWon;
	private int gamesPlayed;
	
	private List<BadmintonSingle> badmintonSingleGames;


	public DisplaySkillGraph(int gamesPlayed, int gamesWon, List<BadmintonSingle>  badmintonSingleGames ) {
		this.badmintonSingleGames = badmintonSingleGames;
		this.gamesPlayed = gamesPlayed;
		this.gamesWon = gamesWon;
		
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
	
	public int getGamesLost(){
		return gamesPlayed-gamesWon;
	}
	
	public List<BadmintonSingle> getBadmintonSingleGames() {
		return badmintonSingleGames;
	}

	public void setBadmintonSingleGames(List<BadmintonSingle> badmintonSingleGames) {
		this.badmintonSingleGames = badmintonSingleGames;
	}
}
