package com.KoreaIT.ksh.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.KoreaIT.ksh.demo.vo.Article;

@Mapper
public interface ArticleRepository {

	// 서비스 메서드
	public void writeArticle(String title, String body, int memberId);

	public void deleteArticle(int id);
	
	public void modifyArticle(int id, String title, String body);

	public Article getArticle(int id);

	public List<Article> getArticles();

	public int getLastInsertId();
}