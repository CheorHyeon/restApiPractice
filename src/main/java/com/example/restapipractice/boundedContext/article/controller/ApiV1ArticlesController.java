package com.example.restapipractice.boundedContext.article.controller;

import static org.springframework.http.MediaType.*;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.restapipractice.base.rsData.RsData;
import com.example.restapipractice.boundedContext.article.entity.Article;
import com.example.restapipractice.boundedContext.article.service.ArticleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/articles", produces = APPLICATION_JSON_VALUE)
@Tag(name = "ApiV1ArticlesController", description = "게시물 CRUD 컨트롤러")
public class ApiV1ArticlesController {
	private final ArticleService articleService;

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
}