package de.fhdo.master.mi.sms.project.mamphi.repository;

import de.fhdo.master.mi.sms.project.mamphi.model.Land;
import de.fhdo.master.mi.sms.project.mamphi.model.RandomizationWeek;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static de.fhdo.master.mi.sms.project.mamphi.utils.MamphiStatements.*;

public class RandomizationWeekRepository extends BaseRepository<RandomizationWeek> {

    private List<RandomizationWeek> randomizationList;

    public RandomizationWeekRepository() {
        super();
    }


    @Override
    public RandomizationWeekRepository setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
        return this;
    }

    @Override
    public List<RandomizationWeek> findAll() {
        return findAll(FETCH_ALL_RANDOMIZATION_WEEK_ITEMS);
    }

    public List<RandomizationWeek> findAll(String query) {
        try (Connection connection = DriverManager.getConnection(databaseUrl)) {

            Statement statement = connection.createStatement();
            randomizationList = new ArrayList<>();
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {

                RandomizationWeek randWeekItem = new RandomizationWeek(results.getInt("patientenID"), results.getInt("Zentrum"),
                        results.getString("Behandlungsarm"), results.getString("Datum"));
                randomizationList.add(randWeekItem);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return randomizationList;
    }

    @Override
    public void update(RandomizationWeek randomizationWeek) throws NoSuchMethodException {
        throw new NoSuchMethodException("The method is not implemented yet;");
    }

    public List<RandomizationWeek> findAllByWeek(int week) {
        return findAll(String.format(FETCH_ALL_RANDOMIZATION_BY_WEEK, week));
    }

    public List<RandomizationWeek> findAllByWeekAndLand(int week, Land land) {
        return findAll(String.format(FETCH_ALL_RANDOMIZATION_BY_WEEK_AND_LAND, week, land));
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
