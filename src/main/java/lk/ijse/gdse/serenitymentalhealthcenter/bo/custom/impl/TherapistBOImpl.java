package lk.ijse.gdse.serenitymentalhealthcenter.bo.custom.impl;

import lk.ijse.gdse.serenitymentalhealthcenter.bo.custom.TherapistBO;
import lk.ijse.gdse.serenitymentalhealthcenter.dao.DAOFactory;
import lk.ijse.gdse.serenitymentalhealthcenter.dao.custom.TherapistDAO;
import lk.ijse.gdse.serenitymentalhealthcenter.dto.TherapistDto;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.Therapist;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TherapistBOImpl implements TherapistBO {
    TherapistDAO therapistDAO = (TherapistDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.THERAPIST);
    @Override
    public String getNextTherapistId() throws SQLException, ClassNotFoundException {
        String lastId = therapistDAO.getNextId();

        if (lastId != null) {
            int newId = Integer.parseInt(lastId.substring(1)) + 1;
            return String.format("T%03d", newId);
        } else {
            return "T001";
        }
    }

    @Override
    public List<TherapistDto> getAllTherapist() throws SQLException, ClassNotFoundException {
        List<TherapistDto> therapistDtos = new ArrayList<>();

        List<Therapist> all = therapistDAO.getAll();
        for (Therapist therapist : all){
            therapistDtos.add(new TherapistDto(
                    therapist.getId(),
                    therapist.getName(),
                    therapist.getSpecialization(),
                    therapist.getExperienceYear(),
                    therapist.getPhoneNumber(),
                    therapist.getAssignedProgram()
            ));
        }
        System.out.println(therapistDtos);
        return therapistDtos;
    }

    @Override
    public boolean saveTherapist(TherapistDto therapistDto) throws SQLException, ClassNotFoundException {
        return therapistDAO.save(new Therapist(
                therapistDto.getId(),
                therapistDto.getName(),
                therapistDto.getSpecialization(),
                therapistDto.getExperienceYear(),
                therapistDto.getPhoneNumber(),
                therapistDto.getAssignedProgram()
        ));
    }

    @Override
    public boolean deleteTherapist(String therapistId) throws SQLException, ClassNotFoundException {
        return therapistDAO.delete(therapistId);
    }

    @Override
    public boolean upadateTherapist(TherapistDto therapistDto) throws SQLException, ClassNotFoundException {
        return therapistDAO.update(new Therapist(
                therapistDto.getId(),
                therapistDto.getName(),
                therapistDto.getSpecialization(),
                therapistDto.getExperienceYear(),
                therapistDto.getPhoneNumber(),
                therapistDto.getAssignedProgram()
        ));
    }
    @Override
    public List<String> getAllTherapistIds() throws SQLException, ClassNotFoundException {
        return therapistDAO.getAllTherapistIds();
    }
    @Override
    public Therapist findById(String selectedTherapistId) throws SQLException, ClassNotFoundException {
        return therapistDAO.findByIdfortherapist(selectedTherapistId);
    }
}
