package com.KoreaIT.ksh.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KoreaIT.ksh.demo.repository.ReplyRepository;
import com.KoreaIT.ksh.demo.util.Ut;
import com.KoreaIT.ksh.demo.vo.Reply;
import com.KoreaIT.ksh.demo.vo.ResultData;

@Service
public class ReplyService {
	@Autowired
	private ReplyRepository replyRepository;

	public ReplyService(ReplyRepository replyRepository) {
		this.replyRepository = replyRepository;
	}

	public Reply getReply(int id) {
		return replyRepository.getReply(id);
	}

	public ResultData modifyReply(int id, String body) {
		replyRepository.modifyReply(id, body);
		Reply reply = getReply(id);
		return ResultData.from("S-1", Ut.f("%d번 댓글을 수정했습니다",id),reply);
	}

	public ResultData<Integer> writeReply(String body, int memberId, int relId, int boardId) {
		replyRepository.writeReply(body, memberId, relId, boardId);
		int id = replyRepository.getLastInsertId();
		return ResultData.from("S-1",  Ut.f("%d번 댓글 작성 완료", id), id);
	}

	public void deleteReply(int id) {
		replyRepository.deleteReply(id);
	}

	public List<Reply> getReplys(int id) {
		return replyRepository.getReplys(id);
	}

	public ResultData increaseGoodReactionPoint(int relId) {
		int affectedRow = replyRepository.increaseGoodReactionPoint(relId);

		if (affectedRow == 0) {
			return ResultData.from("F-1", "게시글 정보가 없습니다.",affectedRow);
		}
		return ResultData.from("S-1", "좋아요 증가", affectedRow);
	}

	public ResultData increaseBadReactionPoint(int relId) {
		int affectedRow = replyRepository.increaseBadReactionPoint(relId);

		if (affectedRow == 0) {
			return ResultData.from("F-1", "게시글 정보가 없습니다.", affectedRow);
		}
		return ResultData.from("S-1", "싫어요 증가", affectedRow);
	}

	public ResultData decreaseGoodReactionPoint(int relId) {
		int affectedRow = replyRepository.decreaseGoodReactionPoint(relId);

		if (affectedRow == 0) {
			return ResultData.from("F-1", "해당 게시글은 존재하지 않습니다.", affectedRow);
		}
		return ResultData.from("S-1", "좋아요 감소", affectedRow);
	}

	public ResultData decreaseBadReactionPoint(int relId) {
		int affectedRow = replyRepository.decreaseBadReactionPoint(relId);

		if (affectedRow == 0) {
			return ResultData.from("F-1", "해당 게시글은 존재하지 않습니다.", affectedRow);
		}
		return ResultData.from("S-1", "싫어요 감소", affectedRow);

	}
}
