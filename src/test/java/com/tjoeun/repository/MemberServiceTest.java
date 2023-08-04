package com.tjoeun.repository;


import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tjoeun.dto.MemberFormDto;
import com.tjoeun.entity.Member;
import com.tjoeun.service.MemberService;

// @Transactional <-- test 이후에 DB 상태를 자동으로 rollback 해 줌
@SpringBootTest
@Transactional
// @Rollback(value=false)
class MemberServiceTest {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public Member createMember() {
		MemberFormDto memberFormDto = new MemberFormDto();
		memberFormDto.setEmail("student@tjoeun.com");
		memberFormDto.setName("더조은");
		memberFormDto.setAddress("정발산");
		memberFormDto.setPassword("123456789");
		
		Member member = Member.createMember(memberFormDto, passwordEncoder);
		return member;
	}

	@Test
	@DisplayName("회원가입 테스트")
	public void regMemberTest() {
		Member member = createMember();
		Member savedMember = memberService.saveMember(member);
		
		assertEquals(member.getEmail(), savedMember.getEmail());
		assertEquals(member.getName(), savedMember.getName());
		assertEquals(member.getAddress(), savedMember.getAddress());
		assertEquals(member.getPassword(), savedMember.getPassword());
		assertEquals(member.getRole(), savedMember.getRole());
		
	}

}