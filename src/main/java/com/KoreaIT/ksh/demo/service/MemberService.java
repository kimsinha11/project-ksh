package com.KoreaIT.ksh.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.KoreaIT.ksh.demo.repository.ArticleRepository;
import com.KoreaIT.ksh.demo.repository.MemberRepository;
import com.KoreaIT.ksh.demo.util.Ut;
import com.KoreaIT.ksh.demo.vo.Article;
import com.KoreaIT.ksh.demo.vo.Member;
import com.KoreaIT.ksh.demo.vo.ResultData;

@Service
public class MemberService {
	
	@Value("${custom.siteMainUri")
	private String siteMainUri;
	
	@Value("${custom.siteName")
	private String siteName;
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private ArticleRepository articleRepository;
	
	public MemberService(MemberRepository memberRepository, ArticleRepository articleRepository) {
		this.memberRepository = memberRepository;
		this.articleRepository = articleRepository;
	}
	
	// 회원 가입
	public ResultData<Integer> join(String loginId, String loginPw, String name, String nickname, String cellphoneNum, String email) {
		// 로그인 아이디 중복체크
		Member existsMember = getMemberByLoginId(loginId);
		
		if (existsMember != null) {
			return ResultData.from("F-7", Ut.f("이미 사용중인 아이디(%s)입니다", loginId));
		}
		
		// 이름 + 이메일 중복체크
		existsMember = getMemberByNameAndEmail(name, email);
		
		if (existsMember != null) {
			return ResultData.from("F-8", Ut.f("이미 사용중인 이름(%s)과 이메일(%s)입니다", name, email));
		}
		
		memberRepository.join(loginId, loginPw, name, nickname, cellphoneNum, email);
		
		int id = memberRepository.getLastInsertId();
		
		return ResultData.from("S-1", "회원가입이 완료되었습니다", "id", id);
	}
	
	// 이름과 이메일로 회원 조회
	public Member getMemberByNameAndEmail(String name, String email) {
		return memberRepository.getMemberByNameAndEmail(name, email);
	}
	
	// 로그인 아이디로 회원 조회
	public Member getMemberByLoginId(String loginId) {
		return memberRepository.getMemberByLoginId(loginId);
	}
	
	// 회원 번호로 회원 조회
	public Member getMemberById(int id) {
		return memberRepository.getMemberById(id);
	}
	
	// 회원 프로필 조회
	public Member profile(int id) {
		return memberRepository.profile(id);
	}
	
	// 회원 수정
	public ResultData modifyMember(int id, String loginPw, String name, String nickname, String cellphoneNum, String email) {
		memberRepository.modifyMember(id, loginPw, name, nickname, cellphoneNum, email);
		
		Member member = getMemberById(id);
		
		return ResultData.from("S-1", Ut.f("%d번 회원을 수정했습니다", id), "member", member);
	}
	
	// 회원 수 조회
	public int getMembersCount(String authLevel, String searchKeywordTypeCode, String searchKeyword) {
		return memberRepository.getMembersCount(authLevel, searchKeywordTypeCode, searchKeyword);
	}
	
	// 조건에 맞는 회원 목록 조회
	public List<Member> getForPrintMembers(String authLevel, String searchKeywordTypeCode, String searchKeyword, int itemsInAPage, int page) {
		int limitStart = (page - 1) * itemsInAPage;
		int limitTake = itemsInAPage;
		List<Member> members = memberRepository.getForPrintMembers(authLevel, searchKeywordTypeCode, searchKeyword, limitStart, limitTake);
		
		return members;
	}
	
	// 닉네임으로 회원 조회
	public Member getMemberByNickname(String nickname) {
		return memberRepository.getMemberByNickname(nickname);
	}
	
	// 이메일로 회원 조회
	public Member getMemberByEmail(String email) {
		return memberRepository.getMemberByEmail(email);
	}
	
	// 여러 회원 삭제
	public void deleteMembers(List<Integer> memberIds) {
		for (int memberId : memberIds) {
			Member member = getMemberById(memberId);
			
			if (member != null) {
				deleteMember(member);
			}
		}
	}
	
	// 회원 삭제
	private void deleteMember(Member member) {
		memberRepository.deleteMember(member.getId());
	}
	
	// 회원 번호로 회원 삭제
	public void deleteMember(int memberId) {
		// 회원 정보 삭제
		memberRepository.deleteMemberById(memberId);
	}
}
