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
import site.shcrm.shcrm_backend.DTO.ExpenseDTO;
import site.shcrm.shcrm_backend.Entity.ExpenseEntity;
import site.shcrm.shcrm_backend.Entity.MembersEntity;
import site.shcrm.shcrm_backend.Service.ExpenseService;

import java.util.List;

@RestController
@RequestMapping("/expense")
@RequiredArgsConstructor
@Tag(name = "경비 관리", description = "경비 관리 관련 API")
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping("/create")
    @Operation(
            summary = "경비 생성",
            description = "새로운 경비를 생성합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "다음과 같은 JSON 데이터를 포함하여 요청을 보내야 합니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ExpenseDTO.class),
                            examples = @ExampleObject(
                                    name = "경비 생성 예시",
                                    value = """
                                            {
                                              "amount": 150.75,
                                              "merchantName": "테스트",
                                              "address": "테스트",
                                              "expenseDate": "2024-09-04",
                                              "policyId": 1,
                                              "categoryId": 1,
                                              "reimbursement": "N",
                                              "employeeId": 1
                                            }"""
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "경비가 성공적으로 저장되었습니다."),
                    @ApiResponse(responseCode = "500", description = "경비 저장에 실패했습니다.")
            }
    )
    public ResponseEntity<String> saveExpense(@RequestBody ExpenseDTO expenseDTO) {
        int expenseId = expenseService.saveExpense(expenseDTO);

        if (expenseId > 0) {
            return ResponseEntity.ok("경비가 성공적으로 저장되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("경비 저장에 실패했습니다.");
        }
    }

    @PutMapping("/update")
    @Operation(
            summary = "경비 업데이트",
            description = "기존의 경비를 업데이트합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "다음과 같은 JSON 데이터를 포함하여 요청을 보내야 합니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ExpenseDTO.class),
                            examples = @ExampleObject(
                                    name = "경비 업데이트 예시",
                                    value = """
                                            {
                                              "expenseId": 1,
                                              "amount": 222.22,
                                              "merchantName": "ABC Store",
                                              "address": "123 Main St, City, Country",
                                              "expenseDate": "2024-09-04",
                                              "policyId": 1,
                                              "categoryId": 1,
                                              "reimbursement": "Y",
                                              "employeeId": 1
                                            }"""
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "경비가 성공적으로 업데이트되었습니다."),
                    @ApiResponse(responseCode = "404", description = "경비를 찾을 수 없습니다. 삭제되었거나 권한이 없습니다."),
                    @ApiResponse(responseCode = "500", description = "경비 업데이트 중 오류가 발생했습니다.")
            }
    )
    public ResponseEntity<String> updateExpense(@RequestBody ExpenseDTO expenseDTO) {
        boolean isUpdated = expenseService.updateExpense(expenseDTO);

        if (isUpdated) {
            return ResponseEntity.ok("경비가 성공적으로 업데이트되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("경비 업데이트에 실패했습니다. 경비가 없거나 삭제되었거나 권한이 없습니다.");
        }
    }

    @PostMapping("/delete")
    @Operation(
            summary = "경비 삭제",
            description = "경비를 삭제 상태로 변경합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "다음과 같은 JSON 데이터를 포함하여 요청을 보내야 합니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ExpenseDTO.class),
                            examples = @ExampleObject(
                                    name = "경비 삭제 예시",
                                    value = """
                                            {
                                              "expenseId": 2,
                                              "employeeId": 1
                                            }"""
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "경비가 성공적으로 삭제되었습니다."),
                    @ApiResponse(responseCode = "404", description = "경비를 찾을 수 없습니다. 이미 삭제되었거나 권한이 없습니다."),
                    @ApiResponse(responseCode = "500", description = "경비 삭제 중 오류가 발생했습니다.")
            }
    )
    public ResponseEntity<String> deleteExpense(@RequestBody ExpenseDTO expenseDTO) {
        boolean isDeleted = expenseService.markExpenseAsDeleted(expenseDTO.getExpenseId(), expenseDTO.getEmployeeId());

        if (isDeleted) {
            return ResponseEntity.ok("경비가 성공적으로 삭제되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("경비 삭제에 실패했습니다. 경비가 없거나 이미 삭제되었거나 권한이 없습니다.");
        }
    }

    @GetMapping("/all")
    @Operation(
            summary = "활성 경비 조회",
            description = "특정 직원의 활성 경비 리스트를 조회합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "다음과 같은 JSON 데이터를 포함하여 요청을 보내야 합니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ExpenseDTO.class),
                            examples = @ExampleObject(
                                    name = "활성 경비 조회 예시",
                                    value = """
                                            {
                                              "employeeId": 1
                                            }"""
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "활성 경비 목록을 성공적으로 반환합니다."),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
            }
    )
    public ResponseEntity<List<ExpenseEntity>> getActiveExpenses(@RequestBody ExpenseDTO expenseDTO) {
        List<ExpenseEntity> activeExpenses = expenseService.getAllActiveExpenses(expenseDTO);
        return ResponseEntity.ok(activeExpenses);
    }

    @GetMapping("/details")
    @Operation(
            summary = "경비 ID로 경비 조회",
            description = "특정 경비 ID로 경비를 조회합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "다음과 같은 JSON 데이터를 포함하여 요청을 보내야 합니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ExpenseDTO.class),
                            examples = @ExampleObject(
                                    name = "경비 ID로 경비 조회 예시",
                                    value = """
                                            {
                                              "expenseId": 1,
                                              "employeeId": 1
                                            }"""
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "경비를 성공적으로 반환합니다."),
                    @ApiResponse(responseCode = "404", description = "경비를 찾을 수 없습니다. 경비가 없거나 삭제되었거나 권한이 없습니다."),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
            }
    )
    public ResponseEntity<ExpenseEntity> getExpenseById(@RequestBody ExpenseDTO expenseDTO) {
        ExpenseEntity expense = expenseService.getExpenseById(expenseDTO);

        if (expense != null) {
            return ResponseEntity.ok(expense);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


}