package lk.ijse.gdse.serenitymentalhealthcenter.dao.custom.impl;

import lk.ijse.gdse.serenitymentalhealthcenter.config.FactoryConfiguration;
import lk.ijse.gdse.serenitymentalhealthcenter.dao.custom.TherapyProgramDAO;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.Patient;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.Therapist;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.TherapyProgram;
import lk.ijse.gdse.serenitymentalhealthcenter.exception.DuplicateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class TherapyProgramDAOImpl implements TherapyProgramDAO {

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Query<String> query = session.createQuery("SELECT p.id FROM TherapyProgram p ORDER BY p.id DESC", String.class);
            query.setMaxResults(1);
            String lastId = query.uniqueResult();

            transaction.commit();
            session.close();

            if (lastId != null) {
                int lastNum = Integer.parseInt(lastId.replace("MI1", ""));
                int nextNum = lastNum + 1;
                return String.format("MI1%03d", nextNum);
            } else {
                return "MI1001";
            }
        } catch (Exception e) {
            transaction.rollback();
            session.close();
            throw new RuntimeException("no therapy ID", e);
        }
    }

    @Override
    public List<TherapyProgram> getAll() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        List<TherapyProgram> list = session.createQuery(
                "from TherapyProgram ", TherapyProgram.class
        ).getResultList();

        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public boolean save(TherapyProgram entity) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            if (entity.getId() != null) {
                TherapyProgram therapyProgram = session.get(TherapyProgram.class, entity.getId());
                if (therapyProgram != null) {
                    throw new DuplicateException("TherapyProgram already exists");
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
            TherapyProgram therapyProgram = session.get(TherapyProgram.class, id);
            session.remove(therapyProgram);
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
    public boolean update(TherapyProgram entity) throws SQLException, ClassNotFoundException {
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
    public List<String> getAllTherapyIds() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Query<String> query = session.createQuery("SELECT p.id FROM TherapyProgram p", String.class);
            List<String> therapyIds = query.list();
            transaction.commit();
            return therapyIds;
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException("Failed to get therapy IDs", e);
        } finally {
            session.close();
        }
    }

    @Override
    public TherapyProgram findById(String id) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            return session.get(TherapyProgram.class, id);
        } finally {
            session.close();
        }
    }

    @Override
    public TherapyProgram findByIdfortherapy(String selectedTherapyId) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            return session.get(TherapyProgram.class, selectedTherapyId);
        } finally {
            session.close();
        }
    }
}
