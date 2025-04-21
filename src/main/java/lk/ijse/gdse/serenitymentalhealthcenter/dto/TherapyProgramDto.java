package lk.ijse.gdse.serenitymentalhealthcenter.dto;

import lk.ijse.gdse.serenitymentalhealthcenter.entity.Therapist;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TherapyProgramDto {
    private String id;
    private String name;
    private String duration;
    private BigDecimal cost;
    private String description;
    private String therapistId;

//    public TherapyProgramDto(String therapyId, String therayName, String duration, BigDecimal fee, String description, String therapistId) {
//        this.id = therapyId;
//        this.name = therayName;
//        this.duration = duration;
//        this.cost = fee;
//        this.description = description;
//        this.therapistId = therapistId;
//    }

////    public TherapyProgramDto(String therapyId, String therayName, String duration, Double fee, String description, String therapistId) {
////
////    }
//
//    public TherapyProgramDto(String id, String name, String duration, BigDecimal cost, String description, String s) {
//    }
//
//    public TherapyProgramDto(String id, String name, String duration, BigDecimal cost, String description, String id1, String name1) {
//    }

}
