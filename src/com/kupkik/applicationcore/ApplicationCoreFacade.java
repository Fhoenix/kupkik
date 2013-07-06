package com.kupkik.applicationcore;

import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.Key;
import com.kupkik.model.Season;
import com.kupkik.model.Tournament;
import com.kupkik.model.UserWithPassword;
import com.kupkik.model.game.BadmintonSingle;
import com.kupkik.persistence.badminton.PFBadmintonGetters;
import com.kupkik.persistence.badminton.PFBadmintonSaver;
import com.kupkik.persistence.common.PFCommonGetter;
import com.kupkik.persistence.common.PFCommonSaver;
import com.kupkik.persistence.common.PFCommonTester;
import com.kupkik.utils.CredentialsUtils;

/**
 * The application-core. It may be accessed by the client via any kind of
 * technology (which is build on top of the application-core), like HTML, REST
 * or RSS. Because of that, the application-core has enforce the
 * application-rules, like validation.
 */
public class ApplicationCoreFacade
{
    public static final int   MIN_USER_NAME_SIZE       = 3;
    public static final int   MAX_USER_NAME_SIZE       = 50;
    public static final int   MIN_PASSWORD_SIZE        = 3;

    public static final int   MAX_TOURNAMENT_NAME_SIZE = 50;
    public static final int   MIN_TOURNAMENT_NAME_SIZE = 5;
    
    public static final int   MAX_SEASON_NAME_SIZE = 50;
    public static final int   MIN_SEASON_NAME_SIZE = 5;



    public ApplicationCoreFacade()
    {
//TODO COS MAYBE INSTANCES OF THE FACADES
    }

    /**
     * The result of trying to create a new user.
     */
    public enum SaveUserAnswer
    {
        OK,
        USER_ALREADY_EXISTS,
        USER_NAME_INVALID,
        PASSWORD_INVALID
    }

    /**
     * The result of trying to create a new tournament.
     */
    public enum CreateTournamentAnswer
    {
        OK,
        NAME_INVALID,
        TOURNAMENT_ALREADY_EXISTS,
        SEASON_DOES_NOT_EXIST, 
        USER_CREDENTIALS_INVALID, TOURNAMENT_DOES_NOT_EXIST
    }

    
    /**
     * The result of trying to create a new tournament.
     */
    public enum CreateSeasonAnswer
    {
        OK,
        NAME_INVALID,
        SEASON_ALREADY_EXISTS,
        USER_CREDENTIALS_INVALID
    }

    
    public enum CreateGameAnswer{
    	OK,
    	NOK
    }
    
    
    
   
    public CreateGameAnswer createBadmintonSingleGame(final String pTournamentName, final String playerOne, final String playerTwo, final int resultOne, final int resultTwo, final Date date){
    	PFBadmintonSaver.saveNewBadmintonSingleGame(pTournamentName, playerOne, playerTwo, resultOne, resultTwo, date);
        return CreateGameAnswer.OK;
    }
    
    /**
     * create a new tournament
     * 
     * @param pTournamentName
     *            the name of the new tournament
     * @return the result of trying to create a tournament
     */
    public CreateSeasonAnswer createSeason( final String pSeasonName, final String pUserName, final String pUserPasswordMd5 )
    {
        if( pSeasonName.length() > MAX_TOURNAMENT_NAME_SIZE )
        {
            return CreateSeasonAnswer.NAME_INVALID;
        }
        if( pSeasonName.length() < MIN_TOURNAMENT_NAME_SIZE )
        {
            return CreateSeasonAnswer.NAME_INVALID;
        }
        if( !pSeasonName.matches("[0-9a-zA-Z_ ]*") )
        {
            return CreateSeasonAnswer.NAME_INVALID;
        }

        if( PFCommonTester.doesSeasonExist(pSeasonName) )
        {
            return CreateSeasonAnswer.SEASON_ALREADY_EXISTS;
        }
        
        if( !doesUserExistWithNameAndMd5Password(pUserName, pUserPasswordMd5))
        {
            return CreateSeasonAnswer.USER_CREDENTIALS_INVALID;
        }

        PFCommonSaver.saveNewSeason(pSeasonName, pUserName);

        return CreateSeasonAnswer.OK;
    }
    
    
    /**
     * create a new tournament
     * 
     * @param pTournamentName
     *            the name of the new tournament
     * @return the result of trying to create a tournament
     */
    public CreateTournamentAnswer createTournament( final String pTournamentName, Key seasonKey )
    {
        if( pTournamentName.length() > MAX_TOURNAMENT_NAME_SIZE )
        {
            return CreateTournamentAnswer.NAME_INVALID;
        }
        if( pTournamentName.length() < MIN_TOURNAMENT_NAME_SIZE )
        {
            return CreateTournamentAnswer.NAME_INVALID;
        }
        if( !pTournamentName.matches("[0-9a-zA-Z_ ]*") )
        {
            return CreateTournamentAnswer.NAME_INVALID;
        }

        if( PFCommonTester.doesTournamentExistWithName(pTournamentName) )
        {
            return CreateTournamentAnswer.TOURNAMENT_ALREADY_EXISTS;
        }
        
        if( !doesSeasonExists(seasonKey.getName()))
        {
            return CreateTournamentAnswer.TOURNAMENT_DOES_NOT_EXIST;
        }

        PFCommonSaver.saveNewTournament(pTournamentName, seasonKey);

        return CreateTournamentAnswer.OK;
    }

    private boolean doesSeasonExists(String seasonName) {
		// TODO COS IMPLEMENT METHOD
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
        return PFCommonTester.doesUserExistWithNameAndMd5Password(pUserName, pPasswordMd5);
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
    public boolean doesSeasonExist( final String seasonName )
    {
        return PFCommonTester.doesSeasonExist(seasonName);
    }
    

    /**
     * Does the user exist?
     * 
     * @param pUserName
     *            the name of the user
     * @return 'true', if the user exists, otherwise 'false'
     */
    public boolean doesUserExistWithName( final String pUserName )
    {
        return PFCommonTester.doesUserExistWithName(pUserName);
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

        PFCommonSaver.saveNewUser(pUserName, passwordMd5);

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

	/**
	 * get all users in database
	 * 
	 * @return all users, not ordered
	 */
	public List<UserWithPassword> getAllUsers()
	{
	    return PFCommonGetter.getAllUsers();
	}

	public List<BadmintonSingle> getLatestBadmintonSingleGames(int count){
		return PFBadmintonGetters.getLatestBadmintonSingleGames(count);
	}

	/**
	 * get all tournaments in database
	 * 
	 * @return all tournaments, not ordered
	 */
	public List<Tournament> getAllTournaments()
	{
	    return PFCommonGetter.getAllTournaments();
	}

	public List<Season> getAllSeasonsForUser(UserWithPassword currentUser) {
		return PFCommonGetter.getAllSeasonsForUser(currentUser.getName());
	}

	public UserWithPassword getUserWithPasswordByName(String currentUser) {
		return PFCommonGetter.getUserByName(currentUser);
	}

	public List<Tournament> getAllTournamentsOfUser(String userName) {

		return PFCommonGetter.getAllTournamentsOfUser(userName);
	}

}
