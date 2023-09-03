package com.example.restapipractice.boundedContext.article.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class ArticleControllerTest {
	@Autowired
	private MockMvc mvc;

	@Test
	@DisplayName("GET /articles")
	void t1() throws Exception {
		// When
		ResultActions resultActions = mvc
			.perform(
				get("/api/v1/articles")
			)
			.andDo(print());

		// Then
		resultActions
			.andExpect(status().is2xxSuccessful())
			.andExpect(jsonPath("$.resultCode").value("S-1"))
			.andExpect(jsonPath("$.msg").exists())
			.andExpect(jsonPath("$.data.articles[0].id").exists());
	}

	@Test
	@DisplayName("GET /articles/1")
	void t2() throws Exception {
		// When
		ResultActions resultActions = mvc
			.perform(
				get("/api/v1/articles/1")
			)
			.andDo(print());

		// Then
		resultActions
			.andExpect(status().is2xxSuccessful())
			.andExpect(jsonPath("$.resultCode").value("S-1"))
			.andExpect(jsonPath("$.msg").exists())
			.andExpect(jsonPath("$.data.article.id").value(1));
	}
}