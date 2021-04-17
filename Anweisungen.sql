-- Liste der vorhandenen Zentren
SELECT * FROM Zentren;

-- Liste der deutschen Zentren
SELECT * FROM Zentren WHERE Land = 'D';

-- Liste der britischen Zentren
SELECT * FROM Zentren WHERE Land = 'GB';

-- Nächstmögliche ID für ein britisches Zentrum
SELECT max(Zentrum_Id) + 1 as newID
FROM Zentren
WHERE Land = 'GB'

-- Nächstmögliche ID für ein deutsches Zentrum
SELECT max(Zentrum_Id) + 1 as newID
FROM Zentren
WHERE Land = 'D'

-- Nächstmögliche Patient_Id
SELECT max(Patient_Id) + 1 as newPatientID
FROM Informed_consent;

-- Anzahl der Patienten pro Zentren in der ersten Randomisierungswoche 
SELECT r1.Zentrum, COUNT(r1.Zentrum) as Anzahl
FROM Random_Woche_1 r1
GROUP BY r1.Zentrum;

-- Anzahl der britischen Patienten pro Zentrum in der ersten Randomisierungswochen
SELECT ra.Zentrum as Zentrum, ra.Anzahl as Anzahl, zd.Land
FROM(	SELECT r.Zentrum, COUNT(r.Zentrum) as Anzahl
		FROM Random_Woche_1 r
		GROUP BY r.Zentrum) ra
LEFT JOIN (	SELECT z.Zentrum_Id, z.Land 
			FROM Zentren z) zd ON ra.Zentrum = zd.Zentrum_Id
WHERE zd.Land = 'GB';


SELECT r.Zentrum, count(r.Zentrum) as Anzahl 
FROM Random_Woche_1 r
WHERE r.Zentrum in (SELECT z.Zentrum_Id 
					FROM Zentren z
					WHERE z.Land = 'GB')
GROUP BY Zentrum;

/*Liste der britischen Patienten in der ersten Randomisierungswoche.
Das Datum wird hierbei in deutschsprachigen ausgegeben */

SELECT r.Patient_Id, r.Zentrum, r.Behandlungsarm, strftime('%d.%m.%Y', r.Datum) as Datum
FROM Random_Woche_1 r
JOIN (	SELECT Zentrum_Id FROM Zentren z
		WHERE z.Land = 'GB') zb
on zb.Zentrum_Id = r.Zentrum;

SELECT r.Patient_Id, r.Zentrum, r.Behandlungsarm, r.Datum
FROM Random_Woche_1 r
JOIN (	SELECT Zentrum_Id FROM Zentren z
		WHERE z.Land = 'GB') zb
on zb.Zentrum_Id = r.Zentrum;

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



