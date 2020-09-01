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
import de.fhdo.master.mi.sms.project.mamphi.model.InformedConsent;
import de.fhdo.master.mi.sms.project.mamphi.model.MonitorVisite;
import de.fhdo.master.mi.sms.project.mamphi.model.RandomizationWeek;
import de.fhdo.master.mi.sms.project.mamphi.model.Zentrum;

public class FetchData {

	private Connection connection;
	private Zentrum center;
	private List<Zentrum> centerList;
	private List<InformedConsent> consentList;
	private List<MonitorVisite> visiteList;
	private List<RandomizationWeek> randomizationList;
	private RandomizationWeek randWeekItem;
	private List<Integer> centerIDs;
	private List<Integer> centerListByWeek;
	private List<Integer> patientIDs;
	// db parameters
	private final String url = "jdbc:sqlite:C:\\Users\\biocl\\Desktop\\Beauclair\\USB DISK\\Master\\FH_DO\\PyDev\\sms-sose-2019-mamphi-administration\\data\\mamphi.db";

	public void updateZentrum(Zentrum center) {
		try {
			// create a connection to the database
			connection = DriverManager.getConnection(url);
			int result;

			String sql = "INSERT INTO Zentren VALUES (?, ?, ?, ?, ?)";

			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setLong(1, center.getZentrum_id());
			stmt.setString(2, center.getLand().equals("Deutschland") ? "D" : "GB");
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

	public List<Zentrum> fetchCenterData() {

		try {
			// create a connection to the database
			connection = DriverManager.getConnection(url);
			String query = "Select * from Zentren";

			Statement statement = connection.createStatement();

			ResultSet results = statement.executeQuery(query);

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
	
	public List<Zentrum> fetchCenterData(String land) {

		try {
			// create a connection to the database
			connection = DriverManager.getConnection(url);
			String query = "Select * from Zentren where Land = '" +land+"'";

			Statement statement = connection.createStatement();

			ResultSet results = statement.executeQuery(query);

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

	public List<InformedConsent> fetchInformedConsent() {
		try {
			// create a connection to the database
			connection = DriverManager.getConnection(url);

			String query = "select * from Informed_consent";

			Statement statement = connection.createStatement();

			ResultSet results = statement.executeQuery(query);
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

	public List<InformedConsent> fetchMissingConsent() {

		// create a connection to the database
		try {
			connection = DriverManager.getConnection(url);
			String query = "SELECT * FROM Informed_consent WHERE Einwilligung = 'nan' AND Datum != 'NaT'";

			Statement statement = connection.createStatement();

			ResultSet results = statement.executeQuery(query);
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

	public List<InformedConsent> fetchIncompletedConsent() {

		// create a connection to the database
		try {
			connection = DriverManager.getConnection(url);
			String query = "SELECT * FROM Informed_consent WHERE Einwilligung = 'nan'";

			Statement statement = connection.createStatement();

			ResultSet results = statement.executeQuery(query);
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

	public List<MonitorVisite> fetchMonitorVisites() {

		centerList = fetchCenterData();
		List<Integer> centerIDList = new ArrayList<Integer>();
		List<Integer> centerIDList2 = new ArrayList<Integer>();
		centerIDList = fetchZentrumByRandWeek(1);
		centerIDList2 = fetchZentrumByRandWeek(2);
		MonitorVisite sampleVisite;
		HashMap<String, Integer> centerFreq = new HashMap<String, Integer>();

		HashMap<String, Integer> centerFreqWeek2 = new HashMap<String, Integer>();
		HashMap<String, Integer> allCenterFreq = new HashMap<String, Integer>(centerFreq);

		visiteList = new ArrayList<MonitorVisite>();

		for (Integer integer : centerIDList) {
			if (!centerFreq.containsKey(integer.toString())) {
				centerFreq.put(integer.toString(), 1);
			} else {
				centerFreq.put(integer.toString(), centerFreq.get(integer.toString()) + 1);
			}
		}

		for (Integer integer : centerIDList2) {
			if (!centerFreqWeek2.containsKey(integer.toString())) {
				centerFreqWeek2.put(integer.toString(), 1);
			} else {
				centerFreqWeek2.put(integer.toString(), centerFreqWeek2.get(integer.toString()) + 1);
			}
		}

		centerFreq.forEach((key, value) -> {
			allCenterFreq.put(key, value);
		});

		centerFreqWeek2.forEach((key, value) -> {
			if (!allCenterFreq.containsKey(key)) {
				allCenterFreq.put(key, value);
			} else {
				allCenterFreq.replace(key, allCenterFreq.get(key) + value);
			}
		});

		LocalDate startDate = LocalDate.of(2019, 6, 1);
		LocalDate endDate = startDate.plusYears(2);
		List<LocalDate> listOfDates;
		int numberOfPatient = 0;
		String key;

		for (int i = 0; i < centerList.size() - 1; i++) {
			center = centerList.get(i);
			key = String.valueOf(center.getZentrum_id());
			numberOfPatient = allCenterFreq.containsKey(key) ? allCenterFreq.get(key) : 0;
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
			int result;

			String sql =  "INSERT INTO Random_Woche_"+week +" VALUES (?, ?, ?, ?)";

			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setLong(1, rand.getPatient_id());
			stmt.setLong(2, rand.getZentrum());
			stmt.setString(3, rand.getBehandlungsarm());
			stmt.setString(4, rand.getDate());

			result = stmt.executeUpdate();

			if (result != 0) {
				System.out.println("New row added to Randomization Table Week " + week);
			}
			connection.commit();
			connection.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<InformedConsent> fetchInformedConsentAfterRadomization() {

		// create a connection to the database
		try {
			connection = DriverManager.getConnection(url);
			String query = "SELECT * FROM Informed_consent WHERE Datum > '2019.06.03'";

			Statement statement = connection.createStatement();

			ResultSet results = statement.executeQuery(query);
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

	public void updateInformedConsent(InformedConsent consent) {

		// create a connection to the database
		try {
			connection = DriverManager.getConnection(url);
			int result;

			String sql = "INSERT INTO Informed_consent VALUES (?, ?, ?, ?)";

			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setLong(1, consent.getPatient_id());
			stmt.setLong(2, consent.getZentrum_id());
			stmt.setString(3, consent.getEinwilligung());
			stmt.setString(4, consent.getDate());

			result = stmt.executeUpdate();

			if (result != 0) {
				System.out.println("New row added to Informed Consent table!");
			}
			connection.commit();
			connection.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Integer> fetchCenterID() {

		try {
			// create a connection to the database
			connection = DriverManager.getConnection(url);
			String query = "select Zentrum_Id from Zentren";

			Statement statement = connection.createStatement();

			ResultSet results = statement.executeQuery(query);

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
	
	public List<PatientCenter> fetchNumberPatientenPerCenterByLandByWeek(String land, int week) {

		List<PatientCenter> numberOfPatientPerCenterGermany = new ArrayList<PatientCenter>();
		List<PatientCenter> numberOfPatientPerCenterGB = new ArrayList<PatientCenter>();

		List<PatientCenter> allPatientCenter = fetchNumberOfPatientPerCenterByWeek(week);

		allPatientCenter.forEach((e) -> {
			if (Integer.parseInt(e.getCenter()) < 200) {
				numberOfPatientPerCenterGermany.add(e);
			} else {
				numberOfPatientPerCenterGB.add(e);
			}
		});

		return land.equals("Deutschland") ? numberOfPatientPerCenterGermany : numberOfPatientPerCenterGB;
	}
	
	public List<PatientCenter> fetchNumberOfPatientPerCenterByWeek(int week) {

		centerListByWeek = fetchZentrumByRandWeek(week);
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

	public List<Integer> fetchZentrumByRandWeek(int week) {
		try {
			// create a connection to the database
			connection = DriverManager.getConnection(url);
			String query = "SELECT Zentrum FROM Random_Woche_"+week;

			Statement statement = connection.createStatement();
			centerListByWeek = new ArrayList<Integer>();
			ResultSet results = statement.executeQuery(query);

			// loop through the result set
			while (results.next()) {

				centerListByWeek.add(results.getInt("Zentrum"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return centerListByWeek;
	}

	public List<Integer> fetchPatientenID() {

		try {
			// create a connection to the database
			connection = DriverManager.getConnection(url);
			String query = "select Patient_Id from Informed_consent";

			Statement statement = connection.createStatement();
			patientIDs = new ArrayList<>();
			ResultSet results = statement.executeQuery(query);

			// loop through the result set
			while (results.next()) {
				patientIDs.add(results.getInt("Patient_Id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return patientIDs;

	}

	public List<RandomizationWeek> fetchRandomWeek(int week) {

		try {
			// create a connection to the database
			connection = DriverManager.getConnection(url);
			String query = "SELECT * FROM Random_Woche_"+week;

			Statement statement = connection.createStatement();
			randomizationList = new ArrayList<RandomizationWeek>();
			ResultSet results = statement.executeQuery(query);

			// loop through the result set
			while (results.next()) {

				randWeekItem = new RandomizationWeek(results.getInt("Patient_Id"), results.getInt("Zentrum"),
						results.getString("Behandlungsarm"), results.getString("Datum"));
				randomizationList.add(randWeekItem);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return randomizationList;
	}
}
