package com.kupkik.html.integration.usecasetests;

import java.util.HashMap;

import org.junit.Test;

import com.kupkik.html.integration.UseCaseTestParent;
import com.kupkik.ui.html.HtmlRequestProcessor;

public class ShowViewTest
        extends UseCaseTestParent
{
    @Test
    public void showMainView() throws Exception
    {
        runTest("MainView");
    }

    @Test
    public void showLoginView() throws Exception
    {
        runTest("LoginView");
    }

    @Test
    public void showRegisterView() throws Exception
    {
        runTest("RegisterView");
    }

    public void runTest( final String pViewName ) throws Exception
    {
        // prepare the test

        HashMap<String, String> attributesForSession = new HashMap<>();
        attributesForSession.put("currentUser", null);

        HashMap<String, String> parametersForRequest = new HashMap<>();
        parametersForRequest.put("action", null);
        parametersForRequest.put("showView", pViewName);

        // run the test

        simulateUserRequest(attributesForSession, parametersForRequest);

        // check the results

        checkThatViewWasShown("/views/" + pViewName + ".jsp");
        // check that only the guest-user has been set in session
        checkThatOnlyUserHasBeenSetInSessionAndSameUserHasBeenSetInHttpRequest(HtmlRequestProcessor.GUEST_USER.getName(),
                HtmlRequestProcessor.GUEST_USER.getPasswordMd5());
        // nothing should have been saved
        checkForNoPersistanceToDatabase();
    }
}
