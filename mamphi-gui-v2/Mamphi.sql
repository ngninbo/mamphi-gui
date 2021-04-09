create database mamphi_db;
use mamphi_db;

create table Zentren
(
	Zentrum_Id smallint,
    Land varchar(30),
    Ort varchar(55),
    Prüfer varchar(55),
    Monitor varchar(55)
);

insert into Zentren (Zentrum_Id, Land, Ort, Prüfer, Monitor) values (101, 'D', 'Dortmund', 'Meyer', 'Wittmann');
insert into Zentren(Zentrum_Id,Land,Ort,Prüfer,Monitor)values (102,'D','Essen','Müller','Bruch');
insert into Zentren(Zentrum_Id,Land,Ort,Prüfer,Monitor)values (103,'D','München','Moser','Wittmann');
insert into Zentren(Zentrum_Id,Land,Ort,Prüfer,Monitor)values (104,'D','Herne','Kwiatkowski','Wittmann');
insert into Zentren(Zentrum_Id,Land,Ort,Prüfer,Monitor)values (105,'D','Hannover','Meyer','Lange');
insert into Zentren(Zentrum_Id,Land,Ort,Prüfer,Monitor)values (106,'D','Köln','BrettschneZentrum_Ider','Bruch');
insert into Zentren(Zentrum_Id,Land,Ort,Prüfer,Monitor)values (107,'D','Bremen','VanGool','Lange');
insert into Zentren(Zentrum_Id,Land,Ort,Prüfer,Monitor)values (108,'D','Hamburg','Talmann-Berg','Lange');
insert into Zentren(Zentrum_Id,Land,Ort,Prüfer,Monitor)values (109,'D','Stuttgart','Fischer','Bruch');
insert into Zentren(Zentrum_Id,Land,Ort,Prüfer,Monitor)values (110,'D','Lepzig','Obermeier','Bruch');
insert into Zentren(Zentrum_Id,Land,Ort,Prüfer,Monitor)values (201,'GB','London','McDonald','Gordon');
insert into Zentren(Zentrum_Id,Land,Ort,Prüfer,Monitor)values (202,'GB','London','Priest','Thatcher');
insert into Zentren(Zentrum_Id,Land,Ort,Prüfer,Monitor)values (203,'GB','Manchester','Down','Gordon');
insert into Zentren(Zentrum_Id,Land,Ort,Prüfer,Monitor)values (204,'GB','Brighton','Feldham','Gordon');
insert into Zentren(Zentrum_Id,Land,Ort,Prüfer,Monitor)values (205,'GB','Leeds','Harnister','Thatcher');

create table Informed_consent 
(	
	Patient_Id smallint,
    Zentrum smallint,
    Einwilligung varchar(30),
    Datum varchar(55)
);

insert into Informed_consent(Patient_Id,Zentrum,Einwilligung,Datum)values(1000,102,'nein','10.05.2019');
insert into Informed_consent(Patient_Id,Zentrum,Einwilligung,Datum)values(1001,201,'ja','11.05.2019');
insert into Informed_consent(Patient_Id,Zentrum,Einwilligung,Datum)values(1002,202,'ja','12.05.2019');
insert into Informed_consent(Patient_Id,Zentrum,Einwilligung,Datum)values(1003,101,'ja','13.05.2019');
insert into Informed_consent(Patient_Id,Zentrum,Einwilligung,Datum)values(1004,102,'ja','13.05.2019');
insert into Informed_consent(Patient_Id,Zentrum,Einwilligung,Datum)values(1005,104,'ja','13.05.2019');
insert into Informed_consent(Patient_Id,Zentrum,Einwilligung,Datum)values(1006,103,'ja','13.05.2019');
insert into Informed_consent(Patient_Id,Zentrum,Einwilligung,Datum)values(1007,105,'ja','00.01.1900');
insert into Informed_consent(Patient_Id,Zentrum,Einwilligung,Datum)values(1008,106,'ja','13.05.2019');
insert into Informed_consent(Patient_Id,Zentrum,Einwilligung,Datum)values(1009,108,'ja','14.05.2019');
insert into Informed_consent(Patient_Id,Zentrum,Einwilligung,Datum)values(1010,203,'ja','14.05.2019');
insert into Informed_consent(Patient_Id,Zentrum,Einwilligung,Datum)values(1011,204,'ja','14.05.2019');
insert into Informed_consent(Patient_Id,Zentrum,Einwilligung,Datum)values(1012,205,'ja','14.06.2019');
insert into Informed_consent(Patient_Id,Zentrum,Einwilligung,Datum)values(1013,109,"",'14.05.2019');
insert into Informed_consent(Patient_Id,Zentrum,Einwilligung,Datum)values(1014,110,'ja','14.05.2019');
insert into Informed_consent(Patient_Id,Zentrum,Einwilligung,Datum)values(1016,201,'ja','14.05.2019');
insert into Informed_consent(Patient_Id,Zentrum,Einwilligung,Datum)values(1017,201,'ja','14.05.2019');
insert into Informed_consent(Patient_Id,Zentrum,Einwilligung,Datum)values(1018,203,'ja','14.05.2019');
insert into Informed_consent(Patient_Id,Zentrum,Einwilligung,Datum)values(1019,104,'ja','14.05.2019');
insert into Informed_consent(Patient_Id,Zentrum,Einwilligung,Datum)values(1020,104,'ja','14.01.1900');
insert into Informed_consent(Patient_Id,Zentrum,Einwilligung,Datum)values(1021,103,'ja','15.05.2019');
insert into Informed_consent(Patient_Id,Zentrum,Einwilligung,Datum)values(1022,110,'ja','16.05.2019');
insert into Informed_consent(Patient_Id,Zentrum,Einwilligung,Datum)values(1023,108,'ja','16.05.2019');
insert into Informed_consent(Patient_Id,Zentrum,Einwilligung,Datum)values(1024,101,'ja','16.05.2019');
insert into Informed_consent(Patient_Id,Zentrum,Einwilligung,Datum)values(1025,102,'ja','15.05.2091');
insert into Informed_consent(Patient_Id,Zentrum,Einwilligung,Datum)values(1026,105,'ja','16.05.2019');
insert into Informed_consent(Patient_Id,Zentrum,Einwilligung,Datum)values(1027,106,"",'00.01.1900');
insert into Informed_consent(Patient_Id,Zentrum,Einwilligung,Datum)values(1028,106,'ja','16.05.2019');
insert into Informed_consent(Patient_Id,Zentrum,Einwilligung,Datum)values(1029,204,'ja','16.05.2019');
insert into Informed_consent(Patient_Id,Zentrum,Einwilligung,Datum)values(1030,207,'ja','16.05.2019');

/*
select * from Informed_consent;
select * from Informed_consent where Einwilligung = "ja";
select * from Informed_consent where Einwilligung = "nein";
select * from Informed_consent where Einwilligung = "";

select Zentrum_Id, Land, Ort, Prüfer, Monitor from Zentren;
select * from Zentren where Land = "D";
*/

create table Random_Woche_1
(
	Patient_Id smallint,
    Zentrum smallint,
    Behandlungsarm varchar(55),
    Datum varchar(55)
);

insert into Random_Woche_1(Patient_Id,Zentrum,Behandlungsarm,Datum)values(1001,201,'A','12.05.2019');
insert into Random_Woche_1(Patient_Id,Zentrum,Behandlungsarm,Datum)values(1002,202,'B','12.05.2019');
insert into Random_Woche_1(Patient_Id,Zentrum,Behandlungsarm,Datum)values(1003,101,'A','12.05.2019');
insert into Random_Woche_1(Patient_Id,Zentrum,Behandlungsarm,Datum)values(1004,102,'A','13.05.2019');
insert into Random_Woche_1(Patient_Id,Zentrum,Behandlungsarm,Datum)values(1005,104,'B','13.05.2019');
insert into Random_Woche_1(Patient_Id,Zentrum,Behandlungsarm,Datum)values(1006,103,'B','13.05.2019');
insert into Random_Woche_1(Patient_Id,Zentrum,Behandlungsarm,Datum)values(1007,105,'A','13.05.2019');
insert into Random_Woche_1(Patient_Id,Zentrum,Behandlungsarm,Datum)values(1008,106,'B','13.05.2019');
insert into Random_Woche_1(Patient_Id,Zentrum,Behandlungsarm,Datum)values(1009,108,'B','14.05.2019');
insert into Random_Woche_1(Patient_Id,Zentrum,Behandlungsarm,Datum)values(1010,203,'A','14.05.2019');
insert into Random_Woche_1(Patient_Id,Zentrum,Behandlungsarm,Datum)values(1011,204,'A','14.05.2019');
insert into Random_Woche_1(Patient_Id,Zentrum,Behandlungsarm,Datum)values(1012,205,'A','14.05.2019');
insert into Random_Woche_1(Patient_Id,Zentrum,Behandlungsarm,Datum)values(1013,109,'B','14.05.2019');
insert into Random_Woche_1(Patient_Id,Zentrum,Behandlungsarm,Datum)values(1014,110,'B','14.05.2019');
insert into Random_Woche_1(Patient_Id,Zentrum,Behandlungsarm,Datum)values(1015,201,'A','14.05.2019');
insert into Random_Woche_1(Patient_Id,Zentrum,Behandlungsarm,Datum)values(1016,201,'B','14.05.2019');
insert into Random_Woche_1(Patient_Id,Zentrum,Behandlungsarm,Datum)values(1017,201,'B','14.05.2019');
insert into Random_Woche_1(Patient_Id,Zentrum,Behandlungsarm,Datum)values(1018,203,'B','14.05.2019');
insert into Random_Woche_1(Patient_Id,Zentrum,Behandlungsarm,Datum)values(1019,104,'A','14.05.2019');
insert into Random_Woche_1(Patient_Id,Zentrum,Behandlungsarm,Datum)values(1020,104,'A','14.01.1900');
insert into Random_Woche_1(Patient_Id,Zentrum,Behandlungsarm,Datum)values(1021,103,'B','15.05.2019');
insert into Random_Woche_1(Patient_Id,Zentrum,Behandlungsarm,Datum)values(1022,110,'A','16.05.2019');
insert into Random_Woche_1(Patient_Id,Zentrum,Behandlungsarm,Datum)values(1023,108,'B','16.05.2019');
insert into Random_Woche_1(Patient_Id,Zentrum,Behandlungsarm,Datum)values(1024,101,'A','16.05.2019');
insert into Random_Woche_1(Patient_Id,Zentrum,Behandlungsarm,Datum)values(1025,102,'A','16.05.2019');
insert into Random_Woche_1(Patient_Id,Zentrum,Behandlungsarm,Datum)values(1026,105,'B','16.05.2019');
insert into Random_Woche_1(Patient_Id,Zentrum,Behandlungsarm,Datum)values(1027,106,'B','16.05.2019');
insert into Random_Woche_1(Patient_Id,Zentrum,Behandlungsarm,Datum)values(1028,106,'A','16.05.2019');
insert into Random_Woche_1(Patient_Id,Zentrum,Behandlungsarm,Datum)values(1029,204,'B','16.05.2019');
insert into Random_Woche_1(Patient_Id,Zentrum,Behandlungsarm,Datum)values(1030,207,'A','16.05.2019');

-- select * from Random_Woche_1
-- select * from Random_Woche_1 where Behandlungsarm = "A";

create table Random_Woche_2
(
	Patient_Id smallint,
    Zentrum smallint,
    Behandlungsarm varchar(55),
    Datum varchar(55)
);

insert into Random_Woche_2(Patient_Id,Zentrum,Behandlungsarm,Datum)values(1031,101,'B','02.06.2019');
insert into Random_Woche_2(Patient_Id,Zentrum,Behandlungsarm,Datum)values(1032,102,'B','02.06.2019');
insert into Random_Woche_2(Patient_Id,Zentrum,Behandlungsarm,Datum)values(1033,103,'A','02.06.2019');
insert into Random_Woche_2(Patient_Id,Zentrum,Behandlungsarm,Datum)values(1034,106,'A','03.06.2019');
insert into Random_Woche_2(Patient_Id,Zentrum,Behandlungsarm,Datum)values(1035,108,'B','03.06.2019');
insert into Random_Woche_2(Patient_Id,Zentrum,Behandlungsarm,Datum)values(1036,202,'A','03.06.2019');
insert into Random_Woche_2(Patient_Id,Zentrum,Behandlungsarm,Datum)values(1037,202,'A','03.06.2019');
insert into Random_Woche_2(Patient_Id,Zentrum,Behandlungsarm,Datum)values(1038,101,'B','03.06.2019');
insert into Random_Woche_2(Patient_Id,Zentrum,Behandlungsarm,Datum)values(1039,203,'A','03.06.2019');
insert into Random_Woche_2(Patient_Id,Zentrum,Behandlungsarm,Datum)values(1040,201,'B','03.06.2019');
insert into Random_Woche_2(Patient_Id,Zentrum,Behandlungsarm,Datum)values(1041,104,'A','03.06.2019');
insert into Random_Woche_2(Patient_Id,Zentrum,Behandlungsarm,Datum)values(1042,-999,'B','03.06.2019');
insert into Random_Woche_2(Patient_Id,Zentrum,Behandlungsarm,Datum)values(1043,108,'B','03.06.2019');
insert into Random_Woche_2(Patient_Id,Zentrum,Behandlungsarm,Datum)values(1044,101,'A','03.06.2019');
insert into Random_Woche_2(Patient_Id,Zentrum,Behandlungsarm,Datum)values(1045,104,'B','03.06.2019');
insert into Random_Woche_2(Patient_Id,Zentrum,Behandlungsarm,Datum)values(1046,201,'A','03.06.2019');
insert into Random_Woche_2(Patient_Id,Zentrum,Behandlungsarm,Datum)values(1047,203,'B','03.06.2019');
insert into Random_Woche_2(Patient_Id,Zentrum,Behandlungsarm,Datum)values(1048,110,'A','03.06.2019');
insert into Random_Woche_2(Patient_Id,Zentrum,Behandlungsarm,Datum)values(1049,108,'A','03.06.2019');
insert into Random_Woche_2(Patient_Id,Zentrum,Behandlungsarm,Datum)values(1050,201,'B','03.06.2019');
insert into Random_Woche_2(Patient_Id,Zentrum,Behandlungsarm,Datum)values(1051,110,'B','03.06.2019');
insert into Random_Woche_2(Patient_Id,Zentrum,Behandlungsarm,Datum)values(1052,201,'A','03.06.2019');
insert into Random_Woche_2(Patient_Id,Zentrum,Behandlungsarm,Datum)values(1053,101,'A','03.06.2019');
insert into Random_Woche_2(Patient_Id,Zentrum,Behandlungsarm,Datum)values(1054,201,'B','03.06.2019');

-- select * from Random_Woche_2 where Behandlungsarm = 'A';
/*
drop table Informed_consent
drop table Random_Woche_2;
drop database mamphi_db;
*/
