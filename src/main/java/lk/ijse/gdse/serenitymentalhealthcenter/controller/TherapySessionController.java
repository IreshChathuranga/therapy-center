package lk.ijse.gdse.serenitymentalhealthcenter.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class TherapySessionController implements Initializable {
    public TextField txtPlaceDate;
    public TextField txtDuration;
    public Button btnUpdate;
    public Button btnDelete;
    public TableView tblSession;
    public TableColumn sessionId;
    public TableColumn placeDate;
    public TableColumn sessionDuration;
    public TableColumn payment;
    public TableColumn remaingTotalAmount;
    public TableColumn sessionDate;
    public TableColumn paymentStatus;
    public TableColumn theraphistId;
    public TableColumn therapyId;
    public TableColumn patientId;
    public TextField txtPayment;
    public TextField txtAmount;
    public Rectangle Session;
    public Label lblSessionId;
    public ComboBox<String> cmbTherapyId;
    public ComboBox<String> cmbPatientId;
    public ComboBox<String> cmbTheraphistId;
    public TextField txtDate;
    public TextField txtStatus;
    public Button btnRefresh;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void updateOnAction(ActionEvent actionEvent) {
    }

    public void deleteOnAction(ActionEvent actionEvent) {
    }

    public void onClickTable(MouseEvent mouseEvent) {
    }

    public void refreshOnAction(ActionEvent actionEvent) {
    }
}
