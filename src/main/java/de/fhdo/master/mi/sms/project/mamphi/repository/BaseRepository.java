package de.fhdo.master.mi.sms.project.mamphi.repository;

import de.fhdo.master.mi.sms.project.mamphi.annotation.CrudRepository;

import java.util.List;

@CrudRepository
public abstract class BaseRepository<T> {

    String databaseUrl;
    public abstract BaseRepository<T> setDatabaseUrl(String databaseUrl);
    public abstract List<T> findAll();
    public abstract List<T> findAll(String query);
    public abstract void update(T t) throws NoSuchMethodException;
}
