package lk.ijse.gdse.serenitymentalhealthcenter.dto.tm;

import jakarta.persistence.*;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.Patient;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.Payment;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.Therapist;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.TherapyProgram;

import java.math.BigDecimal;
import java.util.Date;

public class BookingTM {
    private String id;
    private Patient patientId;
    private String phoneNumber;
    private String sessionDuration;
    private Date sessionDate;
    private Date placeDate;

    private Therapist therapistId;
    private TherapyProgram programId;

    private BigDecimal payment;
    private BigDecimal totalRemainingAmount;
    private boolean paymentStatus;
    private Payment paymentId;
}
