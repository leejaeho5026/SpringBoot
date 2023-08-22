package com.hoho;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hoho.entity.Board;
import com.hoho.repository.BoardRepository;

@SpringBootTest
class LeejaehoApplicationTests {
	
	
	@Autowired
	private BoardRepository boardRepository;

	@Test
	@DisplayName("DB에 들어가는지  확인")
	void BoardContent() {
		
		Board b1 = new Board();
		
		b1.setContent("DB에 들어가는지 테스트");
		
		boardRepository.save(b1);
	
	}

	@Test
	@DisplayName("DB 들어가는지 확인2")
	void InsertBoard() {
		
		Board bo = new Board();
		bo.setContent("DB들어갔는지 확인2");		
		boardRepository.save(bo);
				
	}
	
	@Test
	@DisplayName("DB확인")
	void selectDB() {
		
		Board a1 = new Board();
		a1.setContent("DB");
		boardRepository.save(a1);

	}
	
	@Test
	@DisplayName("DB확인2")
	void selectDB2() {
		Optional<Board> OB1 = boardRepository.findById((long)3);
		// Id 인덱스가 3인 Content가 "DB"가 맞는지 확인
		if(OB1.isPresent()) {
			Board b1 = OB1.get();
			assertEquals("DB", b1.getContent());
		}
	}

	@Test
	@DisplayName("DB확인3")
	void DB3() {
		
		Board b1 = boardRepository.findByIdAndContent((long)3, "DB");
	

		
	}
	
	@Test
	@DisplayName("조회 테스트")
	void BoardTest() {
		
		List<Board> BoardList = boardRepository.findAll();
		assertEquals(3, BoardList.size());
		
		Board b1 =  BoardList.get(1);
		assertEquals("DB들어갔는지 확인2", b1.getContent());
		
	}
	
	
	
	
	
	
	
	
}
