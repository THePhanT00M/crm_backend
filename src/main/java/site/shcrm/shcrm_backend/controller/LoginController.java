package site.shcrm.shcrm_backend.controller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
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
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final MembersService membersService;

    public LoginController(AuthenticationManager authenticationManager, JWTUtil jwtUtil, MembersService membersService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.membersService = membersService;
    }

    @PostMapping
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
            return ResponseEntity.ok().header("Authorization", "Bearer " + token).body("로그인 성공");
        } catch (Exception e) {
            // 인증 실패 시
            return ResponseEntity.status(401).body("로그인 실패: " + e.getMessage());
        }
    }
}