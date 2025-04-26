package lk.ijse.gdse.serenitymentalhealthcenter.bo.custom.impl;

import lk.ijse.gdse.serenitymentalhealthcenter.bo.custom.TherapySessionBO;
import lk.ijse.gdse.serenitymentalhealthcenter.dao.DAOFactory;
import lk.ijse.gdse.serenitymentalhealthcenter.dao.custom.*;
import lk.ijse.gdse.serenitymentalhealthcenter.dto.TherapySessionDto;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TherapySessionBOImpl implements TherapySessionBO {
    TherapyProgramDAO therapyProgramDAO = (TherapyProgramDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.THERAPYPROGRAM);
    PatientDAO patientDAO = (PatientDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PATIENT);
    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PAYMENT);
    TherapistDAO therapistDAO = (TherapistDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.THERAPIST);
    TherapySessionDAO therapySessionDAO = (TherapySessionDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.THERAPYSESSION);
    @Override
    public List<TherapySessionDto> getAllSession() throws SQLException, ClassNotFoundException {
        List<TherapySessionDto> therapySessionDtos = new ArrayList<>();

        List<TherapySession> all = therapySessionDAO.getAll();
        for (TherapySession TherapySession : all){
            Patient patient = TherapySession.getPatientId();
            Therapist therapist = TherapySession.getTherapistId();
            TherapyProgram therapyProgram = TherapySession.getProgramId();
            Payment payment = TherapySession.getPaymentId();
            therapySessionDtos.add(new TherapySessionDto(
                    TherapySession.getId(),
                    patient.getId(),
                    TherapySession.getPhoneNumber(),
                    TherapySession.getSessionDuration(),
                    TherapySession.getSessionDate(),
                    TherapySession.getPlaceDate(),
                    therapist.getId(),
                    therapyProgram.getId(),
                    TherapySession.getPayment(),
                    TherapySession.getTotalRemainingAmount(),
                    payment.getId()
            ));
        }
        return therapySessionDtos;
    }

    @Override
    public boolean deleteSession(String sessionId) throws SQLException, ClassNotFoundException {
        return therapySessionDAO.delete(sessionId);
    }

    @Override
    public boolean upadateSession(TherapySessionDto therapySessionDto) throws SQLException, ClassNotFoundException {
        Therapist therapist = therapistDAO.findById(therapySessionDto.getTherapistId());
        Patient patient = patientDAO.findById(therapySessionDto.getPatientId());
        TherapyProgram therapyProgram = therapyProgramDAO.findById(therapySessionDto.getProgramId());
        Payment payment = paymentDAO.findById(therapySessionDto.getPaymentId());
        return therapySessionDAO.update(new TherapySession(
                therapySessionDto.getId(),
                patient,
                therapySessionDto.getPhoneNumber(),
                therapySessionDto.getSessionDuration(),
                therapySessionDto.getSessionDate(),
                therapySessionDto.getPlaceDate(),
                therapist,
                therapyProgram,
                therapySessionDto.getPayment(),
                therapySessionDto.getTotalRemainingAmount(),
                payment
        ));
    }
}
