package com.KoreaIT.ksh.demo.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.KoreaIT.ksh.demo.repository.ScheduleDao;
import com.KoreaIT.ksh.demo.vo.DateData;
import com.KoreaIT.ksh.demo.vo.ScheduleDto;

@Controller
public class UsrCalenderController {
	@Autowired
	SqlSession sqlsession;

	@RequestMapping(value = "calendar.do", method = RequestMethod.GET)
	public String calendar(Model model, HttpServletRequest request, DateData dateData) {

		Calendar cal = Calendar.getInstance();
		DateData calendarData;
		// 검색 날짜
		if (dateData.getDate().equals("") && dateData.getMonth().equals("")) {
			dateData = new DateData(String.valueOf(cal.get(Calendar.YEAR)), String.valueOf(cal.get(Calendar.MONTH)),
					String.valueOf(cal.get(Calendar.DATE)), null, null);
		}

		Map<String, Integer> today_info = dateData.today_info(dateData);
		List<DateData> dateList = new ArrayList<DateData>();

		// 검색 날짜 end
		ScheduleDao scheduleDao = sqlsession.getMapper(ScheduleDao.class);
		ArrayList<ScheduleDto> Schedule_list = scheduleDao.schedule_list(dateData);

		// 달력 데이터에 넣기 위한 배열 추가
		ScheduleDto[][] schedule_data_arr = new ScheduleDto[32][4];
		if (!Schedule_list.isEmpty()) {
			int j = 0;
			for (int i = 0; i < Schedule_list.size(); i++) {
				int startdate = Integer.parseInt(String.valueOf(Schedule_list.get(i).getSchedule_startdate())
						.substring(String.valueOf(Schedule_list.get(i).getSchedule_startdate()).length() - 2));
				int enddate = Integer.parseInt(String.valueOf(Schedule_list.get(i).getSchedule_enddate())
						.substring(String.valueOf(Schedule_list.get(i).getSchedule_enddate()).length() - 2));
				for (int date = startdate; date <= enddate; date++) {
					if (i > 0) {
						int date_before = Integer
								.parseInt(String.valueOf(Schedule_list.get(i - 1).getSchedule_startdate()).substring(
										String.valueOf(Schedule_list.get(i - 1).getSchedule_startdate()).length() - 2));
						if (date_before == date) {
							j = j + 1;
							schedule_data_arr[date][j] = Schedule_list.get(i);
						} else {
							j = 0;
							schedule_data_arr[date][j] = Schedule_list.get(i);
						}
					} else {
						schedule_data_arr[date][j] = Schedule_list.get(i);
					}
				}
				ScheduleDto schedule = Schedule_list.get(i);
				schedule.setColor(generateRandomColor());
			}
		}

		// 실질적인 달력 데이터 리스트에 데이터 삽입 시작.
		// 일단 시작 인덱스까지 아무것도 없는 데이터 삽입
		for (int i = 1; i < today_info.get("start"); i++) {
			calendarData = new DateData(null, null, null, null, null);
			dateList.add(calendarData);
		}

		// 날짜 삽입
		for (int i = today_info.get("startDay"); i <= today_info.get("endDay"); i++) {
			ScheduleDto[] schedule_data_arr3 = schedule_data_arr[i];

			if (i == today_info.get("today")) {
				calendarData = new DateData(String.valueOf(dateData.getYear()), String.valueOf(dateData.getMonth()),
						String.valueOf(i), "today", schedule_data_arr3);
			} else {
				calendarData = new DateData(String.valueOf(dateData.getYear()), String.valueOf(dateData.getMonth()),
						String.valueOf(i), "normal_date", schedule_data_arr3);
			}
			dateList.add(calendarData);
		}

		// 빈 데이터 삽입
		int index = 7 - dateList.size() % 7;
		if (dateList.size() % 7 != 0) {
			for (int i = 0; i < index; i++) {
				calendarData = new DateData(String.valueOf(dateData.getYear()), String.valueOf(dateData.getMonth()),
						null, "normal_date", null);
				dateList.add(calendarData);
			}
		}

		// 배열에 담음
		model.addAttribute("dateList", dateList); // 날짜 데이터 배열
		model.addAttribute("today_info", today_info);
		return "usr/calender/calender";
	}

	private String generateRandomColor() {
		Random random = new Random();
		int r = random.nextInt(256);
		int g = random.nextInt(256);
		int b = random.nextInt(256);
		return String.format("#%02x%02x%02x", r, g, b);
	}

	@RequestMapping(value = "schedule_add.do", method = RequestMethod.GET)
	public String schedule_add(HttpServletRequest request, ScheduleDto scheduleDto, RedirectAttributes rttr) {
		ScheduleDao scheduleRepository = sqlsession.getMapper(ScheduleDao.class);
		int count = scheduleRepository.before_schedule_add_search(scheduleDto);
		String message = "";
		String url = "redirect:calendar.do";

		if (count >= 4) {
			message = "스케쥴은 최대 4개만 등록 가능합니다.";
		} else {
			scheduleRepository.schedule_add(scheduleDto);
			message = "스케쥴이 등록되었습니다";
		}

		rttr.addFlashAttribute("message", message);
		return url;
	}
}
