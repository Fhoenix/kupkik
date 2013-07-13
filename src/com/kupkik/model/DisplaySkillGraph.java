package com.kupkik.model;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class DisplaySkillGraph {

	private int totalNumberWon;
	private int totalNumberPlayed;




	private List<Tournament> tournaments;
	private int totalNumberGames;

	public DisplaySkillGraph(int totalNumberPlayed, int totalNumberWon, int totalNumberGames, List<Tournament> tournaments ) {

		this.totalNumberPlayed = totalNumberPlayed;
		this.totalNumberWon = totalNumberWon;
		this.totalNumberGames = totalNumberGames;
		this.tournaments = tournaments;

	}

	public int getTotalNumberWon() {
		return totalNumberWon;
	}

	public void setTotalNumberWon(int totalNumberWon) {
		this.totalNumberWon = totalNumberWon;
	}

	public int getTotalNumberPlayed() {
		return totalNumberPlayed;
	}

	public int getTotalNumberLost() {
		return totalNumberPlayed-totalNumberWon;
	}

	public void setTotalNumberPlayed(int totalNumberPlayed) {
		this.totalNumberPlayed = totalNumberPlayed;
	}

	public int getTotalNumberGames() {
		return totalNumberGames;
	}

	public void setTotalNumberGames(int totalNumberGames) {
		this.totalNumberGames = totalNumberGames;
	}


	public String[] getTournamentNames() {
		String[] tournamentNames = new String[tournaments.size()];
		for (int i = 0; i < tournaments.size(); i++){
			tournamentNames[i]= "\""+tournaments.get(i).getName()+"\"";	
		}
		return tournamentNames;
	}
	
	public String[] getWinRateInPercentForEachTournament() {
		String[] values = new String[tournaments.size()];
		for (int i = 0; i < tournaments.size(); i++){
			Double rate;
			if (tournaments.get(i).getGamesPlayed() <= 0){
				rate = 0.00;
			}else{
				rate = (100.00/tournaments.get(i).getGamesPlayed())*tournaments.get(i).getGamesWon();	
			}
			values[i] = String.valueOf(rate);
		}
		return values;
	}

	




}
