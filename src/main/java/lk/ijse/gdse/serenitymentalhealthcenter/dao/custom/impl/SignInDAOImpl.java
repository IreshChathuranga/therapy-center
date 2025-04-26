package lk.ijse.gdse.serenitymentalhealthcenter.dao.custom.impl;

import lk.ijse.gdse.serenitymentalhealthcenter.config.FactoryConfiguration;
import lk.ijse.gdse.serenitymentalhealthcenter.dao.custom.SignInDAO;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.Patient;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.Therapist;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.User;
import lk.ijse.gdse.serenitymentalhealthcenter.exception.DuplicateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class SignInDAOImpl implements SignInDAO {
    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<User> getAll() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Query<User> query = session.createQuery("from User ", User.class);
        List<User> list = query.list();
        session.close();
        return list;
    }

    @Override
    public boolean save(User entity) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            if (entity.getUserName() != null) {
                User user = session.get(User.class, entity.getUserName());
                if (user != null) {
                    throw new DuplicateException("User already exists");
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
    public boolean update(User entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<String> getAllRoles() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Query<String> query = session.createQuery("SELECT role FROM User u", String.class);
            List<String> therapistIds = query.list();
            transaction.commit();
            return therapistIds;
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException("Failed to get user role", e);
        } finally {
            session.close();
        }
    }
}
