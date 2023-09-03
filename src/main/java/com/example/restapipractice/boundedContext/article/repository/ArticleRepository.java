package com.example.restapipractice.boundedContext.article.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restapipractice.boundedContext.article.entity.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {
	List<Article> findAllByOrderByIdDesc();
}
