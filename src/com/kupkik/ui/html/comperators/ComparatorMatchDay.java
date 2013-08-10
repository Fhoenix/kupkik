package com.kupkik.ui.html.comperators;

import java.util.Comparator;

import com.kupkik.model.MatchDay;

public class ComparatorMatchDay implements Comparator<MatchDay>{
	@Override
	public int compare( MatchDay matchDay1, MatchDay matchDay2 )
	{
		return matchDay1.getName().compareTo(matchDay2.getName());
	}
}