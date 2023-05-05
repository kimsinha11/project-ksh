package com.KoreaIT.ksh.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.ksh.demo.service.ArticleService;
import com.KoreaIT.ksh.demo.util.Ut;
import com.KoreaIT.ksh.demo.vo.Article;
import com.KoreaIT.ksh.demo.vo.Rq;

@Controller
public class UsrArticleController {
	@Autowired
	private ArticleService articleService;
	//F-A 로그인 오류 F-B 로그아웃 오류 F-N 빈 값 오류 F-F 없거나 불일치 오류 F-C 권한오류
	
	
	@RequestMapping("/usr/article/write")
	public String write(Model model, String title, String body) {

		return "usr/article/write";
	}
	
	@RequestMapping("/usr/article/doWrite")
	@ResponseBody
	public String doWrite(HttpServletRequest req, HttpSession httpSession, String title, String body) {
	
		Rq rq = (Rq) req.getAttribute("rq");
	
		
		if (Ut.empty(title)) {
			return Ut.jsHistoryBack("F-A", "제목을 입력해주세요.");
		}
		if (Ut.empty(body)) {
			return Ut.jsHistoryBack("F-A", "내용을 입력해주세요");
		}
		articleService.writeArticle(title, body, rq.getLoginedMemberId());
		
		
		return Ut.jsReplace("S-1", "작성완료", "list");
		
	}
	
	@RequestMapping("/usr/article/modify")
	public String modify(Model model, HttpServletRequest req, int id, String title, String body) {
		Rq rq = (Rq) req.getAttribute("rq");
				

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
	public String doModify(HttpServletRequest req, int id, String title, String body) {
		Rq rq = (Rq) req.getAttribute("rq");
		

		Article article = articleService.getArticle(id);
		
		if(article == null) {
			return Ut.jsHistoryBack("F-F", id + "번 글은 존재하지 않습니다.");
		}
		articleService.modifyArticle(id, title, body);

		return Ut.jsReplace("S-1", "수정되었습니다", "list");

	}

	@RequestMapping("/usr/article/delete")
	@ResponseBody
	public String doDelete(Model model, HttpServletRequest req, int id) {

		Rq rq = (Rq) req.getAttribute("rq");

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
	public String showList(Model model) {
		List<Article> articles = articleService.getArticles();

		model.addAttribute("articles", articles);

		return "usr/article/list";
	}

	@RequestMapping("/usr/article/detail")
	public String getArticle(Model model, HttpServletRequest req, int id) {
		
		Rq rq = (Rq) req.getAttribute("rq");
		
		Article article = articleService.getArticle(id);

		if (article == null) {
			return rq.jsHistoryBackOnView(Ut.f("%d번 글은 존재하지 않습니다",id));
		}

		model.addAttribute("article", article);
		model.addAttribute("loginedMemberId", rq.getLoginedMemberId());

		return "usr/article/detail";
	}
}
