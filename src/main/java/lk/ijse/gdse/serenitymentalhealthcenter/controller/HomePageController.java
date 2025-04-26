package lk.ijse.gdse.serenitymentalhealthcenter.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {
    private static final double DEFAULT_WIDTH = 900;
    private static final double DEFAULT_HEIGHT = 639;
    public AnchorPane mainAnchor;
    public Label lblRegistration;
    public Button btnRegistration;
    public AnchorPane loardAnchor;
    public Label lblHomePage;
    public Label lblPatient;
    public Button btnPatient;
    public Label lblProgram;
    public Button btnProgram;
    public Label lblTherapist;
    public Button btnTherapist;
    public Label lblBooking;
    public Button btnBooking;
    public Label lblSession;
    public Button btnSession;
    public Label lblPayment;
    public Button btnPayment;
    public Label lblSetting;
    public Button btnSetting;
    public Label lblLogout;
    public Button btnLogout;
    private String userRole;

    public void setRole(String role) {
        this.userRole = role;
        adjustUIForRole();
    }

    private void adjustUIForRole() {
        if ("admin".equalsIgnoreCase(userRole)) {
            btnTherapist.setDisable(false);
            btnProgram.setDisable(false);
            btnSetting.setDisable(false);
            btnLogout.setDisable(false);

            btnRegistration.setDisable(true);
            btnPatient.setDisable(true);
            btnBooking.setDisable(true);
            btnSession.setDisable(true);
            btnPayment.setDisable(true);

            try {
                navigateTo("/view/Therapist.fxml");
                lblHomePage.setText("MANAGE THERAPIST");
                applyButtonStyles(lblTherapist, btnTherapist);
            } catch (IOException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to load page").show();
            }

        } else if ("receptionist".equalsIgnoreCase(userRole)) {
            btnRegistration.setDisable(false);
            btnPatient.setDisable(false);
            btnBooking.setDisable(false);
            btnSession.setDisable(false);
            btnPayment.setDisable(false);
            btnSetting.setDisable(false);
            btnLogout.setDisable(false);

            btnTherapist.setDisable(true);
            btnProgram.setDisable(true);

            try {
                navigateTo("/view/Patient.fxml");
                lblHomePage.setText("MANAGE PATIENT");
                applyButtonStyles(lblPatient, btnPatient);
            } catch (IOException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to load page").show();
            }
        }
    }

    public void registrationOnAction(ActionEvent actionEvent) throws IOException {
        applyButtonStyles(lblRegistration,btnRegistration);
        lblHomePage.setText("MANAGE REGISTRATION");
        navigateTo("/view/ProgramRegistration.fxml");
    }

    public void patientOnAction(ActionEvent actionEvent) throws IOException {
        applyButtonStyles(lblPatient,btnPatient);
        lblHomePage.setText("MANAGE PATIENT");
        navigateTo("/view/Patient.fxml");
    }

    public void programOnAction(ActionEvent actionEvent) throws IOException {
        applyButtonStyles(lblProgram,btnProgram);
        lblHomePage.setText("MANAGE PROGRAM");
        navigateTo("/view/TherapyProgram.fxml");
    }

    public void therapistOnAction(ActionEvent actionEvent) throws IOException {
        applyButtonStyles(lblTherapist,btnTherapist);
        lblHomePage.setText("MANAGE THERAPIST");
        navigateTo("/view/Therapist.fxml");
    }

    public void bookingOnAction(ActionEvent actionEvent) throws IOException {
        applyButtonStyles(lblBooking,btnBooking);
        lblHomePage.setText("PLACE BOOKING");
        navigateTo("/view/SessionBooking.fxml");
    }

    public void sessionOnAction(ActionEvent actionEvent) throws IOException {
        applyButtonStyles(lblSession,btnSession);
        lblHomePage.setText("MANAGE SESSION");
        navigateTo("/view/TherapySession.fxml");
    }

    public void paymentOnAction(ActionEvent actionEvent) throws IOException {
        applyButtonStyles(lblPayment,btnPayment);
        lblHomePage.setText("MANAGE PAYMENTS");
        navigateTo("/view/Payment.fxml");
    }

    public void settingOnAction(ActionEvent actionEvent) {
    }

    public void logoutOnAction(ActionEvent actionEvent) throws IOException {
        applyButtonStyles(lblLogout,btnLogout);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();
        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES){
            navigateToLogin("/view/LogIn.fxml");
        }
    }
    public void navigateTo(String fxmlpath) throws IOException {
        loardAnchor.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlpath));
        loardAnchor.getChildren().add(load);
    }
    private void applyButtonStyles(Label activeLabel, Button activeButton) {
        String inactiveStyle = "-fx-background-color: #ffffff; -fx-background-radius: 45; -fx-text-fill:  #09463e;";
        String activeStyle = "-fx-background-color: #71b9a2; -fx-background-radius: 45; -fx-text-fill:  #09463e;";

        activeLabel.setStyle(activeStyle);
        activeButton.setStyle(activeStyle);

        Label[] labels = {lblRegistration, lblPatient, lblTherapist, lblProgram, lblSession,lblSetting, lblBooking, lblPayment, lblLogout};
        Button[] buttons = {btnRegistration, btnPatient, btnTherapist, btnProgram, btnSession, btnSetting, btnBooking, btnPayment, btnLogout};

        for (Label label : labels) {
            if (label != activeLabel) {
                label.setStyle(inactiveStyle);
            }
        }

        for (Button button : buttons) {
            if (button != activeButton) {
                button.setStyle(inactiveStyle);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        try {
//            navigateTo("/view/Patient.fxml");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }
    public void navigateToLogin(String fxmlpath) throws IOException {
        try {
            mainAnchor.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlpath));
            Parent load = loader.load();

            Stage currentStage = (Stage) mainAnchor.getScene().getWindow();

            currentStage.setWidth(DEFAULT_WIDTH);
            currentStage.setHeight(DEFAULT_HEIGHT);

            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            currentStage.setX((screenBounds.getWidth() - DEFAULT_WIDTH) / 2);
            currentStage.setY((screenBounds.getHeight() - DEFAULT_HEIGHT) / 2);

            Scene newScene = new Scene(load, DEFAULT_WIDTH, DEFAULT_HEIGHT);
            currentStage.setScene(newScene);
            currentStage.setResizable(false);
            currentStage.show();
        }catch (IOException e){
            new Alert(Alert.AlertType.ERROR,"Fail UI").show();
        }
    }
}
