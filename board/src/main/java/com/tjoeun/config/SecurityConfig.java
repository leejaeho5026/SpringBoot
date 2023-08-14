package com.tjoeun.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //Controller에서 @PreAuthorize("isAuthenticated()"사용하기 위해 사용
public class SecurityConfig {

	// @Bean : Spring Framework 가 메모리에 미리 객체를 생성해 놓음
	// Spring Security 설정 Bean
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http.formLogin()
		    .loginPage("/users/login")
		    .defaultSuccessUrl("/")
		    .and()
		    .logout()
		    .logoutRequestMatcher(new AntPathRequestMatcher("/users/logout"))
		    .logoutSuccessUrl("/")
		    .invalidateHttpSession(true);

		http.authorizeHttpRequests()
        .mvcMatchers("/**").permitAll()
        .anyRequest().authenticated();
		
		return http.build();
		
		
	/*	
		http.exceptionHandling()
		    .authenticationEntryPoint(new CustomAuthenticationEntryPoint());
	*/
		
	}
	
	// 회원가입할 때 입력한 비밀번호 암호 처리 Bean
	@Bean
	public PasswordEncoder passwordEncoder () {
		return new BCryptPasswordEncoder();  
	}
	
	
	@Bean
	public AuthenticationManager authenticationManager(										
														AuthenticationConfiguration authenticationConfigueration) throws Exception {
		return authenticationConfigueration.getAuthenticationManager();
	}
	
	
	
	
	
	
	
	
	
	
}



