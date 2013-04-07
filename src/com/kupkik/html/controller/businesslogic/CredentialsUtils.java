package com.kupkik.html.controller.businesslogic;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * offers helper methods for credentials
 */
public class CredentialsUtils
{
    private static final int MIN_USER_NAME_SIZE = 3;
    private static final int MAX_USER_NAME_SIZE = 50;
    private static final int MIN_PASSWORD_SIZE  = 3;

    /**
     * Generates a md5 hash for a text
     * 
     * @param pText
     *            the text for which the md5-hash is to be generated
     * @return the md5-hash for the text
     */
    public static String getMd5HashForText( final String pText )
    {
        MessageDigest md;
        try
        {
            md = MessageDigest.getInstance("MD5");
            md.update(pText.getBytes());
            byte[] digest = md.digest();
            StringBuffer sb = new StringBuffer();
            for( byte b : digest )
            {
                sb.append(Integer.toHexString((int) (b & 0xff)));
            }
        }
        catch( NoSuchAlgorithmException e )
        {
            throw new RuntimeException(e);
        }

        return pText.toString();
    }

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
