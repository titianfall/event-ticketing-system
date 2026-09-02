package com.example.ticketing.global.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * Authorization 헤더의 Bearer 토큰을 검증해 SecurityContext에 인증 정보를 넣는다.
 *
 * 토큰이 없거나 잘못돼도 여기서 응답을 만들지 않는다.
 * 인증 없이 그냥 통과시키면 permitAll 경로는 정상 처리되고, 보호된 경로는 뒤에서 EntryPoint가 401을 만든다.
 *
 * @Component를 붙이지 않는다. Filter 타입 빈은 스프링 부트가 서블릿 필터로도
 * 자동 등록해서 시큐리티 체인 밖에서 한 번 더 도는 문제가 생긴다.
 * SecurityConfig 에서 new로 만들어 체인에만 등록한다.
 */
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String HEADER = "Authorization";
    private static final String PREFIX = "Bearer ";

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = resolveToken(request);

        if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                Claims claims = jwtProvider.parse(token);       // 서명 불일치/만료면 예외
                Long memberId = Long.valueOf(claims.getSubject());
                String role = claims.get("role", String.class);

                var authentication = new UsernamePasswordAuthenticationToken(
                        memberId,                                // principal - 컨트롤러에서 꺼내 쓴다
                        null,                                    // credentials - 토큰 인증이라 비워 둔다
                        List.of(new SimpleGrantedAuthority("ROLE_" + role))
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (JwtException | IllegalArgumentException e) {
                SecurityContextHolder.clearContext();            // 인증 없는 상태로 통과시킨다
            }
        }

        filterChain.doFilter(request, response);
    }

    /** "Bearer xxx.yyy.zzz" 에서 토큰 부분만 잘라낸다. 형식이 아닐경우 null */
    private String resolveToken(HttpServletRequest request) {
        String header = request.getHeader(HEADER);
        if (header != null && header.startsWith(PREFIX)) {
            return header.substring(PREFIX.length());
        }
        return null;
    }
}
