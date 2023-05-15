package com.KoreaIT.ksh.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.ksh.demo.service.BoardService;
import com.KoreaIT.ksh.demo.service.MemberService;
import com.KoreaIT.ksh.demo.util.Ut;
import com.KoreaIT.ksh.demo.vo.Article;
import com.KoreaIT.ksh.demo.vo.Board;
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
		
		// 우리가 갈 수 있는 경로를 경우의 수로 표현
		// 인코딩
		// 그 외에는 처리 불가 -> 메인으로 보내기
		
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
	public String doJoin(String loginId, String loginPw, String loginPwConfirm, String name, String nickname, String cellphoneNum,
			String email, @RequestParam(defaultValue = "/") String afterLoginUri) {

		if (Ut.empty(loginId)) {
			return rq.jsHistoryBack("F-N", "아이디를 입력해주세요.");
		}
		if (Ut.empty(loginPw)) {
			return rq.jsHistoryBack("F-N", "비밀번호를 입력해주세요.");
		}
		if (Ut.empty(loginPwConfirm)) {
			return rq.jsHistoryBack("F-N", "비밀번호확인을 입력해주세요.");
		}
		if (Ut.empty(name)) {
			return rq.jsHistoryBack("F-N", "이름을 입력해주세요.");
		}
		if (Ut.empty(nickname)) {
			return rq.jsHistoryBack("F-N", "닉네임을 입력해주세요.");
		}
		if (Ut.empty(cellphoneNum)) {
			return rq.jsHistoryBack("F-N", "전화번호를 입력해주세요.");
		}
		if (Ut.empty(email)) {
			return rq.jsHistoryBack("F-N", "이메일을 입력해주세요.");
		}

		ResultData<Integer> joinRd = memberService.join(loginId, loginPw, name, nickname, cellphoneNum, email);

		if (joinRd.isFail()) {
			return Ut.jsHistoryBack("F-3", "이미 사용중인 아이디입니다");
		}

		Member member = memberService.getMemberById(joinRd.getData1());
		
		String afterJoinUri = "../member/login?afterLoginUri=" + Ut.getEncodedUri(afterLoginUri);

		return Ut.jsReplace("S-1", Ut.f("회원가입이 완료되었습니다"), afterJoinUri);
	}
	@RequestMapping("/usr/member/getLoginIdDup")
	@ResponseBody
	public ResultData getLoginIdDup(String loginId) {
		
		if (Ut.empty(loginId)) {
			return ResultData.from("F-N", "아이디를 입력해주세요");
		}
		
		Member existsMember = memberService.getMemberByLoginId(loginId);
		
		if (existsMember != null) {
			return ResultData.from("F-1", "해당 아이디는 이미 사용중인 아이디입니다", "loginId", loginId);
		}
		
		return ResultData.from("S-1", "사용 가능!", "loginId", loginId);
	}
	@RequestMapping("/usr/member/getNicknameDup")
	@ResponseBody
	public ResultData getNicknameDup(String nickname) {
		
		if (Ut.empty(nickname)) {
			return ResultData.from("F-N", "닉네임를 입력해주세요");
		}
		
		Member existsMember = memberService.getMemberByNickname(nickname);
		
		if (existsMember != null) {
			return ResultData.from("F-1", "해당 닉네임은 이미 사용중인 닉네임입니다", "nickname", nickname);
		}
		
		return ResultData.from("S-1", "사용 가능!", "nickname", nickname);
	}
	@RequestMapping("/usr/member/getEmailDup")
	@ResponseBody
	public ResultData getEmailDup(String email) {
		
		if (Ut.empty(email)) {
			return ResultData.from("F-N", "이메일을 입력해주세요");
		}
		
		Member existsMember = memberService.getMemberByEmail(email);
		
		if (existsMember != null) {
			return ResultData.from("F-1", "해당 이메일은 이미 사용중인 닉네임입니다", "email", email);
		}
		
		return ResultData.from("S-1", "사용 가능!", "email", email);
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
	
	
	@RequestMapping("/usr/member/findLoginId")
	public String showFindLoginId() {

		return "usr/member/findLoginId";
	}
	
	@RequestMapping("/usr/member/findLoginPw")
	public String showFindLoginPw() {
		
		return "usr/member/findLoginPw";
	}

	@RequestMapping("/usr/member/doFindLoginId")
	@ResponseBody
	public String doFindLoginId(@RequestParam(defaultValue = "/") String afterFindLoginIdUri, String name,
			String email) {
		
		Member member = memberService.getMemberByNameAndEmail(name, email);
		
		if (member == null) {
			return Ut.jsHistoryBack("F-1", "너는 없는 사람이야");
		}
		
		return Ut.jsReplace("S-1", Ut.f("너의 아이디는 [ %s ] 야", member.getLoginId()), afterFindLoginIdUri);
	}
	


}