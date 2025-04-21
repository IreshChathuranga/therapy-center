package lk.ijse.gdse.serenitymentalhealthcenter.dto.tm;

import lk.ijse.gdse.serenitymentalhealthcenter.entity.Therapist;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TherapyProgramTM {
    private String id;
    private String name;
    private String duration;
    private BigDecimal cost;
    private String description;
    private String therapistId;

}
