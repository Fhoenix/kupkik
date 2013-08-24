package com.kupkik.applicationcore.answers;

/** The result of trying to create a new user. Prefix: USER_*/
public enum SaveUserAnswer
{
	USER_OK,
	USER_ALREADY_EXISTS,
	USER_NAME_TOO_SHORT,
	USER_NAME_TOO_LONG,
	USER_NAME_USES_INVALID_CHARACTERS,
	USER_FIRSTNAME_INVALID,
	USER_SURNAME_INVALID,
	USER_PASSWORD_INVALID
}