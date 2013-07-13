package com.kupkik.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.appengine.api.datastore.Key;

public class Season {

	private String name;
	private Key key;
	private Key parentKey;
	private List<Tournament> tournaments = new ArrayList<Tournament>();
	
	
	public Season(String name, Key key, Key parentKey){
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


	public List<Tournament> getTournaments() {
		return Collections.unmodifiableList(tournaments);
	}


	public void setTournaments(List<Tournament> tournaments) {
		this.tournaments = tournaments;
	}
	

}
