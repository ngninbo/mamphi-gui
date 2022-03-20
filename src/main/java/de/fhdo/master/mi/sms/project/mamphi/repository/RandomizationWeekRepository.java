package de.fhdo.master.mi.sms.project.mamphi.repository;

import de.fhdo.master.mi.sms.project.mamphi.model.Land;
import de.fhdo.master.mi.sms.project.mamphi.model.RandomizationWeek;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.*;

import static de.fhdo.master.mi.sms.project.mamphi.utils.MamphiStatements.*;

public class RandomizationWeekRepository extends BaseRepository<RandomizationWeek> {

    private Statement statement;
    private static ResultSet results;
    private List<RandomizationWeek> randomizationList;
    private RandomizationWeek randWeekItem;

    public RandomizationWeekRepository() {
        super();
    }

    public RandomizationWeekRepository(String database) {
        super(database);
    }

    @Override
    public void update(RandomizationWeek randomizationWeek) {
        throw new NotImplementedException("The method is not implemented yet;");
    }

    @Override
    public List<RandomizationWeek> findAll() {
        try (Connection connection = DriverManager.getConnection(databaseUrl)) {

            statement = connection.createStatement();
            randomizationList = new ArrayList<>();
            results = statement.executeQuery(FETCH_ALL_RANDOMIZATION_WEEK_ITEMS);

            while (results.next()) {

                randWeekItem = new RandomizationWeek(results.getInt("patientenID"), results.getInt("Zentrum"),
                        results.getString("Behandlungsarm"), results.getString("Datum"));
                randomizationList.add(randWeekItem);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return randomizationList;
    }

    public RandomizationWeekRepository createTable() {
        try (Connection connection = DriverManager.getConnection(TRIAL_DB_URL)) {
            connection.setAutoCommit(false);

            statement = connection.createStatement();
            statement.executeUpdate(CREATE_RANDOM_WEEK_1_TABLE);

            statement = connection.createStatement();
            statement.executeUpdate(CREATE_RANDOM_WEEK_2_TABLE);

            statement.close();
            connection.commit();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return this;
    }

    public RandomizationWeekRepository populate() {
        try (Connection connection = DriverManager.getConnection(TRIAL_DB_URL)) {
            connection.setAutoCommit(false);

            statement = connection.createStatement();
            statement.executeUpdate(RANDOM_WEEK_1_INIT_DATA);

            statement = connection.createStatement();
            statement.executeUpdate(RANDOM_WEEK_2_INIT_DATA);

            statement.close();
            connection.commit();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return this;
    }

    public List<RandomizationWeek> findAllByWeek(int week) {
        try (Connection connection = DriverManager.getConnection(databaseUrl)) {
            statement = connection.createStatement();
            randomizationList = new ArrayList<>();
            results = statement.executeQuery(String.format(FETCH_ALL_RANDOMIZATION_BY_WEEK, week));

            while (results.next()) {

                randWeekItem = new RandomizationWeek(results.getInt("patientenID"), results.getInt("Zentrum"),
                        results.getString("Behandlungsarm"), results.getString("Datum"));
                randomizationList.add(randWeekItem);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return randomizationList;
    }

    public List<RandomizationWeek> findAllByWeekAndLand(int week, Land land) {
        try (Connection connection = DriverManager.getConnection(databaseUrl)) {

            statement = connection.createStatement();
            randomizationList = new ArrayList<>();
            results = statement.executeQuery(String.format(FETCH_ALL_RANDOMIZATION_BY_WEEK_AND_LAND, week, land));

            while (results.next()) {

                randWeekItem = new RandomizationWeek(results.getInt("patientenID"), results.getInt("Zentrum"),
                        results.getString("Behandlungsarm"), results.getString("Datum"));
                randomizationList.add(randWeekItem);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return randomizationList;
    }

    public void update(RandomizationWeek rand, int week) {
        try (Connection connection = DriverManager.getConnection(databaseUrl)) {

            String query = String.format(UPDATE_RANDOMIZATION_WEEK, week);

            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setLong(1, rand.getPatientenID());
            stmt.setLong(2, rand.getZentrum());
            stmt.setString(3, rand.getBehandlungsarm());
            stmt.setString(4, rand.getDate());

            int result = stmt.executeUpdate();

            if (result != 0) {
                System.out.printf(UPDATE_RANDOMIZATION_WEEK_SUCCESS_MSG, week);
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
