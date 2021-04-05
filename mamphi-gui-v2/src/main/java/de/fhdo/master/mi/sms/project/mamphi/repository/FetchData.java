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
	private final String url = "jdbc:sqlite:C:\\mamphi\\mamphi.db";

	public void updateZentrum(Zentrum center) {
		try {
			// create a connection to the database
			connection = DriverManager.getConnection(url);

			query = "INSERT INTO Zentren VALUES (?, ?, ?, ?, ?)";

			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setLong(1, center.getZentrum_id());
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
						results.getInt("Zentrum_Id"));
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
						results.getInt("Zentrum_Id"));
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

			query = "select Patient_Id, Zentrum, Einwilligung, strftime('%d.%m.%Y', Datum) as Datum from Informed_consent";

			statement = connection.createStatement();

			results = statement.executeQuery(query);
			InformedConsent consent;
			consentList = new ArrayList<>();

			// loop through the result set
			while (results.next()) {

				consent = new InformedConsent(results.getInt("Patient_Id"), results.getInt("Zentrum"),
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
				query = "SELECT Patient_Id, Zentrum, Einwilligung, strftime('%d.%m.%Y', Datum) as Datum " +
						"FROM Informed_consent WHERE Einwilligung = 'nan'";
			} else if (consent.equals(Consent.MISSING)) {
				query = "SELECT Patient_Id, Zentrum, Einwilligung, strftime('%d.%m.%Y', Datum) as Datum " +
						"FROM Informed_consent WHERE Einwilligung = 'nan' AND Datum != 'NaT'";
			} else {
				query = "SELECT Patient_Id, Zentrum, Einwilligung, strftime('%d.%m.%Y', Datum) as Datum " +
						"FROM Informed_consent WHERE Einwilligung != 'nan' AND Datum > '2019.06.03'";
			}

			statement = connection.createStatement();

			results = statement.executeQuery(query);
			InformedConsent informedConsent;
			consentList = new ArrayList<>();
			// loop through the result set
			while (results.next()) {

				informedConsent = new InformedConsent(results.getInt("Patient_Id"), results.getInt("Zentrum"),
						results.getString("Einwilligung").toUpperCase(), results.getString("Datum"));
				consentList.add(informedConsent);

			}
			connection.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return consentList;
	}

    public List<MonitorVisite> fetchMonitorVisites() {

        try {
            connection = DriverManager.getConnection(url);

            query = "SELECT tab1.Zentrum_Id, tab1.Land, tab1.Ort, tab1.Pruefer, tab1.Monitor, tab2.Gesamtanzahl\n" +
					"FROM\n" +
					"(SELECT * FROM Zentren ORDER BY Zentrum_Id ASC) tab1\n" +
					"JOIN (SELECT Zentrum_Id, Gesamtanzahl \n" +
					"FROM\n" +
					"(SELECT Zentrum_Id FROM Zentren ORDER BY Zentrum_Id ASC)\n" +
					"LEFT JOIN (SELECT Zentrum, SUM(Anzahl) as Gesamtanzahl\n" +
					"FROM\n" +
					"(SELECT Random_Woche_1.Zentrum, COUNT(Random_Woche_1.Zentrum) as Anzahl\n" +
					"FROM Random_Woche_1 GROUP BY Random_Woche_1.Zentrum\n" +
					"UNION SELECT Random_Woche_2.Zentrum, COUNT(Random_Woche_2.Zentrum) as Anzahl\n" +
					"FROM Random_Woche_2 GROUP BY Random_Woche_2.Zentrum)\n" +
					"GROUP BY Zentrum) ON Zentrum_Id = Zentrum) tab2 ON tab1.Zentrum_Id = tab2.Zentrum_Id;";

			statement = connection.createStatement();

			results = statement.executeQuery(query);

            visiteList = new ArrayList<>();

            while (results.next()) {

            	MonitorVisite monitorVisite = new MonitorVisite(results.getInt("Zentrum_Id"),
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

			query = "INSERT INTO Random_Woche_" + week + " VALUES (?, ?, ?, ?)";

			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setLong(1, rand.getPatient_id());
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
			query = "INSERT INTO Informed_consent VALUES (?, ?, ?, ?)";

			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setLong(1, consent.getPatient_id());
			stmt.setLong(2, consent.getZentrum_id());
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
			query = "select Zentrum_Id from Zentren";

			statement = connection.createStatement();

			results = statement.executeQuery(query);

			centerIDs = new ArrayList<>();

			// loop through the result set
			while (results.next()) {
				centerIDs.add(results.getInt("Zentrum_Id"));
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

            if (land.equals(Land.D)) {

                query = "SELECT tab1.Zentrum as Zentrum, tab1.Anzahl as Anzahl, tab2.Land as Land_kurz, Replace(Land, 'D', 'Deutschland') as Land_long "
                        + "FROM (SELECT Random_Woche_" + week + ".Zentrum, COUNT(Random_Woche_" + week
                        + ".Zentrum) as Anzahl " + "FROM Random_Woche_" + week + " GROUP BY Random_Woche_" + week
                        + ".Zentrum) tab1 "
                        + "LEFT JOIN (SELECT Zentrum_Id, Land FROM Zentren) tab2 ON tab1.Zentrum = tab2.Zentrum_Id WHERE Land_kurz = 'D'";
            } else {

                query = "SELECT tab1.Zentrum as Zentrum, tab1.Anzahl as Anzahl, tab2.Land as Land_kurz, Replace(Land, 'GB', 'Großbritanien') as Land_long "
                        + "FROM (SELECT Random_Woche_" + week + ".Zentrum, COUNT(Random_Woche_" + week
                        + ".Zentrum) as Anzahl " + "FROM Random_Woche_" + week + " GROUP BY Random_Woche_" + week
                        + ".Zentrum) tab1 "
                        + "LEFT JOIN (SELECT Zentrum_Id, Land FROM Zentren) tab2 ON tab1.Zentrum = tab2.Zentrum_Id WHERE Land_kurz = 'GB'";
            }

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
            query = "SELECT Random_Woche_" + week + ".Zentrum, COUNT(Random_Woche_" + week + ".Zentrum) as Anzahl "
                    + "FROM Random_Woche_" + week + " GROUP BY Random_Woche_" + week + ".Zentrum";
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

            query = "SELECT Zentrum_Id, Gesamtanzahl \n" +
					"FROM\n" +
					"(SELECT Zentrum_Id FROM Zentren ORDER BY Zentrum_Id ASC)\n" +
					"LEFT JOIN (SELECT Zentrum, SUM(Anzahl) as Gesamtanzahl\n" +
					"FROM\n" +
					"(SELECT Random_Woche_1.Zentrum, COUNT(Random_Woche_1.Zentrum) as Anzahl\n" +
					"FROM Random_Woche_1 GROUP BY Random_Woche_1.Zentrum\n" +
					"UNION SELECT Random_Woche_2.Zentrum, COUNT(Random_Woche_2.Zentrum) as Anzahl\n" +
					"FROM Random_Woche_2 GROUP BY Random_Woche_2.Zentrum)\n" +
					"GROUP BY Zentrum) ON Zentrum_Id = Zentrum;";

			statement = connection.createStatement();

			results = statement.executeQuery(query);
            // results = connection.createStatement().executeQuery(query);

            while (results.next()) {
                list.add(new PatientCenter(results.getString("Zentrum_Id"), results.getInt("Gesamtanzahl")));
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
			query = "select Patient_Id from Informed_consent";

			statement = connection.createStatement();
			patientIDs = new ArrayList<>();
			results = statement.executeQuery(query);

			// loop through the result set
			while (results.next()) {
				patientIDs.add(results.getInt("Patient_Id"));
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
			query = "SELECT Patient_Id, Zentrum, Behandlungsarm, strftime('%d.%m.%Y', Datum) as Datum FROM Random_Woche_" + week;

			statement = connection.createStatement();
			randomizationList = new ArrayList<>();
			results = statement.executeQuery(query);

			// loop through the result set
			while (results.next()) {

				randWeekItem = new RandomizationWeek(results.getInt("Patient_Id"), results.getInt("Zentrum"),
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
			query = "SELECT Patient_Id, Zentrum, Behandlungsarm, strftime('%d.%m.%Y', Datum) as Datum " +
					"FROM Random_Woche_1 " +
					"UNION SELECT Patient_Id, Zentrum, Behandlungsarm, strftime('%d.%m.%Y', Datum) as Datum " +
					"FROM Random_Woche_2";

			statement = connection.createStatement();
			randomizationList = new ArrayList<>();
			results = statement.executeQuery(query);

			// loop through the result set
			while (results.next()) {

				randWeekItem = new RandomizationWeek(results.getInt("Patient_Id"), results.getInt("Zentrum"),
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

			query = "SELECT max(Zentrum_Id) + 1 as MAX_ID\n" +
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
