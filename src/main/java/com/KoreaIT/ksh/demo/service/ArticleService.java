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


	// 글 작성 메서드
	public ResultData<Integer> writeArticle(String title, String body, int memberId, int boardId) {
		// 글을 저장하고 생성된 글 번호를 반환
		articleRepository.writeArticle(title, body, memberId, boardId);

		int id = articleRepository.getLastInsertId();

		// 결과 데이터 객체 생성하여 반환
		return ResultData.from("S-1", Ut.f("%d번 글이 생성되었습니다", id), "id", id);
	}

	// 글 번호로 글 조회
	public Article getArticle(int id) {
		return articleRepository.getArticle(id);
	}

	// 글 삭제
	public void deleteArticle(int id) {
		articleRepository.deleteArticle(id);
	}

	// 글 수정
	public ResultData modifyArticle(int id, String title, String body) {
		articleRepository.modifyArticle(id, title, body);

		// 수정된 글 정보 조회
		Article article = getArticle(id);

		return ResultData.from("S-1", Ut.f("%d번 글을 수정 했습니다", id), "article", article);
	}

	// 게시판의 글 목록 조회
	public List<Article> getArticles(int boardId, int i, int itemsPerPage, String searchKeyword, Integer searchId) {
		return articleRepository.getArticles(boardId, i, itemsPerPage, searchKeyword, searchId);
	}

	// 글 수정 권한 확인
	public ResultData actorCanModify(int loginedMemberId, Article article) {
		if (article.getMemberId() != loginedMemberId) {
			return ResultData.from("F-2", Ut.f("해당 글에 대한 권한이 없습니다"));
		}
		return ResultData.from("S-1", "수정 가능");
	}

	// 글 개수 조회
	public int getArticlesCount(Integer boardId, Integer searchId, String searchKeyword) {
		return articleRepository.getArticlesCount(boardId, searchId, searchKeyword);
	}

	// 조회수 증가
	public ResultData increaseHitCount(int id) {
		int affectedRow = articleRepository.increaseHitCount(id);

		if (affectedRow == 0) {
			return ResultData.from("F-1", "해당 게시물은 없음", "affectedRow", affectedRow);
		}

		return ResultData.from("S-1", "조회수 증가", "affectedRowRd", affectedRow);
	}

	// 글의 조회수 조회
	public int getArticleHitCount(int id) {
		return articleRepository.getArticleHitCount(id);
	}

	// 좋아요 수 증가
	public ResultData increaseGoodReactionPoint(int relId) {
		int affectedRow = articleRepository.increaseGoodReactionPoint(relId);

		if (affectedRow == 0) {
			return ResultData.from("F-1", "게시글 정보가 없습니다.", "affectedRow", affectedRow);
		}
		return ResultData.from("S-1", "좋아요 증가", "affectedRow", affectedRow);
	}

	// 싫어요 수 증가
	public ResultData increaseBadReactionPoint(int relId) {
		int affectedRow = articleRepository.increaseBadReactionPoint(relId);

		if (affectedRow == 0) {
			return ResultData.from("F-1", "게시글 정보가 없습니다.", "affectedRow", affectedRow);
		}
		return ResultData.from("S-1", "싫어요 증가", "affectedRow", affectedRow);
	}

	// 좋아요 수 감소
	public ResultData decreaseGoodReactionPoint(int relId) {
		int affectedRow = articleRepository.decreaseGoodReactionPoint(relId);

		if (affectedRow == 0) {
			return ResultData.from("F-1", "해당 게시글은 존재하지 않습니다.", "affectedRow", affectedRow);
		}
		return ResultData.from("S-1", "좋아요 감소", "affectedRow", affectedRow);
	}

	// 싫어요 수 감소
	public ResultData decreaseBadReactionPoint(int relId) {
		int affectedRow = articleRepository.decreaseBadReactionPoint(relId);

		if (affectedRow == 0) {
			return ResultData.from("F-1", "해당 게시글은 존재하지 않습니다.", "affectedRow", affectedRow);
		}
		return ResultData.from("S-1", "싫어요 감소", "affectedRow", affectedRow);
	}

	// 댓글 수 조회
	public List<Article> getCommentsCount() {
		return articleRepository.getCommentsCount();
	}


	// 여러 글 삭제
	public void deleteArticles(List<Integer> articleIds) {
		for (int articleId : articleIds) {
			Article article = getArticle(articleId);

			if (article != null) {
				deleteArticle(article);
			}
		}
	}

	private void deleteArticle(Article article) {
		articleRepository.deleteArticle(article.getId());
	}

	// 개인이 쓴 글 삭제
	public void deletemyArticles(List<Integer> articleIds) {
		for (int articleId : articleIds) {
			Article article = getArticle(articleId);
			
			if(article != null) {
				deleteArticle(article);
			}
		}
		
	}

	// 개인이 작성한 글 목록 조회
	public List<Article> getMyArticles(Integer boardId, int memberId, int i, int itemsPerPage,
			String searchKeyword, Integer searchId) {
		return articleRepository.getMyArticles(boardId,memberId, i, itemsPerPage, searchKeyword, searchId);
	}

	// 개인이 좋아요 한 글 목록 조회
	public List<Article> getMylikes(Integer boardId, int memberId, int i, int itemsPerPage,
			String searchKeyword, Integer searchId) {
		return articleRepository.getMylikes(boardId, memberId, i, itemsPerPage, searchKeyword, searchId);
	}

}