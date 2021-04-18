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
	private final String url = "jdbc:sqlite:C:\\mamphi\\mamphidb.db";  

	public void updateZentrum(Zentrum center) {
		try {
			// create a connection to the database
			connection = DriverManager.getConnection(url);

			query = "INSERT INTO Zentren VALUES (?, ?, ?, ?, ?)";

			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setLong(1, center.getZentrumID());
			stmt.setString(2, center.getLand().equals("Deutschland") ? Land.D.toString() : Land.GB.toString());
			stmt.setString(3, center.getOrt());
			stmt.setString(4, center.getPruefer());
			stmt.setString(5, center.getMonitor());

			result = stmt.executeUpdate();

			if (result != 0) {
				System.out.println("New row added to zentren table!");
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
			query = "Select * from Zentren";

			statement = connection.createStatement();

			results = statement.executeQuery(query);

			centerList = new ArrayList<>();

			// loop through the result set
			while (results.next()) {
				center = new Zentrum(results.getString("Monitor"), results.getString("Pruefer"),
						results.getString("Ort"),
						results.getString("Land").equals(Land.D.toString()) ? "Deutschland" : "Großbritanien",
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
			query = "Select * from Zentren where Land = '" + land + "'";

			statement = connection.createStatement();

			results = statement.executeQuery(query);

			centerList = new ArrayList<>();

			// loop through the result set
			while (results.next()) {
				center = new Zentrum(results.getString("Monitor"), results.getString("Pruefer"),
						results.getString("Ort"),
						results.getString("Land").equals("D") ? "Deutschland" : "Großbritanien",
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

			query = "select patientenID, Zentrum, Einwilligung, Datum from Informed_consent";

			statement = connection.createStatement();

			results = statement.executeQuery(query);
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

			query = (isInformed) ? "select patientenID, Zentrum, Einwilligung, Datum from Informed_consent WHERE Einwilligung = 'ja'":
				"select patientenID, Zentrum, Einwilligung, Datum from Informed_consent WHERE Einwilligung = 'nein'";

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
				query = "SELECT 	PatientenID, Zentrum, Einwilligung, Datum	\r\n" + 
						"FROM Informed_consent\r\n" + 
						"WHERE Einwilligung IS NULL OR Datum IS NULL;";
			} else if (consent.equals(Consent.MISSING)) {
				query = "SELECT PatientenID, Zentrum, Einwilligung, Datum " +
						"FROM Informed_consent WHERE Einwilligung IS NULL;";
			} else {
				query = "SELECT * FROM Informed_consent\r\n" + 
						"WHERE substr(Datum,7)||substr(Datum,4,2)||substr(Datum,1,2) > '20190603';";
			}

			statement = connection.createStatement();

			results = statement.executeQuery(query);
			InformedConsent informedConsent;
			consentList = new ArrayList<>();
			// loop through the result set
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

    public List<MonitorVisite> fetchMonitorVisites(boolean isAllCenterInvolved) {

        try {
            connection = DriverManager.getConnection(url);

            query = (isAllCenterInvolved) ? "SELECT z.ZentrumID, z.Land, z.Ort, z.Pruefer, z.Monitor, zg.Gesamtanzahl as Gesamtanzahl\r\n" + 
            		"FROM(SELECT * FROM Zentren ORDER BY ZentrumID ASC) z\r\n" + 
            		"JOIN (	SELECT ZentrumID, Gesamtanzahl \r\n" + 
            		"		FROM (SELECT ZentrumID FROM Zentren ORDER BY ZentrumID ASC)\r\n" + 
            		"		LEFT JOIN (	SELECT Zentrum, SUM(Anzahl) as Gesamtanzahl\r\n" + 
            		"					FROM(	SELECT r1.Zentrum, COUNT(r1.Zentrum) as Anzahl\r\n" + 
            		"							FROM Randomisierung_Woche_1 r1 \r\n" + 
            		"							GROUP BY r1.Zentrum\r\n" + 
            		"							UNION 	SELECT r2.Zentrum, COUNT(r2.Zentrum) as Anzahl\r\n" + 
            		"									FROM Randomisierung_Woche_2 r2 \r\n" + 
            		"									GROUP BY r2.Zentrum)\r\n" + 
            		"					GROUP BY Zentrum) \r\n" + 
            		"		ON ZentrumID = Zentrum) zg \r\n" + 
            		"ON z.ZentrumID = zg.ZentrumID;":
            		"SELECT z.ZentrumID, z.Land, z.Ort, z.Pruefer, z.Monitor, zg.Gesamtanzahl \r\n" + 
            		"FROM Zentren z\r\n" + 
            		"JOIN (	SELECT za.Zentrum, SUM(za.ANZAHL) AS Gesamtanzahl \r\n" + 
            		"		FROM (	SELECT r1.Zentrum, COUNT(r1.Zentrum) as Anzahl\r\n" + 
            		"				FROM Randomisierung_Woche_1 r1\r\n" + 
            		"				GROUP BY r1.Zentrum\r\n" + 
            		"				UNION\r\n" + 
            		"				SELECT 	CASE \r\n" + 
            		"							WHEN r2.Zentrum IS NULL THEN 999\r\n" + 
            		"							ELSE r2.Zentrum\r\n" + 
            		"						END AS Zentrum, \r\n" + 
            		"						COUNT(r2.Zentrum) as Anzahl\r\n" + 
            		"				FROM Randomisierung_Woche_2 r2\r\n" + 
            		"				GROUP BY r2.Zentrum) za\r\n" + 
            		"		GROUP BY Zentrum) zg \r\n" + 
            		"ON zg.Zentrum = z.ZentrumID;";

			statement = connection.createStatement();

			results = statement.executeQuery(query);

            visiteList = new ArrayList<>();

            while (results.next()) {

            	MonitorVisite monitorVisite = new MonitorVisite(results.getInt("ZentrumID"),
						results.getString("Land").equals("D") ? "Deutschland" : "Großbritanien",
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

	public void updateRandomiationTable(RandomizationWeek rand, int week) {

		// create a connection to the database
		try {
			connection = DriverManager.getConnection(url);

			query = "INSERT INTO Randomisierung_Woche_" + week + " (PatientenID, Zentrum, Behandlungsarm, Datum) VALUES (?, ?, ?, ?)";

			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setLong(1, rand.getPatientenID());
			stmt.setLong(2, rand.getZentrum());
			stmt.setString(3, rand.getBehandlungsarm());
			stmt.setString(4, rand.getDate());

			result = stmt.executeUpdate();

			if (result != 0) {
				System.out.println("New row added to Randomization Table Week " + week);
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
			query = "INSERT INTO Informed_consent (PatientenID, Zentrum, Einwilligung, Datum) VALUES (?, ?, ?, ?)";

			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setLong(1, consent.getPatientenID());
			stmt.setLong(2, consent.getZentrumID());
			stmt.setString(3, consent.getEinwilligung());
			stmt.setString(4, consent.getDate());
			

			result = stmt.executeUpdate();

			if (result != 0) {
				System.out.println("New row added to Informed Consent table!");
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
			query = "select ZentrumID from Zentren";

			statement = connection.createStatement();

			results = statement.executeQuery(query);

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

	public List<PatientCenter> fetchAllNumberPatientenPerCenterByLandByWeek(Land land, int week) {

		List<PatientCenter> listNumberPatientByCenterByLand = new ArrayList<>();

		try {
			connection = DriverManager.getConnection(url);

			query = "SELECT Zentrum, count(Zentrum) as Anzahl "
					+ "FROM Randomisierung_Woche_"+ week +
					" JOIN (SELECT ZentrumID FROM Zentren WHERE Land = '"+ land +"') on ZentrumID = Zentrum GROUP BY Zentrum";

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
            
            query = "SELECT 	CASE \r\n" + 
            		"			WHEN r.Zentrum IS NULL THEN 999\r\n" + 
            		"			ELSE r.Zentrum\r\n" + 
            		"		END AS Zentrum, \r\n" + 
            		"		COUNT(r.Zentrum) as Anzahl\r\n" + 
            		"FROM Randomisierung_Woche_"+ week +" r\r\n" + 
            		"GROUP BY r.Zentrum;";
            
            results = connection.createStatement().executeQuery(query);

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

            query = "SELECT za.Zentrum, SUM(za.ANZAHL) AS Gesamtanzahl \r\n" + 
            		"FROM (	SELECT r1.Zentrum, COUNT(r1.Zentrum) as Anzahl\r\n" + 
            		"		FROM Randomisierung_Woche_1 r1\r\n" + 
            		"		GROUP BY r1.Zentrum\r\n" + 
            		"		UNION\r\n" + 
            		"		SELECT 	CASE \r\n" + 
            		"					WHEN r2.Zentrum IS NULL THEN 999\r\n" + 
            		"					ELSE r2.Zentrum\r\n" + 
            		"				END AS Zentrum, \r\n" + 
            		"				COUNT(r2.Zentrum) as Anzahl\r\n" + 
            		"		FROM Randomisierung_Woche_2 r2\r\n" + 
            		"		GROUP BY r2.Zentrum) za\r\n" + 
            		"GROUP BY Zentrum;";

			statement = connection.createStatement();

			results = statement.executeQuery(query);
            // results = connection.createStatement().executeQuery(query);

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
			query = "select patientenID from Informed_consent";

			statement = connection.createStatement();
			patientIDs = new ArrayList<>();
			results = statement.executeQuery(query);

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
			query = "SELECT 	r.PatientenID, \r\n" + 
					"		CASE \r\n" + 
					"			WHEN r.Zentrum IS NULL THEN 999\r\n" + 
					"			ELSE r.Zentrum\r\n" + 
					"		END AS Zentrum, \r\n" + 
					"		r.Behandlungsarm, r.Datum\r\n" + 
					"FROM Randomisierung_Woche_"+ week +" r\r\n" + 
					"WHERE PatientenID IS NOT NULL;";

			statement = connection.createStatement();
			randomizationList = new ArrayList<>();
			results = statement.executeQuery(query);

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
			query = "SELECT patientenID, Zentrum, Behandlungsarm, Datum "
					+ "FROM Randomisierung_Woche_"+ week +" JOIN (SELECT ZentrumID FROM Zentren WHERE Land = '"+ land +"') on ZentrumID = Zentrum";

			statement = connection.createStatement();
			randomizationList = new ArrayList<RandomizationWeek>();
			results = statement.executeQuery(query);

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
			query = "SELECT r1.PatientenID, r1.Zentrum, r1.Behandlungsarm, r1.Datum\r\n" + 
					"FROM Randomisierung_Woche_1 r1\r\n" + 
					"UNION\r\n" + 
					"SELECT 	r2.PatientenID, \r\n" + 
					"		CASE \r\n" + 
					"			WHEN r2.Zentrum IS NULL THEN 999\r\n" + 
					"			ELSE r2.Zentrum\r\n" + 
					"		END AS Zentrum, \r\n" + 
					"		r2.Behandlungsarm, r2.Datum\r\n" + 
					"FROM Randomisierung_Woche_2 r2\r\n" + 
					"WHERE PatientenID IS NOT NULL;";

			statement = connection.createStatement();
			randomizationList = new ArrayList<>();
			results = statement.executeQuery(query);

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

			query = "SELECT max(ZentrumID) + 1 as MAX_ID\n" +
					"FROM Zentren\n" +
					"WHERE Land = '"+ land +"'";

			statement = connection.createStatement();

			results = statement.executeQuery(query);

			while (results.next()){
				maxId = results.getInt("MAX_ID");
			}

		}catch (SQLException e){
			e.printStackTrace();
		}

		return maxId;
	}
}
