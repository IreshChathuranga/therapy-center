package lk.ijse.gdse.serenitymentalhealthcenter.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import lk.ijse.gdse.serenitymentalhealthcenter.bo.BOFactory;
import lk.ijse.gdse.serenitymentalhealthcenter.bo.custom.*;
import lk.ijse.gdse.serenitymentalhealthcenter.dto.ProgramRegistrationDto;
import lk.ijse.gdse.serenitymentalhealthcenter.dto.TherapistDto;
import lk.ijse.gdse.serenitymentalhealthcenter.dto.TherapySessionDto;
import lk.ijse.gdse.serenitymentalhealthcenter.dto.tm.ProgramRegistrationTM;
import lk.ijse.gdse.serenitymentalhealthcenter.dto.tm.TherapistTM;
import lk.ijse.gdse.serenitymentalhealthcenter.dto.tm.TherapySessionTM;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.TherapySession;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class TherapySessionController implements Initializable {
    public TextField txtPlaceDate;
    public TextField txtDuration;
    public Button btnUpdate;
    public Button btnDelete;
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
    public TextField txtSessionId;
    public TextField txtPhoneNumber;
    public TableView<TherapySessionTM> tblSession;
    public TableColumn<TherapySessionTM,String> sessionId;
    public TableColumn<TherapySessionTM,String> patientId;
    public TableColumn<TherapySessionTM,String> phoneNumber;
    public TableColumn<TherapySessionTM,String> sessionDuration;
    public TableColumn<TherapySessionTM,Date> sessionDate;
    public TableColumn<TherapySessionTM,Date> placeDate;
    public TableColumn<TherapySessionTM,String> theraphistId;
    public TableColumn<TherapySessionTM,String> therapyId;
    public TableColumn<TherapySessionTM, BigDecimal> payment;
    public TableColumn<TherapySessionTM,BigDecimal> totalAmount;
    public TableColumn<TherapySessionTM,String> paymentId;
    public ComboBox<String> cmbPaymentId;

    TherapyProgramBO therapyProgramBO = (TherapyProgramBO) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPYPROGRAM);

    PatientBO patientBO = (PatientBO) BOFactory.getInstance().getBO(BOFactory.BOType.PATIENT);

    PaymentBO paymentBO = (PaymentBO) BOFactory.getInstance().getBO(BOFactory.BOType.PAYMENT);
    TherapistBO therapistBO = (TherapistBO) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPIST);
    TherapySessionBO therapySessionBO = (TherapySessionBO) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPYSESSION);
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sessionId.setCellValueFactory(new PropertyValueFactory<>("id"));
        patientId.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        sessionDuration.setCellValueFactory(new PropertyValueFactory<>("sessionDuration"));
        sessionDate.setCellValueFactory(new PropertyValueFactory<>("sessionDate"));
        placeDate.setCellValueFactory(new PropertyValueFactory<>("placeDate"));
        theraphistId.setCellValueFactory(new PropertyValueFactory<>("therapistId"));
        therapyId.setCellValueFactory(new PropertyValueFactory<>("programId"));
        payment.setCellValueFactory(new PropertyValueFactory<>("payment"));
        totalAmount.setCellValueFactory(new PropertyValueFactory<>("totalRemainingAmount"));
        paymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));

        try{
            refreshPage();
        }catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Fail Therapist id").show();
        }
    }

    public void updateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String sessionId = txtSessionId.getText();
        String patientId = cmbPatientId.getValue();
        String phoneNumber = txtPhoneNumber.getText();
        String duration = txtDuration.getText();
        Date sessionDate = Date.valueOf(txtDate.getText());
        Date placeDate = Date.valueOf(txtPlaceDate.getText());
        String theraphistId = cmbTheraphistId.getValue();
        String therapyId = cmbTherapyId.getValue();
        BigDecimal payment = new BigDecimal(txtPayment.getText());
        BigDecimal amount = new BigDecimal(txtAmount.getText());
        String paymentId = cmbPaymentId.getValue();

        TherapySessionDto dto = new TherapySessionDto(
                sessionId,
                patientId,
                phoneNumber,
                duration,
                sessionDate,
                placeDate,
                theraphistId,
                therapyId,
                payment,
                amount,
                paymentId
        );

        boolean isUpdated = therapySessionBO.upadateSession(dto);
        if(isUpdated){
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Session Updated").show();
        }else {
            new Alert(Alert.AlertType.ERROR, "Update fail").show();
        }
    }

    public void deleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String sessionId = txtSessionId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = therapySessionBO.deleteSession(sessionId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Session deleted").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete Session...!").show();
            }
        }
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        List<TherapySessionDto> therapySessionDtos = therapySessionBO.getAllSession();
        ObservableList<TherapySessionTM> therapySessionTMS = FXCollections.observableArrayList();
        for(TherapySessionDto dto : therapySessionDtos){
            therapySessionTMS.add(new TherapySessionTM(
                    dto.getId(),
                    dto.getPatientId(),
                    dto.getPhoneNumber(),
                    dto.getSessionDuration(),
                    dto.getSessionDate(),
                    dto.getPlaceDate(),
                    dto.getTherapistId(),
                    dto.getProgramId(),
                    dto.getPayment(),
                    dto.getTotalRemainingAmount(),
                    dto.getPaymentId()
            ));
        }
        tblSession.setItems(therapySessionTMS);
    }

    public void onClickTable(MouseEvent mouseEvent) {
        TherapySessionTM therapySessionTM = tblSession.getSelectionModel().getSelectedItem();
        if(therapySessionTM !=null){
            txtSessionId.setText(therapySessionTM.getId());
            cmbPatientId.getSelectionModel().select(therapySessionTM.getPatientId());
            txtPhoneNumber.setText(therapySessionTM.getPhoneNumber());
            txtDuration.setText(therapySessionTM.getSessionDuration());
            txtDate.setText(String.valueOf(therapySessionTM.getSessionDate()));
            txtPlaceDate.setText(String.valueOf(therapySessionTM.getPlaceDate()));
            cmbTheraphistId.getSelectionModel().select(therapySessionTM.getTherapistId());
            cmbTherapyId.getSelectionModel().select(therapySessionTM.getProgramId());
            txtPayment.setText(String.valueOf(therapySessionTM.getPayment()));
            txtAmount.setText(String.valueOf(therapySessionTM.getTotalRemainingAmount()));
            cmbPaymentId.getSelectionModel().select(therapySessionTM.getPaymentId());

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }
    private void refreshPage() throws SQLException, ClassNotFoundException {
        loadPatientIds();
        loadProgramIds();
        loadTherapistIds();
        loadPaymentIds();
        loadTableData();

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        txtSessionId.setText("");
        cmbPatientId.getSelectionModel().clearSelection();
        txtPhoneNumber.setText("");
        txtDuration.setText("");
        txtDate.setText("");
        txtPlaceDate.setText("");
        cmbTheraphistId.getSelectionModel().clearSelection();
        cmbTherapyId.getSelectionModel().clearSelection();
        txtPayment.setText("");
        txtAmount.setText("");
        cmbPaymentId.getSelectionModel().clearSelection();
    }
    public void refreshOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        refreshPage();
    }
    private void loadPatientIds() throws SQLException, ClassNotFoundException {
        List<String> patientId = patientBO.getAllPatientIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(patientId);
        cmbPatientId.setItems(observableList);
    }
    private void loadProgramIds() throws SQLException, ClassNotFoundException {
        List<String> programId = therapyProgramBO.getAllTherapyIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(programId);
        cmbTherapyId.setItems(observableList);
    }
    private void loadTherapistIds() throws SQLException, ClassNotFoundException {
        List<String> therapyId = therapistBO.getAllTherapistIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(therapyId);
        cmbTheraphistId.setItems(observableList);
    }
    private void loadPaymentIds() throws SQLException, ClassNotFoundException {
        List<String> paymentId = paymentBO.getAllPaymentIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(paymentId);
        cmbPaymentId.setItems(observableList);
    }
}
