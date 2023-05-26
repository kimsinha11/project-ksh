package com.KoreaIT.ksh.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.ksh.demo.service.LikeButtonService;
import com.KoreaIT.ksh.demo.vo.ResultData;
import com.KoreaIT.ksh.demo.vo.Rq;

@Controller
public class UsrLikeButtonController {
    
    @Autowired
    private LikeButtonService likeButtonService;
    @Autowired
    Rq rq;
    
 // 좋아요(긍정적 반응) 처리
 	@RequestMapping("/usr/likebutton/doGoodReaction")
 	@ResponseBody
 	public ResultData doGoodReaction(String relId, String relTypeCode) {
 		// 현재 사용자가 해당 리소스에 대해 좋아요 반응을 할 수 있는지 확인
 		ResultData actorCanMakeReactionRd = likeButtonService.actorCanMakeReaction(rq.getLoginedMemberId(),  relTypeCode,  relId);

 		int actorCanMakeReaction = (int) actorCanMakeReactionRd.getData1();

 		if (actorCanMakeReaction == 1) {
 			// 이미 좋아요를 누른 상태이므로 좋아요 취소 처리
 			ResultData rd = likeButtonService.deleteGoodReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
 			return ResultData.from("S-1", "좋아요 취소");
 		} 

 		// 좋아요 반응 처리
 		ResultData rd = likeButtonService.addGoodReactionPoint(rq.getLoginedMemberId(), relTypeCode,  relId);

 		if (rd.isFail()) {
 			ResultData.from("F-2", rd.getMsg());
 		}

 		return ResultData.from("S-2", "좋아요");
 	}

   
}
