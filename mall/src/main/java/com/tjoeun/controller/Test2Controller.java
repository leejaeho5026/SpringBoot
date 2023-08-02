package com.tjoeun.controller;



import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tjoeun.dto.ItemDTO;
import com.tjoeun.test.Person;

import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/test")
@Log4j2
public class Test2Controller {

//public void로 하면 test/t1으로 찾아감
	@GetMapping("/t1")
	// t1(Person person)하면 자동으로 생성하고 model에 앞글자 소문자로 올려줌
	public void t1(Person person, Model model) { // html에서 ${text}로 받기 때문에 Model로 같은이름으로 올린다.

		person.setName("더조은");
		person.setHeight(122);

		model.addAttribute("text", "String boot 2.7.14");

	}
	// public String ()으로하면 return "";으로 보내야되지만
	// public void ()으로 하면 ("")바로 찾아간다.

	@GetMapping("/t2")
	// ModelAttribute 생략
	public void t2(ItemDTO itemDTO) {

		itemDTO.setItemDetail("상세설명");
		itemDTO.setItemNm("상품1");
		itemDTO.setPrice(11111);
		itemDTO.setRegTime(LocalDateTime.now());
	}

	// ({2개 ,넣기})
	@GetMapping({"/t3", "/t4"})
	public void t3(ItemDTO itemDTO, Model model) {

		List<ItemDTO> itemList = new ArrayList<>();

		for (int i = 0; i < 10; i++) {
			itemDTO.setItemDetail("상세설명-" + (i + 1));
			itemDTO.setItemNm("상품1-" + (i + 1));
			itemDTO.setPrice(11111);
			itemDTO.setRegTime(LocalDateTime.now());
		}
		itemList.add(itemDTO);

		model.addAttribute("itemList", itemList);
	}

	@GetMapping("/t5")
	public void t5(String name, String height, Model model) {
		 log.info(">>>>>>>>>>>>>" + name + ","+ height);
		
		model.addAttribute("name",name);
		model.addAttribute("height", height);
	}
	
	
	@GetMapping({"/content1", "/content2"})
	public void content() {
		
	}
	
	
	
	
}
