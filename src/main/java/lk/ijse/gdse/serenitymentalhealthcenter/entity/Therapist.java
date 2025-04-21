package lk.ijse.gdse.serenitymentalhealthcenter.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "therapist")
public class Therapist {
    @Id
    @Column (name = "therapist_id")
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String specialization;

    @Column(name = "experience_year" ,nullable = false)
    private String experienceYear;

    @Column(name = "phone_number" ,nullable = false)
    private String phoneNumber;

    @Column(name ="assigned_program")
    private String assignedProgram;

    @OneToMany(mappedBy = "therapistId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TherapySession> therapySessions;

    @OneToMany(mappedBy = "therapistId", cascade = CascadeType.ALL)
    private List<TherapyProgram> therapyProgram;

    public Therapist(String id, String name, String specialization, String experienceYear, String phoneNumber, String assignedProgram) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.experienceYear = experienceYear;
        this.phoneNumber = phoneNumber;
        this.assignedProgram = assignedProgram;
    }
}
