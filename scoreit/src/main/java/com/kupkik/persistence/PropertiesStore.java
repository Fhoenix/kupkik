package com.kupkik.persistence;

public enum PropertiesStore {
	
	//USER
	PASSWORD_MD5("passwordMd5"),
	FIRSTNAME("firstname"),
	SURNAME("surname"),
	
	//SEASON
	USERS_ALLOWED_TO_EDIT("EditUser"),
	
	//GAME
	DATE("date"),
	GAMETYPE("gameType"),
	TEAM_ONE("teamOne"),
	TEAM_TWO ("teamTwo"),
	RESULT_ONE("resultOne"),
	RESULT_TWO("resultTwo");
	
	
	private final String entityName;
	
	PropertiesStore(String entityName){
		this.entityName = entityName;
	}
	
	public String toString(){
		return entityName;
	}

}
