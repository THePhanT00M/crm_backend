package site.shcrm.shcrm_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import site.shcrm.shcrm_backend.DTO.JoinDTO;
import site.shcrm.shcrm_backend.Service.JoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;

@RestController
@RequestMapping("/join")
@Tag(name = "회원 가입", description = "회원 가입 관련 API")
public class JoinController {

    @Autowired
    private JoinService joinService;

    @PostMapping
    @Operation(summary = "회원 가입 처리", description = "회원 가입 요청을 처리합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = JoinDTO.class),
                            examples = @ExampleObject(
                                    name = "회원 가입 예시",
                                    value = """
                                            {
                                              "employeeId": "1",
                                              "passwordHash": "1",
                                              "firstName": "연",
                                              "lastName": "제찬",
                                              "email": "wpcks99@naver.com",
                                              "hireDate": "2024-08-30",
                                              "jobTitle": "Developer",
                                              "phoneNumber": "010-2783-7261"
                                            }"""
                            )
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 가입이 성공적으로 처리되었습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
    })
    public String joinProcess(@RequestBody JoinDTO joinDTO) {
        joinService.joinProcess(joinDTO);
        return "성공";
    }
}


