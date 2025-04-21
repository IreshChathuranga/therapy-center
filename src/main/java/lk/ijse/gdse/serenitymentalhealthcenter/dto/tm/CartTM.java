package lk.ijse.gdse.serenitymentalhealthcenter.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CartTM {
    private String sessionId;
    private Date placeDate;
    private String sessionDuration;
    private Date sessionDate;
    private String paymentId;
    private BigDecimal totalRemainingAmount;
    private BigDecimal payment;
    private boolean paymentStatus;
    private String therapistId;
    private String therapyId;
    private String patientId;
    private Button removeBtn;
}
