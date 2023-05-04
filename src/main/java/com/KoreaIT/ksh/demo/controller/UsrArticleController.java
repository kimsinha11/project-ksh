package com.KoreaIT.ksh.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.ksh.demo.service.ArticleService;
import com.KoreaIT.ksh.demo.util.Ut;
import com.KoreaIT.ksh.demo.vo.Article;
import com.KoreaIT.ksh.demo.vo.ResultData;

@Controller
public class UsrArticleController {
	@Autowired
	private ArticleService articleService;
	
	@RequestMapping("/usr/article/doWrite")
	@ResponseBody
	public ResultData doWrite(String title, String body) {
		if(Ut.empty(title)) {
			return ResultData.from("F-1", Ut.f("제목을 입력해주세요"));
		}
		ResultData writeArticleRd = articleService.writeArticle(title, body);
		int id = (int) writeArticleRd.getData1();
		Article article = articleService.getArticle(id);
		return ResultData.from(writeArticleRd.getResultCode(), writeArticleRd.getMsg(), article);
	}
	

	@RequestMapping("/usr/article/list")
	@ResponseBody
	public ResultData showList() {
		ResultData getArticlesRd = articleService.getArticles();
		return ResultData.from(getArticlesRd.getResultCode(), getArticlesRd.getMsg(), getArticlesRd.getData1());
	}
	
	@RequestMapping("/usr/article/detail")
	@ResponseBody
	public ResultData showDetail(int id) {
		Article article = articleService.getArticle(id);
		if(article == null) {
			return ResultData.from("F-1", Ut.f("%d번 게시물은 존재하지 않습니다.", id));
		}
		
		return ResultData.from("S-1", Ut.f("%d번 게시물입니다.", id), article);
	}
	
	@RequestMapping("/usr/article/modify")
	@ResponseBody
	public ResultData doModify(int id, String title, String body) {
		Article article = articleService.getArticle(id);
		if(article == null) {
			return ResultData.from("F-1", Ut.f("%d번 글은 존재하지 않습니다", id), id);
		}
		articleService.modifyArticle(id, title, body);
		return ResultData.from("S-1", Ut.f("%d번 글을 수정 했습니다", id), id);
	}
	
	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public ResultData doDelete(int id) {
		Article article = articleService.getArticle(id);
		if(article == null) {
			return ResultData.from("F-1", Ut.f("%d번 글은 존재하지 않습니다", id), id);
		}
		articleService.deleteArticle(id);
		return ResultData.from("S-1", Ut.f("%d번 글을 삭제 했습니다", id), id);
	}
}