package lk.ijse.gdse.serenitymentalhealthcenter.dto.tm;

import jakarta.persistence.*;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.ProgramRegistration;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.TherapySession;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentTM {

    private String id;
    private BigDecimal amount;
    private Date paymentDate;

    private TherapySession sessionId;

//    private ProgramRegistration programRegistrationId;
}
