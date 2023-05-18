package com.KoreaIT.ksh.demo.repository;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.KoreaIT.ksh.demo.vo.DateData;
import com.KoreaIT.ksh.demo.vo.ScheduleDto;

@Mapper
public interface ScheduleDao {

	public int schedule_add(ScheduleDto scheduleDto);
	public int before_schedule_add_search(ScheduleDto scheduleDto);
	public ArrayList<ScheduleDto> schedule_list(DateData scheduleDto);
}


