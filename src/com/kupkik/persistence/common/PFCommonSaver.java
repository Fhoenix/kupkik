package com.kupkik.persistence.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.jasper.tagplugins.jstl.core.ForEach;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
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

		Entity saveGame = new Entity(EntityNameStore.GAME.toString(), seasonKey);

		saveGame.setProperty(PropertiesStore.TEAM_ONE.toString(), playerOne);
		saveGame.setProperty(PropertiesStore.TEAM_TWO.toString(), playerTwo);
		saveGame.setProperty(PropertiesStore.RESULT_ONE.toString(), resultOne);
		saveGame.setProperty(PropertiesStore.RESULT_TWO.toString(), resultTwo);
		saveGame.setProperty(PropertiesStore.GAMETYPE.toString(), gameType);


		// parse the date into another format
		saveGame.setProperty("date", date);

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		datastore.put(saveGame);

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


	public static void updateNewSeason(List<String> usersAllowedToEditSeason,Key seasonKey) {

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter keyFilter = new FilterPredicate(Entity.KEY_RESERVED_PROPERTY, FilterOperator.EQUAL, seasonKey);
		Query getUserQuery = new Query(EntityNameStore.SEASON.toString()).setFilter(keyFilter);
		PreparedQuery getUser = datastore.prepare(getUserQuery);


		List<Entity> userEntity = getUser.asList(FetchOptions.Builder.withLimit(1));

		for (Entity entity : userEntity) {
			entity.setProperty(PropertiesStore.USERS_ALLOWED_TO_EDIT.toString(), usersAllowedToEditSeason);
			datastore.put(entity);
		}


	}


	public static void setNewUserPassword(Key user, String password) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter keyFilter = new FilterPredicate(Entity.KEY_RESERVED_PROPERTY, FilterOperator.EQUAL, user);
		Query getUserQuery = new Query(EntityNameStore.USER.toString()).setFilter(keyFilter);
		PreparedQuery getUser = datastore.prepare(getUserQuery);


		List<Entity> userEntity = getUser.asList(FetchOptions.Builder.withLimit(1));

		for (Entity entity : userEntity) {

			entity.setProperty(PropertiesStore.PASSWORD_MD5.toString(), password);
			datastore.put(entity);
		}



	}
}
