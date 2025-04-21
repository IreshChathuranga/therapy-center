package lk.ijse.gdse.serenitymentalhealthcenter.bo.custom.impl;

import lk.ijse.gdse.serenitymentalhealthcenter.bo.custom.ProgramRegistrationBO;
import lk.ijse.gdse.serenitymentalhealthcenter.dao.DAOFactory;
import lk.ijse.gdse.serenitymentalhealthcenter.dao.custom.PatientDAO;
import lk.ijse.gdse.serenitymentalhealthcenter.dao.custom.ProgramRegistrationDAO;
import lk.ijse.gdse.serenitymentalhealthcenter.dao.custom.TherapyProgramDAO;
import lk.ijse.gdse.serenitymentalhealthcenter.dto.ProgramRegistrationDto;
import lk.ijse.gdse.serenitymentalhealthcenter.dto.TherapyProgramDto;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.Patient;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.ProgramRegistration;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.Therapist;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.TherapyProgram;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProgramRegistrationBOImpl implements ProgramRegistrationBO {
    ProgramRegistrationDAO programRegistrationDAO = (ProgramRegistrationDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PROGRAMREGISTRATION);
    PatientDAO patientDAO = (PatientDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PATIENT);
    TherapyProgramDAO therapyProgramDAO = (TherapyProgramDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.THERAPYPROGRAM);

    @Override
    public String getNextRegistrationId() throws SQLException, ClassNotFoundException {
        return programRegistrationDAO.getNextId();
    }

    @Override
    public List<ProgramRegistrationDto> getAllRegistration() throws SQLException, ClassNotFoundException {
        List<ProgramRegistrationDto> programRegistrationDtos = new ArrayList<>();

        List<ProgramRegistration> all = programRegistrationDAO.getAll();
        for (ProgramRegistration ProgramRegistration : all){
            Patient patient = ProgramRegistration.getPatientId();
            TherapyProgram therapyProgram = ProgramRegistration.getProgramId();
            programRegistrationDtos.add(new ProgramRegistrationDto(
                    ProgramRegistration.getProgramRegistrationId(),
                    ProgramRegistration.getDate(),
                    ProgramRegistration.getAdvancePayment(),
                    patient.getId(),
                    therapyProgram.getId()
            ));
        }
        return programRegistrationDtos;
    }

    @Override
    public boolean saveRegistration(ProgramRegistrationDto programRegistrationDto) throws SQLException, ClassNotFoundException {
        Patient patient = patientDAO.findById(programRegistrationDto.getPatientId());
        TherapyProgram therapyProgram = therapyProgramDAO.findById(programRegistrationDto.getProgramId());
        return programRegistrationDAO.save(new ProgramRegistration(
                programRegistrationDto.getProgramRegistrationId(),
                programRegistrationDto.getDate(),
                programRegistrationDto.getAdvancePayment(),
                patient,
                therapyProgram
        ));
    }

    @Override
    public boolean deleteRegistration(String registrationId) throws SQLException, ClassNotFoundException {
        return programRegistrationDAO.delete(registrationId);
    }

    @Override
    public boolean upadateRegistration(ProgramRegistrationDto programRegistrationDto) throws SQLException, ClassNotFoundException {
        Patient patient = patientDAO.findById(programRegistrationDto.getPatientId());
        TherapyProgram therapyProgram = therapyProgramDAO.findById(programRegistrationDto.getProgramId());
        return programRegistrationDAO.update(new ProgramRegistration(
                programRegistrationDto.getProgramRegistrationId(),
                programRegistrationDto.getDate(),
                programRegistrationDto.getAdvancePayment(),
                patient,
                therapyProgram
        ));
    }
}
