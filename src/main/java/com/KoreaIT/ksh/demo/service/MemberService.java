package com.KoreaIT.ksh.demo.service;

import org.springframework.stereotype.Service;

import com.KoreaIT.ksh.demo.repository.MemberRepository;
import com.KoreaIT.ksh.demo.vo.Member;

@Service
public class MemberService {
	private MemberRepository memberRepository;

	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public int doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum,
			String email) {
		Member existsMember = getMemberByLoginId(loginId);
		
		if(existsMember != null) {
			return -1;
		}
		
		existsMember = getMemberByNameAndEmail(name, email);
		if(existsMember != null) {
			return -2;
		}
		memberRepository.doJoin(loginId, loginPw, name, nickname, cellphoneNum, email);
		return memberRepository.getLastInsertId();
	}

	private Member getMemberByNameAndEmail(String name, String email) {
		return memberRepository.getMemberByNameAndEmail(name, email);
	}

	private Member getMemberByLoginId(String loginId) {
		return memberRepository.getMemberByLoginId(loginId);
	}

	public Member getMemberById(int id) {
		return memberRepository.getMemberById(id);
	}

}