package com.kupkik.messages;

import com.kupkik.applicationcore.ApplicationCoreFacade;

/**
 * Naming for constants are HANDLERNAME_TEXT
 * @author scheuermann
 *
 */
public class MessageError {


	public static final String SEASON_NAME_ALREADY_EXISTS = "Es existiert bereits ein Saison mit diesem Namen!";
	public static final String SEASON_NO_SUFFICIENT_RIGHTS_TO_CREATE_SEASON = "Sie sind nicht dazu berechtigt eine Saison anzulegen.";
	public static final String SEASON_USER_NOT_LOGGED_IN = "Nur eingeloggte Nutzer k?nnen Turniere anlegen!";
	public static final String SEASON_NO_SEASONS_AVAILABLE = "Keine Saisons vorhanden!";
	public static final String SEASON_NAME_INVALID = "Der Name der Saison muss zwischen " + 
	        ApplicationCoreFacade.MIN_SEASON_NAME_SIZE
            + " und " + ApplicationCoreFacade.MAX_SEASON_NAME_SIZE
            + " Zeichen lang sein und darf nur Buchstaben, Zahlen, Leerzeichen " + " oder Unterstriche enthalten!";
	public static final String SEASON_NEEDS_GAMETYPES = "Please select at least one GameType!";
	
	//User Registration
	public static final String REGISTER_PASSWORD_NOT_EUQUAL = "Die Passwörter sind nicht gleich!";
	public static final String REGISTER_PASSWORD_LENGTH_INVALID = "Das Passwort muss mindestens " + ApplicationCoreFacade.MIN_PASSWORD_SIZE + " Zeichen lang sein!";
	public static final String REGISTER_USER_ALREADY_EXISTS = "Es existiert bereits ein Benutzer mit dem Namen ";
	public static final String REGISTER_USER_NAME_TOO_LONG = "BENUTERNAME ZU LANG. Maximale Länge "+   + ApplicationCoreFacade.MAX_USER_NAME_SIZE ;
	public static final String REGISTER_USER_NAME_TOO_SHORT = "Der Benutzername muss mindestens " + ApplicationCoreFacade.MIN_USER_NAME_SIZE ;
	public static final String REGISTER_USERNAME_INVALID_CHARACTER = "Unzulässige Zeichen im Nutzernamen";
	public static final String REGISTER_FIRSTNAME_INVALID = "Firstname invalide! " ;
	public static final String REGISTER_SURNAME_INVALID = "Surname invalid";
	
	//MATCHDAY
	public static final String MATCHDAY_NO_SUFFICIENT_RIGHTS_TO_CREATE_SEASON = "Sind sind nicht berechtigt Turniere zu erstellen";
	public static final String MATCHDAY_NAME_INVALID = "Der Name des Turniers muss zwischen " + ApplicationCoreFacade.MIN_MATCHDAY_NAME_SIZE
    + " und " + ApplicationCoreFacade.MAX_MATCHDAY_NAME_SIZE
    + " Zeichen lang sein und darf nur Buchstaben, Zahlen, Leerzeichen " + " oder Unterstriche enthalten!";
	public static final String MATCHDAY_SEASON_DOES_NOT_EXIST = "Season does not exist";
	public static final String MATCHDAY_WITH_THAT_NAME_EXISTS = "Es existiert bereits ein Turnier mit diesem Namen!";


	//Badminton Single
	public static final String BADMINTON_SINGLE_MIN_POINTS_NOT_REACHED = "Keines der Spiele hat die minimale Punktezahlt "+ ApplicationCoreFacade.MIN_END_RESULT + "erreicht!";
	public static final String BADMINTON_RESULT_INVALID = "Die Ergebnisse nicht nicht valide! PunkteAbstand stimmt nicht!";
	public static final String BADMINTON_SINGLE_USER_EQUALS_EACH_OTHER = "USER EQUALS EACH OTHER";
	public static final String BADMINTON_SINGLE_USER_NOT_LOGGED_IN = "Nur eingeloggte Nutzer können Spiele anlegen!";
	
	//Badminton Double
	public static final String BADMINTON_DOUBLE_NOT_LOGGED_IN = "Nur eingeloggte Nutzer können Spiele anlegen!";
	public static final String BADMINTON_DOUBLE_ERROR_WHILE_CREATING_GAME = "Das Spiel konnte nicht angelegt werden!";

	//Profile
	public static final String STATISTICS_NOT_LOGGED_IN = "Nur eingeloggte Nutzer können Statistiken sehen!";
	
	//Login
	public static final String LOGIN_USER_ALREADY_EXISTS = "Es existiert kein Nutzer mit diesem Namen und Passwort.";
}
