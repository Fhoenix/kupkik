package com.kupkik.persistence.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.kupkik.model.Season;
import com.kupkik.persistence.EntityNameStore;
import com.kupkik.persistence.PropertiesStore;

public class PFCommonSaver {


    /**
	 * Save a new user in the database.
	 * 
	 * @param pUserName
	 *            the name of the new user
	 * @param pUserPassword
	 *            The password of the new user as a MD5 hash.
	 */
	public static void saveNewUser( final String pUserName, final String pUserPasswordMd5, final String firstname, final String surname )
	{
	    Entity user = new Entity(EntityNameStore.USER.toString(), pUserName);
	    user.setProperty(PropertiesStore.PASSWORD_MD5.toString(), pUserPasswordMd5);
	    user.setProperty(PropertiesStore.FIRSTNAME.toString(), firstname);
	    user.setProperty(PropertiesStore.SURNAME.toString(), surname);
	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    datastore.put(user);
	}
	

	public static void saveGame(final Key seasonKey, final List<Key> playerOne, final List<Key> playerTwo, final int resultOne, final int resultTwo, final Date date, String gameType){
		
		Entity badmintonSingleGame = new Entity(EntityNameStore.GAME.toString(), seasonKey);
		
		badmintonSingleGame.setProperty(PropertiesStore.TEAM_ONE.toString(), playerOne);
		badmintonSingleGame.setProperty(PropertiesStore.TEAM_TWO.toString(), playerTwo);
		badmintonSingleGame.setProperty(PropertiesStore.RESULT_ONE.toString(), resultOne);
		badmintonSingleGame.setProperty(PropertiesStore.RESULT_TWO.toString(), resultTwo);
		badmintonSingleGame.setProperty(PropertiesStore.GAMETYPE.toString(), gameType);
		
		  // create SimpleDateFormat object with desired date format
        SimpleDateFormat sdfDestination = new SimpleDateFormat(
                "dd-MM-yyyy");

        // parse the date into another format
        String strDate = sdfDestination.format(date);
		badmintonSingleGame.setProperty("date", strDate);
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    datastore.put(badmintonSingleGame);
		
	}


	/**
	 * Saves a new Season to the database.
	 * 
	 * @param pSeasonName
	 *            the name of the new pSeasonName
	 * @param pUserName
	 *            the name of the user, who creates the season
	 */
	public static void saveNewSeason( final String pSeasonName, final String pUserName, final String gameType , final List<String> usersAllowedToEditSeason)
	{
	    Key userKey = KeyFactory.createKey(EntityNameStore.USER.toString(), pUserName);
	    Entity season = new Entity(EntityNameStore.SEASON.toString(), pSeasonName, userKey);
	    season.setProperty(PropertiesStore.GAMETYPE.toString(), gameType);
	    season.setProperty(PropertiesStore.USERS_ALLOWED_TO_EDIT.toString(), usersAllowedToEditSeason);
	    
	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    datastore.put(season);
	}
}
