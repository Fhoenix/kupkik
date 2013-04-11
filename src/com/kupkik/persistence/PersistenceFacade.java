package com.kupkik.persistence;

import java.util.logging.Logger;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.kupkik.ui.html.HtmlStarterServlet;

/**
 * Provides access to the database. Only performs stupid CRUD operations,
 * without any validation. Validation is done in the application-core.
 */
public class PersistenceFacade
{
    private static final Logger sLogger = Logger.getLogger(HtmlStarterServlet.class.getName());

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
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Key key = KeyFactory.createKey("User", pUserName);

        try
        {
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
