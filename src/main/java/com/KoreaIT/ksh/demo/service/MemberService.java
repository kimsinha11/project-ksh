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

	public ResultData<Integer> join(String loginId, String loginPw, String name, String nickname, String cellphoneNum,
			String email) {
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

	public Member getMemberByNameAndEmail(String name, String email) {
		return memberRepository.getMemberByNameAndEmail(name, email);
	}

	public Member getMemberByLoginId(String loginId) {
		return memberRepository.getMemberByLoginId(loginId);
	}

	public Member getMemberById(int id) {
		return memberRepository.getMemberById(id);
	}

	public Member profile(int id) {
		return memberRepository.profile(id);
	}

	public ResultData modifyMember(int id, String loginPw, String name, String nickname, String cellphoneNum,
			String email) {
		memberRepository.modifyMember(id, loginPw, name, nickname, cellphoneNum, email);

		Member member = getMemberById(id);

		return ResultData.from("S-1", Ut.f("%d번 회원을 수정 했습니다", id), "member", member);
	}

	public int getMembersCount(String authLevel, String searchKeywordTypeCode, String searchKeyword) {
		return memberRepository.getMembersCount(authLevel, searchKeywordTypeCode, searchKeyword);
	}

	public List<Member> getForPrintMembers(String authLevel, String searchKeywordTypeCode, String searchKeyword,
			int itemsInAPage, int page) {

		int limitStart = (page - 1) * itemsInAPage;
		int limitTake = itemsInAPage;
		List<Member> members = memberRepository.getForPrintMembers(authLevel, searchKeywordTypeCode, searchKeyword,
				limitStart, limitTake);

		return members;
	}

	public Member getMemberByNickname(String nickname) {
		return memberRepository.getMemberByNickname(nickname);
	}

	public Member getMemberByEmail(String email) {
		return memberRepository.getMemberByEmail(email);
	}
	public void deleteMembers(List<Integer> memberIds) {
		for (int memberId : memberIds) {
			Member member = getMemberById(memberId);

			if (member != null) {
				deleteMember(member);
			}
		}
	}

	private void deleteMember(Member member) {
		memberRepository.deleteMember(member.getId());
	}
	@Transactional
	public void deleteMember(int memberId) {
		// 회원 정보 삭제
		memberRepository.deleteMemberById(memberId);

		// 회원과 관련된 게시물 삭제
		deleteMemberArticles(memberId);
	}

	public void deleteMemberArticles(int memberId) {
		// 회원과 관련된 게시물 조회 및 삭제
		List<Article> memberArticles = articleRepository.getArticlesByMemberId(memberId);

		for (Article article : memberArticles) {
			ArticleRepository.memberArticlesdelete(article);
		}
	}

}