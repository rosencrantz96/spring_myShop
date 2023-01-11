package com.myshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // Controller역할을 하는 클래스로 정의하는 어노테이션
@RequestMapping(value = "/thymeleaf") // request url 경로 지정하는 어노테이션
public class ThymeleafExController {
	
	@GetMapping(value = "/ex01") //url 지정하는 어노테이션
	public String thymeleafEx01(Model model) {
		model.addAttribute("data", "타임리프 예제입니다.");
		return "thymeleafEx/thymeleafEx01";
	}
}
