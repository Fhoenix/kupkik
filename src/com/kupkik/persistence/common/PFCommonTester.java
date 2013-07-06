package com.kupkik.persistence.common;

import java.util.logging.Logger;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.kupkik.model.Season;
import com.kupkik.ui.html.HtmlStarterServlet;

public class PFCommonTester {
	private static final Logger sLogger = Logger.getLogger(HtmlStarterServlet.class.getName());


	/**
	 * Does a tournament with a specific name exist?
	 * 
	 * @param pTournamentName
	 *            the name of the tournament in question
	 * @return 'true', if a tournament with this name does exist
	 */
	public static boolean doesTournamentExistWithName( final String pTournamentName )
	{
		sLogger.info("doesTournamentExistWithName: " + pTournamentName);
		Key key = KeyFactory.createKey("Tournament", pTournamentName);

		try
		{
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			datastore.get(key);
		}
		catch( EntityNotFoundException e )
		{
			sLogger.info("tournament does NOT exist: " + pTournamentName);
			return false;
		}

		sLogger.info("tournament does exist: " + pTournamentName);
		return true;
	}
	
	/**
	 * Checks if the Season with the given name already exists.
	 * @param seasonName Name of the {@link Season}
	 * @return true or false
	 */
	public static boolean doesSeasonExist(String seasonName) {
		sLogger.info("doesExistWithKey: " + seasonName);
		Key key = KeyFactory.createKey("Season", seasonName);

		try
		{
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			datastore.get(key);

		}
		catch( EntityNotFoundException e )
		{
			sLogger.info("tournament does NOT exist: " + key.getName());
			return false;
		}

		sLogger.info("tournament does exist: " + key.getName());
		return true;
	}

	/**
	 * Does the user exist?
	 * 
	 * @param pUserName
	 *            the name of the user
	 * @param pPasswordMd5
	 *            the password (md5) of the user
	 * @return 'true', if the user exists, otherwise 'false'
	 */
	public static boolean doesUserExistWithNameAndMd5Password( final String pUserName, final String pPasswordMd5 )
	{
		sLogger.info("doesUserExistWithNameAndMd5Password: " + pUserName + ", " + pPasswordMd5);
		Key key = KeyFactory.createKey("User", pUserName);

		Entity user = null;

		try
		{
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			user = datastore.get(key);
		}
		catch( EntityNotFoundException e )
		{
			sLogger.info("user does NOT exist: " + pUserName);
			return false;
		}

		if( !user.getProperty("passwordMd5").equals(pPasswordMd5) )
		{
			return false;
		}

		return true;
	}

	/**
	 * Does the user exist?
	 * 
	 * @param pUserName
	 *            The name of the user.
	 * @return 'true', if the user exists, otherwise 'false'
	 */
	public static boolean doesUserExistWithName( final String pUserName )
	{
		sLogger.info("doesUserExistWithName: " + pUserName);
		Key key = KeyFactory.createKey("User", pUserName);

		try
		{
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			datastore.get(key);
		}
		catch( EntityNotFoundException e )
		{
			sLogger.info("user does NOT exist: " + pUserName);
			return false;
		}

		sLogger.info("user does exist: " + pUserName);
		return true;
	}

}
