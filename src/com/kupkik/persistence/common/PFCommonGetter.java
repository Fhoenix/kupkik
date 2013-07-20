package com.kupkik.persistence.common;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.kupkik.model.Season;
import com.kupkik.model.Tournament;
import com.kupkik.model.User;
import com.kupkik.model.UserWithPassword;

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
    
	/**
	 * Get all Tournaments in database
	 * 
	 * @return all tournaments, not ordered
	 */
	public static List<Tournament> getAllTournaments()
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query getAllTournamentsQuery = new Query("Tournament");
		PreparedQuery getAllTournamentsPreparedQuery = datastore.prepare(getAllTournamentsQuery);

		List<Entity> userEntities = getAllTournamentsPreparedQuery.asList(FetchOptions.Builder.withDefaults());
		List<Tournament> tournaments = new ArrayList<Tournament>();
		for( Entity currentTournamentEntity : userEntities )
		{
			Tournament currentTournament = new Tournament(currentTournamentEntity.getKey().getName(), currentTournamentEntity.getKey(), currentTournamentEntity.getParent());
			tournaments.add(currentTournament);
		}

		return tournaments;
	}

	/**
	 * Get Tournament by Tournament Key
	 * @param tournamentKey tournamentKey
	 * @return Instance of {@link Tournament}
	 */
    public static Tournament getTournamentByKey(Key tournamentKey){
    	
    	DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();
    	Filter filterName = new FilterPredicate("Key", FilterOperator.EQUAL,tournamentKey );
    	
    	Query getTournamentKey = new Query("Tournament").setFilter(filterName);
    	PreparedQuery pQGetTournament = dataStore.prepare(getTournamentKey);
    	
        List<Entity> tournamentEntity = pQGetTournament.asList(FetchOptions.Builder.withLimit(1));
        
        Tournament tournament = null;
        for (Entity item: tournamentEntity){
        	tournament = new Tournament(item.getKey().getName(), item.getKey(), item.getParent());
        }
        
        return tournament;
    }
    
	/**
	 * Gets all users 
	 * @return List<UserWithPassword>
	 */
	public static List<UserWithPassword> getAllUsers()
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query getAllUsersQuery = new Query("User");
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
	 * Get all Tournaments that belong to one User.
	 * @param userName Users name.
	 * @return a List of {@link Tournament}.
	 */
	public static List<Tournament> getAllTournamentsOfUser(String userName) {
		
		List<Season> seasons = getAllSeasonsForUser(userName);
		
		List<Tournament> result = new ArrayList<Tournament>();
		
		for(Season item :seasons){
		
			DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();			
			Query getTournamentKey = new Query("Tournament").setAncestor(item.getKey());
			
			PreparedQuery pQGetSeason = dataStore.prepare(getTournamentKey);
			List<Entity> seasonEntity = pQGetSeason.asList(FetchOptions.Builder.withDefaults());
			
			 for (Entity entity: seasonEntity){
				 result.add( new Tournament(entity.getKey().getName(), entity.getKey(), entity.getParent()));
		        }			 
		}
		return result;	
	}
	
	/**
	 * Get all Seasons that belongs to an user.
	 * @param userName user name.
	 * @return List of {@link Season}.
	 */
	public static List<Season> getAllSeasonsForUser(String userName) {
		
		List<Season> seasons = new ArrayList<Season>();
		
		if(PFCommonTester.doesUserExistWithName(userName)){
			User userByName = getUserByName(userName);
			DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();			
			Query getSeasonKey = new Query("Season").setAncestor(userByName.getKey());
			
			PreparedQuery pQGetSeason = dataStore.prepare(getSeasonKey);
			List<Entity> seasonEntity = pQGetSeason.asList(FetchOptions.Builder.withDefaults());
			
			
			 for (Entity item: seasonEntity){
				 seasons.add( new Season(item.getKey().getName(), item.getKey(), item.getParent()));
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
			Query getSeasonKey = new Query("Season");
			
			PreparedQuery pQGetSeason = dataStore.prepare(getSeasonKey);
			List<Entity> seasonEntity = pQGetSeason.asList(FetchOptions.Builder.withDefaults());
			
			
			 for (Entity item: seasonEntity){
				 seasons.add( new Season(item.getKey().getName(), item.getKey(), item.getParent()));
		        }
		
		return seasons;	
	}

}
