package com.KoreaIT.ksh.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {
	private int id;
	private String regDate;
	private String updateDate;
	private String title;
	private String body;
	private int memberId;
	private String name;
	private int boardId;
	private int hitCount;
	private int goodReactionPoint;
	private int badReactionPoint;
	private int count;
//	private boolean actorCanDelete;
}