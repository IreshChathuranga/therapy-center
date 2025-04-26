package lk.ijse.gdse.serenitymentalhealthcenter.bo;

import lk.ijse.gdse.serenitymentalhealthcenter.bo.custom.impl.*;

public class BOFactory {
    public static BOFactory boFactory;
    private BOFactory(){}
    public static BOFactory getInstance(){
        return boFactory==null ? new BOFactory() : boFactory;
    }

    public enum BOType{
        PATIENT,THERAPIST,THERAPYPROGRAM,SIGNIN,LOGIN,PROGRAMREGISTRATION,SESSIONBOOKING,PAYMENT,THERAPYSESSION
    }
    public SuperBO getBO(BOType boType){
        switch (boType){
            case PATIENT -> {
                return new PatientBOImpl();
            }
            case THERAPIST -> {
                return new TherapistBOImpl();
            }
            case THERAPYPROGRAM -> {
                return new TherapyProgramBOImpl();
            }
            case SIGNIN -> {
                return new SignInBOImpl();
            }
            case LOGIN -> {
                return new LoginBOImpl();
            }
            case PROGRAMREGISTRATION -> {
                return new ProgramRegistrationBOImpl();
            }
            case SESSIONBOOKING -> {
                return new SessionBookingBOImpl();
            }
            case PAYMENT -> {
                return new PaymentBOImpl();
            }
            case THERAPYSESSION -> {
                return new TherapySessionBOImpl();
            }
            default -> {
                return null;
            }
        }
    }
}
