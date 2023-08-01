package com.tjoeun.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.tjoeun.constant.ItemSellStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="item")
@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor

public class Item {
	@Id //primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) //auto increment
	@Column(name="item_id") // 컬럼이름 item_id로
	private Long id; //상품코드
	
	@Column(nullable=false, length=50) //notnull설정
	private String itemNm; //상품이름
	
	@Column(nullable=false)
	private int price; // 상품가격

	@Column(nullable=false, name="number")
	private int stockNumber; //재고수량
	
	@Lob //내용이 길 때 대용량에 사용(큰 데이터) 길이가 255 이상인 문자열 저장
	@Column(nullable=false)
	private String itemDetail; //상품 상세정보
	
	@Enumerated(EnumType.STRING) //enum클래스를 사용할 때, 상품이 팔렸을 때 문자열로 
	private ItemSellStatus itemSellStatus;
	
	private LocalDateTime regTime; //등록시간
	
	private LocalDateTime updateTime; //수정시간
	
	
	
	
}
