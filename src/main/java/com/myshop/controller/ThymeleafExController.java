package com.myshop.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myshop.dto.ItemDto;

@Controller // Controller역할을 하는 클래스로 정의하는 어노테이션
@RequestMapping(value = "/thymeleaf") // request url 경로 지정하는 어노테이션
public class ThymeleafExController {
	
	@GetMapping(value = "/ex01") //url 지정하는 어노테이션
	public String thymeleafEx01(Model model) {
		model.addAttribute("data", "타임리프 예제입니다.");
		return "thymeleafEx/thymeleafEx01";
	}
	
	@GetMapping(value = "/ex02") //url 지정하는 어노테이션
	public String thymeleafEx02(Model model) {
		ItemDto itemDto = new ItemDto();
		
		itemDto.setItemNm("테스트 상품");
		itemDto.setPrice(10000);
		itemDto.setItemDetail("테스트 상품 상세 설명");
		itemDto.setRegTime(LocalDateTime.now());
		
		model.addAttribute("itemDto", itemDto); // 값을 뷰 단에다 넣어주는 것? ("키", 값)
		
		return "thymeleafEx/thymeleafEx02";
		
	}
	
	@GetMapping(value = "/ex03") //url 지정하는 어노테이션
	public String thymeleafEx03(Model model) {
		
		List<ItemDto> itemDtoList = new ArrayList<>();
		
		for (int i=1; i<=10; i++ ) {
			ItemDto itemDto = new ItemDto();
			
			itemDto.setItemNm("테스트 상품" + i);
			itemDto.setPrice(10000 + i);
			itemDto.setItemDetail("테스트 상품 상세 설명" + i);
			itemDto.setRegTime(LocalDateTime.now());
			
			itemDtoList.add(itemDto);
		}
		
		model.addAttribute("itemDtoList", itemDtoList); // 값을 뷰 단에다 넣어주는 것? ("키", 값)
		// 아무거나
		return "thymeleafEx/thymeleafEx03";
		
	}
	
	@GetMapping(value = "/ex04") //url 지정하는 어노테이션
	public String thymeleafEx04(Model model) {
		
		List<ItemDto> itemDtoList = new ArrayList<>();
		
		for (int i=1; i<=10; i++ ) {
			ItemDto itemDto = new ItemDto();
			
			itemDto.setItemNm("테스트 상품" + i);
			itemDto.setPrice(10000 + i);
			itemDto.setItemDetail("테스트 상품 상세 설명" + i);
			itemDto.setRegTime(LocalDateTime.now());
			
			itemDtoList.add(itemDto);
		}
		
		model.addAttribute("itemDtoList", itemDtoList); // 값을 뷰 단에다 넣어주는 것? ("키", 값)
		// 아무거나
		return "thymeleafEx/thymeleafEx04";
		
	}
	
	@GetMapping(value = "/ex05") //url 지정하는 어노테이션
	public String thymeleafEx05(Model model) {
		return "thymeleafEx/thymeleafEx05";
	}
	
	@GetMapping(value = "/ex06") //url 지정하는 어노테이션
	public String thymeleafEx06(String param1, String param2, Model model) {
		System.out.println(param1 + "," +param2);
		model.addAttribute("param1", param1);
		model.addAttribute("param2", param2);
		
		return "thymeleafEx/thymeleafEx06";
	}
	
	@GetMapping(value = "/ex07") //url 지정하는 어노테이션
	public String thymeleafEx07(Model model) {
		return "thymeleafEx/thymeleafEx07";
	}
	
}
