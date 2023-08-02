package com.tjoeun.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class ItemDTO {


	private Long id; // 상품코드


	private String itemNm; // 상품이름


	private int price; // 상품가격


	private int stockNumber; // 재고수량


	private String itemDetail; // 상품 상세정보

	private String itemSellStatus; // 판매상태

	private LocalDateTime regTime; // 등록시간

	private LocalDateTime updateTime; // 수정시간

}
