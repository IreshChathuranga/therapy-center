package lk.ijse.gdse.serenitymentalhealthcenter.entity;


import jakarta.persistence.*;
import lombok.*;;
@NoArgsConstructor
@Data
@Entity
@Table(name = "user")
public class User {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String role;

    @Column(name = "phone_number" ,nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String address;
    @Id
    @Column (name = "user_name")
    private String userName;

    @Column(nullable = false)
    private String password;

    public User(String name, String role, String phoneNumber, String address, String userName, String password) {
        this.name = name;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.userName = userName;
        this.password = password;
    }
}
