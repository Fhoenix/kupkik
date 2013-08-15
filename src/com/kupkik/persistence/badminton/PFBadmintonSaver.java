package com.kupkik.persistence.badminton;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.kupkik.persistence.EntityNameStore;

public class PFBadmintonSaver {




	public static void saveGame(final Key matchDayKey, final List<Key> playerOne, final List<Key> playerTwo, final int resultOne, final int resultTwo, final Date date, String gameType){
	
		Entity badmintonSingleGame = new Entity("Game", matchDayKey);
		
		badmintonSingleGame.setProperty("teamOne", playerOne);
		badmintonSingleGame.setProperty("teamTwo", playerTwo);
		badmintonSingleGame.setProperty("resultOne", resultOne);
		badmintonSingleGame.setProperty("resultTwo", resultTwo);
		badmintonSingleGame.setProperty("gameType", gameType);
		
		badmintonSingleGame.setProperty("date", date);
		   DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	       datastore.put(badmintonSingleGame);
		
	}

}
