package de.fhdo.master.mi.sms.project.mamphi.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.fhdo.master.mi.sms.project.mamphi.model.PatientCenter;
import de.fhdo.master.mi.sms.project.mamphi.model.Consent;
import de.fhdo.master.mi.sms.project.mamphi.model.InformedConsent;
import de.fhdo.master.mi.sms.project.mamphi.model.Land;
import de.fhdo.master.mi.sms.project.mamphi.model.MonitorVisite;
import de.fhdo.master.mi.sms.project.mamphi.model.RandomizationWeek;
import de.fhdo.master.mi.sms.project.mamphi.model.Zentrum;
import static de.fhdo.master.mi.sms.project.mamphi.utils.MamphiStatements.*;

public class FetchData {

	private static Connection connection;
	private static Statement statement;
	private static ResultSet results;
	private static int result;
	private Zentrum center;
	private List<Zentrum> centerList;
	private List<InformedConsent> consentList;
    private List<MonitorVisite> visiteList;
	private List<RandomizationWeek> randomizationList;
	private RandomizationWeek randWeekItem;
	private List<Integer> centerIDs;
	private List<Integer> patientIDs;
	private static String query;
	private int maxId;

    private static final LocalDate startDate = LocalDate.of(2019, 6, 1);
    private static final LocalDate endDate = startDate.plusYears(2);

	// DB parameters
    // TODO: Setup the path to your sqlite data base here
	private final String url = MAMPHI_DB_URL;

	public void updateZentrum(Zentrum center) {
		try {
			// create a connection to the database
			connection = DriverManager.getConnection(url);

			PreparedStatement stmt = connection.prepareStatement(INTO_CENTER_VALUES);
			stmt.setLong(1, center.getZentrumID());
			stmt.setString(2, GERMANY.equals(center.getLand()) ? Land.D.toString() : Land.GB.toString());
			stmt.setString(3, center.getOrt());
			stmt.setString(4, center.getPruefer());
			stmt.setString(5, center.getMonitor());

			result = stmt.executeUpdate();

			if (result != 0) {
				System.out.println(CENTER_ADD_SUCCESS_MSG);
			}

			connection.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Zentrum> fetchAllCenter() {

		try {
			// create a connection to the database
			connection = DriverManager.getConnection(url);
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
			connection.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return centerList;
	}

	public List<Zentrum> fetchAllCenterByLand(Land land) {

		try {
			// create a connection to the database
			connection = DriverManager.getConnection(url);

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
			connection.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return centerList;

	}

	public List<InformedConsent> fetchAllInformedConsent() {
		try {
			// create a connection to the database
			connection = DriverManager.getConnection(url);

			statement = connection.createStatement();

			results = statement.executeQuery(SELECT_FROM_INFORMED_CONSENT);
			InformedConsent consent;
			consentList = new ArrayList<>();

			// loop through the result set
			while (results.next()) {
				
				String einwilligung = "";
				
				if (results.getString("Einwilligung") != null) {
					einwilligung = results.getString("Einwilligung").toUpperCase();
				}

				consent = new InformedConsent(results.getInt("patientenID"), results.getInt("Zentrum"),
						einwilligung, results.getString("Datum"));
				consentList.add(consent);
			}
			connection.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return consentList;
	}
	
	public List<InformedConsent> fetchAllInformedConsent(boolean isInformed){
		
		try {
			// create a connection to the database
			connection = DriverManager.getConnection(url);

			query = (isInformed) ? String.format(SELECT_FROM_INFORMED_CONSENT_WHERE_CONSENT, YES) :
					String.format(SELECT_FROM_INFORMED_CONSENT_WHERE_CONSENT, NO);

			statement = connection.createStatement();

			results = statement.executeQuery(query);
			InformedConsent consent;
			consentList = new ArrayList<>();

			// loop through the result set
			while (results.next()) {

				consent = new InformedConsent(results.getInt("patientenID"), results.getInt("Zentrum"),
						results.getString("Einwilligung").toUpperCase(), results.getString("Datum"));
				consentList.add(consent);
			}
			connection.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return consentList;
	}

	public List<InformedConsent> fetchAllInformedConsent(Consent consent) {

		try {
			connection = DriverManager.getConnection(url);

			if (consent.equals(Consent.INCOMPLETE)) {
				query = SELECT_INCOMPLETE_CONSENT;
			} else if (consent.equals(Consent.MISSING)) {
				query = SELECT_MISSING_CONSENT;
			} else {
				query = SELECT_LATE_INFORMED_CONSENT;
			}

			statement = connection.createStatement();

			results = statement.executeQuery(query);
			InformedConsent informedConsent;
			consentList = new ArrayList<>();

			while (results.next()) {
				
				String einwilligung = "";
				
				if (results.getString("Einwilligung") != null) {
					einwilligung = results.getString("Einwilligung").toUpperCase();
				}

				informedConsent = new InformedConsent(results.getInt("patientenID"), results.getInt("Zentrum"),
						einwilligung, results.getString("Datum"));
				consentList.add(informedConsent);

			}
			connection.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return consentList;
	}

    public List<MonitorVisite> fetchMonitorVisitPlan(boolean isAllCenterInvolved) {

        try {
            connection = DriverManager.getConnection(url);

			statement = connection.createStatement();

			results = statement.executeQuery((isAllCenterInvolved) ? SELECT_MONITOR_PLAN_WITH_ALL_CENTER_INVOLVED :
					SELECT_MONITOR_PLAN_WITH_ANY_CENTER_INVOLVED);

            visiteList = new ArrayList<>();

            while (results.next()) {

            	MonitorVisite monitorVisite = new MonitorVisite(results.getInt("ZentrumID"),
						results.getString("Land").equals("D") ? GERMANY : "Großbritanien",
						results.getString("Ort"), results.getString("Pruefer"),
						results.getString("Monitor"),
						results.getInt("Gesamtanzahl"));

				List<LocalDate> listOfVisiteDates;
				if (monitorVisite.getNumberOfPatient() > 10) {
					listOfVisiteDates = startDate.datesUntil(endDate, Period.ofMonths(1)).collect(Collectors.toList());
					monitorVisite.setVisiteDate(listOfVisiteDates.subList(0, 5));
				}
				else if (monitorVisite.getNumberOfPatient() > 4 && monitorVisite.getNumberOfPatient() < 10) {
					listOfVisiteDates = startDate.datesUntil(endDate, Period.ofMonths(2)).collect(Collectors.toList());
					monitorVisite.setVisiteDate(listOfVisiteDates.subList(0, 5));
				}
				else if (monitorVisite.getNumberOfPatient() > 0 && monitorVisite.getNumberOfPatient() < 5) {
					listOfVisiteDates = startDate.datesUntil(endDate, Period.ofMonths(3)).collect(Collectors.toList());
					monitorVisite.setVisiteDate(listOfVisiteDates.subList(0, 5));
				}
				else {
					listOfVisiteDates = new ArrayList<>();
					monitorVisite.setVisiteDate(listOfVisiteDates);
				}

                visiteList.add(monitorVisite);
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return visiteList;
    }

	public void updateRandomizationTable(RandomizationWeek rand, int week) {

		// create a connection to the database
		try {
			connection = DriverManager.getConnection(url);

			query = String.format(UPDATE_RANDOMIZATION_WEEK, week);

			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setLong(1, rand.getPatientenID());
			stmt.setLong(2, rand.getZentrum());
			stmt.setString(3, rand.getBehandlungsarm());
			stmt.setString(4, rand.getDate());

			result = stmt.executeUpdate();

			if (result != 0) {
				System.out.printf(UPDATE_RANDOMIZATION_WEEK_SUCCESS_MSG, week);
			}
			connection.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void updateInformedConsent(InformedConsent consent) {

		// create a connection to the database
		try {

			connection = DriverManager.getConnection(url);

			PreparedStatement stmt = connection.prepareStatement(UPDATE_INFORMED_CONSENT);
			stmt.setLong(1, consent.getPatientenID());
			stmt.setLong(2, consent.getZentrumID());
			stmt.setString(3, consent.getEinwilligung());
			stmt.setString(4, consent.getDate());
			

			result = stmt.executeUpdate();

			if (result != 0) {
				System.out.println(UPDATE_INFORMED_CONSENT_SUCCESS_MSG);
			}
			connection.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Integer> fetchAllCenterID() {

		try {
			// create a connection to the database
			connection = DriverManager.getConnection(url);

			statement = connection.createStatement();

			results = statement.executeQuery(SELECT_CENTER_ID_FROM_CENTER);

			centerIDs = new ArrayList<>();

			// loop through the result set
			while (results.next()) {
				centerIDs.add(results.getInt("ZentrumID"));
			}
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return centerIDs;
	}

	public List<PatientCenter> fetchAllNumberPatientPerCenterByLandByWeek(Land land, int week) {

		List<PatientCenter> listNumberPatientByCenterByLand = new ArrayList<>();

		try {
			connection = DriverManager.getConnection(url);

			query = String.format(FETCH_PATIENT_PER_CENTER_PER_WEEK_BY_LAND, week, land);

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

	public List<PatientCenter> fetchAllNumberOfPatientPerCenterByWeek(int week) {

        List<PatientCenter> list = new ArrayList<>();

        try {
            connection = DriverManager.getConnection(url);

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
	
	public List<PatientCenter> fetchNumberOfPatientPerCenterByAllWeek() {

        List<PatientCenter> list = new ArrayList<>();

        try {
            connection = DriverManager.getConnection(url);

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

	public List<Integer> fetchAllPatientenID() {

		try {
			// create a connection to the database
			connection = DriverManager.getConnection(url);

			statement = connection.createStatement();
			patientIDs = new ArrayList<>();
			results = statement.executeQuery(SELECT_PATIENT_ID_FROM_INFORMED_CONSENT);

			// loop through the result set
			while (results.next()) {
				patientIDs.add(results.getInt("patientenID"));
			}
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return patientIDs;

	}

	public List<RandomizationWeek> fetchAllRandomizationByWeek(int week) {

		try {
			// create a connection to the database
			connection = DriverManager.getConnection(url);

			statement = connection.createStatement();
			randomizationList = new ArrayList<>();
			results = statement.executeQuery(String.format(FETCH_ALL_RANDOMIZATION_BY_WEEK, week));

			// loop through the result set
			while (results.next()) {

				randWeekItem = new RandomizationWeek(results.getInt("patientenID"), results.getInt("Zentrum"),
						results.getString("Behandlungsarm"), results.getString("Datum"));
				randomizationList.add(randWeekItem);
			}
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return randomizationList;
	}

	public List<RandomizationWeek> fetchAllRandomizationByWeekInLand(int week, Land land) {

		try {
			// create a connection to the database
			connection = DriverManager.getConnection(url);

			statement = connection.createStatement();
			randomizationList = new ArrayList<>();
			results = statement.executeQuery(String.format(FETCH_ALL_RANDOMIZATION_BY_WEEK_AND_LAND, week, land));

			// loop through the result set
			while (results.next()) {

				randWeekItem = new RandomizationWeek(results.getInt("patientenID"), results.getInt("Zentrum"),
						results.getString("Behandlungsarm"), results.getString("Datum"));
				randomizationList.add(randWeekItem);
			}
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return randomizationList;
	}
	
	public List<RandomizationWeek> fetchAllRandomWeekItems() {

		try {
			// create a connection to the database
			connection = DriverManager.getConnection(url);

			statement = connection.createStatement();
			randomizationList = new ArrayList<>();
			results = statement.executeQuery(FETCH_ALL_RANDOMIZATION_WEEK_ITEMS);

			// loop through the result set
			while (results.next()) {

				randWeekItem = new RandomizationWeek(results.getInt("patientenID"), results.getInt("Zentrum"),
						results.getString("Behandlungsarm"), results.getString("Datum"));
				randomizationList.add(randWeekItem);
			}
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return randomizationList;
	}

	public int getMaxID(Land land){

		try {
			connection = DriverManager.getConnection(url);

			statement = connection.createStatement();

			results = statement.executeQuery(String.format(FETCH_NEXT_CENTER_ID_LAND, land));

			while (results.next()){
				maxId = results.getInt("MAX_ID");
			}

		}catch (SQLException e){
			e.printStackTrace();
		}

		return maxId;
	}
}
