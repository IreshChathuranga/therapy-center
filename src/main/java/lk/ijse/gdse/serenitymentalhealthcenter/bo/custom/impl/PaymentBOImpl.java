package lk.ijse.gdse.serenitymentalhealthcenter.bo.custom.impl;

import lk.ijse.gdse.serenitymentalhealthcenter.bo.custom.PaymentBO;
import lk.ijse.gdse.serenitymentalhealthcenter.dao.DAOFactory;
import lk.ijse.gdse.serenitymentalhealthcenter.dao.custom.PaymentDAO;

import java.sql.SQLException;
import java.util.List;

public class PaymentBOImpl implements PaymentBO {
    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PAYMENT);

    @Override
    public String getNextPaymentId() throws SQLException, ClassNotFoundException {
        return paymentDAO.getNextId();
    }

    @Override
    public List<String> getAllPaymentIds() throws SQLException, ClassNotFoundException {
        return paymentDAO.getAllPaymentIds();
    }
}
