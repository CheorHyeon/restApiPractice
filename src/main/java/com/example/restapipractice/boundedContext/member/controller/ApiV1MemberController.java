package com.example.restapipractice.boundedContext.member.controller;

import static org.springframework.http.MediaType.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.restapipractice.base.rsData.RsData;
import com.example.restapipractice.boundedContext.member.entity.Member;
import com.example.restapipractice.boundedContext.member.service.MemberService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RestController // 모든 메서드에서 @ResponseBody 생략 가능
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/member", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE) // Json 받고, 결과물 반환
public class ApiV1MemberController {
	private final MemberService memberService;
	@Data
	public static class LoginRequest {
		@NotBlank
		private String username;
		@NotBlank
		private String password;
	}
	
	@AllArgsConstructor
	@Getter
	public static class LoginResponse {
		private final String accessToken;
	}
	@PostMapping("/login")
	// @RequestBody : 요청의 본문(Json, Xml 등)을 Java 객체로 변환
	// access Token 생성
	public RsData<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse resp) {
		String accessToken = memberService.genAccessToken(loginRequest.getUsername(), loginRequest.getPassword());

		// 응답을 규격화한 객체 반환
		// 잭슨이 Json형태로 규격화하여 반환됨
		return RsData.of(
			"S-1",
			"엑세스토큰이 생성되었습니다.",
			new LoginResponse(accessToken)
		);
	}

	@AllArgsConstructor
	@Getter
	public static class MeResponse {
		private final Member member;
	}

	// access Token을 소비
	// consumes = ALL_VALUE => 나는 딱히 JSON 을 입력받기를 고집하지 않겠다.
	@GetMapping(value = "/me", consumes = ALL_VALUE)
	public RsData<MeResponse> me() {
		Member member = memberService.findByUsername("user1").get();

		return RsData.of(
			"S-1",
			"성공",
			new MeResponse(member)
		);
	}
}