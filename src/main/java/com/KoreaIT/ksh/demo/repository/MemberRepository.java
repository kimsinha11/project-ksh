package com.KoreaIT.ksh.demo.repository;

import org.apache.ibatis.annotations.Mapper;

import com.KoreaIT.ksh.demo.vo.Member;

@Mapper
public interface MemberRepository {

	// 서비스 메서드
	void doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum, String email);

	int getLastInsertId();

	Member getMemberById(int id);

	Member getMemberByLoginId(String loginId);

	Member getMemberByNameAndEmail(String name, String email);
			
}