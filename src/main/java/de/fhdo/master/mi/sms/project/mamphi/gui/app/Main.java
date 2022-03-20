package de.fhdo.master.mi.sms.project.mamphi.gui.app;

import de.fhdo.master.mi.sms.project.mamphi.model.*;
import de.fhdo.master.mi.sms.project.mamphi.service.TrialService;
import de.fhdo.master.mi.sms.project.mamphi.service.TrialServiceBuilder;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;

import static de.fhdo.master.mi.sms.project.mamphi.utils.MamphiStatements.DATABASE;
import static de.fhdo.master.mi.sms.project.mamphi.utils.UITranslation.*;
import static de.fhdo.master.mi.sms.project.mamphi.utils.GuiConstants.*;

public class Main extends Application {

    private static String selectedCountry, selConsent, chosenGroup;
    private static int chosenCenter, chosenPatient, week;
    private static TrialService trialService;
    private TableView<InformedConsent> consentTable;
    private List<InformedConsent> consentList;
    private ObservableList<InformedConsent> consentData;
    private List<Integer> centerIds;
    private List<Integer> patientIds;
    private TableView<Zentrum> centerTable;
    private List<Zentrum> centerList;
    private ObservableList<Zentrum> data;

    private ObservableList<Integer> centerIdData;
    private ObservableList<String> landNames;
    private ComboBox<String> cbLand;
    private final ArrayList<User> users = new ArrayList<>();
    private List<RandomizationWeek> randWeekItems;
    private ObservableList<RandomizationWeek> randWeekData;
    private TableView<RandomizationWeek> randWeekTable;
    private static FlowPane root;

    public static void main(String[] args) {

        trialService = TrialServiceBuilder.init()
                .withDatabase(DATABASE)
                .withCenterRepository()
                .withInformedConsentRepository()
                .withRandomizationWeekRepository()
                .withInitialData()
                .build();

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            primaryStage.setTitle(MAMPHI_ADMINISTRATION_GUI_TITLE);

            User demo = new User("demo", "demo");
            users.add(demo);
            User admin = new User("admin", "admin");
            users.add(admin);
            // Set Icon
            // Create an Image
            InputStream stream = getClass().getResourceAsStream("mamphi.png");
            assert stream != null;
            Image icon = new Image(stream);
            primaryStage.getIcons().add(icon);

            // Define grid pane for login
            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(INSETS_VALUE);
            grid.setVgap(INSETS_VALUE);
            grid.setPadding(new Insets(H_BOX_SPACING_VALUE, H_BOX_SPACING_VALUE, H_BOX_SPACING_VALUE, H_BOX_SPACING_VALUE));
            Text sceneTitle = new Text(WELCOME_TXT);
            sceneTitle.setId("welcome-text");
            grid.add(sceneTitle, INSETS_MIN_VALUE, INSETS_MIN_VALUE, 2, 1);

            Label userName = new Label("User Name:");
            grid.add(userName, INSETS_MIN_VALUE, 1);

            TextField userTextField = new TextField();
            userTextField.setPromptText("Username");
            grid.add(userTextField, 1, 1);

            Label pw = new Label("Password:");
            grid.add(pw, INSETS_MIN_VALUE, 2);

            PasswordField pwBox = new PasswordField();
            pwBox.setPromptText("Password");
            grid.add(pwBox, 1, 2);

            Button btn = new Button("Sign in");
            btn.setTooltip(new Tooltip(LOGIN_BUTTON_TOOLTIP_TXT));
            btn.setId("sign-in");
            HBox hbBtn = new HBox(INSETS_VALUE);
            hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
            hbBtn.getChildren().add(btn);
            grid.add(hbBtn, 1, 4);

            final Text actionTarget = new Text();
            grid.add(actionTarget, 1, 6);
            actionTarget.setId("actionTarget");

            final Button consentBtn = new Button(CONSENT_BUTTON_LABEL);
            consentBtn.setTooltip(new Tooltip(CONSENT_BUTTON_TOOLTIP_TXT));
            consentBtn.setFont(new Font(FONT_NAME, FONT));
            final Button centerBtn = new Button(CENTER_BUTTON_LABEL);
            centerBtn.setTooltip(new Tooltip(CENTER_BUTTON_TOOLTIP_TXT));
            centerBtn.setFont(new Font(FONT_NAME, FONT));
            final Button randomWeekBtn = new Button(RANDOM_WEEK_BTN_LABEL);
            randomWeekBtn.setTooltip(new Tooltip(RANDOM_WEEK_TOOLTIP_TXT));
            randomWeekBtn.setFont(new Font(FONT_NAME, FONT));
            final Button monitoringBtn = new Button(MONITORING_PLAN_BTN_LABEL);
            monitoringBtn.setTooltip(new Tooltip(MONITORING_PLAN_TOOLTIP_TXT));
            monitoringBtn.setFont(new Font(FONT_NAME, FONT));
            final Button logoutBtn = new Button(SIGN_OUT_BTN_LABEL);
            logoutBtn.setTooltip(new Tooltip(SIGN_OUT_BTN_TOOLTIP_TXT));
            logoutBtn.setFont(new Font(FONT_NAME, FONT));

            HBox btnMenu = new HBox();
            btnMenu.getChildren().addAll(centerBtn, consentBtn, randomWeekBtn, monitoringBtn, logoutBtn);
            // btnMenu.setSpacing(5);
            btnMenu.setPadding(new Insets(INSETS_VALUE, INSETS_VALUE, INSETS_MIN_VALUE, INSETS_VALUE));

            root = new FlowPane(INSETS_VALUE, INSETS_VALUE);

            Scene loginScene = new Scene(grid, LOGIN_SCENE_GRID_DIM, LOGIN_SCENE_GRID_DIM);
            loginScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("application.css")).toExternalForm());

            Scene mainScene = new Scene(root, MAIN_SCENE_HEIGHT, MAIN_SCENE_WIDTH);
            mainScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("application.css")).toExternalForm());

            // Add event handler for login button in the login view
            btn.setOnAction(event -> {

                User loginUser = new User(userTextField.getText(), pwBox.getText());

                if (users.contains(loginUser)) {

                    primaryStage.setScene(mainScene);
                    actionTarget.setText("");
                    primaryStage.show();

                } else {
                    actionTarget.setText(SIGN_IN_ERROR_MSG);
                }

                userTextField.clear();
                pwBox.clear();
            });

            // Add Event Handler for log out button
            logoutBtn.setOnAction(event -> {
                primaryStage.setScene(loginScene);
                primaryStage.show();
            });

            // Center Table
            final HBox hbCenterTable = createCenterTable();

            // Add Event listener for each button from button menu
            centerBtn.setOnAction(event -> {

                root = new FlowPane(INSETS_VALUE, INSETS_VALUE);
                // Center Table
                final HBox hbCenterTable1 = createCenterTable();
                // Add the created horizontal box to the root pane
                root.getChildren().addAll(btnMenu, hbCenterTable1);

                mainScene.setRoot(root);
            });

            consentBtn.setOnAction(ActionEvent -> {

                root = new FlowPane(INSETS_VALUE, INSETS_VALUE);
                final HBox hbConsentTable = createConsentTable();

                // Add the created horizontal box to the root pane
                root.getChildren().addAll(btnMenu, hbConsentTable);

                mainScene.setRoot(root);
            });

            randomWeekBtn.setOnAction(ActionEvent -> {

                root = new FlowPane(INSETS_VALUE, INSETS_VALUE);
                final HBox hbox = createRandomizationPane();

                // Add the created horizontal box to the root pane
                root.getChildren().addAll(btnMenu, hbox);
                mainScene.setRoot(root);
            });

            monitoringBtn.setOnAction(event -> {
                root = new FlowPane(INSETS_VALUE, INSETS_VALUE);
                Text monitorPlanLabel = new Text(MONITOR_PLAN_OVERVIEW_TXT);
                monitorPlanLabel.setFont(new Font(FONT_NAME, FONT));
                TableView<MonitorVisite> monitorPlan = new TableView<>();
                monitorPlan.setEditable(false);
                List<MonitorVisite> visitItems = trialService.getMonitorVisitPlan(false);
                ObservableList<MonitorVisite> visitData = FXCollections.observableArrayList(visitItems);
                TableColumn<MonitorVisite, String> ortCol = new TableColumn<>("Ort");
                TableColumn<MonitorVisite, String> monitorCol = new TableColumn<>("Monitor");
                monitorCol.setMinWidth(DEFAULT_MIN_WIDTH);

                monitorCol.setCellValueFactory(new PropertyValueFactory<>("monitor"));
                monitorCol.setCellFactory(TextFieldTableCell.forTableColumn());
                monitorCol.setOnEditCommit(event1 -> event1.getTableView().getItems().get(event1.getTablePosition().getRow())
                        .setMonitor(event1.getNewValue()));

                TableColumn<MonitorVisite, String> proctorCol = new TableColumn<>("Prüfer");
                proctorCol.setMinWidth(DEFAULT_MIN_WIDTH);
                proctorCol.setCellValueFactory(new PropertyValueFactory<>("pruefer"));
                proctorCol.setCellFactory(TextFieldTableCell.forTableColumn());
                proctorCol.setOnEditCommit(event12 -> event12.getTableView().getItems().get(event12.getTablePosition().getRow())
                        .setPruefer(event12.getNewValue()));

                ortCol.setMinWidth(DEFAULT_MIN_WIDTH);
                ortCol.setCellValueFactory(new PropertyValueFactory<>("ort"));
                ortCol.setCellFactory(TextFieldTableCell.forTableColumn());
                ortCol.setOnEditCommit(event13 -> event13.getTableView().getItems().get(event13.getTablePosition().getRow())
                        .setOrt(event13.getNewValue()));

                TableColumn<MonitorVisite, String> landCol = new TableColumn<>("Land");
                landCol.setMinWidth(DEFAULT_MIN_WIDTH);
                landCol.setCellValueFactory(new PropertyValueFactory<>("land"));
                landCol.setCellFactory(TextFieldTableCell.forTableColumn());
                landCol.setOnEditCommit(event14 -> event14.getTableView().getItems().get(event14.getTablePosition().getRow())
                        .setLand(event14.getNewValue()));

                TableColumn<MonitorVisite, String> centerIDCol = new TableColumn<>("Zentrum ID");
                centerIDCol.setMinWidth(DEFAULT_MIN_WIDTH);
                centerIDCol.setCellValueFactory(new PropertyValueFactory<>("zentrumID"));

                TableColumn<MonitorVisite, Integer> numberPatientCol = new TableColumn<>("Anzahl Patienten");
                numberPatientCol.setMinWidth(DEFAULT_MIN_WIDTH);
                numberPatientCol
                        .setCellValueFactory(new PropertyValueFactory<>("numberOfPatient"));

                TableColumn<MonitorVisite, List<LocalDate>> visitsCol = new TableColumn<>(VISIT_COLUMN_LABEL);
                PropertyValueFactory<MonitorVisite, List<LocalDate>> visitDates = new PropertyValueFactory<>(
                        "visitDate");
                visitsCol.setCellValueFactory(visitDates);

                visitsCol.setPrefWidth(VISIT_COL_PREF_WIDTH);

                monitorPlan.setItems(visitData);
                monitorPlan.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                monitorPlan.getColumns().addAll(centerIDCol, landCol, ortCol, proctorCol, monitorCol,
                        numberPatientCol, visitsCol);

                // Delete some items
                Button deleteItemsBtn = new Button(DELETE_BTN_LABEL);
                deleteItemsBtn.setOnAction(ActionEvent -> {
                    ObservableList<MonitorVisite> selectedVisits = monitorPlan.getSelectionModel()
                            .getSelectedItems();
                    ArrayList<MonitorVisite> rows = new ArrayList<>(selectedVisits);
                    rows.forEach(e -> monitorPlan.getItems().remove(e));
                });

                // Adding new items
                Text centerFormText = new Text(CENTER_FORM_TEXT);
                centerFormText.setFont(new Font(FONT_NAME, FONT));
                final TextField addMonitorName = new TextField();
                final TextField addProctorName = new TextField();
                final TextField addOrt = new TextField();

                Label monitorText = new Label("Monitor: ");
                addMonitorName.setPromptText("Monitor Name");
                addMonitorName.setMinWidth(MIN_WIDTH);
                HBox hbMonitor = new HBox(monitorText, addMonitorName);
                hbMonitor.setSpacing(SPACING_DEFAULT_VALUE);
                hbMonitor.setPadding(new Insets(INSETS_VALUE, INSETS_VALUE, INSETS_VALUE, INSETS_VALUE));

                Label proctorText = new Label("Prüfer: ");
                addProctorName.setMinWidth(MIN_WIDTH);
                addProctorName.setPromptText("Prüfer Name");
                HBox hbProctor = new HBox(proctorText, addProctorName);
                hbProctor.setSpacing(SPACING_MIN_VALUE);
                hbProctor.setPadding(new Insets(INSETS_VALUE, INSETS_VALUE, INSETS_VALUE, INSETS_VALUE));

                Label ortText = new Label("Ort: ");
                addOrt.setMinWidth(MIN_WIDTH);
                addOrt.setPromptText("Ort");
                HBox hbOrt = new HBox(ortText, addOrt);
                hbOrt.setSpacing(SPACING_MAX_VALUE);
                hbOrt.setPadding(new Insets(INSETS_VALUE, INSETS_VALUE, INSETS_VALUE, INSETS_VALUE));

                Label landText = new Label("Land: ");
                landNames = FXCollections.observableArrayList(GERMANY, ENGLAND);
                cbLand = new ComboBox<>(landNames);
                cbLand.setValue(COUNTRY_CHOICE_BTN_LABEL);
                cbLand.setMinWidth(DEFAULT_MIN_WIDTH);
                cbLand.setPadding(new Insets(INSETS_MIN_VALUE, INSETS_MIN_VALUE, INSETS_MIN_VALUE, FONT));

                cbLand.setOnAction(arg0 -> selectedCountry = cbLand.getValue());

                HBox hbLand = new HBox(landText, cbLand);
                hbLand.setSpacing(H_BOX_SPACING_VALUE);
                hbLand.setPadding(new Insets(INSETS_VALUE));
                final Button addCenterBtn = new Button(SAVE_BTN_LABEL);
                addCenterBtn.setOnAction(event15 -> {

                    Zentrum neuCenter = new Zentrum(addMonitorName.getText(), addProctorName.getText(),
                            addOrt.getText(), selectedCountry, data);

                    data.add(neuCenter);
                    trialService.update(neuCenter);
                    addMonitorName.clear();
                    addProctorName.clear();
                    addOrt.clear();
                });

                final VBox vbCenter = new VBox(centerFormText, hbMonitor, hbProctor, hbLand, hbOrt, addCenterBtn);
                vbCenter.setSpacing(SPACING_DEFAULT_VALUE);
                vbCenter.setPadding(new Insets(INSETS_VALUE, INSETS_VALUE, INSETS_VALUE, INSETS_VALUE));
                vbCenter.setVisible(false);
                final HBox hbox = new HBox();
                hbox.setSpacing(INSETS_VALUE);
                hbox.setPadding(new Insets(INSETS_VALUE, INSETS_VALUE, INSETS_VALUE, INSETS_VALUE));
                VBox vb = new VBox(monitorPlanLabel, monitorPlan, deleteItemsBtn);
                vb.setSpacing(INSETS_VALUE);
                hbox.getChildren().addAll(vb, vbCenter);

                // Add the created horizontal box to the root pane
                root.getChildren().addAll(btnMenu, hbox);
                mainScene.setRoot(root);
            });

            // Add button menu and the created horizontal box to the root pane
            root.getChildren().addAll(btnMenu, hbCenterTable);

            primaryStage.setScene(loginScene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return HBox
     */
    private HBox createRandomizationPane() {

        randWeekTable = new TableView<>();
        randWeekTable.setEditable(false);
        randWeekItems = trialService.findAllRandomWeek();
        randWeekData = FXCollections.observableArrayList(randWeekItems);

        TableColumn<RandomizationWeek, Integer> patientIDCol = new TableColumn<>("Patient ID");
        patientIDCol.setMinWidth(DEFAULT_MIN_WIDTH);
        patientIDCol.setCellValueFactory(new PropertyValueFactory<>("patientenID"));

        TableColumn<RandomizationWeek, Integer> centerCol = new TableColumn<>("Zentrum");
        centerCol.setMinWidth(DEFAULT_MIN_WIDTH);
        centerCol.setCellValueFactory(new PropertyValueFactory<>("zentrum"));

        TableColumn<RandomizationWeek, String> groupCol = new TableColumn<>("Behandlungsarm");
        groupCol.setMinWidth(50);
        groupCol.setCellValueFactory(new PropertyValueFactory<>("behandlungsarm"));
        groupCol.setCellFactory(TextFieldTableCell.forTableColumn());
        groupCol.setOnEditCommit(event -> event.getTableView().getItems().get(event.getTablePosition().getRow())
                .setBehandlungsarm(event.getNewValue()));

        TableColumn<RandomizationWeek, String> dateCol = new TableColumn<>("Datum der Randomisierung");
        dateCol.setMinWidth(300);
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateCol.setCellFactory(TextFieldTableCell.forTableColumn());
        dateCol.setOnEditCommit(event -> event.getTableView().getItems().get(event.getTablePosition().getRow())
                .setDate(event.getNewValue()));

        randWeekTable.setItems(randWeekData);
        randWeekTable.getColumns().addAll(patientIDCol, centerCol, groupCol, dateCol);

        // Adding new Items
        Label addPatientLabel = new Label("Patient ID: ");
        patientIds = trialService.findAllPatientID();
        ObservableList<Integer> patientIdData = FXCollections.observableArrayList(patientIds);

        ComboBox<Integer> cbAddRandPatient = new ComboBox<>(patientIdData);
        cbAddRandPatient.setPromptText(CHOICE_PROMPT_TEXT);
        cbAddRandPatient.setMinWidth(patientIDCol.getPrefWidth());
        cbAddRandPatient.setOnAction(event -> chosenPatient = cbAddRandPatient.getValue());

        HBox hbAddPatientRand = new HBox(addPatientLabel, cbAddRandPatient);
        hbAddPatientRand.setSpacing(INSETS_VALUE);
        hbAddPatientRand.setPadding(new Insets(INSETS_VALUE, INSETS_VALUE, INSETS_VALUE, INSETS_VALUE));

        Label addCenterLabel = new Label("Zentrum: ");
        centerIds = trialService.findAllCenterIds();
        centerIdData = FXCollections.observableArrayList(centerIds);
        ComboBox<Integer> cbAddCenterRand = new ComboBox<>(centerIdData);
        cbAddCenterRand.setPromptText(CHOICE_PROMPT_TEXT);
        cbAddCenterRand.setMinWidth(centerCol.getPrefWidth());
        cbAddCenterRand.setOnAction(event -> chosenCenter = cbAddCenterRand.getValue());

        HBox hbAddCenterRand = new HBox(addCenterLabel, cbAddCenterRand);
        hbAddCenterRand.setSpacing(INSETS_VALUE);
        hbAddCenterRand.setPadding(new Insets(INSETS_VALUE, INSETS_VALUE, INSETS_VALUE, INSETS_VALUE));

        Label addGroupLabel = new Label("Behandlungsarm: ");
        ObservableList<String> groups = FXCollections.observableArrayList("A", "B");
        ComboBox<String> cbAddGroupRand = new ComboBox<>(groups);
        cbAddGroupRand.setPromptText(CHOICE_PROMPT_TEXT);
        cbAddGroupRand.setMinWidth(DEFAULT_MIN_WIDTH);

        cbAddGroupRand.setOnAction(event -> chosenGroup = cbAddGroupRand.getValue());

        HBox hbAddGroupRand = new HBox(addGroupLabel, cbAddGroupRand);
        hbAddGroupRand.setSpacing(INSETS_VALUE);
        hbAddGroupRand.setPadding(new Insets(INSETS_VALUE, INSETS_VALUE, INSETS_VALUE, INSETS_VALUE));

        Label dateRandLabel = new Label(DATE_LABEL);
        final DatePicker addDateRand = new DatePicker();
        addDateRand.setMinWidth(dateCol.getPrefWidth());
        addDateRand.setPromptText(DATE_FORMAT_TXT);

        HBox hbAddDateRand = new HBox(dateRandLabel, addDateRand);
        hbAddDateRand.setSpacing(INSETS_VALUE);
        hbAddDateRand.setPadding(new Insets(INSETS_VALUE, INSETS_VALUE, INSETS_VALUE, INSETS_VALUE));

        Button addRandBtn = new Button(ENTRY_SAVE_BTN_LABEL);
        addRandBtn.setOnAction(event -> {

            RandomizationWeek rand = new RandomizationWeek(chosenPatient, chosenCenter, chosenGroup,
                    addDateRand.getValue().toString());
            trialService.update(rand, week);
        });

        Text numberPatientPerCenterTableLabel = new Text(NUMBER_PATIENT_PER_CENTER_ALL_WEEK_OVERVIEW_LABEL);
        numberPatientPerCenterTableLabel.setFont(new Font(FONT_NAME, FONT));
        List<PatientCenter> patientPerCenter = trialService.findNumberOfPatientPerCenterByAllWeek();
        ObservableList<PatientCenter> patientPerCenterData = FXCollections.observableArrayList(patientPerCenter);
        TableView<PatientCenter> patientPerCenterTable = new TableView<>();
        patientPerCenterTable.setEditable(false);
        TableColumn<PatientCenter, String> patientPerCenterCol = new TableColumn<>("Zentrum");
        patientPerCenterCol.setMinWidth(DEFAULT_MIN_WIDTH);
        patientPerCenterCol.setCellValueFactory(new PropertyValueFactory<>("center"));

        TableColumn<PatientCenter, Integer> patientPerCenterCol2 = new TableColumn<>("Anzahl der Patienten");
        patientPerCenterCol2.setMinWidth(MIN_WIDTH);
        patientPerCenterCol2.setCellValueFactory(new PropertyValueFactory<>("count"));

        patientPerCenterTable.setItems(patientPerCenterData);
        patientPerCenterTable.getColumns().addAll(patientPerCenterCol, patientPerCenterCol2);

        VBox patientPerCenterVb = new VBox(numberPatientPerCenterTableLabel, patientPerCenterTable);
        patientPerCenterVb.setSpacing(FONT);
        patientPerCenterVb.setPadding(new Insets(50, INSETS_VALUE, INSETS_VALUE, FONT));

        VBox randAddVb = new VBox(hbAddPatientRand, hbAddCenterRand, hbAddGroupRand, hbAddDateRand, addRandBtn);
        randAddVb.setSpacing(INSETS_VALUE);

        Label randWeekTableLabel = new Label(FILTER_LABEL);
        Text randTableText = new Text(RANDOM_WEEK_ALL_WEEK_OVERVIEW_LABEL);
        randTableText.setFont(new Font(FONT_NAME, FONT));

        ObservableList<String> randListOpt = FXCollections.observableArrayList(
                RANDOM_WEEK_ALL_WEEK_OVERVIEW_LABEL,
                String.format(RANDOM_WEEK_OVERVIEW_OPTION, 1),
                String.format(RANDOM_WEEK_OVERVIEW_OPTION, 2),
                String.format(RANDOM_WEEK_LAND_OVERVIEW_OPTION, GERMANY, 1),
                String.format(RANDOM_WEEK_LAND_OVERVIEW_OPTION, ENGLAND, 1),
                String.format(RANDOM_WEEK_LAND_OVERVIEW_OPTION, GERMANY, 2),
                String.format(RANDOM_WEEK_LAND_OVERVIEW_OPTION, ENGLAND, 2));

        ComboBox<String> cbRandWeek = new ComboBox<>(randListOpt);
        cbRandWeek.setPromptText(VIEW_ANOTHER_RANDOM_WEEK_PROMPT_TXT);
        cbRandWeek.setOnAction(event -> {
            String choice = cbRandWeek.getValue();
            List<PatientCenter> patientPerCenter1;
            ObservableList<PatientCenter> patientPerCenterData1;
            switch (choice) {
                case RANDOM_WEEK_ONE_OVERVIEW_OPTION:
                    week = ONE_VALUE;
                    patientPerCenter1 = trialService.findNumberOfPatientPerCenterByWeek(ONE_VALUE);
                    patientPerCenterData1 = FXCollections.observableArrayList(patientPerCenter1);
                    patientPerCenterTable.setItems(patientPerCenterData1);
                    randWeekItems = trialService.findAllByWeek(ONE_VALUE);
                    randWeekData = FXCollections.observableArrayList(randWeekItems);
                    randTableText.setText(String.format(RANDOM_WEEK_OVERVIEW_TITLE, ONE_VALUE));
                    numberPatientPerCenterTableLabel.setText(String.format(RANDOM_WEEK_OVERVIEW_LABEL, ONE_VALUE));
                    randWeekTable.setItems(randWeekData);
                    break;
                case RANDOM_WEEK_TWO_OVERVIEW_OPTION:
                    week = TWO_VALUE;
                    patientPerCenter1 = trialService.findNumberOfPatientPerCenterByWeek(TWO_VALUE);
                    patientPerCenterData1 = FXCollections.observableArrayList(patientPerCenter1);
                    patientPerCenterTable.setItems(patientPerCenterData1);
                    randWeekItems = trialService.findAllByWeek(TWO_VALUE);
                    randWeekData = FXCollections.observableArrayList(randWeekItems);
                    randTableText.setText(String.format(RANDOM_WEEK_OVERVIEW_TITLE, TWO_VALUE));
                    numberPatientPerCenterTableLabel.setText(String.format(RANDOM_WEEK_OVERVIEW_LABEL, TWO_VALUE));
                    randWeekTable.setItems(randWeekData);
                    break;
                case RANDOM_WEEK_ONE_GERMANY_OVERVIEW_OPTION:
                    week = ONE_VALUE;
                    patientPerCenter1 = trialService.findNumberPatientPerCenterByLandByWeek(Land.D, ONE_VALUE);
                    patientPerCenterData1 = FXCollections.observableArrayList(patientPerCenter1);
                    patientPerCenterTable.setItems(patientPerCenterData1);
                    randWeekItems = trialService.findAllByWeekAndLand(ONE_VALUE, Land.D);
                    randWeekData = FXCollections.observableArrayList(randWeekItems);
                    randTableText.setText(String.format(RANDOM_WEEK_OVERVIEW_TITLE, ONE_VALUE));
                    numberPatientPerCenterTableLabel.setText(String.format(NUMBER_PATIENT_PER_CENTER_GERMANY_WEEK_OVERVIEW_LABEL, ONE_VALUE));
                    randWeekTable.setItems(randWeekData);
                    break;
                case RANDOM_WEEK_TWO_GERMANY_OVERVIEW_OPTION:
                    week = TWO_VALUE;
                    patientPerCenter1 = trialService.findNumberPatientPerCenterByLandByWeek(Land.D, TWO_VALUE);
                    patientPerCenterData1 = FXCollections.observableArrayList(patientPerCenter1);
                    patientPerCenterTable.setItems(patientPerCenterData1);
                    randWeekItems = trialService.findAllByWeekAndLand(TWO_VALUE, Land.D);
                    randWeekData = FXCollections.observableArrayList(randWeekItems);
                    randTableText.setText(String.format(RANDOM_WEEK_OVERVIEW_TITLE, TWO_VALUE));
                    numberPatientPerCenterTableLabel.setText(String.format(NUMBER_PATIENT_PER_CENTER_GERMANY_WEEK_OVERVIEW_LABEL, TWO_VALUE));
                    randWeekTable.setItems(randWeekData);
                    break;
                case RANDOM_WEEK_ONE_ENGLAND_OVERVIEW_OPTION:
                    week = ONE_VALUE;
                    patientPerCenter1 = trialService.findNumberPatientPerCenterByLandByWeek(Land.GB, ONE_VALUE);
                    patientPerCenterData1 = FXCollections.observableArrayList(patientPerCenter1);
                    patientPerCenterTable.setItems(patientPerCenterData1);
                    randWeekItems = trialService.findAllByWeekAndLand(ONE_VALUE, Land.GB);
                    randWeekData = FXCollections.observableArrayList(randWeekItems);
                    randTableText.setText(String.format(RANDOM_WEEK_OVERVIEW_TITLE, ONE_VALUE));
                    numberPatientPerCenterTableLabel.setText(String.format(NUMBER_PATIENT_PER_CENTER_ENGLAND_WEEK_OVERVIEW_LABEL, ONE_VALUE));
                    randWeekTable.setItems(randWeekData);
                    break;
                case RANDOM_WEEK_TWO_ENGLAND_OVERVIEW_OPTION:
                    week = TWO_VALUE;
                    patientPerCenter1 = trialService.findNumberPatientPerCenterByLandByWeek(Land.GB, week);
                    patientPerCenterData1 = FXCollections.observableArrayList(patientPerCenter1);
                    patientPerCenterTable.setItems(patientPerCenterData1);
                    randWeekItems = trialService.findAllByWeekAndLand(week, Land.GB);
                    randWeekData = FXCollections.observableArrayList(randWeekItems);
                    randTableText.setText(String.format(RANDOM_WEEK_OVERVIEW_TITLE, TWO_VALUE));
                    numberPatientPerCenterTableLabel.setText(String.format(NUMBER_PATIENT_PER_CENTER_ENGLAND_WEEK_OVERVIEW_LABEL, TWO_VALUE));
                    randWeekTable.setItems(randWeekData);
                    break;
                default:
                    patientPerCenter1 = trialService.findNumberOfPatientPerCenterByAllWeek();
                    patientPerCenterData1 = FXCollections.observableArrayList(patientPerCenter1);
                    patientPerCenterTable.setItems(patientPerCenterData1);
                    randWeekItems = trialService.findAllRandomWeek();
                    randWeekData = FXCollections.observableArrayList(randWeekItems);
                    randTableText.setText(RANDOM_WEEK_ALL_WEEK_OVERVIEW_LABEL);
                    numberPatientPerCenterTableLabel.setText(NUMBER_PATIENT_PER_CENTER_ALL_WEEK_OVERVIEW_LABEL);
                    randWeekTable.setItems(randWeekData);
                    break;
            }
        });

        HBox hbRand = new HBox(randWeekTableLabel, cbRandWeek);
        hbRand.setSpacing(INSETS_VALUE);
        hbRand.setPadding(new Insets(SPACING_DEFAULT_VALUE, INSETS_MIN_VALUE, INSETS_MIN_VALUE, PADDING_BOTTOM_VALUE));

        VBox vbRand = new VBox(randTableText, hbRand, randWeekTable);
        vbRand.setSpacing(INSETS_VALUE);
        vbRand.setPadding(new Insets(INSETS_VALUE));
        randAddVb.setVisible(false);
        final HBox hbox = new HBox(vbRand, patientPerCenterVb, randAddVb);
        hbox.setSpacing(INSETS_VALUE);
        hbox.setPadding(new Insets(INSETS_VALUE, INSETS_VALUE, INSETS_VALUE, INSETS_VALUE));
        return hbox;
    }

    /**
     * @return HBox
     */
    private HBox createConsentTable() {

        consentTable = new TableView<>();
        consentTable.setEditable(true);
        consentList = trialService.findAllInformedConsent();
        consentData = FXCollections.observableArrayList(consentList);
        TableColumn<InformedConsent, Integer> patientIDCol = new TableColumn<>("Patient ID");
        patientIDCol.setMinWidth(DEFAULT_MIN_WIDTH);
        patientIDCol.setCellValueFactory(new PropertyValueFactory<>("patientenID"));

        TableColumn<InformedConsent, Integer> centerCol = new TableColumn<>("Zentrum");
        centerCol.setMinWidth(DEFAULT_MIN_WIDTH);
        centerCol.setCellValueFactory(new PropertyValueFactory<>("zentrumID"));

        TableColumn<InformedConsent, String> consentCol = new TableColumn<>("Einwilligung erteilt");
        consentCol.setMinWidth(MIN_WIDTH);
        consentCol.setCellValueFactory(new PropertyValueFactory<>("einwilligung"));
        consentCol.setCellFactory(TextFieldTableCell.forTableColumn());
        consentCol.setOnEditCommit(event -> event.getTableView().getItems().get(event.getTablePosition().getRow())
                .setEinwilligung(event.getNewValue().toUpperCase()));

        TableColumn<InformedConsent, String> dateCol = new TableColumn<>("Datum der Einwilligung");
        dateCol.setMinWidth(MIN_WIDTH);
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateCol.setCellFactory(TextFieldTableCell.forTableColumn());
        dateCol.setOnEditCommit(event -> event.getTableView().getItems().get(event.getTablePosition().getRow())
                .setDate(event.getNewValue()));

        consentTable.setItems(consentData);
        consentTable.getColumns().addAll(patientIDCol, centerCol, consentCol, dateCol);
        Text consentFormText = new Text(CONSENT_FORM_TXT);
        consentFormText.setFont(new Font(FONT_NAME, FONT));
        Button deleteConsentBtn = new Button(CONSENT_DELETE_BTN_LABEL);
        deleteConsentBtn.setVisible(true);
        deleteConsentBtn.setOnAction(event -> {
            InformedConsent selectedConsent = consentTable.getSelectionModel().getSelectedItem();
            // deleteConsentBtn.setVisible(selectedConsent != null ? true : false);
            consentTable.getItems().remove(selectedConsent);
        });

        // Adding new Item for informed consent
        centerIds = trialService.findAllCenterIds();
        Collections.sort(centerIds);
        centerIdData = FXCollections.observableArrayList(centerIds);
        Label centerText = new Label("Zentrum: ");
        final ComboBox<Integer> cbCenter = new ComboBox<>(centerIdData);
        cbCenter.setMinWidth(consentTable.getColumns().get(1).getPrefWidth());
        cbCenter.setPromptText(CHOICE_PROMPT_TEXT);
        cbCenter.setOnAction(event -> chosenCenter = cbCenter.getValue() > INSETS_MIN_VALUE ? cbCenter.getValue() : VALUE_MISSING);

        HBox hbAddCenter = new HBox(centerText, cbCenter);
        hbAddCenter.setSpacing(SPACING_MAX_VALUE);
        hbAddCenter.setPadding(new Insets(INSETS_VALUE));

        Label consentText = new Label("Einwilligung: ");
        ObservableList<String> options = FXCollections.observableArrayList(YES.toUpperCase(), NO.toUpperCase());
        final ComboBox<String> cbConsent = new ComboBox<>(options);
        cbConsent.setValue(CHOICE_PROMPT_TEXT);
        cbConsent.setMinWidth(consentTable.getColumns().get(2).getPrefWidth());

        cbConsent.setOnAction(arg0 -> selConsent = cbConsent.getValue().toLowerCase());

        HBox hbAddConsent = new HBox(consentText, cbConsent);
        hbAddConsent.setSpacing(INSETS_VALUE);
        hbAddConsent.setPadding(new Insets(INSETS_VALUE));

        Label dateText = new Label(DATE_LABEL);
        final DatePicker consentDate = new DatePicker();
        consentDate.setMinWidth(consentTable.getColumns().get(3).getPrefWidth());
        consentDate.setPromptText(DATE_FORMAT_TXT);
        HBox hbAddDate = new HBox(dateText, consentDate);
        hbAddDate.setSpacing(40);
        hbAddDate.setPadding(new Insets(INSETS_VALUE));

        final Button addConsentBtn = new Button(SAVE_BTN_LABEL);

        addConsentBtn.setOnAction(event -> {

            patientIds = trialService.findAllPatientID();

            Collections.sort(patientIds);

            InformedConsent newInformedConsent = new InformedConsent(patientIds.get(patientIds.size() - 1) + 1,
                    chosenCenter, selConsent == null ? "nan" : selConsent,
                    consentDate.getValue() != null ? consentDate.getValue().toString() : "NaT");
            consentData.add(newInformedConsent);
            trialService.update(newInformedConsent);
        });

        final VBox vbConsent = new VBox(consentFormText, hbAddCenter, hbAddConsent, hbAddDate, addConsentBtn,
                deleteConsentBtn);
        vbConsent.setSpacing(INSETS_VALUE);
        vbConsent.setPadding(new Insets(50, INSETS_VALUE, INSETS_VALUE, SPACING_MAX_VALUE));

        final HBox hbConsentTable = new HBox();
        hbConsentTable.setSpacing(INSETS_VALUE);

        Text consentTableLabel = new Text(CONSENT_TABLE_LABEL);
        consentTableLabel.setFont(new Font(FONT_NAME, FONT));
        // Filter Consent Table
        Label filterConsent = new Label(FILTER_LABEL);
        ObservableList<String> consentFilterOptions = FXCollections.observableArrayList(ALL_CONSENT_OPTION,
                MISSING_CONSENT_OVERVIEW_OPTION,
                INCOMPLETE_CONSENT_OVERVIEW_OPTION,
                INFORMED_CONSENT_AFTER_RANDOMIZATION_OVERVIEW_OPTION,
                INFORMED_CONSENT_OVERVIEW_OPTION,
                DECLINED_CONSENT_OVERVIEW_OPTION);

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
                    missingList = trialService.findAllInformedConsent(Consent.MISSING);
                    missingConsentData = FXCollections.observableArrayList(missingList);
                    consentTable.setItems(missingConsentData);
                    vbConsent.setVisible(false);
                    break;
                case INCOMPLETE_CONSENT_OVERVIEW_OPTION:
                    uncompletedList = trialService.findAllInformedConsent(Consent.INCOMPLETE);
                    incompleteConsentData = FXCollections.observableArrayList(uncompletedList);
                    consentTable.setItems(incompleteConsentData);
                    vbConsent.setVisible(false);
                    break;
                case INFORMED_CONSENT_AFTER_RANDOMIZATION_OVERVIEW_OPTION:
                    lateConsentList = trialService.findAllInformedConsent(Consent.LATE);
                    lateConsentData = FXCollections.observableArrayList(lateConsentList);
                    consentTable.setItems(lateConsentData);
                    vbConsent.setVisible(false);
                    break;

                case INFORMED_CONSENT_OVERVIEW_OPTION:
                    consentList = trialService.findAllInformedConsent(true);
                    consentData = FXCollections.observableArrayList(consentList);
                    consentTable.setItems(consentData);
                    vbConsent.setVisible(true);
                    break;

                case DECLINED_CONSENT_OVERVIEW_OPTION:
                    consentList = trialService.findAllInformedConsent(false);
                    consentData = FXCollections.observableArrayList(consentList);
                    consentTable.setItems(consentData);
                    vbConsent.setVisible(true);
                    break;

                default:
                    consentList = trialService.findAllInformedConsent();
                    consentData = FXCollections.observableArrayList(consentList);
                    consentTable.setItems(consentData);
                    vbConsent.setVisible(true);
                    break;
            }
        });

        HBox hbConsentFilter = new HBox(filterConsent, cbConsentFilter);
        hbConsentFilter.setSpacing(INSETS_VALUE);
        hbConsentFilter.setPadding(new Insets(SPACING_DEFAULT_VALUE, INSETS_MIN_VALUE, INSETS_MIN_VALUE, PADDING_BOTTOM_VALUE));

        VBox vbox = new VBox(consentTableLabel, hbConsentFilter, consentTable);
        vbox.setSpacing(SPACING_DEFAULT_VALUE);

        hbConsentTable.setPadding(new Insets(INSETS_VALUE, INSETS_VALUE, INSETS_VALUE, INSETS_VALUE));
        hbConsentTable.getChildren().addAll(vbox, vbConsent);
        return hbConsentTable;
    }

    /**
     * @return HBox
     */
    private HBox createCenterTable() {
        centerTable = new TableView<>();
        centerTable.setEditable(true);
        centerList = trialService.findAllCenter();

        centerList.sort((o1, o2) -> {
            if (o1.getZentrumID() > o2.getZentrumID()) {
                return 1;
            }
            return INSETS_MIN_VALUE;
        });

        data = FXCollections.observableArrayList(centerList);

        TableColumn<Zentrum, String> monitorCol = new TableColumn<>("Monitor");
        monitorCol.setMinWidth(120);

        monitorCol.setCellValueFactory(new PropertyValueFactory<>("monitor"));
        monitorCol.setCellFactory(TextFieldTableCell.forTableColumn());
        monitorCol.setOnEditCommit(event -> event.getTableView().getItems().get(event.getTablePosition().getRow())
                .setMonitor(event.getNewValue()));

        TableColumn<Zentrum, String> prueferCol = new TableColumn<>("Prüfer");
        prueferCol.setMinWidth(DEFAULT_MIN_WIDTH);
        prueferCol.setCellValueFactory(new PropertyValueFactory<>("pruefer"));
        prueferCol.setCellFactory(TextFieldTableCell.forTableColumn());
        prueferCol.setOnEditCommit(event -> event.getTableView().getItems().get(event.getTablePosition().getRow())
                .setPruefer(event.getNewValue()));

        TableColumn<Zentrum, String> ortCol = new TableColumn<>("Ort");
        ortCol.setMinWidth(DEFAULT_MIN_WIDTH);
        ortCol.setCellValueFactory(new PropertyValueFactory<>("ort"));
        ortCol.setCellFactory(TextFieldTableCell.forTableColumn());
        ortCol.setOnEditCommit(event -> event.getTableView().getItems().get(event.getTablePosition().getRow())
                .setOrt(event.getNewValue()));

        TableColumn<Zentrum, String> landCol = new TableColumn<>("Land");
        landCol.setMinWidth(DEFAULT_MIN_WIDTH);
        landCol.setCellValueFactory(new PropertyValueFactory<>("land"));
        landCol.setCellFactory(TextFieldTableCell.forTableColumn());
        landCol.setOnEditCommit(event -> event.getTableView().getItems().get(event.getTablePosition().getRow())
                .setLand(event.getNewValue()));

        TableColumn<Zentrum, String> zentrumIDCol = new TableColumn<>("Zentrum ID");
        zentrumIDCol.setMinWidth(DEFAULT_MIN_WIDTH);
        zentrumIDCol.setCellValueFactory(new PropertyValueFactory<>("zentrumID"));

        centerTable.setItems(data);
        centerTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        centerTable.getColumns().addAll(zentrumIDCol, landCol, ortCol, prueferCol, monitorCol);

        // Delete center items from table (not from database)
        Button deleteCenterBtn = new Button(DELETE_BTN_LABEL);
        deleteCenterBtn.setOnAction(event -> {

            Zentrum selectedCenter = centerTable.getSelectionModel().getSelectedItem();
            centerTable.getItems().remove(selectedCenter);
        });

        Text centerTableLabel = new Text(CENTER_OVERVIEW_LABEL);

        // Filter Consent Table
        Label filterCenter = new Label(FILTER_LABEL);
        ObservableList<String> centerFilterOptions = FXCollections.observableArrayList(
                CENTER_OVERVIEW_OPTION,
                GERMAN_CENTER_OVERVIEW_OPTION,
                BRITISCH_CENTER_OVERVIEW_OPTION);

        final ComboBox<String> cbCenterFilter = new ComboBox<>(centerFilterOptions);
        cbCenterFilter.setPromptText(CONSENT_OVERVIEW_FILTER_PROMPT_TXT);
        cbCenterFilter.setOnAction(event -> {

            String choice = cbCenterFilter.getValue();
            List<Zentrum> centerListLand;
            ObservableList<Zentrum> centerListData;

            switch (choice) {

                case GERMAN_CENTER_OVERVIEW_OPTION:
                    centerListLand = trialService.findAllCenter(Land.D);
                    centerTableLabel.setText(GERMAN_CENTER_OVERVIEW_LABEL);
                    centerListData = FXCollections.observableArrayList(centerListLand);
                    centerTable.setItems(centerListData);
                    break;

                case BRITISCH_CENTER_OVERVIEW_OPTION:
                    centerListLand = trialService.findAllCenter(Land.GB);
                    centerTableLabel.setText(BRITISCH_CENTER_OVERVIEW_LABEL);
                    centerListData = FXCollections.observableArrayList(centerListLand);
                    centerTable.setItems(centerListData);
                    break;

                default:
                    centerList = trialService.findAllCenter();
                    data = FXCollections.observableArrayList(centerList);
                    centerTableLabel.setText(CENTER_OVERVIEW_LABEL);
                    centerTable.setItems(data);
                    break;
            }
        });

        HBox hbCenterFilter = new HBox(filterCenter, cbCenterFilter);
        hbCenterFilter.setSpacing(INSETS_VALUE);
        hbCenterFilter.setPadding(new Insets(SPACING_DEFAULT_VALUE, INSETS_MIN_VALUE, INSETS_MIN_VALUE, 250));

        // Adding new center functionality
        Text centerFormText = new Text(CENTER_FORM_TEXT);
        centerFormText.setFont(new Font(FONT_NAME, FONT));
        final TextField addMonitorName = new TextField();
        final TextField addPrueferName = new TextField();
        final TextField addOrt = new TextField();

        Label monitorText = new Label("Monitor: ");
        addMonitorName.setPromptText("Monitor Name");
        addMonitorName.setMinWidth(MIN_WIDTH);
        HBox hbMonitor = new HBox(monitorText, addMonitorName);
        hbMonitor.setSpacing(SPACING_DEFAULT_VALUE);
        hbMonitor.setPadding(new Insets(INSETS_VALUE, INSETS_VALUE, INSETS_VALUE, INSETS_VALUE));

        Label prueferText = new Label("Prüfer: ");
        addPrueferName.setMinWidth(MIN_WIDTH);
        addPrueferName.setPromptText("Prüfer Name");
        HBox hbPruefer = new HBox(prueferText, addPrueferName);
        hbPruefer.setSpacing(SPACING_MIN_VALUE);
        hbPruefer.setPadding(new Insets(INSETS_VALUE, INSETS_VALUE, INSETS_VALUE, INSETS_VALUE));

        Label ortText = new Label("Ort: ");
        addOrt.setMinWidth(MIN_WIDTH);
        addOrt.setPromptText("Ort");
        HBox hbOrt = new HBox(ortText, addOrt);
        hbOrt.setSpacing(SPACING_MAX_VALUE);
        hbOrt.setPadding(new Insets(INSETS_VALUE, INSETS_VALUE, INSETS_VALUE, INSETS_VALUE));

        Label landText = new Label("Land: ");
        landNames = FXCollections.observableArrayList(GERMANY, ENGLAND);
        cbLand = new ComboBox<>(landNames);
        cbLand.setValue(COUNTRY_CHOICE_BTN_LABEL);
        cbLand.setMinWidth(DEFAULT_MIN_WIDTH);
        cbLand.setPadding(new Insets(INSETS_MIN_VALUE, INSETS_MIN_VALUE, INSETS_MIN_VALUE, FONT));

        cbLand.setOnAction(arg0 -> selectedCountry = cbLand.getValue() != null ? cbLand.getValue() : null);

        final Text centerInputValid = new Text();
        centerInputValid.setId("centerInputValid");
        HBox hbLand = new HBox(landText, cbLand);
        hbLand.setSpacing(H_BOX_SPACING_VALUE);
        hbLand.setPadding(new Insets(INSETS_VALUE));
        final Button addCenterBtn = new Button(SAVE_BTN_LABEL);
        addCenterBtn.setOnAction(event -> {

            if (addMonitorName.getText() != null && addPrueferName.getText() != null && addOrt.getText() != null && selectedCountry != null) {
                Zentrum neuCenter = new Zentrum(addMonitorName.getText(), addPrueferName.getText(), addOrt.getText(),
                        selectedCountry, trialService.nextId(GERMANY.equals(selectedCountry) ? Land.D : Land.GB));
                data.add(neuCenter);
                trialService.update(neuCenter);
                addMonitorName.clear();
                addPrueferName.clear();
                addOrt.clear();
                centerInputValid.setText("");
            } else {
                centerInputValid.setText(INPUT_VALIDATION_ERROR_MSG);
            }
        });

        final VBox vbAddCenter = new VBox(centerFormText, hbMonitor, hbPruefer, hbLand, hbOrt, centerInputValid, addCenterBtn,
                deleteCenterBtn);
        vbAddCenter.setSpacing(SPACING_DEFAULT_VALUE);
        vbAddCenter.setPadding(new Insets(80, INSETS_VALUE, INSETS_VALUE, SPACING_MAX_VALUE));

        centerTableLabel.setFont(new Font(FONT_NAME, FONT));
        final VBox vbCenter = new VBox(centerTableLabel, hbCenterFilter, centerTable);
        vbCenter.setSpacing(SPACING_DEFAULT_VALUE);

        final HBox hbox = new HBox();
        hbox.setSpacing(INSETS_VALUE);
        hbox.setPadding(new Insets(INSETS_VALUE, INSETS_VALUE, INSETS_VALUE, INSETS_VALUE));
        hbox.getChildren().addAll(vbCenter, vbAddCenter);
        return hbox;
    }
}
