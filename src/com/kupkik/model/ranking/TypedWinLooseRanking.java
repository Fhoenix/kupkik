package com.kupkik.model.ranking;

import java.util.List;

public class TypedWinLooseRanking {

	
	private List<WinLooseRows> ranking;
	private String gameType;


	public TypedWinLooseRanking(List<WinLooseRows> ranking, String gameType) {
		this.gameType = gameType;
		this.ranking = ranking;
	}
	
	/**
	 * @return the gameType
	 */
	public String getGameType() {
		return gameType;
	}

	/**
	 * @param gameType the gameType to set
	 */
	public void setGameType(String gameType) {
		this.gameType = gameType;
	}

	/**
	 * @return the ranking
	 */
	public List<WinLooseRows> getRanking() {
		return ranking;
	}
}
