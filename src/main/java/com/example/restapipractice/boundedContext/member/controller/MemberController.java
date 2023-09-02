package com.example.restapipractice.boundedContext.member.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // 모든 메서드에서 @ResponseBody 생략 가능
@RequestMapping("/member")
public class MemberController {
	@PostMapping("/login")
	public String login() {
		return "성공";
	}
}