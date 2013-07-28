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




	public static void saveNewBadmintonSingleGame(final Key matchDayKey, final List<Key> playerOne, final List<Key> playerTwo, final int resultOne, final int resultTwo, final Date date){
	
		Entity badmintonSingleGame = new Entity(EntityNameStore.BADMINTON_SINGLE_GAME, matchDayKey);
		
		badmintonSingleGame.setProperty("teamOne", playerOne);
		badmintonSingleGame.setProperty("teamTwo", playerTwo);
		badmintonSingleGame.setProperty("resultOne", resultOne);
		badmintonSingleGame.setProperty("resultTwo", resultTwo);
		
		badmintonSingleGame.setProperty("date", date);
		   DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	       datastore.put(badmintonSingleGame);
		
	}
	
	
	public static void saveNewBadmintonDoubleGame(final Key matchDayKey, final List<Key> teamOne, final List<Key> teamTwo, final int resultOne, final int resultTwo, final Date date){
		
		Entity badmintonSingleGame = new Entity(EntityNameStore.BADMINTON_DOUBLE_GAME, matchDayKey);
		
		badmintonSingleGame.setProperty("teamOne", teamOne);
		badmintonSingleGame.setProperty("teamTwo", teamTwo);
		badmintonSingleGame.setProperty("resultOne", resultOne);
		badmintonSingleGame.setProperty("resultTwo", resultTwo);
		
		badmintonSingleGame.setProperty("date", date);
		   DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	       datastore.put(badmintonSingleGame);
		
	}
}
