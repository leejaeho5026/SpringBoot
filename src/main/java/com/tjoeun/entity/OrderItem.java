package com.tjoeun.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter
@ToString
@NoArgsConstructor
public class OrderItem {


	@Id
	@Column(name="order_item")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private  Long id;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "orders_id")
	private Orders order; //주문
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private Item item; //상품이름
	
	private int orderPrice; // 가격
	
	private int count; //수량
	
	private LocalDateTime regTime; //등록일
	
	private LocalDateTime updateTime; //수정일
	
	
	
	
}
