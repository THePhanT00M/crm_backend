package site.shcrm.shcrm_backend.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "첨부 파일 데이터 전송 객체")
public class AttachmentDTO {

    @Schema(description = "첨부 파일 ID", example = "1")
    private int attachmentId;

    @Schema(description = "관련 타입", example = "REPORT", allowableValues = {"REPORT", "EXPENSE"})
    private RelatedType relatedType;

    @Schema(description = "관련 ID", example = "1")
    private int relatedId;

    @Schema(description = "원본 파일 이름", example = "report.pdf")
    private String originalName;

    @Schema(description = "파일 URL", example = "https://example.com/files/report.pdf")
    private String fileUrl;

    public enum RelatedType {
        @Schema(description = "보고서")
        REPORT,

        @Schema(description = "경비")
        EXPENSE
    }
}
