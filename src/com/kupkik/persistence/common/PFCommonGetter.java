package com.kupkik.persistence.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.kupkik.model.DisplaySkillGraph;
import com.kupkik.model.Season;
import com.kupkik.model.MatchDay;
import com.kupkik.model.User;
import com.kupkik.model.UserWithPassword;
import com.kupkik.model.game.Game;
import com.kupkik.persistence.EntityNameStore;

public class PFCommonGetter {

	//    private static final Logger sLogger = Logger.getLogger(HtmlStarterServlet.class.getName());



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

	public static UserWithPassword getUserByKey(Key userKey){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter keyFilter = new FilterPredicate(Entity.KEY_RESERVED_PROPERTY, FilterOperator.EQUAL, userKey);
		Query getUserQuery = new Query(EntityNameStore.USER).setFilter(keyFilter);
		PreparedQuery getUser = datastore.prepare(getUserQuery);


		List<Entity> userEntity = getUser.asList(FetchOptions.Builder.withLimit(1));

		UserWithPassword userWithPassword = null;
		for (Entity item: userEntity){
			userWithPassword = new UserWithPassword(item.getKey().getName(),item.getProperty("passwordMd5").toString(),item.getKey(), item.getProperty("firstname").toString(),item.getProperty("surname").toString());
		}
		return userWithPassword;

	}


	public static Season getSeasonByKey(Key season){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter keyFilter = new FilterPredicate(Entity.KEY_RESERVED_PROPERTY, FilterOperator.EQUAL, season);
		Query getUserQuery = new Query(EntityNameStore.SEASON).setFilter(keyFilter);
		PreparedQuery getUser = datastore.prepare(getUserQuery);


		List<Entity> userEntity = getUser.asList(FetchOptions.Builder.withLimit(1));


		for (Entity item: userEntity){
			return new Season(item.getKey().getName(), item.getKey(), item.getParent(), (String) item.getProperty("GameType"));
		}
		return null;


	}




	/**
	 * Get all MatchDays in database
	 * 
	 * @return all MatchDays, not ordered
	 */
	public static List<MatchDay> getAllMatchDays()
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query getAllMatchDaysQuery = new Query(EntityNameStore.MATCHDAY);
		PreparedQuery getAllMatchDaysPreparedQuery = datastore.prepare(getAllMatchDaysQuery);

		List<Entity> userEntities = getAllMatchDaysPreparedQuery.asList(FetchOptions.Builder.withDefaults());
		List<MatchDay> matchDays = new ArrayList<MatchDay>();
		for( Entity currentMatchDayEntity : userEntities )
		{
			MatchDay currentMatchDay = new MatchDay(currentMatchDayEntity.getKey().getName(), currentMatchDayEntity.getKey(), currentMatchDayEntity.getParent());
			matchDays.add(currentMatchDay);
		}

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
		Query getAllMatchDaysQuery = new Query(EntityNameStore.MATCHDAY);
		PreparedQuery getAllMatchDaysPreparedQuery = datastore.prepare(getAllMatchDaysQuery);

		List<Entity> userEntities = getAllMatchDaysPreparedQuery.asList(FetchOptions.Builder.withLimit(count));
		List<MatchDay> matchDays = new ArrayList<MatchDay>();
		for( Entity currentMatchDayEntity : userEntities )
		{
			MatchDay currentMatchDay = new MatchDay(currentMatchDayEntity.getKey().getName(), currentMatchDayEntity.getKey(), currentMatchDayEntity.getParent());
			matchDays.add(currentMatchDay);
		}

		return matchDays;
	}

	/**
	 * Get MatchDay by MatchDay Key
	 * @param matchDayKey matchDayKey
	 * @return Instance of {@link MatchDay}
	 */
	public static MatchDay getMatchDayByKey(Key matchDayKey){

		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();
		Filter filterName = new FilterPredicate("Key", FilterOperator.EQUAL,matchDayKey );

		Query getMatchDayKey = new Query(EntityNameStore.MATCHDAY).setFilter(filterName);
		PreparedQuery pQGetMatchDay = dataStore.prepare(getMatchDayKey);

		List<Entity> matchDaysEntity = pQGetMatchDay.asList(FetchOptions.Builder.withLimit(1));

		MatchDay matchDays = null;
		for (Entity item: matchDaysEntity){
			matchDays = new MatchDay(item.getKey().getName(), item.getKey(), item.getParent());
		}

		return matchDays;
	}

	/**
	 * Gets all users 
	 * @return List<UserWithPassword>
	 */
	public static List<UserWithPassword> getAllUsers()
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query getAllUsersQuery = new Query(EntityNameStore.USER);
		PreparedQuery getAllUsersPreparedQuery = datastore.prepare(getAllUsersQuery);

		List<Entity> userEntities = getAllUsersPreparedQuery.asList(FetchOptions.Builder.withDefaults());
		List<UserWithPassword> users = new ArrayList<UserWithPassword>();
		for( Entity currentUserEntity : userEntities )
		{
			UserWithPassword currentUser = new UserWithPassword(currentUserEntity.getKey().getName(), currentUserEntity.getProperty("passwordMd5").toString(), currentUserEntity.getKey(), currentUserEntity.getProperty("firstname").toString(), currentUserEntity.getProperty("surname").toString());
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

		List<Season> seasons = getAllSeasonsForUser(userKey);

		List<MatchDay> result = new ArrayList<MatchDay>();

		for(Season item :seasons){
			if(item.getGameType().equals(gameType)){
				DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();			
				Query getMatchDayKey = new Query(EntityNameStore.MATCHDAY).setAncestor(item.getKey());

				PreparedQuery pQGetSeason = dataStore.prepare(getMatchDayKey);
				List<Entity> seasonEntity = pQGetSeason.asList(FetchOptions.Builder.withDefaults());

				for (Entity entity: seasonEntity){
					result.add( new MatchDay(entity.getKey().getName(), entity.getKey(), entity.getParent()));
				}	
			}
		}
		return result;	
	}

	public  static DisplaySkillGraph getAllGamesInSeason(String gameType, Key season, Key userName){


		DisplaySkillGraph dataskillGraph = null;
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();
		Query getMatchDayBySeasonKey = new Query(EntityNameStore.MATCHDAY).setAncestor(season);
		PreparedQuery PQMatchDays = dataStore.prepare(getMatchDayBySeasonKey);

		List<Entity> matchDayEntity = PQMatchDays.asList(FetchOptions.Builder.withDefaults());

		int totalGamesPlayed = 0;
		int totalGamesWon = 0;
		int totalNumberGames = 0;


		List<MatchDay> matchDays = new ArrayList<MatchDay>();
		for (Entity matchDay: matchDayEntity){
			int gamesWon = 0;
			int gamesPlayed = 0;

			DatastoreService dataStore2 = DatastoreServiceFactory.getDatastoreService();
			Query getGamesForMatchDay = new Query(gameType).setAncestor(matchDay.getKey());
			PreparedQuery PQGames = dataStore2.prepare(getGamesForMatchDay);
			List<Entity> games = PQGames.asList(FetchOptions.Builder.withDefaults());	

			List<Game> badmintonSingleGames = new ArrayList<Game>();
			for(Entity game : games){
				totalNumberGames++;
				List<Key> playerOne = (List<Key>) game.getProperty("teamOne");
				List<Key> playerTwo = (List<Key>) game.getProperty("teamTwo");

				int resultOne = Integer.parseInt(game.getProperty("resultOne").toString());
				int resultTwo = Integer.parseInt(game.getProperty("resultTwo").toString());
				//Date date = (Date) game.getProperty("date");
				String matchDayName = game.getParent().getName();

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

				badmintonSingleGames.add(new Game(userTeam1, userTeam2,null, resultOne, resultTwo,  matchDayName));


				for (User user : userTeam1) {
					if(user.getKey().equals(userName)){
						gamesPlayed++;
						totalGamesPlayed++;
						if(resultOne > resultTwo){
							gamesWon++;
							totalGamesWon++;
						}
					}
				}


				for (User user : userTeam2) {
					if(user.getKey().equals(userName)){
						gamesPlayed++;
						totalGamesPlayed++;
						if( resultTwo > resultOne){
							gamesWon++;
							totalGamesWon++;
						}
					}
				}
			}
			MatchDay t = new MatchDay(matchDay.getKey().getName(), matchDay.getKey(), matchDay.getParent());
			t.setGames(badmintonSingleGames);
			t.setGamesWon(gamesWon);
			t.setGamesPlayed(gamesPlayed);

			matchDays.add(t);


		}
		dataskillGraph = new DisplaySkillGraph(totalGamesPlayed, totalGamesWon, totalNumberGames,matchDays);
		return dataskillGraph;




	}
	/**
	 * Get all Seasons that belongs to an user.
	 * @param userName user name.
	 * @return List of {@link Season}.
	 */
	public static List<Season> getAllSeasonsForUser(Key userKey) {

		List<Season> seasons = new ArrayList<Season>();



		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();			
		Query getSeasonKey = new Query(EntityNameStore.SEASON);

		PreparedQuery pQGetSeason = dataStore.prepare(getSeasonKey);
		List<Entity> seasonEntity = pQGetSeason.asList(FetchOptions.Builder.withDefaults());


		for (Entity item: seasonEntity){

			List<String> userKeys = (List<String>)  item.getProperty("EditUser");
			for (String key : userKeys) {
				if( KeyFactory.stringToKey(key).equals(userKey)){
					seasons.add( new Season(item.getKey().getName(), item.getKey(), item.getParent(), (String) item.getProperty("GameType")));

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
		Query getSeasonKey = new Query(EntityNameStore.SEASON);

		PreparedQuery pQGetSeason = dataStore.prepare(getSeasonKey);
		List<Entity> seasonEntity = pQGetSeason.asList(FetchOptions.Builder.withDefaults());


		for (Entity item: seasonEntity){
			seasons.add( new Season(item.getKey().getName(), item.getKey(), item.getParent(), (String) item.getProperty("GameType")));
		}

		return seasons;	
	}

	public static List<MatchDay> getMatchDaysForSeason(Key season, String gameType){

		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();
		Query getMatchDaysbySeasonKey = new Query(EntityNameStore.MATCHDAY).setAncestor(season);
		PreparedQuery PQMatchDays = dataStore.prepare(getMatchDaysbySeasonKey);
		List<Entity> matchDayEntity = PQMatchDays.asList(FetchOptions.Builder.withDefaults());


		List<MatchDay> matchDays = new ArrayList<MatchDay>();
		for (Entity matchDay: matchDayEntity){

			DatastoreService dataStore2 = DatastoreServiceFactory.getDatastoreService();
			Query getGamesForMatchDay = new Query(gameType).setAncestor(matchDay.getKey());
			PreparedQuery PQGames = dataStore2.prepare(getGamesForMatchDay);
			List<Entity> games = PQGames.asList(FetchOptions.Builder.withDefaults());	


			List<Game> iGames = new ArrayList<Game>();

			for(Entity game : games){
				List<Key> team1 = (List<Key>) game.getProperty("teamOne");
				List<Key> team2 = (List<Key>) game.getProperty("teamTwo");

				List<User> userTeam1 = new ArrayList<User>();
				List<User> userTeam2 = new ArrayList<User>();
				List<UserWithPassword> allUsers = PFCommonGetter.getAllUsers();
				for (UserWithPassword userWithPassword : allUsers) {
					for (Key team1item : team1){
						if(userWithPassword.getKey().equals(team1item)){
							userTeam1.add(userWithPassword);
						}
					}

					for (Key team2item : team2){
						if(userWithPassword.getKey().equals(team2item)){
							userTeam2.add(userWithPassword);
						}
					}
				}


				int resultOne = Integer.parseInt(game.getProperty("resultOne").toString());
				int resultTwo = Integer.parseInt(game.getProperty("resultTwo").toString());
				Date date = (Date) game.getProperty("date");
				String matchDayName = game.getParent().getName();

				//TODO  COS THIS MUST BE GENERIC
				iGames.add(new Game(userTeam1,userTeam2, date,resultOne,resultTwo,matchDayName));
			}
			MatchDay tmp =  new MatchDay(matchDay.getKey().getName(), matchDay.getKey(), matchDay.getParent());
			tmp.setGames(iGames);
			matchDays.add(tmp);

		}
		return matchDays;
	}

}
