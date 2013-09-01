package com.kupkik.ui.html.comperators;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import com.kupkik.model.MatchDay;

public class ComparatorMatchDay implements Comparator<MatchDay>{
	@Override
	public int compare( MatchDay matchDay1, MatchDay matchDay2 )
	{
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		Date m1date = null;
		Date m2date= null;
		try {
			m1date = df.parse(matchDay1.getName());
			m2date = df.parse(matchDay2.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}    
		return m1date.compareTo(m2date) > 0 ? 1 : 0;



	}
}