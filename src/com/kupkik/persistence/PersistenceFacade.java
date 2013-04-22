package com.kupkik.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.kupkik.model.Tournament;
import com.kupkik.model.User;
import com.kupkik.ui.html.HtmlStarterServlet;

/**
 * Provides access to the database. Only performs stupid CRUD operations,
 * without any validation. Validation is done in the application-core.
 */
public class PersistenceFacade
{
    private static final Logger sLogger = Logger.getLogger(HtmlStarterServlet.class.getName());

    /**
     * Saves a new tournament to the database.
     * 
     * @param pTournamentName
     *            the name of the new tournament
     * @param pUserName
     *            the name of the user, who creates the tournament
     */
    public void saveNewTournament( final String pTournamentName, final String pUserName )
    {
        Key userKey = KeyFactory.createKey("User", pUserName);
        Entity user = new Entity("Tournament", pTournamentName, userKey);
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(user);
    }

    /**
     * Does a tournament with a specific name exist?
     * 
     * @param pTournamentName
     *            the name of the tournament in question
     * @return 'true', if a tournament with this name does exist
     */
    public boolean doesTournamentExistWithName( final String pTournamentName )
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
     * Does the user exist?
     * 
     * @param pUserName
     *            the name of the user
     * @param pPasswordMd5
     *            the password (md5) of the user
     * @return 'true', if the user exists, otherwise 'false'
     */
    public boolean doesUserExistWithNameAndMd5Password( final String pUserName, final String pPasswordMd5 )
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
    public boolean doesUserExistWithName( final String pUserName )
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

    /**
     * get all tournaments in database
     * 
     * @return all tournaments, not ordered
     */
    public List<Tournament> getAllTournaments()
    {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query getAllTournamentsQuery = new Query("Tournament");
        PreparedQuery getAllTournamentsPreparedQuery = datastore.prepare(getAllTournamentsQuery);

        List<Entity> userEntities = getAllTournamentsPreparedQuery.asList(FetchOptions.Builder.withDefaults());
        List<Tournament> tournaments = new ArrayList<Tournament>();
        for( Entity currentTournamentEntity : userEntities )
        {
            Tournament currentTournament = new Tournament(currentTournamentEntity.getKey().getName());
            tournaments.add(currentTournament);
        }

        return tournaments;
    }

    public List<User> getAllUsers()
    {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query getAllUsersQuery = new Query("User");
        PreparedQuery getAllUsersPreparedQuery = datastore.prepare(getAllUsersQuery);

        List<Entity> userEntities = getAllUsersPreparedQuery.asList(FetchOptions.Builder.withDefaults());
        List<User> users = new ArrayList<User>();
        for( Entity currentUserEntity : userEntities )
        {
            User currentUser = new User(currentUserEntity.getKey().getName());
            users.add(currentUser);
        }

        return users;
    }

    /**
     * Save a new user in the database.
     * 
     * @param pUserName
     *            the name of the new user
     * @param pUserPassword
     *            The password of the new user as a MD5 hash.
     */
    public void saveNewUser( final String pUserName, final String pUserPasswordMd5 )
    {
        Entity user = new Entity("User", pUserName);
        user.setProperty("passwordMd5", pUserPasswordMd5);
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(user);
    }
}
