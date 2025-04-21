package lk.ijse.gdse.serenitymentalhealthcenter.bo.custom;

import lk.ijse.gdse.serenitymentalhealthcenter.bo.SuperBO;
import lk.ijse.gdse.serenitymentalhealthcenter.dto.UserDto;

import java.sql.SQLException;
import java.util.List;

public interface SignInBO extends SuperBO {
    boolean saveUser(UserDto userDto) throws SQLException, ClassNotFoundException;
    List<String> getAllRoles() throws SQLException, ClassNotFoundException;
}
