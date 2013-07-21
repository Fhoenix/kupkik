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
	public static final int MIN_USER_NAME_SIZE       	= 3;
	public static final int MAX_USER_NAME_SIZE       	= 50;
	public static final int MAX_FIRSTNAME_SIZE       	= 50;
	public static final int MAX_SURNAME_SIZE       		= 50;
	public static final int MIN_PASSWORD_SIZE        	= 3;

	public static final int MAX_MATCHDAY_NAME_SIZE		= 50;
	public static final int MIN_MATCHDAY_NAME_SIZE 		= 5;

	public static final int MAX_SEASON_NAME_SIZE 		= 50;
	public static final int MIN_SEASON_NAME_SIZE 		= 5;

	// Badminton Validation Rules
	public static final int	MAX_WINNING_DISTANCE		= 2;
	public static final int	MIN_END_RESULT					= 21;
	

	public ApplicationCoreFacade()
	{
		//TODO COS MAYBE INSTANCES OF THE FACADES
	}

	/**
	 * The result of trying to create a new user. Prefix: USER_
	 */
	public enum SaveUserAnswer
	{
		USER_OK,
		USER_ALREADY_EXISTS,
		USER_NAME_TOO_SHORT,
		USER_NAME_TOO_LONG,
		USER_NAME_USES_INVALID_CHARACTERS,
		USER_FIRSTNAME_INVALID,
		USER_SURNAME_INVALID,
		USER_PASSWORD_INVALID
	}

	/**
	 * The result of trying to create a new matchday. Prefix: MATCHDAY_
	 */
	public enum CreateMatchDayAnswer
	{
		MATCHDAY_OK,
		MATCHDAY_NAME_INVALID,
		MATCHDAY_ALREADY_EXISTS,
		MATCHDAY_SEASON_DOES_NOT_EXIST, 
		MATCHDAY_USER_CREDENTIALS_INVALID,
		MATCHDAY_DOES_NOT_EXIST
	}


	/**
	 * The result of trying to create a new Season.
	 */
	public enum CreateSeasonAnswer
	{
		SEASON_OK,
		SEASON_NAME_INVALID,
		SEASON_ALREADY_EXISTS,
		SEASON_USER_CREDENTIALS_INVALID
	}


	public enum CreateGameAnswer{
		GAME_OK,
		GAME_NOK
	}
	
	public enum CreateBadmintonSingleGameAnswer{
		BADMINTON_SINGLE_OK,
		BADMINTON_SINGLE_USER_EQUAL_EACH_OTHER,
		BADMINTON_SINGLE_MIN_POINTS_NOT_REACHED,
		BADMINTON_SINGLE_RESULTS_INVALID
	}




	public CreateBadmintonSingleGameAnswer createBadmintonSingleGame(final Key matchDayKey, final Key playerOne, final Key playerTwo, final int resultOne, final int resultTwo, final Date date){
		
		if(playerOne.equals(playerTwo)){
			return CreateBadmintonSingleGameAnswer.BADMINTON_SINGLE_USER_EQUAL_EACH_OTHER;
		}
		//One result must be at least MIN_END_RESULT
		if (!(resultOne >= MIN_END_RESULT) && !(resultTwo >= MIN_END_RESULT)){
			return CreateBadmintonSingleGameAnswer.BADMINTON_SINGLE_MIN_POINTS_NOT_REACHED;
		//Distance must be bigger than MAX_WINNING_DISTANCE
		}else if (!(Math.abs(resultOne-resultTwo) >= MAX_WINNING_DISTANCE)){
			return CreateBadmintonSingleGameAnswer.BADMINTON_SINGLE_RESULTS_INVALID;
		//In case one of both results is >= MIN_END_RESULT - abs of difference of both results must be exactly MAX_WINNING_DISTANCE
		}else if ((resultOne > MIN_END_RESULT || resultTwo > MIN_END_RESULT ) && !(Math.abs(resultOne-resultTwo) == MAX_WINNING_DISTANCE)){
			return CreateBadmintonSingleGameAnswer.BADMINTON_SINGLE_RESULTS_INVALID;
		}
		PFBadmintonSaver.saveNewBadmintonSingleGame(matchDayKey, playerOne, playerTwo, resultOne, resultTwo, date);
		return CreateBadmintonSingleGameAnswer.BADMINTON_SINGLE_OK;
		
		
	}

	public CreateGameAnswer createBadmintonDoubleGame(final Key matchDayKey, final List<Key> team1, final List<Key> team2, final int resultOne, final int resultTwo, final Date date){
		PFBadmintonSaver.saveNewBadmintonDoubleGame(matchDayKey, team1, team2, resultOne, resultTwo, date);
		return CreateGameAnswer.GAME_OK;
	}

	/**
	 * create a new Season
	 * 
	 * @param pSeasonName
	 *            pSeasonName
	 * @param pUserName pUserName
	 * @param pUserPasswordMd5 pUserPasswordMd5
	 * @return the result of trying to create a Season
	 */
	public CreateSeasonAnswer createSeason( final String pSeasonName, final String pUserName, final String pUserPasswordMd5 )
	{
		if( pSeasonName.length() > MAX_MATCHDAY_NAME_SIZE )
		{
			return CreateSeasonAnswer.SEASON_NAME_INVALID;
		}
		if( pSeasonName.length() < MIN_MATCHDAY_NAME_SIZE )
		{
			return CreateSeasonAnswer.SEASON_NAME_INVALID;
		}
		if( !pSeasonName.matches("[0-9a-zA-Z_@]*") )
		{
			return CreateSeasonAnswer.SEASON_NAME_INVALID;
		}

		if( PFCommonTester.doesSeasonExist(pSeasonName) )
		{
			return CreateSeasonAnswer.SEASON_ALREADY_EXISTS;
		}

		if( !doesUserExistWithNameAndMd5Password(pUserName, pUserPasswordMd5))
		{
			return CreateSeasonAnswer.SEASON_USER_CREDENTIALS_INVALID;
		}

		PFCommonSaver.saveNewSeason(pSeasonName, pUserName);

		return CreateSeasonAnswer.SEASON_OK;
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
			return CreateMatchDayAnswer.MATCHDAY_NAME_INVALID;
		}
		if( matchDayName.length() < MIN_MATCHDAY_NAME_SIZE )
		{
			return CreateMatchDayAnswer.MATCHDAY_NAME_INVALID;
		}
		if( !matchDayName.matches("[0-9a-zA-Z_ ]*") )
		{
			return CreateMatchDayAnswer.MATCHDAY_NAME_INVALID;
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

		return CreateMatchDayAnswer.MATCHDAY_OK;
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
					return SaveUserAnswer.USER_FIRSTNAME_INVALID ;
				}

				if( surname.length() > MAX_SURNAME_SIZE ){
					return SaveUserAnswer.USER_SURNAME_INVALID ;
				}

				// is the password valid?
						if( !isPasswordValid(pUserPassword) )
						{
							return SaveUserAnswer.USER_PASSWORD_INVALID;
						}

						// everything is OK => save the user

						String passwordMd5 = CredentialsUtils.getMd5HashForText(pUserPassword);

						PFCommonSaver.saveNewUser(pUserName, passwordMd5, firstname, surname);

						return SaveUserAnswer.USER_OK;
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
