package com.tjoeun.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter
@ToString
public class CartItem {

	
	@Id // 테이블 PK역활
	@Column(name="cart_item_id") //DB의 테이블 컬럼 이름 바꿀 때
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 생성 ID값을 따로 받지 않아도, 자동으로 AUTO_IncreMent를 하여 생성한다.
	private Long id;
	
	// Cartitem 테이블과 Cart 테이블 N:1 관계
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "cart_id") 
	private Cart cart; // CartItem클래스에 Cart는 => Cart클래스의 cart_id를 가져와서 사용
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name= "item_id")
	private Item item; // CartItem클래스에 item은 =>  Item클래스의 item_id를 가져와서 사용
	
	private int count;
	
	
	
}
