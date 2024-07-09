package site.shcrm.shcrm_backend.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    private String id;

    private String name;
    private String email;
    private String department;
    private String password;

    private String role;
}
