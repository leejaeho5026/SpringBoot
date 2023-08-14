package com.tjoeun;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tjoeun.entity.Answer;
import com.tjoeun.entity.Question;
import com.tjoeun.repository.AnswerRepository;
import com.tjoeun.repository.QuestionRepository;
import com.tjoeun.service.QuestionService;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class BoardAppTest {
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private AnswerRepository answerRepository;
	
	@Autowired
	private QuestionService questionService;

	@Test
	@DisplayName("질문테스트(insert)1")
	void questionTest() {
		Question q1 = new Question();
		q1.setSubject("Spring Boot에 대해 배우기");
		q1.setContent("Spring Boot에 대해 배우기 Content");
		q1.setCreateDate(LocalDateTime.now());
		questionRepository.save(q1);
		
		
		Question q2 = new Question();
		q2.setSubject("Spring Boot에 대해 배우기2");
		q2.setContent("Spring Boot에 대해 배우기2 Content2");
		q2.setCreateDate(LocalDateTime.now());
		questionRepository.save(q2);
		
	}

	@Test
	@DisplayName("조회 테스트")
	void selectTest() {
		List<Question>questionList = questionRepository.findAll();
		
		assertEquals(1, questionList.size()); // questionList에 값이 2개냐
		
		Question q1 = questionList.get(0); //첫번째꺼 꺼내기  q1.setSubject("Spring Boot에 대해 배우기")
		assertEquals("Spring Boot에 대해 배우기" ,q1.getSubject()); //첫번 째 내용이 ""안에 있는게 맞는지 확인
	}
	
	@Test
	@DisplayName("조회테스트2")
	void selectTest2() {
		Optional<Question> questionOne = questionRepository.findById((long) 1); //findById(Id의 타입), 인덱스
		
		if(questionOne.isPresent()) {
			Question q1 = questionOne.get();
			
			assertEquals("Spring Boot에 대해 배우기", q1.getSubject());
		}
	}
	@Test
	@DisplayName("조회테스트3")
	void selectTest3() { //제목과 내용이 맞는지 Repository에 객체가 있어야함
		Question q1 = questionRepository.findBySubjectAndContent("Spring Boot에 대해 배우기","Spring Boot에 대해 배우기 Content");
		assertEquals(1,q1.getId()); //위에 내용을 쓴 아이디가 3이 맞는지
	}
	
	@Test
	@DisplayName("조회 테스트4")
	void selectTest4() {
		List<Question> questionList = questionRepository.findBySubjectLike("Spr%%");
		//assertEquals(3, questionList.get(1).getId());

	Question q1 = questionList.get(0);
	assertEquals(1,q1.getId());
	}	

	@Test
	@DisplayName("수정 테스트1")
	void updateTest1() {
		Optional<Question> q1 = questionRepository.findById((long)1);
		
		assertTrue(q1.isPresent());
		
		Question question = q1.get();
		question.setSubject("Spring Board ???");
		questionRepository.save(question);
	}

	@Test
	@DisplayName("삭제 테스트 - 1")
	void deleteTest1() {
		
		assertEquals(2, questionRepository.count());
		
		Optional<Question> q1 = questionRepository.findById((long)1);
		assertTrue(q1.isPresent());
		Question question = q1.get();
		questionRepository.delete(question);
		
		assertEquals(1, questionRepository.count());
		//인덱스1번 삭제
	}


//질문2번글에 답변테스트
@Test
@DisplayName("답변 테스트(answer에 insert)1")
void answerTest1() {
	//DB에서 2번 질문글 가져오기
	Question q1 = questionRepository.findById((long)2)
														.orElseThrow(EntityNotFoundException::new);
	// assertTrue(q1.isPresent());
	// Question question = q1.get();
	//orElseThrow해서 필요없음
	
	Answer answer1 = new Answer();
	answer1.setContent("Querydsl은 정적 타입을 사용해서 SQL과 같은 Query를 생성할 수 있도록 해주는 오픈소스 프레임 워크");
	//어떤 질문글에 대한 답변글인지 표시함
	
	answer1.setQuestion(q1);
	answer1.setCreateDate(LocalDateTime.now());
	answerRepository.save(answer1);
}


	//현재 답글이 어느 질문글에 대한 답변인지 테스트
	@Test
	@DisplayName("답변 조회 테스트(select)1")
	void answerTest2() {
		
		//Id 타입 long 형변환
	Answer answer1 = 	answerRepository.findById((long) 1)
								                               .orElseThrow(EntityNotFoundException::new);	
	
	//1번 답글에 질문의 대한 id는 2번이 맞는지 확인
	assertEquals(2, answer1.getQuestion().getId());	
	}
	
	@Test
	@DisplayName("질문글 - 답변글 조회 테스트(select) 2")
	void questionAnswerTest1() {
		//질문글
		Question question2 = questionRepository.findById((long)2)
				.orElseThrow(EntityNotFoundException::new);
		//답변글
		Answer answer1 = 	answerRepository.findById((long) 1)
                .orElseThrow(EntityNotFoundException::new);	
	
		String answer = "Querydsl은 정적 타입을 사용해서 SQL과 같은 Query를 생성할 수 있도록 해주는 오픈소스 프레임 워크";
				
		assertEquals(answer, answer1.getContent());
		
		//answer1 답변글에 있는 달린 질문글의
		//answer1 Entity의 question_id가 question1 Entity의 question_id과 같은지 확인
		assertEquals(answer1.getQuestion().getId(),question2.getId());

		
		log.info(">>>>>>>>>>>> answer1.getQuestion().getId() : " + answer1.getQuestion().getId());
		log.info(">>>>>>>>>>>> question2.getId()             : " + question2.getId());
	}
	
	//질문글 한꺼번에 올리기
	@Test
	@DisplayName("질문글 한번에 다 올리기")
	void uploadBoardTest() {
		for(int i = 1; i<=500; i++) {
			String subject =  String.format("게시글 테스트 : [%03d]", i);
			String content = String.format("여기는 질문글 Test [%03d]",i);
			
			questionService.saveQuestion(subject,content, null);
		}
	}
	
	
	
	
	
	
	
	
}








