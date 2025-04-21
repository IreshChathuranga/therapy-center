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
import lk.ijse.gdse.serenitymentalhealthcenter.dto.PatientDto;
import lk.ijse.gdse.serenitymentalhealthcenter.dto.tm.PatientTM;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.time.LocalDate;

public class PatientController implements Initializable {
    PatientBO patientBO = (PatientBO) BOFactory.getInstance().getBO(BOFactory.BOType.PATIENT);
    public TextField txtName;
    public TextField txtAddress;
    public Label lblPatientId;
    public Label lblPatientName;
    public Label lblAddress;
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public TableView<PatientTM> tblPatient;
    public TableColumn<PatientTM , String> patientId;
    public TableColumn<PatientTM , String> patientName;
    public TableColumn<PatientTM , String> patientAddress;
    public TableColumn<PatientTM , String> gender;
    public TableColumn<PatientTM , String> phoneNumber;
    public TableColumn<PatientTM , Year> yearOfBirth;
    public TableColumn<PatientTM , Date> date;
    public TextField txtGender;
    public Label lblGender;
    public TextField txtNumber;
    public Label lblNumber;
    public Rectangle Patient;
    public Label lblInstructor;
    public Button btnRefresh;
    public TextField txtBirth;
    public Label lblBirth;
    public TextField txtDate;
    public Label lblDate;
    public Label lblPatient;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        patientId.setCellValueFactory(new PropertyValueFactory<>("id"));
        patientName.setCellValueFactory(new PropertyValueFactory<>("name"));
        patientAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        yearOfBirth.setCellValueFactory(new PropertyValueFactory<>("yearOfBirth"));
        date.setCellValueFactory(new PropertyValueFactory<>("registrationDate"));

        try{
            refreshPage();
            loadNextPatientId();
        }catch(ClassNotFoundException|SQLException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Fail Patient id").show();
        }
    }

    public void saveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String patientId = lblPatient.getText(); // this should already contain next ID
        if (patientId == null || patientId.isEmpty()) {
            patientId = patientBO.getNextPatientId(); // fallback
        }

        String patientName = txtName.getText();
        String patientAddress = txtAddress.getText();
        String gender = txtGender.getText();
        String number = txtNumber.getText();
        Year birth = Year.parse(txtBirth.getText());
        Date registrationDate = Date.valueOf(txtDate.getText());

        PatientDto patientDto = new PatientDto(
                patientId,
                patientName,
                patientAddress,
                gender,
                number,
                birth,
                registrationDate
        );

        boolean isSaved = patientBO.savePatient(patientDto);
        if (isSaved) {
            loadNextPatientId(); // refresh lblPatient with next ID
            txtName.clear();
            txtAddress.clear();
            txtGender.clear();
            txtNumber.clear();
            txtBirth.clear();
            txtDate.clear();
            new Alert(Alert.AlertType.INFORMATION, "Patient Saved").show();
            loadTableData();
        } else {
            new Alert(Alert.AlertType.ERROR, "Save failed").show();
        }
    }
    public void loadNextPatientId() throws SQLException, ClassNotFoundException {
        String nextPatientId = patientBO.getNextPatientId();
        lblPatient.setText(nextPatientId);
    }

    public void updateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String patientId = lblPatient.getText();
        String patientName = txtName.getText();
        String patientAddress = txtAddress.getText();
        String gender = txtGender.getText();
        String number = txtNumber.getText();
        Year birth = Year.parse(txtBirth.getText());
        Date registrasionDate = Date.valueOf(txtDate.getText());

        PatientDto patientDto = new PatientDto(
                patientId,
                patientName,
                patientAddress,
                gender,
                number,
                birth,
                registrasionDate
        );

        boolean isUpdated = patientBO.upadatePatient(patientDto);
        if(isUpdated){
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Patient Updated").show();
        }else {
            new Alert(Alert.AlertType.ERROR, "Update fail").show();
        }
    }

    public void deleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String patientId = lblPatient.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = patientBO.deletePatient(patientId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Patient deleted").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete patient...!").show();
            }
        }
    }

    public void onClickTable(MouseEvent mouseEvent) {
        PatientTM patientTM = tblPatient.getSelectionModel().getSelectedItem();
        if(patientTM !=null){
            lblPatient.setText(patientTM.getId());
            txtName.setText(patientTM.getName());
            txtAddress.setText(patientTM.getAddress());
            txtGender.setText(patientTM.getGender());
            txtNumber.setText(patientTM.getPhoneNumber());
            txtBirth.setText(String.valueOf(patientTM.getYearOfBirth()));
            txtDate.setText(String.valueOf(patientTM.getRegistrationDate()));

            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    public void refreshOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        refreshPage();
    }
    private void loadTableData() throws SQLException, ClassNotFoundException {
        List<PatientDto> patientDtos = patientBO.getAllPatient();
        ObservableList<PatientTM> patientTMS = FXCollections.observableArrayList();
        for(PatientDto patientDto:patientDtos){
            PatientTM patientTM = new PatientTM();
            patientTM.setId(patientDto.getId());
            patientTM.setName(patientDto.getName());
            patientTM.setAddress(patientDto.getAddress());
            patientTM.setGender(patientDto.getGender());
            patientTM.setPhoneNumber(patientDto.getPhoneNumber());
            patientTM.setYearOfBirth(patientDto.getYearOfBirth());
            patientTM.setRegistrationDate(patientDto.getRegistrationDate());
            patientTMS.add(patientTM);
        }
        tblPatient.setItems(patientTMS);
    }
//        try {
//            List<PatientDto> patientDtos = patientBO.getAllPatient();
//
//            // Safeguard against null
//            if (patientDtos == null) {
//                patientDtos = new ArrayList<>();
//            }
//
//            ObservableList<PatientTM> obList = FXCollections.observableArrayList();
//
//            for (PatientDto dto : patientDtos) {
//                obList.add(new PatientTM(
//                        dto.getId(),
//                        dto.getName(),
//                        dto.getAddress(),
//                        dto.getGender(),
//                        dto.getPhoneNumber(),
//                        dto.getYearOfBirth(),
//                        dto.getRegistrationDate()
//                ));
//            }
//
//            tblPatient.setItems(obList);
//
//        } catch (SQLException | ClassNotFoundException e) {
//            e.printStackTrace();
//            new Alert(Alert.AlertType.ERROR, "Failed to load patient data!").show();
//        }
//    }
    private void refreshPage() throws SQLException, ClassNotFoundException {
        loadNextPatientId();
        loadTableData();

        btnSave.setDisable(false);

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        txtName.setText("");
        txtAddress.setText("");
        txtGender.setText("");
        txtNumber.setText("");
        txtBirth.setText("");
        txtDate.setText("");
    }
}
