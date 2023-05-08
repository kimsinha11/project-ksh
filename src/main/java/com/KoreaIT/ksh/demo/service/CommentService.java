package com.KoreaIT.ksh.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KoreaIT.ksh.demo.repository.ArticleRepository;
import com.KoreaIT.ksh.demo.repository.CommentRepository;
import com.KoreaIT.ksh.demo.util.Ut;
import com.KoreaIT.ksh.demo.vo.Article;
import com.KoreaIT.ksh.demo.vo.Comment;
import com.KoreaIT.ksh.demo.vo.ResultData;

@Service
public class CommentService {
	@Autowired
	private CommentRepository commentRepository;

	public CommentService(CommentRepository commentRepository) {
		this.commentRepository = commentRepository;
	}

	public Comment getComment(int id) {
		return commentRepository.getComment(id);
	}

	public ResultData cmodifyComment(int id, String body) {
		commentRepository.cmodifyComment(id, body);

		Comment comment = getComment(id);

		return ResultData.from("S-1", Ut.f("%d번 댓글을 수정 했습니다", id), "comment", comment);
		
	}

	public ResultData<Integer> cwriteComment(String body, int memberId, int relId, int boardId) {
		commentRepository.cwriteComment(body, memberId, relId, boardId);

		int id = commentRepository.getLastInsertId();

		return ResultData.from("S-1", Ut.f("%d번 댓글이 생성되었습니다", id), "id", id);

	}

	public void cdeleteComment(int id) {
		commentRepository.cdeleteComment(id);
		
	}

	public List<Comment> getComments(int id) {
		return commentRepository.getComments(id);
	}
	
	public ResultData increaseGoodReactionPoint(int relId) {
		int affectedRow = commentRepository.increaseGoodReactionPoint(relId);

		if (affectedRow == 0) {
			return ResultData.from("F-1", "게시글 정보가 없습니다.", "affectedRow", affectedRow);
		}
		return ResultData.from("S-1", "좋아요 증가", "affectedRow", affectedRow);
	}

	public ResultData increaseBadReactionPoint(int relId) {
		int affectedRow = commentRepository.increaseBadReactionPoint(relId);

		if (affectedRow == 0) {
			return ResultData.from("F-1", "게시글 정보가 없습니다.", "affectedRow", affectedRow);
		}
		return ResultData.from("S-1", "싫어요 증가", "affectedRow", affectedRow);
	}

	public ResultData decreaseGoodReactionPoint(int relId) {
		int affectedRow = commentRepository.decreaseGoodReactionPoint(relId);

		if (affectedRow == 0) {
			return ResultData.from("F-1", "해당 게시글은 존재하지 않습니다.", "affectedRow", affectedRow);
		}
		return ResultData.from("S-1", "좋아요 감소", "affectedRow", affectedRow);
	}

	public ResultData decreaseBadReactionPoint(int relId) {
		int affectedRow = commentRepository.decreaseBadReactionPoint(relId);

		if (affectedRow == 0) {
			return ResultData.from("F-1", "해당 게시글은 존재하지 않습니다.", "affectedRow", affectedRow);
		}
		return ResultData.from("S-1", "싫어요 감소", "affectedRow", affectedRow);

	}

}
