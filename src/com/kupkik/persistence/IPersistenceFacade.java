package com.kupkik.persistence;

import com.kupkik.model.User;
import com.kupkik.model.UserWithPassword;

public interface IPersistenceFacade
{
    public boolean doesUserAlreadyExist( User pUser );

    public void saveNewUser( UserWithPassword pNewUser );
}
