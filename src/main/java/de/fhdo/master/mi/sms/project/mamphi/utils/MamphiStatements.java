package de.fhdo.master.mi.sms.project.mamphi.utils;

public class MamphiStatements {

    // TODO: Set the database file name here
    public static final String DATABASE = "mamphi.db";

    public static final String TRIAL_DB_URL = "jdbc:sqlite:%s";
    public static final String INTO_CENTER_VALUES = "INSERT INTO Zentren VALUES (?, ?, ?, ?, ?)";
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

    public static final String CREATE_CENTER_TABLE = "CREATE TABLE IF NOT EXISTS Zentren (" +
            "ZentrumID INTEGER,\n" +
            "Land TEXT,\n" +
            "Ort TEXT,\n" +
            "Pruefer TEXT,\n" +
            "Monitor TEXT\n" +
            ");";

    public static final String TABLE_CENTER_INIT_DATA = "INSERT INTO Zentren (ZentrumID, Land, Ort, Pruefer, Monitor ) " +
            "VALUES (101,'D','Dortmund','Meyer','Wittmann'),\n" +
            " (102,'D','Essen','Müller','Bruch'),\n" +
            " (103,'D','München','Moser','Wittmann'),\n" +
            " (104,'D','Herne','Kwiatkowski','Wittmann'),\n" +
            " (105,'D','Hannover','Meyer','Lange'),\n" +
            " (106,'D','Köln','Brettschneider','Bruch'),\n" +
            " (107,'D','Bremen','Van Gool','Lange'),\n" +
            " (108,'D','Hamburg','Talmann-Berg','Lange'),\n" +
            " (109,'D','Stuttgart','Fischer','Bruch'),\n" +
            " (110,'D','Lepzig','Obermeier','Bruch'),\n" +
            " (201,'GB','London','McDonald','Gordon'),\n" +
            " (202,'GB','London','Priest','Thatcher'),\n" +
            " (203,'GB','Manchester','Down','Gordon'),\n" +
            " (204,'GB','Brighton','Feldham','Gordon'),\n" +
            " (205,'GB','Leeds','Harnister','Thatcher');";


    public static final String CREATE_INFORMED_CONSENT_TABLE = "CREATE TABLE IF NOT EXISTS " +
            "Informed_consent (" +
            "PatientenID INTEGER, " +
            "Zentrum INTEGER, " +
            "Einwilligung TEXT, " +
            "Datum	TEXT, " +
            "field5	TEXT);";

    public static final String TABLE_INFORMED_CONSENT_INIT_DATA = "INSERT INTO Informed_consent " +
            "(PatientenID, Zentrum, Einwilligung, Datum, field5 ) VALUES " +
            " (1000,102,'nein','10.05.2019',NULL),\n" +
            " (1001,201,'ja','11.05.2019',NULL),\n" +
            " (1002,202,'ja','12.05.2019',NULL),\n" +
            " (1003,101,'ja','13.05.2019',NULL),\n" +
            " (1004,102,'ja','13.05.2019',NULL),\n" +
            " (1005,104,'ja','13.05.2019',NULL),\n" +
            " (1006,103,'ja','13.05.2019',NULL),\n" +
            " (1007,105,'ja',NULL,NULL),\n" +
            " (1008,106,'ja','13.05.2019',NULL),\n" +
            " (1009,108,'ja','14.05.2019',NULL),\n" +
            " (1010,203,'ja','14.05.2019',NULL),\n" +
            " (1011,204,'ja','14.05.2019',NULL),\n" +
            " (1012,205,'ja','14.06.2019',NULL),\n" +
            " (1013,109,NULL,'14.05.2019',NULL),\n" +
            " (1014,110,'ja','14.05.2019',NULL),\n" +
            " (1016,201,'ja','14.05.2019',NULL),\n" +
            " (1017,201,'ja','14.05.2019',NULL),\n" +
            " (1018,203,'ja','14.05.2019',NULL),\n" +
            " (1019,104,'ja','14.05.2019',NULL),\n" +
            " (1020,104,'ja','14.01.1900',NULL),\n" +
            " (1021,103,'ja','15.05.2019',NULL),\n" +
            " (1022,110,'ja','16.05.2019',NULL),\n" +
            " (1023,108,'ja','16.05.2019',NULL),\n" +
            " (1024,101,'ja','16.05.2019',NULL),\n" +
            " (1025,102,'ja','15.05.2091',NULL),\n" +
            " (1026,105,'ja','16.05.2019',NULL),\n" +
            " (1027,106,NULL,NULL,NULL),\n" +
            " (1028,106,'ja','16.05.2019',NULL),\n" +
            " (1029,204,'ja','16.05.2019',NULL),\n" +
            " (1030,207,'ja','16.05.2019',NULL);";

    public static final String CREATE_RANDOM_WEEK_1_TABLE = "CREATE TABLE IF NOT EXISTS Randomisierung_Woche_1 (" +
            "PatientenID INTEGER," +
            "Zentrum INTEGER," +
            "Behandlungsarm TEXT," +
            "Datum TEXT," +
            "field5 TEXT" +
            ");";

    public static final String RANDOM_WEEK_1_INIT_DATA = "INSERT INTO Randomisierung_Woche_1 " +
            "(PatientenID, Zentrum, Behandlungsarm, Datum, field5) VALUES " +
            " (1001,201,'A','12.05.2019',NULL),\n" +
            " (1002,202,'B','12.05.2019',NULL),\n" +
            " (1003,101,'A','12.05.2019',NULL),\n" +
            " (1004,102,'A','13.05.2019',NULL),\n" +
            " (1005,104,'B','13.05.2019',NULL),\n" +
            " (1006,103,'B','13.05.2019',NULL),\n" +
            " (1007,105,'A','13.05.2019',NULL),\n" +
            " (1008,106,'B','13.05.2019',NULL),\n" +
            " (1009,108,'B','14.05.2019',NULL),\n" +
            " (1010,203,'A','14.05.2019',NULL),\n" +
            " (1011,204,'A','14.05.2019',NULL),\n" +
            " (1012,205,'A','14.05.2019',NULL),\n" +
            " (1013,109,'B','14.05.2019',NULL),\n" +
            " (1014,110,'B','14.05.2019',NULL),\n" +
            " (1015,201,'A','14.05.2019',NULL),\n" +
            " (1016,201,'B','14.05.2019',NULL),\n" +
            " (1017,201,'B','14.05.2019',NULL),\n" +
            " (1018,203,'B','14.05.2019',NULL),\n" +
            " (1019,104,'A','14.05.2019',NULL),\n" +
            " (1020,104,'A','14.01.1900',NULL),\n" +
            " (1021,103,'B','15.05.2019',NULL),\n" +
            " (1022,110,'A','16.05.2019',NULL),\n" +
            " (1023,108,'B','16.05.2019',NULL),\n" +
            " (1024,101,'A','16.05.2019',NULL),\n" +
            " (1025,102,'A','16.05.2019',NULL),\n" +
            " (1026,105,'B','16.05.2019',NULL),\n" +
            " (1027,106,'B','16.05.2019',NULL),\n" +
            " (1028,106,'A','16.05.2019',NULL),\n" +
            " (1029,204,'B','16.05.2019',NULL),\n" +
            " (1030,207,'A','16.05.2019',NULL);";

    public static final String CREATE_RANDOM_WEEK_2_TABLE = "CREATE TABLE IF NOT EXISTS Randomisierung_Woche_2 (\n" +
            "\tPatientenID\tINTEGER,\n" +
            "\tZentrum\tINTEGER,\n" +
            "\tBehandlungsarm\tTEXT,\n" +
            "\tDatum\tTEXT,\n" +
            "\tfield5\tTEXT\n" +
            ");";

    public static final String RANDOM_WEEK_2_INIT_DATA = "INSERT INTO Randomisierung_Woche_2 (PatientenID, Zentrum , Behandlungsarm , Datum, field5) VALUES \n" +
            " (1031,'101','B','02.06.2019',NULL),\n" +
            " (1032,'102','B','02.06.2019',NULL),\n" +
            " (1033,'103','A','02.06.2019',NULL),\n" +
            " (1034,'106','A','03.06.2019',NULL),\n" +
            " (1035,'108','B','03.06.2019',NULL),\n" +
            " (1036,'202','A','03.06.2019',NULL),\n" +
            " (1037,'202','A','03.06.2019',NULL),\n" +
            " (1038,'101','B','03.06.2019',NULL),\n" +
            " (1039,'203','A','03.06.2019',NULL),\n" +
            " (1040,'201','B','03.06.2019',NULL),\n" +
            " (1041,'104','A','03.06.2019',NULL),\n" +
            " (1042,NULL,'B','03.06.2019',NULL),\n" +
            " (1043,'108','B','03.06.2019',NULL),\n" +
            " (1044,'101','A','03.06.2019',NULL),\n" +
            " (1045,'104','B','03.06.2019',NULL),\n" +
            " (1046,'201','A','03.06.2019',NULL),\n" +
            " (1047,'203','B','03.06.2019',NULL),\n" +
            " (1048,'110','A','03.06.2019',NULL),\n" +
            " (1049,'108','A','03.06.2019',NULL),\n" +
            " (1050,'201','B','03.06.2019',NULL),\n" +
            " (1051,'110','B','03.06.2019',NULL),\n" +
            " (1052,'201','A','03.06.2019',NULL),\n" +
            " (1053,'101','A','03.06.2019',NULL),\n" +
            " (1054,'201','B','03.06.2019',NULL);";
}
