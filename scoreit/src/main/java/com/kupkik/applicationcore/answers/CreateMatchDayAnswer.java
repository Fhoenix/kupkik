package com.kupkik.applicationcore.answers;

/**
 * The result of trying to create a new matchday. Prefix: MATCHDAY_
 */
public enum CreateMatchDayAnswer
{
	MATCHDAY_OK,
	MATCHDAY_NAME_INVALID,
	MATCHDAY_ALREADY_EXISTS,
	MATCHDAY_SEASON_DOES_NOT_EXIST, 
	MATCHDAY_USER_CREDENTIALS_INVALID,
	MATCHDAY_DOES_NOT_EXIST
}
