package com.kupkik.applicationcore;

import com.kupkik.persistence.PersistenceFacade;
import com.kupkik.utils.CredentialsUtils;

/**
 * The application-core. It may be accessed by the client via any kind of
 * technology (which is build on top of the application-core), like HTML, REST
 * or RSS. Because of that, the application-core has enforce the
 * application-rules, like validation.
 */
public class ApplicationCoreFacade
{
    public static final int   MIN_USER_NAME_SIZE = 3;
    public static final int   MAX_USER_NAME_SIZE = 50;
    public static final int   MIN_PASSWORD_SIZE  = 3;

    private PersistenceFacade mPersistenceFacade;

    public ApplicationCoreFacade(final PersistenceFacade pPersistenceFacade)
    {
        mPersistenceFacade = pPersistenceFacade;
    }

    /**
     * The result of trying to save a user in the database.
     */
    public enum SaveUserAnswer
    {
        OK,
        USER_ALREADY_EXISTS,
        USER_NAME_INVALID,
        PASSWORD_INVALID
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
        return mPersistenceFacade.doesUserExistWithName(pUserName);
    }

    /**
     * Save a new user in the database.
     * 
     * @param pUserName
     *            the name of the new user
     * @param pUserPassword
     *            The password of the new user. The password will be saved as a
     *            MD5-hash.
     * @return The result of trying to save the user in the database.
     */
    public SaveUserAnswer saveNewUser( final String pUserName, final String pUserPassword )
    {
        // does the user exist already?

        if( doesUserExistWithName(pUserName) )
        {
            return SaveUserAnswer.USER_ALREADY_EXISTS;
        }

        // is the user-name valid?

        if( !isUsernameValid(pUserName) )
        {
            return SaveUserAnswer.USER_NAME_INVALID;
        }

        // is the password valid?

        if( !isPasswordValid(pUserPassword) )
        {
            return SaveUserAnswer.PASSWORD_INVALID;
        }

        // everything is OK => save the user

        String passwordMd5 = CredentialsUtils.getMd5HashForText(pUserPassword);

        mPersistenceFacade.saveNewUser(pUserName, passwordMd5);

        return SaveUserAnswer.OK;
    }

    private boolean isPasswordValid( final String pPassword )
    {
        if( pPassword.length() < MIN_PASSWORD_SIZE )
        {
            return false;
        }

        return true;
    }

    private boolean isUsernameValid( final String pUserName )
    {
        if( pUserName.length() < MIN_USER_NAME_SIZE )
        {
            return false;
        }
        if( pUserName.length() > MAX_USER_NAME_SIZE )
        {
            return false;
        }
        if( !pUserName.matches("[0-9a-zA-Z_]*") )
        {
            return false;
        }

        return true;
    }

}
