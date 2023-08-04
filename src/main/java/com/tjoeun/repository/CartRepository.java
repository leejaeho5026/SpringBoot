package com.tjoeun.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tjoeun.entity.Cart;

																							//Cart 클래스의 ID 타입 (Long)
public interface CartRepository  extends JpaRepository<Cart, Long>{
	

}
