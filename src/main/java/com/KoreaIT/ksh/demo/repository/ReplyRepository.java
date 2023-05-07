package com.KoreaIT.ksh.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.KoreaIT.ksh.demo.vo.Reply;

@Mapper
public interface ReplyRepository {

	public Reply getReply(int id);

	public void modifyReply(int id, String body);

	public void writeReply(String body, int memberId, int relId, int boardId);

	public int getLastInsertId();

	public void deleteReply(int id);

	public List<Reply> getReplys(int id);

}
