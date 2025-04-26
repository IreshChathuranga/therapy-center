package lk.ijse.gdse.serenitymentalhealthcenter.dao.custom.impl;

import lk.ijse.gdse.serenitymentalhealthcenter.config.FactoryConfiguration;
import lk.ijse.gdse.serenitymentalhealthcenter.dao.custom.SessionBookingDAO;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.Booking;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.Therapist;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.TherapySession;
import lk.ijse.gdse.serenitymentalhealthcenter.exception.DuplicateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class SessionBookingDAOImpl implements SessionBookingDAO {
    private Session session;

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
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
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            if (entity.getId() != null) {
                TherapySession therapySession = session.get(TherapySession.class, entity.getId());
                if (therapySession != null) {
                    throw new DuplicateException("TherapySession already exists");
                }
            }

            session.persist(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(TherapySession entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public boolean saveWithSession(Session session, TherapySession therapySession) {
        try{
            session.merge(therapySession);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
