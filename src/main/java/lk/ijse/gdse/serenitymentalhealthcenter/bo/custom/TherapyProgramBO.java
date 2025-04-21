package lk.ijse.gdse.serenitymentalhealthcenter.bo.custom;

import lk.ijse.gdse.serenitymentalhealthcenter.bo.SuperBO;
import lk.ijse.gdse.serenitymentalhealthcenter.dto.TherapistDto;
import lk.ijse.gdse.serenitymentalhealthcenter.dto.TherapyProgramDto;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.Therapist;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.TherapyProgram;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface TherapyProgramBO extends SuperBO {
    String getNextTherapyId() throws SQLException, ClassNotFoundException;
    List<TherapyProgramDto> getAllTherapy() throws SQLException, ClassNotFoundException;
    boolean saveTherapy(TherapyProgramDto therapyProgramDto) throws SQLException, ClassNotFoundException;
    boolean deleteTherapy(String therapyId) throws SQLException, ClassNotFoundException;
    boolean upadateTherapy(TherapyProgramDto therapyProgramDto) throws SQLException, ClassNotFoundException;
    List<String> getAllTherapyIds() throws SQLException, ClassNotFoundException;
    TherapyProgram findById(String selectedTherapyId) throws SQLException, ClassNotFoundException;
}
