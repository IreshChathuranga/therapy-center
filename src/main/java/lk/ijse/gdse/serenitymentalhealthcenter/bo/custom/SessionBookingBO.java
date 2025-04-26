package lk.ijse.gdse.serenitymentalhealthcenter.bo.custom;

import lk.ijse.gdse.serenitymentalhealthcenter.bo.SuperBO;
import lk.ijse.gdse.serenitymentalhealthcenter.dto.BookingDto;
import lk.ijse.gdse.serenitymentalhealthcenter.dto.TherapySessionDto;

import java.sql.SQLException;

public interface SessionBookingBO extends SuperBO {
    String getNextSessionId() throws SQLException, ClassNotFoundException;
    boolean savePlaceBooking(BookingDto bookingDto) throws SQLException, ClassNotFoundException;
}
