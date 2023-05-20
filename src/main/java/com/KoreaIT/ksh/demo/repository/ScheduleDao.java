package com.KoreaIT.ksh.demo.repository;

import java.sql.Date;
import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.KoreaIT.ksh.demo.vo.DateData;
import com.KoreaIT.ksh.demo.vo.ScheduleDto;

@Mapper
public interface ScheduleDao {

	public int schedule_add(ScheduleDto scheduleDto);
	public int before_schedule_add_search(ScheduleDto scheduleDto);
	public ArrayList<ScheduleDto> schedule_list(DateData scheduleDto);
	public ScheduleDto getSchedule(int idx);
	public void deleteSchedule(int idx);
	public void editSchedule(int schedule_idx, String schedule_subject, String schedule_desc, Date schedule_startdate, Date schedule_enddate);
}


