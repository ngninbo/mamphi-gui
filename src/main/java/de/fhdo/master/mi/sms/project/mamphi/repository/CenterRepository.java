package de.fhdo.master.mi.sms.project.mamphi.repository;

import de.fhdo.master.mi.sms.project.mamphi.model.Land;
import de.fhdo.master.mi.sms.project.mamphi.model.MonitorVisite;
import de.fhdo.master.mi.sms.project.mamphi.model.PatientCenter;
import de.fhdo.master.mi.sms.project.mamphi.model.Zentrum;

import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static de.fhdo.master.mi.sms.project.mamphi.utils.MamphiStatements.*;
import static de.fhdo.master.mi.sms.project.mamphi.utils.UITranslation.GERMANY;

public class CenterRepository extends BaseRepository<Zentrum> {

    private Statement statement;
    private static ResultSet results;
    private Zentrum center;
    private List<Zentrum> centerList;
    private List<Integer> centerIDs;
    private List<Integer> patientIDs;
    private int id;
    private List<MonitorVisite> monitorVisits;
    private static final LocalDate START_DATE = LocalDate.of(2019, 6, 1);
    private static final LocalDate END_DATE = START_DATE.plusYears(2);

    public CenterRepository() {
        super();
    }

    public CenterRepository(String database) {
        super(database);
    }

    @Override
    public void update(Zentrum center) {
        try (Connection connection = DriverManager.getConnection(databaseUrl)) {
            // create a connection to the database
            PreparedStatement stmt = connection.prepareStatement(INTO_CENTER_VALUES);
            stmt.setLong(1, center.getZentrumID());
            stmt.setString(2, GERMANY.equals(center.getLand()) ? Land.D.toString() : Land.GB.toString());
            stmt.setString(3, center.getOrt());
            stmt.setString(4, center.getPruefer());
            stmt.setString(5, center.getMonitor());

            int result = stmt.executeUpdate();

            if (result != 0) {
                System.out.println(CENTER_ADD_SUCCESS_MSG);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public List<Zentrum> findAll() {
        try (Connection connection = DriverManager.getConnection(databaseUrl)) {
            statement = connection.createStatement();

            results = statement.executeQuery(SELECT_FROM_CENTER);

            centerList = new ArrayList<>();

            // loop through the result set
            while (results.next()) {
                center = new Zentrum(results.getString("Monitor"), results.getString("Pruefer"),
                        results.getString("Ort"),
                        results.getString("Land").equals(Land.D.toString()) ? GERMANY : "Großbritanien",
                        results.getInt("ZentrumID"));
                centerList.add(center);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return centerList;
    }

    public List<Zentrum> findAllByLand(Land land) {
        try (Connection connection = DriverManager.getConnection(databaseUrl)) {
            statement = connection.createStatement();

            results = statement.executeQuery(String.format(SELECT_FROM_CENTER_WHERE_LAND, land));

            centerList = new ArrayList<>();

            while (results.next()) {
                center = new Zentrum(results.getString("Monitor"), results.getString("Pruefer"),
                        results.getString("Ort"),
                        results.getString("Land").equals("D") ? GERMANY : "Großbritanien",
                        results.getInt("ZentrumID"));
                centerList.add(center);
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return centerList;
    }

    public List<Integer> findAllCenterIDs() {

        try (Connection connection = DriverManager.getConnection(databaseUrl)) {

            statement = connection.createStatement();

            results = statement.executeQuery(SELECT_CENTER_ID_FROM_CENTER);

            centerIDs = new ArrayList<>();

            while (results.next()) {
                centerIDs.add(results.getInt("ZentrumID"));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return centerIDs;
    }

    public List<Integer> findAllPatientID() {

        try (Connection connection = DriverManager.getConnection(databaseUrl)) {

            statement = connection.createStatement();
            patientIDs = new ArrayList<>();
            results = statement.executeQuery(SELECT_PATIENT_ID_FROM_INFORMED_CONSENT);

            while (results.next()) {
                patientIDs.add(results.getInt("patientenID"));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return patientIDs;

    }

    public int nextId(Land land){

        try (Connection connection = DriverManager.getConnection(databaseUrl)) {

            statement = connection.createStatement();

            results = statement.executeQuery(String.format(FETCH_NEXT_CENTER_ID_LAND, land));

            while (results.next()){
                id = results.getInt("MAX_ID");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return id;
    }

    public List<PatientCenter> findNumberPatientPerCenterByLandByWeek(Land land, int week) {

        List<PatientCenter> listNumberPatientByCenterByLand = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(databaseUrl)) {

            String query = String.format(FETCH_PATIENT_PER_CENTER_PER_WEEK_BY_LAND, week, land);

            statement = connection.createStatement();

            results = statement.executeQuery(query);

            while (results.next()) {
                listNumberPatientByCenterByLand
                        .add(new PatientCenter(results.getString("Zentrum"), results.getInt("Anzahl")));
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return listNumberPatientByCenterByLand;
    }

    public List<PatientCenter> findNumberOfPatientPerCenterByWeek(int week) {

        List<PatientCenter> list = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(databaseUrl)) {

            results = connection.createStatement().executeQuery(String.format(FETCH_PATIENT_PER_CENTER_BY_WEEK, week));

            while (results.next()) {
                list.add(new PatientCenter(results.getString("Zentrum"), results.getInt("Anzahl")));
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
                list.add(new PatientCenter(results.getString("Zentrum"), results.getInt("Gesamtanzahl")));
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return list;
    }

    public List<MonitorVisite> getMonitorVisitPlan(boolean isAllCenterInvolved) {

        try (Connection connection = DriverManager.getConnection(databaseUrl)) {

            statement = connection.createStatement();

            results = statement.executeQuery((isAllCenterInvolved) ? SELECT_MONITOR_PLAN_WITH_ALL_CENTER_INVOLVED :
                    SELECT_MONITOR_PLAN_WITH_ANY_CENTER_INVOLVED);

            monitorVisits = new ArrayList<>();

            while (results.next()) {

                MonitorVisite monitorVisite = new MonitorVisite(results.getInt("ZentrumID"),
                        results.getString("Land").equals("D") ? GERMANY : "Großbritanien",
                        results.getString("Ort"), results.getString("Pruefer"),
                        results.getString("Monitor"),
                        results.getInt("Gesamtanzahl"));

                List<LocalDate> listOfVisiteDates;
                if (monitorVisite.getNumberOfPatient() > 10) {
                    listOfVisiteDates = START_DATE.datesUntil(END_DATE, Period.ofMonths(1)).collect(Collectors.toList());
                    monitorVisite.setVisitDate(listOfVisiteDates.subList(0, 5));
                }
                else if (monitorVisite.getNumberOfPatient() > 4 && monitorVisite.getNumberOfPatient() < 10) {
                    listOfVisiteDates = START_DATE.datesUntil(END_DATE, Period.ofMonths(2)).collect(Collectors.toList());
                    monitorVisite.setVisitDate(listOfVisiteDates.subList(0, 5));
                }
                else if (monitorVisite.getNumberOfPatient() > 0 && monitorVisite.getNumberOfPatient() < 5) {
                    listOfVisiteDates = START_DATE.datesUntil(END_DATE, Period.ofMonths(3)).collect(Collectors.toList());
                    monitorVisite.setVisitDate(listOfVisiteDates.subList(0, 5));
                }
                else {
                    listOfVisiteDates = new ArrayList<>();
                    monitorVisite.setVisitDate(listOfVisiteDates);
                }

                monitorVisits.add(monitorVisite);
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return monitorVisits;
    }
}
