package com.kupkik.persistence.badminton;

import java.util.Arrays;
import java.util.Date;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class PFBadmintonSaver {




	public static void saveNewBadmintonSingleGame(final Key pTournamentKey, final String playerOne, final String playerTwo, final int resultOne, final int resultTwo, final Date date){
	
		Entity badmintonSingleGame = new Entity("BadmintonSingle", pTournamentKey);
		
		badmintonSingleGame.setProperty("playerOne", playerOne);
		badmintonSingleGame.setProperty("playerTwo", playerTwo);
		badmintonSingleGame.setProperty("resultOne", resultOne);
		badmintonSingleGame.setProperty("resultTwo", resultTwo);
		
		badmintonSingleGame.setProperty("date", date);
		   DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	       datastore.put(badmintonSingleGame);
		
	}
	
	
	public static void saveNewBadmintonDoubleGame(final Key pTournamentKey, final String[] teamOne, final String[] teamTwo, final int resultOne, final int resultTwo, final Date date){
		
		Entity badmintonSingleGame = new Entity("BadmintonDouble", pTournamentKey);
		
		badmintonSingleGame.setProperty("teamOne", Arrays.asList(teamOne));
		badmintonSingleGame.setProperty("teamTwo", Arrays.asList(teamTwo));
		badmintonSingleGame.setProperty("resultOne", resultOne);
		badmintonSingleGame.setProperty("resultTwo", resultTwo);
		
		badmintonSingleGame.setProperty("date", date);
		   DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	       datastore.put(badmintonSingleGame);
		
	}
}
