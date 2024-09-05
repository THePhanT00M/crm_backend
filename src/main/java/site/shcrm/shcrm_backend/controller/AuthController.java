package site.shcrm.shcrm_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.shcrm.shcrm_backend.DTO.LoginDTO;
import site.shcrm.shcrm_backend.JWT.JWTUtil;
import site.shcrm.shcrm_backend.JWT.MembersDetails;
import site.shcrm.shcrm_backend.Service.MembersService;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@Tag(name = "로그인", description = "사용자 로그인 관련 API")
public class AuthController {

    private final MembersService membersService;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    public AuthController(MembersService membersService, AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.membersService = membersService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    @Operation(
            summary = "로그인",
            description = "사용자가 로그인하고 JWT 토큰을 생성합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LoginDTO.class),
                            examples = @ExampleObject(
                                    name = "로그인 예시",
                                    value = """
                                            {
                                              "employeeId": "1",
                                              "password": "1"
                                            }"""
                            )
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공. JWT 토큰과 사용자 이름을 반환합니다."),
            @ApiResponse(responseCode = "401", description = "인증 실패. 잘못된 자격 증명입니다.")
    })
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginDTO loginDTO) {
        try {
            Authentication authentication = membersService.authenticate(
                    loginDTO.getEmployeeId(),
                    loginDTO.getPassword()
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            MembersDetails membersDetails = (MembersDetails) authentication.getPrincipal();
            Integer employeeId = membersDetails.getMembersEntity().getEmployeeId();
            String token = jwtUtil.createJwt(employeeId, 60 * 60 * 10L);

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("token", "Bearer " + token);
            responseBody.put("username", membersDetails.getUsername());

            return ResponseEntity.ok(responseBody);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "잘못된 자격 증명입니다."));
        } catch (javax.naming.AuthenticationException e) {
            throw new RuntimeException(e);
        }
    }
}