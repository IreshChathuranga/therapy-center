package lk.ijse.gdse.serenitymentalhealthcenter.bo.custom.impl;

import lk.ijse.gdse.serenitymentalhealthcenter.bo.custom.SignInBO;
import lk.ijse.gdse.serenitymentalhealthcenter.dao.DAOFactory;
import lk.ijse.gdse.serenitymentalhealthcenter.dao.custom.PatientDAO;
import lk.ijse.gdse.serenitymentalhealthcenter.dao.custom.SignInDAO;
import lk.ijse.gdse.serenitymentalhealthcenter.dto.UserDto;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.Patient;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.User;

import java.sql.SQLException;
import java.util.List;

public class SignInBOImpl implements SignInBO {
    SignInDAO signInDAO = (SignInDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SIGNIN);
    @Override
    public boolean saveUser(UserDto userDto) throws SQLException, ClassNotFoundException {
        return signInDAO.save(new User(
                userDto.getName(),
                userDto.getRole(),
                userDto.getPhoneNumber(),
                userDto.getAddress(),
                userDto.getUserName(),
                userDto.getPassword()
        ));
    }

    @Override
    public List<String> getAllRoles() throws SQLException, ClassNotFoundException {
        return signInDAO.getAllRoles();
    }
}
