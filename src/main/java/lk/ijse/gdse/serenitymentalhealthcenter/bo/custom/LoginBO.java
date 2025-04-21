package lk.ijse.gdse.serenitymentalhealthcenter.bo.custom;

import lk.ijse.gdse.serenitymentalhealthcenter.bo.SuperBO;
import lk.ijse.gdse.serenitymentalhealthcenter.dto.UserDto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface LoginBO extends SuperBO {
    List<UserDto> loadUserData() throws SQLException, ClassNotFoundException;
}
