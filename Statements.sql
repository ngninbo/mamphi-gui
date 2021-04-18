-- Liste der vorhandenen Zentren
SELECT * FROM Zentren;

-- Liste der deutschen Zentren
SELECT * FROM Zentren WHERE Land = 'D';

-- Liste der britischen Zentren
SELECT * FROM Zentren WHERE Land = 'GB';

-- Nächstmögliche ID für ein britisches Zentrum
SELECT max(ZentrumID) + 1 as newID
FROM Zentren
WHERE Land = 'GB'

-- Nächstmögliche ID für ein deutsches Zentrum
SELECT max(ZentrumID) + 1 as newID
FROM Zentren
WHERE Land = 'D'

-- Nächstmögliche PatientenID
SELECT max(PatientenID) + 1 as newPatientID
FROM Informed_consent;

-- Anzahl der Patienten pro Zentren in der ersten Randomisierungswoche 
SELECT r1.Zentrum, COUNT(r1.Zentrum) as Anzahl
FROM Randomisierung_Woche_1 r1
GROUP BY r1.Zentrum;

-- Anzahl der Patienten pro Zentren in der zweiten Randomisierungswoche 
SELECT 	CASE 
			WHEN r2.Zentrum IS NULL THEN 999
			ELSE r2.Zentrum
		END AS Zentrum, 
		COUNT(r2.Zentrum) as Anzahl
FROM Randomisierung_Woche_2 r2
GROUP BY r2.Zentrum;

-- Anzahl der Patienten pro Zentren in allen Randomisierungswochen
SELECT za.Zentrum, SUM(za.ANZAHL) AS Gesamtanzahl 
FROM (	SELECT r1.Zentrum, COUNT(r1.Zentrum) as Anzahl
		FROM Randomisierung_Woche_1 r1
		GROUP BY r1.Zentrum
		UNION
		SELECT 	CASE 
					WHEN r2.Zentrum IS NULL THEN 999
					ELSE r2.Zentrum
				END AS Zentrum, 
				COUNT(r2.Zentrum) as Anzahl
		FROM Randomisierung_Woche_2 r2
		GROUP BY r2.Zentrum) za
GROUP BY Zentrum;


-- Anzahl der britischen Patienten pro Zentrum in der ersten Randomisierungswochen
SELECT ra.Zentrum as Zentrum, ra.Anzahl as Anzahl, zd.Land
FROM(	SELECT r.Zentrum, COUNT(r.Zentrum) as Anzahl
		FROM Randomisierung_Woche_1 r
		GROUP BY r.Zentrum) ra
LEFT JOIN (	SELECT z.ZentrumID, z.Land 
			FROM Zentren z) zd ON ra.Zentrum = zd.ZentrumID
WHERE zd.Land = 'GB';


SELECT r.Zentrum, count(r.Zentrum) as Anzahl 
FROM Randomisierung_Woche_1 r
WHERE r.Zentrum in (SELECT z.ZentrumID 
					FROM Zentren z
					WHERE z.Land = 'GB')
GROUP BY Zentrum;

/*Liste der britischen Patienten in der ersten Randomisierungswoche.
Das Datum wird hierbei in deutschsprachigen ausgegeben */

SELECT r.PatientenID, r.Zentrum, r.Behandlungsarm, strftime('%d.%m.%Y', r.Datum) as Datum
FROM Randomisierung_Woche_1 r
JOIN (	SELECT ZentrumID FROM Zentren z
		WHERE z.Land = 'GB') zb
on zb.ZentrumID = r.Zentrum;

SELECT r.PatientenID, r.Zentrum, r.Behandlungsarm, r.Datum
FROM Randomisierung_Woche_1 r
JOIN (	SELECT ZentrumID FROM Zentren z
		WHERE z.Land = 'GB') zb
on zb.ZentrumID = r.Zentrum;

-- Anzahl der Patienten pro Zentrum in der ersten Randomisierungswoche
SELECT Zentrum, count(Zentrum) as Anzahl
FROM Randomisierung_Woche_1
GROUP BY Zentrum;



SELECT r1.PatientenID, r1.Zentrum, r1.Behandlungsarm, r1.Datum
FROM Randomisierung_Woche_1 r1
UNION
SELECT 	r2.PatientenID, 
		CASE 
			WHEN r2.Zentrum IS NULL THEN 999
			ELSE r2.Zentrum
		END AS Zentrum, 
		r2.Behandlungsarm, r2.Datum
FROM Randomisierung_Woche_2 r2
WHERE PatientenID IS NOT NULL;


-- Anzahl der deutsche Patienten pro Zentrum in der ersten Randomisierungswoche
SELECT r.Zentrum, count(r.Zentrum) as Anzahl
FROM Randomisierung_Woche_1 r
JOIN (	SELECT z.ZentrumID FROM Zentren z
		WHERE Land = 'D') zd on zd.ZentrumID = r.Zentrum
GROUP BY Zentrum;

-- Liste der Patienten in der ersten Radominisierungswoche
SELECT r.PatientenID, r.Zentrum, r.Behandlungsarm, strftime('%d.%m.%Y', r.Datum) as Datum 
FROM Randomisierung_Woche_1 r;

SELECT r.PatientenID, r.Zentrum, r.Behandlungsarm, Datum
FROM Randomisierung_Woche_1 r;


-- Liste der Einwilligungen
SELECT * FROM Informed_consent;

select patientenID, Zentrum, Einwilligung, Datum from Informed_consent;

-- Liste der Patienten bei denen die Einwilligung fehlt
SELECT * FROM Informed_consent
WHERE Einwilligung IS NULL

-- Liste der Patienten bei denen die Einwilligung unvollständig sind
SELECT 	PatientenID, Zentrum, Einwilligung, Datum	
FROM Informed_consent
WHERE Einwilligung IS NULL OR Datum IS NULL;

-- Liste der Patienten bei denen die Einwilligung nach der Randomisierung kommt
SELECT * FROM Informed_consent
where substr(Datum,7)||substr(Datum,4,2)||substr(Datum,1,2) > '20190603';

SELECT substr(Datum,7)||substr(Datum,4,2)||substr(Datum,1,2) AS Datum 
FROM Informed_consent;

SELECT strftime('$d.$m.$Y', '03.06.2019');


SELECT patientenID, Zentrum, Einwilligung, Datum 
FROM Informed_consent 
WHERE Einwilligung = 'ja'


/*Monitoringplan: Anzeige der Informationen über ZentrumID, Land, Ort, Pruefer, 
Monitor und die Gesamtanzahl an Patienten aus Zentren, die entweder in der ersten 
oder zweiten Randomisierungwoche involviert waren. OUTER JOIN aktuell nicht unterstützt!*/
SELECT z.ZentrumID, z.Land, z.Ort, z.Pruefer, z.Monitor, zg.Gesamtanzahl as NP
FROM(SELECT * FROM Zentren ORDER BY ZentrumID ASC) z
JOIN (	SELECT ZentrumID, Gesamtanzahl 
		FROM (SELECT ZentrumID FROM Zentren ORDER BY ZentrumID ASC)
		LEFT JOIN (	SELECT Zentrum, SUM(Anzahl) as Gesamtanzahl
					FROM(	SELECT r1.Zentrum, COUNT(r1.Zentrum) as Anzahl
							FROM Randomisierung_Woche_1 r1 
							GROUP BY r1.Zentrum
							UNION 	SELECT r2.Zentrum, COUNT(r2.Zentrum) as Anzahl
									FROM Randomisierung_Woche_2 r2 
									GROUP BY r2.Zentrum)
					GROUP BY Zentrum) 
		ON ZentrumID = Zentrum) zg 
ON z.ZentrumID = zg.ZentrumID;

/*Monitoringplan: Anzeige der Informationen über ZentrumID, Land, Ort, Pruefer, 
Monitor und die Gesamtanzahl an Patienten aus Zentren, die entweder in der ersten 
oder zweiten Randomisierungwoche involviert waren. OUTER JOIN aktuell nicht unterstützt!*/
SELECT z.ZentrumID, z.Land, z.Ort, z.Pruefer, z.Monitor, zg.Gesamtanzahl as Gesamtanzahl
FROM(SELECT * FROM Zentren ORDER BY ZentrumID ASC) z
JOIN (	SELECT ZentrumID, Gesamtanzahl 
		FROM (	SELECT ru.Zentrum, SUM(ru.Anzahl) as Gesamtanzahl
				FROM(	SELECT r1.Zentrum, COUNT(r1.Zentrum) as Anzahl
						FROM Randomisierung_Woche_1 r1 
						GROUP BY r1.Zentrum
						UNION 	SELECT r2.Zentrum, COUNT(r2.Zentrum) as Anzahl
								FROM Randomisierung_Woche_2 r2 
								GROUP BY r2.Zentrum) ru
				GROUP BY ru.Zentrum) zs
		JOIN (	SELECT ZentrumID FROM Zentren  
				ORDER BY ZentrumID ASC) zi
		ON zi.ZentrumID = zs.Zentrum) zg 
ON z.ZentrumID = zg.ZentrumID
WHERE z.ZentrumID in(SELECT ZentrumID FROM (	SELECT ZentrumID, Gesamtanzahl 
												FROM (	SELECT ru2.Zentrum, SUM(ru2.Anzahl) as Gesamtanzahl
														FROM( 	SELECT r12.Zentrum, COUNT(r12.Zentrum) as Anzahl
																FROM Randomisierung_Woche_1 r12 
																GROUP BY r12.Zentrum
																UNION 	SELECT r22.Zentrum, COUNT(r22.Zentrum) as Anzahl
																		FROM Randomisierung_Woche_2 r22
																		GROUP BY r22.Zentrum) ru2
														GROUP BY ru2.Zentrum) zs2
												JOIN (	SELECT ZentrumID FROM Zentren ORDER BY ZentrumID ASC) zi2
												ON zi2.ZentrumID = zs2.Zentrum));


												

SELECT z.ZentrumID, z.Land, z.Ort, z.Pruefer, z.Monitor, zg.Gesamtanzahl 
FROM Zentren z
JOIN (	SELECT za.Zentrum, SUM(za.ANZAHL) AS Gesamtanzahl 
		FROM (	SELECT r1.Zentrum, COUNT(r1.Zentrum) as Anzahl
				FROM Randomisierung_Woche_1 r1
				GROUP BY r1.Zentrum
				UNION
				SELECT 	CASE 
							WHEN r2.Zentrum IS NULL THEN 999
							ELSE r2.Zentrum
						END AS Zentrum, 
						COUNT(r2.Zentrum) as Anzahl
				FROM Randomisierung_Woche_2 r2
				GROUP BY r2.Zentrum) za
		GROUP BY Zentrum) zg 
ON zg.Zentrum = z.ZentrumID;




-- Anzahl der Patienten pro Zentrum in der ersten Randomisierungswoche
SELECT Zentrum, count(Zentrum) as Anzahl
FROM Random_Woche_1
GROUP BY Zentrum;

-- Anzahl der deutsche Patienten pro Zentrum in der ersten Randomisierungswoche
SELECT r.Zentrum, count(r.Zentrum) as Anzahl
FROM Random_Woche_1 r
JOIN (	SELECT z.Zentrum_Id FROM Zentren z
		WHERE Land = 'D') zd on zd.Zentrum_Id = r.Zentrum
GROUP BY Zentrum;

-- Liste der Patienten in der ersten Radominisierungswoche
SELECT r.Patient_Id, r.Zentrum, r.Behandlungsarm, strftime('%d.%m.%Y', r.Datum) as Datum 
FROM Random_Woche_1 r;

SELECT r.Patient_Id, r.Zentrum, r.Behandlungsarm, Datum 
FROM Random_Woche_1 r;


-- Liste der Einwilligungen
SELECT * FROM Informed_consent;

-- Liste der Patienten bei denen die Einwilligung fehlt
SELECT * FROM Informed_consent
WHERE Einwilligung IS 'nan'

-- Liste der Patienten bei denen die Einwilligung unvollständig sind
SELECT * FROM Informed_consent
WHERE Einwilligung IS 'nan' OR Datum IS 'NaT';

-- Liste der Patienten bei denen die Einwilligung nach der Randomisierung kommt
SELECT * FROM Informed_consent
WHERE Datum > '2019.06.03';


/*Monitoringplan: Anzeige der Informationen über Zentrum_ID, Land, Ort, Prüfer, 
Monitor und die Gesamtanzahl an Patienten aus Zentren, die entweder in der ersten 
oder zweiten Randomisierungwoche involviert waren. OUTER JOIN aktuell nicht unterstützt!*/
SELECT z.Zentrum_Id, z.Land, z.Ort, z.Prüfer, z.Monitor, zg.Gesamtanzahl as NP
FROM(SELECT * FROM Zentren ORDER BY Zentrum_Id ASC) z
JOIN (	SELECT Zentrum_Id, Gesamtanzahl 
		FROM (SELECT Zentrum_Id FROM Zentren ORDER BY Zentrum_Id ASC)
		LEFT JOIN (	SELECT Zentrum, SUM(Anzahl) as Gesamtanzahl
					FROM(	SELECT r1.Zentrum, COUNT(r1.Zentrum) as Anzahl
							FROM Random_Woche_1 r1 
							GROUP BY r1.Zentrum
							UNION 	SELECT r2.Zentrum, COUNT(r2.Zentrum) as Anzahl
									FROM Random_Woche_2 r2 
									GROUP BY r2.Zentrum)
					GROUP BY Zentrum) 
		ON Zentrum_Id = Zentrum) zg 
ON z.Zentrum_Id = zg.Zentrum_Id;

/*Monitoringplan: Anzeige der Informationen über Zentrum_ID, Land, Ort, Prüfer, 
Monitor und die Gesamtanzahl an Patienten aus Zentren, die entweder in der ersten 
oder zweiten Randomisierungwoche involviert waren. OUTER JOIN aktuell nicht unterstützt!*/
SELECT z.Zentrum_Id, z.Land, z.Ort, z.Prüfer, z.Monitor, zg.Gesamtanzahl as NP
FROM(SELECT * FROM Zentren ORDER BY Zentrum_Id ASC) z
JOIN (	SELECT Zentrum_Id, Gesamtanzahl 
		FROM (	SELECT ru.Zentrum, SUM(ru.Anzahl) as Gesamtanzahl
				FROM(	SELECT r1.Zentrum, COUNT(r1.Zentrum) as Anzahl
						FROM Random_Woche_1 r1 
						GROUP BY r1.Zentrum
						UNION 	SELECT r2.Zentrum, COUNT(r2.Zentrum) as Anzahl
								FROM Random_Woche_2 r2 
								GROUP BY r2.Zentrum) ru
				GROUP BY ru.Zentrum) zs
		JOIN (	SELECT Zentrum_Id FROM Zentren  
				ORDER BY Zentrum_Id ASC) zi
		ON zi.Zentrum_Id = zs.Zentrum) zg 
ON z.Zentrum_Id = zg.Zentrum_Id;




