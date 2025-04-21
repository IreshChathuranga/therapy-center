package lk.ijse.gdse.serenitymentalhealthcenter.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "therapy_program")
public class TherapyProgram {
    @Id
    @Column (name = "program_id")
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String duration;

    @Column(precision = 10, scale = 2)
    private BigDecimal cost;

    @Column(nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "therapist_id" , nullable = false)
    private Therapist therapistId;

    @OneToMany(mappedBy = "programId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProgramRegistration> programRegistration;

    @OneToMany(mappedBy = "programId",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TherapySession> therapySessions;

    public TherapyProgram(String id, String name, String duration, BigDecimal cost, String description, Therapist therapistId) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.cost = cost;
        this.description = description;
        this.therapistId = therapistId;
    }

    public TherapyProgram(String id, String name, String duration, BigDecimal cost, String description, String therapistId) {
    }
}
