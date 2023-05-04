package com.KoreaIT.ksh.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.ksh.demo.service.MemberService;
import com.KoreaIT.ksh.demo.util.Ut;
import com.KoreaIT.ksh.demo.vo.Member;
import com.KoreaIT.ksh.demo.vo.ResultData;

@Controller
public class UsrMemberController {
	@Autowired
	private MemberService memberService;

	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public Object doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum,
			String email) {
		
		if (Ut.empty(loginId)) {
			return "아이디를 입력해주세요";
		}
		if (Ut.empty(loginPw)) {
			return "비밀번호를 입력해주세요";
		}
		if (Ut.empty(name)) {
			return "이름을 입력해주세요";
		}
		if (Ut.empty(nickname)) {
			return "닉네임을 입력해주세요";
		}
		if (Ut.empty(cellphoneNum)) {
			return "전화번호를 입력해주세요";
		}
		if (Ut.empty(email)) {
			return "이메일을 입력해주세요";
		}
		
		ResultData joinRd = memberService.doJoin(loginId, loginPw, name, nickname, cellphoneNum, email);
		if (joinRd.isFail()) {
			return joinRd;
		}

		
		
		Member member = memberService.getMemberById((int) joinRd.getData1());

		return ResultData.newData(joinRd, member);
	}
	
	@RequestMapping("/usr/member/doLogin")
	@ResponseBody
	public ResultData doLogin(HttpSession httpSession, String loginId, String loginPw) {
		boolean isLogined = false;
		
		if(httpSession.getAttribute("loginedMemberId")!=null) {
			isLogined = true;
		}
		//F-A 로그인 오류 F-B 로그아웃 오류 F-N 빈 값 오류 F-F 없거나 불일치 오류
		if(isLogined) {
			return ResultData.from("F-A", "이미 로그인 상태입니다");
		}
		if(Ut.empty(loginId)) {
			return ResultData.from("F-N", "아이디를 입력해주세요");
		}
		if(Ut.empty(loginPw)) {
			return ResultData.from("F-N", "비밀번호를 입력해주세요");
		}
		
		Member member = memberService.getMemberByLoginId(loginId);
		
		if (member == null) {
			return ResultData.from("F-F", Ut.f("%s는 존재하지 않는 아이디입니다", loginId));
		}

		if (member.getLoginPw().equals(loginPw) == false) {
			return ResultData.from("F-F", Ut.f("비밀번호가 일치하지 않습니다"));
		}
		
		httpSession.setAttribute("loginedMemberId", member.getId());
		
		return ResultData.from("S-1", Ut.f("%s님 환영합니다", member.getNickname()));
	}
	
	@RequestMapping("/usr/member/doLogout")
	@ResponseBody
	public ResultData doLogout(HttpSession httpSession) {
		boolean isLogined = false;
		
		if(httpSession.getAttribute("loginedMEmberid")==null) {
		
			isLogined = true;
		}
		
		if(isLogined) {
			return ResultData.from("F-B", "이미 로그아웃 상태입니다");
		}
		
		httpSession.removeAttribute("loginedMemberId");	
		
		return ResultData.from("S-1", "로그아웃 되었습니다");
	}
}