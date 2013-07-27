package com.kupkik.persistence.badminton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.internal.matchers.Each;

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
import com.kupkik.model.MatchDay;
import com.kupkik.model.User;
import com.kupkik.model.UserWithPassword;
import com.kupkik.model.game.BadmintonDouble;
import com.kupkik.model.game.BadmintonSingle;
import com.kupkik.model.game.IGame;
import com.kupkik.persistence.EntityNameStore;
import com.kupkik.persistence.common.PFCommonGetter;

public class PFBadmintonGetters {
	//    private static final Logger sLogger = Logger.getLogger(HtmlStarterServlet.class.getName());


	/**
	 * Get the Latest BadmintonSingleGames
	 * @param count
	 * @return
	 */
	public static List<BadmintonSingle> getLatestBadmintonSingleGames(int count){

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Query getLatestBadmintonSingleMatchesQuery = new Query(EntityNameStore.BADMINTON_SINGLE_GAME);
		getLatestBadmintonSingleMatchesQuery.addSort("date", Query.SortDirection.DESCENDING);
		PreparedQuery getLatestBadmintonSingleMatchesPreparedQuery = datastore.prepare(getLatestBadmintonSingleMatchesQuery);

		List<Entity> matchesEntities = getLatestBadmintonSingleMatchesPreparedQuery.asList(FetchOptions.Builder.withLimit(count));

		List<BadmintonSingle> matches = new ArrayList<BadmintonSingle>();
		for( Entity currentEntity : matchesEntities )
		{
			Key playerOne = (Key) currentEntity.getProperties().get("playerOne");
			Key playerTwo = (Key) currentEntity.getProperties().get("playerTwo");
			int resultOne = Integer.parseInt(currentEntity.getProperties().get("resultOne").toString());
			int resultTwo =  Integer.parseInt(currentEntity.getProperties().get("resultTwo").toString());
			String matchDayName = currentEntity.getParent().getName();
			Date date = (Date) currentEntity.getProperties().get("date");

			User userPlayerOne = null;
			User userPlayerTwo = null;
			List<UserWithPassword> allUsers = PFCommonGetter.getAllUsers();
			for (UserWithPassword userWithPassword : allUsers) {
				if(userWithPassword.getKey().equals(playerOne)){
					userPlayerOne = userWithPassword;
				}
				if(userWithPassword.getKey().equals(playerTwo)){
					userPlayerTwo = userWithPassword;
				}
			}
			
			BadmintonSingle currentBadmintonGame = new BadmintonSingle(userPlayerOne,userPlayerTwo,resultOne,resultTwo,date,matchDayName);
			matches.add(currentBadmintonGame);
		}

		return matches;

	}

	public  static DisplaySkillGraph getAllBadmintonSingleGamesInSeason(Key season, Key userName){

		
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
			Query getGamesForMatchDay = new Query(EntityNameStore.BADMINTON_SINGLE_GAME).setAncestor(matchDay.getKey());
			PreparedQuery PQGames = dataStore2.prepare(getGamesForMatchDay);
			List<Entity> games = PQGames.asList(FetchOptions.Builder.withDefaults());	
			
			List<IGame> badmintonSingleGames = new ArrayList<IGame>();
			for(Entity game : games){
				totalNumberGames++;
				Key playerOne = (Key) game.getProperty("playerOne");
				Key playerTwo = (Key) game.getProperty("playerTwo");
				
				int resultOne = Integer.parseInt(game.getProperty("resultOne").toString());
				int resultTwo = Integer.parseInt(game.getProperty("resultTwo").toString());
				//Date date = (Date) game.getProperty("date");
				String matchDayName = game.getParent().getName();
				
				User userPlayerOne = null;
				User userPlayerTwo = null;
				List<UserWithPassword> allUsers = PFCommonGetter.getAllUsers();
				for (UserWithPassword userWithPassword : allUsers) {
					if(userWithPassword.getKey().equals(playerOne)){
						userPlayerOne = userWithPassword;
					}
					if(userWithPassword.getKey().equals(playerTwo)){
						userPlayerTwo = userWithPassword;
					}
				}
				
				badmintonSingleGames.add(new BadmintonSingle(userPlayerOne, userPlayerTwo, resultOne, resultTwo, null, matchDayName));
				
				
				if (playerOne.equals(userName)
						|| playerTwo.equals(userName)){
					totalGamesPlayed++;
					gamesPlayed++;

				

					if((playerOne.equals(userName) && resultTwo < resultOne) 
							|| (playerTwo.equals(userName) && resultOne < resultTwo) ){
						totalGamesWon++;
						gamesWon++;
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
	
	
	
	
	
public  static DisplaySkillGraph getAllBadmintonDoubleGamesInSeason(Key season, Key userName){

		
		DisplaySkillGraph dataskillGraph = null;
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();
		Query getMatchDaysbySeasonKey = new Query(EntityNameStore.MATCHDAY).setAncestor(season);
		PreparedQuery PQMatchDays = dataStore.prepare(getMatchDaysbySeasonKey);

		List<Entity> matchDayEntity = PQMatchDays.asList(FetchOptions.Builder.withDefaults());

		int totalGamesPlayed = 0;
		int totalGamesWon = 0;
		int totalNumberGames = 0;
		

		List<MatchDay> matchDays = new ArrayList<MatchDay>();
		for (Entity matchDay: matchDayEntity){
			int gamesWon = 0;
			int gamesPlayed = 0;
			
			DatastoreService dataStore2 = DatastoreServiceFactory.getDatastoreService();
			Query getGamesForMatchDay = new Query(EntityNameStore.BADMINTON_DOUBLE_GAME).setAncestor(matchDay.getKey());
			PreparedQuery PQGames = dataStore2.prepare(getGamesForMatchDay);
			List<Entity> games = PQGames.asList(FetchOptions.Builder.withDefaults());	
			
			List<IGame> badmintonDoubleGames = new ArrayList<IGame>();
			for(Entity game : games){
				totalNumberGames++;
				List<Key> team1 = (List<Key>) game.getProperty("teamOne");
				List<Key> team2 = (List<Key>) game.getProperty("teamTwo");
			
				List<User> userTeam1 = new ArrayList<>();
				List<User> userTeam2 = new ArrayList<>();
				List<UserWithPassword> allUsers = PFCommonGetter.getAllUsers();
				for (UserWithPassword userWithPassword : allUsers) {
					if(userWithPassword.getKey().equals(team1.get(0))){
						userTeam1.add(userWithPassword);
					}
					if(userWithPassword.getKey().equals(team1.get(1))){
						userTeam1.add(userWithPassword);
					}
					if(userWithPassword.getKey().equals(team2.get(0))){
						userTeam2.add(userWithPassword);
					}
					if(userWithPassword.getKey().equals(team2.get(1))){
						userTeam2.add(userWithPassword);
					}
				}
				
				
				int resultOne = Integer.parseInt(game.getProperty("resultOne").toString());
				int resultTwo = Integer.parseInt(game.getProperty("resultTwo").toString());
				//Date date = (Date) game.getProperty("date");
				String matchDayName = game.getParent().getName();
				
				
				badmintonDoubleGames.add(new BadmintonDouble(userTeam1,userTeam2,matchDayName,resultOne,resultTwo, null));
				
				
				if (team1.get(0).equals(userName)
						|| team1.get(1).equals(userName)
						||team2.get(0).equals(userName)
						||team2.get(1).equals(userName)){
					totalGamesPlayed++;
					gamesPlayed++;

				

					if(((team1.get(0).equals(userName) || team1.get(1).equals(userName) ) && resultTwo < resultOne) 
							|| 
							((team2.get(0).equals(userName) || team2.get(1).equals(userName) )  && resultOne < resultTwo)){
						totalGamesWon++;
						gamesWon++;
					}
				}
			}
			MatchDay t = new MatchDay(matchDay.getKey().getName(), matchDay.getKey(), matchDay.getParent());
			t.setGames(badmintonDoubleGames);
			t.setGamesWon(gamesWon);
			t.setGamesPlayed(gamesPlayed);
			
			matchDays.add(t);
			
			
		}
		dataskillGraph = new DisplaySkillGraph(totalGamesPlayed, totalGamesWon, totalNumberGames,matchDays);
		return dataskillGraph;




	}

}
