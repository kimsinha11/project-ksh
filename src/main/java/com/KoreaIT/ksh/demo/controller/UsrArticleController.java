package com.KoreaIT.ksh.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.ksh.demo.service.ArticleService;
import com.KoreaIT.ksh.demo.vo.Article;

@Controller
public class UsrArticleController {
	@Autowired
	private ArticleService articleService;
	
	@RequestMapping("/usr/article/write")
	@ResponseBody
	public Article doWrite(String title, String body) {
		Article article = articleService.writeArticle(title, body);
		return article;
	}
	

	@RequestMapping("/usr/article/list")
	@ResponseBody
	public List<Article> showList() {
		return articleService.articles();
	}
	
	@RequestMapping("/usr/article/detail")
	@ResponseBody
	public Object showDetail(int id) {
		Article article = articleService.getArticle(id);
		if(article == null) {
			return id+"번 글은 존재하지 않습니다";
		}
		return article;
	}
	
	@RequestMapping("/usr/article/modify")
	@ResponseBody
	public Object doModify(int id, String title, String body) {
		Article article = articleService.getArticle(id);
		if(article == null) {
			return id + "번 글은 존재하지 않습니다";
		}
		articleService.modifyArticle(id, title, body);
		return article;
	}
	
	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(int id) {
		Article article = articleService.getArticle(id);
		if(article == null) {
			return id + "번 글은 존재하지 않습니다";
		}
		articleService.deleteArticle(id);
		return id + "번 글을 삭제했습니다";
	}
}