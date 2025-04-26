package lk.ijse.gdse.serenitymentalhealthcenter.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.Rectangle;
import lk.ijse.gdse.serenitymentalhealthcenter.bo.BOFactory;
import lk.ijse.gdse.serenitymentalhealthcenter.bo.custom.*;
import lk.ijse.gdse.serenitymentalhealthcenter.dto.BookingDto;
import lk.ijse.gdse.serenitymentalhealthcenter.dto.PatientDto;
import lk.ijse.gdse.serenitymentalhealthcenter.dto.TherapyProgramDto;
import lk.ijse.gdse.serenitymentalhealthcenter.dto.TherapySessionDto;
import lk.ijse.gdse.serenitymentalhealthcenter.dto.tm.BookingTM;
import lk.ijse.gdse.serenitymentalhealthcenter.dto.tm.CartTM;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.Patient;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.Payment;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.Therapist;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.TherapyProgram;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SessionBookingController implements Initializable {
    public TextField txtPlaceDate;
    public TextField txtDuration;
    public Button btnAddTable;
    public Button btnPlaceSession;
    public TableView<CartTM> tblBookingSession;
    public TableColumn<CartTM , String> sessionId;
    public TableColumn<CartTM , Date> placeDate;
    public TableColumn<CartTM , String> sessionDuration;
    public TableColumn<CartTM , Date> sessionDate;
    public TableColumn<CartTM , String> paymentId;
    public TableColumn<CartTM , BigDecimal> remaingTotalAmount;
    public TableColumn<CartTM , BigDecimal> payment;
    public TableColumn<CartTM , String> theraphistId;
    public TableColumn<CartTM , String> therapyId;
    public TableColumn<CartTM , String> patientId;
    public TableColumn<? , ?> action;
    public TextField txtPayment;
    public TextField txtAmount;
    public Rectangle Session;
    public Label lblSessionId;
    public ComboBox<String> cmbTherapyId;
    public ComboBox<String> cmbPatientId;
    public ComboBox<String> cmbTheraphistId;
    public TextField txtDate;
    public TextField txtStatus;
    public Label lblTheraphistName;
    public Label lblTherapyName;
    public Label lblPatientName;
    public Rectangle Session1;
    public Label lblPaymentId;
    public Button btnRefresh;

    private final ObservableList<CartTM> cartTMS = FXCollections.observableArrayList();
    public TextField txtPhone;
    public TableColumn phoneNumber;
    PaymentBO paymentBO = (PaymentBO) BOFactory.getInstance().getBO(BOFactory.BOType.PAYMENT);

    PatientBO patientBO = (PatientBO) BOFactory.getInstance().getBO(BOFactory.BOType.PATIENT);
    TherapistBO therapistBO = (TherapistBO) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPIST);
    SessionBookingBO sessionBookingBO = (SessionBookingBO) BOFactory.getInstance().getBO(BOFactory.BOType.SESSIONBOOKING);
    TherapyProgramBO therapyProgramBO = (TherapyProgramBO) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPYPROGRAM);
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValues();
        try {
            refreshPage();
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load data").show();
        }
    }
    private void setCellValues() {
        sessionId.setCellValueFactory(new PropertyValueFactory<>("sessionId"));
        placeDate.setCellValueFactory(new PropertyValueFactory<>("placeDate"));
        sessionDuration.setCellValueFactory(new PropertyValueFactory<>("sessionDuration"));
        sessionDate.setCellValueFactory(new PropertyValueFactory<>("sessionDate"));
        paymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        remaingTotalAmount.setCellValueFactory(new PropertyValueFactory<>("totalRemainingAmount"));
        payment.setCellValueFactory(new PropertyValueFactory<>("payment"));
        theraphistId.setCellValueFactory(new PropertyValueFactory<>("therapistId"));
        therapyId.setCellValueFactory(new PropertyValueFactory<>("therapyId"));
        patientId.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        action.setCellValueFactory(new PropertyValueFactory<>("removeBtn"));

        tblBookingSession.setItems(cartTMS);
    }

    public void addTableOnAction(ActionEvent Event) {
        String sessionId = lblSessionId.getText();
        Date placeDate = Date.valueOf(txtPlaceDate.getText());
        String sessionDuration = txtDuration.getText();
        Date sessionDate = Date.valueOf(txtDate.getText());
        String paymentId = lblPaymentId.getText();
        BigDecimal amount = new BigDecimal(txtAmount.getText());
        BigDecimal payment = new BigDecimal(txtPayment.getText());
        String therapistId = cmbTheraphistId.getValue();
        String phone = txtPhone.getText();

        if (therapistId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select therapist..!").show();
        }
        String therapyId = cmbTherapyId.getValue();

        if (therapyId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select program..!").show();
        }
        String patientId = cmbPatientId.getValue();
        if (patientId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select patient..!").show();
        }

        Button btn = new Button("Remove");

        CartTM newCartTM = new CartTM(
                sessionId,
                placeDate,
                sessionDuration,
                sessionDate,
                paymentId,
                amount,
                payment,
                therapistId,
                therapyId,
                patientId,
                phone,
                btn
        );
        btn.setOnAction(actionEvent -> {
            cartTMS.remove(newCartTM);
            tblBookingSession.refresh();
        });
        cartTMS.add(newCartTM);
    }

    public void placeSessionOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (tblBookingSession.getItems().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please add data..!").show();
        }
        if (cmbTheraphistId.getSelectionModel().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select therapist for place booking..!").show();
        }
        if (cmbTherapyId.getSelectionModel().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select progarm for place booking..!").show();
        }
        if (cmbPatientId.getSelectionModel().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select patient for place booking..!").show();
        }
        String sessionId = lblSessionId.getText();
        if (sessionId == null || sessionId.isEmpty()) {
            sessionId = sessionBookingBO.getNextSessionId();
            lblSessionId.setText(sessionId);
        }
        Date placeDate = Date.valueOf(txtPlaceDate.getText());
        String duration = txtDuration.getText();
        Date sessionDate = Date.valueOf(txtDate.getText());
        String paymentId = lblPaymentId.getText();
        if (paymentId == null || paymentId.isEmpty()) {
            paymentId = paymentBO.getNextPaymentId();
            lblPaymentId.setText(paymentId);
        }
        BigDecimal amount = new BigDecimal(txtAmount.getText());
        BigDecimal payment = new BigDecimal(txtPayment.getText());
        String therapistId = cmbTheraphistId.getValue();
        String therapyId = cmbTherapyId.getValue();
        String patientId = cmbPatientId.getValue();
        String phone = txtPhone.getText();

//        List<Payment> paymentDTOS = new ArrayList<>();

//        for (CartTM cartTM : cartTMS) {
//            Payment paymentDTO = new Payment(
//                    paymentId,
//                    amount,
//                    sessionDate,
//                    sessionId
//            );
//        }
        BookingDto bookingDto = new BookingDto(
                sessionId,
                patientId,
                phone,
                duration,
                sessionDate,
                placeDate,
                therapistId,
                therapyId,
                payment,
                amount,
                paymentId
        );
        boolean isSaved = sessionBookingBO.savePlaceBooking(bookingDto);
        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Booking saved..!").show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Booking fail..!").show();
        }
    }

    private void loadTherapyIds() throws SQLException, ClassNotFoundException {
        List<String> therapyId = therapyProgramBO.getAllTherapyIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(therapyId);
        cmbTherapyId.setItems(observableList);
    }
    public void therapyOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String selectedTherapyId = cmbTherapyId.getSelectionModel().getSelectedItem();
        TherapyProgram therapyProgram = therapyProgramBO.findById(selectedTherapyId);

        if (therapyProgram != null) {
            lblTherapyName.setText(therapyProgram.getName());
        }
    }

    public void patientOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String selectedPatientId = cmbPatientId.getSelectionModel().getSelectedItem();
        Patient patient = patientBO.findById(selectedPatientId);

        if (patient != null) {
            lblPatientName.setText(patient.getName());
        }
    }
    private void loadPatientIds() throws SQLException, ClassNotFoundException {
        List<String> patientId = patientBO.getAllPatientIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(patientId);
        cmbPatientId.setItems(observableList);
    }

    public void theraphistOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String selectedTherapistId = cmbTheraphistId.getSelectionModel().getSelectedItem();
        Therapist therapist = therapistBO.findById(selectedTherapistId);

        if (therapist != null) {
            lblTheraphistName.setText(therapist.getName());
        }
    }
    private void loadTherapistIds() throws SQLException, ClassNotFoundException {
        List<String> therapistId = therapistBO.getAllTherapistIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(therapistId);
        cmbTheraphistId.setItems(observableList);
    }

    public void refreshOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        refreshPage();
    }
    private void refreshPage() throws SQLException, ClassNotFoundException {
        lblSessionId.setText(sessionBookingBO.getNextSessionId());
        lblPaymentId.setText(paymentBO.getNextPaymentId());
        loadPatientIds();
        loadTherapistIds();
        loadTherapyIds();

        txtPlaceDate.setText("");
        txtDuration.setText("");
        txtDate.setText("");
        txtAmount.setText("");
        txtPayment.setText("");
        cmbTheraphistId.getSelectionModel().clearSelection();
        cmbTherapyId.getSelectionModel().clearSelection();
        cmbPatientId.getSelectionModel().clearSelection();
        txtPhone.setText("");
        lblTheraphistName.setText("");
        lblTherapyName.setText("");
        lblPatientName.setText("");

        cartTMS.clear();

        tblBookingSession.refresh();
    }

}
