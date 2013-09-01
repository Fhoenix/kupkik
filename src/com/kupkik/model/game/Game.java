package com.kupkik.model.game;

import java.util.List;

import com.kupkik.model.User;

public class Game {



	private List<User> team1;
	private List<User> team2;
	private int resultTwo;
	private int resultOne;
	private String date;
	private String pictogramPath; 
	public static final String PATH = "/res/images/";
	public static final String FILE_EXTENSION = ".png";

	public Game(final List<User> team1, final List<User> team2, final String date, final int resultOne, final int resultTwo, String gameType ){
		this.team1 = team1;
		this.team2 = team2;
		this.resultOne = resultOne;
		this.resultTwo = resultTwo;
		this.date = date;
		this.pictogramPath = PATH + gameType + FILE_EXTENSION;
		
	}

	/**
	 * @return the pictogramPath
	 */
	public String getPictogramPath() {
		return pictogramPath;
	}



	public void setPlayerOne(List<User> playerOne) {
		this.team1 = playerOne;
	}



	public void setPlayerTwo(List<User> playerTwo) {
		this.team2 = playerTwo;
	}

	public int getResultTwo() {
		return resultTwo;
	}

	public void setResultTwo(int resultTwo) {
		this.resultTwo = resultTwo;
	}

	public int getResultOne() {
		return resultOne;
	}

	public void setResultOne(int resultOne) {
		this.resultOne = resultOne;
	}

	public String getDate() {
		return date;
	}



	public List<User> getTeamOne() {
		return team1;
	}


	public List<User> getTeamTwo() {
		return team2;
	}


}
