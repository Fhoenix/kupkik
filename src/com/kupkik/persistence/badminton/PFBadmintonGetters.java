package com.kupkik.persistence.badminton;

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
import com.kupkik.model.Tournament;
import com.kupkik.model.game.BadmintonSingle;

public class PFBadmintonGetters {
	//    private static final Logger sLogger = Logger.getLogger(HtmlStarterServlet.class.getName());


	/**
	 * Get the Latest BadmintonSingleGames
	 * @param count
	 * @return
	 */
	public static List<BadmintonSingle> getLatestBadmintonSingleGames(int count){

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Query getLatestBadmintonSingleMatchesQuery = new Query("BadmintonSingle");
		getLatestBadmintonSingleMatchesQuery.addSort("date", Query.SortDirection.DESCENDING);
		PreparedQuery getLatestBadmintonSingleMatchesPreparedQuery = datastore.prepare(getLatestBadmintonSingleMatchesQuery);

		List<Entity> matchesEntities = getLatestBadmintonSingleMatchesPreparedQuery.asList(FetchOptions.Builder.withLimit(count));

		List<BadmintonSingle> matches = new ArrayList<BadmintonSingle>();
		for( Entity currentEntity : matchesEntities )
		{
			String playerOne = currentEntity.getProperties().get("playerOne").toString();
			String playerTwo = currentEntity.getProperties().get("playerOne").toString();
			int resultOne = Integer.parseInt(currentEntity.getProperties().get("resultOne").toString());
			int resultTwo =  Integer.parseInt(currentEntity.getProperties().get("resultTwo").toString());
			String tournamentName = currentEntity.getParent().getName();
			Date date = (Date) currentEntity.getProperties().get("date");


			BadmintonSingle currentBadmintonGame = new BadmintonSingle(playerOne,playerTwo,resultOne,resultTwo,date,tournamentName);
			matches.add(currentBadmintonGame);
		}

		return matches;

	}

	public  static DisplaySkillGraph getAllGamesInSeason(Key season, String userName){

		
		DisplaySkillGraph dataskillGraph = null;
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();
		Query getTournamentsBySeasonKey = new Query("Tournament").setAncestor(season);
		PreparedQuery PQTournaments = dataStore.prepare(getTournamentsBySeasonKey);

		List<Entity> tournamentEntity = PQTournaments.asList(FetchOptions.Builder.withDefaults());

		int gamesPlayed = 0;
		int gamesWon = 0;
		List<BadmintonSingle> badmintonSingleGames = new ArrayList<BadmintonSingle>();

		for (Entity tournament: tournamentEntity){

			DatastoreService dataStore2 = DatastoreServiceFactory.getDatastoreService();
			Query getGamesForTournament = new Query("BadmintonSingle").setAncestor(tournament.getKey());
			PreparedQuery PQGames = dataStore2.prepare(getGamesForTournament);
			List<Entity> games = PQGames.asList(FetchOptions.Builder.withDefaults());	
			
			for(Entity game : games){
				String playerOne = game.getProperty("playerOne").toString();
				
				
				String playerTwo = game.getProperty("playerTwo").toString();
				int resultOne = Integer.parseInt(game.getProperty("resultOne").toString());
				int resultTwo = Integer.parseInt(game.getProperty("resultTwo").toString());
				//Date date = (Date) game.getProperty("date");
				String tournamentName = game.getParent().getName();
				
				badmintonSingleGames.add(new BadmintonSingle(playerOne, playerTwo, resultOne, resultTwo, null, tournamentName));
				
				if (playerOne.equals(userName)
						|| playerTwo.equals(userName)){
					gamesPlayed++;

				

					if((playerOne.equals(userName) && resultTwo < resultOne) 
							|| (playerTwo.equals(userName) && resultOne < resultTwo) ){
						gamesWon++;
					}
				}
			}
			
		}
		dataskillGraph = new DisplaySkillGraph(gamesPlayed, gamesWon,badmintonSingleGames);
		return dataskillGraph;




	}

}
