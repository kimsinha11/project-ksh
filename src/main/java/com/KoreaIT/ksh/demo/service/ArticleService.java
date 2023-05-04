package com.KoreaIT.ksh.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.KoreaIT.ksh.demo.vo.Article;

@Service
public class ArticleService {

	private int lastArticleId;
	private List<Article> articles;
	
	public ArticleService() {
		lastArticleId = 0;
		articles = new ArrayList<>();
	}


	// 서비스 메서드
	public Article writeArticle(String title, String body) {
		int id = lastArticleId + 1;

		Article article = new Article(id, title, body);
		articles.add(article);
		lastArticleId++;

		return article;
	}

	public void deleteArticle(int id) {
		Article article = getArticle(id);
		articles.remove(article);
	}

	public void modifyArticle(int id, String title, String body) {
		Article article = getArticle(id);

		article.setTitle(title);
		article.setBody(body);
	}

	public Article getArticle(int id) {
		for (Article article : articles) {
			if (article.getId() == id) {
				return article;
			}
		}
		return null;
	}


	public List<Article> articles() {
		return articles;
	}
}