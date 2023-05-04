package com.KoreaIT.ksh.demo.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberRepository {

	// 서비스 메서드
	void doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum, String email);
			
}