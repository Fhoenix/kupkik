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
	private List<String> gameTypes = new ArrayList<String>();
	
	
	public Season(String name, Key key, Key parentKey, List<String> gameTypes){
		this.gameTypes = gameTypes;
		this.name = name;
		this.key = key;
		this.parentKey = parentKey;
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
	public List<String> getGameTypes() {
		return gameTypes;
	}
	

}
