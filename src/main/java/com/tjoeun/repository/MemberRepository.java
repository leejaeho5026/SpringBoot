package com.tjoeun.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tjoeun.entity.Member;

																							//<T(클래스) , ID>
public interface MemberRepository extends JpaRepository<Member, Long> {
	
	// Query Method
	
	Member findByEmail(String email);

	
	// Optional를 사용할 때
	// Optional<Member> findByEmail(String email);
}
