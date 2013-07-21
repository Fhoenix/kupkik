package com.kupkik.applicationcore;

import java.util.Date;
import java.util.List;


import com.google.appengine.api.datastore.Key;
import com.kupkik.model.DisplaySkillGraph;
import com.kupkik.model.Season;
import com.kupkik.model.MatchDay;
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
	public static final int   MAX_FIRSTNAME_SIZE       = 50;
	public static final int   MAX_SURNAME_SIZE       = 50;
	public static final int   MIN_PASSWORD_SIZE        = 3;

	public static final int   MAX_MATCHDAY_NAME_SIZE = 50;
	public static final int   MIN_MATCHDAY_NAME_SIZE = 5;

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
		USER_NAME_TOO_SHORT,
		USER_NAME_TOO_LONG,
		USER_NAME_USES_INVALID_CHARACTERS,
		FIRSTNAME_INVALID,
		SURNAME_INVALID,
		PASSWORD_INVALID
	}

	/**
	 * The result of trying to create a new matchday.
	 */
	public enum CreateMatchDayAnswer
	{
		OK,
		NAME_INVALID,
		MATCHDAY_ALREADY_EXISTS,
		SEASON_DOES_NOT_EXIST, 
		USER_CREDENTIALS_INVALID, MATCHDAY_DOES_NOT_EXIST
	}


	/**
	 * The result of trying to create a new Season.
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




	public CreateGameAnswer createBadmintonSingleGame(final Key matchDayKey, final Key playerOne, final Key playerTwo, final int resultOne, final int resultTwo, final Date date){
		PFBadmintonSaver.saveNewBadmintonSingleGame(matchDayKey, playerOne, playerTwo, resultOne, resultTwo, date);
		return CreateGameAnswer.OK;
	}

	public CreateGameAnswer createBadmintonDoubleGame(final Key matchDayKey, final List<Key> team1, final List<Key> team2, final int resultOne, final int resultTwo, final Date date){
		PFBadmintonSaver.saveNewBadmintonDoubleGame(matchDayKey, team1, team2, resultOne, resultTwo, date);
		return CreateGameAnswer.OK;
	}

	/**
	 * create a new matchday
	 * 
	 * @param pSeasonName
	 *            pSeasonName
	 * @param pUserName pUserName
	 * @param pUserPasswordMd5 pUserPasswordMd5
	 * @return the result of trying to create a matchday
	 */
	public CreateSeasonAnswer createSeason( final String pSeasonName, final String pUserName, final String pUserPasswordMd5 )
	{
		if( pSeasonName.length() > MAX_MATCHDAY_NAME_SIZE )
		{
			return CreateSeasonAnswer.NAME_INVALID;
		}
		if( pSeasonName.length() < MIN_MATCHDAY_NAME_SIZE )
		{
			return CreateSeasonAnswer.NAME_INVALID;
		}
		if( !pSeasonName.matches("[0-9a-zA-Z_@]*") )
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
	 * create a new MatchDay
	 * 
	 * 
	 * @param matchDayName
	 *            the name of the new matchday
	 * @return the result of trying to create a matchday
	 */
	public CreateMatchDayAnswer createMatchDay( final String matchDayName, Key seasonKey )
	{
		if( matchDayName.length() > MAX_MATCHDAY_NAME_SIZE )
		{
			return CreateMatchDayAnswer.NAME_INVALID;
		}
		if( matchDayName.length() < MIN_MATCHDAY_NAME_SIZE )
		{
			return CreateMatchDayAnswer.NAME_INVALID;
		}
		if( !matchDayName.matches("[0-9a-zA-Z_ ]*") )
		{
			return CreateMatchDayAnswer.NAME_INVALID;
		}

		if( PFCommonTester.doesMatchDayExistWithName(matchDayName) )
		{
			return CreateMatchDayAnswer.MATCHDAY_ALREADY_EXISTS;
		}

		if( !doesSeasonExists(seasonKey.getName()))
		{
			return CreateMatchDayAnswer.MATCHDAY_DOES_NOT_EXIST;
		}

		PFCommonSaver.saveNewMatchDay(matchDayName, seasonKey);

		return CreateMatchDayAnswer.OK;
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
	public SaveUserAnswer saveNewUser( final String pUserName, final String pUserPassword, final String firstname, final String surname)
	{
		// does the user exist already?
		if( doesUserExistWithName(pUserName) ){
			return SaveUserAnswer.USER_ALREADY_EXISTS;
		}

		if (isUserNameTooShort(pUserName)){
			return SaveUserAnswer.USER_NAME_TOO_SHORT;
		}

		if (isUserNameTooLong(pUserName)){
			return SaveUserAnswer.USER_NAME_TOO_LONG;
		}

		// is the user-name valid?
				if( !isUserNameCharctersValid(pUserName) ){
					return SaveUserAnswer.USER_NAME_USES_INVALID_CHARACTERS;
				}

				if( firstname.length() > MAX_FIRSTNAME_SIZE ){
					return SaveUserAnswer.FIRSTNAME_INVALID ;
				}

				if( surname.length() > MAX_SURNAME_SIZE ){
					return SaveUserAnswer.SURNAME_INVALID ;
				}

				// is the password valid?
						if( !isPasswordValid(pUserPassword) )
						{
							return SaveUserAnswer.PASSWORD_INVALID;
						}

						// everything is OK => save the user

						String passwordMd5 = CredentialsUtils.getMd5HashForText(pUserPassword);

						PFCommonSaver.saveNewUser(pUserName, passwordMd5, firstname, surname);

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

	private boolean isUserNameTooShort(final String pUserName){
		if( pUserName.length() < MIN_USER_NAME_SIZE )
		{
			return true;
		}
		return false;
	}

	private boolean isUserNameTooLong(String pUserName) {
		if( pUserName.length() > MAX_USER_NAME_SIZE )
		{
			return true;
		}
		return false;
	}

	private boolean isUserNameCharctersValid( final String pUserName )
	{

		if( EmailValidator.validate(pUserName) )
		{
			return true;
		}

		return false;
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
	  * get all matchday in database
	  * 
	  * @return all MatchDays, not ordered
	  */
	 public List<MatchDay> getAllMatchDays()
	 {
		 return PFCommonGetter.getAllMatchDays();
	 }

	 public List<Season> getAllSeasonsForUser(UserWithPassword currentUser) {
		 return PFCommonGetter.getAllSeasonsForUser(currentUser.getName());
	 }

	 public UserWithPassword getUserWithPasswordByName(String currentUser) {
		 return PFCommonGetter.getUserByName(currentUser);
	 }

	 public List<MatchDay> getAllMatchDaysOfUser(String userName) {
		 return PFCommonGetter.getAllMatchDaysOfUser(userName);
	 }

	 public  static DisplaySkillGraph getAllBadmintonSingleGamesInSeason(Key season, Key userName){
		 return PFBadmintonGetters.getAllBadmintonSingleGamesInSeason( season,  userName);
	 }

	 public  static DisplaySkillGraph getAllBadmintonDoubleGamesInSeason(Key season, Key userName){
		 return PFBadmintonGetters.getAllBadmintonDoubleGamesInSeason( season,  userName);
	 }

	 /** Returns all Seasons in a List<Seasons> */

	 public List<Season> getAllSeasons() {
		 return PFCommonGetter.getAllSeasons();
	 }

}
