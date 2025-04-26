package lk.ijse.gdse.serenitymentalhealthcenter.dao.custom.impl;

import lk.ijse.gdse.serenitymentalhealthcenter.config.FactoryConfiguration;
import lk.ijse.gdse.serenitymentalhealthcenter.dao.custom.TherapistDAO;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.Therapist;
import lk.ijse.gdse.serenitymentalhealthcenter.exception.DuplicateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class TherapistDAOImpl implements TherapistDAO {
    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        String lastId = null;

        try {
            lastId = session
                    .createQuery("SELECT t.id FROM Therapist t ORDER BY t.id DESC", String.class)
                    .setMaxResults(1)
                    .uniqueResult();
        } finally {
            session.close();
        }
        return lastId;
    }

    @Override
    public List<Therapist> getAll() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Query<Therapist> query = session.createQuery("from Therapist ", Therapist.class);
        List<Therapist> list = query.list();
        session.close();
        return list;
    }

    @Override
    public boolean save(Therapist entity) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            // Only check for duplicate if the ID is not null
            if (entity.getId() != null) {
                Therapist therapist = session.get(Therapist.class, entity.getId());
                if (therapist != null) {
                    throw new DuplicateException("Therapist already exists");
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
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            Therapist therapist = session.get(Therapist.class, id);
            session.remove(therapist);
            transaction.commit();
            session.close();
            return true;
        }catch (Exception e){
            transaction.rollback();
            session.close();
            e.printStackTrace();
            return false;
        }finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public boolean update(Therapist entity) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.merge(entity);
            transaction.commit();
            session.close();
            return true;
        }catch (Exception e){
            transaction.rollback();
            session.close();
            e.printStackTrace();
            return false;
        }finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<String> getAllTherapistIds() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Query<String> query = session.createQuery("SELECT t.id FROM Therapist t", String.class);
            List<String> therapistIds = query.list();
            transaction.commit();
            return therapistIds;
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException("Failed to get therapist IDs", e);
        } finally {
            session.close();
        }
    }

    @Override
    public Therapist findById(String id) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            return session.get(Therapist.class, id);
        } finally {
            session.close();
        }
    }

    @Override
    public Therapist findByIdfortherapist(String selectedTherapistId) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            return session.get(Therapist.class, selectedTherapistId);
        } finally {
            session.close();
        }
    }
}
