package com.kupkik.model.game;

import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.kupkik.model.User;

public class BadmintonDouble implements IGame {



	private List<User> team1;
	private List<User> team2;
	private int resultTwo;
	private int resultOne;
	private Date date;
	private String matchDay;

	public BadmintonDouble(List<User> team1,List<User> team2,final String matchDay, final int resultOne, final int resultTwo, final Date date){
		this.team1 = team1;
		this.team2 = team2;
		this.resultOne = resultOne;
		this.resultTwo = resultTwo;
		this.matchDay = matchDay;
		this.date = date;
	}


	public List<User> getTeam1() {
		return team1;
	}



	public List<User>  getTeam2() {
		return team2;
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




}
