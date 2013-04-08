package com.kupkik.persistence;

import com.kupkik.model.User;
import com.kupkik.model.UserWithPassword;

public class PersistenceFacade
        implements IPersistenceFacade
{

    @Override
    public boolean doesUserAlreadyExist( User pUser )
    {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public void saveNewUser( UserWithPassword pNewUser )
    {
        // TODO Auto-generated method stub
    }

}
