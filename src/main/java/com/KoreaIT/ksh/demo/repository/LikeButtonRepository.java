package com.KoreaIT.ksh.demo.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LikeButtonRepository {

	public int getSumReactionPointByMemberId(int loginedMemberId, String  relTypeCode, String relId);

	public void deleteReactionPoint(int loginedMemberId,   String  relTypeCode, String relId);

	public int addGoodReactionPoint(int loginedMemberId,  String  relTypeCode, String relId);

}
