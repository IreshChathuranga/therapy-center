package lk.ijse.gdse.serenitymentalhealthcenter.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;


@NoArgsConstructor
@Data
@Entity
@Table(name = "Program_registration")
public class ProgramRegistration {
    @Id
    @Column (name = "programregistration_id")
    private String programRegistrationId;

    @Column(nullable = false)
    private Date date;

    @Column(precision = 10, scale = 2)
    private BigDecimal advancePayment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patientId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id" ,nullable = false)
    private TherapyProgram programId;

    public ProgramRegistration(String programRegistrationId, Date date, BigDecimal advancePayment, Patient patient, TherapyProgram therapyProgram) {
        this.programRegistrationId = programRegistrationId;
        this.date = date;
        this.advancePayment = advancePayment;
        this.patientId = patient;
        this.programId = therapyProgram;
    }
}
