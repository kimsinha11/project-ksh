package com.KoreaIT.ksh.demo.controller;

import java.util.ArrayList;
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
		
		// 멤버 수를 파라미터에 따라 조회하여 가져온다.
		int membersCount = memberService.getMembersCount(authLevel, searchKeywordTypeCode, searchKeyword);

		int itemsInAPage = 10;
		
		// 페이지 수를 계산한다.
		int pagesCount = (int) Math.ceil((double) membersCount / itemsInAPage);

		// 멤버 리스트를 파라미터, 페이지 번호, 페이지 당 아이템 수에 따라 가져온다.
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
		
		// 게시물 수를 파라미터에 따라 조회하여 가져온다.
		int totalCount = articleService.getArticlesCount(boardId, searchId, searchKeyword);
		// 전체 페이지 수를 계산한다.
		int totalPages = (int) Math.ceil((double) totalCount / itemsPerPage);
		int lastPageInGroup = (int) Math.min(((pageNum - 1) / 10 * 10 + 10), totalPages);
		int itemsInAPage = (pageNum - 1) * itemsPerPage;
		// 게시물 리스트를 파라미터, 페이지 당 아이템 수, 검색 키워드에 따라 가져온다.
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
		
			// 게시물에 대한 댓글 리스트를 가져온다.
			List<Comment> comments = commentService.getComments(id);

			model.addAttribute("comments", comments);

			Board board = BoardService.getBoardById(boardId);
			
			model.addAttribute(board);
			
		return "adm/memberAndArticle/articledetail";
	}
	
	@RequestMapping("/adm/article/delete")
	@ResponseBody
	public String doDelete(Model model,@RequestParam(defaultValue = "") String ids, @RequestParam(defaultValue = "/adm/memberAndArticle/list") String replaceUri) {

		// 관리자 권한이 있는지 확인한다.
		if (rq.isAdmin()) {
			List<Integer> articleIds = new ArrayList<>();

			for (String idStr : ids.split(",")) {
				articleIds.add(Integer.parseInt(idStr));
			}
			
			// 선택한 게시물들을 삭제한다.
			articleService.deleteArticles(articleIds);
		
			return Ut.jsReplace("해당 게시들이 삭제되었습니다.", replaceUri);
		} else {
			return Ut.jsHistoryBack("F-C", "권한이 없습니다.");
		}
	}

	@RequestMapping("/adm/memberAndArticle/doDeleteMembers")
	@ResponseBody
	public String doDeleteMembers(@RequestParam(defaultValue = "") String ids,
			@RequestParam(defaultValue = "/adm/memberAndArticle/list") String replaceUri) {
		List<Integer> memberIds = new ArrayList<>();

		for (String idStr : ids.split(",")) {
			memberIds.add(Integer.parseInt(idStr));
		}
		
		// 선택한 회원들을 삭제한다.
		memberService.deleteMembers(memberIds);

		return Ut.jsReplace("해당 회원들이 삭제되었습니다.", replaceUri);
	}
	
	@RequestMapping("/adm/member/doLogout")
	@ResponseBody
	public String doLogout(@RequestParam(defaultValue = "/") String afterLogoutUri) {
		
		// 로그아웃을 처리한다.
		rq.logout();

		return Ut.jsReplace("S-1", "로그아웃 되었습니다", afterLogoutUri);
	}
}