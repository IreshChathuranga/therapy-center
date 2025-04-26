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
import lk.ijse.gdse.serenitymentalhealthcenter.bo.custom.PatientBO;
import lk.ijse.gdse.serenitymentalhealthcenter.bo.custom.ProgramRegistrationBO;
import lk.ijse.gdse.serenitymentalhealthcenter.bo.custom.TherapistBO;
import lk.ijse.gdse.serenitymentalhealthcenter.bo.custom.TherapyProgramBO;
import lk.ijse.gdse.serenitymentalhealthcenter.dto.ProgramRegistrationDto;
import lk.ijse.gdse.serenitymentalhealthcenter.dto.TherapyProgramDto;
import lk.ijse.gdse.serenitymentalhealthcenter.dto.tm.ProgramRegistrationTM;
import lk.ijse.gdse.serenitymentalhealthcenter.dto.tm.TherapyProgramTM;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProgramRegistrationController implements Initializable {
    public TextField txtDate;
    public TextField txtAdvancePayment;
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public TableView<ProgramRegistrationTM> tblRegistarion;

    public Rectangle Patient;
    public Label lblRegistration;
    public Button btnRefresh;
    public ComboBox<String> cmbPatient;
    public ComboBox<String> cmbProgram;
    public TableColumn<ProgramRegistrationTM , String> registrationId;
    public TableColumn<ProgramRegistrationTM , Date> registrationDate;
    public TableColumn<ProgramRegistrationTM , BigDecimal> advancePayment;
    public TableColumn<ProgramRegistrationTM , String> patientId;
    public TableColumn<ProgramRegistrationTM , String> programId;

    TherapyProgramBO therapyProgramBO = (TherapyProgramBO) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPYPROGRAM);

    PatientBO patientBO = (PatientBO) BOFactory.getInstance().getBO(BOFactory.BOType.PATIENT);
    ProgramRegistrationBO programRegistrationBO = (ProgramRegistrationBO) BOFactory.getInstance().getBO(BOFactory.BOType.PROGRAMREGISTRATION);


    public void saveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String registrationId = lblRegistration.getText();
        if (registrationId == null || registrationId.isEmpty()) {
            registrationId = programRegistrationBO.getNextRegistrationId();
        }

        Date date = Date.valueOf(txtDate.getText());
        BigDecimal payment = new BigDecimal(txtAdvancePayment.getText());
        String patientId = cmbPatient.getValue();
        String programId = cmbProgram.getValue();

        ProgramRegistrationDto dto = new ProgramRegistrationDto(
                registrationId,
                date,
                payment,
                patientId,
                programId
        );

        boolean isSaved = programRegistrationBO.saveRegistration(dto);

        if (isSaved) {
            loadNextRegistrationId();
            txtDate.clear();
            txtAdvancePayment.clear();
            cmbPatient.getSelectionModel().clearSelection();
            cmbProgram.getSelectionModel().clearSelection();
            new Alert(Alert.AlertType.INFORMATION, "Registration Saved").show();
            loadTableData();
        } else {
            new Alert(Alert.AlertType.ERROR, "Save failed").show();
        }
    }

    public void updateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String registrationId = lblRegistration.getText();
        Date date = Date.valueOf(txtDate.getText());
        BigDecimal payment = new BigDecimal(txtAdvancePayment.getText());
        String patientId = cmbPatient.getValue();
        String programId = cmbProgram.getValue();

        ProgramRegistrationDto dto = new ProgramRegistrationDto(
                registrationId,
                date,
                payment,
                patientId,
                programId
        );

        boolean isUpdated = programRegistrationBO.upadateRegistration(dto);
        if(isUpdated){
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Registration Updated").show();
        }else {
            new Alert(Alert.AlertType.ERROR, "Update fail").show();
        }
    }

    public void deleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String registrationId = lblRegistration.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = programRegistrationBO.deleteRegistration(registrationId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Registration deleted").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete Registration...!").show();
            }
        }
    }

    public void onClickTable(MouseEvent mouseEvent) {
        ProgramRegistrationTM programRegistrationTM = tblRegistarion.getSelectionModel().getSelectedItem();
        if(programRegistrationTM !=null){
            lblRegistration.setText(programRegistrationTM.getProgramRegistrationId());
            txtDate.setText(String.valueOf(programRegistrationTM.getDate()));
            txtAdvancePayment.setText(String.valueOf(programRegistrationTM.getAdvancePayment()));
            cmbPatient.getSelectionModel().select(programRegistrationTM.getPatientId());
            cmbProgram.getSelectionModel().select(programRegistrationTM.getProgramId());
//            cmbTherapistId.getSelectionModel().select(String.valueOf(therapyProgramTM.getTherapistId()));

            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    public void refreshOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        refreshPage();
    }
    public void loadNextRegistrationId() {
        try {
            String nextId = programRegistrationBO.getNextRegistrationId();
            lblRegistration.setText(nextId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        registrationId.setCellValueFactory(new PropertyValueFactory<>("programRegistrationId"));
        registrationDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        advancePayment.setCellValueFactory(new PropertyValueFactory<>("advancePayment"));
        patientId.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        programId.setCellValueFactory(new PropertyValueFactory<>("programId"));

        try{
            refreshPage();
            loadNextRegistrationId();
        }catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Fail PatientRegistration id").show();
        }
    }
    private void loadTableData() throws SQLException, ClassNotFoundException {
        List<ProgramRegistrationDto> programRegistrationDtos = programRegistrationBO.getAllRegistration();
        ObservableList<ProgramRegistrationTM> programRegistrationTMS = FXCollections.observableArrayList();
        for(ProgramRegistrationDto dto : programRegistrationDtos){
            programRegistrationTMS.add(new ProgramRegistrationTM(
                    dto.getProgramRegistrationId(),
                    dto.getDate(),
                    dto.getAdvancePayment(),
                    dto.getPatientId(),
                    dto.getProgramId()
            ));
        }
        tblRegistarion.setItems(programRegistrationTMS);
    }
    private void refreshPage() throws SQLException, ClassNotFoundException {
        loadNextRegistrationId();
        loadPatientIds();
        loadProgramIds();
        loadTableData();

        btnSave.setDisable(false);

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        txtDate.setText("");
        txtAdvancePayment.setText("");
        cmbPatient.getSelectionModel().clearSelection();
        cmbProgram.getSelectionModel().clearSelection();
    }
    private void loadPatientIds() throws SQLException, ClassNotFoundException {
        List<String> patientId = patientBO.getAllPatientIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(patientId);
        cmbPatient.setItems(observableList);
    }
    private void loadProgramIds() throws SQLException, ClassNotFoundException {
        List<String> programId = therapyProgramBO.getAllTherapyIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(programId);
        cmbProgram.setItems(observableList);
    }
}
