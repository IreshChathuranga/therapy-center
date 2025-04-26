package lk.ijse.gdse.serenitymentalhealthcenter.bo.custom;

import lk.ijse.gdse.serenitymentalhealthcenter.bo.SuperBO;
import lk.ijse.gdse.serenitymentalhealthcenter.dto.TherapyProgramDto;
import lk.ijse.gdse.serenitymentalhealthcenter.dto.TherapySessionDto;

import java.sql.SQLException;
import java.util.List;

public interface TherapySessionBO extends SuperBO {
    List<TherapySessionDto> getAllSession() throws SQLException, ClassNotFoundException;
    boolean deleteSession(String sessionId) throws SQLException, ClassNotFoundException;
    boolean upadateSession(TherapySessionDto therapySessionDto) throws SQLException, ClassNotFoundException;
}
