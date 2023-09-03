package com.example.restapipractice.boundedContext.member.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.example.restapipractice.base.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class Member extends BaseEntity {

	@Column(unique = true)
	private String username;

	@JsonIgnore
	private String password;

	private String email;

	// 현재 회원이 가지고 있는 권한들을 List<GrantedAuthority> 형태로 리턴
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		if (getUsername().equals("admin")) {
			authorities.add(new SimpleGrantedAuthority("ADMIN"));
		}
		authorities.add(new SimpleGrantedAuthority("MEMBER"));

		return authorities;
	}

	public Map<String, Object> toClaims() {
		return Map.of(
			"id", getId(),
			"username", getUsername()
		);
	}
}