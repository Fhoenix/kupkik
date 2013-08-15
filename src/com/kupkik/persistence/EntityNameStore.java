package com.kupkik.persistence;

public enum EntityNameStore {
	
	
	BADMINTON_SINGLE_GAME("BadmintonSingle"),
	BADMINTON_DOUBLE_GAME("BadmintonDouble"),
	KICKER_GAME("Kicker"),
	USER("User"),
	SEASON ("Season"),
	MATCHDAY ("MatchDay");
	
	private final String entityName;
	
	EntityNameStore(String entityName){
		this.entityName = entityName;
	}
	
	public String toString(){
		return entityName;
	}

}
