package com.tjoeun.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.tjoeun.entity.Answer;
import com.tjoeun.entity.Question;
import com.tjoeun.entity.Users;
import com.tjoeun.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionService {
	
	private final QuestionRepository questionRepository;
  
	//질문 글 전체 가져오기, 페이징처리
	public Page<Question> getList(int page){ 	
		//오름차순으로 정렬
		List<Sort.Order> sorts = new ArrayList<>();
		//Sort클래스안에 Order가 내부 클래스
		sorts.add(Sort.Order.desc("createDate")); // Entity Question에 있는 private LocalDateTime createDate 가져옴
		
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts)); // (인덱스,사이즈, Sort.by()
		
		// List<Question> questionList = questionRepository.findAll(); //Jpa는 getList가 없기 때문에 findAll()로 사용
		Page<Question> questionPage = questionRepository.findAll(pageable);
		
		return questionPage;
	  
	}
	// 질문 글 하나씩 가져오기
	public Question getQuestionOne(Long id){
		
		Question question = questionRepository.findById(id)
		                                      .orElseThrow(EntityNotFoundException::new);
		return question;
		
	}
	
	//질문글 DB에 저장하기
	public void saveQuestion(String subject, String  content, Users users) {
	
		Question q1 = new Question();
		q1.setSubject(subject);
		q1.setContent(content);
		q1.setCreateDate(LocalDateTime.now());
		q1.setUsers(users);		
		
		// Repository에 q1으로 저장 
		questionRepository.save(q1);
		
	}
	//수정하기
	public void modify(Question question, String subject, String content) {
		question.setSubject(subject);
		question.setContent(content);
		question.setModifyDate(LocalDateTime.now());
		
		questionRepository.save(question);
	}
	
	//질문글 삭제하기
	public void delete(Question question) {
		questionRepository.delete(question);
	}
	
	//추천 저장
	public void vote(Question question, Users users) {
		question.getVoter().add(users);
		questionRepository.save(question);
	}
	
	//검색기능 - Jpa에서 제공하는 Specification 인터페이스 사용
	private Specification<Question> search(String keyword){
		
		Specification<Question> spec = new Specification<Question>() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public Predicate toPredicate(Root<Question> question, CriteriaQuery<?> query,CriteriaBuilder criteriaBuilder) {
			
			query.distinct(true);
			Join<Question, Users> qu = question.join("users", JoinType.LEFT);
			Join<Question, Answer> qa = question.join("answerList", JoinType.LEFT);
			Join<Answer, Users> au = question.join("users", JoinType.LEFT);
		
		//제목,내용,질문작성자,답글내용,답글작성자
		return criteriaBuilder.or(criteriaBuilder.like(question.get("subject"), "%"+ keyword + "%"),
				criteriaBuilder.like(question.get("content"), "%"+ keyword + "%"),
				criteriaBuilder.like(qu.get("username"), "%"+ keyword + "%"),
				criteriaBuilder.like(qa.get("subject"), "%"+ keyword + "%"),
				criteriaBuilder.like(au.get("subject"), "%"+ keyword + "%"));
			
			
			}//toPredicate
		};// 익명클래스
		return spec;
		
	} // search

	
	

	/* specification<Question>를 사용할 때 
	 // QuestionRepository의Page<Question> findAll 메소드 호출하기
	public Page<Question> getList(int page, String keyword){
		//날짜별로 내림차순 할 때 사용
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		Specification<Question> spec = search(keyword);
		
		return questionRepository.findAll(spec, pageable);
	}
*/

	// QuestionRepository에 있는 @Query문 사용하기 
	public Page<Question> getList(int page, String keyword){
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		return questionRepository.findAllByKeyword(keyword, pageable);
	}



}
