package lk.ijse.gdse.serenitymentalhealthcenter.bo.custom;

import lk.ijse.gdse.serenitymentalhealthcenter.bo.SuperBO;

import java.sql.SQLException;

public interface PaymentBO extends SuperBO {
    String getNextPaymentId() throws SQLException, ClassNotFoundException;
}
