package lk.ijse.gdse.serenitymentalhealthcenter.dao.custom.impl;

import lk.ijse.gdse.serenitymentalhealthcenter.config.FactoryConfiguration;
import lk.ijse.gdse.serenitymentalhealthcenter.dao.CrudDAO;
import lk.ijse.gdse.serenitymentalhealthcenter.dao.custom.PatientDAO;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.Patient;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.Therapist;
import lk.ijse.gdse.serenitymentalhealthcenter.exception.DuplicateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class PatientDAOImpl implements PatientDAO {
    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            // HQL query to get the last inserted patient ID (ordered descending)
            Query<String> query = session.createQuery("SELECT p.id FROM Patient p ORDER BY p.id DESC", String.class);
            query.setMaxResults(1);
            String lastId = query.uniqueResult();

            transaction.commit();
            session.close();

            if (lastId != null) {
                int lastNum = Integer.parseInt(lastId.replace("P", ""));
                int nextNum = lastNum + 1;
                return String.format("P%03d", nextNum); // Format: P001, P002, ...
            } else {
                return "P001";
            }
        } catch (Exception e) {
            transaction.rollback();
            session.close();
            throw new RuntimeException("no patient ID", e);
        }
    }

    @Override
    public List<Patient> getAll() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Query<Patient> query = session.createQuery("from Patient ", Patient.class);
        List<Patient> list = query.list();
        session.close();
        return list;
    }

    @Override
    public boolean save(Patient entity) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            // Only check for duplicate if the ID is not null
            if (entity.getId() != null) {
                Patient patient = session.get(Patient.class, entity.getId());
                if (patient != null) {
                    throw new DuplicateException("Patient already exists");
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
            Patient patient = session.get(Patient.class, id);
            session.remove(patient);
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
    public boolean update(Patient entity) throws SQLException, ClassNotFoundException {
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
    public List<String> getAllPatientIds() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Query<String> query = session.createQuery("SELECT p.id FROM Patient p", String.class);
            List<String> patientIds = query.list();
            transaction.commit();
            return patientIds;
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException("Failed to get patient IDs", e);
        } finally {
            session.close();
        }
    }

    @Override
    public Patient findById(String id) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            return session.get(Patient.class, id);
        } finally {
            session.close();
        }
    }

    @Override
    public Patient findByIdforname(String selectedPatientId) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            return session.get(Patient.class, selectedPatientId);
        } finally {
            session.close();
        }
    }
}
