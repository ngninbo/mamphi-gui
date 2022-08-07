package de.fhdo.master.mi.sms.project.mamphi.repository;

import de.fhdo.master.mi.sms.project.mamphi.model.Country;
import de.fhdo.master.mi.sms.project.mamphi.model.MonitorVisit;
import de.fhdo.master.mi.sms.project.mamphi.model.PatientCenter;
import de.fhdo.master.mi.sms.project.mamphi.model.Centre;
import de.fhdo.master.mi.sms.project.mamphi.annotation.CrudRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static de.fhdo.master.mi.sms.project.mamphi.utils.GuiConstants.*;
import static de.fhdo.master.mi.sms.project.mamphi.utils.TrialStatements.*;
import static de.fhdo.master.mi.sms.project.mamphi.utils.UITranslation.ENGLAND;
import static de.fhdo.master.mi.sms.project.mamphi.utils.UITranslation.GERMANY;

@CrudRepository
public class CenterRepository extends BaseRepository<Centre> {

    private static final Logger LOGGER = Logger.getLogger(CenterRepository.class.getName());

    private Statement statement;
    private static ResultSet results;
    private int id;
    private List<MonitorVisit> monitorVisits;

    public CenterRepository() {
        super();
    }

    @Override
    public CenterRepository setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
        return this;
    }

    @Override
    public List<Centre> findAll() {
        return findAll(SELECT_FROM_CENTER);
    }

    public List<Centre> findAllByCountry(Country country) {

        LOGGER.info("Fetching items from centre table.");

        return findAll(String.format(SELECT_FROM_CENTER_WHERE_COUNTRY, country));
    }

    @Override
    public List<Centre> findAll(String query) {

        List<Centre> centerList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(databaseUrl)) {
            statement = connection.createStatement();

            results = statement.executeQuery(query);

            while (results.next()) {
                Centre center = new Centre(results.getString("Monitor"), results.getString("Trier"),
                        results.getString("Place"),
                        results.getString("Country").equals(Country.DE.toString()) ? GERMANY : ENGLAND,
                        results.getInt("CentreID"));
                centerList.add(center);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        LOGGER.info("Fetch of centre items completed!");
        return centerList;
    }

    @Override
    public void update(Centre center) {
        try (Connection connection = DriverManager.getConnection(databaseUrl)) {

            PreparedStatement stmt = connection.prepareStatement(INTO_CENTER_VALUES);
            stmt.setLong(1, center.getCentreID());
            stmt.setString(2, GERMANY.equals(center.getCountry()) ? Country.DE.toString() : Country.GB.toString());
            stmt.setString(3, center.getPlace());
            stmt.setString(FOUR, center.getTrier());
            stmt.setString(FIVE, center.getMonitor());

            int result = stmt.executeUpdate();

            if (result != 0) {
                System.out.println(CENTER_ADD_SUCCESS_MSG);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public List<String> findAllCenterIDs() {

        List<String> centerIDs = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(databaseUrl)) {

            statement = connection.createStatement();

            results = statement.executeQuery(SELECT_CENTER_ID_FROM_CENTER);

            while (results.next()) {
                centerIDs.add(results.getString("CentreID"));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return centerIDs;
    }

    public List<Integer> findAllPatientID() {

        List<Integer> patientIDs = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(databaseUrl)) {

            statement = connection.createStatement();
            results = statement.executeQuery(SELECT_PATIENT_ID_FROM_INFORMED_CONSENT);

            while (results.next()) {
                patientIDs.add(results.getInt("patientID"));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return patientIDs;

    }

    public int nextId(Country country){

        try (Connection connection = DriverManager.getConnection(databaseUrl)) {

            statement = connection.createStatement();

            results = statement.executeQuery(String.format(FETCH_NEXT_CENTRE_ID_BY_COUNTRY, country));

            while (results.next()){
                id = results.getInt("MAX_ID");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return id;
    }

    public List<PatientCenter> findNumberPatientPerCenterByCountryByWeek(Country country, int week) {

        List<PatientCenter> listNumberPatientByCentreByCountry = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(databaseUrl)) {

            String query = String.format(FETCH_PATIENT_PER_CENTER_PER_WEEK_BY_COUNTRY, week, country);

            statement = connection.createStatement();

            results = statement.executeQuery(query);

            while (results.next()) {
                listNumberPatientByCentreByCountry
                        .add(new PatientCenter(results.getString("Centre"), results.getInt("NumberOfPatient")));
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return listNumberPatientByCentreByCountry;
    }

    public List<PatientCenter> findNumberOfPatientPerCenterByWeek(int week) {

        List<PatientCenter> list = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(databaseUrl)) {

            results = connection.createStatement().executeQuery(String.format(FETCH_PATIENT_PER_CENTER_BY_WEEK, week));

            while (results.next()) {
                list.add(new PatientCenter(results.getString("centre"), results.getInt("NumberOfPatient")));
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return list;
    }

    public List<PatientCenter> findNumberOfPatientPerCenterByAllWeek() {

        List<PatientCenter> list = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(databaseUrl)) {

            statement = connection.createStatement();

            results = statement.executeQuery(FETCH_NUM_PATIENT_PER_CENTER_ALL_WEEK);

            while (results.next()) {
                list.add(new PatientCenter(results.getString("Centre"), results.getInt("TotalNumberOfPatient")));
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return list;
    }

    public List<MonitorVisit> getMonitorVisitPlan(boolean isAllCenterInvolved) {

        try (Connection connection = DriverManager.getConnection(databaseUrl)) {

            statement = connection.createStatement();

            results = statement.executeQuery((isAllCenterInvolved) ? SELECT_MONITOR_PLAN_WITH_ALL_CENTER_INVOLVED :
                    SELECT_MONITOR_PLAN_WITH_ANY_CENTER_INVOLVED);

            monitorVisits = new ArrayList<>();

            while (results.next()) {

                MonitorVisit monitorVisit = new MonitorVisit(results.getInt("CentreID"),
                        results.getString("Country").equals("DE") ? GERMANY : ENGLAND,
                        results.getString("Place"), results.getString("Trier"),
                        results.getString("Monitor"),
                        results.getInt("TotalNumberOfPatient"));

                monitorVisit.setVisitDates();

                monitorVisits.add(monitorVisit);
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return monitorVisits;
    }
}
