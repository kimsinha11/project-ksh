package com.KoreaIT.ksh.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.ksh.demo.service.ArticleService;
import com.KoreaIT.ksh.demo.service.BoardService;
import com.KoreaIT.ksh.demo.util.Ut;
import com.KoreaIT.ksh.demo.vo.Article;
import com.KoreaIT.ksh.demo.vo.Board;
import com.KoreaIT.ksh.demo.vo.ResultData;
import com.KoreaIT.ksh.demo.vo.Rq;

@Controller
public class UsrArticleController {
	@Autowired
	private ArticleService articleService;
	@Autowired
	private BoardService boardService;
	@Autowired
	private Rq rq;
	//F-A 로그인 오류 F-B 로그아웃 오류 F-N 빈 값 오류 F-F 없거나 불일치 오류 F-C 권한오류
	
	
	@RequestMapping("/usr/article/write")
	public String write(Model model, String title, String body) {

		return "usr/article/write";
	}
	
	@RequestMapping("/usr/article/doWrite")
	@ResponseBody
	public String doWrite(HttpSession httpSession, String title, String body, int boardId) {
		
		if (Ut.empty(title)) {
			return Ut.jsHistoryBack("F-A", "제목을 입력해주세요.");
		}
		if (Ut.empty(body)) {
			return Ut.jsHistoryBack("F-A", "내용을 입력해주세요");
		}
		
		Board board = BoardService.getBoardById(boardId);
		ResultData writeArticleRd = articleService.writeArticle(title, body, rq.getLoginedMemberId(), boardId);
		int id = (int) writeArticleRd.getData1();
		
		return Ut.jsReplace("S-1", "작성완료", Ut.f("../article/detail?id=%d",id));
		
	}
	
	@RequestMapping("/usr/article/modify")
	public String modify(Model model, int id, String title, String body) {
	
		Article article = articleService.getArticle(id);
		
		if(article == null) {
			return rq.jsHistoryBackOnView(Ut.f("%d번 글은 존재하지 않습니다",id));
		}
		if (article.getMemberId() == rq.getLoginedMemberId()) {

			model.addAttribute("article", article);
			return "usr/article/modify";
		} else {
			return rq.jsHistoryBackOnView(Ut.f("권한이없습니다."));
		}

	}

	
	
	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public String doModify( int id, String title, String body) {
		
		Article article = articleService.getArticle(id);
		
		if(article == null) {
			return Ut.jsHistoryBack("F-F", id + "번 글은 존재하지 않습니다.");
		}
		articleService.modifyArticle(id, title, body);

		return Ut.jsReplace("S-1", "수정되었습니다", "list");

	}

	@RequestMapping("/usr/article/delete")
	@ResponseBody
	public String doDelete(Model model, int id) {


		Article article = articleService.getArticle(id);
		if (article == null) {
			return Ut.jsHistoryBack("F-F", id + "번 글은 존재하지 않습니다.");
		}

		if (article.getMemberId() == rq.getLoginedMemberId()) {
			articleService.deleteArticle(id);
			model.addAttribute("article", article);
			return Ut.jsReplace("S-1", "삭제완료", "list");
		} else {
			return Ut.jsHistoryBack("F-C", "권한이 없습니다.");
		}
	}

	@RequestMapping("/usr/article/list")
	public String showList(Model model, Integer boardId, @RequestParam(defaultValue = "1") int pageNum,
			@RequestParam(defaultValue = "10") int itemsPerPage, String searchKeyword, Integer searchId) {

		if (boardId == null) {
			boardId = 1;
		}
		Board board = BoardService.getBoardById(boardId);

		if (board == null) {
			return rq.jsHistoryBackOnView("그런 게시판은 없어");
		}

		int totalCount = articleService.getArticlesCount(boardId);
		int totalPages = (int) Math.ceil((double) totalCount / itemsPerPage);
		int lastPageInGroup = (int) Math.min(((pageNum - 1) / 10 * 10 + 10), totalPages);
		int itemsInAPage = (pageNum - 1) * itemsPerPage;
		List<Article> articles = articleService.getArticles(boardId, itemsInAPage, itemsPerPage);
			
		model.addAttribute("board", board);

		model.addAttribute("articles", articles);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("itemsPerPage", itemsPerPage);
		model.addAttribute("lastPageInGroup", lastPageInGroup);

		return "usr/article/list";
	}

	@RequestMapping("/usr/article/detail")
	public String getArticle(Model model, int id) {
	
		Article article = articleService.getArticle(id);

		if (article == null) {
			return rq.jsHistoryBackOnView(Ut.f("%d번 글은 존재하지 않습니다",id));
		}

		model.addAttribute("article", article);
		model.addAttribute("loginedMemberId", rq.getLoginedMemberId());

		return "usr/article/detail";
	}
}
