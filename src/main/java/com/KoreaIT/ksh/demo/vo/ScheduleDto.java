package com.KoreaIT.ksh.demo.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDto {
	int memberId;
	int schedule_idx;
	int schedule_num;
	String schedule_subject;
	String schedule_desc;
	Date schedule_startdate;
	Date schedule_enddate;
	String color;

 
}