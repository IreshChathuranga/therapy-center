package lk.ijse.gdse.serenitymentalhealthcenter.bo.custom.impl;

import lk.ijse.gdse.serenitymentalhealthcenter.bo.custom.SessionBookingBO;
import lk.ijse.gdse.serenitymentalhealthcenter.dao.DAOFactory;
import lk.ijse.gdse.serenitymentalhealthcenter.dao.custom.SessionBookingDAO;
import lk.ijse.gdse.serenitymentalhealthcenter.dao.custom.TherapyProgramDAO;

import java.sql.SQLException;

public class SessionBookingBOImpl implements SessionBookingBO {
    SessionBookingDAO sessionBookingDAO = (SessionBookingDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SESSIONBOOKING);

    @Override
    public String getNextSessionId() throws SQLException, ClassNotFoundException {
        return sessionBookingDAO.getNextId();
    }
}
