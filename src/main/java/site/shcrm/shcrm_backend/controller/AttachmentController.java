package site.shcrm.shcrm_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.shcrm.shcrm_backend.DTO.AttachmentDTO;
import site.shcrm.shcrm_backend.DTO.LoginDTO;
import site.shcrm.shcrm_backend.Service.AttachmentService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/attachment")
@RequiredArgsConstructor
@Tag(name = "첨부파일 관리", description = "첨부파일 업로드 및 삭제 관련 API")
public class AttachmentController {

    private final AttachmentService attachmentService;

    // 파일 업로드 엔드포인트
    @PostMapping("/upload")
    @Operation(
            summary = "파일 업로드",
            description = "여러 파일을 업로드합니다. 관련 ID 및 타입도 함께 제공해야 합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "파일과 함께 업로드할 관련 ID와 관련 타입 정보를 포함하는 요청입니다. 관련 ID와 관련 타입은 요청 파라미터로 전달되어야 합니다.",
                    content = @Content(
                            mediaType = "multipart/form-data",
                            examples = @ExampleObject(
                                    name = "파일 업로드 예시",
                                    value = """
                                            {
                                              "files": ["file1.jpg", "file2.png"],
                                              "relatedId": 1,
                                              "relatedType": "REPORT"
                                            }"""
                            )
                    )
            ),
            parameters = {
                    @Parameter(name = "files", description = "업로드할 파일들. 여러 개의 파일을 배열로 전송할 수 있습니다.", required = true, content = @Content(mediaType = "multipart/form-data")),
                    @Parameter(name = "relatedId", description = "파일과 관련된 ID", required = true, schema = @Schema(type = "integer")),
                    @Parameter(name = "relatedType", description = "파일의 관련 타입. 예를 들어, 'REPORT' 또는 'EXPENSE' 등이 될 수 있습니다.", required = true, schema = @Schema(type = "string", allowableValues = {"REPORT", "EXPENSE"}))
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "파일이 성공적으로 업로드되었습니다."),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청입니다. 파일이나 관련 정보가 올바르지 않습니다."),
                    @ApiResponse(responseCode = "500", description = "파일 업로드 중 오류가 발생했습니다.")
            }
    )
    public ResponseEntity<List<AttachmentDTO>> uploadFiles(
            @RequestParam("files") MultipartFile[] files,
            @RequestParam("relatedId") int relatedId,
            @RequestParam("relatedType") AttachmentDTO.RelatedType relatedType) throws IOException {
        List<AttachmentDTO> uploadedFiles = attachmentService.uploadFiles(files, relatedId, relatedType);
        return ResponseEntity.ok(uploadedFiles);
    }


    // 파일 삭제 엔드포인트
    @DeleteMapping("/delete")
    @Operation(
            summary = "파일 삭제",
            description = "파일을 삭제합니다. 삭제할 파일의 ID를 제공해야 합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "삭제할 파일의 ID를 포함한 요청 본문",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AttachmentDTO.class),
                            examples = @ExampleObject(
                                    name = "파일 삭제 예시",
                                    value = """
                                            {
                                              "attachmentId": 2
                                            }"""
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "파일이 성공적으로 삭제되었습니다."),
                    @ApiResponse(responseCode = "404", description = "파일을 찾을 수 없습니다. 파일이 존재하지 않거나 이미 삭제되었을 수 있습니다."),
                    @ApiResponse(responseCode = "500", description = "파일 삭제 중 오류가 발생했습니다.")
            }
    )
    public ResponseEntity<String> deleteFile(@RequestBody AttachmentDTO attachmentDTO) {
        int attachmentId = attachmentDTO.getAttachmentId();
        boolean isDeleted = attachmentService.deleteFile(attachmentDTO);
        if (isDeleted) {
            return ResponseEntity.ok("파일이 성공적으로 삭제되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("파일 삭제에 실패했습니다. 파일을 찾을 수 없습니다.");
        }
    }
}