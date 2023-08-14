package com.tjoeun.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.tjoeun.dto.AnswerFormDto;
import com.tjoeun.entity.Answer;
import com.tjoeun.entity.Question;
import com.tjoeun.entity.Users;
import com.tjoeun.service.AnswerService;
import com.tjoeun.service.QuestionService;
import com.tjoeun.service.UsersService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/answer")
@RequiredArgsConstructor
public class AnswerController {
	
	private final QuestionService questionService;
	
	private final AnswerService answerService;
	
	private final UsersService usersService;
	
	//답글 등록
	// id <-- Question 의 id
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create/{id}")
	public String createAnswer(@Valid AnswerFormDto answerFormDto, BindingResult result,
													@PathVariable("id") Long id, Model model,
													@RequestParam("content") String content,
													Principal principal) {
		//content는 JSP에서 name="content" id="content"로 답변내용을 저장해서 올리기 때문에
		//@RequestParam("content")로 받아야함
		// principal usersService에서 Usersname가져올려고 사용		
		
		Question question = questionService.getQuestionOne(id);
		Users users = usersService.getUsers(principal.getName());
		
		if(result.hasErrors()) {
			//리턴하기전에 가져와야함
			model.addAttribute("question", question);
			return "question_detail";
		}
		
		// 불러온 question entity 에 대한 답변(answer) 저장하기
		Answer answer = answerService.createAnswer(question, answerFormDto.getContent(),users);
	
		return String.format("redirect:/question/detail/%s#answer_%s", id, answer.getId());
	}

	//답글 수정하기
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String answerModify(AnswerFormDto answerFormDto, @PathVariable("id") Long id,
													Principal principal) {
		
		Answer answer = answerService.getAnswerOne(id);
		
		if(!answer.getUsers().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한 없음");
		}
		
		answerFormDto.setContent(answer.getContent());
	
			return "answer_form";
	}
	
	//답글 수정
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String answerModify(@Valid AnswerFormDto answerFormDto, BindingResult result, 
												 	@PathVariable("id") Long id, Principal principal) {
	
	if(result.hasErrors()) {
		return "answer_form";
	}
	Answer answer = answerService.getAnswerOne(id);
	if(!answer.getUsers().getUsername().equals(principal.getName())) {
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한 없음");
	}
	answerService.modify(answer, answerFormDto.getContent());
	
	return String.format("redirect:/question/detail/%s/#answer_%s", answer.getQuestion().getId(), answer.getId());
	}
	
	//답글 삭제
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String answerDelete(Principal principal, @PathVariable("id") Long id) {
		
		Answer answer = answerService.getAnswerOne(id);
		if(!answer.getUsers().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한 없음");
	}
	answerService.delete(answer);
	
	return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());	
	}


	//추천하기
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/vote/{id}")
	public String answerVote(Principal principal, @PathVariable("id") Long id) {
		
		Answer answer = answerService.getAnswerOne(id);
		Users users = usersService.getUsers(principal.getName());
		
		answerService.vote(answer, users);
		
		return String.format("redirect:/question/detail/%s/#answer_%s", answer.getQuestion().getId(), answer.getId());	
		
	}



















}
