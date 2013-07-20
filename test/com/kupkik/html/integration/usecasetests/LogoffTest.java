package com.kupkik.html.integration.usecasetests;

import java.util.HashMap;

import org.junit.Test;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.kupkik.html.integration.UseCaseTestParent;
import com.kupkik.model.UserWithPassword;
import com.kupkik.ui.html.HtmlRequestProcessor;
import com.kupkik.utils.CredentialsUtils;

public class LogoffTest
        extends UseCaseTestParent
{
    @Test
    public void logoffUser() throws Exception
    {
        // prepare the test

        final String userName = "usul";
        final String password = "qwert";
        final String md5Password = CredentialsUtils.getMd5HashForText(password);
        final Key key = KeyFactory.createKey("User", userName);
        final String firstname = "qwert";
        final String surname = "qwert";
        UserWithPassword currentUser = new UserWithPassword(userName,md5Password,key,firstname, surname);

        HashMap<String, Object> attributesForSession = new HashMap<>();
        attributesForSession.put("currentUser", currentUser);

        HashMap<String, String> parametersForRequest = new HashMap<>();
        parametersForRequest.put("action", "Logoff");
        parametersForRequest.put("showView", null);

        // run the test

        simulateUserRequest(attributesForSession, parametersForRequest);

        // check the results
        
        checkThatViewWasShown("/views/MainView.jsp");
        // check that only the guest-user has been set in session
        checkThatOnlyUserHasBeenSetInSessionAndSameUserHasBeenSetInHttpRequest(HtmlRequestProcessor.GUEST_USER.getName(), 
                HtmlRequestProcessor.GUEST_USER.getPasswordMd5());
        // nothing should have been saved
        checkForNoPersistanceToDatabase();
    }
}
