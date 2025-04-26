package lk.ijse.gdse.serenitymentalhealthcenter.dao;

import lk.ijse.gdse.serenitymentalhealthcenter.dao.custom.impl.*;

public class DAOFactory {
    public static DAOFactory daoFactory;
    private DAOFactory(){}
    public static DAOFactory getInstance(){
        return daoFactory==null ? new DAOFactory() : daoFactory;
    }
    public enum DAOType{
        PATIENT,THERAPIST,THERAPYPROGRAM,SIGNIN,PROGRAMREGISTRATION,SESSIONBOOKING,PAYMENT,THERAPYSESSION
    }
    public SuperDAO getDAO(DAOType daoType){
        switch (daoType){
            case PATIENT -> {
                return new PatientDAOImpl();
            }
            case THERAPIST -> {
                return new TherapistDAOImpl();
            }
            case THERAPYPROGRAM -> {
                return new TherapyProgramDAOImpl();
            }
            case SIGNIN -> {
                return new SignInDAOImpl();
            }
            case PROGRAMREGISTRATION -> {
                return new ProgramRegistrationDAOImpl();
            }
            case SESSIONBOOKING -> {
                return new SessionBookingDAOImpl();
            }
            case PAYMENT -> {
                return new PaymentDAOImpl();
            }
            case THERAPYSESSION -> {
                return new TherapySessionDAOImpl();
            }
            default -> {
                return null;
            }
        }
    }
}
