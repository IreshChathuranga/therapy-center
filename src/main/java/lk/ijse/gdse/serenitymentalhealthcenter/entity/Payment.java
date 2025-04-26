package lk.ijse.gdse.serenitymentalhealthcenter.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @Column (name = "payment_id")
    private String id;
    @Column(scale = 2 ,nullable = false)
    private BigDecimal amount;
    @Column(name = "payment_date" ,nullable = false)
    private Date paymentDate;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id")
    private TherapySession sessionId;

    public Payment(String paymentId, BigDecimal amount, java.sql.Date sessionDate, String sessionId) {
    }

}
