package com.KoreaIT.ksh.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KoreaIT.ksh.demo.repository.CommentRepository;
import com.KoreaIT.ksh.demo.util.Ut;
import com.KoreaIT.ksh.demo.vo.Comment;
import com.KoreaIT.ksh.demo.vo.ResultData;

@Service
public class CommentService {
	
	@Autowired
	private CommentRepository commentRepository;
	
	public CommentService(CommentRepository commentRepository) {
		this.commentRepository = commentRepository;
	}
	
	// 댓글 번호로 댓글 조회
	public Comment getComment(int id) {
		return commentRepository.getComment(id);
	}
	
	// 댓글 수정
	public ResultData cmodifyComment(int id, String body) {
		commentRepository.cmodifyComment(id, body);
		
		// 수정된 댓글 정보 조회
		Comment comment = getComment(id);
		
		return ResultData.from("S-1", Ut.f("%d번 댓글을 수정했습니다", id), "comment", comment);
	}
	
	// 댓글 작성
	public ResultData<Integer> cwriteComment(String body, int memberId, int relId, int boardId) {
		commentRepository.cwriteComment(body, memberId, relId, boardId);
		
		int id = commentRepository.getLastInsertId();
		
		return ResultData.from("S-1", Ut.f("%d번 댓글이 생성되었습니다", id), "id", id);
	}
	
	// 댓글 삭제
	public void cdeleteComment(int id) {
		commentRepository.cdeleteComment(id);
	}
	
	// 해당 게시글의 댓글 목록 조회
	public List<Comment> getComments(int id) {
		return commentRepository.getComments(id);
	}
	
	// 좋아요 수 증가
	public ResultData increaseGoodReactionPoint(int relId) {
		int affectedRow = commentRepository.increaseGoodReactionPoint(relId);
		
		if (affectedRow == 0) {
			return ResultData.from("F-1", "게시글 정보가 없습니다.", "affectedRow", affectedRow);
		}
		return ResultData.from("S-1", "좋아요 증가", "affectedRow", affectedRow);
	}
	
	// 싫어요 수 증가
	public ResultData increaseBadReactionPoint(int relId) {
		int affectedRow = commentRepository.increaseBadReactionPoint(relId);
		
		if (affectedRow == 0) {
			return ResultData.from("F-1", "게시글 정보가 없습니다.", "affectedRow", affectedRow);
		}
		return ResultData.from("S-1", "싫어요 증가", "affectedRow", affectedRow);
	}
	
	// 좋아요 수 감소
	public ResultData decreaseGoodReactionPoint(int relId) {
		int affectedRow = commentRepository.decreaseGoodReactionPoint(relId);
		
		if (affectedRow == 0) {
			return ResultData.from("F-1", "해당 게시글은 존재하지 않습니다.", "affectedRow", affectedRow);
		}
		return ResultData.from("S-1", "좋아요 감소", "affectedRow", affectedRow);
	}
	
	// 싫어요 수 감소
	public ResultData decreaseBadReactionPoint(int relId) {
		int affectedRow = commentRepository.decreaseBadReactionPoint(relId);
		
		if (affectedRow == 0) {
			return ResultData.from("F-1", "해당 게시글은 존재하지 않습니다.", "affectedRow", affectedRow);
		}
		return ResultData.from("S-1", "싫어요 감소", "affectedRow", affectedRow);
	}
}
