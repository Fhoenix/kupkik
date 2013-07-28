package com.kupkik.model.game;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.kupkik.model.User;

public class BadmintonSingle implements IGame {



	private List<User> team1;
	private List<User> team2;
	private int resultTwo;
	private int resultOne;
	private Date date;
	private String matchDay;

	public BadmintonSingle(final List<User> team1, final List<User> team2, final int resultOne, final int resultTwo, final Date date, final String matchDay){
		this.team1 = team1;
		this.team2 = team2;
		this.resultOne = resultOne;
		this.resultTwo = resultTwo;
		this.matchDay = matchDay;
		this.date = date;
	}

	public List<User> getPlayerOne() {
		return team1;
	}

	public void setPlayerOne(List<User> playerOne) {
		this.team1 = playerOne;
	}

	public List<User> getPlayerTwo() {
		return team2;
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

	@Override
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public List<User> getTeamOne() {
	
		return team1;
	}

	@Override
	public List<User> getTeamTwo() {

		return team2;
	}




}
