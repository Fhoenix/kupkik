package com.kupkik.applicationcore;

import java.util.Date;
import java.util.List;


import com.google.appengine.api.datastore.Key;

import com.kupkik.applicationcore.answers.CreateBadmintonSingleGameAnswer;
import com.kupkik.applicationcore.answers.CreateGameAnswer;
import com.kupkik.applicationcore.answers.CreateSeasonAnswer;
import com.kupkik.applicationcore.answers.SaveUserAnswer;
import com.kupkik.model.DisplaySkillGraph;
import com.kupkik.model.Season;
import com.kupkik.model.MatchDay;
import com.kupkik.model.UserWithPassword;
import com.kupkik.model.game.Game;
import com.kupkik.model.ranking.WinLooseRanking;
import com.kupkik.persistence.EntityNameStore;
import com.kupkik.persistence.GameTypStore;
import com.kupkik.persistence.common.PFCommonGetter;
import com.kupkik.persistence.common.PFCommonSaver;
import com.kupkik.persistence.common.PFCommonTester;
import com.kupkik.persistence.ranking.PFRankingGetters;
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

	
	public static final int MAX_SEASON_NAME_SIZE 		= 50;
	public static final int MIN_SEASON_NAME_SIZE 		= 5;

	
	public static final int	MAX_WINNING_DISTANCE		= 2;
	public static final int	MIN_END_RESULT				= 21;
	

	public ApplicationCoreFacade(){
		//TODO COS MAYBE INSTANCES OF THE FACADES
	}


	/**
	 * Creates Badminton Single Games
	 * @param seasonKey key of the parent (Season)
	 * @param team1	List of team1
	 * @param team2 List of team2
	 * @param result1 Result of team1
	 * @param result2 Result of team2
	 * @param date date
	 * @param gameType the gameType
	 * @return CreateBadmintonSingleGameAnswer
	 */
	public CreateBadmintonSingleGameAnswer createBadmintonSingleGame(final Key seasonKey, final List<Key> team1, final List<Key> team2, final String result1, final String result2, final Date date, String gameType){
		if(team1.equals(team2)){
			return CreateBadmintonSingleGameAnswer.BADMINTON_SINGLE_USER_EQUAL_EACH_OTHER;
		}
		
		
		int parsedResultOne;
		int parsedResultTwo;
		
		try{
			 parsedResultOne = Integer.parseInt(result1);
			 parsedResultTwo = Integer.parseInt(result2);
	
		}catch (NumberFormatException e) {
			return CreateBadmintonSingleGameAnswer.BADMINTON_SINGLE_RESULTS_INVALID;
		}
		
		
		if(parsedResultOne < 0 || parsedResultTwo < 0){
			return CreateBadmintonSingleGameAnswer.BADMINTON_SINGLE_RESULTS_INVALID;
		}
		
		//One result must be at least MIN_END_RESULT
		if (!(parsedResultOne >= MIN_END_RESULT) && !(parsedResultTwo >= MIN_END_RESULT)){
			return CreateBadmintonSingleGameAnswer.BADMINTON_SINGLE_MIN_POINTS_NOT_REACHED;
		//Distance must be bigger than MAX_WINNING_DISTANCE
		}else if (!(Math.abs(parsedResultOne-parsedResultTwo) >= MAX_WINNING_DISTANCE)){
			return CreateBadmintonSingleGameAnswer.BADMINTON_SINGLE_RESULTS_INVALID;
		//In case one of both results is >= MIN_END_RESULT - abs of difference of both results must be exactly MAX_WINNING_DISTANCE
		}else if ((parsedResultOne > MIN_END_RESULT || parsedResultTwo > MIN_END_RESULT ) && !(Math.abs(parsedResultOne-parsedResultTwo) == MAX_WINNING_DISTANCE)){
			return CreateBadmintonSingleGameAnswer.BADMINTON_SINGLE_RESULTS_INVALID;
		}
		PFCommonSaver.saveGame(seasonKey, team1, team2, parsedResultOne, parsedResultTwo, date, gameType);
		return CreateBadmintonSingleGameAnswer.BADMINTON_SINGLE_OK;
		
		
	}

	/**
	 * Create Badminton Double Game
	 * @param seasonKey key of the parent (Season)
	 * @param team1	List of team1
	 * @param team2 List of team2
	 * @param result1 Result of team1
	 * @param result2 Result of team2
	 * @param date date
	 * @param gameType the gameType
	 * @return CreateGameAnswer
	 */
	public CreateGameAnswer createBadmintonDoubleGame(final Key seasonKey, final List<Key> team1, final List<Key> team2, final String resultOne, final String resultTwo, final Date date, String gameType){
	
		if(GameValidator.anyUserSelectedTwice(team1, team2)){
			return CreateGameAnswer.GAME_NOK;
		}
		int parsedResultOne;
		int parsedResultTwo;
		
		try{
			 parsedResultOne = Integer.parseInt(resultOne);
			 parsedResultTwo = Integer.parseInt(resultTwo);
	
		}catch (NumberFormatException e) {
			return CreateGameAnswer.GAME_NOK;
		}
		
		if(parsedResultOne < 0 || parsedResultTwo < 0){
			return CreateGameAnswer.GAME_NOK;
		}
		
		
		PFCommonSaver.saveGame(seasonKey, team1, team2, parsedResultOne, parsedResultTwo, date, gameType);
		return CreateGameAnswer.GAME_OK;
	}
	
	

	/**
	 * Create a new Season
	 * 
	 * @param pSeasonName pSeasonName
	 * @param pUserName pUserName
	 * @param pUserPasswordMd5 pUserPasswordMd5
	 * @return the result of trying to create a Season
	 */
	public CreateSeasonAnswer createSeason( final String pSeasonName, final String pUserName, final String pUserPasswordMd5, String gameType,  List<String> usersAllowedToEditSeason ){
		if( pSeasonName.length() > MAX_SEASON_NAME_SIZE ){
			return CreateSeasonAnswer.SEASON_NAME_INVALID;
		}
		if( pSeasonName.length() < MIN_SEASON_NAME_SIZE ){
			return CreateSeasonAnswer.SEASON_NAME_INVALID;
		}
		if( !pSeasonName.matches("[0-9a-zA-Z_@]*")){
			return CreateSeasonAnswer.SEASON_NAME_INVALID;
		}
		if( PFCommonTester.doesSeasonExist(pSeasonName)){
			return CreateSeasonAnswer.SEASON_ALREADY_EXISTS;
		}
		if( !doesUserExistWithNameAndMd5Password(pUserName, pUserPasswordMd5)){
			return CreateSeasonAnswer.SEASON_USER_CREDENTIALS_INVALID;
		}
		PFCommonSaver.saveNewSeason(pSeasonName, pUserName, gameType, usersAllowedToEditSeason);
		return CreateSeasonAnswer.SEASON_OK;
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
	public boolean doesUserExistWithNameAndMd5Password( final String pUserName, final String pPasswordMd5 ){
		return PFCommonTester.doesUserExistWithNameAndMd5Password(pUserName, pPasswordMd5);
	}





	/**
	 * Does the user exist?
	 * 
	 * @param pUserName
	 *            the name of the user
	 * @return 'true', if the user exists, otherwise 'false'
	 */
	public boolean doesUserExistWithName( final String pUserName ){
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

	 public List<Game> getLatestBadmintonSingleGames(int count){
		 return PFCommonGetter.getLatestGamesByCount(count);
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

	 public List<MatchDay> getLatestMatchDays(int count)
	 {
		 return PFCommonGetter.getLatestMatchDays(count);
	 }
	 
	 public static List<Season> getAllSeasonsForUserAndGameType(Key currentUser, String gameType) {
		 return PFCommonGetter.getAllSeasonsForUserAndGameType(currentUser, gameType);
	 }
	 public static List<Season> getAllSeasonsForUser(Key currentUser) {
		 return PFCommonGetter.getAllSeasonsForUser(currentUser);
	 }
	 
	 public static Season getSeasonsByKey(Key season) {
		 return PFCommonGetter.getSeasonByKey(season);
	 }

	 public static UserWithPassword getUserWithPasswordByName(String currentUser) {
		 return PFCommonGetter.getUserByName(currentUser);
	 }

	 public static List<MatchDay> getAllMatchDaysOfUser(Key userKey, String gameType) {
		 return PFCommonGetter.getAllMatchDaysOfUser(userKey, gameType);
	 }

	 public  static DisplaySkillGraph getAllBadmintonSingleGamesInSeason(Key season, Key userName){
		 return PFCommonGetter.getAllGamesInSeason( GameTypStore.BADMINTON_SINGLE_GAME.toString(), season,  userName);
	 }

	 public  static DisplaySkillGraph getAllBadmintonDoubleGamesInSeason(Key season, Key userName){
		 return PFCommonGetter.getAllGamesInSeason( GameTypStore.BADMINTON_DOUBLE_GAME.toString(), season,  userName);
	 }

	 /** Returns all Seasons in a List<Seasons> */

	 public List<Season> getAllSeasons() {
		 return PFCommonGetter.getAllSeasons();
	 }

	public static WinLooseRanking getWinLoosRanking(Key seasonKey) {
		
		return PFRankingGetters.getWinLoosRanking(seasonKey);
	}

	public CreateGameAnswer createKickerGame(Key matchDayKey, List<Key> team1,
			List<Key> team2, String result1, String result2, Date date, String gameType) {
		if(GameValidator.anyUserSelectedTwice(team1, team2)){
			return CreateGameAnswer.GAME_NOK;
		}
		
		int parsedResultOne;
		int parsedResultTwo;
		
		try{
			 parsedResultOne = Integer.parseInt(result1);
			 parsedResultTwo = Integer.parseInt(result2);
	
		}catch (NumberFormatException e) {
			return CreateGameAnswer.GAME_NOK;
		}
		
		if(parsedResultOne < 0 || parsedResultTwo < 0){
			return CreateGameAnswer.GAME_NOK;
		}
		
		
		
		PFCommonSaver.saveGame(matchDayKey, team1, team2, parsedResultOne, parsedResultTwo, date, gameType);
		return CreateGameAnswer.GAME_OK;
	}

	public static DisplaySkillGraph getAllKickerGamesInSeason(Key seasonKey,
			Key userKey) {
		return PFCommonGetter.getAllGamesInSeason( GameTypStore.KICKER_GAME.toString(), seasonKey,  userKey);
	}

}
