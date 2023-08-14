package com.tjoeun.controller;



import java.security.Principal;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
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
import com.tjoeun.dto.QuestionFormDto;
import com.tjoeun.entity.Question;
import com.tjoeun.entity.Users;
import com.tjoeun.service.QuestionService;
import com.tjoeun.service.UsersService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/question")
public class QuestionController {
	
	private final QuestionService questionService;
	
	private final UsersService usersService;
	
	//질문글 전체 조회하기, 페이징 처리
	//검색기능 추가
	@GetMapping("/list")
	public String list(Model model, @RequestParam(value="page", defaultValue="0") int page,
														@RequestParam(value="keyword", defaultValue="") String keyword) {
														//값이 page로 들어오면 그 int page 정보가 들어가고 아무것도 안 들어오면 기본값 =0

		//질문글 조회 메소드
		//List<Question> questionList = questionService.getList();
		//model.addAttribute("questionList", questionList);
		
		//페이징 처리
		Page<Question> paging = questionService.getList(page, keyword);
		model.addAttribute("paging", paging);
		model.addAttribute("keyword", keyword);
		
		return "question_list";
	}
	
	@GetMapping("/detail/{id}")
	public String detail(@PathVariable("id") Long id, Model model, AnswerFormDto answerFormDto) {
		Question question = questionService.getQuestionOne(id);
		model.addAttribute("question", question);
		return "question_detail";
	}

	
	//질문 등록 했을 때 질문 폼으로이동
	@GetMapping("/create")
	@PreAuthorize("isAuthenticated()")
	public String createQuestion(QuestionFormDto questionFormDto) {
		//model.addAttribute()가 생략 됨 -> RequestScore에 올려줌
		return "question_form";
	}
	
	
	//질문 입력 받았을 때 
	@PostMapping("/create")
	@PreAuthorize("isAuthenticated()")
	public String createQuestion(@Valid QuestionFormDto questionFormDto,
														BindingResult result, Principal principal) {
		//@Valid 검증하는 메소드, BindingResult result
		
		if(result.hasErrors()) {
			return "question_form";
		}
		
		// UserService에서 Username가져오기
		Users users = usersService.getUsers(principal.getName());
		
		
		//질문을  DB에 저장 - 서비스 메소드 호출
		questionService.saveQuestion(questionFormDto.getSubject(),questionFormDto.getContent(),users);
		
		
		return "redirect:/question/list";
	}
	
	//수정 할려고 할 때 하는 메소드
	
	// sec:authorize="isAuthenticated()"맞추기 => @PreAuthorize
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")	//@PathVariable(JSP에서 {}안에 있는 내용 사용)
	public String questionModify(QuestionFormDto questionFormDto, @PathVariable("id") Long id, Principal principal) {
		
		Question question = questionService.getQuestionOne(id);
		if(!question.getUsers().getUsername().equals(principal.getName())){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한 없음");
		}
		//고친 내용을 Dto.Subject에 넣어라
		questionFormDto.setSubject(question.getSubject());
		questionFormDto.setContent(question.getContent());
		
		return "question_form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String questionModify(@Valid QuestionFormDto questionFormDto, BindingResult result,
													  Principal principal, @PathVariable("id") Long id) {
																					// modify/{id}랑 같이 때문에 ("id")생략 가능
		if(result.hasErrors()) {
			return "question_form";
		}
		//id가 일치하는지 확인
		Question question = questionService.getQuestionOne(id);
		if(!question.getUsers().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한 없음");
		}
		
		questionService.modify(question, questionFormDto.getSubject(),questionFormDto.getContent());
		
		return String.format("redirect:/question/detail/%s", id);
	}
	
	//질문글 삭제하기
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String questionDelete(Principal principal, @PathVariable("id") Long id) {
		Question question = questionService.getQuestionOne(id);
		//아이디가 같지 않으면 오류 발생시킴
		if(!question.getUsers().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한 없음");
		}
		
		questionService.delete(question);
		return "redirect:/";
	}
	
	//질문글 추천하기
	@PreAuthorize("isAuthenticated()") //로그인 한 사람만 추천할 수 있도록
	@GetMapping("/vote/{id}")
	public String questionVote(Principal principal, @PathVariable("id") Long id) {
		
		Question question = questionService.getQuestionOne(id);
		Users users = usersService.getUsers(principal.getName());
		questionService.vote(question, users);
		
		return String.format("redirect:/question/detail/%s", id);
	}
	

	
	
}




