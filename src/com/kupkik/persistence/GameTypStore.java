package com.kupkik.persistence;

public enum GameTypStore {
	
	
	BADMINTON_SINGLE_GAME("BadmintonSingle"),
	BADMINTON_DOUBLE_GAME("BadmintonDouble"),
	KICKER_GAME("Kicker");
	
	
	private final String gameType;
	
	GameTypStore(String gameType){
		this.gameType = gameType;
	}
	
	public String toString(){
		return gameType;
	}

}
