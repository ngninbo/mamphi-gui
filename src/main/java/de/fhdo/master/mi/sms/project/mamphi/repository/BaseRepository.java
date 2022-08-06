package de.fhdo.master.mi.sms.project.mamphi.repository;

import de.fhdo.master.mi.sms.project.mamphi.annotation.CrudRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static de.fhdo.master.mi.sms.project.mamphi.utils.TrialStatements.SQL_SCRIPT;

@CrudRepository
public abstract class BaseRepository<T> {

    String databaseUrl;
    public abstract BaseRepository<T> setDatabaseUrl(String databaseUrl);
    public abstract List<T> findAll();
    public abstract List<T> findAll(String query);
    public abstract void update(T t) throws NoSuchMethodException;

    public BaseRepository() {
        super();
    }

    public static void createDatabase(String databaseUrl) throws SQLException, IOException {

        Connection connection = DriverManager.getConnection(databaseUrl);
        String query = new String(Files.readAllBytes(Paths.get(SQL_SCRIPT)));
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
    }
}
