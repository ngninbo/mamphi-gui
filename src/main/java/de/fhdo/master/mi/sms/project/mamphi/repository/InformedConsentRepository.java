package de.fhdo.master.mi.sms.project.mamphi.repository;

import de.fhdo.master.mi.sms.project.mamphi.model.Consent;
import de.fhdo.master.mi.sms.project.mamphi.model.InformedConsent;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static de.fhdo.master.mi.sms.project.mamphi.utils.GuiConstants.EMPTY;
import static de.fhdo.master.mi.sms.project.mamphi.utils.TrialStatements.*;
import static de.fhdo.master.mi.sms.project.mamphi.utils.UITranslation.NO;
import static de.fhdo.master.mi.sms.project.mamphi.utils.UITranslation.YES;

public class InformedConsentRepository extends BaseRepository<InformedConsent> {

    private String query;

    public InformedConsentRepository() {
        super();
    }

    @Override
    public InformedConsentRepository setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
        return this;
    }

    @Override
    public List<InformedConsent> findAll() {
        return findAll(SELECT_FROM_INFORMED_CONSENT);
    }

    public List<InformedConsent> findAll(boolean isInformed) {
        query = (isInformed) ? String.format(SELECT_FROM_INFORMED_CONSENT_WHERE_CONSENT, YES) :
                String.format(SELECT_FROM_INFORMED_CONSENT_WHERE_CONSENT, NO);

        return findAll(query);
    }

    @Override
    public void update(InformedConsent informedConsent) {

        try (Connection connection = DriverManager.getConnection(databaseUrl)) {

            PreparedStatement stmt = connection.prepareStatement(UPDATE_INFORMED_CONSENT);
            stmt.setLong(1, informedConsent.getPatientID());
            stmt.setLong(2, informedConsent.getCentreID());
            stmt.setString(3, informedConsent.getConsent());
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

    public List<InformedConsent> findAllByConsent(Consent consent) {
        if (consent.equals(Consent.INCOMPLETE)) {
            query = SELECT_INCOMPLETE_CONSENT;
        } else if (consent.equals(Consent.MISSING)) {
            query = SELECT_MISSING_CONSENT;
        } else {
            query = SELECT_LATE_INFORMED_CONSENT;
        }

        return findAll(query);
    }

    @Override
    public List<InformedConsent> findAll(String query) {

        List<InformedConsent> consentList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(databaseUrl)) {

            Statement statement = connection.createStatement();

            ResultSet results = statement.executeQuery(query);
            InformedConsent informedConsent;

            while (results.next()) {

                informedConsent = new InformedConsent(results.getInt("patientID"), results.getInt("Centre"),
                        results.getString("Consent") != null ? results.getString("Consent").toUpperCase() : EMPTY,
                        results.getString("Date"));
                consentList.add(informedConsent);

            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return consentList;
    }
}
