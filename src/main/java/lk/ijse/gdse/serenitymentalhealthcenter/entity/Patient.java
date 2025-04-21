package lk.ijse.gdse.serenitymentalhealthcenter.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Year;
import java.sql.Date;
import java.util.List;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "patient")
public class Patient {
    @Id
    @Column (name = "patient_id")
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String gender;

    @Column(name = "phone_number" ,nullable = false)
    private String phoneNumber;

    @Column(name = "year_of_birth" ,nullable = false)
    private Year yearOfBirth;

    @Column(name = "registration_date" ,nullable = false)
    private Date registrationDate;

    @OneToMany(mappedBy = "patientId", fetch = FetchType.LAZY)
    private List<ProgramRegistration> programRegistration;

    @OneToMany(mappedBy = "patientId", fetch = FetchType.LAZY)
    private List<TherapySession> therapySessions;

    public Patient(String id, String name, String address, String gender, String phoneNumber, Year yearOfBirth, Date registrationDate) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.yearOfBirth = yearOfBirth;
        this.registrationDate = registrationDate;
    }
}
