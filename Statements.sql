-- Liste der vorhandenen Zentren
SELECT * FROM Centres;

-- Liste der deutschen Zentren
SELECT * FROM Centres WHERE Country = 'D';

-- Liste der britischen Zentren
SELECT * FROM Centres WHERE Country = 'GB';

-- Nächstmögliche ID für ein britisches Zentrum
SELECT max(CentreID) + 1 as newID
FROM Centres
WHERE Country = 'GB';

-- Nächstmögliche ID für ein deutsches Zentrum
SELECT max(CentreID) + 1 as newID
FROM Centres
WHERE Country = 'DE';

-- Nächstmögliche PatientId
SELECT max(PatientId) + 1 as newPatientID
FROM Informed_consent;

-- Anzahl der Patienten pro Zentren in der ersten Randomisierungswoche 
SELECT r1.Centre, COUNT(r1.Centre) as Anzahl
FROM Randomization_Week_1 r1
GROUP BY r1.Centre;

-- Anzahl der Patienten pro Zentren in der zweiten Randomisierungswoche 
SELECT 	CASE 
			WHEN r2.Centre IS NULL THEN 999
			ELSE r2.Centre
		END AS Centre, 
		COUNT(r2.Centre) as Anzahl
FROM Randomization_Week_2 r2
GROUP BY r2.Centre;

-- Anzahl der Patienten pro Zentren in allen Randomisierungswochen
SELECT za.Centre, SUM(za.ANZAHL) AS TotalNumberOfPatient 
FROM (	SELECT r1.Centre, COUNT(r1.Centre) as Anzahl
		FROM Randomization_Week_1 r1
		GROUP BY r1.Centre
		UNION
		SELECT 	CASE 
					WHEN r2.Centre IS NULL THEN 999
					ELSE r2.Centre
				END AS Centre, 
				COUNT(r2.Centre) as Anzahl
		FROM Randomization_Week_2 r2
		GROUP BY r2.Centre) za
GROUP BY Centre;


-- Anzahl der britischen Patienten pro Zentrum in der ersten Randomisierungswochen
SELECT ra.Centre as Centre, ra.Anzahl as Anzahl, zd.Country
FROM(	SELECT r.Centre, COUNT(r.Centre) as Anzahl
		FROM Randomization_Week_1 r
		GROUP BY r.Centre) ra
LEFT JOIN (	SELECT z.CentreID, z.Country 
			FROM Centres z) zd ON ra.Centre = zd.CentreID
WHERE zd.Country = 'GB';


SELECT r.Centre, count(r.Centre) as Anzahl 
FROM Randomization_Week_1 r
WHERE r.Centre in (SELECT z.CentreID 
					FROM Centres z
					WHERE z.Country = 'GB')
GROUP BY Centre;

/*Liste der britischen Patienten in der ersten Randomisierungswoche.
Das Date wird hierbei in deutschsprachigen ausgegeben */

SELECT r.PatientID, r.Centre, r.TreatmentGroup, strftime('%d.%m.%Y', r.Date) as Date
FROM Randomization_Week_1 r
JOIN (	SELECT CentreID FROM Centres z
		WHERE z.Country = 'GB') zb
on zb.CentreID = r.Centre;

SELECT r.PatientID, r.Centre, r.TreatmentGroup, r.Date
FROM Randomization_Week_1 r
JOIN (	SELECT CentreID FROM Centres z
		WHERE z.Country = 'GB') zb
on zb.CentreID = r.Centre;

-- Anzahl der Patienten pro Zentrum in der ersten Randomisierungswoche
SELECT Centre, count(Centre) as Anzahl
FROM Randomization_Week_1
GROUP BY Centre;

SELECT r1.PatientID, r1.Centre, r1.TreatmentGroup, r1.Date
FROM Randomization_Week_1 r1
UNION
SELECT 	r2.PatientID,
		CASE 
			WHEN r2.Centre IS NULL THEN 999
			ELSE r2.Centre
		END AS Centre, 
		r2.TreatmentGroup, r2.Date
FROM Randomization_Week_2 r2
WHERE PatientId IS NOT NULL;


-- Anzahl der deutsche Patienten pro Zentrum in der ersten Randomisierungswoche
SELECT r.Centre, count(r.Centre) as Anzahl
FROM Randomization_Week_1 r
JOIN (	SELECT z.CentreID FROM Centres z
		WHERE Country = 'D') zd on zd.CentreID = r.Centre
GROUP BY Centre;

-- Liste der Patienten in der ersten Radominisierungswoche
SELECT r.PatientID, r.Centre, r.TreatmentGroup, strftime('%d.%m.%Y', r.Date) as Date 
FROM Randomization_Week_1 r;

SELECT r.PatientID, r.Centre, r.TreatmentGroup, Date
FROM Randomization_Week_1 r;


-- Liste der Consenten
SELECT * FROM Informed_consent;

select patientID, Centre, Consent, Date from Informed_consent;

-- Liste der Patienten bei denen die Einwilligung fehlt
SELECT * FROM Informed_consent
WHERE Consent IS NULL;

-- Liste der Patienten bei denen die Einwilligung unvollständig sind
SELECT 	PatientID, Centre, Consent, Date	
FROM Informed_consent
WHERE Consent IS NULL OR Date IS NULL;

-- Liste der Patienten bei denen die Einwilligung nach der Randomisierung kommt
SELECT * FROM Informed_consent
where substr(Date,7)||substr(Date,4,2)||substr(Date,1,2) > '20190603';

SELECT substr(Date,7)||substr(Date,4,2)||substr(Date,1,2) AS Date 
FROM Informed_consent;

SELECT strftime('$d.$m.$Y', '03.06.2019');


SELECT PatientID, Centre, Consent, Date 
FROM Informed_consent 
WHERE Consent = 'ja';


/*Monitoringplan: Anzeige der Informationen über ZentrumID, Country, Place, Trier, 
Monitor und die Gesamtanzahl an Patienten aus Centres, die entweder in der ersten 
oder zweiten Randomisierungwoche involviert waren. OUTER JOIN aktuell nicht unterstützt!*/
SELECT z.CentreID, z.Country, z.Place, z.Trier, z.Monitor, zg.TotalNumberOfPatient as NP
FROM(SELECT * FROM Centres ORDER BY CentreID) z
JOIN (	SELECT CentreID, TotalNumberOfPatient 
		FROM (SELECT CentreID FROM Centres ORDER BY CentreID)
		LEFT JOIN (	SELECT Centre, SUM(Anzahl) as TotalNumberOfPatient
					FROM(	SELECT r1.Centre, COUNT(r1.Centre) as Anzahl
							FROM Randomization_Week_1 r1 
							GROUP BY r1.Centre
							UNION 	SELECT r2.Centre, COUNT(r2.Centre) as Anzahl
									FROM Randomization_Week_2 r2 
									GROUP BY r2.Centre)
					GROUP BY Centre) 
		ON CentreID = Centre) zg 
ON z.CentreID = zg.CentreID;

/*Monitoringplan: Anzeige der Informationen über ZentrumID, Country, Place, Trier, 
Monitor und die Gesamtanzahl an Patienten aus Centres, die entweder in der ersten 
oder zweiten Randomisierungwoche involviert waren. OUTER JOIN aktuell nicht unterstützt!*/
SELECT z.CentreID, z.Country, z.Place, z.Trier, z.Monitor, zg.TotalNumberOfPatient as TotalNumberOfPatient
FROM(SELECT * FROM Centres ORDER BY CentreID) z
JOIN (	SELECT CentreID, TotalNumberOfPatient 
		FROM (	SELECT ru.Centre, SUM(ru.Anzahl) as TotalNumberOfPatient
				FROM(	SELECT r1.Centre, COUNT(r1.Centre) as Anzahl
						FROM Randomization_Week_1 r1 
						GROUP BY r1.Centre
						UNION 	SELECT r2.Centre, COUNT(r2.Centre) as Anzahl
								FROM Randomization_Week_2 r2 
								GROUP BY r2.Centre) ru
				GROUP BY ru.Centre) zs
		JOIN (	SELECT CentreID FROM Centres  
				ORDER BY CentreID) zi
		ON zi.CentreID = zs.Centre) zg 
ON z.CentreID = zg.CentreID
WHERE z.CentreID in(SELECT CentreID FROM (	SELECT CentreID, TotalNumberOfPatient
												FROM (	SELECT ru2.Centre, SUM(ru2.Anzahl) as TotalNumberOfPatient
														FROM( 	SELECT r12.Centre, COUNT(r12.Centre) as Anzahl
																FROM Randomization_Week_1 r12 
																GROUP BY r12.Centre
																UNION 	SELECT r22.Centre, COUNT(r22.Centre) as Anzahl
																		FROM Randomization_Week_2 r22
																		GROUP BY r22.Centre) ru2
														GROUP BY ru2.Centre) zs2
												JOIN (	SELECT CentreID FROM Centres ORDER BY CentreID) zi2
												ON zi2.CentreID = zs2.Centre));


												

SELECT z.CentreID, z.Country, z.Place, z.Trier, z.Monitor, zg.TotalNumberOfPatient 
FROM Centres z
JOIN (	SELECT za.Centre, SUM(za.ANZAHL) AS TotalNumberOfPatient 
		FROM (	SELECT r1.Centre, COUNT(r1.Centre) as Anzahl
				FROM Randomization_Week_1 r1
				GROUP BY r1.Centre
				UNION
				SELECT 	CASE 
							WHEN r2.Centre IS NULL THEN 999
							ELSE r2.Centre
						END AS Centre, 
						COUNT(r2.Centre) as Anzahl
				FROM Randomization_Week_2 r2
				GROUP BY r2.Centre) za
		GROUP BY Centre) zg 
ON zg.Centre = z.CentreID;


-- Anzahl der Patienten pro Zentrum in der ersten Randomisierungswoche
SELECT Centre, count(Centre) as Anzahl
FROM Randomization_Week_1
GROUP BY Centre;

-- Anzahl der deutsche Patienten pro Zentrum in der ersten Randomisierungswoche
SELECT r.Centre, count(r.Centre) as Anzahl
FROM Randomization_Week_1 r
JOIN (	SELECT z.CentreId FROM Centres z
		WHERE Country = 'D') zd on zd.CentreId = r.Centre
GROUP BY Centre;

-- Liste der Patienten in der ersten Radominisierungswoche
SELECT r.PatientId, r.Centre, r.TreatmentGroup, strftime('%d.%m.%Y', r.Date) as Date 
FROM Randomization_Week_1 r;

SELECT r.PatientId, r.Centre, r.TreatmentGroup, Date 
FROM Randomization_Week_1 r;


-- Liste der Einwilligungen
SELECT * FROM Informed_consent;

-- Liste der Patienten bei denen die Einwilligung fehlt
SELECT * FROM Informed_consent
WHERE Consent IS 'nan';

-- Liste der Patienten bei denen die Einwilligung unvollständig sind
SELECT * FROM Informed_consent
WHERE Consent IS 'nan' OR Date IS 'NaT';

-- Liste der Patienten bei denen die Einwilligung nach der Randomisierung kommt
SELECT * FROM Informed_consent
WHERE Date > '2019.06.03';


/*Monitoringplan: Anzeige der Informationen über Zentrum_ID, Country, Place, Trier, 
Monitor und die Gesamtanzahl an Patienten aus Zentren, die entweder in der ersten 
oder zweiten Randomisierungwoche involviert waren. OUTER JOIN aktuell nicht unterstützt!*/
SELECT z.CentreId, z.Country, z.Place, z.Trier, z.Monitor, zg.TotalNumberOfPatient as NP
FROM(SELECT * FROM Centres ORDER BY CentreId) z
JOIN (	SELECT CentreId, TotalNumberOfPatient 
		FROM (SELECT CentreId FROM Centres ORDER BY CentreId)
		LEFT JOIN (	SELECT Centre, SUM(Anzahl) as TotalNumberOfPatient
					FROM(	SELECT r1.Centre, COUNT(r1.Centre) as Anzahl
							FROM Randomization_Week_1 r1 
							GROUP BY r1.Centre
							UNION 	SELECT r2.Centre, COUNT(r2.Centre) as Anzahl
									FROM Randomization_Week_2 r2 
									GROUP BY r2.Centre)
					GROUP BY Centre) 
		ON CentreId = Centre) zg 
ON z.CentreId = zg.CentreId;

/*Monitoringplan: Anzeige der Informationen über Zentrum_ID, Country, Place, Trier, 
Monitor und die Gesamtanzahl an Patienten aus Zentren, die entweder in der ersten 
oder zweiten Randomisierungwoche involviert waren. OUTER JOIN aktuell nicht unterstützt!*/
SELECT z.CentreId, z.Country, z.Place, z.Trier, z.Monitor, zg.TotalNumberOfPatient as NP
FROM(SELECT * FROM Centres ORDER BY CentreId) z
JOIN (	SELECT CentreId, TotalNumberOfPatient 
		FROM (	SELECT ru.Centre, SUM(ru.numberOfPatient) as TotalNumberOfPatient
				FROM(	SELECT r1.Centre, COUNT(r1.Centre) as numberOfPatient
						FROM Randomization_Week_1 r1 
						GROUP BY r1.Centre
						UNION 	SELECT r2.Centre, COUNT(r2.Centre) as numberOfPatient
								FROM Randomization_Week_2 r2 
								GROUP BY r2.Centre) ru
				GROUP BY ru.Centre) zs
		JOIN (	SELECT CentreId FROM Centres  
				ORDER BY CentreId) zi
		ON zi.CentreId = zs.Centre) zg 
ON z.CentreId = zg.CentreId;




