package site.shcrm.shcrm_backend.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Schema(description = "회원 가입 데이터 전송 객체")
public class JoinDTO {

    @Schema(description = "직원 ID", example = "123")
    private Integer employeeId;

    @Schema(description = "비밀번호", example = "hashedPassword123")
    private String passwordHash;

    @Schema(description = "이름", example = "홍")
    private String firstName;

    @Schema(description = "성", example = "길동")
    private String lastName;

    @Schema(description = "이메일", example = "hong.gildong@example.com")
    private String email;

    @Schema(description = "전화번호", example = "010-1234-5678")
    private String phoneNumber;

    @Schema(description = "입사일", example = "2024-01-01")
    private LocalDate hireDate;

    @Schema(description = "직무 제목", example = "소프트웨어 엔지니어")
    private String jobTitle;
}