package site.shcrm.shcrm_backend.JWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import site.shcrm.shcrm_backend.Entity.MembersEntity;
import site.shcrm.shcrm_backend.repository.MembersRepository;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final MembersRepository membersRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public JWTFilter(JWTUtil jwtUtil, MembersRepository membersRepository, BCryptPasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.membersRepository = membersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.substring(7);

        if (jwtUtil.isExpired(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        Integer employeeId = jwtUtil.getEmployeeId(token);

        MembersEntity membersEntity = membersRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        MembersDetails membersDetails = new MembersDetails(membersEntity, passwordEncoder);

        Authentication authToken = new UsernamePasswordAuthenticationToken(membersDetails, null, membersDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}