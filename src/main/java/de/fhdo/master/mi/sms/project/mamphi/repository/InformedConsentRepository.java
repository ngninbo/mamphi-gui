package de.fhdo.master.mi.sms.project.mamphi.repository;

import de.fhdo.master.mi.sms.project.mamphi.model.Consent;
import de.fhdo.master.mi.sms.project.mamphi.model.InformedConsent;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static de.fhdo.master.mi.sms.project.mamphi.utils.MamphiStatements.*;
import static de.fhdo.master.mi.sms.project.mamphi.utils.UITranslation.NO;
import static de.fhdo.master.mi.sms.project.mamphi.utils.UITranslation.YES;

public class InformedConsentRepository extends BaseRepository<InformedConsent>{

    private Statement statement;
    private String query;
    private static ResultSet results;
    private List<InformedConsent> consentList;

    public InformedConsentRepository() {
        super();
    }

    public InformedConsentRepository(String database) {
        super(database);
    }

    @Override
    public void update(InformedConsent informedConsent) {

        try (Connection connection = DriverManager.getConnection(databaseUrl)) {

            PreparedStatement stmt = connection.prepareStatement(UPDATE_INFORMED_CONSENT);
            stmt.setLong(1, informedConsent.getPatientenID());
            stmt.setLong(2, informedConsent.getZentrumID());
            stmt.setString(3, informedConsent.getEinwilligung());
            stmt.setString(4, informedConsent.getDate());


            int result = stmt.executeUpdate();

            if (result != 0) {
                System.out.println(UPDATE_INFORMED_CONSENT_SUCCESS_MSG);
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public List<InformedConsent> findAll() {
        try (Connection connection = DriverManager.getConnection(databaseUrl)) {

            statement = connection.createStatement();

            results = statement.executeQuery(SELECT_FROM_INFORMED_CONSENT);
            InformedConsent consent;
            consentList = new ArrayList<>();

            while (results.next()) {

                String einwilligung = "";

                if (results.getString("Einwilligung") != null) {
                    einwilligung = results.getString("Einwilligung").toUpperCase();
                }

                consent = new InformedConsent(results.getInt("patientenID"), results.getInt("Zentrum"),
                        einwilligung, results.getString("Datum"));
                consentList.add(consent);
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return consentList;
    }

    public List<InformedConsent> findAll(boolean isInformed) {
        try (Connection connection = DriverManager.getConnection(databaseUrl)) {

            query = (isInformed) ? String.format(SELECT_FROM_INFORMED_CONSENT_WHERE_CONSENT, YES) :
                    String.format(SELECT_FROM_INFORMED_CONSENT_WHERE_CONSENT, NO);

            statement = connection.createStatement();

            results = statement.executeQuery(query);
            InformedConsent consent;
            consentList = new ArrayList<>();

            while (results.next()) {

                consent = new InformedConsent(results.getInt("patientenID"), results.getInt("Zentrum"),
                        results.getString("Einwilligung").toUpperCase(), results.getString("Datum"));
                consentList.add(consent);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return consentList;
    }

    public List<InformedConsent> findAllByConsent(Consent consent) {

        try (Connection connection = DriverManager.getConnection(databaseUrl)) {

            if (consent.equals(Consent.INCOMPLETE)) {
                query = SELECT_INCOMPLETE_CONSENT;
            } else if (consent.equals(Consent.MISSING)) {
                query = SELECT_MISSING_CONSENT;
            } else {
                query = SELECT_LATE_INFORMED_CONSENT;
            }

            statement = connection.createStatement();

            results = statement.executeQuery(query);
            InformedConsent informedConsent;
            consentList = new ArrayList<>();

            while (results.next()) {

                String einwilligung = "";

                if (results.getString("Einwilligung") != null) {
                    einwilligung = results.getString("Einwilligung").toUpperCase();
                }

                informedConsent = new InformedConsent(results.getInt("patientenID"), results.getInt("Zentrum"),
                        einwilligung, results.getString("Datum"));
                consentList.add(informedConsent);

            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return consentList;
    }
}
