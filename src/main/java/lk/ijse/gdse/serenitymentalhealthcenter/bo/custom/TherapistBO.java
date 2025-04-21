package lk.ijse.gdse.serenitymentalhealthcenter.bo.custom;

import lk.ijse.gdse.serenitymentalhealthcenter.bo.SuperBO;
import lk.ijse.gdse.serenitymentalhealthcenter.dto.PatientDto;
import lk.ijse.gdse.serenitymentalhealthcenter.dto.TherapistDto;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.Therapist;

import java.sql.SQLException;
import java.util.List;

public interface TherapistBO extends SuperBO {
    String getNextTherapistId() throws SQLException, ClassNotFoundException;
    List<TherapistDto> getAllTherapist() throws SQLException, ClassNotFoundException;
    boolean saveTherapist(TherapistDto therapistDto) throws SQLException, ClassNotFoundException;
    boolean deleteTherapist(String therapistId) throws SQLException, ClassNotFoundException;
    boolean upadateTherapist(TherapistDto therapistDto) throws SQLException, ClassNotFoundException;
    List<String> getAllTherapistIds() throws SQLException, ClassNotFoundException;
    Therapist findById(String selectedTherapistId) throws SQLException, ClassNotFoundException;
}
