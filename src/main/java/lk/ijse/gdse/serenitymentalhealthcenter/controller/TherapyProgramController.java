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
import lk.ijse.gdse.serenitymentalhealthcenter.bo.custom.TherapistBO;
import lk.ijse.gdse.serenitymentalhealthcenter.bo.custom.TherapyProgramBO;
import lk.ijse.gdse.serenitymentalhealthcenter.dto.PatientDto;
import lk.ijse.gdse.serenitymentalhealthcenter.dto.TherapistDto;
import lk.ijse.gdse.serenitymentalhealthcenter.dto.TherapyProgramDto;
import lk.ijse.gdse.serenitymentalhealthcenter.dto.tm.TherapistTM;
import lk.ijse.gdse.serenitymentalhealthcenter.dto.tm.TherapyProgramTM;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.Therapist;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class TherapyProgramController implements Initializable {
    public TextField txtName;
    public TextField txtDuration;
    public Label lblTherapyId;
    public Label lblTherapyName;
    public Label lblDuration;
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public TableView<TherapyProgramTM> tblTherapy;
    public TableColumn<TherapyProgramTM , String> therapyId;
    public TableColumn<TherapyProgramTM , String> therapyName;
    public TableColumn<TherapyProgramTM , String> duration;
    public TableColumn<TherapyProgramTM , BigDecimal> fee;
    public TableColumn<TherapyProgramTM , String> description;
    public TableColumn<TherapyProgramTM , String> therapistId;
    public TextField txtFee;
    public Label lblFee;
    public TextField txtDescription;
    public Label lblDescription;
    public Rectangle Therapy;
    public Label lblInstructor;
    public Button btnRefresh;
    public TextField txtTherapistId;
    public Label lblTheraphistId;
    public ComboBox<String> cmbTherapistId;

    TherapyProgramBO therapyProgramBO = (TherapyProgramBO) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPYPROGRAM);

    TherapistBO therapistBO = (TherapistBO) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPIST);
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        therapyId.setCellValueFactory(new PropertyValueFactory<>("id"));
        therapyName.setCellValueFactory(new PropertyValueFactory<>("name"));
        duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        fee.setCellValueFactory(new PropertyValueFactory<>("cost"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        therapistId.setCellValueFactory(new PropertyValueFactory<>("therapistId"));

        try{
            refreshPage();
            loadNextTherapyId();
        }catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Fail Patient id").show();
        }
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        List<TherapyProgramDto> therapyProgramDtos = therapyProgramBO.getAllTherapy();
        ObservableList<TherapyProgramTM> therapyProgramTMS = FXCollections.observableArrayList();
        for(TherapyProgramDto dto : therapyProgramDtos){
            therapyProgramTMS.add(new TherapyProgramTM(
                    dto.getId(),
                    dto.getName(),
                    dto.getDuration(),
                    dto.getCost(),
                    dto.getDescription(),
                    dto.getTherapistId()
            ));
            System.out.println(dto.getTherapistId());
        }
        tblTherapy.setItems(therapyProgramTMS);
    }
    public void saveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String therapyId = lblTherapyId.getText();
        if (therapyId == null || therapyId.isEmpty()) {
            therapyId = therapyProgramBO.getNextTherapyId();
        }

        String therapyName = txtName.getText();
        String duration = txtDuration.getText();
        BigDecimal fee = new BigDecimal(txtFee.getText());
        String description = txtDescription.getText();
        String therapistId = cmbTherapistId.getValue();

        TherapyProgramDto dto = new TherapyProgramDto(
                therapyId,
                therapyName,
                duration,
                fee,
                description,
                therapistId
        );

        boolean isSaved = therapyProgramBO.saveTherapy(dto);

        if (isSaved) {
            loadNextTherapyId();
            txtName.clear();
            txtDuration.clear();
            txtFee.clear();
            txtDescription.clear();
            cmbTherapistId.getSelectionModel().clearSelection();
            new Alert(Alert.AlertType.INFORMATION, "Therapy Saved").show();
            loadTableData();
        } else {
            new Alert(Alert.AlertType.ERROR, "Save failed").show();
        }
    }

    public void updateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String therapyId = lblTherapyId.getText();
        String therapyName = txtName.getText();
        String duration = txtDuration.getText();
        BigDecimal fee = new BigDecimal(txtFee.getText());
        String description = txtDescription.getText();
        String therapistId = cmbTherapistId.getValue();

        TherapyProgramDto therapyProgramDto = new TherapyProgramDto(
                therapyId,
                therapyName,
                duration,
                fee,
                description,
                therapistId
        );

        boolean isUpdated = therapyProgramBO.upadateTherapy(therapyProgramDto);
        if(isUpdated){
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Therapy Updated").show();
        }else {
            new Alert(Alert.AlertType.ERROR, "Update fail").show();
        }
    }

    public void deleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String therapyId = lblTherapyId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = therapyProgramBO.deleteTherapy(therapyId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Therapy deleted").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete Therapy...!").show();
            }
        }
    }

    public void onClickTable(MouseEvent mouseEvent) {
        TherapyProgramTM therapyProgramTM = tblTherapy.getSelectionModel().getSelectedItem();
        if(therapyProgramTM !=null){
            lblTherapyId.setText(therapyProgramTM.getId());
            txtName.setText(therapyProgramTM.getName());
            txtDuration.setText(therapyProgramTM.getDuration());
            txtFee.setText(String.valueOf(therapyProgramTM.getCost()));
            txtDescription.setText(therapyProgramTM.getDescription());
            cmbTherapistId.getSelectionModel().select(String.valueOf(therapyProgramTM.getTherapistId()));

            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    public void refreshOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        refreshPage();
    }
    public void loadNextTherapyId(){
        try {
            String nextId = therapyProgramBO.getNextTherapyId();
            lblTherapyId.setText(nextId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void refreshPage() throws SQLException, ClassNotFoundException {
        loadNextTherapyId();
        loadTherapyIds();
        loadTableData();

        btnSave.setDisable(false);

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        txtName.setText("");
        txtDuration.setText("");
        txtFee.setText("");
        txtDescription.setText("");
        cmbTherapistId.getSelectionModel().clearSelection();
    }
    private void loadTherapyIds() throws SQLException, ClassNotFoundException {
        List<String> therapyId = therapistBO.getAllTherapistIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(therapyId);
        cmbTherapistId.setItems(observableList);
    }
}
