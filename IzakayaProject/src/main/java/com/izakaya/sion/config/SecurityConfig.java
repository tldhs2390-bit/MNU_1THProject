package com.izakaya.sion.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/admin/main",
                    "/admin/login",
                    "/css/**",
                    "/images/**",
                    "/js/**"
                ).permitAll()
                .requestMatchers("/admin/**").authenticated()
                .anyRequest().permitAll()
            )

            .formLogin(form -> form
                // ✅ 로그인 페이지를 명확히 지정 (중요)
                .loginPage("/admin/main")
                // ✅ 로그인 처리 URL
                .loginProcessingUrl("/admin/login")
                // ✅ 파라미터 기본값(username/password) 그대로 사용
                .usernameParameter("username")
                .passwordParameter("password")
                // ✅ 성공/실패
                .defaultSuccessUrl("/admin/main", true)
                .failureUrl("/admin/main?error")
                .permitAll()
            )

            .logout(logout -> logout
                .logoutUrl("/admin/logout")
                .logoutSuccessUrl("/admin/main?logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                )
             // ✅ 세션 만료 처리
                .sessionManagement(session -> session
                    .invalidSessionUrl("/admin/main")
                    .maximumSessions(1)
                    .expiredUrl("/admin/main")
                );
        

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin =
            User.withUsername("admin")
                .password("1234")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin);
    }

    @Bean
    @SuppressWarnings("deprecation")
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}