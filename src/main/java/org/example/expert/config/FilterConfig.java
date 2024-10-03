package org.example.expert.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.expert.security.JwtAuthorizationFilter;
import org.example.expert.security.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class FilterConfig {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        // Spring Security 필터 체인에 JwtAuthorizationFilter를 추가
        http.addFilterBefore(new JwtAuthorizationFilter(jwtUtil,userDetailsService) {
            @Override
            protected boolean shouldNotFilter(HttpServletRequest req) throws ServletException {
                String path = req.getRequestURI();
                // auth로 시작되는 경로에 대해서는 필터가 진행되지않음
                return path.startsWith("/auth");
            }
        }, UsernamePasswordAuthenticationFilter.class);

//         auth 경로 이외에 인증되지않았으면 403 에러남
        http.authorizeHttpRequests(authReq ->
                authReq.requestMatchers("/auth/**").permitAll()
                        .anyRequest().authenticated()
        );

        return http.build();
    }

}
