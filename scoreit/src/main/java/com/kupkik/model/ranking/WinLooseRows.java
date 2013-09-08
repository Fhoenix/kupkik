package com.kupkik.model.ranking;

import com.kupkik.model.User;

public class WinLooseRows {

	private User user;
	private int totalNumberOfGamesPlayed;
	private int totalNumberOfGamesWon;
	private int totalNumberOfGames;

	
	public WinLooseRows(User user, int gamesPlayed, int gamesWon, int totalNumberOfGames) {
		super();
		this.user = user;
		this.totalNumberOfGames = totalNumberOfGames;
		this.totalNumberOfGamesPlayed = gamesPlayed;
		this.totalNumberOfGamesWon = gamesWon;
	}

	/**
	 * @return the totalNumberOfGamesPlayed
	 */
	public int getTotalNumberOfGames() {
		return totalNumberOfGames;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the gamesPlayed
	 */
	public int getGamesPlayed() {
		return totalNumberOfGamesPlayed;
	}

	/**
	 * @param gamesPlayed the gamesPlayed to set
	 */
	public void setGamesPlayed(int gamesPlayed) {
		this.totalNumberOfGamesPlayed = gamesPlayed;
	}

	/**
	 * @return the gamesWon
	 */
	public int getGamesWon() {
		return totalNumberOfGamesWon;
	}

	/**
	 * @param gamesWon the gamesWon to set
	 */
	public void setGamesWon(int gamesWon) {
		this.totalNumberOfGamesWon = gamesWon;
	}

	/**
	 * @return the gamesWonInPercent
	 */
	public double getGamesWonInPercent() {
		if (totalNumberOfGamesPlayed == 0){
			return 0.00;
		}
		double rate = (100.00/totalNumberOfGamesPlayed)*totalNumberOfGamesWon;	
		return rate;
	}


	/**
	 * @return the gamesLostInPercent
	 */
	public double getGamesLostInPercent() {
		double rate = 100.00 - getGamesWonInPercent();
		return rate;
	}


	
}
