package com.KoreaIT.ksh.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KoreaIT.ksh.demo.repository.ArticleRepository;
import com.KoreaIT.ksh.demo.util.Ut;
import com.KoreaIT.ksh.demo.vo.Article;
import com.KoreaIT.ksh.demo.vo.ResultData;

@Service
public class ArticleService {

	@Autowired
	private ArticleRepository articleRepository;
	
	public ArticleService(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}


	// 서비스 메서드
	public ResultData writeArticle(String title, String body) {
		int id = articleRepository.getLastInsertId();
		articleRepository.writeArticle(title, body);
		return ResultData.from("S-1", Ut.f("%d번 글이 생성되었씁니다", id),id);
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


	public ResultData getArticles() {
		return ResultData.from("S-1", Ut.f("게시물 리스트"), articleRepository.getArticles());
	}
}