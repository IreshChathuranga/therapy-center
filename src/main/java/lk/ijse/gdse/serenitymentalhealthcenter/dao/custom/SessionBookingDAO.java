package lk.ijse.gdse.serenitymentalhealthcenter.dao.custom;

import lk.ijse.gdse.serenitymentalhealthcenter.dao.CrudDAO;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.Booking;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.TherapySession;
import org.hibernate.Session;

public interface SessionBookingDAO extends CrudDAO<TherapySession> {
    void setSession(Session session);

    boolean saveWithSession(Session session, TherapySession therapySession);
}
