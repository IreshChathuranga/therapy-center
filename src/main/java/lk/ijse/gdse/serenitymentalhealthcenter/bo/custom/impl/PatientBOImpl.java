package lk.ijse.gdse.serenitymentalhealthcenter.bo.custom.impl;

import lk.ijse.gdse.serenitymentalhealthcenter.bo.custom.PatientBO;
import lk.ijse.gdse.serenitymentalhealthcenter.dao.DAOFactory;
import lk.ijse.gdse.serenitymentalhealthcenter.dao.custom.PatientDAO;
import lk.ijse.gdse.serenitymentalhealthcenter.dto.PatientDto;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.Patient;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class PatientBOImpl implements PatientBO {
    PatientDAO patientDAO = (PatientDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PATIENT);
    @Override
    public String getNextPatientId() throws SQLException, ClassNotFoundException {
        return patientDAO.getNextId();
    }

    @Override
    public List<PatientDto> getAllPatient() throws SQLException, ClassNotFoundException {
        List<PatientDto> patientDtos = new ArrayList<>();

        List<Patient> all = patientDAO.getAll();
        for (Patient patient : all){
            patientDtos.add(new PatientDto(
                    patient.getId(),
                    patient.getName(),
                    patient.getAddress(),
                    patient.getGender(),
                    patient.getPhoneNumber(),
                    patient.getYearOfBirth(),
                    patient.getRegistrationDate()
            ));
        }
        System.out.println(patientDtos);
        return patientDtos;
    }

    @Override
    public boolean savePatient(PatientDto patientDto) throws SQLException, ClassNotFoundException {
        return patientDAO.save(new Patient(
                patientDto.getId(),
                patientDto.getName(),
                patientDto.getAddress(),
                patientDto.getGender(),
                patientDto.getPhoneNumber(),
                patientDto.getYearOfBirth(),
                patientDto.getRegistrationDate()
        ));
    }

    @Override
    public boolean deletePatient(String patientId) throws SQLException, ClassNotFoundException {
        return patientDAO.delete(patientId);
    }

    @Override
    public boolean upadatePatient(PatientDto patientDto) throws SQLException, ClassNotFoundException {
        return patientDAO.update(new Patient(
                patientDto.getId(),
                patientDto.getName(),
                patientDto.getAddress(),
                patientDto.getGender(),
                patientDto.getPhoneNumber(),
                patientDto.getYearOfBirth(),
                patientDto.getRegistrationDate()
        ));
    }

    @Override
    public List<String> getAllPatientIds() throws SQLException, ClassNotFoundException {
        return patientDAO.getAllPatientIds();
    }

    @Override
    public Patient findById(String selectedPatientId) throws SQLException, ClassNotFoundException {
        return patientDAO.findByIdforname(selectedPatientId);
    }
}

