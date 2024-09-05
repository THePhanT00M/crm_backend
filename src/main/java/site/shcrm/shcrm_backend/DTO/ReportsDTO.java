package site.shcrm.shcrm_backend.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Schema(description = "보고서 데이터 전송 객체")
public class ReportsDTO {

    @Schema(description = "보고서 ID", example = "1")
    private int reportId;

    @Schema(description = "제목", example = "샘플 보고서 제목")
    private String title;

    @Schema(description = "내용", example = "이것은 샘플 보고서의 내용입니다.")
    private String content;

    @Schema(description = "상태", example = "진행중")
    private String status;

    @Schema(description = "정책 ID", example = "1")
    private Integer policyId;

    @Schema(description = "카테고리 ID", example = "1")
    private Integer categoryId;

    @Schema(description = "직원 ID", example = "1")
    private Integer employeeId;
}
