package com.kupkik.model.game;

import java.util.Date;

public class BadmintonSingle {



	private String playerOne;
	private String playerTwo;
	private int resultTwo;
	private int resultOne;
	private Date date;
	private String tournament;

	public BadmintonSingle(final String playerOne, final String playerTwo, final int resultOne, final int resultTwo, final Date date, final String tournament){
		this.playerOne = playerOne;
		this.playerTwo = playerTwo;
		this.resultOne = resultOne;
		this.resultTwo = resultTwo;
		this.tournament = tournament;
		this.date = date;
	}

	public String getPlayerOne() {
		return playerOne;
	}

	public void setPlayerOne(String playerOne) {
		this.playerOne = playerOne;
	}

	public String getPlayerTwo() {
		return playerTwo;
	}

	public void setPlayerTwo(String playerTwo) {
		this.playerTwo = playerTwo;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
