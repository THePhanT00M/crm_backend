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
import site.shcrm.shcrm_backend.DTO.PolicyDTO;
import site.shcrm.shcrm_backend.Service.PolicyService;

@Tag(name = "정책", description = "정책 관련 API")
@RestController
@RequestMapping("/policy")
@RequiredArgsConstructor
public class PolicyController {

    private final PolicyService policyService;

    // 정책 생성
    @PostMapping("/create")
    @Operation(
            summary = "정책 생성",
            description = "새로운 정책을 생성합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "다음과 같은 JSON 데이터를 포함하여 요청을 보내야 합니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PolicyDTO.class),
                            examples = @ExampleObject(
                                    name = "정책 생성 예시",
                                    value = """
                                            {
                                              "policyName": "테스트",
                                              "description": "설명",
                                              "effectiveDate": "2024-01-01",
                                              "expirationDate": "2024-12-31"
                                            }"""
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "정책이 성공적으로 생성되었습니다."),
                    @ApiResponse(responseCode = "500", description = "정책 생성 중 오류가 발생했습니다.")
            }
    )
    public ResponseEntity<PolicyDTO> createPolicy(@RequestBody PolicyDTO policyDTO) {
        try {
            PolicyDTO createdPolicy = policyService.createPolicy(policyDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPolicy);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // 정책 업데이트
    @PutMapping("/update")
    @Operation(
            summary = "정책 업데이트",
            description = "기존 정책을 업데이트합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "다음과 같은 JSON 데이터를 포함하여 요청을 보내야 합니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PolicyDTO.class),
                            examples = @ExampleObject(
                                    name = "정책 업데이트 예시",
                                    value = """
                                            {
                                              "policyId": 1,
                                              "policyName": "테스트후",
                                              "description": "Comprehensive health insurance policy covering various medical expenses.",
                                              "effectiveDate": "2024-01-01",
                                              "expirationDate": "2024-12-31"
                                            }"""
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "정책이 성공적으로 업데이트되었습니다."),
                    @ApiResponse(responseCode = "404", description = "업데이트할 정책을 찾을 수 없습니다."),
                    @ApiResponse(responseCode = "500", description = "정책 업데이트 중 오류가 발생했습니다.")
            }
    )
    public ResponseEntity<PolicyDTO> updatePolicy(@RequestBody PolicyDTO policyDTO) {
        try {
            // PolicyDTO에서 policyId를 추출하여 업데이트
            PolicyDTO updatedPolicy = policyService.updatePolicy(policyDTO.getPolicyId(), policyDTO);
            return ResponseEntity.ok(updatedPolicy);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // 정책 삭제
    @DeleteMapping("/delete")
    @Operation(
            summary = "정책 삭제",
            description = "기존 정책을 삭제합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "다음과 같은 JSON 데이터를 포함하여 요청을 보내야 합니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PolicyDTO.class),
                            examples = @ExampleObject(
                                    name = "정책 삭제 예시",
                                    value = """
                                            {
                                              "policyId": 2
                                            }"""
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "204", description = "정책이 성공적으로 삭제되었습니다."),
                    @ApiResponse(responseCode = "404", description = "삭제할 정책을 찾을 수 없습니다."),
                    @ApiResponse(responseCode = "500", description = "정책 삭제 중 오류가 발생했습니다.")
            }
    )
    public ResponseEntity<Void> deletePolicy(@RequestBody PolicyDTO policyDTO) {
        try {
            // PolicyDTO에서 policyId를 추출하여 삭제
            policyService.deletePolicy(policyDTO.getPolicyId());
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}