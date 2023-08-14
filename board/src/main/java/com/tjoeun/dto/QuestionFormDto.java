package com.tjoeun.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class QuestionFormDto {

	@NotEmpty(message = "제목을 입력하세요.")
	@Size(max=500)
	private String subject;
	
	@NotEmpty(message = "내용을 입력하세요.")
	private String content;
}
