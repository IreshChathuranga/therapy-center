package lk.ijse.gdse.serenitymentalhealthcenter.bo.custom;

import lk.ijse.gdse.serenitymentalhealthcenter.bo.SuperBO;
import lk.ijse.gdse.serenitymentalhealthcenter.dto.PatientDto;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.Patient;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface PatientBO extends SuperBO {
    String getNextPatientId() throws SQLException, ClassNotFoundException;
    List<PatientDto> getAllPatient() throws SQLException, ClassNotFoundException;
    boolean savePatient(PatientDto patientDto) throws SQLException, ClassNotFoundException;
    boolean deletePatient(String patientId) throws SQLException, ClassNotFoundException;
    boolean upadatePatient(PatientDto patientDto) throws SQLException, ClassNotFoundException;
    List<String> getAllPatientIds() throws SQLException, ClassNotFoundException;
    Patient findById(String selectedPatientId) throws SQLException, ClassNotFoundException;
}
