package site.shcrm.shcrm_backend.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JoinDTO {

    private String username;
    private String email;
    private String department;
    private String password;
    private int role;
}