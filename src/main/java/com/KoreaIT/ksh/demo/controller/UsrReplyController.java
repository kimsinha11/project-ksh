package com.KoreaIT.ksh.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.ksh.demo.service.BoardService;
import com.KoreaIT.ksh.demo.service.ReactionPointService;
import com.KoreaIT.ksh.demo.service.ReplyService;
import com.KoreaIT.ksh.demo.util.Ut;
import com.KoreaIT.ksh.demo.vo.Board;
import com.KoreaIT.ksh.demo.vo.Reply;
import com.KoreaIT.ksh.demo.vo.ResultData;
import com.KoreaIT.ksh.demo.vo.Rq;

@Controller
public class UsrReplyController {
	@Autowired
	private ReplyService replyService;
	@Autowired
	private BoardService boardService;
	@Autowired
	private Rq rq;
	@Autowired
	private ReactionPointService reactionPointService;

	@RequestMapping("/usr/reply/modify")

	public String modify(Model model, int id) {

		Reply reply = replyService.getReply(id);

		if (reply == null) {
			return rq.jsHistoryBackOnView(Ut.f("%d번 댓글은 존재하지 않습니다", id));
		}
		if (reply.getMemberId() == rq.getLoginedMemberId()) {

			model.addAttribute("reply", reply);
			return "usr/reply/modify";
		} else {
			return rq.jsHistoryBackOnView(Ut.f("권한이없습니다."));
		}

	}


	@RequestMapping("/usr/reply/doModify")
	@ResponseBody
	public String doModify(int id,String body, int relId) {

		Reply reply = replyService.getReply(id);

		if (reply == null) {
			return Ut.jsHistoryBack("F-D", id + "번 글은 존재하지 않습니다.");
		}
		if (reply.getMemberId() == rq.getLoginedMemberId()) {
			replyService.modifyReply(id, body);
			return Ut.jsReplace("S-1", "수정완료", Ut.f("../article/detail?id=%d", relId));
		} else {
			return rq.jsHistoryBackOnView(Ut.f("권한이없습니다."));
		}

	}

	@RequestMapping("/usr/reply/doWrite")
	@ResponseBody
	public String doWrite(HttpSession httpSession, String body, int relId, int boardId) {

		if (Ut.empty(body)) {
			return Ut.jsHistoryBack("F-A", "내용을 입력해주세요");
		}

		Board board = BoardService.getBoardById(boardId);
		ResultData<Integer> writeReplyRd = replyService.writeReply(body, rq.getLoginedMemberId(), relId,
				boardId);
		int id = (int) writeReplyRd.getData1();
		return Ut.jsReplace("S-1", "작성완료", Ut.f("../article/detail?id=%d", relId));

	}

	@RequestMapping("/usr/reply/delete")
	@ResponseBody
	public String doDelete(Model model, int id, int relId) {

		Reply reply = replyService.getReply(id);
		if (reply == null) {
			return Ut.jsHistoryBack("F-D", id + "번 댓글은 존재하지 않습니다.");
		}

		if (reply.getMemberId() == rq.getLoginedMemberId()) {
			replyService.deleteReply(id);
			model.addAttribute("reply", reply);
			return Ut.jsReplace("S-1", "삭제완료", Ut.f("../article/detail?id=%d", relId));
		} else {
			return Ut.jsHistoryBack("F-C", "권한이 없습니다.");
		}
	}
}
