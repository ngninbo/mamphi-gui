package de.fhdo.master.mi.sms.project.mamphi.gui.app;

import de.fhdo.master.mi.sms.project.mamphi.model.Centre;
import de.fhdo.master.mi.sms.project.mamphi.model.Country;
import de.fhdo.master.mi.sms.project.mamphi.model.MonitorVisit;
import de.fhdo.master.mi.sms.project.mamphi.utils.GuiConstants;
import de.fhdo.master.mi.sms.project.mamphi.utils.UITranslation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MonitoringPlanHBox {
    private final Main main;

    public MonitoringPlanHBox(Main main) {
        this.main = main;
    }

    HBox createMonitoringPlan() {
        Text monitorPlanLabel = new Text(UITranslation.MONITOR_PLAN_OVERVIEW_TXT);
        monitorPlanLabel.setFont(new Font(GuiConstants.FONT_NAME, GuiConstants.FONT));
        TableView<MonitorVisit> monitorPlan = new TableView<>();
        monitorPlan.setEditable(false);
        List<MonitorVisit> visitItems = Main.trialService.getMonitorVisitPlan(false);
        ObservableList<MonitorVisit> visitData = FXCollections.observableArrayList(visitItems);
        TableColumn<MonitorVisit, String> placeCol = new TableColumn<>("Ort");
        TableColumn<MonitorVisit, String> monitorCol = new TableColumn<>("Monitor");
        monitorCol.setMinWidth(GuiConstants.DEFAULT_MIN_WIDTH);

        monitorCol.setCellValueFactory(new PropertyValueFactory<>("monitor"));
        monitorCol.setCellFactory(TextFieldTableCell.forTableColumn());
        monitorCol.setOnEditCommit(event1 -> event1.getTableView().getItems().get(event1.getTablePosition().getRow())
                .setMonitor(event1.getNewValue()));

        TableColumn<MonitorVisit, String> trierrCol = new TableColumn<>("Prüfer");
        trierrCol.setMinWidth(GuiConstants.DEFAULT_MIN_WIDTH);
        trierrCol.setCellValueFactory(new PropertyValueFactory<>("trier"));
        trierrCol.setCellFactory(TextFieldTableCell.forTableColumn());
        trierrCol.setOnEditCommit(event12 -> event12.getTableView().getItems().get(event12.getTablePosition().getRow())
                .setTrier(event12.getNewValue()));

        placeCol.setMinWidth(GuiConstants.DEFAULT_MIN_WIDTH);
        placeCol.setCellValueFactory(new PropertyValueFactory<>("place"));
        placeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        placeCol.setOnEditCommit(event13 -> event13.getTableView().getItems().get(event13.getTablePosition().getRow())
                .setPlace(event13.getNewValue()));

        TableColumn<MonitorVisit, String> countryCol = new TableColumn<>("Land");
        countryCol.setMinWidth(GuiConstants.DEFAULT_MIN_WIDTH);
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        countryCol.setCellFactory(TextFieldTableCell.forTableColumn());
        countryCol.setOnEditCommit(event14 -> event14.getTableView().getItems().get(event14.getTablePosition().getRow())
                .setCountry(event14.getNewValue()));

        TableColumn<MonitorVisit, String> centerIDCol = new TableColumn<>("Zentrum ID");
        centerIDCol.setMinWidth(GuiConstants.DEFAULT_MIN_WIDTH);
        centerIDCol.setCellValueFactory(new PropertyValueFactory<>("centreID"));

        TableColumn<MonitorVisit, Integer> numberPatientCol = new TableColumn<>("Anzahl Patienten");
        numberPatientCol.setMinWidth(GuiConstants.DEFAULT_MIN_WIDTH);
        numberPatientCol
                .setCellValueFactory(new PropertyValueFactory<>("numberOfPatient"));

        TableColumn<MonitorVisit, List<LocalDate>> visitsCol = new TableColumn<>(UITranslation.VISIT_COLUMN_LABEL);
        PropertyValueFactory<MonitorVisit, List<LocalDate>> visitDates = new PropertyValueFactory<>(
                "visitDate");
        visitsCol.setCellValueFactory(visitDates);

        visitsCol.setPrefWidth(GuiConstants.VISIT_COL_PREF_WIDTH);

        monitorPlan.setItems(visitData);
        monitorPlan.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        monitorPlan.getColumns().addAll(centerIDCol, countryCol, placeCol, trierrCol, monitorCol,
                numberPatientCol, visitsCol);

        // Delete some items
        Button deleteItemsBtn = new Button(UITranslation.DELETE_BTN_LABEL);
        deleteItemsBtn.setOnAction(ActionEvent -> {
            ObservableList<MonitorVisit> selectedVisits = monitorPlan.getSelectionModel()
                    .getSelectedItems();
            ArrayList<MonitorVisit> rows = new ArrayList<>(selectedVisits);
            rows.forEach(e -> monitorPlan.getItems().remove(e));
        });

        // Adding new items
        Text centerFormText = new Text(UITranslation.CENTER_FORM_TEXT);
        centerFormText.setFont(new Font(GuiConstants.FONT_NAME, GuiConstants.FONT));
        final TextField addMonitorName = new TextField();
        final TextField addTrierName = new TextField();
        final TextField addPlace = new TextField();

        Label monitorText = new Label("Monitor: ");
        addMonitorName.setPromptText("Monitor Name");
        addMonitorName.setMinWidth(GuiConstants.MIN_WIDTH);
        HBox hbMonitor = new HBox(monitorText, addMonitorName);
        hbMonitor.setSpacing(GuiConstants.SPACING_DEFAULT_VALUE);
        hbMonitor.setPadding(new Insets(GuiConstants.INSETS_VALUE, GuiConstants.INSETS_VALUE, GuiConstants.INSETS_VALUE, GuiConstants.INSETS_VALUE));

        Label trierText = new Label("Prüfer: ");
        addTrierName.setMinWidth(GuiConstants.MIN_WIDTH);
        addTrierName.setPromptText("Prüfer Name");
        HBox hbTrier = new HBox(trierText, addTrierName);
        hbTrier.setSpacing(GuiConstants.SPACING_MIN_VALUE);
        hbTrier.setPadding(new Insets(GuiConstants.INSETS_VALUE, GuiConstants.INSETS_VALUE, GuiConstants.INSETS_VALUE, GuiConstants.INSETS_VALUE));

        Label placeText = new Label("Ort: ");
        addPlace.setMinWidth(GuiConstants.MIN_WIDTH);
        addPlace.setPromptText("Ort");
        HBox hbPlace = new HBox(placeText, addPlace);
        hbPlace.setSpacing(GuiConstants.SPACING_MAX_VALUE);
        hbPlace.setPadding(new Insets(GuiConstants.INSETS_VALUE, GuiConstants.INSETS_VALUE, GuiConstants.INSETS_VALUE, GuiConstants.INSETS_VALUE));

        Label countryText = new Label("Land: ");
        main.setCountryNames(FXCollections.observableArrayList(UITranslation.GERMANY, UITranslation.ENGLAND));
        main.setCbCountry(new ComboBox<>(main.getCountryNames()));
        main.getCbCountry().setValue(UITranslation.COUNTRY_CHOICE_BTN_LABEL);
        main.getCbCountry().setMinWidth(GuiConstants.DEFAULT_MIN_WIDTH);
        main.getCbCountry().setPadding(new Insets(GuiConstants.INSETS_MIN_VALUE, GuiConstants.INSETS_MIN_VALUE, GuiConstants.INSETS_MIN_VALUE, GuiConstants.FONT));

        main.getCbCountry().setOnAction(arg0 -> Main.selectedCountry = main.getCbCountry().getValue());

        HBox hbCountry = new HBox(countryText, main.getCbCountry());
        hbCountry.setSpacing(GuiConstants.H_BOX_SPACING_VALUE);
        hbCountry.setPadding(new Insets(GuiConstants.INSETS_VALUE));
        final Button addCenterBtn = new Button(UITranslation.SAVE_BTN_LABEL);
        addCenterBtn.setOnAction(event15 -> {

            final String monitorNameText = addMonitorName.getText();
            Centre neuCenter = new Centre(monitorNameText, addTrierName.getText(),
                    addPlace.getText(), Main.selectedCountry, Main.trialService.nextId(UITranslation.GERMANY.equals(Main.selectedCountry) ? Country.DE : Country.GB));

            main.getCentres().add(neuCenter);
            Main.trialService.update(neuCenter);
            addMonitorName.clear();
            addTrierName.clear();
            addPlace.clear();
        });

        final VBox vbCenter = new VBox(centerFormText, hbMonitor, hbTrier, hbCountry, hbPlace, addCenterBtn);
        vbCenter.setSpacing(GuiConstants.SPACING_DEFAULT_VALUE);
        vbCenter.setPadding(new Insets(GuiConstants.INSETS_VALUE, GuiConstants.INSETS_VALUE, GuiConstants.INSETS_VALUE, GuiConstants.INSETS_VALUE));
        vbCenter.setVisible(false);
        final HBox hbox = new HBox();
        hbox.setSpacing(GuiConstants.INSETS_VALUE);
        hbox.setPadding(new Insets(GuiConstants.INSETS_VALUE, GuiConstants.INSETS_VALUE, GuiConstants.INSETS_VALUE, GuiConstants.INSETS_VALUE));
        VBox vb = new VBox(monitorPlanLabel, monitorPlan, deleteItemsBtn);
        vb.setSpacing(GuiConstants.INSETS_VALUE);
        hbox.getChildren().addAll(vb, vbCenter);
        return hbox;
    }
}