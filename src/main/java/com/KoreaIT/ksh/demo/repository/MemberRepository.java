package com.KoreaIT.ksh.demo.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.KoreaIT.ksh.demo.vo.Member;

@Mapper
public interface MemberRepository {

	@Select("""
			SELECT *
			FROM member
			WHERE member.id
			= #{id}
				""")
	Member profile(int id);

	@Insert("""
			INSERT INTO `member`
			SET regDate = NOW(),
			updateDate = NOW(),
			loginId = #{loginId},
			loginPw = #{loginPw},
			`name` = #{name},
			nickname = #{nickname},
			cellphoneNum = #{cellphoneNum},
			email = #{email}
			""")
	void join(String loginId, String loginPw, String name, String nickname, String cellphoneNum, String email);

	@Select("""
			SELECT *
			FROM `member`
			WHERE id = #{id}
			""")
	Member getMemberById(int id);

	@Select("""
			SELECT LAST_INSERT_ID()
			""")
	int getLastInsertId();

	@Select("""
			SELECT *
			FROM `member`
			WHERE loginId = #{loginId}
			""")
	Member getMemberByLoginId(String loginId);

	@Select("""
			SELECT *
			FROM `member`
			WHERE name = #{name}
			AND email = #{email}
			""")
	Member getMemberByNameAndEmail(String name, String email);

	@Update("""
			UPDATE `member`
			SET loginPw = #{loginPw},
			`name` = #{name},
			nickname = #{nickname},
			cellphoneNum = #{cellphoneNum},
			email = #{email},
			updateDate= NOW()
			WHERE id = #{id}
					""")
	void modifyMember(int id, String loginPw, String name, String nickname, String cellphoneNum, String email);



}