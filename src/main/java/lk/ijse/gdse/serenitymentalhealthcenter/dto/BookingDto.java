package lk.ijse.gdse.serenitymentalhealthcenter.dto;

import lk.ijse.gdse.serenitymentalhealthcenter.entity.Patient;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.Payment;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.Therapist;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.TherapyProgram;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookingDto {
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
//    private List<Payment> paymentDTOS;


}
