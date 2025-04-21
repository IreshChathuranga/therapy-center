package lk.ijse.gdse.serenitymentalhealthcenter.dao.custom;

import lk.ijse.gdse.serenitymentalhealthcenter.dao.CrudDAO;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.Patient;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.Therapist;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.TherapyProgram;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface TherapyProgramDAO extends CrudDAO<TherapyProgram> {
    List<String> getAllTherapyIds() throws SQLException, ClassNotFoundException;
    TherapyProgram findById(String id) throws SQLException, ClassNotFoundException;
    TherapyProgram findByIdfortherapy(String selectedTherapyId) throws SQLException, ClassNotFoundException;
}
