package de.fhdo.master.mi.sms.project.mamphi.gui.app;

import de.fhdo.master.mi.sms.project.mamphi.model.Consent;
import de.fhdo.master.mi.sms.project.mamphi.model.InformedConsent;
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

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

public class ConsentHBox {
    private final Main main;

    public ConsentHBox(Main main) {
        this.main = main;
    }

    /**
     * @return HBox
     */
    HBox createConsentTable() {

        main.setConsentTable(new TableView<>());
        main.getConsentTable().setEditable(true);
        main.setConsentList(Main.trialService.findAllInformedConsent());
        main.setConsentData(FXCollections.observableArrayList(main.getConsentList()));
        TableColumn<InformedConsent, Integer> patientIDCol = new TableColumn<>("Patient ID");
        patientIDCol.setMinWidth(GuiConstants.DEFAULT_MIN_WIDTH);
        patientIDCol.setCellValueFactory(new PropertyValueFactory<>("patientID"));

        TableColumn<InformedConsent, Integer> centerCol = new TableColumn<>("Zentrum");
        centerCol.setMinWidth(GuiConstants.DEFAULT_MIN_WIDTH);
        centerCol.setCellValueFactory(new PropertyValueFactory<>("centreID"));

        TableColumn<InformedConsent, String> consentCol = new TableColumn<>("Einwilligung erteilt");
        consentCol.setMinWidth(GuiConstants.MIN_WIDTH);
        consentCol.setCellValueFactory(new PropertyValueFactory<>("consent"));
        consentCol.setCellFactory(TextFieldTableCell.forTableColumn());
        consentCol.setOnEditCommit(event -> event.getTableView().getItems().get(event.getTablePosition().getRow())
                .setConsent(event.getNewValue().toUpperCase()));

        TableColumn<InformedConsent, String> dateCol = new TableColumn<>("Datum der Einwilligung");
        dateCol.setMinWidth(GuiConstants.MIN_WIDTH);
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateCol.setCellFactory(TextFieldTableCell.forTableColumn());
        dateCol.setOnEditCommit(event -> event.getTableView()
                .getItems()
                .get(event.getTablePosition().getRow())
                .setDate(event.getNewValue()));

        main.getConsentTable().setItems(main.getConsentData());
        main.getConsentTable().getColumns().addAll(patientIDCol, centerCol, consentCol, dateCol);
        Text consentFormText = new Text(UITranslation.CONSENT_FORM_TXT);
        consentFormText.setFont(new Font(GuiConstants.FONT_NAME, GuiConstants.FONT));
        Button deleteConsentBtn = new Button(UITranslation.CONSENT_DELETE_BTN_LABEL);
        deleteConsentBtn.setVisible(true);
        deleteConsentBtn.setOnAction(event -> {
            InformedConsent selectedConsent = main.getConsentTable().getSelectionModel().getSelectedItem();
            // deleteConsentBtn.setVisible(selectedConsent != null ? true : false);
            main.getConsentTable().getItems().remove(selectedConsent);
        });

        // Adding new Item for informed consent
        main.setCenterIds(Main.trialService.findAllCenterIds());
        Collections.sort(main.getCenterIds());
        main.setCenterIdData(FXCollections.observableArrayList(main.getCenterIds()));
        Label centerText = new Label("Zentrum: ");
        final ComboBox<String> cbCenter = new ComboBox<>(main.getCenterIdData());
        cbCenter.setMinWidth(main.getConsentTable().getColumns().get(1).getPrefWidth());
        cbCenter.setPromptText(UITranslation.CHOICE_PROMPT_TEXT);
        cbCenter.setOnAction(event -> Main.chosenCenter = String.valueOf(GuiConstants.INSETS_MIN_VALUE).compareTo(cbCenter.getValue()) < 0
                ? cbCenter.getValue() : String.valueOf(GuiConstants.VALUE_MISSING));

        HBox hbAddCenter = new HBox(centerText, cbCenter);
        hbAddCenter.setSpacing(GuiConstants.SPACING_MAX_VALUE);
        hbAddCenter.setPadding(new Insets(GuiConstants.INSETS_VALUE));

        Label consentText = new Label("Einwilligung: ");
        ObservableList<String> options = FXCollections.observableArrayList(GuiConstants.EMPTY, UITranslation.YES.toUpperCase(), UITranslation.NO.toUpperCase());
        final ComboBox<String> cbConsent = new ComboBox<>(options);
        cbConsent.setValue(UITranslation.CHOICE_PROMPT_TEXT);
        cbConsent.setMinWidth(main.getConsentTable().getColumns().get(2).getPrefWidth());

        cbConsent.setOnAction(arg0 -> Main.selConsent = cbConsent.getValue().toLowerCase());

        HBox hbAddConsent = new HBox(consentText, cbConsent);
        hbAddConsent.setSpacing(GuiConstants.INSETS_VALUE);
        hbAddConsent.setPadding(new Insets(GuiConstants.INSETS_VALUE));

        Label dateText = new Label(UITranslation.DATE_LABEL);
        final DatePicker consentDate = new DatePicker();
        consentDate.setMinWidth(main.getConsentTable().getColumns().get(3).getPrefWidth());
        consentDate.setPromptText(UITranslation.DATE_FORMAT_TXT);
        HBox hbAddDate = new HBox(dateText, consentDate);
        hbAddDate.setSpacing(40);
        hbAddDate.setPadding(new Insets(GuiConstants.INSETS_VALUE));

        final Button addConsentBtn = new Button(UITranslation.SAVE_BTN_LABEL);

        addConsentBtn.setOnAction(event -> {

            main.setPatientIds(Main.trialService.findAllPatientID());

            Collections.sort(main.getPatientIds());

            if (Main.selConsent != null && Main.chosenCenter != null && consentDate.getValue() != null) {
                InformedConsent newInformedConsent = new InformedConsent(main.getPatientIds().get(main.getPatientIds().size() - 1) + 1,
                        Integer.parseInt(Main.chosenCenter), Main.selConsent == null ? "nan" : Main.selConsent,
                        consentDate.getValue() != null ? consentDate.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) : "NaT");
                main.getConsentData().add(newInformedConsent);
                Main.trialService.update(newInformedConsent);
                Main.selConsent = null;
                Main.chosenCenter = null;
                cbCenter.setValue(GuiConstants.EMPTY);
                cbConsent.setValue(GuiConstants.EMPTY);
                consentDate.setValue(null);
            }
        });

        final VBox vbConsent = new VBox(consentFormText, hbAddCenter, hbAddConsent, hbAddDate, addConsentBtn,
                deleteConsentBtn);
        vbConsent.setSpacing(GuiConstants.INSETS_VALUE);
        vbConsent.setPadding(new Insets(50, GuiConstants.INSETS_VALUE, GuiConstants.INSETS_VALUE, GuiConstants.SPACING_MAX_VALUE));

        final HBox hbConsentTable = new HBox();
        hbConsentTable.setSpacing(GuiConstants.INSETS_VALUE);

        Text consentTableLabel = new Text(UITranslation.CONSENT_TABLE_LABEL);
        consentTableLabel.setFont(new Font(GuiConstants.FONT_NAME, GuiConstants.FONT));
        Label filterConsent = new Label(UITranslation.FILTER_LABEL);
        ObservableList<String> consentFilterOptions = FXCollections.observableArrayList(UITranslation.ALL_CONSENT_OPTION,
                UITranslation.MISSING_CONSENT_OVERVIEW_OPTION,
                UITranslation.INCOMPLETE_CONSENT_OVERVIEW_OPTION,
                UITranslation.INFORMED_CONSENT_AFTER_RANDOMIZATION_OVERVIEW_OPTION,
                UITranslation.INFORMED_CONSENT_OVERVIEW_OPTION,
                UITranslation.DECLINED_CONSENT_OVERVIEW_OPTION);

        final ComboBox<String> cbConsentFilter = new ComboBox<>(consentFilterOptions);
        cbConsentFilter.setPromptText(UITranslation.CONSENT_OVERVIEW_FILTER_PROMPT_TXT);

        cbConsentFilter.setOnAction(event -> {

            String choice = cbConsentFilter.getValue();
            List<InformedConsent> missingList;
            ObservableList<InformedConsent> missingConsentData;
            List<InformedConsent> uncompletedList;
            ObservableList<InformedConsent> incompleteConsentData;
            List<InformedConsent> lateConsentList;
            ObservableList<InformedConsent> lateConsentData;

            switch (choice) {
                case UITranslation.MISSING_CONSENT_OVERVIEW_OPTION:
                    missingList = Main.trialService.findAllInformedConsent(Consent.MISSING);
                    missingConsentData = FXCollections.observableArrayList(missingList);
                    main.getConsentTable().setItems(missingConsentData);
                    vbConsent.setVisible(false);
                    break;
                case UITranslation.INCOMPLETE_CONSENT_OVERVIEW_OPTION:
                    uncompletedList = Main.trialService.findAllInformedConsent(Consent.INCOMPLETE);
                    incompleteConsentData = FXCollections.observableArrayList(uncompletedList);
                    main.getConsentTable().setItems(incompleteConsentData);
                    vbConsent.setVisible(false);
                    break;
                case UITranslation.INFORMED_CONSENT_AFTER_RANDOMIZATION_OVERVIEW_OPTION:
                    lateConsentList = Main.trialService.findAllInformedConsent(Consent.LATE);
                    lateConsentData = FXCollections.observableArrayList(lateConsentList);
                    main.getConsentTable().setItems(lateConsentData);
                    vbConsent.setVisible(false);
                    break;

                case UITranslation.INFORMED_CONSENT_OVERVIEW_OPTION:
                    main.setConsentList(Main.trialService.findAllInformedConsent(true));
                    main.setConsentData(FXCollections.observableArrayList(main.getConsentList()));
                    main.getConsentTable().setItems(main.getConsentData());
                    vbConsent.setVisible(true);
                    break;

                case UITranslation.DECLINED_CONSENT_OVERVIEW_OPTION:
                    main.setConsentList(Main.trialService.findAllInformedConsent(false));
                    main.setConsentData(FXCollections.observableArrayList(main.getConsentList()));
                    main.getConsentTable().setItems(main.getConsentData());
                    vbConsent.setVisible(true);
                    break;

                default:
                    main.setConsentList(Main.trialService.findAllInformedConsent());
                    main.setConsentData(FXCollections.observableArrayList(main.getConsentList()));
                    main.getConsentTable().setItems(main.getConsentData());
                    vbConsent.setVisible(true);
                    break;
            }
        });

        HBox hbConsentFilter = new HBox(filterConsent, cbConsentFilter);
        hbConsentFilter.setSpacing(GuiConstants.INSETS_VALUE);
        hbConsentFilter.setPadding(new Insets(GuiConstants.SPACING_DEFAULT_VALUE, GuiConstants.INSETS_MIN_VALUE, GuiConstants.INSETS_MIN_VALUE, GuiConstants.PADDING_BOTTOM_VALUE));

        VBox vbox = new VBox(consentTableLabel, hbConsentFilter, main.getConsentTable());
        vbox.setSpacing(GuiConstants.SPACING_DEFAULT_VALUE);

        hbConsentTable.setPadding(new Insets(GuiConstants.INSETS_VALUE, GuiConstants.INSETS_VALUE, GuiConstants.INSETS_VALUE, GuiConstants.INSETS_VALUE));
        hbConsentTable.getChildren().addAll(vbox, vbConsent);
        return hbConsentTable;
    }
}