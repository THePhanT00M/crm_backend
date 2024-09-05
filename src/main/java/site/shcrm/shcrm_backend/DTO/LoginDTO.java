package site.shcrm.shcrm_backend.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "로그인 데이터 전송 객체")
public class LoginDTO {

    @Schema(description = "직원 ID", example = "emp123")
    private String employeeId;

    @Schema(description = "비밀번호", example = "password123")
    private String password;
}
