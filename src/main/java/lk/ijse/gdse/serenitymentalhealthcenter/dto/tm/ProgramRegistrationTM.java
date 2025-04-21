package lk.ijse.gdse.serenitymentalhealthcenter.dto.tm;

import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProgramRegistrationTM {
    private String programRegistrationId;
    private Date date;
    private BigDecimal advancePayment;
    private String patientId;
    private String programId;
}
