package com.tjoeun.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class AnswerFormDto {

	@NotEmpty(message="답변 내용을 입력하세요")
	private String content;
}
