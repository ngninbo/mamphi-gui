package de.fhdo.master.mi.sms.project.mamphi.gui.app;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import de.fhdo.master.mi.sms.project.mamphi.model.User;
import de.fhdo.master.mi.sms.project.mamphi.model.Consent;
import de.fhdo.master.mi.sms.project.mamphi.model.InformedConsent;
import de.fhdo.master.mi.sms.project.mamphi.model.Land;
import de.fhdo.master.mi.sms.project.mamphi.model.MonitorVisite;
import de.fhdo.master.mi.sms.project.mamphi.model.PatientCenter;
import de.fhdo.master.mi.sms.project.mamphi.model.RandomizationWeek;
import de.fhdo.master.mi.sms.project.mamphi.model.Zentrum;
import de.fhdo.master.mi.sms.project.mamphi.repository.FetchData;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Main extends Application {
	
	private static final int valueMissing = -999;
	private static String selectedLand, selConsent, choosenGroup;
	private static int choosenCenter, choosenPatient, week;
	private static FetchData fetcher = new FetchData();
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
	private ArrayList<User> users = new ArrayList<>();
	private List<RandomizationWeek> randWeekItems;
	private ObservableList<RandomizationWeek> randWeekData;
	private TableView<RandomizationWeek> randWeekTable;
	private VBox vbRand = new VBox();
	private static FlowPane root;

	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("Mamphi Administration GUI");

			User demo = new User("demo", "demo");
			users.add(demo);
			User admin = new User("admin", "admin");
			users.add(admin);
			// Set Icon
			// Create an Image
			InputStream stream = getClass().getResourceAsStream("mamphi.png");
			Image icon = new Image(stream);
			primaryStage.getIcons().add(icon);

			// Define grid pane for login
			GridPane grid = new GridPane();
			grid.setAlignment(Pos.CENTER);
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(25, 25, 25, 25));
			Text scenetitle = new Text("Welcome");
			scenetitle.setId("welcome-text");
			// scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
			grid.add(scenetitle, 0, 0, 2, 1);

			Label userName = new Label("User Name:");
			grid.add(userName, 0, 1);

			TextField userTextField = new TextField();
			userTextField.setPromptText("Username");
			grid.add(userTextField, 1, 1);

			Label pw = new Label("Password:");
			grid.add(pw, 0, 2);

			PasswordField pwBox = new PasswordField();
			pwBox.setPromptText("Password");
			grid.add(pwBox, 1, 2);
			// grid.setGridLinesVisible(true);

			Button btn = new Button("Sign in");
			btn.setTooltip(new Tooltip("Einloggen und Studie verwalten"));
			btn.setId("sign-in");
			HBox hbBtn = new HBox(10);
			hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
			hbBtn.getChildren().add(btn);
			grid.add(hbBtn, 1, 4);

			final Text actiontarget = new Text();
			grid.add(actiontarget, 1, 6);
			actiontarget.setId("actiontarget");

			final Button consentBtn = new Button("Patientenliste");
			consentBtn.setTooltip(new Tooltip("Einwilligungen verwalten"));
			consentBtn.setFont(new Font("Arial", 20));
			final Button centerBtn = new Button("Zentrumsliste");
			centerBtn.setTooltip(new Tooltip("Zentren verwalten"));
			centerBtn.setFont(new Font("Arial", 20));
			final Button randomWeekBtn = new Button("Wochenliste");
			randomWeekBtn.setTooltip(new Tooltip("Wochenliste verwalten"));
			randomWeekBtn.setFont(new Font("Arial", 20));
			final Button monitoringBtn = new Button("Monitoring Plan");
			monitoringBtn.setTooltip(new Tooltip("Monitorplan anzeigen und verwalten"));
			monitoringBtn.setFont(new Font("Arial", 20));
			final Button logoutBtn = new Button("Sign out");
			logoutBtn.setTooltip(new Tooltip("Ausloggen"));
			logoutBtn.setFont(new Font("Arial", 20));

			HBox btnMenu = new HBox();
			btnMenu.getChildren().addAll(centerBtn, consentBtn, randomWeekBtn, monitoringBtn, logoutBtn);
			// btnMenu.setSpacing(5);
			btnMenu.setPadding(new Insets(10, 10, 0, 10));

			root = new FlowPane(10, 10);

			Scene loginScene = new Scene(grid, 400, 400);
			loginScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			Scene mainScene = new Scene(root, 999, 600);
			mainScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			// Add event handler for login button in the login view
			btn.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {

					User loginUser = new User(userTextField.getText(), pwBox.getText());

					if(users.contains(loginUser)) {

						primaryStage.setScene(mainScene);
						actiontarget.setText("");
						primaryStage.show();

					} else {
						actiontarget.setText("Sign in data incorrect");
					}

					userTextField.clear();
					pwBox.clear();
				}
			});

			// Add Event Handler for log out button
			logoutBtn.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					primaryStage.setScene(loginScene);
					primaryStage.show();
				}
			});

			// Center Table
			final HBox hbCenterTable = createCenterTable();

			// Add Event listener for each button from button menu
			centerBtn.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {

					root = new FlowPane(10, 10);
					// Center Table
					final HBox hbCenterTable = createCenterTable();
					// Add the created horizontal box to the root pane
					root.getChildren().addAll(btnMenu, hbCenterTable);

					mainScene.setRoot(root);
				}
			});

			consentBtn.setOnAction(ActionEvent -> {

				root = new FlowPane(10, 10);
				final HBox hbConsentTable = createConsentTable();

				// Add the created horizontal box to the root pane
				root.getChildren().addAll(btnMenu, hbConsentTable);

				mainScene.setRoot(root);
			});

			randomWeekBtn.setOnAction(ActionEvent -> {

				root = new FlowPane(10, 10);
				final HBox hbox = createRamdomizationPane();

				// Add the created horizontal box to the root pane
				root.getChildren().addAll(btnMenu, hbox);
				mainScene.setRoot(root);
			});

			monitoringBtn.setOnAction(new EventHandler<ActionEvent>() {

				@SuppressWarnings("unchecked")
				@Override
				public void handle(ActionEvent event) {
					root = new FlowPane(10, 10);
					Text monitorplanLabel = new Text("Plan wann welches Monitor welches Zentrum besuchen soll.");
					monitorplanLabel.setFont(new Font("Arial", 20));
					TableView<MonitorVisite> monitorplan = new TableView<MonitorVisite>();
					monitorplan.setEditable(false);
					List<MonitorVisite> visiteItems = fetcher.fetchMonitorVisites();
					ObservableList<MonitorVisite> visiteData = FXCollections.observableArrayList(visiteItems);
					TableColumn<MonitorVisite, String> ortCol = new TableColumn<MonitorVisite, String>("Ort");
					TableColumn<MonitorVisite, String> monitorCol = new TableColumn<MonitorVisite, String>("Monitor");
					monitorCol.setMinWidth(100);

					monitorCol.setCellValueFactory(new PropertyValueFactory<MonitorVisite, String>("monitor"));
					monitorCol.setCellFactory(TextFieldTableCell.forTableColumn());
					monitorCol.setOnEditCommit(new EventHandler<CellEditEvent<MonitorVisite, String>>() {

						@Override
						public void handle(CellEditEvent<MonitorVisite, String> event) {
							((Zentrum) event.getTableView().getItems().get(event.getTablePosition().getRow()))
									.setMonitor(event.getNewValue());
						}
					});

					TableColumn<MonitorVisite, String> prueferCol = new TableColumn<MonitorVisite, String>("Prüfer");
					prueferCol.setMinWidth(100);
					prueferCol.setCellValueFactory(new PropertyValueFactory<MonitorVisite, String>("pruefer"));
					prueferCol.setCellFactory(TextFieldTableCell.forTableColumn());
					prueferCol.setOnEditCommit(new EventHandler<CellEditEvent<MonitorVisite, String>>() {

						@Override
						public void handle(CellEditEvent<MonitorVisite, String> event) {
							((Zentrum) event.getTableView().getItems().get(event.getTablePosition().getRow()))
									.setPruefer(event.getNewValue());
						}
					});

					ortCol.setMinWidth(100);
					ortCol.setCellValueFactory(new PropertyValueFactory<MonitorVisite, String>("ort"));
					ortCol.setCellFactory(TextFieldTableCell.forTableColumn());
					ortCol.setOnEditCommit(new EventHandler<CellEditEvent<MonitorVisite, String>>() {

						@Override
						public void handle(CellEditEvent<MonitorVisite, String> event) {
							((MonitorVisite) event.getTableView().getItems().get(event.getTablePosition().getRow()))
									.setOrt(event.getNewValue());
						}
					});

					TableColumn<MonitorVisite, String> landCol = new TableColumn<MonitorVisite, String>("Land");
					landCol.setMinWidth(100);
					landCol.setCellValueFactory(new PropertyValueFactory<MonitorVisite, String>("land"));
					landCol.setCellFactory(TextFieldTableCell.forTableColumn());
					landCol.setOnEditCommit(new EventHandler<CellEditEvent<MonitorVisite, String>>() {

						@Override
						public void handle(CellEditEvent<MonitorVisite, String> event) {
							((MonitorVisite) event.getTableView().getItems().get(event.getTablePosition().getRow()))
									.setLand(event.getNewValue());
						}
					});

					TableColumn<MonitorVisite, String> zentrumIDCol = new TableColumn<MonitorVisite, String>(
							"Zentrum ID");
					zentrumIDCol.setMinWidth(100);
					zentrumIDCol.setCellValueFactory(new PropertyValueFactory<MonitorVisite, String>("zentrum_id"));

					TableColumn<MonitorVisite, Integer> numberPatientCol = new TableColumn<MonitorVisite, Integer>(
							"Anzahl Patienten");
					numberPatientCol.setMinWidth(100);
					numberPatientCol
							.setCellValueFactory(new PropertyValueFactory<MonitorVisite, Integer>("numberOfPatient"));

					TableColumn<MonitorVisite, List<LocalDate>> visitesCol = new TableColumn<MonitorVisite, List<LocalDate>>(
							"Besuch Termine");
					PropertyValueFactory<MonitorVisite, List<LocalDate>> visitesDates = new PropertyValueFactory<MonitorVisite, List<LocalDate>>(
							"visiteDate");
					visitesCol.setCellValueFactory(visitesDates);

					visitesCol.setPrefWidth(380);

					monitorplan.setItems(visiteData);
					monitorplan.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
					monitorplan.getColumns().addAll(zentrumIDCol, landCol, ortCol, prueferCol, monitorCol,
							numberPatientCol, visitesCol);

					// Delete some items
					Button deleteItemsBtn = new Button("Löschen");
					deleteItemsBtn.setOnAction(ActionEvent -> {
						ObservableList<MonitorVisite> selectedVisites = monitorplan.getSelectionModel()
								.getSelectedItems();
						ArrayList<MonitorVisite> rows = new ArrayList<MonitorVisite>(selectedVisites);
						rows.forEach(e -> {
							monitorplan.getItems().remove(e);
						});
					});

					// Adding new items
					Text centerFormText = new Text("Neues Zentrum anlegen: ");
					centerFormText.setFont(new Font("Arial", 20));
					final TextField addMonitorName = new TextField();
					final TextField addPrueferName = new TextField();
					final TextField addOrt = new TextField();

					Label monitorText = new Label("Monitor: ");
					addMonitorName.setPromptText("Monitor Name");
					addMonitorName.setMinWidth(200);
					HBox hbMonitor = new HBox(monitorText, addMonitorName);
					hbMonitor.setSpacing(5);
					hbMonitor.setPadding(new Insets(10, 10, 10, 10));

					Label prueferText = new Label("Prüfer: ");
					addPrueferName.setMinWidth(200);
					addPrueferName.setPromptText("Prüfer Name");
					HBox hbPruefer = new HBox(prueferText, addPrueferName);
					hbPruefer.setSpacing(15);
					hbPruefer.setPadding(new Insets(10, 10, 10, 10));

					Label ortText = new Label("Ort: ");
					addOrt.setMinWidth(200);
					addOrt.setPromptText("Ort");
					HBox hbOrt = new HBox(ortText, addOrt);
					hbOrt.setSpacing(30);
					hbOrt.setPadding(new Insets(10, 10, 10, 10));

					Label landText = new Label("Land: ");
					landNames = FXCollections.observableArrayList("Deutschland", "Großbritanien");
					cbLand = new ComboBox<String>(landNames);
					cbLand.setValue("Land wählen");
					cbLand.setMinWidth(100);
					cbLand.setPadding(new Insets(0, 0, 0, 20));

					cbLand.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent arg0) {

							selectedLand = cbLand.getValue();
						}
					});

					HBox hbLand = new HBox(landText, cbLand);
					hbLand.setSpacing(25);
					hbLand.setPadding(new Insets(10));
					final Button addCenterBtn = new Button("Speichern");
					addCenterBtn.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {

							Zentrum neuZentrum = new Zentrum(addMonitorName.getText(), addPrueferName.getText(),
									addOrt.getText(), selectedLand, data);

							data.add(neuZentrum);
							fetcher.updateZentrum(neuZentrum);
							addMonitorName.clear();
							addPrueferName.clear();
							addOrt.clear();
						}
					});

					final VBox vbCenter = new VBox(centerFormText, hbMonitor, hbPruefer, hbLand, hbOrt, addCenterBtn);
					vbCenter.setSpacing(5);
					vbCenter.setPadding(new Insets(10, 10, 10, 10));
					vbCenter.setVisible(false);
					final HBox hbox = new HBox();
					hbox.setSpacing(10);
					hbox.setPadding(new Insets(10, 10, 10, 10));
					VBox vb = new VBox(monitorplanLabel, monitorplan, deleteItemsBtn);
					vb.setSpacing(10);
					hbox.getChildren().addAll(vb, vbCenter);

					// Add the created horizontal box to the root pane
					root.getChildren().addAll(btnMenu, hbox);
					mainScene.setRoot(root);
				}
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
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private HBox createRamdomizationPane() {

		randWeekTable = new TableView<RandomizationWeek>();
		randWeekTable.setEditable(false);
		randWeekItems = fetcher.fetchAllRandomWeekItems();
		randWeekData = FXCollections.observableArrayList(randWeekItems);

		TableColumn<RandomizationWeek, Integer> patientIDCol = new TableColumn<RandomizationWeek, Integer>(
				"Patient ID");
		patientIDCol.setMinWidth(100);
		patientIDCol.setCellValueFactory(new PropertyValueFactory<RandomizationWeek, Integer>("patient_id"));

		TableColumn<RandomizationWeek, Integer> centerCol = new TableColumn<RandomizationWeek, Integer>("Zentrum");
		centerCol.setMinWidth(100);
		centerCol.setCellValueFactory(new PropertyValueFactory<RandomizationWeek, Integer>("zentrum"));

		TableColumn<RandomizationWeek, String> groupCol = new TableColumn<RandomizationWeek, String>("Behandlungsarm");
		groupCol.setMinWidth(50);
		groupCol.setCellValueFactory(new PropertyValueFactory<RandomizationWeek, String>("behandlungsarm"));
		groupCol.setCellFactory(TextFieldTableCell.forTableColumn());
		groupCol.setOnEditCommit(new EventHandler<CellEditEvent<RandomizationWeek, String>>() {

			@Override
			public void handle(CellEditEvent<RandomizationWeek, String> event) {
				// TODO Auto-generated method stub
				((RandomizationWeek) event.getTableView().getItems().get(event.getTablePosition().getRow()))
						.setBehandlungsarm(event.getNewValue());
			}

		});

		TableColumn<RandomizationWeek, String> dateCol = new TableColumn<RandomizationWeek, String>(
				"Datum der Randomisierung");
		dateCol.setMinWidth(300);
		dateCol.setCellValueFactory(new PropertyValueFactory<RandomizationWeek, String>("date"));
		dateCol.setCellFactory(TextFieldTableCell.forTableColumn());
		dateCol.setOnEditCommit(new EventHandler<CellEditEvent<RandomizationWeek, String>>() {

			@Override
			public void handle(CellEditEvent<RandomizationWeek, String> event) {
				// TODO Auto-generated method stub
				((RandomizationWeek) event.getTableView().getItems().get(event.getTablePosition().getRow()))
						.setDate(event.getNewValue());
			}
		});

		randWeekTable.setItems(randWeekData);
		randWeekTable.getColumns().addAll(patientIDCol, centerCol, groupCol, dateCol);

		// Adding new Items
		Label addPatientLabel = new Label("Patient ID: ");
		patientIds = fetcher.fetchAllPatientenID();
		ObservableList<Integer> patientIdData = FXCollections.observableArrayList(patientIds);

		ComboBox<Integer> cbAddRandPatient = new ComboBox<Integer>(patientIdData);
		cbAddRandPatient.setPromptText("Bitte Wählen");
		cbAddRandPatient.setMinWidth(patientIDCol.getPrefWidth());
		cbAddRandPatient.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				choosenPatient = cbAddRandPatient.getValue();
			}
		});

		HBox hbAddPatientRand = new HBox(addPatientLabel, cbAddRandPatient);
		hbAddPatientRand.setSpacing(10);
		hbAddPatientRand.setPadding(new Insets(10, 10, 10, 10));

		Label addCenterLabel = new Label("Zentrum: ");
		centerIds = fetcher.fetchAllCenterID();
		centerIdData = FXCollections.observableArrayList(centerIds);
		ComboBox<Integer> cbAddCenterRand = new ComboBox<Integer>(centerIdData);
		cbAddCenterRand.setPromptText("Bitte Wählen");
		cbAddCenterRand.setMinWidth(centerCol.getPrefWidth());
		cbAddCenterRand.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				choosenCenter = cbAddCenterRand.getValue();
			}
		});

		HBox hbAddCenterRand = new HBox(addCenterLabel, cbAddCenterRand);
		hbAddCenterRand.setSpacing(10);
		hbAddCenterRand.setPadding(new Insets(10, 10, 10, 10));

		Label addGroupLabel = new Label("Behandlungsarm: ");
		ObservableList<String> groups = FXCollections.observableArrayList("A", "B");
		ComboBox<String> cbAddGroupRand = new ComboBox<String>(groups);
		cbAddGroupRand.setPromptText("Bitte Wählen");
		cbAddGroupRand.setMinWidth(100);

		cbAddGroupRand.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				choosenGroup = cbAddGroupRand.getValue();
			}
		});

		HBox hbAddGroupRand = new HBox(addGroupLabel, cbAddGroupRand);
		hbAddGroupRand.setSpacing(10);
		hbAddGroupRand.setPadding(new Insets(10, 10, 10, 10));

		Label dateRandLabel = new Label("Datum: ");
		final DatePicker addDateRand = new DatePicker();
		addDateRand.setMinWidth(dateCol.getPrefWidth());
		addDateRand.setPromptText("JJJJ-MM-TT");

		HBox hbAddDateRand = new HBox(dateRandLabel, addDateRand);
		hbAddDateRand.setSpacing(10);
		hbAddDateRand.setPadding(new Insets(10, 10, 10, 10));

		Button addRandBtn = new Button("Eintrag Speichern");
		addRandBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				RandomizationWeek rand = new RandomizationWeek(choosenPatient, choosenCenter, choosenGroup,
						addDateRand.getValue().toString());
				fetcher.updateRandomiationTable(rand, week);
			}
		});

		Text numberPatientPerCenterTableLabel = new Text("Anzahl Patient pro Zentrum \nalle Wochen");
		numberPatientPerCenterTableLabel.setFont(new Font("Arial", 20));
		List<PatientCenter> patientPerCenter = fetcher.fetchNumberOfPatientPerCenterByAllWeek();
		ObservableList<PatientCenter> patientPerCenterData = FXCollections.observableArrayList(patientPerCenter);
		TableView<PatientCenter> patientPerCenterTable = new TableView<PatientCenter>();
		patientPerCenterTable.setEditable(false);
		TableColumn<PatientCenter, String> patientPerCenterCol = new TableColumn<PatientCenter, String>("Zentrum");
		patientPerCenterCol.setMinWidth(100);
		patientPerCenterCol.setCellValueFactory(new PropertyValueFactory<PatientCenter, String>("center"));

		TableColumn<PatientCenter, Integer> patientPerCenterCol2 = new TableColumn<PatientCenter, Integer>(
				"Anzahl der Patienten");
		patientPerCenterCol2.setMinWidth(200);
		patientPerCenterCol2.setCellValueFactory(new PropertyValueFactory<PatientCenter, Integer>("count"));

		patientPerCenterTable.setItems(patientPerCenterData);
		patientPerCenterTable.getColumns().addAll(patientPerCenterCol, patientPerCenterCol2);

		VBox patientPerCenterVb = new VBox(numberPatientPerCenterTableLabel, patientPerCenterTable);
		patientPerCenterVb.setSpacing(20);
		patientPerCenterVb.setPadding(new Insets(50, 10, 10, 20));

		VBox randAddVb = new VBox(hbAddPatientRand, hbAddCenterRand, hbAddGroupRand, hbAddDateRand, addRandBtn);
		randAddVb.setSpacing(10);

		Label randWeekTableLabel = new Label("Filtern: ");
		Text randTableText = new Text("Angabe zur Randomisierung in alle Wochen");
		randTableText.setFont(new Font("Arial", 20));

		ObservableList<String> randListOpt = FXCollections.observableArrayList(
				"Angabe zur Randomisierung in alle Wochen",
				"Angaben zur Randomisierung in der Woche 1 anzeigen",
				"Angaben zur Randomisierung in der Woche 2 anzeigen",
				"Anzahl der Patienten pro Zentrum in Deutschland in der Woche 1",
				"Anzahl der Patienten pro Zentrum in Großbritanien in der Woche 1",
				"Anzahl der Patienten pro Zentrum in Deutschland in der Woche 2",
				"Anzahl der Patienten pro Zentrum in Großbritanien in der Woche 2");

		ComboBox<String> cbRandWeek = new ComboBox<String>(randListOpt);
		cbRandWeek.setPromptText("Andere wochenliche Liste anzeigen lassen");
		cbRandWeek.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				String choice = cbRandWeek.getValue();
				List<PatientCenter> patientPerCenter;
				ObservableList<PatientCenter> patientPerCenterData;
				switch (choice) {
				case "Angaben zur Randomisierung in der Woche 1 anzeigen":
					week = 1;
					patientPerCenter = fetcher.fetchAllNumberOfPatientPerCenterByWeek(week);
					patientPerCenterData = FXCollections.observableArrayList(patientPerCenter);
					patientPerCenterTable.setItems(patientPerCenterData);
					randWeekItems = fetcher.fetchAllRandomizationByWeek(week);
					randWeekData = FXCollections.observableArrayList(randWeekItems);
					randTableText.setText("Angaben zur Randomisierung in der Woche 1");
					numberPatientPerCenterTableLabel.setText("Anzahl Patienten pro Zentrum \nin Woche 1");
					randWeekTable.setItems(randWeekData);
					break;
				case "Angaben zur Randomisierung in der Woche 2 anzeigen":
					week = 2;
					patientPerCenter = fetcher.fetchAllNumberOfPatientPerCenterByWeek(week);
					patientPerCenterData = FXCollections.observableArrayList(patientPerCenter);
					patientPerCenterTable.setItems(patientPerCenterData);
					randWeekItems = fetcher.fetchAllRandomizationByWeek(week);
					randWeekData = FXCollections.observableArrayList(randWeekItems);
					randTableText.setText("Angaben zur Randomisierung in der Woche 2");
					numberPatientPerCenterTableLabel.setText("Anzahl Patienten pro Zentrum \nin Woche 2");
					randWeekTable.setItems(randWeekData);
					break;
				case "Anzahl der Patienten pro Zentrum in Deutschland in der Woche 1":
					week = 1;
					patientPerCenter = fetcher.fetchAllNumberPatientenPerCenterByLandByWeek(Land.D, week);
					patientPerCenterData = FXCollections.observableArrayList(patientPerCenter);
					patientPerCenterTable.setItems(patientPerCenterData);
					randWeekItems = fetcher.fetchAllRandomizationByWeek(week);
					randWeekData = FXCollections.observableArrayList(randWeekItems);
					randTableText.setText("Angaben zur Randomisierung in der Woche 1");
					numberPatientPerCenterTableLabel.setText("Anzahl Patienten pro deutsches Zentrum \nin Woche 1");
					randWeekTable.setItems(randWeekData);
					break;
				case "Anzahl der Patienten pro Zentrum in Deutschland in der Woche 2":
					week = 2;
					patientPerCenter = fetcher.fetchAllNumberPatientenPerCenterByLandByWeek(Land.D, week);
					patientPerCenterData = FXCollections.observableArrayList(patientPerCenter);
					patientPerCenterTable.setItems(patientPerCenterData);
					randWeekItems = fetcher.fetchAllRandomizationByWeek(week);
					randWeekData = FXCollections.observableArrayList(randWeekItems);
					randTableText.setText("Angaben zur Randomisierung in der Woche 2");
					numberPatientPerCenterTableLabel.setText("Anzahl Patienten pro deutsches Zentrum \nin Woche 2");
					randWeekTable.setItems(randWeekData);
					break;
				case "Anzahl der Patienten pro Zentrum in Großbritanien in der Woche 1":
					week = 1;
					patientPerCenter = fetcher.fetchAllNumberPatientenPerCenterByLandByWeek(Land.GB, week);
					patientPerCenterData = FXCollections.observableArrayList(patientPerCenter);
					patientPerCenterTable.setItems(patientPerCenterData);
					randWeekItems = fetcher.fetchAllRandomizationByWeek(week);
					randWeekData = FXCollections.observableArrayList(randWeekItems);
					randTableText.setText("Angaben zur Randomisierung in der Woche 1");
					numberPatientPerCenterTableLabel.setText("Anzahl Patienten pro britisches Zentrum \nin Woche 1");
					randWeekTable.setItems(randWeekData);
					break;
				case "Anzahl der Patienten pro Zentrum in Großbritanien in der Woche 2":
					week = 2;
					patientPerCenter = fetcher.fetchAllNumberPatientenPerCenterByLandByWeek(Land.GB, week);
					patientPerCenterData = FXCollections.observableArrayList(patientPerCenter);
					patientPerCenterTable.setItems(patientPerCenterData);
					randWeekItems = fetcher.fetchAllRandomizationByWeek(week);
					randWeekData = FXCollections.observableArrayList(randWeekItems);
					randTableText.setText("Angaben zur Randomisierung in der Woche 2");
					numberPatientPerCenterTableLabel.setText("Anzahl Patienten pro britisches Zentrum \nin Woche 2");
					randWeekTable.setItems(randWeekData);
					break;
				case "Angabe zur Randomisierung in alle Wochen":
					patientPerCenter = fetcher.fetchNumberOfPatientPerCenterByAllWeek();
					patientPerCenterData = FXCollections.observableArrayList(patientPerCenter);
					patientPerCenterTable.setItems(patientPerCenterData);
					randWeekItems = fetcher.fetchAllRandomWeekItems();
					randWeekData = FXCollections.observableArrayList(randWeekItems);
					randTableText.setText("Angaben zur Randomisierung in alle Wochen");
					numberPatientPerCenterTableLabel.setText("Anzahl Patienten pro Zentrum \nalle Woche");
					randWeekTable.setItems(randWeekData);
					break;
				}
			}
		});

		HBox hbRand = new HBox(randWeekTableLabel, cbRandWeek);
		hbRand.setSpacing(10);
		hbRand.setPadding(new Insets(5, 0, 0, 150));

		vbRand = new VBox(randTableText, hbRand, randWeekTable);
		vbRand.setSpacing(10);
		vbRand.setPadding(new Insets(10));
		randAddVb.setVisible(false);
		final HBox hbox = new HBox(vbRand, patientPerCenterVb, randAddVb);
		hbox.setSpacing(10);
		hbox.setPadding(new Insets(10, 10, 10, 10));
		return hbox;
	}

	/**
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private HBox createConsentTable() {

		consentTable = new TableView<InformedConsent>();
		consentTable.setEditable(true);
		consentList = fetcher.fetchAllInformedConsent();
		consentData = FXCollections.observableArrayList(consentList);
		TableColumn<InformedConsent, Integer> patientIDCol = new TableColumn<InformedConsent, Integer>("Patient ID");
		patientIDCol.setMinWidth(100);
		patientIDCol.setCellValueFactory(new PropertyValueFactory<InformedConsent, Integer>("patient_id"));

		TableColumn<InformedConsent, Integer> zentrumCol = new TableColumn<InformedConsent, Integer>("Zentrum");
		zentrumCol.setMinWidth(100);
		zentrumCol.setCellValueFactory(new PropertyValueFactory<InformedConsent, Integer>("zentrum_id"));

		TableColumn<InformedConsent, String> consentCol = new TableColumn<InformedConsent, String>(
				"Einwilligung erteilt");
		consentCol.setMinWidth(200);
		consentCol.setCellValueFactory(new PropertyValueFactory<InformedConsent, String>("einwilligung"));
		consentCol.setCellFactory(TextFieldTableCell.forTableColumn());
		consentCol.setOnEditCommit(new EventHandler<CellEditEvent<InformedConsent, String>>() {

			@Override
			public void handle(CellEditEvent<InformedConsent, String> event) {

				((InformedConsent) event.getTableView().getItems().get(event.getTablePosition().getRow()))
						.setEinwilligung(event.getNewValue().toUpperCase());
			}
		});

		TableColumn<InformedConsent, String> dateCol = new TableColumn<InformedConsent, String>(
				"Datum der Einwilligung");
		dateCol.setMinWidth(200);
		dateCol.setCellValueFactory(new PropertyValueFactory<InformedConsent, String>("date"));
		dateCol.setCellFactory(TextFieldTableCell.forTableColumn());
		dateCol.setOnEditCommit(new EventHandler<CellEditEvent<InformedConsent, String>>() {

			@Override
			public void handle(CellEditEvent<InformedConsent, String> event) {

				((InformedConsent) event.getTableView().getItems().get(event.getTablePosition().getRow()))
						.setDate(event.getNewValue());
			}
		});

		consentTable.setItems(consentData);
		consentTable.getColumns().addAll(patientIDCol, zentrumCol, consentCol, dateCol);
		Text consentFormText = new Text("Neue Einwilligung anlegen:");
		consentFormText.setFont(new Font("Arial", 20));
		Button deleteConsentBtn = new Button("Einwilligung löschen");
		deleteConsentBtn.setVisible(true);
		deleteConsentBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				InformedConsent selectedConsent = consentTable.getSelectionModel().getSelectedItem();
				// deleteConsentBtn.setVisible(selectedConsent != null ? true : false);
				consentTable.getItems().remove(selectedConsent);
			}
		});

		// Adding new Item for informed consent
		centerIds = fetcher.fetchAllCenterID();
		Collections.sort(centerIds);
		centerIdData = FXCollections.observableArrayList(centerIds);
		Label centerText = new Label("Zentrum: ");
		final ComboBox<Integer> cbCenter = new ComboBox<Integer>(centerIdData);
		cbCenter.setMinWidth(consentTable.getColumns().get(1).getPrefWidth());
		cbCenter.setPromptText("Bitte wählen");
		cbCenter.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				choosenCenter = cbCenter.getValue() > 0 ? cbCenter.getValue() : valueMissing;
			}

		});

		HBox hbAddCenter = new HBox(centerText, cbCenter);
		hbAddCenter.setSpacing(30);
		hbAddCenter.setPadding(new Insets(10));

		Label consentText = new Label("Einwilligung: ");
		ObservableList<String> options = FXCollections.observableArrayList("JA", "NEIN");
		final ComboBox<String> cbConsent = new ComboBox<String>(options);
		cbConsent.setValue("Bitte wählen");
		cbConsent.setMinWidth(consentTable.getColumns().get(2).getPrefWidth());

		cbConsent.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				selConsent = cbConsent.getValue().toLowerCase();
			}
		});

		HBox hbAddConsent = new HBox(consentText, cbConsent);
		hbAddConsent.setSpacing(10);
		hbAddConsent.setPadding(new Insets(10));

		Label dateText = new Label("Datum: ");
		final DatePicker consentDate = new DatePicker();
		consentDate.setMinWidth(consentTable.getColumns().get(3).getPrefWidth());
		consentDate.setPromptText("JJJJ-MM-TT");
		HBox hbAddDate = new HBox(dateText, consentDate);
		hbAddDate.setSpacing(40);
		hbAddDate.setPadding(new Insets(10));
		
		final Button addConsentBtn = new Button("Speichern");

		addConsentBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				patientIds = fetcher.fetchAllPatientenID();

				Collections.sort(patientIds);

				InformedConsent newInformedConsent = new InformedConsent(patientIds.get(patientIds.size() - 1) + 1,
						choosenCenter, selConsent == null ? "nan" : selConsent,
						consentDate.getValue() != null ? consentDate.getValue().toString() : "NaT");
				consentData.add(newInformedConsent);
				fetcher.updateInformedConsent(newInformedConsent);
			}
		});

		final VBox vbConsent = new VBox(consentFormText, hbAddCenter, hbAddConsent, hbAddDate, addConsentBtn,
				deleteConsentBtn);
		vbConsent.setSpacing(10);
		vbConsent.setPadding(new Insets(50, 10, 10, 30));

		final HBox hbConsentTable = new HBox();
		hbConsentTable.setSpacing(10);

		Text consentTableLabel = new Text("Angabe zu Einwilligung der Studienteilnehmer");
		consentTableLabel.setFont(new Font("Arial", 20));
		// Filter Consent Table
		Label filterConsent = new Label("Filtern: ");
		ObservableList<String> consentFilterOptions = FXCollections.observableArrayList("Alle Einwilligungen",
				"Liste der Patienten mit fehlende Einwilligung", "Liste der Patienten mit unvollständigen Einwilligung",
				"Liste der Patienten mit erteilten Einwilligung nach der Radomisierung");

		final ComboBox<String> cbConsentFilter = new ComboBox<String>(consentFilterOptions);
		cbConsentFilter.setPromptText("Liste auswählen und anzeigen");

		cbConsentFilter.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				String choice = cbConsentFilter.getValue();
				List<InformedConsent> missingList;
				ObservableList<InformedConsent> missingConsentData;
				List<InformedConsent> incompletedList;
				ObservableList<InformedConsent> incompleteConsentData;
				List<InformedConsent> lateConsentList;
				ObservableList<InformedConsent> lateConsentData;

				switch (choice) {
				case "Liste der Patienten mit fehlende Einwilligung":
					missingList = fetcher.fetchAllInformedConsent(Consent.MISSING);
					missingConsentData = FXCollections.observableArrayList(missingList);
					consentTable.setItems(missingConsentData);
					vbConsent.setVisible(false);
					break;
				case "Liste der Patienten mit unvollständigen Einwilligung":
					incompletedList = fetcher.fetchAllInformedConsent(Consent.INCOMPLETE);
					incompleteConsentData = FXCollections.observableArrayList(incompletedList);
					consentTable.setItems(incompleteConsentData);
					vbConsent.setVisible(false);
					break;
				case "Liste der Patienten mit erteilten Einwilligung nach der Radomisierung":
					lateConsentList = fetcher.fetchAllInformedConsent(Consent.LATE);
					lateConsentData = FXCollections.observableArrayList(lateConsentList);
					consentTable.setItems(lateConsentData);
					vbConsent.setVisible(false);
					break;
				case "Alle Einwilligungen":
					consentList = fetcher.fetchAllInformedConsent();
					consentData = FXCollections.observableArrayList(consentList);
					consentTable.setItems(consentData);
					vbConsent.setVisible(true);
					break;
				}

			}
		});

		HBox hbConsentFilter = new HBox(filterConsent, cbConsentFilter);
		hbConsentFilter.setSpacing(10);
		hbConsentFilter.setPadding(new Insets(5, 0, 0, 150));

		VBox vbox = new VBox(consentTableLabel, hbConsentFilter, consentTable);
		vbox.setSpacing(5);

		hbConsentTable.setPadding(new Insets(10, 10, 10, 10));
		hbConsentTable.getChildren().addAll(vbox, vbConsent);
		return hbConsentTable;
	}

	/**
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private HBox createCenterTable() {
		centerTable = new TableView<Zentrum>();
		centerTable.setEditable(true);
		centerList = fetcher.fetchAllCenter();

		Collections.sort(centerList, new Comparator<Zentrum>() {

			@Override
			public int compare(Zentrum o1, Zentrum o2) {
				if (o1.getZentrum_id() > o2.getZentrum_id()) {
					return 1;
				}
				return -1;
			}
		});

		data = FXCollections.observableArrayList(centerList);

		TableColumn<Zentrum, String> monitorCol = new TableColumn<Zentrum, String>("Monitor");
		monitorCol.setMinWidth(120);

		monitorCol.setCellValueFactory(new PropertyValueFactory<Zentrum, String>("monitor"));
		monitorCol.setCellFactory(TextFieldTableCell.forTableColumn());
		monitorCol.setOnEditCommit(new EventHandler<CellEditEvent<Zentrum, String>>() {

			@Override
			public void handle(CellEditEvent<Zentrum, String> event) {
				((Zentrum) event.getTableView().getItems().get(event.getTablePosition().getRow()))
						.setMonitor(event.getNewValue());
			}
		});

		TableColumn<Zentrum, String> prueferCol = new TableColumn<Zentrum, String>("Prüfer");
		prueferCol.setMinWidth(100);
		prueferCol.setCellValueFactory(new PropertyValueFactory<Zentrum, String>("pruefer"));
		prueferCol.setCellFactory(TextFieldTableCell.forTableColumn());
		prueferCol.setOnEditCommit(new EventHandler<CellEditEvent<Zentrum, String>>() {

			@Override
			public void handle(CellEditEvent<Zentrum, String> event) {
				((Zentrum) event.getTableView().getItems().get(event.getTablePosition().getRow()))
						.setPruefer(event.getNewValue());
			}
		});

		TableColumn<Zentrum, String> ortCol = new TableColumn<Zentrum, String>("Ort");
		ortCol.setMinWidth(100);
		ortCol.setCellValueFactory(new PropertyValueFactory<Zentrum, String>("ort"));
		ortCol.setCellFactory(TextFieldTableCell.forTableColumn());
		ortCol.setOnEditCommit(new EventHandler<CellEditEvent<Zentrum, String>>() {

			@Override
			public void handle(CellEditEvent<Zentrum, String> event) {
				((Zentrum) event.getTableView().getItems().get(event.getTablePosition().getRow()))
						.setOrt(event.getNewValue());
			}
		});

		TableColumn<Zentrum, String> landCol = new TableColumn<Zentrum, String>("Land");
		landCol.setMinWidth(100);
		landCol.setCellValueFactory(new PropertyValueFactory<Zentrum, String>("land"));
		landCol.setCellFactory(TextFieldTableCell.forTableColumn());
		landCol.setOnEditCommit(new EventHandler<CellEditEvent<Zentrum, String>>() {

			@Override
			public void handle(CellEditEvent<Zentrum, String> event) {
				((Zentrum) event.getTableView().getItems().get(event.getTablePosition().getRow()))
						.setLand(event.getNewValue());
			}
		});

		TableColumn<Zentrum, String> zentrumIDCol = new TableColumn<Zentrum, String>("Zentrum ID");
		zentrumIDCol.setMinWidth(100);
		zentrumIDCol.setCellValueFactory(new PropertyValueFactory<Zentrum, String>("zentrum_id"));

		centerTable.setItems(data);
		centerTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		centerTable.getColumns().addAll(zentrumIDCol, landCol, ortCol, prueferCol, monitorCol);

		// Delete center items from table (not from database)
		Button deleteCenterBtn = new Button("Eintrag löschen");
		deleteCenterBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				Zentrum selectedCenter = centerTable.getSelectionModel().getSelectedItem();
				centerTable.getItems().remove(selectedCenter);
			}
		});

		Text centerTableLabel = new Text("Liste aller vorhandenen Zentren in der Studie");

		// Filter Consent Table
		Label filterCenter = new Label("Filtern: ");
		ObservableList<String> centerFilterOptions = FXCollections.observableArrayList("Liste aller Zentren",
				"Liste der Zentren in Deutschland", "Liste der Zentren in Großbritanien");

		final ComboBox<String> cbCenterFilter = new ComboBox<String>(centerFilterOptions);
		cbCenterFilter.setPromptText("Liste auswählen und anzeigen");
		cbCenterFilter.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				String choice = cbCenterFilter.getValue();
				List<Zentrum> centerListLand;
				ObservableList<Zentrum> centerListData;

				switch (choice) {
				case "Liste aller Zentren":
					centerList = fetcher.fetchAllCenter();
					data = FXCollections.observableArrayList(centerList);
					centerTableLabel.setText("Liste aller vorhandenen Zentren in der Studie");
					centerTable.setItems(data);
					break;
				case "Liste der Zentren in Deutschland":
					centerListLand = fetcher.fetchAllCenterByLand(Land.D);
					centerTableLabel.setText("Liste aller vorhandenen deutschen Zentren in der Studie");
					centerListData = FXCollections.observableArrayList(centerListLand);
					centerTable.setItems(centerListData);
					break;
				case "Liste der Zentren in Großbritanien":
					centerListLand = fetcher.fetchAllCenterByLand(Land.GB);
					centerTableLabel.setText("Liste aller vorhandenen britischen Zentren in der Studie");
					centerListData = FXCollections.observableArrayList(centerListLand);
					centerTable.setItems(centerListData);
					break;
				}
			}
		});

		HBox hbCenterFilter = new HBox(filterCenter, cbCenterFilter);
		hbCenterFilter.setSpacing(10);
		hbCenterFilter.setPadding(new Insets(5, 0, 0, 250));

		// Adding new center functionality
		Text centerFormText = new Text("Neues Zentrum anlegen: ");
		centerFormText.setFont(new Font("Arial", 20));
		final TextField addMonitorName = new TextField();
		final TextField addPrueferName = new TextField();
		final TextField addOrt = new TextField();

		Label monitorText = new Label("Monitor: ");
		addMonitorName.setPromptText("Monitor Name");
		addMonitorName.setMinWidth(200);
		HBox hbMonitor = new HBox(monitorText, addMonitorName);
		hbMonitor.setSpacing(5);
		hbMonitor.setPadding(new Insets(10, 10, 10, 10));

		Label prueferText = new Label("Prüfer: ");
		addPrueferName.setMinWidth(200);
		addPrueferName.setPromptText("Prüfer Name");
		HBox hbPruefer = new HBox(prueferText, addPrueferName);
		hbPruefer.setSpacing(15);
		hbPruefer.setPadding(new Insets(10, 10, 10, 10));

		Label ortText = new Label("Ort: ");
		addOrt.setMinWidth(200);
		addOrt.setPromptText("Ort");
		HBox hbOrt = new HBox(ortText, addOrt);
		hbOrt.setSpacing(30);
		hbOrt.setPadding(new Insets(10, 10, 10, 10));

		Label landText = new Label("Land: ");
		landNames = FXCollections.observableArrayList("Deutschland", "Großbritanien");
		cbLand = new ComboBox<String>(landNames);
		cbLand.setValue("Land wählen");
		cbLand.setMinWidth(100);
		cbLand.setPadding(new Insets(0, 0, 0, 20));

		cbLand.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				selectedLand = cbLand.getValue() !=null ? cbLand.getValue() : null;
			}
		});
		
		final Text centerInputValid = new Text();
		centerInputValid.setId("centerInputValid");
		HBox hbLand = new HBox(landText, cbLand);
		hbLand.setSpacing(25);
		hbLand.setPadding(new Insets(10));
		final Button addCenterBtn = new Button("Speichern");
		addCenterBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				Zentrum neuZentrum = null;
				
				if(addMonitorName.getText() != null && addPrueferName.getText() != null && addOrt.getText() != null && selectedLand !=null) {
					neuZentrum = new Zentrum(addMonitorName.getText(), addPrueferName.getText(), addOrt.getText(), selectedLand, data);
					data.add(neuZentrum);
					fetcher.updateZentrum(neuZentrum);
					addMonitorName.clear();
					addPrueferName.clear();
					addOrt.clear();
					centerInputValid.setText("");
				}else {
					centerInputValid.setText("Bitte Eingaben prüfen!");
				}
			}
		});

		final VBox vbAddCenter = new VBox(centerFormText, hbMonitor, hbPruefer, hbLand, hbOrt, centerInputValid, addCenterBtn,
				deleteCenterBtn);
		vbAddCenter.setSpacing(5);
		vbAddCenter.setPadding(new Insets(80, 10, 10, 30));

		centerTableLabel.setFont(new Font("Arial", 20));
		final VBox vbCenter = new VBox(centerTableLabel, hbCenterFilter, centerTable);
		vbCenter.setSpacing(5);

		final HBox hbox = new HBox();
		hbox.setSpacing(10);
		hbox.setPadding(new Insets(10, 10, 10, 10));
		hbox.getChildren().addAll(vbCenter, vbAddCenter);
		return hbox;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
