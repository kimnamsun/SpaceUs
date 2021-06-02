package com.kh.spaceus.community.group.model.vo;

import java.io.Serializable;
import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GBComment implements Serializable {

	private String groupBoardCommentNo; 
	private String writer;
	private String groupBoardRef;
	private int secret; 
	private String groupBoardCommentRef; 
	private String groupBoardContent; 
	private String groupBoardCommentLevel;
	private Date groupBoardDate; 
	private String reportCnt;
	private String nickname;
	
}
