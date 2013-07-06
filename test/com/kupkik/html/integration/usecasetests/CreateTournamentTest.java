package com.kupkik.html.integration.usecasetests;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.kupkik.html.integration.UseCaseTestParent;
import com.kupkik.model.UserWithPassword;
import com.kupkik.ui.html.HtmlRequestProcessor;
import com.kupkik.utils.CredentialsUtils;

public class CreateTournamentTest
        extends UseCaseTestParent
{
    
    @Test
    public void createTournamentOk() throws Exception
    {
        // prepare the test
        
        final String userName = "usul";
        final String password = "qwert";
        final Key key = KeyFactory.createKey("User", userName);
        
        final String md5Password = CredentialsUtils.getMd5HashForText(password);
        UserWithPassword currentUser = new UserWithPassword(userName,md5Password,key);

        final String tournamentName = "A test tournament";

        HashMap<String, Object> attributesForSession = new HashMap<>();
        attributesForSession.put("currentUser", currentUser);

        HashMap<String, String> parametersForRequest = new HashMap<>();
        parametersForRequest.put("action", "CreateTournament");
        parametersForRequest.put("showView", null);
        parametersForRequest.put("name", tournamentName);

       // when(mPersistenceFacadeMock.doesUserExistWithNameAndMd5Password(userName, md5Password)).thenReturn(true);
       // when(mPersistenceFacadeMock.doesTournamentExistWithName(tournamentName)).thenReturn(false);

        // run the test

        simulateUserRequest(attributesForSession, parametersForRequest);

        // check the results
        
        checkThatViewWasShown("/views/MainView.jsp");
        // check that no error message has been set in the http-request
        String errorMessage = (String) mHttpServletRequestMock.getAttributsSet().get("errorMessage");
        Assert.assertNull(errorMessage);
        // check that user is still logged in
        checkThatUserIsStillLoggedIn(userName, md5Password);
        // only one tournament should have been saved
       
        //TODO COS: MODIFY THIS METHOD
        //verify(mPersistenceFacadeMock, times(1)).saveNewTournament(any(), anyString());
        // only our tournament should have been saved
      //TODO COS: MODIFY THIS METHOD
//        verify(mPersistenceFacadeMock, times(1)).saveNewTournament(tournamentName, userName);
    }
    
    @Test
    public void createTournamentFailsBecauseNotLoggedIn() throws Exception
    {
        // prepare the test

        final String tournamentName = "A test tournament";

        HashMap<String, Object> attributesForSession = new HashMap<>();
        attributesForSession.put("currentUser", null);

        HashMap<String, String> parametersForRequest = new HashMap<>();
        parametersForRequest.put("action", "CreateTournament");
        parametersForRequest.put("showView", null);
        parametersForRequest.put("name", tournamentName);

//        when(mPersistenceFacadeMock.doesTournamentExistWithName(tournamentName)).thenReturn(false);

        // run the test

        simulateUserRequest(attributesForSession, parametersForRequest);

        // check the results
        
        checkThatViewWasShown("/views/NewTournamentView.jsp");
        // check that an error message has been set in the http-request
        String errorMessage = (String) mHttpServletRequestMock.getAttributsSet().get("errorMessage");
        Assert.assertTrue(errorMessage.length() > 1);
        // nothing should have been saved
        checkForNoPersistanceToDatabase();
        // check that only the guest-user has been set in session
        checkThatOnlyUserHasBeenSetInSessionAndSameUserHasBeenSetInHttpRequest(HtmlRequestProcessor.GUEST_USER.getName(), 
                HtmlRequestProcessor.GUEST_USER.getPasswordMd5());
    }
    
    @Test
    public void createTournamentFailsBecauseNameTooLong() throws Exception
    {
        // prepare the test
        
        final String userName = "usul";
        final String password = "qwert";
        final String md5Password = CredentialsUtils.getMd5HashForText(password);
        final Key key = KeyFactory.createKey("User", userName);
        
        UserWithPassword currentUser = new UserWithPassword(userName,md5Password,key);

        final String tournamentName = "adddddddddddddddddddddddddddddddddddfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff"
                + "adddddddddddddddddddddddddddddddddddfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff"
                + "adddddddddddddddddddddddddddddddddddfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff";

        HashMap<String, Object> attributesForSession = new HashMap<>();
        attributesForSession.put("currentUser", currentUser);

        HashMap<String, String> parametersForRequest = new HashMap<>();
        parametersForRequest.put("action", "CreateTournament");
        parametersForRequest.put("showView", null);
        parametersForRequest.put("name", tournamentName);

//        when(mPersistenceFacadeMock.doesUserExistWithNameAndMd5Password(userName, md5Password)).thenReturn(true);
//        when(mPersistenceFacadeMock.doesTournamentExistWithName(tournamentName)).thenReturn(false);

        // run the test

        simulateUserRequest(attributesForSession, parametersForRequest);

        // check the results
        
        checkThatViewWasShown("/views/NewTournamentView.jsp");
        // check that an error message has been set in the http-request
        String errorMessage = (String) mHttpServletRequestMock.getAttributsSet().get("errorMessage");
        Assert.assertTrue(errorMessage.length() > 1);
        // nothing should have been saved
        checkForNoPersistanceToDatabase();
        // check that user is still logged in
        checkThatUserIsStillLoggedIn(userName, md5Password);
    }
    
    @Test
    public void createTournamentFailsBecauseNameTooShort() throws Exception
    {
        // prepare the test
        
        final String userName = "usul";
        final String password = "qwert";
        final String md5Password = CredentialsUtils.getMd5HashForText(password);
        final Key key = KeyFactory.createKey("User", userName);
        UserWithPassword currentUser = new UserWithPassword(userName,md5Password,key);

        final String tournamentName = "a";

        HashMap<String, Object> attributesForSession = new HashMap<>();
        attributesForSession.put("currentUser", currentUser);

        HashMap<String, String> parametersForRequest = new HashMap<>();
        parametersForRequest.put("action", "CreateTournament");
        parametersForRequest.put("showView", null);
        parametersForRequest.put("name", tournamentName);

//        when(mPersistenceFacadeMock.doesUserExistWithNameAndMd5Password(userName, md5Password)).thenReturn(true);
//        when(mPersistenceFacadeMock.doesTournamentExistWithName(tournamentName)).thenReturn(false);

        // run the test

        simulateUserRequest(attributesForSession, parametersForRequest);

        // check the results
        
        checkThatViewWasShown("/views/NewTournamentView.jsp");
        // check that an error message has been set in the http-request
        String errorMessage = (String) mHttpServletRequestMock.getAttributsSet().get("errorMessage");
        Assert.assertTrue(errorMessage.length() > 1);
        // nothing should have been saved
        checkForNoPersistanceToDatabase();
        // check that user is still logged in
        checkThatUserIsStillLoggedIn(userName, md5Password);
    }
    
    @Test
    public void createTournamentFailsBecauseNameContainsInvalidCharacters() throws Exception
    {
        // prepare the test
        
        final String userName = "usul";
        final String password = "qwert";
        final String md5Password = CredentialsUtils.getMd5HashForText(password);
        final Key key = KeyFactory.createKey("User", userName);
        UserWithPassword currentUser = new UserWithPassword(userName,md5Password,key);

        final String tournamentName = "A test@ tournament";

        HashMap<String, Object> attributesForSession = new HashMap<>();
        attributesForSession.put("currentUser", currentUser);

        HashMap<String, String> parametersForRequest = new HashMap<>();
        parametersForRequest.put("action", "CreateTournament");
        parametersForRequest.put("showView", null);
        parametersForRequest.put("name", tournamentName);

//        when(mPersistenceFacadeMock.doesUserExistWithNameAndMd5Password(userName, md5Password)).thenReturn(true);
//        when(mPersistenceFacadeMock.doesTournamentExistWithName(tournamentName)).thenReturn(false);

        // run the test

        simulateUserRequest(attributesForSession, parametersForRequest);

        // check the results
        
        checkThatViewWasShown("/views/NewTournamentView.jsp");
        // check that an error message has been set in the http-request
        String errorMessage = (String) mHttpServletRequestMock.getAttributsSet().get("errorMessage");
        Assert.assertTrue(errorMessage.length() > 1);
        // nothing should have been saved
        checkForNoPersistanceToDatabase();
        // check that user is still logged in
        checkThatUserIsStillLoggedIn(userName, md5Password);
    }
    
    @Test
    public void createTournamentFailsBecauseTournamentAlreadyExists() throws Exception
    {
        // prepare the test
        
        final String userName = "usul";
        final String password = "qwert";
        final String md5Password = CredentialsUtils.getMd5HashForText(password);
        final Key key = KeyFactory.createKey("User", userName);
        UserWithPassword currentUser = new UserWithPassword(userName,md5Password,key);

        final String tournamentName = "A test tournament";

        HashMap<String, Object> attributesForSession = new HashMap<>();
        attributesForSession.put("currentUser", currentUser);

        HashMap<String, String> parametersForRequest = new HashMap<>();
        parametersForRequest.put("action", "CreateTournament");
        parametersForRequest.put("showView", null);
        parametersForRequest.put("name", tournamentName);

//        when(mPersistenceFacadeMock.doesUserExistWithNameAndMd5Password(userName, md5Password)).thenReturn(true);
//        when(mPersistenceFacadeMock.doesTournamentExistWithName(tournamentName)).thenReturn(true);

        // run the test

        simulateUserRequest(attributesForSession, parametersForRequest);

        // check the results
        
        checkThatViewWasShown("/views/NewTournamentView.jsp");
        // check that an error message has been set in the http-request
        String errorMessage = (String) mHttpServletRequestMock.getAttributsSet().get("errorMessage");
        Assert.assertTrue(errorMessage.length() > 1);
        // nothing should have been saved
        checkForNoPersistanceToDatabase();
        // check that user is still logged in
        checkThatUserIsStillLoggedIn(userName, md5Password);
    }
}
