package lk.ijse.gdse.serenitymentalhealthcenter.bo.custom.impl;

import lk.ijse.gdse.serenitymentalhealthcenter.bo.custom.SessionBookingBO;
import lk.ijse.gdse.serenitymentalhealthcenter.config.FactoryConfiguration;
import lk.ijse.gdse.serenitymentalhealthcenter.dao.DAOFactory;
import lk.ijse.gdse.serenitymentalhealthcenter.dao.custom.*;
import lk.ijse.gdse.serenitymentalhealthcenter.dto.BookingDto;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;

public class SessionBookingBOImpl implements SessionBookingBO {
    SessionBookingDAO sessionBookingDAO = (SessionBookingDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SESSIONBOOKING);
    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PAYMENT);
    PatientDAO patientDAO = (PatientDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PATIENT);
    TherapistDAO therapistDAO = (TherapistDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.THERAPIST);
    TherapyProgramDAO therapyProgramDAO = (TherapyProgramDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.THERAPYPROGRAM);

    @Override
    public String getNextSessionId() throws SQLException, ClassNotFoundException {
        return sessionBookingDAO.getNextId();
    }

    @Override
    public boolean savePlaceBooking(BookingDto bookingDto) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

            try{
                Patient patientId = patientDAO.findById(bookingDto.getPatientId());
                Therapist therapistId = therapistDAO.findById(bookingDto.getTherapistId());
                TherapyProgram therapyProgramId = therapyProgramDAO.findById(bookingDto.getProgramId());
                Payment paymentId = paymentDAO.findById(bookingDto.getPaymentId());
                TherapySession therapySession = new TherapySession(
                        bookingDto.getId(),
                        patientId,
                        bookingDto.getPhoneNumber(),
                        bookingDto.getSessionDuration(),
                        bookingDto.getSessionDate(),
                        bookingDto.getPlaceDate(),
                        therapistId,
                        therapyProgramId,
                        bookingDto.getPayment(),
                        bookingDto.getTotalRemainingAmount(),
                        paymentId
                );

                boolean isBookingSaved = sessionBookingDAO.saveWithSession(session,therapySession);

                    if (isBookingSaved) {
                        System.out.println(bookingDto.getPaymentId());
                        Payment payment = new Payment(bookingDto.getPaymentId(),bookingDto.getTotalRemainingAmount(),bookingDto.getSessionDate(),therapySession);
                        System.out.println(payment.getId());
                        boolean isPaymentSaved = paymentDAO.saveWithSession(session,payment);

                        if (isPaymentSaved) {
                            transaction.commit();
                            return true;
                        }
                    }

                transaction.rollback();
                return false;

            } catch (Exception e) {
                if (transaction != null) transaction.rollback();
                e.printStackTrace();
                return false;
            }
        }
}
