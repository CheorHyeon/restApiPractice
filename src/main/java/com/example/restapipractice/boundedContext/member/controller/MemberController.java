package com.example.restapipractice.boundedContext.member.controller;

import static org.springframework.http.MediaType.*;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.restapipractice.boundedContext.member.entity.Member;
import com.example.restapipractice.boundedContext.member.service.MemberService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController // 모든 메서드에서 @ResponseBody 생략 가능
@RequiredArgsConstructor
@RequestMapping(value = "/member", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE) // Json 받고, 결과물 반환
public class MemberController {
	private final MemberService memberService;
	@Data
	public static class LoginRequest {
		@NotBlank
		private String username;
		@NotBlank
		private String password;
	}
	@PostMapping("/login")
	// @RequestBody : 요청의 본문(Json, Xml 등)을 Java 객체로 변환
	public Member login(@Valid @RequestBody LoginRequest loginRequest) {
		return memberService.findByUsername(loginRequest.getUsername()).orElse(null);
	}
}