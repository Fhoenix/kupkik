package com.kupkik.html.integration;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.appengine.api.datastore.DatastoreAttributes;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Index;
import com.google.appengine.api.datastore.Index.IndexState;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyRange;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.datastore.TransactionOptions;

public class DatastoreMock
        implements DatastoreService
{
    private Map<String, Entity> mEntitiesForKeyNames;

    public void setEntitiesForKeyNames( Map<String, Entity> pEntitiesForKeyNames )
    {
        mEntitiesForKeyNames = pEntitiesForKeyNames;
    }

    @Override
    public Entity get( Key pKey ) throws EntityNotFoundException
    {
        if( !mEntitiesForKeyNames.containsKey(pKey.getName()) )
        {
            throw new EntityNotFoundException(pKey);
        }

        Entity entity = mEntitiesForKeyNames.get(pKey.getName());

        return entity;
    }

    @Override
    public PreparedQuery prepare( Query pQuery )
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public PreparedQuery prepare( Transaction pTxn, Query pQuery )
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public Transaction getCurrentTransaction()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public Transaction getCurrentTransaction( Transaction pReturnedIfNoTxn )
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public Collection<Transaction> getActiveTransactions()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public Entity get( Transaction pTxn, Key pKey ) throws EntityNotFoundException
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public Map<Key, Entity> get( Iterable<Key> pKeys )
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public Map<Key, Entity> get( Transaction pTxn, Iterable<Key> pKeys )
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public Key put( Entity pEntity )
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public Key put( Transaction pTxn, Entity pEntity )
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public List<Key> put( Iterable<Entity> pEntities )
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public List<Key> put( Transaction pTxn, Iterable<Entity> pEntities )
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public void delete( Key... pKeys )
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public void delete( Transaction pTxn, Key... pKeys )
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public void delete( Iterable<Key> pKeys )
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public void delete( Transaction pTxn, Iterable<Key> pKeys )
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public Transaction beginTransaction()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public Transaction beginTransaction( TransactionOptions pOptions )
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public KeyRange allocateIds( String pKind, long pNum )
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public KeyRange allocateIds( Key pParent, String pKind, long pNum )
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public KeyRangeState allocateIdRange( KeyRange pRange )
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public DatastoreAttributes getDatastoreAttributes()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

    @Override
    public Map<Index, IndexState> getIndexes()
    {
        throw new RuntimeException("This mock method was not supposed to be used");
    }

}
