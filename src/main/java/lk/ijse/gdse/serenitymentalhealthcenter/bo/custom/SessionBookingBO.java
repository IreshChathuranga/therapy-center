package lk.ijse.gdse.serenitymentalhealthcenter.bo.custom;

import lk.ijse.gdse.serenitymentalhealthcenter.bo.SuperBO;

import java.sql.SQLException;

public interface SessionBookingBO extends SuperBO {
    String getNextSessionId() throws SQLException, ClassNotFoundException;
}
