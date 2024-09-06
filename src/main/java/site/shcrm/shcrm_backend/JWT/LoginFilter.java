package site.shcrm.shcrm_backend.JWT;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import site.shcrm.shcrm_backend.DTO.LoginDTO;
import site.shcrm.shcrm_backend.Service.MembersService;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final JWTUtil jwtUtil;

    public LoginFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        super();
        setAuthenticationManager(authenticationManager);  // AuthenticationManager 설정
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            LoginDTO loginDTO = mapper.readValue(request.getInputStream(), LoginDTO.class);

            // 문자열에서 정수로 변환
            Integer employeeId = Integer.parseInt(loginDTO.getEmployeeId());
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    employeeId, loginDTO.getPassword());

            return this.getAuthenticationManager().authenticate(authToken);
        } catch (IOException | NumberFormatException e) {
            throw new RuntimeException("Failed to parse authentication request body", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        // 인증 성공 시 JWT 토큰 생성
        UserDetails userDetails = (UserDetails) authResult.getPrincipal();
        String token = jwtUtil.createToken(userDetails.getUsername());

        // 응답 JSON 객체 생성
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("employeeId", ((MembersDetails) userDetails).getMembersEntity().getEmployeeId());
        responseBody.put("email", ((MembersDetails) userDetails).getMembersEntity().getEmail());

        response.setHeader("Authorization", "Bearer " + token);

        // 응답 설정
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getWriter(), responseBody);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Authentication failed: " + failed.getMessage());
    }
}