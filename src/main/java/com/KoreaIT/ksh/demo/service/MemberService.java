package com.KoreaIT.ksh.demo.service;

import org.springframework.stereotype.Service;

import com.KoreaIT.ksh.demo.repository.MemberRepository;

@Service
public class MemberService {
	private MemberRepository memberRepository;

	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public void doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum,
			String email) {
		memberRepository.doJoin(loginId, loginPw, name, nickname, cellphoneNum, email);
	}

}