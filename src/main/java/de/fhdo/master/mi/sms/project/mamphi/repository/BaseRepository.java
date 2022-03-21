package de.fhdo.master.mi.sms.project.mamphi.repository;

import java.util.List;

public abstract class BaseRepository<T> {

    String databaseUrl;
    public abstract void update(T t) throws NoSuchMethodException;
    public abstract BaseRepository<T> setDatabaseUrl(String databaseUrl);

    public abstract List<T> findAll();

    public BaseRepository() {
        super();
    }
}
