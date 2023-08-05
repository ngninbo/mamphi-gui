package de.fhdo.master.mi.sms.project.mamphi.gui.app;

import de.fhdo.master.mi.sms.project.mamphi.model.Consent;
import de.fhdo.master.mi.sms.project.mamphi.model.ConsentInformedStatus;
import de.fhdo.master.mi.sms.project.mamphi.model.InformedConsent;
import de.fhdo.master.mi.sms.project.mamphi.utils.TrialUtils;
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

import static de.fhdo.master.mi.sms.project.mamphi.utils.GuiConstants.*;
import static de.fhdo.master.mi.sms.project.mamphi.utils.UITranslation.*;

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
        patientIDCol.setMinWidth(DEFAULT_MIN_WIDTH);
        patientIDCol.setCellValueFactory(new PropertyValueFactory<>("patientID"));

        TableColumn<InformedConsent, Integer> centerCol = new TableColumn<>("Zentrum");
        centerCol.setMinWidth(DEFAULT_MIN_WIDTH);
        centerCol.setCellValueFactory(new PropertyValueFactory<>("centreID"));

        TableColumn<InformedConsent, String> consentCol = new TableColumn<>("Einwilligung erteilt");
        consentCol.setMinWidth(MIN_WIDTH);
        consentCol.setCellValueFactory(new PropertyValueFactory<>("consent"));
        consentCol.setCellFactory(TextFieldTableCell.forTableColumn());
        consentCol.setOnEditCommit(event -> event.getTableView().getItems().get(event.getTablePosition().getRow())
                .setConsent(event.getNewValue().toUpperCase()));

        TableColumn<InformedConsent, String> dateCol = new TableColumn<>("Datum der Einwilligung");
        dateCol.setMinWidth(MIN_WIDTH);
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateCol.setCellFactory(TextFieldTableCell.forTableColumn());
        dateCol.setOnEditCommit(event -> event.getTableView()
                .getItems()
                .get(event.getTablePosition().getRow())
                .setDate(event.getNewValue()));

        main.getConsentTable().setItems(main.getConsentData());
        main.getConsentTable().getColumns().addAll(patientIDCol, centerCol, consentCol, dateCol);
        Text consentFormText = new Text(CONSENT_FORM_TXT);
        consentFormText.setFont(new Font(FONT_NAME, FONT));
        Button deleteConsentBtn = new Button(CONSENT_DELETE_BTN_LABEL);
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
        cbCenter.setPromptText(CHOICE_PROMPT_TEXT);
        cbCenter.setOnAction(event -> Main.chosenCenter = String.valueOf(INSETS_MIN_VALUE).compareTo(cbCenter.getValue()) < 0
                ? cbCenter.getValue() : String.valueOf(VALUE_MISSING));

        HBox hbAddCenter = new HBox(centerText, cbCenter);
        hbAddCenter.setSpacing(SPACING_MAX_VALUE);
        hbAddCenter.setPadding(new Insets(INSETS_VALUE));

        Label consentText = new Label("Einwilligung: ");
        ObservableList<String> options = FXCollections.observableArrayList(EMPTY, YES.toUpperCase(), NO.toUpperCase());
        final ComboBox<String> cbConsent = new ComboBox<>(options);
        cbConsent.setValue(CHOICE_PROMPT_TEXT);
        cbConsent.setMinWidth(main.getConsentTable().getColumns().get(2).getPrefWidth());

        cbConsent.setOnAction(arg0 -> Main.selConsent = cbConsent.getValue().toLowerCase());

        HBox hbAddConsent = new HBox(consentText, cbConsent);
        hbAddConsent.setSpacing(INSETS_VALUE);
        hbAddConsent.setPadding(new Insets(INSETS_VALUE));

        Label dateText = new Label(DATE_LABEL);
        final DatePicker consentDate = new DatePicker();
        consentDate.setMinWidth(main.getConsentTable().getColumns().get(3).getPrefWidth());
        consentDate.setPromptText(DATE_FORMAT_TXT);
        HBox hbAddDate = new HBox(dateText, consentDate);
        hbAddDate.setSpacing(40);
        hbAddDate.setPadding(new Insets(INSETS_VALUE));

        final Button addConsentBtn = new Button(SAVE_BTN_LABEL);

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
                cbCenter.setValue(EMPTY);
                cbConsent.setValue(EMPTY);
                consentDate.setValue(null);
            }
        });

        final VBox vbConsent = new VBox(consentFormText, hbAddCenter, hbAddConsent, hbAddDate, addConsentBtn,
                deleteConsentBtn);
        vbConsent.setSpacing(INSETS_VALUE);
        vbConsent.setPadding(new Insets(50, INSETS_VALUE, INSETS_VALUE, SPACING_MAX_VALUE));

        final HBox hbConsentTable = new HBox();
        hbConsentTable.setSpacing(INSETS_VALUE);

        Text consentTableLabel = new Text(CONSENT_TABLE_LABEL);
        consentTableLabel.setFont(new Font(FONT_NAME, FONT));
        Label filterConsent = new Label(FILTER_LABEL);
        ObservableList<String> consentFilterOptions = FXCollections.observableArrayList(TrialUtils.getConsentOverviewOptions());

        final ComboBox<String> cbConsentFilter = new ComboBox<>(consentFilterOptions);
        cbConsentFilter.setPromptText(CONSENT_OVERVIEW_FILTER_PROMPT_TXT);

        cbConsentFilter.setOnAction(event -> {

            String choice = cbConsentFilter.getValue();
            List<InformedConsent> missingList;
            ObservableList<InformedConsent> missingConsentData;
            List<InformedConsent> uncompletedList;
            ObservableList<InformedConsent> incompleteConsentData;
            List<InformedConsent> lateConsentList;
            ObservableList<InformedConsent> lateConsentData;

            switch (choice) {
                case MISSING_CONSENT_OVERVIEW_OPTION:
                    missingList = Main.trialService.findAllInformedConsent(Consent.MISSING);
                    missingConsentData = FXCollections.observableArrayList(missingList);
                    main.getConsentTable().setItems(missingConsentData);
                    vbConsent.setVisible(false);
                    break;
                case INCOMPLETE_CONSENT_OVERVIEW_OPTION:
                    uncompletedList = Main.trialService.findAllInformedConsent(Consent.NOT_COMPLETED);
                    incompleteConsentData = FXCollections.observableArrayList(uncompletedList);
                    main.getConsentTable().setItems(incompleteConsentData);
                    vbConsent.setVisible(false);
                    break;
                case INFORMED_CONSENT_AFTER_RANDOMIZATION_OVERVIEW_OPTION:
                    lateConsentList = Main.trialService.findAllInformedConsent(Consent.LATE);
                    lateConsentData = FXCollections.observableArrayList(lateConsentList);
                    main.getConsentTable().setItems(lateConsentData);
                    vbConsent.setVisible(false);
                    break;

                case INFORMED_CONSENT_OVERVIEW_OPTION:
                    main.setConsentList(Main.trialService.findAllInformedConsent(ConsentInformedStatus.YES));
                    main.setConsentData(FXCollections.observableArrayList(main.getConsentList()));
                    main.getConsentTable().setItems(main.getConsentData());
                    vbConsent.setVisible(true);
                    break;

                case DECLINED_CONSENT_OVERVIEW_OPTION:
                    main.setConsentList(Main.trialService.findAllInformedConsent(ConsentInformedStatus.NO));
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
        hbConsentFilter.setSpacing(INSETS_VALUE);
        hbConsentFilter.setPadding(new Insets(SPACING_DEFAULT_VALUE, INSETS_MIN_VALUE, INSETS_MIN_VALUE, PADDING_BOTTOM_VALUE));

        VBox vbox = new VBox(consentTableLabel, hbConsentFilter, main.getConsentTable());
        vbox.setSpacing(SPACING_DEFAULT_VALUE);

        hbConsentTable.setPadding(new Insets(INSETS_VALUE, INSETS_VALUE, INSETS_VALUE, INSETS_VALUE));
        hbConsentTable.getChildren().addAll(vbox, vbConsent);
        return hbConsentTable;
    }
}