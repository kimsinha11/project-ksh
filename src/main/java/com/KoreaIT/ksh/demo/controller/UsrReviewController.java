package com.KoreaIT.ksh.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.ksh.demo.service.BoardService;
import com.KoreaIT.ksh.demo.service.CommentService;
import com.KoreaIT.ksh.demo.service.ReactionPointService;
import com.KoreaIT.ksh.demo.service.ReviewService;
import com.KoreaIT.ksh.demo.util.Ut;
import com.KoreaIT.ksh.demo.vo.Article;
import com.KoreaIT.ksh.demo.vo.Board;
import com.KoreaIT.ksh.demo.vo.Review;
import com.KoreaIT.ksh.demo.vo.Rq;

@Controller
public class UsrReviewController {

	@Autowired
	private ReviewService reviewService;
	@Autowired
	private BoardService boardService;
	@Autowired
	private Rq rq;
	@Autowired
	private ReactionPointService reactionPointService;
	@Autowired
	private CommentService commentService;


	@RequestMapping("/usr/review/delete")
	@ResponseBody
	public String doDelete(Model model, int id, int boardId) {

		Review review = reviewService.getReview(id);
		if (review == null) {
			return Ut.jsHistoryBack("F-D", id + "번 글은 존재하지 않습니다.");
		}

		if (review.getMemberId() == rq.getLoginedMemberId()) {
			reviewService.deleteReview(id);
			model.addAttribute("review", review);
			return Ut.jsReplace("S-1", "삭제완료", Ut.f("../review/list?boardId=%d", boardId));
		} else {
			return Ut.jsHistoryBack("F-C", "권한이 없습니다.");
		}
	}

	@RequestMapping("/usr/review/list")
	public String showList(Model model, @RequestParam(defaultValue = "0") Integer boardId, @RequestParam(defaultValue = "1") int pageNum,
			@RequestParam(defaultValue = "10") int itemsPerPage, String searchKeyword, Integer searchId) {

		Board board = BoardService.getBoardById(boardId);

		if (boardId != 0 && board == null) {
			return rq.jsHistoryBackOnView("그런 게시판은 없어");
		}
		int totalCount = reviewService.getReviewsCount(boardId, searchId, searchKeyword);
		int totalPages = (int) Math.ceil((double) totalCount / itemsPerPage);
		int lastPageInGroup = (int) Math.min(((pageNum - 1) / 10 * 10 + 10), totalPages);
		int itemsInAPage = (pageNum - 1) * itemsPerPage;
		List<Review> reviews = reviewService.getReviews(boardId, itemsInAPage, itemsPerPage, searchKeyword,
				searchId);
	

		model.addAttribute("board", board);
		model.addAttribute("reviews", reviews);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("itemsPerPage", itemsPerPage);
		model.addAttribute("lastPageInGroup", lastPageInGroup);

		return "usr/review/list";
	}
	
	@RequestMapping("/usr/review/detail")
	public String getReview(Model model, int id, int boardId) {

		Review review = reviewService.getReview(id);

		model.addAttribute("review", review);
		model.addAttribute("loginedMemberId", rq.getLoginedMemberId());
		
			Board board = BoardService.getBoardById(boardId);
			
			model.addAttribute(board);
			
		return "usr/review/detail";
	}

}