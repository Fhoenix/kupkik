package com.kupkik.model.game;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.kupkik.model.User;

public class BadmintonSingle implements IGame {



	private User playerOne;
	private User playerTwo;
	private int resultTwo;
	private int resultOne;
	private Date date;
	private String matchDay;

	public BadmintonSingle(final User playerOne, final User playerTwo, final int resultOne, final int resultTwo, final Date date, final String matchDay){
		this.playerOne = playerOne;
		this.playerTwo = playerTwo;
		this.resultOne = resultOne;
		this.resultTwo = resultTwo;
		this.matchDay = matchDay;
		this.date = date;
	}

	public User getPlayerOne() {
		return playerOne;
	}

	public void setPlayerOne(User playerOne) {
		this.playerOne = playerOne;
	}

	public User getPlayerTwo() {
		return playerTwo;
	}

	public void setPlayerTwo(User playerTwo) {
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

	@Override
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public List<User> getTeamOne() {
		List<User> teamOne = new ArrayList<User>();
		teamOne.add(playerOne);
		return teamOne;
	}

	@Override
	public List<User> getTeamTwo() {
		List<User> teamTwo = new ArrayList<User>();
		teamTwo.add(playerTwo);
		return teamTwo;
	}




}
