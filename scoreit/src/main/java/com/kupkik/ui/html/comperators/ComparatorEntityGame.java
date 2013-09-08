package com.kupkik.ui.html.comperators;

import java.util.Comparator;
import java.util.Date;

import com.google.appengine.api.datastore.Entity;
import com.kupkik.persistence.PropertiesStore;

public class ComparatorEntityGame implements Comparator<Entity>{

	@Override
	public int compare(Entity o1, Entity o2) {
		// TODO Auto-generated method stub
		

			Date date1 = (Date) o1.getProperty(PropertiesStore.DATE.toString());
			Date date2 = (Date) o2.getProperty(PropertiesStore.DATE.toString());
			
			return date1.compareTo(date2);
		
	}

}