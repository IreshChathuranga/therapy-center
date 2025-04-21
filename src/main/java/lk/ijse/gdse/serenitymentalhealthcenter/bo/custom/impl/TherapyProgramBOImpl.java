package lk.ijse.gdse.serenitymentalhealthcenter.bo.custom.impl;

import lk.ijse.gdse.serenitymentalhealthcenter.bo.custom.TherapyProgramBO;
import lk.ijse.gdse.serenitymentalhealthcenter.config.FactoryConfiguration;
import lk.ijse.gdse.serenitymentalhealthcenter.dao.DAOFactory;
import lk.ijse.gdse.serenitymentalhealthcenter.dao.custom.TherapistDAO;
import lk.ijse.gdse.serenitymentalhealthcenter.dao.custom.TherapyProgramDAO;
import lk.ijse.gdse.serenitymentalhealthcenter.dto.TherapistDto;
import lk.ijse.gdse.serenitymentalhealthcenter.dto.TherapyProgramDto;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.Therapist;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.TherapyProgram;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TherapyProgramBOImpl implements TherapyProgramBO {
    TherapyProgramDAO therapyProgramDAO = (TherapyProgramDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.THERAPYPROGRAM);
    TherapistDAO therapistDAO = (TherapistDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.THERAPIST);
    @Override
    public String getNextTherapyId() throws SQLException, ClassNotFoundException {
        return therapyProgramDAO.getNextId();
    }

    @Override
    public List<TherapyProgramDto> getAllTherapy() throws SQLException, ClassNotFoundException {
        List<TherapyProgramDto> therapyProgramDtos = new ArrayList<>();

        List<TherapyProgram> all = therapyProgramDAO.getAll();
        for (TherapyProgram TherapyProgram : all){
            Therapist therapist = TherapyProgram.getTherapistId();
            therapyProgramDtos.add(new TherapyProgramDto(
                    TherapyProgram.getId(),
                    TherapyProgram.getName(),
                    TherapyProgram.getDuration(),
                    TherapyProgram.getCost(),
                    TherapyProgram.getDescription(),
                    therapist.getId()
            ));
        }
        return therapyProgramDtos;
    }

    @Override
    public boolean saveTherapy(TherapyProgramDto therapyProgramDto) throws SQLException, ClassNotFoundException {
        Therapist therapist = therapistDAO.findById(therapyProgramDto.getTherapistId());
        return therapyProgramDAO.save(new TherapyProgram(
                therapyProgramDto.getId(),
                therapyProgramDto.getName(),
                therapyProgramDto.getDuration(),
                therapyProgramDto.getCost(),
                therapyProgramDto.getDescription(),
                therapist
        ));
    }

    @Override
    public boolean deleteTherapy(String therapyId) throws SQLException, ClassNotFoundException {
        return therapyProgramDAO.delete(therapyId);
    }

    @Override
    public boolean upadateTherapy(TherapyProgramDto therapyProgramDto) throws SQLException, ClassNotFoundException {
        Therapist therapist = therapistDAO.findById(therapyProgramDto.getTherapistId());
        return therapyProgramDAO.update(new TherapyProgram(
                therapyProgramDto.getId(),
                therapyProgramDto.getName(),
                therapyProgramDto.getDuration(),
                therapyProgramDto.getCost(),
                therapyProgramDto.getDescription(),
                therapist
        ));
    }

    @Override
    public List<String> getAllTherapyIds() throws SQLException, ClassNotFoundException {
        return therapyProgramDAO.getAllTherapyIds();
    }

    @Override
    public TherapyProgram findById(String selectedTherapyId) throws SQLException, ClassNotFoundException {
        return therapyProgramDAO.findByIdfortherapy(selectedTherapyId);
    }

}
