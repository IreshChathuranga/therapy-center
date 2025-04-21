package lk.ijse.gdse.serenitymentalhealthcenter.dao.custom;

import lk.ijse.gdse.serenitymentalhealthcenter.dao.CrudDAO;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.Patient;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.Therapist;

import java.sql.SQLException;
import java.util.List;

public interface PatientDAO extends CrudDAO<Patient> {
    List<String> getAllPatientIds() throws SQLException, ClassNotFoundException;
    Patient findById(String id) throws SQLException, ClassNotFoundException;
    Patient findByIdforname(String selectedPatientId) throws SQLException, ClassNotFoundException;
}
