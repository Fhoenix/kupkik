package com.kupkik.persistence.badminton;

import java.text.SimpleDateFormat;
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




	public static void saveGame(final Key seasonKey, final List<Key> playerOne, final List<Key> playerTwo, final int resultOne, final int resultTwo, final Date date, String gameType){
	
		Entity badmintonSingleGame = new Entity("Game", seasonKey);
		
		badmintonSingleGame.setProperty("teamOne", playerOne);
		badmintonSingleGame.setProperty("teamTwo", playerTwo);
		badmintonSingleGame.setProperty("resultOne", resultOne);
		badmintonSingleGame.setProperty("resultTwo", resultTwo);
		badmintonSingleGame.setProperty("gameType", gameType);
		
		  // create SimpleDateFormat object with desired date format
        SimpleDateFormat sdfDestination = new SimpleDateFormat(
                "dd-MM-yyyy");

        // parse the date into another format
        String strDate = sdfDestination.format(date);
		badmintonSingleGame.setProperty("date", strDate);
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    datastore.put(badmintonSingleGame);
		
	}

}
