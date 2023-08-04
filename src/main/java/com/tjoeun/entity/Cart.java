package com.tjoeun.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter
@ToString
@NoArgsConstructor //파라미터가 없는 기본 생성자
public class Cart {

	
	@Id // 테이블 PK역활
	@Column(name="cart_id") //DB의 테이블 컬럼 이름 바꿀 때
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 생성 ID값을 따로 받지 않아도, 자동으로 AUTO_IncreMent를 하여 생성한다.
	private Long id;
	
	// Cart 테이블과 Member 테이블 1:1 관계
	@OneToOne(fetch=FetchType.LAZY) // 지연로딩
	@JoinColumn(name = "member_id") // 조인할 테이블이름 쓰기, member테이블의 member_id 컬럼을 가져다가 쓰겠다.
	private Member member;




















}
