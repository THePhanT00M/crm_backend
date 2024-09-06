package site.shcrm.shcrm_backend.JWT;

import jakarta.persistence.Column;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import site.shcrm.shcrm_backend.Service.MembersService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final MembersService membersService;

    public JWTFilter(JWTUtil jwtUtil, @Lazy MembersService membersService) {
        this.jwtUtil = jwtUtil;
        this.membersService = membersService;
    }

    @Override
    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain) throws jakarta.servlet.ServletException, IOException {
        String header = request.getHeader("Authorization");

        // 헤더가 존재하고 'Bearer '로 시작하는 경우
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7); // 'Bearer ' 부분을 제거하고 토큰만 추출
            try {
                String username = jwtUtil.getUsernameFromToken(token); // 토큰에서 사용자 이름 추출
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    // 사용자 이름으로 UserDetails 가져오기
                    UserDetails userDetails = membersService.loadUserByUsername(username);
                    if (jwtUtil.validateToken(token, userDetails)) {
                        // 유효한 경우 인증 정보를 SecurityContext에 설정
                        SecurityContextHolder.getContext().setAuthentication(
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities())
                        );
                    }
                }
            } catch (Exception e) {
                // 예외 처리 (예: 로그 기록)
                e.printStackTrace();
            }
        }

        // 다음 필터로 요청을 전달
        filterChain.doFilter(request, response);
    }
}