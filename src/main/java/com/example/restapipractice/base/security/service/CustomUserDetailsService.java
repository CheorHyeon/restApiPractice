package com.example.restapipractice.base.security.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.restapipractice.boundedContext.member.entity.Member;
import com.example.restapipractice.boundedContext.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = memberRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));

		return new User(member.getUsername(), member.getPassword(), member.getAuthorities());
	}
}