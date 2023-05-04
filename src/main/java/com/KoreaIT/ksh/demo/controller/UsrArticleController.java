package com.KoreaIT.ksh.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.ksh.demo.vo.Article;

@Controller
public class UsrArticleController {
	int lastArticleId;
	List<Article> articles;
	
	public UsrArticleController() {
		lastArticleId = 0;
		articles = new ArrayList<>();
	}
	
	@RequestMapping("/usr/article/write")
	@ResponseBody
	public Article doWrite(String title, String body) {
		int id = lastArticleId + 1;
		
		Article article = new Article(id, title, body);
		articles.add(article);
		lastArticleId++;
		
		return article;
	}
	
	@RequestMapping("/usr/article/list")
	@ResponseBody
	public List<Article> showList() {
		return articles;
	}
}