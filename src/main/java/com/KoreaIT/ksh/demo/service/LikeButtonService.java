package com.KoreaIT.ksh.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KoreaIT.ksh.demo.repository.LikeButtonRepository;
import com.KoreaIT.ksh.demo.vo.ResultData;
import com.KoreaIT.ksh.demo.vo.Rq;

@Service
public class LikeButtonService {
	@Autowired
	private LikeButtonRepository likeButtonRepository;
	@Autowired
	Rq rq;

	public ResultData actorCanMakeReaction(int loginedMemberId, String relId) {
		if (loginedMemberId == 0) {
			return ResultData.from("F-L", "로그인 후 이용해주세요.");
		}
		// 해당 회원의 리액션 포인트 합계 조회
		int sumReactionPointByMemberId = likeButtonRepository.getSumReactionPointByMemberId(loginedMemberId, relId);

		if (sumReactionPointByMemberId != 0) {
			return ResultData.from("F-1", "추천 불가", "sumReactionPointByMemberId", sumReactionPointByMemberId);
		}

		return ResultData.from("S-1", "추천 가능", "sumReactionPointByMemberId", sumReactionPointByMemberId);
	}

	public ResultData deleteGoodReactionPoint(int loginedMemberId, String relId) {
		// 리액션 포인트 삭제
		likeButtonRepository.deleteReactionPoint(loginedMemberId, relId);

		return ResultData.from("S-1", "좋아요 취소 됨");
	}

	public ResultData addGoodReactionPoint(int loginedMemberId, String relId) {
		// 리액션 포인트 추가
		int affectedRow = likeButtonRepository.addGoodReactionPoint(loginedMemberId, relId);

		if (affectedRow != 1) {
			return ResultData.from("F-1", "좋아요 실패");
		}

		return ResultData.from("S-1", "좋아요 처리 됨");
	}
	
	// 이미 좋아요 리액션 포인트를 추가한 상태인지 확인하는 메서드
		public boolean isAlreadyAddGoodRp(String relId) {
			int getPointTypeCodeByMemberId = getSumReactionPointByMemberId(rq.getLoginedMemberId(),relId);

			if (getPointTypeCodeByMemberId > 0) {
				return true;
			}
			return false;
		}

		// 회원의 리액션 포인트 합계를 조회하는 메서드
		private Integer getSumReactionPointByMemberId(int loginedMemberId, String relId) {
			Integer getSumRP = likeButtonRepository.getSumReactionPointByMemberId(loginedMemberId, relId);

			return (int) getSumRP;
		}


}
