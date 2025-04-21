package lk.ijse.gdse.serenitymentalhealthcenter.entity;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Booking {
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
