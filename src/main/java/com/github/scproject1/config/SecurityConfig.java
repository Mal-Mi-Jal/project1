package com.github.scproject1.config;


import com.github.scproject1.security.JWTToken;
import com.github.scproject1.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JWTToken jwtToken;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // 1. CSRF 보안 비활성화 (테스트용)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/signup", "/api/users/check-email", "/api/users/login" , "/api/posts/{id}/comments").permitAll() // 2. 회원가입, 중복체크는 누구나 허용
                        .requestMatchers("/h2-console/**").permitAll()
                        .anyRequest().authenticated() // 3. 나머지는 로그인해야 가능
                )
                .headers(headers -> headers.frameOptions(options -> options.disable()))
                .addFilterBefore(new JwtAuthenticationFilter(jwtToken), UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
