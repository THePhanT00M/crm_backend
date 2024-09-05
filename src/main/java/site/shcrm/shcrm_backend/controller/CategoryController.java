package site.shcrm.shcrm_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.shcrm.shcrm_backend.DTO.CategoryDTO;
import site.shcrm.shcrm_backend.Service.CategoryService;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
@Tag(name = "카테고리 관리", description = "카테고리 생성, 업데이트, 삭제 관련 API")
public class CategoryController {

    private final CategoryService categoryService;

    // 카테고리 생성
    @PostMapping("/create")
    @Operation(
            summary = "카테고리 생성",
            description = "새로운 카테고리를 생성합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "다음과 같은 JSON 데이터를 포함하여 요청을 보내야 합니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CategoryDTO.class),
                            examples = @ExampleObject(
                                    name = "카테고리 생성 예시",
                                    value = "{\n" +
                                            "  \"categoryName\": \"2\",\n" +
                                            "  \"description\": \"설명\"\n" +
                                            "}"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "카테고리가 성공적으로 생성되었습니다."),
                    @ApiResponse(responseCode = "500", description = "카테고리 생성 중 오류가 발생했습니다.")
            }
    )
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
        try {
            CategoryDTO createdCategory = categoryService.createCategory(categoryDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/update")
    @Operation(
            summary = "카테고리 업데이트",
            description = "기존 카테고리를 업데이트합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "다음과 같은 JSON 데이터를 포함하여 요청을 보내야 합니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CategoryDTO.class),
                            examples = @ExampleObject(
                                    name = "카테고리 업데이트 예시",
                                    value = "{\n" +
                                            "  \"categoryId\": 1,\n" +
                                            "  \"categoryName\": \"1\",\n" +
                                            "  \"description\": \"변경후\"\n" +
                                            "}"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "카테고리가 성공적으로 업데이트되었습니다."),
                    @ApiResponse(responseCode = "404", description = "업데이트할 카테고리를 찾을 수 없습니다."),
                    @ApiResponse(responseCode = "500", description = "카테고리 업데이트 중 오류가 발생했습니다.")
            }
    )
    public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO categoryDTO) {
        try {
            CategoryDTO updatedCategory = categoryService.updateCategory(categoryDTO);
            return ResponseEntity.ok(updatedCategory);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/delete")
    @Operation(
            summary = "카테고리 삭제",
            description = "기존 카테고리를 삭제합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "다음과 같은 JSON 데이터를 포함하여 요청을 보내야 합니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CategoryDTO.class),
                            examples = @ExampleObject(
                                    name = "카테고리 삭제 예시",
                                    value = "{\n" +
                                            "  \"categoryId\": 2\n" +
                                            "}"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "204", description = "카테고리가 성공적으로 삭제되었습니다."),
                    @ApiResponse(responseCode = "404", description = "삭제할 카테고리를 찾을 수 없습니다."),
                    @ApiResponse(responseCode = "500", description = "카테고리 삭제 중 오류가 발생했습니다.")
            }
    )
    public ResponseEntity<Void> deleteCategory(@RequestBody CategoryDTO categoryDTO) {
        try {
            categoryService.deleteCategory(categoryDTO.getCategoryId());
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}

