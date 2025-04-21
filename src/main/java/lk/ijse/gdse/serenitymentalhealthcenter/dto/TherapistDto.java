package lk.ijse.gdse.serenitymentalhealthcenter.dto;

import lk.ijse.gdse.serenitymentalhealthcenter.entity.Therapist;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TherapistDto {
    private String id;
    private String name;
    private String specialization;
    private String experienceYear;
    private String phoneNumber;
    private String assignedProgram;
}
