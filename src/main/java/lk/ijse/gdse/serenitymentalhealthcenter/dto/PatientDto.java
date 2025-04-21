package lk.ijse.gdse.serenitymentalhealthcenter.dto;

import lombok.*;

import java.sql.Date;
import java.time.Year;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PatientDto {
    private String id;
    private String name;
    private String address;
    private String gender;
    private String phoneNumber;
    private Year yearOfBirth;
    private Date registrationDate;
}
