package com.kupkik.persistence.ranking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.kupkik.model.MatchDay;
import com.kupkik.model.Season;
import com.kupkik.model.User;
import com.kupkik.model.UserWithPassword;
import com.kupkik.model.game.Game;
import com.kupkik.model.ranking.TypedWinLooseRanking;
import com.kupkik.model.ranking.WinLooseRanking;
import com.kupkik.model.ranking.WinLooseRows;
import com.kupkik.persistence.EntityNameStore;
import com.kupkik.persistence.common.PFCommonGetter;

public class PFRankingGetters {

	public static WinLooseRanking getWinLoosRanking(Key seasonKey) {





		//Get all Users TODO COS get only Users connected to a season
		List<UserWithPassword> allUsers = PFCommonGetter.getAllUsers();

		//Get the GameTypes for a Season
		Season seasonByKey = PFCommonGetter.getSeasonByKey(seasonKey);
		List<String> gameTypes = seasonByKey.getGameTypes();

		List<TypedWinLooseRanking> overallRanking = new ArrayList<TypedWinLooseRanking>();


		for (String string : gameTypes) {
			List<WinLooseRows> winLooseRow = new ArrayList<WinLooseRows>();
			//Get all MatchDays for the Season
			List<MatchDay> matchDaysForSeason = PFCommonGetter.getMatchDaysForSeason(seasonKey, string);


			for (UserWithPassword userWithPassword : allUsers) {
				Key key = userWithPassword.getKey();

				int totalGamesPlayed = 0;
				int totalGamesWon = 0;
				int totalNumberGames = 0;


				//For all Users search 
				for (MatchDay m : matchDaysForSeason){

					List<Game> games = m.getGames();
					for (Game iGame : games) {
						totalNumberGames++;
						List<User> teamOne = iGame.getTeamOne();
						for (User user : teamOne) {
							if(user.getKey().equals(key)){
								totalGamesPlayed++;
								if( iGame.getResultOne() > iGame.getResultTwo()){
									totalGamesWon++;
								}
							}
						}


						List<User> teamTwo = iGame.getTeamTwo();
						for (User user : teamTwo) {
							if(user.getKey().equals(key)){
								totalGamesPlayed++;
								if( iGame.getResultTwo() > iGame.getResultOne()){
									totalGamesWon++;
								}
							}
						}						
					}
				}

				winLooseRow.add( new WinLooseRows(userWithPassword, totalGamesPlayed, totalGamesWon, totalNumberGames));


			}


			Collections.sort(winLooseRow,  new WinLooseRowComperator());
			overallRanking.add( new TypedWinLooseRanking(winLooseRow,string));

		}

		return  new WinLooseRanking(overallRanking);

	}

	private static class WinLooseRowComperator
	implements Comparator<WinLooseRows>
	{
		@Override
		public int compare( WinLooseRows row1, WinLooseRows row2 )
		{
			double delta = row1.getGamesWonInPercent() - row2.getGamesWonInPercent();
			if(delta > 0.00001) return -1;
			if(delta < -0.00001) return 1;
			return 0;

		}
	}
}
