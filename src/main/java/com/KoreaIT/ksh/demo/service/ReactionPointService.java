package com.KoreaIT.ksh.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KoreaIT.ksh.demo.repository.ReactionPointRepository;
import com.KoreaIT.ksh.demo.vo.ResultData;
import com.KoreaIT.ksh.demo.vo.Rq;

@Service
public class ReactionPointService {
	@Autowired
	private ReactionPointRepository reactionPointRepository;
	@Autowired
	private ArticleService articleService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private Rq rq;

	public ReactionPointService(ReactionPointRepository reactionPointRepository) {
		this.reactionPointRepository = reactionPointRepository;
	}

	// 액터가 리액션 포인트를 추가할 수 있는지 확인하는 메서드
	public ResultData<Integer> actorCanMakeReaction(int actorId, String relTypeCode, int relId) {
		if (actorId == 0) {
			return ResultData.from("F-L", "로그인 후 이용해주세요.");
		}
		// 해당 회원의 리액션 포인트 합계 조회
		int sumReactionPointByMemberId = reactionPointRepository.getSumReactionPointByMemberId(actorId, relTypeCode,
				relId);

		if (sumReactionPointByMemberId != 0) {
			return ResultData.from("F-1", "추천 불가", "sumReactionPointByMemberId", sumReactionPointByMemberId);
		}

		return ResultData.from("S-1", "추천 가능", "sumReactionPointByMemberId", sumReactionPointByMemberId);

	}

	// 좋아요 리액션 포인트 추가
	public ResultData<Integer> addGoodReactionPoint(int actorId, String relTypeCode, int relId) {
		// 리액션 포인트 추가
		int affectedRow = reactionPointRepository.addGoodReactionPoint(actorId, relTypeCode, relId);

		if (affectedRow != 1) {
			return ResultData.from("F-1", "좋아요 실패");
		}

		// 리액션 포인트 추가에 따른 상세 처리
		switch (relTypeCode) {
		case "article":
			articleService.increaseGoodReactionPoint(relId);
			break;
		case "comment":
			commentService.increaseGoodReactionPoint(relId);
			break;
		}

		return ResultData.from("S-1", "좋아요 처리 됨");

	}

	// 싫어요 리액션 포인트 추가
	public ResultData<Integer> addBadReactionPoint(int actorId, String relTypeCode, int relId) {
		// 리액션 포인트 추가
		int affectedRow = reactionPointRepository.addBadReactionPoint(actorId, relTypeCode, relId);

		if (affectedRow != 1) {
			return ResultData.from("F-1", "싫어요 실패");
		}

		// 리액션 포인트 추가에 따른 상세 처리
		switch (relTypeCode) {
		case "article":
			articleService.increaseBadReactionPoint(relId);
			break;
		case "comment":
			commentService.increaseBadReactionPoint(relId);
			break;
		}

		return ResultData.from("S-1", "싫어요 처리 됨");
	}

	// 좋아요 리액션 포인트 삭제
	public ResultData<String> deleteGoodReactionPoint(int actorId, String relTypeCode, int relId) {
		// 리액션 포인트 삭제
		reactionPointRepository.deleteReactionPoint(actorId, relTypeCode, relId);

		// 리액션 포인트 삭제에 따른 상세 처리
		switch (relTypeCode) {
		case "article":
			articleService.decreaseGoodReactionPoint(relId);
			break;
		case "comment":
			commentService.decreaseGoodReactionPoint(relId);
			break;
		}
		return ResultData.from("S-1", "좋아요 취소 됨");
	}

	// 싫어요 리액션 포인트 삭제
	public ResultData<String> deleteBadReactionPoint(int actorId, String relTypeCode, int relId) {
		// 리액션 포인트 삭제
		reactionPointRepository.deleteReactionPoint(actorId, relTypeCode, relId);

		// 리액션 포인트 삭제에 따른 상세 처리
		switch (relTypeCode) {
		case "article":
			articleService.decreaseBadReactionPoint(relId);
			break;
		case "comment":
			commentService.decreaseBadReactionPoint(relId);
		}
		return ResultData.from("S-1", "싫어요 취소 됨");
	}

	// 이미 좋아요 리액션 포인트를 추가한 상태인지 확인하는 메서드
	public boolean isAlreadyAddGoodRp(int relId, String relTypeCode) {
		int getPointTypeCodeByMemberId = getSumReactionPointByMemberId(rq.getLoginedMemberId(), relTypeCode, relId);

		if (getPointTypeCodeByMemberId > 0) {
			return true;
		}
		return false;
	}

	// 이미 싫어요 리액션 포인트를 추가한 상태인지 확인하는 메서드
	public boolean isAlreadyAddBadRp(int relId, String relTypeCode) {
		int getPointTypeCodeByMemberId = getSumReactionPointByMemberId(rq.getLoginedMemberId(), relTypeCode, relId);

		if (getPointTypeCodeByMemberId < 0) {
			return true;
		}
		return false;
	}

	// 회원의 리액션 포인트 합계를 조회하는 메서드
	private Integer getSumReactionPointByMemberId(int actorId, String relTypeCode, int relId) {
		Integer getSumRP = reactionPointRepository.getSumReactionPointByMemberId(actorId, relTypeCode, relId);

		return (int) getSumRP;
	}

}
