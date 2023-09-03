package com.example.restapipractice.boundedContext.article.entity;

import com.example.restapipractice.base.entity.BaseEntity;
import com.example.restapipractice.boundedContext.member.entity.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Article extends BaseEntity {
	@ManyToOne
	private Member author;
	private String subject;
	private String content;
}