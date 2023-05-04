package com.KoreaIT.ksh.demo.controller;

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
}