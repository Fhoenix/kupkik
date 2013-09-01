package com.kupkik.persistence.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.PropertyProjection;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.kupkik.model.DisplaySkillGraph;
import com.kupkik.model.MatchDay;
import com.kupkik.model.Season;
import com.kupkik.model.User;
import com.kupkik.model.UserWithPassword;
import com.kupkik.model.game.Game;
import com.kupkik.persistence.EntityNameStore;
import com.kupkik.persistence.PropertiesStore;
import com.kupkik.ui.html.comperators.ComparatorEntityGame;
import com.kupkik.ui.html.comperators.ComparatorMatchDay;

public class PFCommonGetter {

	/**
	 * Get a instance of {@link UserWithPassword}. Can return null.
	 * @param userName String of user name.
	 * @return Instance of {@link UserWithPassword}. can return null.
	 */
	public static UserWithPassword getUserByName(String userName){
		List<UserWithPassword> allUsers = getAllUsers();
		for (UserWithPassword item : allUsers){
			if (item.getName().endsWith(userName)){
				return item;
			}
		}
		return null;
	}

	/**
	 * Get the Latest Games
	 * @param count count of Games
	 * @return List<Game>
	 */
	public static List<Game> getLatestGamesByCount(int count){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		List<Game> matches = new ArrayList<Game>();

		Query queryLatestGames = new Query(EntityNameStore.GAME.toString());
		queryLatestGames.addSort(PropertiesStore.DATE.toString(), Query.SortDirection.DESCENDING);
		PreparedQuery getLatestBadmintonSingleMatchesPreparedQuery = datastore.prepare(queryLatestGames);

		List<Entity> matchesEntities = getLatestBadmintonSingleMatchesPreparedQuery.asList(FetchOptions.Builder.withLimit(count));

		for( Entity currentEntity : matchesEntities ){
			List<Key> playerOne = (List<Key>) currentEntity.getProperties().get(PropertiesStore.TEAM_ONE.toString());
			List<Key>  playerTwo = (List<Key>) currentEntity.getProperties().get(PropertiesStore.TEAM_TWO.toString());
			int resultOne = Integer.parseInt(currentEntity.getProperties().get(PropertiesStore.RESULT_ONE.toString()).toString());
			int resultTwo =  Integer.parseInt(currentEntity.getProperties().get(PropertiesStore.RESULT_TWO.toString()).toString());

			String date = currentEntity.getProperty(PropertiesStore.DATE.toString()).toString();

			List<User> userTeam1 = new ArrayList<>();
			List<User> userTeam2 = new ArrayList<>();
			List<UserWithPassword> allUsers = PFCommonGetter.getAllUsers();
			for (UserWithPassword userWithPassword : allUsers) {
				for (Key team1item : playerOne){
					if(userWithPassword.getKey().equals(team1item)){
						userTeam1.add(userWithPassword);
					}
				}

				for (Key team2item : playerTwo){
					if(userWithPassword.getKey().equals(team2item)){
						userTeam2.add(userWithPassword);
					}
				}
			}

			Game currentBadmintonGame = new Game(userTeam1,userTeam2,date,resultOne,resultTwo, currentEntity.getProperty(PropertiesStore.GAMETYPE.toString()).toString());
			matches.add(currentBadmintonGame);
		}

		return matches;

	}



	public static UserWithPassword getUserByKey(Key userKey){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter keyFilter = new FilterPredicate(Entity.KEY_RESERVED_PROPERTY, FilterOperator.EQUAL, userKey);
		Query getUserQuery = new Query(EntityNameStore.USER.toString()).setFilter(keyFilter);
		PreparedQuery getUser = datastore.prepare(getUserQuery);
		List<Entity> userEntity = getUser.asList(FetchOptions.Builder.withLimit(1));
		UserWithPassword userWithPassword = null;
		for (Entity item: userEntity){
			userWithPassword = new UserWithPassword(item.getKey().getName(),item.getProperty(PropertiesStore.PASSWORD_MD5.toString()).toString(),item.getKey(), item.getProperty(PropertiesStore.FIRSTNAME.toString()).toString(),item.getProperty(PropertiesStore.SURNAME.toString()).toString());
		}
		return userWithPassword;

	}


	public static Season getSeasonByKey(Key season){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter keyFilter = new FilterPredicate(Entity.KEY_RESERVED_PROPERTY, FilterOperator.EQUAL, season);
		Query getUserQuery = new Query(EntityNameStore.SEASON.toString()).setFilter(keyFilter);
		PreparedQuery getUser = datastore.prepare(getUserQuery);


		List<Entity> userEntity = getUser.asList(FetchOptions.Builder.withLimit(1));


		for (Entity item: userEntity){
			ArrayList<User> userEdit = extractUsersToEditFromEntitySeason(item);
			return new Season(item.getKey().getName(), item.getKey(), item.getParent(), (String) item.getProperty(PropertiesStore.GAMETYPE.toString()),userEdit);
		}
		return null;


	}




	/**
	 * Get all MatchDays in database
	 * 
	 * @return all MatchDays, not ordered
	 */
	public static List<MatchDay> getAllMatchDays(){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query(EntityNameStore.GAME.toString());
		q.addSort(PropertiesStore.DATE.toString(), Query.SortDirection.ASCENDING);
		PreparedQuery getAllMatchDaysPreparedQuery = datastore.prepare(q);
		List<Entity> matchDaysFromDatastore = getAllMatchDaysPreparedQuery.asList(FetchOptions.Builder.withDefaults());

		List<MatchDay> matchDays = new ArrayList<MatchDay>();

		List<String> dates = new ArrayList<String>();

		for( Entity currentMatchDayEntity : matchDaysFromDatastore ){
			Date date = (Date) currentMatchDayEntity.getProperty(PropertiesStore.DATE.toString());

			SimpleDateFormat sdfDestination = new SimpleDateFormat(
					"dd-MM-yyyy");
			// parse the date into another format
			String strDate = sdfDestination.format(date);
			if(!dates.contains(strDate)) {
				MatchDay currentMatchDay = new MatchDay(strDate, currentMatchDayEntity.getParent());
				matchDays.add(currentMatchDay);
				dates.add(strDate);
			}
		}
		Collections.sort(matchDays, new ComparatorMatchDay());
		return matchDays;
	}





	/**
	 * Get all MatchDays in database
	 * 
	 * @return all MatchDays, not ordered
	 */
	public static List<MatchDay> getLatestMatchDays(int count)
	{

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query(EntityNameStore.GAME.toString());
		q.addSort(PropertiesStore.DATE.toString(), Query.SortDirection.ASCENDING);
		PreparedQuery getAllMatchDaysPreparedQuery = datastore.prepare(q);
		List<Entity> matchDaysFromDatastore = getAllMatchDaysPreparedQuery.asList(FetchOptions.Builder.withLimit(count));

		List<MatchDay> matchDays = new ArrayList<MatchDay>();

		List<String> dates = new ArrayList<String>();

		for( Entity currentMatchDayEntity : matchDaysFromDatastore ){
			Date date = (Date) currentMatchDayEntity.getProperty(PropertiesStore.DATE.toString());

			SimpleDateFormat sdfDestination = new SimpleDateFormat(
					"dd-MM-yyyy");
			// parse the date into another format
			String strDate = sdfDestination.format(date);

			if(!dates.contains(strDate)) {
				MatchDay currentMatchDay = new MatchDay(strDate, currentMatchDayEntity.getParent());
				matchDays.add(currentMatchDay);
				dates.add(strDate);
			}
		}
		Collections.sort(matchDays, new ComparatorMatchDay());
		return matchDays;


	}



	/**
	 * Gets all users 
	 * @return List<UserWithPassword>
	 */
	public static List<UserWithPassword> getAllUsers()
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query getAllUsersQuery = new Query(EntityNameStore.USER.toString());
		PreparedQuery getAllUsersPreparedQuery = datastore.prepare(getAllUsersQuery);

		List<Entity> userEntities = getAllUsersPreparedQuery.asList(FetchOptions.Builder.withDefaults());
		List<UserWithPassword> users = new ArrayList<UserWithPassword>();
		for( Entity currentUserEntity : userEntities )
		{
			UserWithPassword currentUser = new UserWithPassword(currentUserEntity.getKey().getName(), currentUserEntity.getProperty(PropertiesStore.PASSWORD_MD5.toString()).toString(), currentUserEntity.getKey(), currentUserEntity.getProperty(PropertiesStore.FIRSTNAME.toString()).toString(), currentUserEntity.getProperty(PropertiesStore.SURNAME.toString()).toString());
			users.add(currentUser);
		}

		return users;
	}

	/**
	 * Get all MatchDays that belong to one User.
	 * @param userName Users name.
	 * @return a List of {@link MatchDay}.
	 */
	public static List<MatchDay> getAllMatchDaysOfUser(Key userKey, String gameType) {
		List<Season> seasons = getAllSeasonsForUserAndGameType(userKey, gameType);
		List<MatchDay> matchDays = new ArrayList<MatchDay>();
		for(Season item :seasons){
			if(item.getGameType().equals(gameType)){
				List<MatchDay> allMatchDaysBySeason = getMatchDaysForSeason(item.getKey(), gameType);
				for (MatchDay matchDay : allMatchDaysBySeason) {
					matchDays.add( new MatchDay(matchDay.getName(), matchDay.getParentKey()));
				}
			}	
		}
		Collections.sort(matchDays, new ComparatorMatchDay());
		return matchDays;	
	}

	public  static DisplaySkillGraph getAllGamesInSeason(String gameType, Key season, Key userName){
		DisplaySkillGraph dataskillGraph = null;
		List<MatchDay> allMatchDaysBySeason = getMatchDaysForSeason(season, gameType);

		int totalGamesPlayed = 0;
		int totalGamesWon = 0;
		int totalNumberGames = 0;
	
		for (MatchDay matchDay: allMatchDaysBySeason){
			int gamesWon = 0;
			int gamesPlayed = 0;
			for(Game game : matchDay.getGames()){
					for (User user : game.getTeamOne()) {
						if(user.getKey().equals(userName)){
							gamesPlayed++;
							totalGamesPlayed++;
							if(game.getResultOne() > game.getResultTwo()){
								gamesWon++;
								totalGamesWon++;
							}
						}
					}
					for (User user : game.getTeamTwo()) {
						if(user.getKey().equals(userName)){
							gamesPlayed++;
							totalGamesPlayed++;
							if( game.getResultTwo() > game.getResultOne()){
								gamesWon++;
								totalGamesWon++;
							}
						}
					}
				}
			matchDay.setGamesWon(gamesWon);
			matchDay.setGamesPlayed(gamesPlayed);
		}
		dataskillGraph = new DisplaySkillGraph(totalGamesPlayed, totalGamesWon, totalNumberGames,allMatchDaysBySeason);
		return dataskillGraph;




	}
	/**
	 * Get all Seasons that belongs to an user.
	 * @param userName user name.
	 * @return List of {@link Season}.
	 */
	public static List<Season> getAllSeasonsForUserAndGameType(Key userKey, String gameType) {

		List<Season> seasons = new ArrayList<Season>();



		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();			
		Query getSeasonKey = new Query(EntityNameStore.SEASON.toString());

		PreparedQuery pQGetSeason = dataStore.prepare(getSeasonKey);
		List<Entity> seasonEntity = pQGetSeason.asList(FetchOptions.Builder.withDefaults());


		for (Entity item: seasonEntity){
			List<String> userKeys = (List<String>)  item.getProperty(PropertiesStore.USERS_ALLOWED_TO_EDIT.toString());
			for (String key : userKeys) {
				if( KeyFactory.stringToKey(key).equals(userKey)){
					if(item.getProperty(PropertiesStore.GAMETYPE.toString()).equals(gameType)){
						ArrayList<User> userEdit = extractUsersToEditFromEntitySeason(item);
						seasons.add( new Season(item.getKey().getName(), item.getKey(), item.getParent(), (String) item.getProperty(PropertiesStore.GAMETYPE.toString()),userEdit));
					}
				}
			}
		}

		return seasons;	
	}

	/**
	 * Get all Seasons
	 * @param userName user name.
	 * @return List of {@link Season}.
	 */
	public static List<Season> getAllSeasons() {

		List<Season> seasons = new ArrayList<Season>();


		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();			
		Query getSeasonKey = new Query(EntityNameStore.SEASON.toString());

		PreparedQuery pQGetSeason = dataStore.prepare(getSeasonKey);
		List<Entity> seasonEntity = pQGetSeason.asList(FetchOptions.Builder.withDefaults());


		for (Entity item: seasonEntity){
			ArrayList<User> userEdit = extractUsersToEditFromEntitySeason(item);

			seasons.add( new Season(item.getKey().getName(), item.getKey(), item.getParent(), (String) item.getProperty(PropertiesStore.GAMETYPE.toString()), userEdit));
		}

		return seasons;	
	}

	private static ArrayList<User> extractUsersToEditFromEntitySeason(Entity item) {
		List<String> usersToEdit = (List<String>) item.getProperty(PropertiesStore.USERS_ALLOWED_TO_EDIT.toString());
		ArrayList<User> userEdit = new ArrayList<User>();
		for (String userKey : usersToEdit){
			userEdit.add(getUserByKey(KeyFactory.stringToKey(userKey)));
		}
		return userEdit;
	}

	public static List<MatchDay> getMatchDaysForSeason(Key season, String gameType){

		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();
		Query getGamesbySeasonKey = new Query(EntityNameStore.GAME.toString()).setAncestor(season);
	
		PreparedQuery gamesBySeason = dataStore.prepare(getGamesbySeasonKey);
		List<Entity> gamesBySeasons = gamesBySeason.asList(FetchOptions.Builder.withDefaults());
		Collections.sort(gamesBySeasons, new ComparatorEntityGame());
	
		
		List<MatchDay> matchDays = new ArrayList<MatchDay>();
		List<String> dates = new ArrayList<String>();
		for (Entity game: gamesBySeasons){
			Date date = (Date) game.getProperty(PropertiesStore.DATE.toString());
			SimpleDateFormat sdfDestination = new SimpleDateFormat(
					"dd-MM-yyyy");
			// parse the date into another format
			String strDate = sdfDestination.format(date);
			if(!dates.contains(strDate)) {
				matchDays.add(new MatchDay(strDate, season ));
				dates.add(strDate);
			}
			
		}
		Collections.sort(matchDays, new ComparatorMatchDay());
		
		for(MatchDay matchday : matchDays){
			List<Game> games = new ArrayList<Game>();
			for (Entity game: gamesBySeasons){
				Date date = (Date) game.getProperty(PropertiesStore.DATE.toString());
				SimpleDateFormat sdfDestination = new SimpleDateFormat("dd-MM-yyyy");
				// parse the date into another format
				String strDate = sdfDestination.format(date);

				if(matchday.getName().equals(strDate)){
					List<Key> team1 = (List<Key>) game.getProperty(PropertiesStore.TEAM_ONE.toString());
					List<Key> team2 = (List<Key>) game.getProperty(PropertiesStore.TEAM_TWO.toString());
					int result1 = Integer.parseInt( game.getProperty(PropertiesStore.RESULT_ONE.toString()).toString());
					int result2 = Integer.parseInt( game.getProperty(PropertiesStore.RESULT_TWO.toString()).toString());

					List<User> userTeam1 = new ArrayList<>();
					List<User> userTeam2 = new ArrayList<>();

					for (Key key : team1){
						userTeam1.add(getUserByKey(key));
					}

					for (Key key : team2){
						userTeam2.add(getUserByKey(key));
					}

					games.add(new Game(userTeam1, userTeam2, strDate,result1,result2,gameType ));

				}
			}
			matchday.setGames(games);
		}
		return matchDays;
	}



	public static List<Season> getAllSeasonsForUser(Key currentUser) {
		List<Season> seasons = new ArrayList<Season>();



		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();			
		Query getSeasonKey = new Query(EntityNameStore.SEASON.toString());

		PreparedQuery pQGetSeason = dataStore.prepare(getSeasonKey);
		List<Entity> seasonEntity = pQGetSeason.asList(FetchOptions.Builder.withDefaults());


		for (Entity item: seasonEntity){
			List<String> userKeys = (List<String>)  item.getProperty(PropertiesStore.USERS_ALLOWED_TO_EDIT.toString());
			for (String key : userKeys) {
				if( KeyFactory.stringToKey(key).equals(currentUser)){
					ArrayList<User> userEdit = extractUsersToEditFromEntitySeason(item);
					seasons.add( new Season(item.getKey().getName(), item.getKey(), item.getParent(), (String) item.getProperty(PropertiesStore.GAMETYPE.toString()), userEdit));
				}
			}
		}

		return seasons;	
	}


	public static List<Season> getAllCreatedSeasonsForUser(Key currentUser) {
		List<Season> seasons = new ArrayList<Season>();



		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();			
		Query getSeasonKey = new Query(EntityNameStore.SEASON.toString(), currentUser);

		PreparedQuery pQGetSeason = dataStore.prepare(getSeasonKey);
		List<Entity> seasonEntity = pQGetSeason.asList(FetchOptions.Builder.withDefaults());


		for (Entity item: seasonEntity){

			ArrayList<User> userEdit = extractUsersToEditFromEntitySeason(item);
			seasons.add( new Season(item.getKey().getName(), item.getKey(), item.getParent(), (String) item.getProperty(PropertiesStore.GAMETYPE.toString()), userEdit));


		}

		return seasons;	
	}

}
