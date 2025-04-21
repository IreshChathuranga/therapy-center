package lk.ijse.gdse.serenitymentalhealthcenter.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto {
    private String name;
    private String role;
    private String phoneNumber;
    private String address;
    private String userName;
    private String password;
}
