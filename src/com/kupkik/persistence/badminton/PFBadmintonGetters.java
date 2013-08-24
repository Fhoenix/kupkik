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
import com.kupkik.model.game.Game;
import com.kupkik.persistence.EntityNameStore;
import com.kupkik.persistence.common.PFCommonGetter;

public class PFBadmintonGetters {
	//    private static final Logger sLogger = Logger.getLogger(HtmlStarterServlet.class.getName());


	/**
	 * Get the Latest BadmintonSingleGames
	 * @param count
	 * @return
	 */
	public static List<Game> getLatestBadmintonSingleGames(int count){

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		List<Game> matches = new ArrayList<Game>();

		Query getLatestBadmintonSingleMatchesQuery = new Query("Game");
		getLatestBadmintonSingleMatchesQuery.addSort("date", Query.SortDirection.DESCENDING);
		PreparedQuery getLatestBadmintonSingleMatchesPreparedQuery = datastore.prepare(getLatestBadmintonSingleMatchesQuery);

		List<Entity> matchesEntities = getLatestBadmintonSingleMatchesPreparedQuery.asList(FetchOptions.Builder.withLimit(count));

		
		for( Entity currentEntity : matchesEntities )
		{
			List<Key> playerOne = (List<Key>) currentEntity.getProperties().get("teamOne");
			List<Key>  playerTwo = (List<Key>) currentEntity.getProperties().get("teamTwo");
			int resultOne = Integer.parseInt(currentEntity.getProperties().get("resultOne").toString());
			int resultTwo =  Integer.parseInt(currentEntity.getProperties().get("resultTwo").toString());
			
			String date = currentEntity.getProperty("date").toString();

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
			
			Game currentBadmintonGame = new Game(userTeam1,userTeam2,date,resultOne,resultTwo, currentEntity.getProperty("gameType").toString());
			matches.add(currentBadmintonGame);
		}

		return matches;

	}

	
	
	
	

}
