package com.tjoeun.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.tjoeun.entity.Item;

//ItemRepository의 부모는 JpaRepository<Item, Long>이다.
public interface ItemRepository extends JpaRepository<Item, Long>, 
								 												QuerydslPredicateExecutor<Item> {
	// find(EntitiClass이름)By(멤버변수이름-> (DB에서 컬럼이름) JAVA에선 멤버변수이름 사용해야된다.)
	
	//상품이름을 받아서 파라미터로 여러개닌깐 entity.Item에서 리스트로 받아와서 사용
	List<Item> findByItemNm(String ItemNm); // Query Method

	//이름이랑 상세보기 2개  Or를 사용 -> 하나만 일치하면 데이터 가져오기 이름 1개 상세보기 1개
	List<Item> findByItemNmOrItemDetail(String ItemNm, String ItemDetail);
	
	
	// JPQL
	//복잡할 때 @Query문 사용
	//Java에 있는 entiti 클래스를 쓰고 + 창조변수 해야된다.
	//%:안에 String ItemDetail값% 
	//@Param("안에 String (ItemDetail)")이 이름으로 보내기
	@Query("select i from Item i where i.itemDetail like "
			+ "%:itemDetail% order by i.price desc ")	
	List<Item> findByItemDetail(@Param("itemDetail")String itemDetail);
						
	// Native Query, DB에있는 컬럼이름으로 item_detail
	@Query(value="select * from item i where i.item_detail like "
			+ "%:itemDetail% order by i.price desc", nativeQuery=true)
	List<Item> findByItemDetailNative(@Param("itemDetail") String itemDetail);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
