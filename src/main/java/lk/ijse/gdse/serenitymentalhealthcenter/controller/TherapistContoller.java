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
import lk.ijse.gdse.serenitymentalhealthcenter.bo.custom.TherapistBO;
import lk.ijse.gdse.serenitymentalhealthcenter.dto.PatientDto;
import lk.ijse.gdse.serenitymentalhealthcenter.dto.TherapistDto;
import lk.ijse.gdse.serenitymentalhealthcenter.dto.tm.PatientTM;
import lk.ijse.gdse.serenitymentalhealthcenter.dto.tm.TherapistTM;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class TherapistContoller implements Initializable {
    public TextField txtName;
    public TextField txtSpecialization;
    public Label lblTheraphistId;
    public Label lblTheraphistName;
    public Label lblSpecialization;
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public TableView<TherapistTM> tblTheraphist;
    public TableColumn<TherapistTM , String> theraphistId;
    public TableColumn<TherapistTM , String> theraphistName;
    public TableColumn<TherapistTM , String> specialization;
    public TableColumn<TherapistTM , String> years;
    public TableColumn<TherapistTM , String> phoneNumber;
    public TextField txtYears;
    public Label lblIYears;
    public TextField txtNumber;
    public Label lblNumber;
    public Rectangle Theraphist;

    public Button btnRefresh;
    public Label lblTheraphist;
    public TextField txtAssignProgram;
    public TableColumn<TherapistTM , String> assignProgram;

    TherapistBO therapistBO = (TherapistBO) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPIST);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        theraphistId.setCellValueFactory(new PropertyValueFactory<>("id"));
        theraphistName.setCellValueFactory(new PropertyValueFactory<>("name"));
        specialization.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        years.setCellValueFactory(new PropertyValueFactory<>("experienceYear"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        assignProgram.setCellValueFactory(new PropertyValueFactory<>("assignedProgram"));

        try{
            refreshPage();
            loadNextTherapistId();
        }catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Fail Therapist id").show();
        }
    }

    public void saveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String therapistId = lblTheraphist.getText();
        if (therapistId == null || therapistId.isEmpty()) {
            therapistId = therapistBO.getNextTherapistId();
        }

        String therapistName = txtName.getText();
        String specialization = txtSpecialization.getText();
        String years = txtYears.getText();
        String number = txtNumber.getText();
        String assignProgram = txtAssignProgram.getText();

        TherapistDto therapistDto = new TherapistDto(
                therapistId,
                therapistName,
                specialization,
                years,
                number,
                assignProgram
        );

        boolean isSaved = therapistBO.saveTherapist(therapistDto);
        if (isSaved) {
            loadNextTherapistId();
            txtName.clear();
            txtSpecialization.clear();
            txtYears.clear();
            txtNumber.clear();
            txtAssignProgram.clear();
            new Alert(Alert.AlertType.INFORMATION, "Therapist Saved").show();
            loadTableData();
        } else {
            new Alert(Alert.AlertType.ERROR, "Save failed").show();
        }
    }

    public void updateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String therapistId = lblTheraphist.getText();
        String therapistName = txtName.getText();
        String specialization = txtSpecialization.getText();
        String years = txtYears.getText();
        String number = txtNumber.getText();
        String assignProgram = txtAssignProgram.getText();

        TherapistDto therapistDto = new TherapistDto(
                therapistId,
                therapistName,
                specialization,
                years,
                number,
                assignProgram
        );

        boolean isUpdated = therapistBO.upadateTherapist(therapistDto);
        if(isUpdated){
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Theraphist Updated").show();
        }else {
            new Alert(Alert.AlertType.ERROR, "Update fail").show();
        }
    }

    public void deleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String therapistId = lblTheraphist.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = therapistBO.deleteTherapist(therapistId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Therapist deleted").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete Therapist...!").show();
            }
        }
    }

    public void onClickTable(MouseEvent mouseEvent) {
        TherapistTM therapistTM = tblTheraphist.getSelectionModel().getSelectedItem();
        if(therapistTM !=null){
            lblTheraphist.setText(therapistTM.getId());
            txtName.setText(therapistTM.getName());
            txtSpecialization.setText(therapistTM.getSpecialization());
            txtYears.setText(therapistTM.getExperienceYear());
            txtNumber.setText(therapistTM.getPhoneNumber());
            txtAssignProgram.setText(therapistTM.getAssignedProgram());

            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }
    private void loadTableData() throws SQLException, ClassNotFoundException {
        List<TherapistDto> therapistDtos = therapistBO.getAllTherapist();
        ObservableList<TherapistTM> therapistTMS = FXCollections.observableArrayList();
        for(TherapistDto therapistDto:therapistDtos){
            TherapistTM therapistTM = new TherapistTM();
            therapistTM.setId(therapistDto.getId());
            therapistTM.setName(therapistDto.getName());
            therapistTM.setSpecialization(therapistDto.getSpecialization());
            therapistTM.setExperienceYear(therapistDto.getExperienceYear());
            therapistTM.setPhoneNumber(therapistDto.getPhoneNumber());
            therapistTM.setAssignedProgram(therapistDto.getAssignedProgram());
            therapistTMS.add(therapistTM);
        }
        tblTheraphist.setItems(therapistTMS);
    }

    public void refreshOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        refreshPage();
    }
    public void loadNextTherapistId() throws SQLException, ClassNotFoundException {
        String nextTherapistId = therapistBO.getNextTherapistId();
        lblTheraphist.setText(nextTherapistId);
    }
    private void refreshPage() throws SQLException, ClassNotFoundException {
        loadNextTherapistId();
        loadTableData();

        btnSave.setDisable(false);

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        txtName.setText("");
        txtSpecialization.setText("");
        txtYears.setText("");
        txtNumber.setText("");
        txtAssignProgram.setText("");
    }
}
