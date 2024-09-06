package site.shcrm.shcrm_backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import site.shcrm.shcrm_backend.JWT.JWTFilter;
import site.shcrm.shcrm_backend.JWT.JWTUtil;
import site.shcrm.shcrm_backend.JWT.LoginFilter;
import site.shcrm.shcrm_backend.Service.MembersService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JWTUtil jwtUtil;
    private final MembersService membersService;

    @Autowired
    public SecurityConfig(JWTUtil jwtUtil, @Lazy MembersService membersService) {
        this.jwtUtil = jwtUtil;
        this.membersService = membersService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz
                        .anyRequest().permitAll()
                )
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilter(loginFilter(http));

        return http.build();
    }

    @Bean
    public JWTFilter jwtFilter() {
        return new JWTFilter(jwtUtil, membersService);
    }

    @Bean
    public LoginFilter loginFilter(HttpSecurity http) throws Exception {
        return new LoginFilter("/login", authenticationManager(http), jwtUtil);
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .build();
    }
}