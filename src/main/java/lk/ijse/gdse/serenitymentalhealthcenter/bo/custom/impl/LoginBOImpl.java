package lk.ijse.gdse.serenitymentalhealthcenter.bo.custom.impl;

import lk.ijse.gdse.serenitymentalhealthcenter.bo.custom.LoginBO;
import lk.ijse.gdse.serenitymentalhealthcenter.dao.DAOFactory;
import lk.ijse.gdse.serenitymentalhealthcenter.dao.custom.SignInDAO;
import lk.ijse.gdse.serenitymentalhealthcenter.dto.UserDto;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginBOImpl implements LoginBO {
    SignInDAO signInDAO = (SignInDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SIGNIN);
    @Override
    public List<UserDto> loadUserData() throws SQLException, ClassNotFoundException {
        List<User> user = signInDAO.getAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (User use : user) {
            userDtos.add(new UserDto(use.getName(), use.getRole(), use.getPhoneNumber(), use.getAddress(), use.getUserName(), use.getPassword()));
        };
        return userDtos;
    }
}
