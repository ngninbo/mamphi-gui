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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
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
	private List<Integer> centerListByWeek;
	private List<Integer> patientIDs;
	private static String query;

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

			centerList = new ArrayList<Zentrum>();

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

			centerList = new ArrayList<Zentrum>();

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

			query = "select * from Informed_consent";

			statement = connection.createStatement();

			results = statement.executeQuery(query);
			InformedConsent consent;
			consentList = new ArrayList<InformedConsent>();

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
				query = "SELECT * FROM Informed_consent WHERE Einwilligung = 'nan'";
			} else if (consent.equals(Consent.MISSING)) {
				query = "SELECT * FROM Informed_consent WHERE Einwilligung = 'nan' AND Datum != 'NaT'";
			} else {
				query = "SELECT * FROM Informed_consent WHERE Einwilligung != 'nan' AND Datum > '2019.06.03'";
			}

			statement = connection.createStatement();

			results = statement.executeQuery(query);
			InformedConsent informedConsent;
			consentList = new ArrayList<InformedConsent>();
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

		centerList = fetchAllCenter();
		List<Integer> centerIDList = fetchZentrumByAllRandWeek();
		MonitorVisite sampleVisite;
		HashMap<String, Integer> centerFreq = new HashMap<String, Integer>();

		visiteList = new ArrayList<MonitorVisite>();

		for (Integer integer : centerIDList) {
			if (!centerFreq.containsKey(integer.toString())) {
				centerFreq.put(integer.toString(), 1);
			} else {
				centerFreq.put(integer.toString(), centerFreq.get(integer.toString()) + 1);
			}
		}

		centerFreq.forEach((key, value) -> {
			centerFreq.put(key, value);
		});


		LocalDate startDate = LocalDate.of(2019, 6, 1);
		LocalDate endDate = startDate.plusYears(2);
		List<LocalDate> listOfDates;
		int numberOfPatient = 0;
		String key;

		for (Zentrum center : centerList) {
			key = String.valueOf(center.getZentrum_id());
			numberOfPatient = centerFreq.containsKey(key) ? centerFreq.get(key) : 0;
			if (numberOfPatient >= 10) {
				listOfDates = startDate.datesUntil(endDate, Period.ofMonths(1)).collect(Collectors.toList());
				sampleVisite = new MonitorVisite(center, numberOfPatient, listOfDates.subList(0, 5));
			} else if (numberOfPatient > 4 && numberOfPatient < 10) {
				listOfDates = startDate.datesUntil(endDate, Period.ofMonths(2)).collect(Collectors.toList());
				sampleVisite = new MonitorVisite(center, numberOfPatient, listOfDates.subList(0, 5));
			}

			else if (numberOfPatient > 0 && numberOfPatient < 5) {
				listOfDates = startDate.datesUntil(endDate, Period.ofMonths(3)).collect(Collectors.toList());
				sampleVisite = new MonitorVisite(center, numberOfPatient, listOfDates.subList(0, 5));
			} else {
				listOfDates = new ArrayList<LocalDate>();
				sampleVisite = new MonitorVisite(center, numberOfPatient, listOfDates);
			}

			visiteList.add(sampleVisite);
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

		List<PatientCenter> numberOfPatientPerCenterGermany = new ArrayList<PatientCenter>();
		List<PatientCenter> numberOfPatientPerCenterGB = new ArrayList<PatientCenter>();

		List<PatientCenter> allPatientCenter = fetchAllNumberOfPatientPerCenterByWeek(week);

		allPatientCenter.forEach((e) -> {
			if (Integer.parseInt(e.getCenter()) < 200) {
				numberOfPatientPerCenterGermany.add(e);
			} else {
				numberOfPatientPerCenterGB.add(e);
			}
		});

		return land.equals(Land.D) ? numberOfPatientPerCenterGermany : numberOfPatientPerCenterGB;
	}

	public List<PatientCenter> fetchAllNumberOfPatientPerCenterByWeek(int week) {

		centerListByWeek = fetchAllZentrumByRandWeek(week);
		HashMap<String, Integer> centerFreq = new HashMap<String, Integer>();
		List<PatientCenter> list = new ArrayList<PatientCenter>();

		for (Integer integer : centerListByWeek) {
			if (!centerFreq.containsKey(integer.toString())) {
				centerFreq.put(integer.toString(), 1);
			} else {
				centerFreq.put(integer.toString(), centerFreq.get(integer.toString()) + 1);
			}
		}

		centerFreq.forEach((key, value) -> {
			PatientCenter patientCenter = new PatientCenter(key, value);
			list.add(patientCenter);
		});

		Collections.sort(list, new Comparator<PatientCenter>() {

			@Override
			public int compare(PatientCenter o1, PatientCenter o2) {
				// TODO Auto-generated method stub
				return o1.getCenter().compareTo(o2.getCenter());
			}
		});

		return list;
	}
	
	public List<PatientCenter> fetchNumberOfPatientPerCenterByAllWeek() {

		List<Integer> centerListAllWeek = fetchZentrumByAllRandWeek();
		HashMap<String, Integer> centerFreq = new HashMap<String, Integer>();
		List<PatientCenter> list = new ArrayList<PatientCenter>();

		for (Integer integer : centerListAllWeek) {
			if (!centerFreq.containsKey(integer.toString())) {
				centerFreq.put(integer.toString(), 1);
			} else {
				centerFreq.put(integer.toString(), centerFreq.get(integer.toString()) + 1);
			}
		}

		centerFreq.forEach((key, value) -> {
			PatientCenter patientCenter = new PatientCenter(key, value);
			list.add(patientCenter);
		});

		Collections.sort(list, new Comparator<PatientCenter>() {

			@Override
			public int compare(PatientCenter o1, PatientCenter o2) {
				// TODO Auto-generated method stub
				return o1.getCenter().compareTo(o2.getCenter());
			}
		});

		return list;
	}

	public List<Integer> fetchAllZentrumByRandWeek(int week) {
		try {
			// create a connection to the database
			connection = DriverManager.getConnection(url);
			query = "SELECT Zentrum FROM Random_Woche_" + week;

			statement = connection.createStatement();
			centerListByWeek = new ArrayList<Integer>();
			results = statement.executeQuery(query);

			// loop through the result set
			while (results.next()) {

				centerListByWeek.add(results.getInt("Zentrum"));
			}
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return centerListByWeek;
	}
	
	public List<Integer> fetchZentrumByAllRandWeek() {
		try {
			// create a connection to the database
			connection = DriverManager.getConnection(url);
			query = "SELECT Zentrum FROM Random_Woche_1 UNION ALL SELECT Zentrum FROM Random_Woche_2"; 

			statement = connection.createStatement();
			centerListByWeek = new ArrayList<Integer>();
			results = statement.executeQuery(query);

			// loop through the result set
			while (results.next()) {

				centerListByWeek.add(results.getInt("Zentrum"));
			}
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return centerListByWeek;
	}

	public List<Integer> fetchPatientenAllID() {

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
			query = "SELECT * FROM Random_Woche_" + week;

			statement = connection.createStatement();
			randomizationList = new ArrayList<RandomizationWeek>();
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
			query = "SELECT * FROM Random_Woche_1 UNION SELECT * FROM Random_Woche_2";

			statement = connection.createStatement();
			randomizationList = new ArrayList<RandomizationWeek>();
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
}
