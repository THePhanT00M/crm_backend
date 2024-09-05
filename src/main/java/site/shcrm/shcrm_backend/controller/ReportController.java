package site.shcrm.shcrm_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
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
import site.shcrm.shcrm_backend.DTO.ReportsDTO;
import site.shcrm.shcrm_backend.Entity.ReportsEntity;
import site.shcrm.shcrm_backend.Service.ReportsService;

import java.util.List;
@Tag(name = "Report", description = "Report API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportController {

    private final ReportsService reportsService;

    @PostMapping("/create")
    @Operation(
            summary = "새 보고서 생성",
            description = "제공된 세부 정보로 새 보고서를 생성합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ReportsDTO.class),
                            examples = @ExampleObject(
                                    name = "보고서 생성 예시",
                                    value = """
                                            {
                                              "title": "Sample Report Title",
                                              "content": "This is the content of the sample report.",
                                              "status": "Pending",
                                              "policyId": 1,
                                              "categoryId": 1,
                                              "employeeId": 1
                                            }"""
                            )
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "보고서가 성공적으로 저장되었습니다."),
            @ApiResponse(responseCode = "500", description = "보고서 저장에 실패했습니다.")
    })
    public ResponseEntity<String> saveReport(@RequestBody ReportsDTO reportsDTO) {
        boolean isSaved = reportsService.saveReport(reportsDTO);
        if (isSaved) {
            return ResponseEntity.ok("보고서가 성공적으로 저장되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("보고서 저장에 실패했습니다.");
        }
    }


    @GetMapping("/find")
    @Operation(
            summary = "보고서 상세조회",
            description = "ID를 기준으로 보고서를 검색합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ReportsDTO.class),
                            examples = @ExampleObject(
                                    name = "보고서 조회 예시",
                                    value = """
                                            {
                                              "reportId": "1"
                                            }"""
                            )
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "보고서를 찾았습니다."),
            @ApiResponse(responseCode = "404", description = "보고서를 찾을 수 없습니다.")
    })
    public ResponseEntity<ReportsDTO> getReport(@RequestBody ReportsDTO reportsDTO) {
        ReportsDTO report = reportsService.findById(reportsDTO.getReportId());
        if (report != null) {
            return ResponseEntity.ok(report);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @GetMapping("/all")
    @Operation(
            summary = "모든 보고서 조회",
            description = "모든 보고서의 목록을 조회합니다. 추가적인 요청 본문 없이, 이 엔드포인트에 GET 요청을 보내기만 하면 됩니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "보고서 목록을 반환합니다.")
            }
    )
    public ResponseEntity<List<ReportsDTO>> getAllReports() {
        List<ReportsDTO> reportsDTOList = reportsService.findAll();
        return ResponseEntity.ok(reportsDTOList);
    }

    @PutMapping("/update")
    @Operation(
            summary = "보고서 업데이트",
            description = "제공된 세부 정보로 기존 보고서를 업데이트합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "다음과 같은 JSON 데이터를 포함하여 요청을 보내야 합니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ReportsDTO.class),
                            examples = @ExampleObject(
                                    name = "보고서 업데이트 예시",
                                    value = """
                                            {
                                              "reportId": 1,
                                              "title": "테스트",
                                              "content": "1",
                                              "status": "결제완료",
                                              "policyId": 1,
                                              "categoryId": 1,
                                              "employeeId": 1
                                            }"""
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "보고서가 성공적으로 업데이트되었습니다."),
                    @ApiResponse(responseCode = "404", description = "보고서를 찾을 수 없습니다.")
            }
    )
    public ResponseEntity<String> updateReport(@RequestBody ReportsDTO reportsDTO) {
        boolean isUpdated = reportsService.updateReport(reportsDTO.getReportId(), reportsDTO);
        return isUpdated ? ResponseEntity.ok("보고서가 성공적으로 업데이트되었습니다.") : ResponseEntity.status(HttpStatus.NOT_FOUND).body("보고서 업데이트에 실패했습니다.");
    }

}
