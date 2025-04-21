package lk.ijse.gdse.serenitymentalhealthcenter.dao.custom.impl;

import lk.ijse.gdse.serenitymentalhealthcenter.config.FactoryConfiguration;
import lk.ijse.gdse.serenitymentalhealthcenter.dao.custom.SessionBookingDAO;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.Booking;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.TherapySession;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class SessionBookingDAOImpl implements SessionBookingDAO {

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            // HQL query to get the last inserted patient ID (ordered descending)
            Query<String> query = session.createQuery("SELECT s.id FROM TherapySession s ORDER BY s.id DESC", String.class);
            query.setMaxResults(1);
            String lastId = query.uniqueResult();

            transaction.commit();
            session.close();

            if (lastId != null) {
                int lastNum = Integer.parseInt(lastId.replace("S", ""));
                int nextNum = lastNum + 1;
                return String.format("S%03d", nextNum);
            } else {
                return "S001";
            }
        } catch (Exception e) {
            transaction.rollback();
            session.close();
            throw new RuntimeException("no session ID", e);
        }
    }

    @Override
    public List<TherapySession> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(TherapySession entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(TherapySession entity) throws SQLException, ClassNotFoundException {
        return false;
    }
}
