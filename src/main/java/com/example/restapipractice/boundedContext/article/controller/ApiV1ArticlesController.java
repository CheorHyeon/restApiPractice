package com.example.restapipractice.boundedContext.article.controller;

import static org.springframework.http.MediaType.*;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.restapipractice.base.rsData.RsData;
import com.example.restapipractice.boundedContext.article.entity.Article;
import com.example.restapipractice.boundedContext.article.service.ArticleService;
import com.example.restapipractice.boundedContext.member.entity.Member;
import com.example.restapipractice.boundedContext.member.service.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/articles", produces = APPLICATION_JSON_VALUE)
@Tag(name = "ApiV1ArticlesController", description = "게시물 CRUD 컨트롤러")
public class ApiV1ArticlesController {
	private final ArticleService articleService;
	private final MemberService memberService;

	@AllArgsConstructor
	@Getter
	public static class ArticlesResponse {
		private final List<Article> articles;
	}

	@GetMapping(value = "")
	@Operation(summary = "조회")
	public RsData<ArticlesResponse> articles() {
		List<Article> articles = articleService.findAll();

		return RsData.of(
			"S-1",
			"성공",
			new ArticlesResponse(articles)
		);
	}

	@AllArgsConstructor
	@Getter
	public static class ArticleResponse {
		private final Article article;
	}

	@GetMapping(value = "/{id}")
	@Operation(summary = "단건조회")
	public RsData<ArticleResponse> article(@PathVariable Long id) {

		Optional<Article> article = articleService.findById(id);

		if (!article.isPresent()) {
			return RsData.of(
				"F-1",
				"%d번 게시물은 존재하지 않습니다.".formatted(id),
				null);
		}

		return RsData.of(
			"S-1",
			"성공",
			new ArticleResponse(article.get())
		);
	}

	@Data
	public static class WriteRequest {
		@NotBlank
		private String subject;
		@NotBlank
		private String content;
	}

	@AllArgsConstructor
	@Getter
	public static class WriteResponse {
		private final Article article;
	}

	@PostMapping(value = "", consumes = APPLICATION_JSON_VALUE)
	@Operation(summary = "등록", security = @SecurityRequirement(name = "bearerAuth"))
	public RsData<WriteResponse> write(
		@AuthenticationPrincipal User user,
		@Valid @RequestBody WriteRequest writeRequest
	) {
		Member member = memberService.findByUsername(user.getUsername()).orElseThrow();
		RsData<Article> writeRs = articleService.write(member, writeRequest.getSubject(), writeRequest.getContent());

		if (writeRs.isFail()) return (RsData) writeRs;

		return RsData.of(
			writeRs.getResultCode(),
			writeRs.getMsg(),
			new WriteResponse(writeRs.getData())
		);
	}

	@Data
	public static class ModifyRequest {
		private String subject;
		private String content;
	}

	@AllArgsConstructor
	@Getter
	public static class ModifyResponse {
		private final Article article;
	}

	@PatchMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
	@Operation(summary = "수정", security = @SecurityRequirement(name = "bearerAuth"))
	public RsData<ModifyResponse> modify(
		@AuthenticationPrincipal User user,
		@Valid @RequestBody ModifyRequest modifyRequest,
		@PathVariable Long id
	) {
		Member member = memberService.findByUsername(user.getUsername()).orElseThrow();

		Optional<Article> opArticle = articleService.findById(id);

		if (opArticle.isEmpty()) return RsData.of(
			"F-1",
			"%d번 게시물은 존재하지 않습니다.".formatted(id),
			null
		);

		RsData canModifyRs = articleService.canModify(member, opArticle.get());

		if (canModifyRs.isFail()) return canModifyRs;

		RsData<Article> modifyRs = articleService.modify(opArticle.get(), modifyRequest.getSubject(), modifyRequest.getContent());

		return RsData.of(
			modifyRs.getResultCode(),
			modifyRs.getMsg(),
			new ModifyResponse(modifyRs.getData())
		);
	}
}