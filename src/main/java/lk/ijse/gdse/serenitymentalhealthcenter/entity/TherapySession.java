package lk.ijse.gdse.serenitymentalhealthcenter.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "therapy_session")
public class TherapySession {
    @Id
    @Column (name = "session_id")
    private String id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patientId;
    @Column(name = "phone_number" ,nullable = false)
    private String phoneNumber;
    @Column(name = "session_duration" ,nullable = false)
    private String sessionDuration;
    @Column(name = "session_date" ,nullable = false)
    private Date sessionDate;
    @Column(name = "place_date" ,nullable = false)
    private Date placeDate;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "therapist_id" ,nullable = false)
    private Therapist therapistId;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "program_id" ,nullable = false)
    private TherapyProgram programId;
    @Column(scale = 2 ,nullable = false)
    private BigDecimal payment;
    @Column(name = "total_remaining_amount", scale = 2 ,nullable = false)
    private BigDecimal totalRemainingAmount;
    @Column(name = "payment_status" ,nullable = false)
    private boolean paymentStatus;
    @OneToOne(mappedBy = "sessionId", cascade = CascadeType.ALL)
    private Payment paymentId;

//    public TherapySession(String id, Patient patientId, String phoneNumber, String sessionDuration, Date sessionDate, Date placeDate, Therapist therapistId, TherapyProgram programId, BigDecimal payment, BigDecimal totalRemainingAmount, boolean paymentStatus) {
//        this.id = id;
//        this.patientId = patientId;
//        this.phoneNumber = phoneNumber;
//        this.sessionDuration = sessionDuration;
//        this.sessionDate = sessionDate;
//        this.placeDate = placeDate;
//        this.therapistId = therapistId;
//        this.programId = programId;
//        this.payment = payment;
//        this.totalRemainingAmount = totalRemainingAmount;
//        this.paymentStatus = paymentStatus;
//    }
}
