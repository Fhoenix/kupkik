package com.kupkik.model.ranking;

import java.util.List;

public class WinLooseRanking {

	List<TypedWinLooseRanking> overallRanking;

	public WinLooseRanking(List<TypedWinLooseRanking> overallRanking) {
		super();
		this.overallRanking = overallRanking;
	}
	/**
	 * @return the overallRanking
	 */
	public List<TypedWinLooseRanking> getOverallRanking() {
		return overallRanking;
	}

}

