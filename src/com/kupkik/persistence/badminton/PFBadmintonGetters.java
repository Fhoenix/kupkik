package com.kupkik.persistence.badminton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
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
        	
        	Date date = (Date) currentEntity.getProperties().get("date");
        	
        	
        	BadmintonSingle currentBadmintonGame = new BadmintonSingle(playerOne,playerTwo,resultOne,resultTwo,date);
        	matches.add(currentBadmintonGame);
        }

        return matches;
    	
    }
	
}
