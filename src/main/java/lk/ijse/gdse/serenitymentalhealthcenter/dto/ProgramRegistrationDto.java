package lk.ijse.gdse.serenitymentalhealthcenter.dto;

import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProgramRegistrationDto {
    private String programRegistrationId;
    private Date date;
    private BigDecimal advancePayment;
    private String patientId;
    private String programId;
}
