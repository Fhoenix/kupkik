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
			List<Key> playerOne = (List<Key>) currentEntity.getProperties().get("teamOne");
			List<Key>  playerTwo = (List<Key>) currentEntity.getProperties().get("teamTwo");
			int resultOne = Integer.parseInt(currentEntity.getProperties().get("resultOne").toString());
			int resultTwo =  Integer.parseInt(currentEntity.getProperties().get("resultTwo").toString());
			String matchDayName = currentEntity.getParent().getName();
			Date date = (Date) currentEntity.getProperties().get("date");

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
			
			BadmintonSingle currentBadmintonGame = new BadmintonSingle(userTeam1,userTeam2,resultOne,resultTwo,date,matchDayName);
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
				
				badmintonSingleGames.add(new BadmintonSingle(userTeam1, userTeam2, resultOne, resultTwo, null, matchDayName));
				
				
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
				//Date date = (Date) game.getProperty("date");
				String matchDayName = game.getParent().getName();
				
				
				badmintonDoubleGames.add(new BadmintonDouble(userTeam1,userTeam2,matchDayName,resultOne,resultTwo, null));
				
				
			
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
			t.setGames(badmintonDoubleGames);
			t.setGamesWon(gamesWon);
			t.setGamesPlayed(gamesPlayed);
			
			matchDays.add(t);
			
			
		}
		dataskillGraph = new DisplaySkillGraph(totalGamesPlayed, totalGamesWon, totalNumberGames,matchDays);
		return dataskillGraph;




	}

}
