package de.fhdo.master.mi.sms.project.mamphi.gui.app;

import de.fhdo.master.mi.sms.project.mamphi.model.Centre;
import de.fhdo.master.mi.sms.project.mamphi.model.Country;
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

import java.util.List;
import static de.fhdo.master.mi.sms.project.mamphi.model.Country.*;

public class CentreHBox {

    public static final int INSET_VALUES = 80;
    public static final int MIN_WIDTH = 120;
    private final Main main;

    public CentreHBox(Main main) {
        this.main = main;
    }

    /**
     * @return HBox
     */
    HBox createCenterTable() {
        main.setCenterTable(new TableView<>());
        main.getCenterTable().setEditable(true);
        main.setCenterList(Main.trialService.findAllCenter());

        main.getCenterList()
                .sort((o1, o2) -> o1.getCentreID() > o2.getCentreID() ? 1 : GuiConstants.INSETS_MIN_VALUE);

        main.setCentres(FXCollections.observableArrayList(main.getCenterList()));

        TableColumn<Centre, String> monitorCol = new TableColumn<>("Monitor");
        monitorCol.setMinWidth(MIN_WIDTH);

        monitorCol.setCellValueFactory(new PropertyValueFactory<>("monitor"));
        monitorCol.setCellFactory(TextFieldTableCell.forTableColumn());
        monitorCol.setOnEditCommit(event -> event.getTableView().getItems().get(event.getTablePosition().getRow())
                .setMonitor(event.getNewValue()));

        TableColumn<Centre, String> trierCol = new TableColumn<>("Prüfer");
        trierCol.setMinWidth(GuiConstants.DEFAULT_MIN_WIDTH);
        trierCol.setCellValueFactory(new PropertyValueFactory<>("trier"));
        trierCol.setCellFactory(TextFieldTableCell.forTableColumn());
        trierCol.setOnEditCommit(event -> event.getTableView().getItems().get(event.getTablePosition().getRow())
                .setTrier(event.getNewValue()));

        TableColumn<Centre, String> placeCol = new TableColumn<>("Ort");
        placeCol.setMinWidth(GuiConstants.DEFAULT_MIN_WIDTH);
        placeCol.setCellValueFactory(new PropertyValueFactory<>("place"));
        placeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        placeCol.setOnEditCommit(event -> event.getTableView().getItems().get(event.getTablePosition().getRow())
                .setPlace(event.getNewValue()));

        TableColumn<Centre, String> countryCol = new TableColumn<>("Land");
        countryCol.setMinWidth(GuiConstants.DEFAULT_MIN_WIDTH);
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        countryCol.setCellFactory(TextFieldTableCell.forTableColumn());
        countryCol.setOnEditCommit(event -> event.getTableView().getItems().get(event.getTablePosition().getRow())
                .setCountry(event.getNewValue()));

        TableColumn<Centre, String> centreIDCol = new TableColumn<>("Zentrum ID");
        centreIDCol.setMinWidth(GuiConstants.DEFAULT_MIN_WIDTH);
        centreIDCol.setCellValueFactory(new PropertyValueFactory<>("centreID"));

        main.getCenterTable().setItems(main.getCentres());
        main.getCenterTable().getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        main.getCenterTable().getColumns().addAll(centreIDCol, countryCol, placeCol, trierCol, monitorCol);

        // Delete center items from table (not from database)
        Button deleteCenterBtn = new Button(UITranslation.DELETE_BTN_LABEL);
        deleteCenterBtn.setOnAction(event -> {

            Centre selectedCenter = main.getCenterTable().getSelectionModel().getSelectedItem();
            main.getCenterTable().getItems().remove(selectedCenter);
        });

        Text centerTableLabel = new Text(UITranslation.CENTER_OVERVIEW_LABEL);

        // Filter Consent Table
        Label filterCenter = new Label(UITranslation.FILTER_LABEL);
        ObservableList<String> centerFilterOptions = FXCollections.observableArrayList(
                UITranslation.CENTER_OVERVIEW_OPTION,
                UITranslation.GERMAN_CENTER_OVERVIEW_OPTION,
                UITranslation.BRITISH_CENTER_OVERVIEW_OPTION);

        final ComboBox<String> cbCenterFilter = new ComboBox<>(centerFilterOptions);
        cbCenterFilter.setPromptText(UITranslation.CONSENT_OVERVIEW_FILTER_PROMPT_TXT);
        cbCenterFilter.setOnAction(event -> {

            String choice = cbCenterFilter.getValue();
            List<Centre> centerListByCountry;
            ObservableList<Centre> centerListData;

            switch (choice) {

                case UITranslation.GERMAN_CENTER_OVERVIEW_OPTION:
                    centerListByCountry = Main.trialService.findAllCenter(DE);
                    centerTableLabel.setText(UITranslation.GERMAN_CENTER_OVERVIEW_LABEL);
                    centerListData = FXCollections.observableArrayList(centerListByCountry);
                    main.getCenterTable().setItems(centerListData);
                    break;

                case UITranslation.BRITISH_CENTER_OVERVIEW_OPTION:
                    centerListByCountry = Main.trialService.findAllCenter(GB);
                    centerTableLabel.setText(UITranslation.BRITISH_CENTER_OVERVIEW_LABEL);
                    centerListData = FXCollections.observableArrayList(centerListByCountry);
                    main.getCenterTable().setItems(centerListData);
                    break;

                default:
                    main.setCenterList(Main.trialService.findAllCenter());
                    main.setCentres(FXCollections.observableArrayList(main.getCenterList()));
                    centerTableLabel.setText(UITranslation.CENTER_OVERVIEW_LABEL);
                    main.getCenterTable().setItems(main.getCentres());
                    break;
            }
        });

        HBox hbCenterFilter = new HBox(filterCenter, cbCenterFilter);
        hbCenterFilter.setSpacing(GuiConstants.INSETS_VALUE);
        final int v3 = 250;
        hbCenterFilter.setPadding(new Insets(GuiConstants.SPACING_DEFAULT_VALUE, GuiConstants.INSETS_MIN_VALUE, GuiConstants.INSETS_MIN_VALUE, v3));

        // Adding new center functionality
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
        main.setCountryNames(FXCollections.observableArrayList(DE.getFullCountryName(), GB.getFullCountryName()));
        main.setCbCountry(new ComboBox<>(main.getCountryNames()));
        main.getCbCountry().setValue(UITranslation.COUNTRY_CHOICE_BTN_LABEL);
        main.getCbCountry().setMinWidth(GuiConstants.DEFAULT_MIN_WIDTH);
        main.getCbCountry().setPadding(new Insets(GuiConstants.INSETS_MIN_VALUE, GuiConstants.INSETS_MIN_VALUE, GuiConstants.INSETS_MIN_VALUE, GuiConstants.FONT));

        main.getCbCountry()
                .setOnAction(arg0 -> Main.selectedCountry = main.getCbCountry().getValue() != null ? main.getCbCountry().getValue() : null);

        final Text centerInputValid = new Text();
        centerInputValid.setId("centerInputValid");
        HBox hbCountry = new HBox(countryText, main.getCbCountry());
        hbCountry.setSpacing(GuiConstants.H_BOX_SPACING_VALUE);
        hbCountry.setPadding(new Insets(GuiConstants.INSETS_VALUE));
        final Button addCenterBtn = new Button(UITranslation.SAVE_BTN_LABEL);
        addCenterBtn.setOnAction(event -> {

            if (addMonitorName.getText() != null && addTrierName.getText() != null && addPlace.getText() != null && Main.selectedCountry != null) {
                Centre neuCenter = new Centre(addMonitorName.getText(), addTrierName.getText(), addPlace.getText(),
                        Main.selectedCountry, Main.trialService.nextIdByCountry(Country.valueOf(Main.selectedCountry)));
                main.getCentres().add(neuCenter);
                Main.trialService.update(neuCenter);
                addMonitorName.clear();
                addTrierName.clear();
                addPlace.clear();
                main.getCbCountry().setValue(null);
                Main.selectedCountry = null;
            } else {
                centerInputValid.setText(UITranslation.INPUT_VALIDATION_ERROR_MSG);
            }
        });

        final VBox vbAddCenter = new VBox(centerFormText, hbMonitor, hbTrier, hbCountry, hbPlace, centerInputValid, addCenterBtn,
                deleteCenterBtn);
        vbAddCenter.setSpacing(GuiConstants.SPACING_DEFAULT_VALUE);
        vbAddCenter.setPadding(new Insets(INSET_VALUES, GuiConstants.INSETS_VALUE, GuiConstants.INSETS_VALUE, GuiConstants.SPACING_MAX_VALUE));

        centerTableLabel.setFont(new Font(GuiConstants.FONT_NAME, GuiConstants.FONT));
        final VBox vbCenter = new VBox(centerTableLabel, hbCenterFilter, main.getCenterTable());
        vbCenter.setSpacing(GuiConstants.SPACING_DEFAULT_VALUE);

        final HBox hbox = new HBox();
        hbox.setSpacing(GuiConstants.INSETS_VALUE);
        hbox.setPadding(new Insets(GuiConstants.INSETS_VALUE, GuiConstants.INSETS_VALUE, GuiConstants.INSETS_VALUE, GuiConstants.INSETS_VALUE));
        hbox.getChildren().addAll(vbCenter, vbAddCenter);
        return hbox;
    }
}