package de.fhdo.master.mi.sms.project.mamphi.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static de.fhdo.master.mi.sms.project.mamphi.utils.MamphiStatements.TRIAL_DB_URL;

public abstract class BaseRepository<T> {

    String databaseUrl;
    private Statement statement;
    public abstract void update(T t);

    public abstract List<T> findAll();

    public BaseRepository() {
        this.databaseUrl = String.format(TRIAL_DB_URL, "mamphi.db");
    }

    public BaseRepository(String database) {
        this.databaseUrl = String.format(TRIAL_DB_URL, database);
    }

    public BaseRepository<T> createTable(String tableQuery) {
        try (Connection connection = DriverManager.getConnection(databaseUrl)) {
            connection.setAutoCommit(false);

            statement = connection.createStatement();

            statement.executeUpdate(tableQuery);

            statement.close();
            connection.commit();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return this;
    }

    public BaseRepository<T> populate(String insertValueQuery) {
        try (Connection connection = DriverManager.getConnection(databaseUrl)) {
            connection.setAutoCommit(false);

            statement = connection.createStatement();

            statement.executeUpdate(insertValueQuery);

            statement.close();
            connection.commit();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return this;
    }
}
