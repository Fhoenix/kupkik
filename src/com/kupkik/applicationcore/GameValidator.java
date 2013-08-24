package com.kupkik.applicationcore;

import java.util.List;

import com.google.appengine.api.datastore.Key;

public class GameValidator {

	
	
	public static boolean  anyUserSelectedTwice(List<Key> team1, List<Key> team2){
		for (Key key1 : team1) {
			for (Key key2 : team2){
				if(key1.equals(key2)){
					return true;
				}
			}
			
		}
		return false;
	}
}
