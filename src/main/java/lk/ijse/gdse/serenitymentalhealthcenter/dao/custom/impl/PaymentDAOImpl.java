package lk.ijse.gdse.serenitymentalhealthcenter.dao.custom.impl;

import lk.ijse.gdse.serenitymentalhealthcenter.config.FactoryConfiguration;
import lk.ijse.gdse.serenitymentalhealthcenter.dao.custom.PaymentDAO;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.Payment;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.Therapist;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.TherapySession;
import lk.ijse.gdse.serenitymentalhealthcenter.exception.DuplicateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {
    private Session session;
    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Query<String> query = session.createQuery("SELECT p.id FROM Payment p ORDER BY p.id DESC", String.class);
            query.setMaxResults(1);
            String lastId = query.uniqueResult();

            transaction.commit();
            session.close();

            if (lastId != null) {
                int lastNum = Integer.parseInt(lastId.replace("PY", ""));
                int nextNum = lastNum + 1;
                return String.format("PY%03d", nextNum);
            } else {
                return "PY001";
            }
        } catch (Exception e) {
            transaction.rollback();
            session.close();
            throw new RuntimeException("no payment ID", e);
        }
    }

    @Override
    public List<Payment> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(Payment entity) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            // Only check for duplicate if the ID is not null
            if (entity.getId() != null) {
                Payment payment = session.get(Payment.class, entity.getId());
                if (payment != null) {
                    throw new DuplicateException("Payment already exists");
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
    public boolean update(Payment entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public Payment findById(String id) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            return session.get(Payment.class, id);
        } finally {
            session.close();
        }
    }

    @Override
    public boolean saveWithSession(Session session, Payment payment) {
        try{
            session.merge(payment);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<String> getAllPaymentIds() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Query<String> query = session.createQuery("SELECT p.id FROM Payment p", String.class);
            List<String> paymentIds = query.list();
            transaction.commit();
            return paymentIds;
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException("Failed to get payment IDs", e);
        } finally {
            session.close();
        }
    }
}
