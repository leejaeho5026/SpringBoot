package com.tjoeun.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tjoeun.dto.TmpDto;

//리턴값을 브라우저로 보낼 때 RestController사용
//localhost:8787(getMapping("/")은 브라우저에 localhost:8787이 뜻, spring Boot를 보내라
@RestController
public class TestController {

	@GetMapping("/")
	public String home() {
		return "Spring Boot";
	}

	@GetMapping("/test1")
	public String test1() {
		
		TmpDto t1 = TmpDto.builder()
											.name("더조은")
											.height(222)
											.build();
		return "Spring Boot bulider1";
	}


	@GetMapping("/test2")
	public String test2() {
		
		TmpDto t2 = TmpDto.builder()
											.height(333)
											.build();
		return "Spring Boot bulider2";
	}






}
