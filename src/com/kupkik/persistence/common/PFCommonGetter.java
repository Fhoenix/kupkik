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
import com.kupkik.model.MatchDay;
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
    
    public static UserWithPassword getUserByKey(Key userKey){
    	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    	Filter keyFilter = new FilterPredicate(Entity.KEY_RESERVED_PROPERTY, FilterOperator.EQUAL, userKey);
    	Query getUserQuery = new Query("User").setFilter(keyFilter);
		PreparedQuery getUser = datastore.prepare(getUserQuery);
		
		
		List<Entity> userEntity = getUser.asList(FetchOptions.Builder.withLimit(1));
        
		UserWithPassword userWithPassword = null;
        for (Entity item: userEntity){
        	userWithPassword = new UserWithPassword(item.getKey().getName(),item.getProperty("passwordMd5").toString(),item.getKey(), item.getProperty("firstname").toString(),item.getProperty("surname").toString());
        }
        return userWithPassword;
        
	}
    
	/**
	 * Get all MatchDays in database
	 * 
	 * @return all MatchDays, not ordered
	 */
	public static List<MatchDay> getAllMatchDays()
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query getAllMatchDaysQuery = new Query("MatchDay");
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
	 * Get MatchDay by MatchDay Key
	 * @param matchDayKey matchDayKey
	 * @return Instance of {@link MatchDay}
	 */
    public static MatchDay getMatchDayByKey(Key matchDayKey){
    	
    	DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();
    	Filter filterName = new FilterPredicate("Key", FilterOperator.EQUAL,matchDayKey );
    	
    	Query getMatchDayKey = new Query("MatchDay").setFilter(filterName);
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
	 * Get all MatchDays that belong to one User.
	 * @param userName Users name.
	 * @return a List of {@link MatchDay}.
	 */
	public static List<MatchDay> getAllMatchDaysOfUser(String userName) {
		
		List<Season> seasons = getAllSeasonsForUser(userName);
		
		List<MatchDay> result = new ArrayList<MatchDay>();
		
		for(Season item :seasons){
		
			DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();			
			Query getMatchDayKey = new Query("MatchDay").setAncestor(item.getKey());
			
			PreparedQuery pQGetSeason = dataStore.prepare(getMatchDayKey);
			List<Entity> seasonEntity = pQGetSeason.asList(FetchOptions.Builder.withDefaults());
			
			 for (Entity entity: seasonEntity){
				 result.add( new MatchDay(entity.getKey().getName(), entity.getKey(), entity.getParent()));
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
