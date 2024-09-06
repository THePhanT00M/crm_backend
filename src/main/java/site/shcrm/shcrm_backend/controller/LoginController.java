package site.shcrm.shcrm_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.shcrm.shcrm_backend.DTO.LoginDTO;
import site.shcrm.shcrm_backend.JWT.JWTUtil;
import site.shcrm.shcrm_backend.Service.MembersService;

@RestController
@RequestMapping("/login")
@Tag(name = "로그인", description = "로그인 API")
@RequiredArgsConstructor
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final MembersService membersService;

    @PostMapping
    @Operation(
            summary = "로그인",
            description = "사용자의 ID와 비밀번호를 통해 인증을 수행하고, JWT 토큰을 생성하여 응답합니다.",
            requestBody = @RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LoginDTO.class),
                            examples = @ExampleObject(
                                    name = "로그인 예시",
                                    value = """
                                            {
                                              "employeeId": "emp123",
                                              "password": "password123"
                                            }"""
                            )
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공, JWT 토큰이 헤더에 포함됩니다."),
            @ApiResponse(responseCode = "401", description = "로그인 실패, 인증 정보가 유효하지 않거나 잘못되었습니다.")
    })
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        try {
            // 사용자 인증
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    loginDTO.getEmployeeId(), loginDTO.getPassword()
            );
            Authentication authentication = authenticationManager.authenticate(authToken);

            // JWT 토큰 생성
            String token = jwtUtil.createToken(loginDTO.getEmployeeId());

            // 응답에 JWT 토큰 추가
            return ResponseEntity.ok()
                    .header("Authorization", "Bearer " + token)
                    .body("로그인 성공");
        } catch (Exception e) {
            // 인증 실패 시
            return ResponseEntity.status(401).body("로그인 실패: " + e.getMessage());
        }
    }
}