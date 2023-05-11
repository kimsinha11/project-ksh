package com.KoreaIT.ksh.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.ksh.demo.service.ArticleService;
import com.KoreaIT.ksh.demo.service.BoardService;
import com.KoreaIT.ksh.demo.service.CommentService;
import com.KoreaIT.ksh.demo.service.MemberService;
import com.KoreaIT.ksh.demo.util.Ut;
import com.KoreaIT.ksh.demo.vo.Article;
import com.KoreaIT.ksh.demo.vo.Board;
import com.KoreaIT.ksh.demo.vo.Comment;
import com.KoreaIT.ksh.demo.vo.Member;
import com.KoreaIT.ksh.demo.vo.ResultData;
import com.KoreaIT.ksh.demo.vo.Rq;

@Controller
public class AdmMemberController {

	@Autowired
	private MemberService memberService;
	@Autowired
	private ArticleService articleService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private Rq rq;

	@RequestMapping("/adm/memberAndArticle/list")
	public String showList(Model model, @RequestParam(defaultValue = "0") String authLevel,
			@RequestParam(defaultValue = "loginId,name,nickname") String searchKeywordTypeCode,
			@RequestParam(defaultValue = "") String searchKeyword, @RequestParam(defaultValue = "1") int page) {

		int membersCount = memberService.getMembersCount(authLevel, searchKeywordTypeCode, searchKeyword);

		int itemsInAPage = 10;
		int pagesCount = (int) Math.ceil((double) membersCount / itemsInAPage);

		List<Member> members = memberService.getForPrintMembers(authLevel, searchKeywordTypeCode, searchKeyword,
				itemsInAPage, page);

		model.addAttribute("authLevel", authLevel);
		model.addAttribute("searchKeywordTypeCode", searchKeywordTypeCode);
		model.addAttribute("searchKeyword", searchKeyword);

		model.addAttribute("membersCount", membersCount);
		model.addAttribute("pagesCount", pagesCount);

		model.addAttribute("members", members);

		return "adm/memberAndArticle/memberlist";
	}
	@RequestMapping("/adm/article/list")
	public String showList(Model model, @RequestParam(defaultValue = "0") Integer boardId, @RequestParam(defaultValue = "1") int pageNum,
			@RequestParam(defaultValue = "10") int itemsPerPage, String searchKeyword, Integer searchId) {

		Board board = BoardService.getBoardById(boardId);

		if (boardId != 0 && board == null) {
			return rq.jsHistoryBackOnView("그런 게시판은 없어");
		}
		int totalCount = articleService.getArticlesCount(boardId, searchId, searchKeyword);
		int totalPages = (int) Math.ceil((double) totalCount / itemsPerPage);
		int lastPageInGroup = (int) Math.min(((pageNum - 1) / 10 * 10 + 10), totalPages);
		int itemsInAPage = (pageNum - 1) * itemsPerPage;
		List<Article> articles = articleService.getArticles(boardId, itemsInAPage, itemsPerPage, searchKeyword,
				searchId);
		List<Article> commentsCount = articleService.getCommentsCount();
		
		model.addAttribute("commentsCount", commentsCount);
		model.addAttribute("board", board);
		model.addAttribute("articles", articles);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("itemsPerPage", itemsPerPage);
		model.addAttribute("lastPageInGroup", lastPageInGroup);

		return "adm/memberAndArticle/articlelist";
	}
	
	@RequestMapping("/adm/article/detail")
	public String getArticle(Model model, int id, int boardId) {

		Article article = articleService.getArticle(id);
	
		model.addAttribute("article", article);
		model.addAttribute("loginedMemberId", rq.getLoginedMemberId());

			List<Comment> comments = commentService.getComments(id);

			model.addAttribute("comments", comments);

			Board board = BoardService.getBoardById(boardId);
			
			model.addAttribute(board);
			
		return "adm/memberAndArticle/articledetail";
	}
	
	@RequestMapping("/adm/article/delete")
	@ResponseBody
	public String doDelete(Model model, int id, int boardId) {

		Article article = articleService.getArticle(id);
		if (article == null) {
			return Ut.jsHistoryBack("F-D", id + "번 글은 존재하지 않습니다.");
		}

		if (rq.isAdmin()) {
			articleService.deleteArticle(id);
			model.addAttribute("article", article);
			return Ut.jsReplace("S-1", "삭제완료", Ut.f("../article/list?boardId=%d", boardId));
		} else {
			return Ut.jsHistoryBack("F-C", "권한이 없습니다.");
		}
	}


	@RequestMapping("/adm/member/doLogout")
	@ResponseBody
	public String doLogout(@RequestParam(defaultValue = "/") String afterLogoutUri) {

		rq.logout();

		return Ut.jsReplace("S-1", "로그아웃 되었습니다", afterLogoutUri);
	}
}