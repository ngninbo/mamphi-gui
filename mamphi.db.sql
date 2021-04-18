BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "Zentren" (
	"Zentrum_Id"	smallint,
	"Land"	varchar(255),
	"Ort"	varchar(255),
	"Pruefer"	varchar(255),
	"Monitor"	varchar(255)
);
CREATE TABLE IF NOT EXISTS "Informed_consent" (
	"Patient_Id"	smallint,
	"Zentrum"	smallint,
	"Einwilligung"	varchar(255),
	"Datum"	date
);
CREATE TABLE IF NOT EXISTS "Random_Woche_1" (
	"Patient_Id"	smallint,
	"Zentrum"	smallint,
	"Behandlungsarm"	varchar(255),
	"Datum"	date
);
CREATE TABLE IF NOT EXISTS "Random_Woche_2" (
	"Patient_Id"	smallint,
	"Zentrum"	smallint,
	"Behandlungsarm"	varchar(255),
	"Datum"	date
);
INSERT INTO "Zentren" ("Zentrum_Id","Land","Ort","Pruefer","Monitor") VALUES (101,'D','Dortmund','Meyer','Wittmann'),
 (102,'D','Essen','Müller','Bruch'),
 (103,'D','München','Moser','Wittmann'),
 (104,'D','Herne','Kwiatkowski','Wittmann'),
 (105,'D','Hannover','Meyer','Lange'),
 (106,'D','Köln','Brettschneider','Bruch'),
 (107,'D','Bremen','Van Gool','Lange'),
 (108,'D','Hamburg','Talmann-Berg','Lange'),
 (109,'D','Stuttgart','Fischer','Bruch'),
 (110,'D','Lepzig','Obermeier','Bruch'),
 (201,'GB','London','McDonald','Gordon'),
 (202,'GB','London','Priest','Thatcher'),
 (203,'GB','Manchester','Down','Gordon'),
 (204,'GB','Brighton','Feldham','Gordon'),
 (205,'GB','Leeds','Harnister','Thatcher'),
 (206,'GB','London','Dongmo','Ngnintedem'),
 (111,'D','Münster','Nana','Djamen'),
 (207,'GB','Arsenal','Abelard','Nsangou'),
 (208,'GB','Hamm','Sonia','Ives'),
 (112,'D','Hamburg','Ngnintedem','Michel'),
 (114,'D','Ahlen','Steve','Allan'),
 (209,'GB','Manchester','Louis','Morgan'),
 (115,'D','Berlin','Demanou','Belgrace'),
 (116,'D','Giessen','Dongmo','Wilfred'),
 (210,'GB','Chelsea','Merveille','Evabelle'),
 (211,'GB','Standford','Motio','Lidi'),
 (212,'GB','London','Fred','Lahm'),
 (117,'D','Lünen','Biermann','Hanna'),
 (118,'D','Herne','Lostor','Alfred'),
 (119,'D','Dresden','Rostov','Henric'),
 (120,'D','Ulm','Nesterov','Vanick'),
 (121,'D','Köln','Vaduis','Alain'),
 (122,'D','Demo','Demo','Demo'),
 (213,'GB','Admin','Admin','Admin');
INSERT INTO "Informed_consent" ("Patient_Id","Zentrum","Einwilligung","Datum") VALUES (1000,102,'nein','2019-05-10 00:00:00'),
 (1001,201,'ja','2019-05-11 00:00:00'),
 (1002,202,'ja','2019-05-12 00:00:00'),
 (1003,101,'ja','2019-05-13 00:00:00'),
 (1004,102,'ja','2019-05-13 00:00:00'),
 (1005,104,'ja','2019-05-13 00:00:00'),
 (1006,103,'ja','2019-05-13 00:00:00'),
 (1007,105,'ja','NaT'),
 (1008,106,'ja','2019-05-13 00:00:00'),
 (1009,108,'ja','2019-05-14 00:00:00'),
 (1010,203,'ja','2019-05-14 00:00:00'),
 (1011,204,'ja','2019-05-14 00:00:00'),
 (1012,205,'ja','2019-06-14 00:00:00'),
 (1013,109,'nan','2019-05-14 00:00:00'),
 (1014,110,'ja','2019-05-14 00:00:00'),
 (1016,201,'ja','2019-05-14 00:00:00'),
 (1017,201,'ja','2019-05-14 00:00:00'),
 (1018,203,'ja','2019-05-14 00:00:00'),
 (1019,104,'ja','2019-05-14 00:00:00'),
 (1020,104,'ja','1900-01-14 00:00:00'),
 (1021,103,'ja','2019-05-15 00:00:00'),
 (1022,110,'ja','2019-05-16 00:00:00'),
 (1023,108,'ja','2019-05-16 00:00:00'),
 (1024,101,'ja','2019-05-16 00:00:00'),
 (1025,102,'ja','2091-05-15 00:00:00'),
 (1026,105,'ja','2019-05-16 00:00:00'),
 (1027,106,'nan','NaT'),
 (1028,106,'ja','2019-05-16 00:00:00'),
 (1029,204,'ja','2019-05-16 00:00:00'),
 (1030,207,'ja','2019-05-16 00:00:00'),
 (1031,101,'ja','2020-07-07'),
 (1032,102,'ja','2019-05-14'),
 (1033,103,'nein','2019-04-08'),
 (1034,105,'nein','2020-08-03'),
 (1035,121,'nan','NaT'),
 (1036,104,'nan','2019-05-24');
INSERT INTO "Random_Woche_1" ("Patient_Id","Zentrum","Behandlungsarm","Datum") VALUES (1001,201,'A','2019-05-12 00:00:00'),
 (1002,202,'B','2019-05-12 00:00:00'),
 (1003,101,'A','2019-05-12 00:00:00'),
 (1004,102,'A','2019-05-13 00:00:00'),
 (1005,104,'B','2019-05-13 00:00:00'),
 (1006,103,'B','2019-05-13 00:00:00'),
 (1007,105,'A','2019-05-13 00:00:00'),
 (1008,106,'B','2019-05-13 00:00:00'),
 (1009,108,'B','2019-05-14 00:00:00'),
 (1010,203,'A','2019-05-14 00:00:00'),
 (1011,204,'A','2019-05-14 00:00:00'),
 (1012,205,'A','2019-05-14 00:00:00'),
 (1013,109,'B','2019-05-14 00:00:00'),
 (1014,110,'B','2019-05-14 00:00:00'),
 (1015,201,'A','2019-05-14 00:00:00'),
 (1016,201,'B','2019-05-14 00:00:00'),
 (1017,201,'B','2019-05-14 00:00:00'),
 (1018,203,'B','2019-05-14 00:00:00'),
 (1019,104,'A','2019-05-14 00:00:00'),
 (1020,104,'A','1900-01-14 00:00:00'),
 (1021,103,'B','2019-05-15 00:00:00'),
 (1022,110,'A','2019-05-16 00:00:00'),
 (1023,108,'B','2019-05-16 00:00:00'),
 (1024,101,'A','2019-05-16 00:00:00'),
 (1025,102,'A','2019-05-16 00:00:00'),
 (1026,105,'B','2019-05-16 00:00:00'),
 (1027,106,'B','2019-05-16 00:00:00'),
 (1028,106,'A','2019-05-16 00:00:00'),
 (1029,204,'B','2019-05-16 00:00:00'),
 (1030,207,'A','2019-05-16 00:00:00');
INSERT INTO "Random_Woche_2" ("Patient_Id","Zentrum","Behandlungsarm","Datum") VALUES (1031,101,'B','2019-06-02 00:00:00'),
 (1032,102,'B','2019-06-02 00:00:00'),
 (1033,103,'A','2019-06-02 00:00:00'),
 (1034,106,'A','2019-06-03 00:00:00'),
 (1035,108,'B','2019-06-03 00:00:00'),
 (1036,202,'A','2019-06-03 00:00:00'),
 (1037,202,'A','2019-06-03 00:00:00'),
 (1038,101,'B','2019-06-03 00:00:00'),
 (1039,203,'A','2019-06-03 00:00:00'),
 (1040,201,'B','2019-06-03 00:00:00'),
 (1041,104,'A','2019-06-03 00:00:00'),
 (1043,108,'B','2019-06-03 00:00:00'),
 (1044,101,'A','2019-06-03 00:00:00'),
 (1045,104,'B','2019-06-03 00:00:00'),
 (1046,201,'A','2019-06-03 00:00:00'),
 (1047,203,'B','2019-06-03 00:00:00'),
 (1048,110,'A','2019-06-03 00:00:00'),
 (1049,108,'A','2019-06-03 00:00:00'),
 (1050,201,'B','2019-06-03 00:00:00'),
 (1051,110,'B','2019-06-03 00:00:00'),
 (1052,201,'A','2019-06-03 00:00:00'),
 (1053,101,'A','2019-06-03 00:00:00'),
 (1054,201,'B','2019-06-03 00:00:00');
COMMIT;
