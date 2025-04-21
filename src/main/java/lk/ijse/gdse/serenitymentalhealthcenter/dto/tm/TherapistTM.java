package lk.ijse.gdse.serenitymentalhealthcenter.dto.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TherapistTM {
    private String id;
    private String name;
    private String specialization;
    private String experienceYear;
    private String phoneNumber;
    private String assignedProgram;
}
