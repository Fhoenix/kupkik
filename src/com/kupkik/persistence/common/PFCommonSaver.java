package com.kupkik.persistence.common;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.kupkik.model.Season;
import com.kupkik.persistence.EntityNameStore;

public class PFCommonSaver {

	/**
	 * Saves a new MatchDay
	 * @param matchDayName MatchDay Name
	 * @param seasonKey The Parent key refering to the {@link Season}
	 */
    public static void saveNewMatchDay( final String matchDayName, final Key seasonKey )
	{
	    Entity matchDay = new Entity(EntityNameStore.MATCHDAY, matchDayName, seasonKey);
	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    datastore.put(matchDay);
	}
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
	    Entity user = new Entity("User", pUserName);
	    user.setProperty("passwordMd5", pUserPasswordMd5);
	    user.setProperty("firstname", firstname);
	    user.setProperty("surname", surname);
	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    datastore.put(user);
	}
	

	

	/**
	 * Saves a new Season to the database.
	 * 
	 * @param pSeasonName
	 *            the name of the new pSeasonName
	 * @param pUserName
	 *            the name of the user, who creates the season
	 */
	public static void saveNewSeason( final String pSeasonName, final String pUserName, final List<String> gameType )
	{
	    Key userKey = KeyFactory.createKey(EntityNameStore.USER, pUserName);
	    Entity season = new Entity("Season", pSeasonName, userKey);
	    season.setProperty("GameType", gameType);
	    
	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    datastore.put(season);
	}
}
