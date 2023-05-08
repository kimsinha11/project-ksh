package com.KoreaIT.ksh.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	@Autowired
	private Rq rq;

	@RequestMapping("/usr/member/login")
	public String login() {
		
		return "usr/member/login";
	}

	@RequestMapping("/usr/member/doLogin")
	@ResponseBody
	public String doLogin(String loginId, String loginPw, @RequestParam(defaultValue = "/") String afterLoginUri) {

		if (rq.isLogined()) {
			return Ut.jsHistoryBack("F-A", "이미 로그인 상태입니다.");
		}
		if (Ut.empty(loginId)) {
			return Ut.jsHistoryBack("F-3", "아이디를 입력해주세요.");
		}
		if (Ut.empty(loginPw)) {
			return Ut.jsHistoryBack("F-4", "비밀번호를 입력해주세요");
		}

		Member member = memberService.getMemberByLoginId(loginId);

		if (member == null) {
			return Ut.jsHistoryBack("F-3", "없는 아이디입니다");
		}

		if (member.getLoginPw().equals(loginPw) == false) {
			return Ut.jsHistoryBack("F-4", "비밀번호가 틀렸습니다");
		}

		rq.login(member);

		     return Ut.jsReplace("S-1", Ut.f("%s님 환영합니다", member.getNickname()), afterLoginUri);
		
	}

	@RequestMapping("/usr/member/logout")
	@ResponseBody
	public String doLogout( @RequestParam(defaultValue = "/") String afterLogoutUri) {

		if (!rq.isLogined()) {
			return Ut.jsHistoryBack("F-1", "이미 로그아웃 상태입니다.");
		}

		rq.logout();

		return Ut.jsReplace("S-1", "로그아웃 되었습니다", afterLogoutUri);
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
			return Ut.jsHistoryBack("F-3", "아이디를 입력해주세요.");
		}
		if (Ut.empty(loginPw)) {
			return Ut.jsHistoryBack("F-3", "비밀번호를 입력해주세요.");
		}
		if (Ut.empty(name)) {
			return Ut.jsHistoryBack("F-3", "이름을 입력해주세요.");
		}
		if (Ut.empty(nickname)) {
			return Ut.jsHistoryBack("F-3", "닉네임을 입력해주세요.");
		}
		if (Ut.empty(cellphoneNum)) {
			return Ut.jsHistoryBack("F-3", "전화번호를 입력해주세요.");
		}
		if (Ut.empty(email)) {
			return Ut.jsHistoryBack("F-3", "이메일을 입력해주세요.");
		}

		ResultData<Integer> joinRd = memberService.join(loginId, loginPw, name, nickname, cellphoneNum, email);

		if (joinRd.isFail()) {
			return Ut.jsHistoryBack("F-3", "이미 사용중인 아이디입니다");
		}

		Member member = memberService.getMemberById(joinRd.getData1());

		return String.format("<script>alert('회원가입 성공.'); location.replace('../article/list');</script>");
	}

	@RequestMapping("/usr/member/profile")
	public String profile(Model model, int id) {
		id = rq.getLoginedMemberId();
		Member member = memberService.profile(id);

		if (member == null) {
			return rq.jsHistoryBackOnView(Ut.f("%d번 글은 존재하지 않습니다", id));
		}

		model.addAttribute("member", member);
		model.addAttribute("loginedMemberId", rq.getLoginedMemberId());

		return "usr/member/profile";
	}

	@RequestMapping("/usr/member/checkPw")
	public String showcheckPw(Model model, int id) {
		Member member = memberService.getMemberById(id);
		
		model.addAttribute("member", member);
		return "usr/member/checkPw";
	}
	
	@RequestMapping("/usr/member/doCheckPw")

	public String doCheckPw(Model model, String loginId, String loginPw) {
	
		if (Ut.empty(loginPw)) {
			return Ut.jsHistoryBack("F-4", "비밀번호를 입력해주세요");
		}

		Member member = memberService.getMemberByLoginId(loginId);

		if (member == null) {
			return Ut.jsHistoryBack("F-3", "없는 비밀번호입니다");
		}

		if (member.getLoginPw().equals(loginPw) == false) {
			return Ut.jsHistoryBack("F-4", "비밀번호가 틀렸습니다");
		}
		model.addAttribute("member", member);
		return "usr/member/mmodify";

		
	}


	@RequestMapping("/usr/member/domModify")
	@ResponseBody
	public String domModify(int id, String loginPw, String name, String nickname, String cellphoneNum, String email) {

		Member member = memberService.getMemberById(id);

		if (member == null) {
			return Ut.jsHistoryBack("F-D", id + "번 회원은 존재하지 않습니다.");
		}
		
		if (Ut.empty(loginPw)) {
			loginPw = null;
		}
		if(Ut.empty(name)) {
			return rq.jsHistoryBackOnView("이름을 입력해주세요");
		}
		if(Ut.empty(nickname)) {
			return rq.jsHistoryBackOnView("닉네임을 입력해주세요");
		}
		if(Ut.empty(cellphoneNum)) {
			return rq.jsHistoryBackOnView("전화번호를 입력해주세요");
		}
		if(Ut.empty(email)) {
			return rq.jsHistoryBackOnView("이메일을 입력해주세요");
		}
		memberService.modifyMember(id, loginPw, name, nickname, cellphoneNum, email);

		return Ut.jsReplace("S-1", "수정완료", Ut.f("../member/profile?id=%d", id));

	}

}