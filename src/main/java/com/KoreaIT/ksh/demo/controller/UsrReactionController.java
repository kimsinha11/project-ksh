package com.KoreaIT.ksh.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.ksh.demo.service.ReactionPointService;
import com.KoreaIT.ksh.demo.vo.ResultData;
import com.KoreaIT.ksh.demo.vo.Rq;

@Controller
public class UsrReactionController {

	@Autowired
	private ReactionPointService reactionPointService;
	@Autowired
	private Rq rq;

	// 좋아요(긍정적 반응) 처리
	@RequestMapping("/usr/reactionPoint/doGoodReaction")
	@ResponseBody
	public ResultData doGoodReaction(String relTypeCode, int relId) {
		// 현재 사용자가 해당 리소스에 대해 좋아요 반응을 할 수 있는지 확인
		ResultData actorCanMakeReactionRd = reactionPointService.actorCanMakeReaction(rq.getLoginedMemberId(), relTypeCode, relId);

		int actorCanMakeReaction = (int) actorCanMakeReactionRd.getData1();

		if (actorCanMakeReaction == 1) {
			// 이미 좋아요를 누른 상태이므로 좋아요 취소 처리
			ResultData rd = reactionPointService.deleteGoodReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
			return ResultData.from("S-1", "좋아요 취소");
		} else if (actorCanMakeReaction == -1) {
			// 이미 싫어요를 누른 상태이므로 좋아요를 누를 수 없음
			return ResultData.from("F-1", "싫어요 누른 상태입니다.");
		}

		// 좋아요 반응 처리
		ResultData rd = reactionPointService.addGoodReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);

		if (rd.isFail()) {
			ResultData.from("F-2", rd.getMsg());
		}

		return ResultData.from("S-2", "좋아요");
	}

	// 싫어요(부정적 반응) 처리
	@RequestMapping("/usr/reactionPoint/doBadReaction")
	@ResponseBody
	public ResultData doBadReaction(String relTypeCode, int relId) {
		// 현재 사용자가 해당 리소스에 대해 싫어요 반응을 할 수 있는지 확인
		ResultData actorCanMakeReactionRd = reactionPointService.actorCanMakeReaction(rq.getLoginedMemberId(), relTypeCode, relId);

		int actorCanMakeReaction = (int) actorCanMakeReactionRd.getData1();

		if (actorCanMakeReaction == -1) {
			// 이미 싫어요를 누른 상태이므로 싫어요 취소 처리
			ResultData rd = reactionPointService.deleteBadReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
			return ResultData.from("S-1", "싫어요 취소");
		} else if (actorCanMakeReaction == 1) {
			// 이미 좋아요를 누른 상태이므로 싫어요를 누를 수 없음
			return ResultData.from("F-1", "좋아요 누른 상태입니다.");
		}

		// 싫어요 반응 처리
		ResultData rd = reactionPointService.addBadReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);

		if (rd.isFail()) {
			rq.jsHistoryBack("F-2", rd.getMsg());
		}

		return ResultData.from("S-2", "싫어요");
	}
}
