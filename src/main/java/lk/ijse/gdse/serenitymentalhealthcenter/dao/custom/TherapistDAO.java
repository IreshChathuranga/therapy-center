package lk.ijse.gdse.serenitymentalhealthcenter.dao.custom;

import jakarta.persistence.criteria.CriteriaUpdate;
import lk.ijse.gdse.serenitymentalhealthcenter.dao.CrudDAO;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.Patient;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.Therapist;

import java.sql.SQLException;
import java.util.List;

public interface TherapistDAO extends CrudDAO<Therapist> {
    List<String> getAllTherapistIds() throws SQLException, ClassNotFoundException;
    Therapist findById(String id) throws SQLException, ClassNotFoundException;
    Therapist findByIdfortherapist(String selectedTherapistId) throws SQLException, ClassNotFoundException;
}
