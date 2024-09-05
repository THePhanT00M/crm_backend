package site.shcrm.shcrm_backend.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Schema(description = "보고서 이력 데이터 전송 객체")
public class ReportHistoryDTO {

    @Schema(description = "이력 ID", example = "1")
    private int historyId;

    @Schema(description = "보고서 ID", example = "1")
    private int reportId;

    @Schema(description = "직원 ID", example = "1")
    private int employeeId;

    @Schema(description = "변경 요약", example = "보고서 제목 변경")
    private String changeSummary;

    @Schema(description = "이전 내용", example = "이전 보고서 내용")
    private String previousContent;

    @Schema(description = "새로운 내용", example = "새로운 보고서 내용")
    private String newContent;

    @Schema(description = "변경 전 상태", example = "진행중")
    private String statusBefore;

    @Schema(description = "변경 후 상태", example = "완료")
    private String statusAfter;

    @Schema(description = "이전 정책 ID", example = "1")
    private int previousPolicyId;

    @Schema(description = "새로운 정책 ID", example = "2")
    private int newPolicyId;

    @Schema(description = "이전 카테고리 ID", example = "1")
    private int previousCategoryId;

    @Schema(description = "새로운 카테고리 ID", example = "2")
    private int newCategoryId;
}
