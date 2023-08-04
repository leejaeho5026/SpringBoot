package com.tjoeun.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter 
@ToString
public class MemberFormDto {
	
	// 회원가입할 때 입력해야 되는 내용을
	// MemberFormDto 클래스의 멤버변수로 지정함
	// name, email, password, address
	// validation -> @어노테이션으로 -> 컨트롤러에서 @Valid 달기
	
	@NotBlank(message="이름을 반드시 입력해 주세요")
	private String name;
	
	@NotBlank(message="이메일을 반드시 입력해 주세요")
	@Email(message="이메일 형식으로 입력해 주세요")
	private String email;
	
	@NotBlank(message="비밀번호를 반드시 입력해 주세요")
	@Length(min=2, max=20, message="8글자 이상, 20글자 이하로 입력해주세요")
	private String password;
	

	@NotBlank(message="주소을 반드시 입력해 주세요")
	private String address;

}
