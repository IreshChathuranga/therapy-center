package lk.ijse.gdse.serenitymentalhealthcenter.dto.tm;

import jakarta.persistence.*;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.Patient;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.Payment;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.Therapist;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.TherapyProgram;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TherapySessionTM {
    private String id;

    private String patientId;
    private String phoneNumber;
    private String sessionDuration;
    private Date sessionDate;
    private Date placeDate;
    private String therapistId;
    private String programId;
    private BigDecimal payment;
    private BigDecimal totalRemainingAmount;
    private String paymentId;
}
