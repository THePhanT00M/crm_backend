package site.shcrm.shcrm_backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import site.shcrm.shcrm_backend.Service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /*
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    @Override
    public CustomUserDetailsService userDetailsService(){
        return customUserDetailsService;
    }*/

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/login", "/loginProc", "/join", "/joinProc","/reporthome","/reportsave").permitAll()
                        .requestMatchers("/admin").hasRole("admin")
                        .anyRequest().authenticated()
                );


        http.formLogin(auth -> auth
                .loginPage("/login")
                .loginProcessingUrl("/loginProc")
                .permitAll()
        );


        http
                .csrf((auth) -> auth.disable());

        http
                .sessionManagement((auth)->auth
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(true));


        return http.build();
    }

}
