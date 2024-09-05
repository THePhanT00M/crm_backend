package site.shcrm.shcrm_backend.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "카테고리 데이터 전송 객체")
public class CategoryDTO {

    @Schema(description = "카테고리 ID", example = "1")
    private int categoryId;

    @Schema(description = "카테고리 이름", example = "유류비")
    private String categoryName;

    @Schema(description = "설명", example = "모든 전자기기 관련 제품")
    private String description;
}
