package lk.ijse.gdse.serenitymentalhealthcenter.dao.custom;

import lk.ijse.gdse.serenitymentalhealthcenter.dao.CrudDAO;
import lk.ijse.gdse.serenitymentalhealthcenter.dao.SuperDAO;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.Payment;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.Therapist;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.List;

public interface PaymentDAO extends CrudDAO<Payment> {
    void setSession(Session session);
    Payment findById(String id) throws SQLException, ClassNotFoundException;

    boolean saveWithSession(Session session, Payment payment);
    List<String> getAllPaymentIds() throws SQLException, ClassNotFoundException;
}
