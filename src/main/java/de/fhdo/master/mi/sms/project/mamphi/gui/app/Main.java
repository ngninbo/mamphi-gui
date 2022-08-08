package de.fhdo.master.mi.sms.project.mamphi.gui.app;

import de.fhdo.master.mi.sms.project.mamphi.model.*;
import de.fhdo.master.mi.sms.project.mamphi.service.TrialService;
import de.fhdo.master.mi.sms.project.mamphi.service.TrialServiceBuilder;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import static de.fhdo.master.mi.sms.project.mamphi.utils.TrialStatements.DATABASE_NAME;

public class Main extends Application {

    protected static String selectedCountry, selConsent, chosenGroup;
    protected static int chosenPatient, week;
    protected static String chosenCenter;
    protected static TrialService trialService;
    private final HBoxMenu menu = new HBoxMenu(this);
    private TableView<InformedConsent> consentTable;
    private List<InformedConsent> consentList;
    private ObservableList<InformedConsent> consentData;
    private List<String> centerIds;
    private List<Integer> patientIds;
    private TableView<Centre> centerTable;
    private List<Centre> centerList;
    private ObservableList<Centre> centres;

    private ObservableList<String> centerIdData;
    private ObservableList<String> countryNames;
    private ComboBox<String> cbCountry;
    private List<RandomizationWeek> randWeekItems;
    private ObservableList<RandomizationWeek> randWeekData;
    private TableView<RandomizationWeek> randWeekTable;

    public static void main(String[] args) throws SQLException, IOException {

        trialService = TrialServiceBuilder.init()
                .withDatabase(DATABASE_NAME)
                .withCenterRepository()
                .withInformedConsentRepository()
                .withRandomizationWeekRepository()
                .build();

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        new Stagger(menu).setStage(primaryStage);
    }

    public TableView<InformedConsent> getConsentTable() {
        return consentTable;
    }

    public void setConsentTable(TableView<InformedConsent> consentTable) {
        this.consentTable = consentTable;
    }

    public TableView<Centre> getCenterTable() {
        return centerTable;
    }

    public void setCenterTable(TableView<Centre> centerTable) {
        this.centerTable = centerTable;
    }

    public TableView<RandomizationWeek> getRandWeekTable() {
        return randWeekTable;
    }

    public void setRandWeekTable(TableView<RandomizationWeek> randWeekTable) {
        this.randWeekTable = randWeekTable;
    }

    public ObservableList<InformedConsent> getConsentData() {
        return consentData;
    }

    public List<String> getCenterIds() {
        return centerIds;
    }

    public void setCenterIds(List<String> centerIds) {
        this.centerIds = centerIds;
    }

    public List<Integer> getPatientIds() {
        return patientIds;
    }

    public void setPatientIds(List<Integer> patientIds) {
        this.patientIds = patientIds;
    }

    public List<Centre> getCenterList() {
        return centerList;
    }

    public void setCenterList(List<Centre> centerList) {
        this.centerList = centerList;
    }

    public ObservableList<String> getCenterIdData() {
        return centerIdData;
    }

    public void setCenterIdData(ObservableList<String> centerIdData) {
        this.centerIdData = centerIdData;
    }

    public ObservableList<RandomizationWeek> getRandWeekData() {
        return randWeekData;
    }

    public List<InformedConsent> getConsentList() {
        return consentList;
    }

    public void setConsentList(List<InformedConsent> consentList) {
        this.consentList = consentList;
    }

    public void setConsentData(ObservableList<InformedConsent> consentData) {
        this.consentData = consentData;
    }

    public List<RandomizationWeek> getRandWeekItems() {
        return randWeekItems;
    }

    public void setRandWeekItems(List<RandomizationWeek> randWeekItems) {
        this.randWeekItems = randWeekItems;
    }

    public void setRandWeekData(ObservableList<RandomizationWeek> randWeekData) {
        this.randWeekData = randWeekData;
    }

    public ObservableList<Centre> getCentres() {
        return centres;
    }

    public void setCentres(ObservableList<Centre> centres) {
        this.centres = centres;
    }

    public ComboBox<String> getCbCountry() {
        return cbCountry;
    }

    public void setCbCountry(ComboBox<String> cbCountry) {
        this.cbCountry = cbCountry;
    }

    public ObservableList<String> getCountryNames() {
        return countryNames;
    }

    public void setCountryNames(ObservableList<String> countryNames) {
        this.countryNames = countryNames;
    }
}
