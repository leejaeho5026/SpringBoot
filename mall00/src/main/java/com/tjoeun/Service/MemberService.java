package com.tjoeun.Service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.tjoeun.entity.Member;
import com.tjoeun.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

	public final MemberRepository memberRepository;
	


	public Member saveMember(Member member) {

		validateDuplicate(member);
		//가입하려는 회원과 같은 회원이 있는지 검증하는 메소드 호출		
		Member savedMember = memberRepository.save(member);
		return savedMember;
	}
	
	
	
	// 가입하려는 회원과 같은 회원이 있는지 검증하는 메소드
	private void validateDuplicate(Member member) {
		Member foundMember = memberRepository.findByEmail(member.getEmail());
		if(foundMember != null) {
			throw new IllegalStateException("이미 가입하신 회원입니다.");
		}


	}
}
