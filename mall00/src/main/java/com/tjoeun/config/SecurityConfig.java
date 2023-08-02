package com.tjoeun.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	// @Bean : Spring Framework 가 메모리에 미리 객체를 생성해 놓음
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		/*
		http.formLogin()
		    .loginPage("/members/login")
		    .defaultSuccessUrl("/")
		    .usernameParameter("email")
		    .failureUrl("/members/login/error")
		    .and()
		    .logout()
		    .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
		    .logoutSuccessUrl("/");
		*/
		
		//html 토큰
		http.authorizeHttpRequests()
			  .mvcMatchers("/", "/member/**", "/item/**").permitAll()
			  .mvcMatchers("/css/**", "/js/**", "/images/**").permitAll()
			  .mvcMatchers("/admin/**").hasRole("ADMIN")
			  .anyRequest().authenticated();
							
		return http.build();
	}

	//회원가입할 때 입력한 비밀번호 암호 처리하기
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
