package com.kupkik.html.controller.businesslogic;

import java.util.List;

import com.kupkik.model.User;
import com.kupkik.model.UserWithPassword;
import com.kupkik.persistence.IPersistenceFacade;

public class PersistenceFacadeMock
        implements IPersistenceFacade
{
    private List<UserWithPassword> mUsersInDatabase;

    public PersistenceFacadeMock(List<UserWithPassword> pUsersInDatabase)
    {
        mUsersInDatabase = pUsersInDatabase;
    }

    @Override
    public boolean doesUserAlreadyExist( User pUser )
    {
        for( UserWithPassword currentUserInDatabase : mUsersInDatabase )
        {
            if( pUser.getName().equals(currentUserInDatabase.getName()) )
            {
                return true;
            }
        }

        return false;
    }

    @Override
    public void saveNewUser( UserWithPassword pNewUser )
    {
        mUsersInDatabase.add(pNewUser);
    }

}
