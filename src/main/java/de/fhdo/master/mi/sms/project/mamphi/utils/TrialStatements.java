package de.fhdo.master.mi.sms.project.mamphi.utils;

public class TrialStatements {

    // TODO: Set the database file name here
    public static final String DATABASE_NAME = "mamphi.db";
    public static final String SQL_SCRIPT = "trial.db.sql";
    public static final String TRIAL_DB_URL = "jdbc:sqlite:%s";
    public static final String INTO_CENTER_VALUES = "INSERT INTO centres VALUES (?, ?, ?, ?, ?)";
    public static final String CENTER_ADD_SUCCESS_MSG = "New row added to centres table!";
    public static final String SELECT_FROM_CENTER = "Select * from centres";
    public static final String SELECT_FROM_CENTER_WHERE_COUNTRY = "Select * from centres where Country = '%s'";
    public static final String SELECT_FROM_INFORMED_CONSENT = "select patientID, centre, consent, Date from Informed_consent";
    public static final String SELECT_FROM_INFORMED_CONSENT_WHERE_CONSENT = "select patientID, centre, consent, Date from Informed_consent WHERE consent = '%s'";
    public static final String SELECT_INCOMPLETE_CONSENT = "SELECT patientID, centre, consent, Date " +
            "FROM Informed_consent " +
            "WHERE consent IS NULL OR Date IS NULL;";

    public static final String SELECT_MISSING_CONSENT = "SELECT patientID, centre, consent, Date " +
            "FROM Informed_consent WHERE consent IS NULL;";

    public static final String SELECT_LATE_INFORMED_CONSENT = "SELECT * FROM Informed_consent\r\n" +
            "WHERE substr(Date,7)||substr(Date,4,2)||substr(Date,1,2) > '20190603';";


    public static final String SELECT_MONITOR_PLAN_WITH_ALL_CENTER_INVOLVED = "SELECT z.centreID, z.Country, z.Place, z.trier, z.Monitor, zg.TotalNumberOfPatient as TotalNumberOfPatient\r\n" +
            "FROM(SELECT * FROM centres ORDER BY centreID ASC) z\r\n" +
            "JOIN (	SELECT centreID, TotalNumberOfPatient \r\n" +
            "		FROM (SELECT centreID FROM centres ORDER BY centreID ASC)\r\n" +
            "		LEFT JOIN (	SELECT centre, SUM(NumberOfPatient) as TotalNumberOfPatient\r\n" +
            "					FROM(	SELECT r1.centre, COUNT(r1.centre) as NumberOfPatient\r\n" +
            "							FROM Randomization_Week_1 r1 \r\n" +
            "							GROUP BY r1.centre\r\n" +
            "							UNION 	SELECT r2.centre, COUNT(r2.centre) as NumberOfPatient\r\n" +
            "									FROM Randomization_Week_2 r2 \r\n" +
            "									GROUP BY r2.centre)\r\n" +
            "					GROUP BY centre) \r\n" +
            "		ON centreID = centre) zg \r\n" +
            "ON z.centreID = zg.centreID;";

    public static final String SELECT_MONITOR_PLAN_WITH_ANY_CENTER_INVOLVED = "SELECT z.centreID, z.Country, z.Place, z.trier, z.Monitor, zg.TotalNumberOfPatient \r\n" +
            "FROM centres z\r\n" +
            "JOIN (	SELECT za.centre, SUM(za.NumberOfPatient) AS TotalNumberOfPatient \r\n" +
            "		FROM (	SELECT r1.centre, COUNT(r1.centre) as NumberOfPatient\r\n" +
            "				FROM Randomization_Week_1 r1\r\n" +
            "				GROUP BY r1.centre\r\n" +
            "				UNION\r\n" +
            "				SELECT 	CASE \r\n" +
            "							WHEN r2.centre IS NULL THEN 999\r\n" +
            "							ELSE r2.centre\r\n" +
            "						END AS centre, \r\n" +
            "						COUNT(r2.centre) as NumberOfPatient\r\n" +
            "				FROM Randomization_Week_2 r2\r\n" +
            "				GROUP BY r2.centre) za\r\n" +
            "		GROUP BY centre) zg \r\n" +
            "ON zg.centre = z.centreID;";

    public static final String UPDATE_RANDOMIZATION_WEEK = "INSERT INTO Randomization_Week_%s (patientID, centre, TreatmentGroup Date) VALUES (?, ?, ?, ?)";
    public static final String UPDATE_RANDOMIZATION_WEEK_SUCCESS_MSG = "New row added to Randomization Table Week %s\n";
    public static final String UPDATE_INFORMED_CONSENT = "INSERT INTO Informed_consent (patientID, centre, consent, Date) VALUES (?, ?, ?, ?)";
    public static final String UPDATE_INFORMED_CONSENT_SUCCESS_MSG = "New row added to Informed Consent table!";
    public static final String SELECT_CENTER_ID_FROM_CENTER = "select centreID from centres";
    public static final String FETCH_PATIENT_PER_CENTER_PER_WEEK_BY_COUNTRY = "" +
            "SELECT centre, count(centre) as NumberOfPatient " +
            "FROM Randomization_Week_%s " +
            "JOIN (SELECT centreID FROM centres WHERE Country = '%s') on centreID = centre GROUP BY centre";

    public static final String FETCH_PATIENT_PER_CENTER_BY_WEEK = "" +
            "SELECT CASE \r\n" +
            "			WHEN r.centre IS NULL THEN 999\r\n" +
            "			ELSE r.centre\r\n" +
            "		END AS centre, \r\n" +
            "		COUNT(r.centre) as NumberOfPatient\r\n" +
            "FROM Randomization_Week_%s r\r\n" +
            "GROUP BY r.centre;";

    public static final String FETCH_NUM_PATIENT_PER_CENTER_ALL_WEEK = "SELECT za.centre, SUM(za.NumberOfPatient) AS TotalNumberOfPatient \r\n" +
            "FROM (	SELECT r1.centre, COUNT(r1.centre) as NumberOfPatient\r\n" +
            "		FROM Randomization_Week_1 r1\r\n" +
            "		GROUP BY r1.centre\r\n" +
            "		UNION\r\n" +
            "		SELECT 	CASE \r\n" +
            "					WHEN r2.centre IS NULL THEN 999\r\n" +
            "					ELSE r2.centre\r\n" +
            "				END AS centre, \r\n" +
            "				COUNT(r2.centre) as NumberOfPatient\r\n" +
            "		FROM Randomization_Week_2 r2\r\n" +
            "		GROUP BY r2.centre) za\r\n" +
            "GROUP BY centre;";

    public static final String SELECT_PATIENT_ID_FROM_INFORMED_CONSENT = "select patientID from Informed_consent";
    public static final String FETCH_ALL_RANDOMIZATION_BY_WEEK = "SELECT r.patientID, \r\n" +
            "		CASE \r\n" +
            "			WHEN r.centre IS NULL THEN 999\r\n" +
            "			ELSE r.centre\r\n" +
            "		END AS centre, \r\n" +
            "		r.TreatmentGroup, r.Date\r\n" +
            "FROM Randomization_Week_%s r\r\n" +
            "WHERE patientID IS NOT NULL;";

    public static final String FETCH_ALL_RANDOMIZATION_BY_WEEK_AND_COUNTRY = "" +
            "SELECT patientID, centre, TreatmentGroup, Date " +
            "FROM Randomization_Week_%s " +
            "JOIN (SELECT centreID FROM centres WHERE Country = '%s') on centreID = centre";

    public static final String FETCH_ALL_RANDOMIZATION_WEEK_ITEMS = "SELECT r1.patientID, r1.centre, r1.TreatmentGroup, r1.Date\r\n" +
            "FROM Randomization_Week_1 r1\r\n" +
            "UNION\r\n" +
            "SELECT 	r2.patientID, \r\n" +
            "		CASE \r\n" +
            "			WHEN r2.centre IS NULL THEN 999\r\n" +
            "			ELSE r2.centre\r\n" +
            "		END AS centre, \r\n" +
            "		r2.TreatmentGroup, r2.Date\r\n" +
            "FROM Randomization_Week_2 r2\r\n" +
            "WHERE patientID IS NOT NULL;";

    public static final String FETCH_NEXT_CENTRE_ID_BY_COUNTRY = "SELECT max(centreID) + 1 as MAX_ID\n" +
            "FROM centres\n" +
            "WHERE Country = '%s'";
}
