package com.tjoeun.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class UsersFormDto {

	@NotEmpty(message="아이디를 입력하세요")
	@Size(min=3, max=10)
	private String username;
	
	@NotEmpty(message="비밀번호를 입력하세요")
	private String password1;
	
	@NotEmpty(message="비밀번호2를 확인하세요")
	private String password2;

	@NotEmpty(message="이메일을 입력하세요")
	@Email
	private String email;
}
