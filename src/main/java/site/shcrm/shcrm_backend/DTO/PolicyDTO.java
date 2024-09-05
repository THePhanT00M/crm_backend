package site.shcrm.shcrm_backend.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Schema(description = "정책 데이터 전송 객체")
public class PolicyDTO {

    @Schema(description = "정책 ID", example = "1")
    private int policyId;

    @Schema(description = "정책 이름", example = "연차 정책")
    private String policyName;

    @Schema(description = "정책 설명", example = "직원들에게 제공되는 연차 정책")
    private String description;

    @Schema(description = "발효일", example = "2024-01-01")
    private LocalDate effectiveDate;

    @Schema(description = "만료일", example = "2024-12-31")
    private LocalDate expirationDate;
}