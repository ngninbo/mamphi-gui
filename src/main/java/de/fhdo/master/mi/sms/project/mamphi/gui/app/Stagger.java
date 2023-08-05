package de.fhdo.master.mi.sms.project.mamphi.gui.app;

import de.fhdo.master.mi.sms.project.mamphi.model.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static de.fhdo.master.mi.sms.project.mamphi.utils.GuiConstants.*;
import static de.fhdo.master.mi.sms.project.mamphi.utils.GuiConstants.INSETS_VALUE;
import static de.fhdo.master.mi.sms.project.mamphi.utils.UITranslation.*;
import static de.fhdo.master.mi.sms.project.mamphi.utils.UITranslation.SIGN_IN_ERROR_MSG;

public class Stagger {

    private final HBoxMenu menu;
    private final List<User> users = new ArrayList<>();
    private static FlowPane root;

    public Stagger(HBoxMenu menu) {
        this.menu = menu;
    }

    void setStage(Stage primaryStage) {
        try {
            primaryStage.setTitle(MAMPHI_ADMINISTRATION_GUI_TITLE);

            User demo = new User("demo", "demo");
            users.add(demo);
            User admin = new User("admin", "admin");
            users.add(admin);
            // Set Icon
            // Create an Image
            InputStream stream = getClass().getResourceAsStream("trial.png");
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
            actionTarget.setId("actionTarget");
            grid.add(actionTarget, 1, 6);

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
            String style = Objects.requireNonNull(getClass().getResource("application.css")).toExternalForm();
            loginScene.getStylesheets().add(style);

            Scene mainScene = new Scene(root, MAIN_SCENE_HEIGHT, MAIN_SCENE_WIDTH);
            mainScene.getStylesheets().add(style);

            // Add event handler for login button in the login view
            btn.setOnAction(event -> {

                User loginUser = new User(userTextField.getText(), pwBox.getText());

                if (users.contains(loginUser)) {

                    primaryStage.setScene(mainScene);
                    // Add button menu and the created horizontal box to the root pane
                    root.getChildren().addAll(btnMenu, menu.getCentreHBox().createCenterTable());
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

            // Add Event listener for each button from button menu
            centerBtn.setOnAction(event -> {

                root = new FlowPane(INSETS_VALUE, INSETS_VALUE);
                // Center Table
                final HBox hbCenterTable = menu.getCentreHBox().createCenterTable();
                // Add the created horizontal box to the root pane
                root.getChildren().addAll(btnMenu, hbCenterTable);

                mainScene.setRoot(root);
            });

            consentBtn.setOnAction(ActionEvent -> {

                root = new FlowPane(INSETS_VALUE, INSETS_VALUE);
                final HBox hbConsentTable = menu.getConsentHBox().createConsentTable();

                // Add the created horizontal box to the root pane
                root.getChildren().addAll(btnMenu, hbConsentTable);

                mainScene.setRoot(root);
            });

            randomWeekBtn.setOnAction(ActionEvent -> {

                root = new FlowPane(INSETS_VALUE, INSETS_VALUE);
                final HBox hbox = menu.getRandomizationHBox().createRandomizationPane();

                // Add the created horizontal box to the root pane
                root.getChildren().addAll(btnMenu, hbox);
                mainScene.setRoot(root);
            });

            monitoringBtn.setOnAction(event -> {
                root = new FlowPane(INSETS_VALUE, INSETS_VALUE);
                final HBox hbox = menu.getMonitoringPlanHBox().createMonitoringPlan();

                // Add the created horizontal box to the root pane
                root.getChildren().addAll(btnMenu, hbox);
                mainScene.setRoot(root);
            });

            primaryStage.setScene(loginScene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
