package com.example.restapipractice.boundedContext.member.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.restapipractice.boundedContext.member.entity.Member;
import com.example.restapipractice.boundedContext.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;

	public Member join(String username, String password, String email) {
		Member member = Member.builder()
			.username(username)
			.password(password)
			.email(email)
			.build();

		memberRepository.save(member);

		return member;
	}

	public Optional<Member> findByUsername(String username) {
		return memberRepository.findByUsername(username);
	}
}