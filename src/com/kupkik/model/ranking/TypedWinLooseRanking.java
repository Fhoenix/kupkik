package com.kupkik.model.ranking;

import java.util.List;

import com.kupkik.model.game.Game;

public class TypedWinLooseRanking {

	
	private List<WinLooseRows> ranking;
	private String gameType;
	private String imagePath;




	public TypedWinLooseRanking(List<WinLooseRows> ranking, String gameType) {
		this.gameType = gameType;
		this.ranking = ranking;
		this.imagePath = Game.PATH + gameType + Game.FILE_EXTENSION;
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
	 * @return the imagePath
	 */
	public String getImagePath() {
		return imagePath;
	}
	
	/**
	 * @return the ranking
	 */
	public List<WinLooseRows> getRanking() {
		return ranking;
	}
}
