package de.fhdo.master.mi.sms.project.mamphi.gui.app;

import de.fhdo.master.mi.sms.project.mamphi.model.Country;
import de.fhdo.master.mi.sms.project.mamphi.model.PatientCenter;
import de.fhdo.master.mi.sms.project.mamphi.model.RandomizationWeek;
import de.fhdo.master.mi.sms.project.mamphi.utils.GuiConstants;
import de.fhdo.master.mi.sms.project.mamphi.utils.TrialUtils;
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

import java.util.List;

public class RandomizationHBox {
    private final Main main;

    public RandomizationHBox(Main main) {
        this.main = main;
    }

    /**
     * @return HBox
     */
    HBox createRandomizationPane() {

        main.setRandWeekTable(new TableView<>());
        main.getRandWeekTable().setEditable(false);
        main.setRandWeekItems(Main.trialService.findAllRandomWeek());
        main.setRandWeekData(FXCollections.observableArrayList(main.getRandWeekItems()));

        TableColumn<RandomizationWeek, Integer> patientIDCol = new TableColumn<>("Patient ID");
        patientIDCol.setMinWidth(GuiConstants.DEFAULT_MIN_WIDTH);
        patientIDCol.setCellValueFactory(new PropertyValueFactory<>("patientID"));

        TableColumn<RandomizationWeek, Integer> centerCol = new TableColumn<>("Zentrum");
        centerCol.setMinWidth(GuiConstants.DEFAULT_MIN_WIDTH);
        centerCol.setCellValueFactory(new PropertyValueFactory<>("centre"));

        TableColumn<RandomizationWeek, String> groupCol = new TableColumn<>("Behandlungsarm");
        groupCol.setMinWidth(50);
        groupCol.setCellValueFactory(new PropertyValueFactory<>("group"));
        groupCol.setCellFactory(TextFieldTableCell.forTableColumn());
        groupCol.setOnEditCommit(event -> event.getTableView().getItems().get(event.getTablePosition().getRow())
                .setGroup(event.getNewValue()));

        TableColumn<RandomizationWeek, String> dateCol = new TableColumn<>("Datum der Randomisierung");
        dateCol.setMinWidth(300);
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateCol.setCellFactory(TextFieldTableCell.forTableColumn());
        dateCol.setOnEditCommit(event -> event.getTableView().getItems().get(event.getTablePosition().getRow())
                .setDate(event.getNewValue()));

        main.getRandWeekTable().setItems(main.getRandWeekData());
        main.getRandWeekTable().getColumns().addAll(patientIDCol, centerCol, groupCol, dateCol);

        // Adding new Items
        Label addPatientLabel = new Label("Patient ID: ");
        main.setPatientIds(Main.trialService.findAllPatientID());
        ObservableList<Integer> patientIdData = FXCollections.observableArrayList(main.getPatientIds());

        ComboBox<Integer> cbAddRandPatient = new ComboBox<>(patientIdData);
        cbAddRandPatient.setPromptText(UITranslation.CHOICE_PROMPT_TEXT);
        cbAddRandPatient.setMinWidth(patientIDCol.getPrefWidth());
        cbAddRandPatient.setOnAction(event -> Main.chosenPatient = cbAddRandPatient.getValue());

        HBox hbAddPatientRand = new HBox(addPatientLabel, cbAddRandPatient);
        hbAddPatientRand.setSpacing(GuiConstants.INSETS_VALUE);
        hbAddPatientRand.setPadding(new Insets(GuiConstants.INSETS_VALUE, GuiConstants.INSETS_VALUE, GuiConstants.INSETS_VALUE, GuiConstants.INSETS_VALUE));

        Label addCenterLabel = new Label("Zentrum: ");
        main.setCenterIds(Main.trialService.findAllCenterIds());
        main.setCenterIdData(FXCollections.observableArrayList(main.getCenterIds()));
        ComboBox<String> cbAddCenterRand = new ComboBox<>(main.getCenterIdData());
        cbAddCenterRand.setPromptText(UITranslation.CHOICE_PROMPT_TEXT);
        cbAddCenterRand.setMinWidth(centerCol.getPrefWidth());
        cbAddCenterRand.setOnAction(event -> Main.chosenCenter = cbAddCenterRand.getValue());

        HBox hbAddCenterRand = new HBox(addCenterLabel, cbAddCenterRand);
        hbAddCenterRand.setSpacing(GuiConstants.INSETS_VALUE);
        hbAddCenterRand.setPadding(new Insets(GuiConstants.INSETS_VALUE, GuiConstants.INSETS_VALUE, GuiConstants.INSETS_VALUE, GuiConstants.INSETS_VALUE));

        Label addGroupLabel = new Label("Behandlungsarm: ");
        ObservableList<String> groups = FXCollections.observableArrayList("A", "B");
        ComboBox<String> cbAddGroupRand = new ComboBox<>(groups);
        cbAddGroupRand.setPromptText(UITranslation.CHOICE_PROMPT_TEXT);
        cbAddGroupRand.setMinWidth(GuiConstants.DEFAULT_MIN_WIDTH);

        cbAddGroupRand.setOnAction(event -> Main.chosenGroup = cbAddGroupRand.getValue());

        HBox hbAddGroupRand = new HBox(addGroupLabel, cbAddGroupRand);
        hbAddGroupRand.setSpacing(GuiConstants.INSETS_VALUE);
        hbAddGroupRand.setPadding(new Insets(GuiConstants.INSETS_VALUE, GuiConstants.INSETS_VALUE, GuiConstants.INSETS_VALUE, GuiConstants.INSETS_VALUE));

        Label dateRandLabel = new Label(UITranslation.DATE_LABEL);
        final DatePicker addDateRand = new DatePicker();
        addDateRand.setMinWidth(dateCol.getPrefWidth());
        addDateRand.setPromptText(UITranslation.DATE_FORMAT_TXT);

        HBox hbAddDateRand = new HBox(dateRandLabel, addDateRand);
        hbAddDateRand.setSpacing(GuiConstants.INSETS_VALUE);
        hbAddDateRand.setPadding(new Insets(GuiConstants.INSETS_VALUE, GuiConstants.INSETS_VALUE, GuiConstants.INSETS_VALUE, GuiConstants.INSETS_VALUE));

        Button addRandBtn = new Button(UITranslation.ENTRY_SAVE_BTN_LABEL);
        addRandBtn.setOnAction(event -> {

            RandomizationWeek rand = new RandomizationWeek(Main.chosenPatient, Integer.parseInt(Main.chosenCenter), Main.chosenGroup,
                    addDateRand.getValue().toString());
            Main.trialService.update(rand, Main.week);
        });

        Text numberPatientPerCenterTableLabel = new Text(UITranslation.NUMBER_PATIENT_PER_CENTER_ALL_WEEK_OVERVIEW_LABEL);
        numberPatientPerCenterTableLabel.setFont(new Font(GuiConstants.FONT_NAME, GuiConstants.FONT));
        List<PatientCenter> patientPerCenter = Main.trialService.findNumberOfPatientPerCenterByAllWeek();
        ObservableList<PatientCenter> patientPerCenterData = FXCollections.observableArrayList(patientPerCenter);
        TableView<PatientCenter> patientPerCenterTable = new TableView<>();
        patientPerCenterTable.setEditable(false);
        TableColumn<PatientCenter, String> patientPerCenterCol = new TableColumn<>("Zentrum");
        patientPerCenterCol.setMinWidth(GuiConstants.DEFAULT_MIN_WIDTH);
        patientPerCenterCol.setCellValueFactory(new PropertyValueFactory<>("centre"));

        TableColumn<PatientCenter, Integer> patientPerCenterCol2 = new TableColumn<>("Anzahl der Patienten");
        patientPerCenterCol2.setMinWidth(GuiConstants.MIN_WIDTH);
        patientPerCenterCol2.setCellValueFactory(new PropertyValueFactory<>("count"));

        patientPerCenterTable.setItems(patientPerCenterData);
        patientPerCenterTable.getColumns().addAll(patientPerCenterCol, patientPerCenterCol2);

        VBox patientPerCenterVb = new VBox(numberPatientPerCenterTableLabel, patientPerCenterTable);
        patientPerCenterVb.setSpacing(GuiConstants.FONT);
        patientPerCenterVb.setPadding(new Insets(50, GuiConstants.INSETS_VALUE, GuiConstants.INSETS_VALUE, GuiConstants.FONT));

        VBox randAddVb = new VBox(hbAddPatientRand, hbAddCenterRand, hbAddGroupRand, hbAddDateRand, addRandBtn);
        randAddVb.setSpacing(GuiConstants.INSETS_VALUE);

        Label randWeekTableLabel = new Label(UITranslation.FILTER_LABEL);
        Text randTableText = new Text(UITranslation.RANDOM_WEEK_ALL_WEEK_OVERVIEW_LABEL);
        randTableText.setFont(new Font(GuiConstants.FONT_NAME, GuiConstants.FONT));

        ObservableList<String> randListOpt = FXCollections.observableArrayList(TrialUtils.getRandomWeekOverviewOptions());

        ComboBox<String> cbRandWeek = new ComboBox<>(randListOpt);
        cbRandWeek.setPromptText(UITranslation.VIEW_ANOTHER_RANDOM_WEEK_PROMPT_TXT);
        cbRandWeek.setOnAction(event -> {
            String choice = cbRandWeek.getValue();
            List<PatientCenter> patientPerCenter1;
            ObservableList<PatientCenter> patientPerCenterData1;
            switch (choice) {
                case UITranslation.RANDOM_WEEK_ONE_OVERVIEW_OPTION:
                    Main.week = UITranslation.ONE_VALUE;
                    patientPerCenter1 = Main.trialService.findNumberOfPatientPerCenterByWeek(UITranslation.ONE_VALUE);
                    patientPerCenterData1 = FXCollections.observableArrayList(patientPerCenter1);
                    patientPerCenterTable.setItems(patientPerCenterData1);
                    main.setRandWeekItems(Main.trialService.findAllByWeek(UITranslation.ONE_VALUE));
                    main.setRandWeekData(FXCollections.observableArrayList(main.getRandWeekItems()));
                    randTableText.setText(String.format(UITranslation.RANDOM_WEEK_OVERVIEW_TITLE, UITranslation.ONE_VALUE));
                    numberPatientPerCenterTableLabel.setText(String.format(UITranslation.RANDOM_WEEK_OVERVIEW_LABEL, UITranslation.ONE_VALUE));
                    main.getRandWeekTable().setItems(main.getRandWeekData());
                    break;
                case UITranslation.RANDOM_WEEK_TWO_OVERVIEW_OPTION:
                    Main.week = UITranslation.TWO_VALUE;
                    patientPerCenter1 = Main.trialService.findNumberOfPatientPerCenterByWeek(UITranslation.TWO_VALUE);
                    patientPerCenterData1 = FXCollections.observableArrayList(patientPerCenter1);
                    patientPerCenterTable.setItems(patientPerCenterData1);
                    main.setRandWeekItems(Main.trialService.findAllByWeek(UITranslation.TWO_VALUE));
                    main.setRandWeekData(FXCollections.observableArrayList(main.getRandWeekItems()));
                    randTableText.setText(String.format(UITranslation.RANDOM_WEEK_OVERVIEW_TITLE, UITranslation.TWO_VALUE));
                    numberPatientPerCenterTableLabel.setText(String.format(UITranslation.RANDOM_WEEK_OVERVIEW_LABEL, UITranslation.TWO_VALUE));
                    main.getRandWeekTable().setItems(main.getRandWeekData());
                    break;
                case UITranslation.RANDOM_WEEK_ONE_GERMANY_OVERVIEW_OPTION:
                    Main.week = UITranslation.ONE_VALUE;
                    patientPerCenter1 = Main.trialService.findNumberPatientPerCenterByCountryByWeek(Country.DE, UITranslation.ONE_VALUE);
                    patientPerCenterData1 = FXCollections.observableArrayList(patientPerCenter1);
                    patientPerCenterTable.setItems(patientPerCenterData1);
                    main.setRandWeekItems(Main.trialService.findAllByWeekAndCountry(UITranslation.ONE_VALUE, Country.DE));
                    main.setRandWeekData(FXCollections.observableArrayList(main.getRandWeekItems()));
                    randTableText.setText(String.format(UITranslation.RANDOM_WEEK_OVERVIEW_TITLE, UITranslation.ONE_VALUE));
                    numberPatientPerCenterTableLabel.setText(String.format(UITranslation.NUMBER_PATIENT_PER_CENTER_GERMANY_WEEK_OVERVIEW_LABEL, UITranslation.ONE_VALUE));
                    main.getRandWeekTable().setItems(main.getRandWeekData());
                    break;
                case UITranslation.RANDOM_WEEK_TWO_GERMANY_OVERVIEW_OPTION:
                    Main.week = UITranslation.TWO_VALUE;
                    patientPerCenter1 = Main.trialService.findNumberPatientPerCenterByCountryByWeek(Country.DE, UITranslation.TWO_VALUE);
                    patientPerCenterData1 = FXCollections.observableArrayList(patientPerCenter1);
                    patientPerCenterTable.setItems(patientPerCenterData1);
                    main.setRandWeekItems(Main.trialService.findAllByWeekAndCountry(UITranslation.TWO_VALUE, Country.DE));
                    main.setRandWeekData(FXCollections.observableArrayList(main.getRandWeekItems()));
                    randTableText.setText(String.format(UITranslation.RANDOM_WEEK_OVERVIEW_TITLE, UITranslation.TWO_VALUE));
                    numberPatientPerCenterTableLabel.setText(String.format(UITranslation.NUMBER_PATIENT_PER_CENTER_GERMANY_WEEK_OVERVIEW_LABEL, UITranslation.TWO_VALUE));
                    main.getRandWeekTable().setItems(main.getRandWeekData());
                    break;
                case UITranslation.RANDOM_WEEK_ONE_ENGLAND_OVERVIEW_OPTION:
                    Main.week = UITranslation.ONE_VALUE;
                    patientPerCenter1 = Main.trialService.findNumberPatientPerCenterByCountryByWeek(Country.GB, UITranslation.ONE_VALUE);
                    patientPerCenterData1 = FXCollections.observableArrayList(patientPerCenter1);
                    patientPerCenterTable.setItems(patientPerCenterData1);
                    main.setRandWeekItems(Main.trialService.findAllByWeekAndCountry(UITranslation.ONE_VALUE, Country.GB));
                    main.setRandWeekData(FXCollections.observableArrayList(main.getRandWeekItems()));
                    randTableText.setText(String.format(UITranslation.RANDOM_WEEK_OVERVIEW_TITLE, UITranslation.ONE_VALUE));
                    numberPatientPerCenterTableLabel.setText(String.format(UITranslation.NUMBER_PATIENT_PER_CENTER_ENGLAND_WEEK_OVERVIEW_LABEL, UITranslation.ONE_VALUE));
                    main.getRandWeekTable().setItems(main.getRandWeekData());
                    break;
                case UITranslation.RANDOM_WEEK_TWO_ENGLAND_OVERVIEW_OPTION:
                    Main.week = UITranslation.TWO_VALUE;
                    patientPerCenter1 = Main.trialService.findNumberPatientPerCenterByCountryByWeek(Country.GB, Main.week);
                    patientPerCenterData1 = FXCollections.observableArrayList(patientPerCenter1);
                    patientPerCenterTable.setItems(patientPerCenterData1);
                    main.setRandWeekItems(Main.trialService.findAllByWeekAndCountry(Main.week, Country.GB));
                    main.setRandWeekData(FXCollections.observableArrayList(main.getRandWeekItems()));
                    randTableText.setText(String.format(UITranslation.RANDOM_WEEK_OVERVIEW_TITLE, UITranslation.TWO_VALUE));
                    numberPatientPerCenterTableLabel.setText(String.format(UITranslation.NUMBER_PATIENT_PER_CENTER_ENGLAND_WEEK_OVERVIEW_LABEL, UITranslation.TWO_VALUE));
                    main.getRandWeekTable().setItems(main.getRandWeekData());
                    break;
                default:
                    patientPerCenter1 = Main.trialService.findNumberOfPatientPerCenterByAllWeek();
                    patientPerCenterData1 = FXCollections.observableArrayList(patientPerCenter1);
                    patientPerCenterTable.setItems(patientPerCenterData1);
                    main.setRandWeekItems(Main.trialService.findAllRandomWeek());
                    main.setRandWeekData(FXCollections.observableArrayList(main.getRandWeekItems()));
                    randTableText.setText(UITranslation.RANDOM_WEEK_ALL_WEEK_OVERVIEW_LABEL);
                    numberPatientPerCenterTableLabel.setText(UITranslation.NUMBER_PATIENT_PER_CENTER_ALL_WEEK_OVERVIEW_LABEL);
                    main.getRandWeekTable().setItems(main.getRandWeekData());
                    break;
            }
        });

        HBox hbRand = new HBox(randWeekTableLabel, cbRandWeek);
        hbRand.setSpacing(GuiConstants.INSETS_VALUE);
        hbRand.setPadding(new Insets(GuiConstants.SPACING_DEFAULT_VALUE, GuiConstants.INSETS_MIN_VALUE, GuiConstants.INSETS_MIN_VALUE, GuiConstants.PADDING_BOTTOM_VALUE));

        VBox vbRand = new VBox(randTableText, hbRand, main.getRandWeekTable());
        vbRand.setSpacing(GuiConstants.INSETS_VALUE);
        vbRand.setPadding(new Insets(GuiConstants.INSETS_VALUE));
        randAddVb.setVisible(false);
        final HBox hbox = new HBox(vbRand, patientPerCenterVb, randAddVb);
        hbox.setSpacing(GuiConstants.INSETS_VALUE);
        hbox.setPadding(new Insets(GuiConstants.INSETS_VALUE, GuiConstants.INSETS_VALUE, GuiConstants.INSETS_VALUE, GuiConstants.INSETS_VALUE));
        return hbox;
    }
}