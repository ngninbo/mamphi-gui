BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "Zentren" (
	"ZentrumID"	,
	"Land"	,
	"Ort"	,
	"Pruefer"	,
	"Monitor"	
);
CREATE TABLE IF NOT EXISTS "Informed_consent" (
	"PatientenID"	,
	"Zentrum"	,
	"Einwilligung"	,
	"Datum"	,
	"field5"	
);
CREATE TABLE IF NOT EXISTS "Randomisierung_Woche_1" (
	"PatientenID"	,
	"Zentrum"	,
	"Behandlungsarm"	,
	"Datum"	,
	"field5"	
);
CREATE TABLE IF NOT EXISTS "Randomisierung_Woche_2" (
	"PatientenID"	INTEGER,
	"Zentrum"	TEXT,
	"Behandlungsarm"	TEXT,
	"Datum"	TEXT,
	"field5"	TEXT
);
INSERT INTO "Zentren" ("ZentrumID","Land","Ort","Pruefer","Monitor") VALUES ('101','D','Dortmund','Meyer','Wittmann'),
 ('102','D','Essen','Müller','Bruch'),
 ('103','D','München','Moser','Wittmann'),
 ('104','D','Herne','Kwiatkowski','Wittmann'),
 ('105','D','Hannover','Meyer','Lange'),
 ('106','D','Köln','Brettschneider','Bruch'),
 ('107','D','Bremen','Van Gool','Lange'),
 ('108','D','Hamburg','Talmann-Berg','Lange'),
 ('109','D','Stuttgart','Fischer','Bruch'),
 ('110','D','Lepzig','Obermeier','Bruch'),
 ('201','GB','London','McDonald','Gordon'),
 ('202','GB','London','Priest','Thatcher'),
 ('203','GB','Manchester','Down','Gordon'),
 ('204','GB','Brighton','Feldham','Gordon'),
 ('205','GB','Leeds','Harnister','Thatcher'),
 (111,'D','Hamm','Ngnintedem','Dongmo'),
 (206,'GB','Manchester','Ngnintedem','Nana');
INSERT INTO "Informed_consent" ("PatientenID","Zentrum","Einwilligung","Datum","field5") VALUES ('1000','102','nein','10.05.2019',NULL),
 ('1001','201','ja','11.05.2019',NULL),
 ('1002','202','ja','12.05.2019',NULL),
 ('1003','101','ja','13.05.2019',NULL),
 ('1004','102','ja','13.05.2019',NULL),
 ('1005','104','ja','13.05.2019',NULL),
 ('1006','103','ja','13.05.2019',NULL),
 ('1007','105','ja',NULL,NULL),
 ('1008','106','ja','13.05.2019',NULL),
 ('1009','108','ja','14.05.2019',NULL),
 ('1010','203','ja','14.05.2019',NULL),
 ('1011','204','ja','14.05.2019',NULL),
 ('1012','205','ja','14.06.2019',NULL),
 ('1013','109',NULL,'14.05.2019',NULL),
 ('1014','110','ja','14.05.2019',NULL),
 ('1016','201','ja','14.05.2019',NULL),
 ('1017','201','ja','14.05.2019',NULL),
 ('1018','203','ja','14.05.2019',NULL),
 ('1019','104','ja','14.05.2019',NULL),
 ('1020','104','ja','14.01.1900',NULL),
 ('1021','103','ja','15.05.2019',NULL),
 ('1022','110','ja','16.05.2019',NULL),
 ('1023','108','ja','16.05.2019',NULL),
 ('1024','101','ja','16.05.2019',NULL),
 ('1025','102','ja','15.05.2091',NULL),
 ('1026','105','ja','16.05.2019',NULL),
 ('1027','106',NULL,NULL,NULL),
 ('1028','106','ja','16.05.2019',NULL),
 ('1029','204','ja','16.05.2019',NULL),
 ('1030','207','ja','16.05.2019',NULL),
 (1031,111,'ja','2021-04-17',NULL);
INSERT INTO "Randomisierung_Woche_1" ("PatientenID","Zentrum","Behandlungsarm","Datum","field5") VALUES ('1001','201','A','12.05.2019',NULL),
 ('1002','202','B','12.05.2019',NULL),
 ('1003','101','A','12.05.2019',NULL),
 ('1004','102','A','13.05.2019',NULL),
 ('1005','104','B','13.05.2019',NULL),
 ('1006','103','B','13.05.2019',NULL),
 ('1007','105','A','13.05.2019',NULL),
 ('1008','106','B','13.05.2019',NULL),
 ('1009','108','B','14.05.2019',NULL),
 ('1010','203','A','14.05.2019',NULL),
 ('1011','204','A','14.05.2019',NULL),
 ('1012','205','A','14.05.2019',NULL),
 ('1013','109','B','14.05.2019',NULL),
 ('1014','110','B','14.05.2019',NULL),
 ('1015','201','A','14.05.2019',NULL),
 ('1016','201','B','14.05.2019',NULL),
 ('1017','201','B','14.05.2019',NULL),
 ('1018','203','B','14.05.2019',NULL),
 ('1019','104','A','14.05.2019',NULL),
 ('1020','104','A','14.01.1900',NULL),
 ('1021','103','B','15.05.2019',NULL),
 ('1022','110','A','16.05.2019',NULL),
 ('1023','108','B','16.05.2019',NULL),
 ('1024','101','A','16.05.2019',NULL),
 ('1025','102','A','16.05.2019',NULL),
 ('1026','105','B','16.05.2019',NULL),
 ('1027','106','B','16.05.2019',NULL),
 ('1028','106','A','16.05.2019',NULL),
 ('1029','204','B','16.05.2019',NULL),
 ('1030','207','A','16.05.2019',NULL);
INSERT INTO "Randomisierung_Woche_2" ("PatientenID","Zentrum","Behandlungsarm","Datum","field5") VALUES (1031,'101','B','02.06.2019',NULL),
 (1032,'102','B','02.06.2019',NULL),
 (1033,'103','A','02.06.2019',NULL),
 (1034,'106','A','03.06.2019',NULL),
 (1035,'108','B','03.06.2019',NULL),
 (1036,'202','A','03.06.2019',NULL),
 (1037,'202','A','03.06.2019',NULL),
 (1038,'101','B','03.06.2019',NULL),
 (1039,'203','A','03.06.2019',NULL),
 (1040,'201','B','03.06.2019',NULL),
 (1041,'104','A','03.06.2019',NULL),
 (1042,NULL,'B','03.06.2019',NULL),
 (1043,'108','B','03.06.2019',NULL),
 (1044,'101','A','03.06.2019',NULL),
 (1045,'104','B','03.06.2019',NULL),
 (1046,'201','A','03.06.2019',NULL),
 (1047,'203','B','03.06.2019',NULL),
 (1048,'110','A','03.06.2019',NULL),
 (1049,'108','A','03.06.2019',NULL),
 (1050,'201','B','03.06.2019',NULL),
 (1051,'110','B','03.06.2019',NULL),
 (1052,'201','A','03.06.2019',NULL),
 (1053,'101','A','03.06.2019',NULL),
 (1054,'201','B','03.06.2019',NULL),
 (NULL,NULL,NULL,NULL,NULL),
 (NULL,NULL,NULL,NULL,NULL),
 (NULL,NULL,NULL,NULL,NULL),
 (NULL,NULL,NULL,NULL,NULL),
 (NULL,NULL,NULL,NULL,NULL),
 (NULL,NULL,NULL,NULL,NULL);
COMMIT;
