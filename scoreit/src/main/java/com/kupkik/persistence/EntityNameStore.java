package com.kupkik.persistence;

public enum EntityNameStore {
	
	USER("User"),
	SEASON ("Season"),
	GAME("Game");
	
	
	private final String entityName;
	
	EntityNameStore(String entityName){
		this.entityName = entityName;
	}
	
	public String toString(){
		return entityName;
	}

}
