package com.kupkik.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.appengine.api.datastore.Key;

public class Season {

	private String name;
	private Key key;
	private Key parentKey;
	private List<MatchDay> matchDays = new ArrayList<MatchDay>();
	private String gameType;
	private List<User> editUsers = new ArrayList<User>();
	
	
	public Season(String name, Key key, Key parentKey, String gameType, ArrayList<User> editUser){
		this.gameType = gameType;
		this.editUsers = editUser;
		this.name = name;
		this.key = key;
		this.parentKey = parentKey;
	}


	public List<User> getEditUsers() {
		return editUsers;
	}
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Key getKey() {
		return key;
	}


	public void setKey(Key key) {
		this.key = key;
	}


	public Key getParentKey() {
		return parentKey;
	}


	public void setParentKey(Key parentKey) {
		this.parentKey = parentKey;
	}


	public List<MatchDay> getTournaments() {
		return Collections.unmodifiableList(matchDays);
	}


	public void setTournaments(List<MatchDay> matchDays) {
		this.matchDays = matchDays;
	}


	/**
	 * @return the gameTypes
	 */
	public String getGameType() {
		return gameType;
	}
	

}
