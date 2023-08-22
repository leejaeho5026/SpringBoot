package com.hoho.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hoho.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
	
	
	List<Board> findByContentLike(String content);
	
	
	Board findByIdAndContent(Long id, String content);

}
