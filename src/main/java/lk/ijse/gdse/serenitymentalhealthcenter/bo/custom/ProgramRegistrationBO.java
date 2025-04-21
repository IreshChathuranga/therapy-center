package lk.ijse.gdse.serenitymentalhealthcenter.bo.custom;

import lk.ijse.gdse.serenitymentalhealthcenter.bo.SuperBO;
import lk.ijse.gdse.serenitymentalhealthcenter.dto.ProgramRegistrationDto;
import lk.ijse.gdse.serenitymentalhealthcenter.dto.TherapyProgramDto;

import java.sql.SQLException;
import java.util.List;

public interface ProgramRegistrationBO extends SuperBO {
    String getNextRegistrationId() throws SQLException, ClassNotFoundException;
    List<ProgramRegistrationDto> getAllRegistration() throws SQLException, ClassNotFoundException;
    boolean saveRegistration(ProgramRegistrationDto programRegistrationDto) throws SQLException, ClassNotFoundException;
    boolean deleteRegistration(String registrationId) throws SQLException, ClassNotFoundException;
    boolean upadateRegistration(ProgramRegistrationDto programRegistrationDto) throws SQLException, ClassNotFoundException;
}
