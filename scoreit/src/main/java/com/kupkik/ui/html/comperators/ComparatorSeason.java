package com.kupkik.ui.html.comperators;

import java.util.Comparator;

import com.kupkik.model.Season;

public class ComparatorSeason implements Comparator<Season>{

	@Override
	public int compare(Season season1, Season season2) {

		return season1.getName().compareTo(season2.getName());
	}

}