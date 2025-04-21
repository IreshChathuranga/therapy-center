package lk.ijse.gdse.serenitymentalhealthcenter.dao.custom;

import lk.ijse.gdse.serenitymentalhealthcenter.dao.CrudDAO;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface SignInDAO extends CrudDAO<User> {
    List<String> getAllRoles() throws SQLException, ClassNotFoundException;
}
