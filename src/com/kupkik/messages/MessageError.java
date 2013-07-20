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
	
	//User Registration
	public static final String REGISTER_PASSWORD_NOT_EUQUAL = "Die Passwörter sind nicht gleich!";
	public static final String REGISTER_PASSWORD_LENGTH_INVALID = "Das Passwort muss mindestens " + ApplicationCoreFacade.MIN_PASSWORD_SIZE + " Zeichen lang sein!";
	public static final String REGISTER_USER_ALREADY_EXISTS = "Es existiert bereits ein Benutzer mit dem Namen ";
	public static final String REGISTER_USER_NAME_TOO_LONG = "BENUTERNAME ZU LANG. Maximale Länge "+   + ApplicationCoreFacade.MAX_USER_NAME_SIZE ;
	public static final String REGISTER_USER_NAME_TOO_SHORT = "Der Benutzername muss mindestens " + ApplicationCoreFacade.MIN_USER_NAME_SIZE ;
	public static final String REGISTER_USERNAME_INVALID_CHARACTER = "Unzulässige Zeichen im Nutzernamen";
	public static final String REGISTER_FIRSTNAME_INVALID = "Firstname invalide! " ;
	public static final String REGISTER_SURNAME_INVALID = "Surname invalid";
	
	//Tournament
	public static final String TOURNAMENT_NO_SUFFICIENT_RIGHTS_TO_CREATE_SEASON = "Sind sind nicht berechtigt Turniere zu erstellen";
	public static final String TOURNAMENT_NAME_INVALID = "Der Name des Turniers muss zwischen " + ApplicationCoreFacade.MIN_TOURNAMENT_NAME_SIZE
    + " und " + ApplicationCoreFacade.MAX_TOURNAMENT_NAME_SIZE
    + " Zeichen lang sein und darf nur Buchstaben, Zahlen, Leerzeichen " + " oder Unterstriche enthalten!";
	public static final String TOURNAMENT_SEASON_DOES_NOT_EXIST = "Season does not exist";
	public static final String TOURNAMENT_WITH_THAT_NAME_EXISTS = "Es existiert bereits ein Turnier mit diesem Namen!";
	public static final String TOURNAMENT_SUCCESSFULLY_ADDED = "Tournament successfully added!";


}
