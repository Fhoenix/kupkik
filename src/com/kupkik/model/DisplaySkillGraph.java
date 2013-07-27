package com.kupkik.model;

import java.util.List;

public class DisplaySkillGraph {

	private int totalNumberWon;

	private int totalNumberPlayed;
	private List<MatchDay> matchDays;
	private int totalNumberGames;

	public DisplaySkillGraph(int totalNumberPlayed, int totalNumberWon, int totalNumberGames, List<MatchDay> matchDays ) {
		this.totalNumberPlayed = totalNumberPlayed;
		this.totalNumberWon = totalNumberWon;
		this.totalNumberGames = totalNumberGames;
		this.matchDays = matchDays;
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

	public String[] getMatchDayNames() {
		String[] matchDayNames = new String[matchDays.size()];
		for (int i = 0; i < matchDays.size(); i++){
			matchDayNames[i]= "\""+matchDays.get(i).getName()+"\"";	
		}
		return matchDayNames;
	}
	
	public String[] getWinRateInPercentForEachMatchDay() {
		String[] values = new String[matchDays.size()];
		for (int i = 0; i < matchDays.size(); i++){
			Double rate;
			if (matchDays.get(i).getGamesPlayed() <= 0){
				rate = 0.00;
			}else{
				rate = (100.00/matchDays.get(i).getGamesPlayed())*matchDays.get(i).getGamesWon();	
			}
			values[i] = String.valueOf(rate);
		}
		return values;
	}
	
	public String[] getLooseRateInPercentForEachMatchDay() {
		String[] values = new String[matchDays.size()];
		for (int i = 0; i < matchDays.size(); i++){
			Double rate;
			if (matchDays.get(i).getGamesPlayed() <= 0){
				rate = 0.00;
			}else{
				rate = (100.00/matchDays.get(i).getGamesPlayed())*(matchDays.get(i).getGamesPlayed()-matchDays.get(i).getGamesWon());	
			}
			values[i] = String.valueOf(rate);
		}
		return values;
	}
	
	/**
	 * @return the matchDays
	 */
	public List<MatchDay> getMatchDays() {
		return matchDays;
	}

	
}
