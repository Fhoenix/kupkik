package com.kupkik.persistence.common;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.kupkik.model.Season;

public class PFCommonSaver {

	/**
	 * Saves a new Tournament
	 * @param pTournamentName Tournament Name
	 * @param seasonKey The Parent key refering to the {@link Season}
	 */
    public static void saveNewTournament( final String pTournamentName, final Key seasonKey )
	{
	    Entity tournament = new Entity("Tournament", pTournamentName, seasonKey);
	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    datastore.put(tournament);
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
	 * Saves a new tournament to the database.
	 * 
	 * @param pTournamentName
	 *            the name of the new tournament
	 * @param pUserName
	 *            the name of the user, who creates the tournament
	 */
	public static void saveNewSeason( final String pSeasonName, final String pUserName )
	{
	    Key userKey = KeyFactory.createKey("User", pUserName);
	    Entity tournament = new Entity("Season", pSeasonName, userKey);
	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    datastore.put(tournament);
	}
}
