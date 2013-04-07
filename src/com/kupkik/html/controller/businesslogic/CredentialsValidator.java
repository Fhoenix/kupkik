package com.kupkik.html.controller.businesslogic;

/**
 * For validating credentials.
 */
public class CredentialsValidator
{
    private static final int MIN_USER_NAME_SIZE = 3;
    private static final int MAX_USER_NAME_SIZE = 50;
    private static final int MIN_PASSWORD_SIZE  = 3;

    /**
     * Are the credentials valid?
     * 
     * @param pUserName
     *            the chosen user-name
     * @param pPassword1
     *            the chosen password
     * @param pPassword2
     *            the chosen password for comparison
     * @return an error message if the credentials are not valid or "null" if
     *         they are valid
     */
    public static String areCredentialsValid( final String pUserName, final String pPassword1, final String pPassword2 )
    {
        // validate user name

        if( pUserName.length() < MIN_USER_NAME_SIZE )
        {
            return "Der Benutzername muss mindestens " + MIN_USER_NAME_SIZE + " Zeichen lang sein!";
        }
        if( pUserName.length() > MAX_USER_NAME_SIZE )
        {
            return "Der Benutzername darf maximal " + MAX_USER_NAME_SIZE + " Zeichen lang sein!";
        }
        if( !pUserName.matches("[0-9a-zA-Z_]*") )
        {
            return "Für den Benutzernamen sind nur Buchstaben, Ziffern oder Unterstriche erlaubt!";
        }

        // validate password

        else if( !pPassword1.equals(pPassword2) )
        {
            return "Die Passwörter sind nicht gleich!";
        }
        else if( pPassword1.length() < MIN_PASSWORD_SIZE )
        {
            return "Das Passwort muss mindestens so viele Zeichen haben: " + MIN_PASSWORD_SIZE;
        }

        // everything is OK

        return null;
    }
}
