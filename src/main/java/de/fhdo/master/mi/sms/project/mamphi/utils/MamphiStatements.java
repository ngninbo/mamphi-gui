package de.fhdo.master.mi.sms.project.mamphi.utils;

public class MamphiStatements {


    public static final String MAMPHI_DB_URL = "jdbc:sqlite:mamphi.db";
    public static final String INTO_CENTER_VALUES = "INSERT INTO Zentren VALUES (?, ?, ?, ?, ?)";
    public static final String GERMANY = "Deutschland";
    public static final String CENTER_ADD_SUCCESS_MSG = "New row added to zentren table!";
    public static final String SELECT_FROM_CENTER = "Select * from Zentren";
    public static final String SELECT_FROM_CENTER_WHERE_LAND = "Select * from Zentren where Land = '%s'";
    public static final String SELECT_FROM_INFORMED_CONSENT = "select patientenID, Zentrum, Einwilligung, Datum from Informed_consent";
    public static final String SELECT_FROM_INFORMED_CONSENT_WHERE_CONSENT = "select patientenID, Zentrum, Einwilligung, Datum from Informed_consent WHERE Einwilligung = '%s'";
    public static final String SELECT_INCOMPLETE_CONSENT = "SELECT PatientenID, Zentrum, Einwilligung, Datum " +
            "FROM Informed_consent " +
            "WHERE Einwilligung IS NULL OR Datum IS NULL;";

    public static final String SELECT_MISSING_CONSENT = "SELECT PatientenID, Zentrum, Einwilligung, Datum " +
            "FROM Informed_consent WHERE Einwilligung IS NULL;";

    public static final String SELECT_LATE_INFORMED_CONSENT = "SELECT * FROM Informed_consent\r\n" +
            "WHERE substr(Datum,7)||substr(Datum,4,2)||substr(Datum,1,2) > '20190603';";

    public static final String YES = "ja";
    public static final String NO = "nein";

    public static final String SELECT_MONITOR_PLAN_WITH_ALL_CENTER_INVOLVED = "SELECT z.ZentrumID, z.Land, z.Ort, z.Pruefer, z.Monitor, zg.Gesamtanzahl as Gesamtanzahl\r\n" +
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
            "ON z.ZentrumID = zg.ZentrumID;";

    public static final String SELECT_MONITOR_PLAN_WITH_ANY_CENTER_INVOLVED = "SELECT z.ZentrumID, z.Land, z.Ort, z.Pruefer, z.Monitor, zg.Gesamtanzahl \r\n" +
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

    public static final String UPDATE_RANDOMIZATION_WEEK = "INSERT INTO Randomisierung_Woche_%s (PatientenID, Zentrum, Behandlungsarm, Datum) VALUES (?, ?, ?, ?)";
    public static final String UPDATE_RANDOMIZATION_WEEK_SUCCESS_MSG = "New row added to Randomization Table Week %s\n";
    public static final String UPDATE_INFORMED_CONSENT = "INSERT INTO Informed_consent (PatientenID, Zentrum, Einwilligung, Datum) VALUES (?, ?, ?, ?)";
    public static final String UPDATE_INFORMED_CONSENT_SUCCESS_MSG = "New row added to Informed Consent table!";
    public static final String SELECT_CENTER_ID_FROM_CENTER = "select ZentrumID from Zentren";
    public static final String FETCH_PATIENT_PER_CENTER_PER_WEEK_BY_LAND = "" +
            "SELECT Zentrum, count(Zentrum) as Anzahl " +
            "FROM Randomisierung_Woche_%s " +
            "JOIN (SELECT ZentrumID FROM Zentren WHERE Land = '%s') on ZentrumID = Zentrum GROUP BY Zentrum";

    public static final String FETCH_PATIENT_PER_CENTER_BY_WEEK = "" +
            "SELECT CASE \r\n" +
            "			WHEN r.Zentrum IS NULL THEN 999\r\n" +
            "			ELSE r.Zentrum\r\n" +
            "		END AS Zentrum, \r\n" +
            "		COUNT(r.Zentrum) as Anzahl\r\n" +
            "FROM Randomisierung_Woche_%s r\r\n" +
            "GROUP BY r.Zentrum;";

    public static final String FETCH_NUM_PATIENT_PER_CENTER_ALL_WEEK = "SELECT za.Zentrum, SUM(za.ANZAHL) AS Gesamtanzahl \r\n" +
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

    public static final String SELECT_PATIENT_ID_FROM_INFORMED_CONSENT = "select patientenID from Informed_consent";
    public static final String FETCH_ALL_RANDOMIZATION_BY_WEEK = "SELECT 	r.PatientenID, \r\n" +
            "		CASE \r\n" +
            "			WHEN r.Zentrum IS NULL THEN 999\r\n" +
            "			ELSE r.Zentrum\r\n" +
            "		END AS Zentrum, \r\n" +
            "		r.Behandlungsarm, r.Datum\r\n" +
            "FROM Randomisierung_Woche_%s r\r\n" +
            "WHERE PatientenID IS NOT NULL;";

    public static final String FETCH_ALL_RANDOMIZATION_BY_WEEK_AND_LAND = "" +
            "SELECT patientenID, Zentrum, Behandlungsarm, Datum " +
            "FROM Randomisierung_Woche_%s " +
            "JOIN (SELECT ZentrumID FROM Zentren WHERE Land = '%s') on ZentrumID = Zentrum";

    public static final String FETCH_ALL_RANDOMIZATION_WEEK_ITEMS = "SELECT r1.PatientenID, r1.Zentrum, r1.Behandlungsarm, r1.Datum\r\n" +
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

    public static final String FETCH_NEXT_CENTER_ID_LAND = "SELECT max(ZentrumID) + 1 as MAX_ID\n" +
            "FROM Zentren\n" +
            "WHERE Land = '%s'";
}
