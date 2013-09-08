package com.kupkik.applicationcore.answers;

/**
 * The result of trying to create a new Season.
 */
public enum CreateSeasonAnswer
{
	SEASON_OK,
	SEASON_NAME_INVALID,
	SEASON_ALREADY_EXISTS,
	SEASON_USER_CREDENTIALS_INVALID
}