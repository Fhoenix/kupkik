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
import com.google.appengine.api.datastore.PropertyProjection;
import com.google.appengine.api.datastore.RawValue;

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
		Query getUserQuery = new Query(EntityNameStore.USER.toString()).setFilter(keyFilter);
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
		Query getUserQuery = new Query(EntityNameStore.SEASON.toString()).setFilter(keyFilter);
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
		Query q = new Query(EntityNameStore.GAME.toString());
		q.addProjection(new PropertyProjection("date", String.class));


		q.setDistinct(true);
		q.addSort("date", Query.SortDirection.DESCENDING);

		PreparedQuery getAllMatchDaysPreparedQuery = datastore.prepare(q);
		List<Entity> matchDaysFromDatastore = getAllMatchDaysPreparedQuery.asList(FetchOptions.Builder.withDefaults());
		List<MatchDay> matchDays = new ArrayList<MatchDay>();
		for( Entity currentMatchDayEntity : matchDaysFromDatastore )
		{


			MatchDay currentMatchDay = new MatchDay(currentMatchDayEntity.getProperty("date").toString(), currentMatchDayEntity.getParent());
			matchDays.add(currentMatchDay);
		}

		return matchDays;
	}




	/**
	 * Get all MatchDays in database
	 * 
	 * @return all MatchDays, not ordered
	 */
	public static List<MatchDay> getAllMatchDaysBySeason(Key season)
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query(EntityNameStore.GAME.toString()).setAncestor(season);

		q.addProjection(new PropertyProjection("date", String.class));


		q.setDistinct(true);
		q.addSort("date", Query.SortDirection.DESCENDING);

		PreparedQuery getAllMatchDaysPreparedQuery = datastore.prepare(q);
		List<Entity> matchDaysFromDatastore = getAllMatchDaysPreparedQuery.asList(FetchOptions.Builder.withDefaults());
		List<MatchDay> matchDays = new ArrayList<MatchDay>();
		for( Entity currentMatchDayEntity : matchDaysFromDatastore )
		{


			MatchDay currentMatchDay = new MatchDay(currentMatchDayEntity.getProperty("date").toString(), currentMatchDayEntity.getParent());
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
		Query q = new Query(EntityNameStore.GAME.toString());
		q.addProjection(new PropertyProjection("date", String.class));


		q.setDistinct(true);
		q.addSort("date", Query.SortDirection.DESCENDING);

		PreparedQuery getAllMatchDaysPreparedQuery = datastore.prepare(q);
		List<Entity> matchDaysFromDatastore = getAllMatchDaysPreparedQuery.asList(FetchOptions.Builder.withLimit(count));
		List<MatchDay> matchDays = new ArrayList<MatchDay>();
		for( Entity currentMatchDayEntity : matchDaysFromDatastore )
		{


			MatchDay currentMatchDay = new MatchDay(currentMatchDayEntity.getProperty("date").toString(), currentMatchDayEntity.getParent());
			matchDays.add(currentMatchDay);
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
		Query getAllUsersQuery = new Query(EntityNameStore.USER.toString());
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

		List<Season> seasons = getAllSeasonsForUserAndGameType(userKey, gameType);

		List<MatchDay> result = new ArrayList<MatchDay>();

		for(Season item :seasons){
			if(item.getGameType().equals(gameType)){
				DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();			

				List<MatchDay> allMatchDaysBySeason = getAllMatchDaysBySeason(item.getKey());
				for (MatchDay matchDay : allMatchDaysBySeason) {

					result.add( new MatchDay(matchDay.getName(), matchDay.getParentKey()));
				}
			}	

		}
		return result;	
	}

	public  static DisplaySkillGraph getAllGamesInSeason(String gameType, Key season, Key userName){


		DisplaySkillGraph dataskillGraph = null;
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();

		List<MatchDay> allMatchDaysBySeason = getAllMatchDaysBySeason(season);

		int totalGamesPlayed = 0;
		int totalGamesWon = 0;
		int totalNumberGames = 0;


		List<MatchDay> matchDays = new ArrayList<MatchDay>();
		for (MatchDay matchDay: allMatchDaysBySeason){
			int gamesWon = 0;
			int gamesPlayed = 0;

			DatastoreService dataStore2 = DatastoreServiceFactory.getDatastoreService();
			Query getGamesForMatchDay = new Query("Game");
			PreparedQuery PQGames = dataStore2.prepare(getGamesForMatchDay);
			List<Entity> games = PQGames.asList(FetchOptions.Builder.withDefaults());	





			List<Game> badmintonSingleGames = new ArrayList<Game>();
			for(Entity game : games){
				if(game.getProperty("gameType").equals(gameType) && game.getProperty("date").equals(matchDay.getName())){
					totalNumberGames++;
					List<Key> playerOne = (List<Key>) game.getProperty("teamOne");
					List<Key> playerTwo = (List<Key>) game.getProperty("teamTwo");

					int resultOne = Integer.parseInt(game.getProperty("resultOne").toString());
					int resultTwo = Integer.parseInt(game.getProperty("resultTwo").toString());
					String date =  game.getProperty("date").toString();

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

					badmintonSingleGames.add(new Game(userTeam1, userTeam2,date, resultOne, resultTwo,gameType));


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
			}
			MatchDay t = new MatchDay(matchDay.getName(), matchDay.getParentKey());
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
	public static List<Season> getAllSeasonsForUserAndGameType(Key userKey, String gameType) {

		List<Season> seasons = new ArrayList<Season>();



		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();			
		Query getSeasonKey = new Query(EntityNameStore.SEASON.toString());

		PreparedQuery pQGetSeason = dataStore.prepare(getSeasonKey);
		List<Entity> seasonEntity = pQGetSeason.asList(FetchOptions.Builder.withDefaults());


		for (Entity item: seasonEntity){
			List<String> userKeys = (List<String>)  item.getProperty("EditUser");
			for (String key : userKeys) {
				if( KeyFactory.stringToKey(key).equals(userKey)){
					if(item.getProperty("GameType").equals(gameType)){
						seasons.add( new Season(item.getKey().getName(), item.getKey(), item.getParent(), (String) item.getProperty("GameType")));
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
			seasons.add( new Season(item.getKey().getName(), item.getKey(), item.getParent(), (String) item.getProperty("GameType")));
		}

		return seasons;	
	}

	public static List<MatchDay> getMatchDaysForSeason(Key season, String gameType){

		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();
		Query getGamesbySeasonKey = new Query(EntityNameStore.GAME.toString()).setAncestor(season);
		PreparedQuery PQMatchDays = dataStore.prepare(getGamesbySeasonKey);
		List<Entity> matchDayEntity = PQMatchDays.asList(FetchOptions.Builder.withDefaults());


		List<MatchDay> matchDays = new ArrayList<MatchDay>();
		for (Entity matchDay: matchDayEntity){

			DatastoreService dataStore2 = DatastoreServiceFactory.getDatastoreService();
			Query getGamesForMatchDay = new Query("Game").setAncestor(matchDay.getKey());
			PreparedQuery PQGames = dataStore2.prepare(getGamesForMatchDay);
			List<Entity> games = PQGames.asList(FetchOptions.Builder.withDefaults());	


			List<Game> iGames = new ArrayList<Game>();

			for(Entity game : games){
				if(game.getProperty("gameType").equals(gameType)){
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
					String date = game.getProperty("date").toString();

					//TODO  COS THIS MUST BE GENERIC
					iGames.add(new Game(userTeam1,userTeam2, date,resultOne,resultTwo,gameType));
				}
			}
			MatchDay tmp =  new MatchDay(matchDay.getKey().getName(),  matchDay.getParent());
			tmp.setGames(iGames);
			matchDays.add(tmp);

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
			List<String> userKeys = (List<String>)  item.getProperty("EditUser");
			for (String key : userKeys) {
				if( KeyFactory.stringToKey(key).equals(currentUser)){
						seasons.add( new Season(item.getKey().getName(), item.getKey(), item.getParent(), (String) item.getProperty("GameType")));
				}
			}
		}

		return seasons;	
	}

}
