package com.KoreaIT.ksh.demo.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDto {

	int schedule_idx;
	int schedule_num;
	String schedule_subject;
	String schedule_desc;
	Date schedule_startdate;
	Date schedule_enddate;

	
	public int getSchedule_idx() {
		return schedule_idx;
	}
	public void setSchedule_idx(int schedule_idx) {
		this.schedule_idx = schedule_idx;
	}
	
	
	public int getSchedule_num() {
		return schedule_num;
	}
	public void setSchedule_num(int schedule_num) {
		this.schedule_num = schedule_num;
	}
	
	
	public String getSchedule_subject() {
		return schedule_subject;
	}
	public void setSchedule_subject(String schedule_subject) {
		this.schedule_subject = schedule_subject;
	}
	
	
	public String getSchedule_desc() {
		return schedule_desc;
	}
	public void setSchedule_desc(String schedule_desc) {
		this.schedule_desc = schedule_desc;
	}
	
	
	public Date getSchedule_startdate() {
		return schedule_startdate;
	}
	public Date getSchedule_enddate() {
		return schedule_enddate;
	}
	public void setSchedule_startdate(Date schedule_startdate) {
		this.schedule_startdate = schedule_startdate;
	}
	
	public void setSchedule_enddate(Date schedule_enddate) {
		this.schedule_enddate = schedule_enddate;
	}
	
	
	@Override
	public String toString() {
		return "ScheduleDto [schedule_idx=" + schedule_idx + ", schedule_num=" + schedule_num + ", schedule_subject="
				+ schedule_subject + ", schedule_desc=" + schedule_desc + ", schedule_startdate=\" + schedule_startdate + \", schedule_enddate=" + schedule_enddate + "]";
	}
	
	
}