package com.kupkik.model.game;

import java.util.Date;
import java.util.List;

import com.kupkik.model.User;


public interface IGame {

	
	public Date getDate();
	
	public List<User> getTeamOne();
	public List<User> getTeamTwo();
	
	public int getResultOne();
	public int getResultTwo();
}
