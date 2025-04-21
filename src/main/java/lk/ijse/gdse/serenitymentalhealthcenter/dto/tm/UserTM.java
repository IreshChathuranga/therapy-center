package lk.ijse.gdse.serenitymentalhealthcenter.dto.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserTM {
    private String name;
    private String role;
    private String phoneNumber;
    private String address;
    private String userName;
    private String password;
}
