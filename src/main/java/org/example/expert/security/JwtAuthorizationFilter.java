package org.example.expert.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.expert.config.JwtUtil;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Order(1)
@Slf4j(topic = "JwtAuthorizationFilter")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtUtil ju;
    private final UserDetailsServiceImpl userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain fc) throws ServletException, IOException {
        String jwt = ju.getJwtFromHeader(req,res);
        String subJwt = ju.substringToken(jwt);
        if (StringUtils.hasText(subJwt)) {
            Claims claims = ju.extractClaims(subJwt);
            setAuthentication(claims.get("nickname", String.class));
        }
        fc.doFilter(req, res);
    }

    // 인증 처리
    private void setAuthentication(String nickname) {
        SecurityContext sc = SecurityContextHolder.createEmptyContext();
        Authentication auth = createAuthentication(nickname);
        sc.setAuthentication(auth);
        SecurityContextHolder.setContext(sc);
    }

    //인증객체 생성
    private Authentication createAuthentication(String nickname) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(nickname);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
