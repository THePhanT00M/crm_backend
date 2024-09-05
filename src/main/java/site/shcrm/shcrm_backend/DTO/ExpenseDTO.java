package site.shcrm.shcrm_backend.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import site.shcrm.shcrm_backend.Entity.MembersEntity;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Schema(description = "경비 데이터 전송 객체")
public class ExpenseDTO {

    @Schema(description = "경비 ID", example = "1")
    private Integer expenseId;

    @Schema(description = "금액", example = "100000.00")
    private BigDecimal amount;

    @Schema(description = "상점 이름", example = "ABC 마트")
    private String merchantName;

    @Schema(description = "주소", example = "서울시 강남구 역삼동")
    private String address;

    @Schema(description = "경비 발생일", example = "2024-01-01")
    private Date expenseDate;

    @Schema(description = "첨부 파일 ID", example = "123")
    private Integer attachment;

    @Schema(description = "정책 ID", example = "1")
    private Integer policyId;

    @Schema(description = "카테고리 ID", example = "2")
    private Integer categoryId;

    @Schema(description = "보고서 ID", example = "3")
    private Integer reportId;

    @Schema(description = "환급 여부(Y,N)", example = "Y")
    private String reimbursement;

    @Schema(description = "삭제 여부(Y,N)", defaultValue = "N")
    private String isDeleted;

    @Schema(description = "직원 ID", example = "1")
    private Integer employeeId;
}
