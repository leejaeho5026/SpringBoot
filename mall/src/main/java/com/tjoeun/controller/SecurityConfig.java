/*
 * package com.tjoeun.controller;
 * 
 * import org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.Configuration; import
 * org.springframework.security.config.annotation.web.builders.HttpSecurity;
 * import org.springframework.security.config.annotation.web.configuration.
 * EnableWebSecurity; import
 * org.springframework.security.web.SecurityFilterChain;
 * 
 * @Configuration
 * 
 * @EnableWebSecurity public class SecurityConfig {
 * 
 * //@Bean: Spring Framework가 메모리에 미리 객체를 생성해 놓음
 * 
 * @Bean public SecurityFilterChain filterChain(HttpSecurity http) thorws
 * Exception{ http.formLogin() .defaultSuccessUrl("/")
 * .usernameParameter("email") .failureUrl("/members/login/error") .and()
 * .logout() .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
 * .logoutSuccessUrl("/") return http.build(); } }
 */
