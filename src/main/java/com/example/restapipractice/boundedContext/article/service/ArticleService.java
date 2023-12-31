package com.example.restapipractice.boundedContext.article.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.restapipractice.base.rsData.RsData;
import com.example.restapipractice.boundedContext.article.entity.Article;
import com.example.restapipractice.boundedContext.article.repository.ArticleRepository;
import com.example.restapipractice.boundedContext.member.entity.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleService {
	private final ArticleRepository articleRepository;

	public RsData<Article> write(Member author, String subject, String content) {
		Article article = Article.builder()
			.author(author)
			.subject(subject)
			.content(content)
			.build();

		articleRepository.save(article);

		return RsData.of(
			"S-1",
			"게시물이 생성되었습니다.",
			article
		);
	}

	public List<Article> findAll() {
		return articleRepository.findAllByOrderByIdDesc();
	}

	public Optional<Article> findById(Long id) {
		return articleRepository.findById(id);
	}

	public RsData delete(Article article) {
		articleRepository.delete(article);
		return RsData.of("S-1",
			"%d번 게시물이 삭제되었습니다.".formatted(article.getId()));
	}

	public RsData canDelete(Member actor, Article article) {
		if (Objects.equals(actor.getId(), article.getAuthor().getId())) {
			return RsData.of(
				"S-1",
				"게시물을 삭제할 수 있습니다."
			);
		}

		return RsData.of(
			"F-1",
			"게시물을 삭제할 수 없습니다."
		);
	}

	public RsData<Article> modify(Article article, String subject, String content) {
		if (subject != null)
			article.setSubject(subject);
		if (content != null)
			article.setContent(content);
		articleRepository.save(article);
		return RsData.of(
			"S-1",
			"%d번 게시물이 수정되었습니다.".formatted(article.getId()),
			article
		);
	}

	public RsData canModify(Member actor, Article article) {
		if (Objects.equals(actor.getId(), article.getAuthor().getId())) {
			return RsData.of(
				"S-1",
				"게시물을 수정할 수 있습니다."
			);
		}

		return RsData.of(
			"F-1",
			"게시물을 수정할 수 없습니다."
		);
	}
}