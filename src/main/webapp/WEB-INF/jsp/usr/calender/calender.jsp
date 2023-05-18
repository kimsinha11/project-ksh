<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/head.jspf"%>
<html lang="ko">
<head>
<title>캘린더</title>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<!-- jquery datepicker -->
<link rel="stylesheet" href="http://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css" type="text/css" />
<script src="http://code.jquery.com/ui/1.8.18/jquery-ui.min.js"></script>
<link href="/resources/css/main.css" rel="stylesheet" type="text/css">
<script src="/resources/js/board.js"></script>
<!-- jquery datepicker 끝 -->

<meta http-equiv="content-type" content="text/html; charset=utf-8">

<script type="text/javaScript" language="javascript">
	
</script>
<style>
.schedule_form {
	display: none; /* 기본적으로 숨겨진 상태로 설정 */
}

/* 날짜 네비게이션 */
.navigation {
	margin-bottom: 20px;
}

.before_after_year, .before_after_month {
	text-decoration: none;
	color: #000;
	margin-right: 10px;
}

.before_after_year:hover, .before_after_month:hover {
	text-decoration: underline;
}

.this_month {
	font-weight: bold;
	font-size: 20px;
	margin: 0 10px;
}

/* 달력 테이블 */
.calendar_body {
	width: 100%;
	height: 700px;
	border-collapse: collapse;
	margin-bottom: 20px;
}

.calendar_body th {
	background-color: #CECECE;
	text-align: center;
	padding: 10px;
}

.calendar_body td {
	text-align: center;
	padding: 10px;
	border: 1px solid #ddd;
}

.calendar_body .day {
	font-weight: bold;
}

.calendar_body .today {
	background-color: #f5f5f5;
}

.calendar_body .sat_day {
	background-color: #eaf6ff;
}

.calendar_body .sun_day {
	background-color: #fff6f6;
}

.calendar_body .date {
	font-weight: bold;
	margin-bottom: 5px;
}

.calendar_body .date_subject {
	margin-top: 5px;
	font-weight: bold;
}

/* 일정 추가 버튼 */
.schudule_button_div {
	text-align: center;
	margin-bottom: 20px;
}

.board_move {
	background-color: #007bff;
	color: #gray;
	padding: 10px 20px;
	border: none;
	border-radius: 4px;
	font-size: 16px;
	cursor: pointer;
}

.board_move:hover {
	background-color: #0069d9;
}

.today_button_div {
text-align: right;}
</style>

</head>
<body>
		<form name="calendarFrm" id="calendarFrm" action="" method="GET">
				<input type="hidden" name="year" value="${today_info.search_year}" />
				<input type="hidden" name="month" value="${today_info.search_month-1}" />
				<script>
					var message = "${message}";
					console.log(message);
					if (message != "") {
						alert(message);
					}
				</script>

				<script>
					function showForm() {
						var form = document.querySelector(".schedule_form");
						form.style.display = "block"; // 폼을 보이도록 변경
					}
				</script>


				<div class="calendar">

						<!--날짜 네비게이션  -->
						<div class="navigation">
								<a class="before_after_year"
										href="./calendar.do?year=${today_info.search_year-1}&month=${today_info.search_month-1}">
										&lt;&lt;
										<!-- 이전해 -->
								</a>
								<a class="before_after_month"
										href="./calendar.do?year=${today_info.before_year}&month=${today_info.before_month}">
										&lt;
										<!-- 이전달 -->
								</a>
								<span class="this_month">
										&nbsp;${today_info.search_year}.
										<c:if test="${today_info.search_month<10}">0</c:if>${today_info.search_month}
								</span>
								<a class="before_after_month" href="/calendar.do?year=${today_info.after_year}&month=${today_info.after_month}">
										<!-- 다음달 -->
										&gt;
								</a>
								<a class="before_after_year"
										href="/calendar.do?year=${today_info.search_year+1}&month=${today_info.search_month-1}">
										<!-- 다음해 -->
										&gt;&gt;
								</a>
						</div>

						<div class="today_button_div">
								<input type="button" class="btn-text-link btn btn-outline btn-xs" onclick="javascript:location.href='/calendar.do'" value="go today" />
						</div>
						<table class="calendar_body">

								<thead>
										<tr bgcolor="#CECECE">
												<td class="day sun">일</td>
												<td class="day">월</td>
												<td class="day">화</td>
												<td class="day">수</td>
												<td class="day">목</td>
												<td class="day">금</td>
												<td class="day sat">토</td>
										</tr>
								</thead>
								<tbody>
										<tr>
												<c:forEach var="dateList" items="${dateList}" varStatus="date_status">
														<c:choose>
																<c:when test="${dateList.value=='today'}">
																		<c:if test="${date_status.index%7==0}">
																				<tr>
																		</c:if>
																		<td style="background-color: lightgray;" class="today">
																				<div  class="date">
																</c:when>
																<c:when test="${date_status.index%7==6}">
																		<td class="sat_day">
																				<div class="sat">
																</c:when>
																<c:when test="${date_status.index%7==0}">
										</tr>
										<tr>
												<td class="sun_day">
														<div class="sun">
																</c:when>
																<c:otherwise>
																		<td class="normal_day">
																				<div class="date">
																</c:otherwise>
																</c:choose>
																${dateList.date}
														</div>
														<div>
																<c:forEach var="scheduleList" items="${dateList.schedule_data_arr}" varStatus="schedule_data_arr_status">
																		<p class="date_subject">${scheduleList.schedule_subject}</p>
																</c:forEach>
														</div>
												</td>
												</c:forEach>
								</tbody>

						</table>

						<div class="schudule_button_div">
								<button type="button" onclick="showForm()" class="btn-text-link btn btn-outline btn-xs">일정추가</button>

						</div>
		</form>

		<div id="mask_board_move"></div>
		<div class="normal_move_board_modal">
				<script>
					$(function() {
						$("#testDatepicker").datepicker(
								{

									dateFormat : "yy-mm-dd",
									changeMonth : true,
									changeYear : true,
									dayNames : [ '월요일', '화요일', '수요일', '목요일',
											'금요일', '토요일', '일요일' ],
									dayNamesMin : [ '월', '화', '수', '목', '금',
											'토', '일' ],
									monthNamesShort : [ '1', '2', '3', '4',
											'5', '6', '7', '8', '9', '10',
											'11', '12' ]
								});
					});
					function scheduleAdd() {
						var schedule_add_form = document.schedule_add;
						if (schedule_add_form.schedule_date.value == ""
								|| schedule_add_form.schedule_date.value == null) {
							alert("날짜를 입력해주세요.");
							schedule_add_form.schedule_date.focus();
							return false;
						} else if (schedule_add_form.schedule_subject.value == ""
								|| schedule_add_form.schedule_subject.value == null) {
							alert("제목을 입력해주세요.");
							schedule_add_form.schedule_date.focus();
							return false;
						}
						schedule_add_form.submit();
					}
				</script>



				<div class="schedule_form">
						<div class="info"></div>
						<form name="schedule_add" action="schedule_add.do">
								<input type="hidden" name="year" value="${today_info.search_year}" />
								<input type="hidden" name="month" value="${today_info.search_month-1}" />
								<div class="contents">
										<ul>
												<li>
														<div class="text_subject">순번 :</div>
														<div style = "border:1px solid gray;"class="text_desc">
																<input type="text" name="schedule_num" class="text_type1" />
														</div>
												</li>
												<li>
														<div class="text_subject">날짜 :</div>
														<div style = "border:1px solid gray;" class="text_desc">
																<input type="text" name="schedule_date" class="text_type1" id="testDatepicker" readonly="readonly" />
														</div>
												</li>
												<li>
														<div class="text_subject">제목 :</div>
														<div style = "border:1px solid gray;" class="text_desc">
																<input type="text" name="schedule_subject" class="text_type1" />
														</div>
												</li>
												<li>
														<div class="text_subject">내용 :</div>
														<div style = "border:1px solid gray;" class="text_area_desc">
																<textarea name="schedule_desc" class="textarea_type1" rows="7"></textarea>
														</div>
												</li>
												<li class="button_li">
														<button type="button" class="btn-text-link btn btn-outline btn-xs" onclick="scheduleAdd();">일정등록</button>
												</li>
										</ul>

								</div>
						</form>
				</div>

		</div>



<%@ include file="../common/foot.jspf"%>