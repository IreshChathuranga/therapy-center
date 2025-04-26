package lk.ijse.gdse.serenitymentalhealthcenter.dao.custom.impl;

import lk.ijse.gdse.serenitymentalhealthcenter.config.FactoryConfiguration;
import lk.ijse.gdse.serenitymentalhealthcenter.dao.custom.ProgramRegistrationDAO;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.ProgramRegistration;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.TherapyProgram;
import lk.ijse.gdse.serenitymentalhealthcenter.exception.DuplicateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class ProgramRegistrationDAOImpl implements ProgramRegistrationDAO {
    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Query<String> query = session.createQuery("SELECT p.id FROM ProgramRegistration p ORDER BY p.id DESC", String.class);
            query.setMaxResults(1);
            String lastId = query.uniqueResult();

            transaction.commit();
            session.close();

            if (lastId != null) {
                int lastNum = Integer.parseInt(lastId.replace("PR", ""));
                int nextNum = lastNum + 1;
                return String.format("PR%03d", nextNum);
            } else {
                return "PR001";
            }
        } catch (Exception e) {
            transaction.rollback();
            session.close();
            throw new RuntimeException("no registrtion ID", e);
        }
    }

    @Override
    public List<ProgramRegistration> getAll() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        List<ProgramRegistration> list = session.createQuery(
                "from ProgramRegistration ", ProgramRegistration.class
        ).getResultList();

        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public boolean save(ProgramRegistration entity) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            if (entity.getProgramRegistrationId() != null) {
                ProgramRegistration programRegistration = session.get(ProgramRegistration.class, entity.getProgramRegistrationId());
                if (programRegistration != null) {
                    throw new DuplicateException("Registration already exists");
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
            ProgramRegistration programRegistration = session.get(ProgramRegistration.class, id);
            session.remove(programRegistration);
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
    public boolean update(ProgramRegistration entity) throws SQLException, ClassNotFoundException {
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
}
