package com.kupkik.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * offers helper methods
 */
public class CredentialsUtils
{
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
        StringBuffer sb = new StringBuffer();
        try
        {
            md = MessageDigest.getInstance("MD5");
            md.update(pText.getBytes());
            byte[] digest = md.digest();
            for( byte b : digest )
            {
                sb.append(Integer.toHexString((int) (b & 0xff)));
            }
        }
        catch( NoSuchAlgorithmException e )
        {
            throw new RuntimeException(e);
        }

        return sb.toString();
    }
}
