package com.KoreaIT.ksh.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.ksh.demo.service.MemberService;
import com.KoreaIT.ksh.demo.util.Ut;
import com.KoreaIT.ksh.demo.vo.Member;
import com.KoreaIT.ksh.demo.vo.ResultData;
import com.KoreaIT.ksh.demo.vo.Rq;

@Controller
public class UsrMemberController {
	@Autowired
	private MemberService memberService;


	@RequestMapping("/usr/member/login")
	public String login(HttpServletRequest req) {
		Rq rq = (Rq) req.getAttribute("rq");
		
		return "usr/member/login";
	}
	
	@RequestMapping("/usr/member/doLogin")
	@ResponseBody
	public String doLogin(HttpServletRequest req,String loginId, String loginPw) {
		Rq rq = (Rq) req.getAttribute("rq");
		
		if (rq.isLogined()) {
			return Ut.jsHistoryBack("F-A", "이미 로그인 상태입니다.");
		}
		if (Ut.empty(loginId)) {
			return Ut.jsHistoryBack("F-N", "아이디를 입력해주세요.");
		}
		if (Ut.empty(loginPw)) {
			return Ut.jsHistoryBack("F-N", "비밀번호를 입력해주세요");
		}

		Member member = memberService.getMemberByLoginId(loginId);

		if (member == null) {
			return Ut.jsHistoryBack("F-F", "없는 아이디입니다");
		}

		if (member.getLoginPw().equals(loginPw) == false) {
			return Ut.jsHistoryBack("F-F", "비밀번호가 틀렸습니다");
		}
		
		rq.login(member);

		return String.format("<script>alert('로그인 성공.'); location.replace('../article/list');</script>");
	}
	
	@RequestMapping("/usr/member/logout")
	@ResponseBody
	public String doLogout(HttpServletRequest req) {

		Rq rq = (Rq) req.getAttribute("rq");
		if(!rq.isLogined()) {
			return Ut.jsHistoryBack("F-B", "이미 로그아웃 상태입니다.");
		}
		
		rq.logout();

		return Ut.jsReplace("S-1", "로그아웃 되었습니다", "/");
	}
	
	@RequestMapping("/usr/member/join")
	public String join(HttpServletRequest req) {
		Rq rq = (Rq) req.getAttribute("rq");
		
		return "usr/member/join";
	}

	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public String doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum,
			String email) {

		if (Ut.empty(loginId)) {
			return Ut.jsHistoryBack("F-N", "아이디를 입력해주세요.");
		}
		if (Ut.empty(loginPw)) {
			return Ut.jsHistoryBack("F-N", "비밀번호를 입력해주세요.");
		}
		if (Ut.empty(name)) {
			return Ut.jsHistoryBack("F-N", "이름을 입력해주세요.");
		}
		if (Ut.empty(nickname)) {
			return Ut.jsHistoryBack("F-N", "닉네임을 입력해주세요.");
		}
		if (Ut.empty(cellphoneNum)) {
			return Ut.jsHistoryBack("F-N", "전화번호를 입력해주세요.");
		}
		if (Ut.empty(email)) {
			return Ut.jsHistoryBack("F-N", "이메일을 입력해주세요.");
		}

		ResultData joinRd = memberService.doJoin(loginId, loginPw, name, nickname, cellphoneNum, email);

		if (joinRd.isFail()) {
			return Ut.jsHistoryBack("F-3", "이미 사용중인 아이디입니다");
		}

		Member member = memberService.getMemberById((int) joinRd.getData1());

		return String.format("<script>alert('회원가입 성공.'); location.replace('../article/list');</script>");
	}
}