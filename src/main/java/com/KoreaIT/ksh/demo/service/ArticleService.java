package com.KoreaIT.ksh.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KoreaIT.ksh.demo.repository.ArticleRepository;
import com.KoreaIT.ksh.demo.vo.Article;

@Service
public class ArticleService {

	@Autowired
	private ArticleRepository articleRepository;
	
	public ArticleService(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}


	// 서비스 메서드
	public Article writeArticle(String title, String body) {
		return articleRepository.writeArticle(title, body);
	}

	public void deleteArticle(int id) {
		articleRepository.deleteArticle(id);
	}

	public void modifyArticle(int id, String title, String body) {
		articleRepository.modifyArticle(id, title, body);
	}

	public Article getArticle(int id) {
		return articleRepository.getArticle(id);
	}


	public List<Article> getArticles() {
		return articleRepository.getArticles();
	}
}