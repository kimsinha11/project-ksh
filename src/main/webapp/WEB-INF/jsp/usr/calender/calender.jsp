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

<!-- jquery datepicker 끝 -->

<meta http-equiv="content-type" content="text/html; charset=utf-8">

<script type="text/javaScript" language="javascript">
	
</script>

<style>
.day {
	width: 100px;
	height: 30px;
	font-weight: bold;
	font-size: 15px;
	font-weight: bold;
	text-align: center;
}

.sat {
	color: #529dbc;
}

.sun {
	color: red;
}

.today_button_div {
	float: right;
}

.today_button {
	width: 100px;
	height: 30px;
}

.calendar {
	width: 80%;
	margin: auto;
}

.navigation {
	margin-top: 100px;
	margin-bottom: 30px;
	text-align: center;
	font-size: 25px;
	vertical-align: middle;
}

.calendar_body {
	width: 100%;
	background-color: #FFFFFF;
	border: 1px solid white;
	margin-bottom: 50px;
	border-collapse: collapse;
}

.calendar_body .today {
	border: 1px solid white;
	height: 120px;
	background-color: #c9c9c9;
	text-align: left;
	vertical-align: top;
}

.calendar_body .date {
	font-weight: bold;
	font-size: 15px;
	padding-left: 3px;
	padding-top: 3px;
}

.date {
	margin-bottom: 10px;
}

.sat {
	margin-bottom: 10px;
}

.sun {
	margin-bottom: 10px;
}

.calendar_body .sat_day {
	border: 1px solid white;
	height: 120px;
	background-color: #EFEFEF;
	text-align: left;
	vertical-align: top;
}

.calendar_body .sat_day .sat {
	color: #529dbc;
	font-weight: bold;
	font-size: 15px;
	padding-left: 3px;
	padding-top: 3px;
}

.calendar_body .sun_day {
	border: 1px solid white;
	height: 120px;
	background-color: #EFEFEF;
	text-align: left;
	vertical-align: top;
}

.calendar_body .sun_day .sun {
	color: red;
	font-weight: bold;
	font-size: 15px;
	padding-left: 3px;
	padding-top: 3px;
}

.calendar_body .normal_day {
	border: 1px solid white;
	height: 120px;
	background-color: #EFEFEF;
	vertical-align: top;
	text-align: left;
}

.before_after_month {
	margin: 10px;
	font-weight: bold;
}

.before_after_year {
	font-weight: bold;
}

.this_month {
	margin: 10px;
}

.schdule_add_button {
	float: right;
}
/*
		*	게시판 이동 모달
		*/
.date_subject {
	margin: 0px;
	margin-bottom: 5px;
	margin-left: 12px;
	font-size: 12px;
	font-weight: bold;
}

.schedule_form {
	display: none; /* 기본적으로 숨겨진 상태로 설정 */
}

.schedule_editform {
	display: none;
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
	border: 2px solid lightgray;
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
	text-align: right;
}
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
					function showeditForm() {
						var form = document.querySelector(".schedule_editform");
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
								<input type="button" class="btn-text-link btn btn-outline btn-sm"
										onclick="javascript:location.href='/calendar.do'" value="go today" />
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
																				<div class="date">
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
																		<c:if test="${scheduleList.memberId == rq.loginedMemberId}">
																				<p style="background-color: ${scheduleList.color};" class="date_subject">
																						<a href="#" class="schedule-link"
																								data-schedule='{"schedule_num": "${scheduleList.schedule_num}","schedule_idx": "${scheduleList.schedule_idx}",  "start_date": "${scheduleList.schedule_startdate}", "end_date": "${scheduleList.schedule_enddate}", "schedule_subject": "${scheduleList.schedule_subject}", "schedule_desc": "${scheduleList.schedule_desc}"}'
																								onclick="showScheduleDetails(event)">${scheduleList.schedule_subject}</a>
																				</p>
																		</c:if>
																</c:forEach>

														</div>
												</td>
												</c:forEach>
								</tbody>

						</table>

						<div class="schudule_button_div">
								<button type="button" onclick="showForm()" class="btn-text-link btn btn-outline btn-sm">일정추가</button>

						</div>
		</form>

		<script>
			function displayScheduleInfo(schedule) {
				var scheduleDetailsContainer = document
						.getElementById("scheduleDetailsContainer");

				// 일정 정보를 동적으로 생성하여 컨테이너에 추가
				var scheduleDetailsHtml = "<h3 style='color: blue;'>일정 정보</h3>"
						+ "<p class='input input-bordered w-full max-w-xs'>순번: "
						+ schedule.schedule_num
						+ "</p>"
						+ "<p class='input input-bordered w-full max-w-xs '>일정번호: "
						+ schedule.schedule_idx
						+ "</p>"
						+ "<p class='input input-bordered w-full max-w-xs datepicker'>시작일: "
						+ schedule.start_date
						+ "</p>"
						+ "<p class='input input-bordered w-full max-w-xs datepicker'>종료일: "
						+ schedule.end_date
						+ "</p>"
						+ "<p class='input input-bordered w-full max-w-xs'>제목: "
						+ schedule.schedule_subject
						+ "</p>"
						+ "<p class='input input-bordered w-full max-w-xs'>내용: "
						+ schedule.schedule_desc
						+ "</p>"
						+ "<a class='btn-text-link btn btn-outline btn-sm' style='margin: 10px;' onclick=\"if(confirm('정말 삭제하시겠습니까?') == false) return false;\" href='/usr/calender/delete?idx="
						+ schedule.schedule_idx
						+ "'>삭제</a>"
						+ "<a class='btn-text-link btn btn-outline btn-sm' style='margin: 10px;' href='#' onclick='showeditForm("
						+ schedule.schedule_idx + ")'>수정</a>";

				scheduleDetailsContainer.innerHTML = scheduleDetailsHtml;

				// 수정 폼에 schedule_idx 값 설정
				var scheduleEditForm = document.forms["schedule_edit"];
				var scheduleIdxInput = scheduleEditForm
						.querySelector("input[name='schedule_idx']");
				scheduleIdxInput.value = schedule.schedule_idx;
			}
			function showScheduleDetails(event) {
				event.preventDefault();
				var target = event.target;
				var schedule = JSON.parse(target.getAttribute("data-schedule"));

				displayScheduleInfo(schedule);
			}
		</script>


		<script>
			$(function() {
				$("#testDatepicker")
						.datepicker(
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
											'11', '12' ],
									multiSelect : true
								// 여러 날짜 선택을 허용
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
		<script>
			$(function() {
				$("#endDatePicker")
						.datepicker(
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
											'11', '12' ],
									multiSelect : true
								// 여러 날짜 선택을 허용
								});
			});

			function scheduleAdd() {
				var schedule_add_form = document.schedule_add;
				if (schedule_add_form.schedule_startdate.value == ""
						|| schedule_add_form.schedule_startdate.value == null) {
					alert("시작일을 입력해주세요.");
					schedule_add_form.schedule_startdate.focus();
					return false;
				} else if (schedule_add_form.schedule_enddate.value == ""
						|| schedule_add_form.schedule_enddate.value == null) {
					alert("종료일을 입력해주세요.");
					schedule_add_form.schedule_enddate.focus();
					return false;
				} else if (schedule_add_form.schedule_subject.value == ""
						|| schedule_add_form.schedule_subject.value == null) {
					alert("제목을 입력해주세요.");
					schedule_add_form.schedule_subject.focus();
					return false;
				}
				schedule_add_form.submit();
			}
		</script>
		<script>
			$(function() {
				$(".datepicker")
						.datepicker(
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
											'11', '12' ],
									multiSelect : true
								});
			});

			function scheduleEdit() {
				var form = document.schedule_edit;
				if (form.schedule_startdate.value === ""
						|| form.schedule_startdate.value === null) {
					alert("시작일을 입력해주세요.");
					form.schedule_startdate.focus();
					return false;
				}
				if (form.schedule_enddate.value === ""
						|| form.schedule_enddate.value === null) {
					alert("종료일을 입력해주세요.");
					form.schedule_enddate.focus();
					return false;
				}
				if (form.schedule_subject.value === ""
						|| form.schedule_subject.value === null) {
					alert("제목을 입력해주세요.");
					form.schedule_subject.focus();
					return false;
				}
				form.submit();
			}
		</script>
		<!-- 이벤트 위임을 사용하여 일정 요소에 이벤트 핸들러를 등록 -->
		<script>
			document.addEventListener("click", function(event) {
				var target = event.target;
				if (target.matches(".schedule-item")) {
					showScheduleDetails(event);
				}
			});
		</script>
		<div id="scheduleDetailsContainer"></div>

		<div class="schedule_form">
				<div class="info"></div>
				<form name="schedule_add" action="schedule_add.do">
						<input type="hidden" name="year" value="${today_info.search_year}" />
						<input type="hidden" name="month" value="${today_info.search_month-1}" />


						<div class="contents">
								<ul>
										<li>
												<div class="text_subject"></div>
												<div style="border: 1px solid gray;" class="text_desc">
														<input style="width: 100%;" type="hidden" name="memberId" value="${rq.loginedMemberId}" class="text_type1" />
												</div>
										</li>
										<li>

												<div class="text_desc">
														순번 :
														<input style="width: 100%;" type="text" name="schedule_num" class="input input-bordered w-full max-w-xs" />
												</div>
										</li>
										<li>
												<div class="text_desc">
														색상:
														<select name="color" class="input input-bordered w-full max-w-xs" onchange="changeBackgroundColor(this)">
																<option value="#FFFFFF">흰색</option>
																<option value="#FF0000">빨강</option>
																<option value="#FFA500">주황</option>
																<option value="#FFFF00">노랑</option>
																<option value="#00FF00">초록</option>
																<option value="#0000FF">파랑</option>
																<option value="#800080">보라</option>
																<option value="#FFC0CB">핑크</option>
																<option value="#FFFFCC">연노랑</option>
																<option value="#CCFFCC">연초록</option>
																<option value="#CCE0FF">연파랑</option>
																<option value="#E6E6FA">연보라</option>
																<!-- 다른 색상 옵션들 추가 -->
														</select>
												</div>
										</li>
										<li>

												<div class="text_desc">
														시작 :
														<input style="width: 100%;" type="text" name="schedule_startdate"
																class="input input-bordered w-full max-w-xs datepicker" readonly="readonly" />
												</div>
										</li>
										<li>

												<div class="text_desc">
														종료 :
														<input style="width: 100%;" type="text" name="schedule_enddate"
																class="input input-bordered w-full max-w-xs datepicker" readonly="readonly" />
												</div>
										</li>

										<li>

												<div class="text_desc">
														제목 :
														<input style="width: 100%;" type="text" name="schedule_subject"
																class=" input input-bordered w-full max-w-xs" />
												</div>
										</li>
										<li>

												<div class="text_area_desc">
														내용 :
														<textarea style="width: 100%;" name="schedule_desc" class="input input-bordered w-full max-w-xs" rows="7"></textarea>
												</div>
										</li>
										<li class="button_li">
												<button type="button" class="btn-text-link btn btn-outline btn-sm" onclick="scheduleAdd();">일정등록</button>
										</li>
								</ul>

						</div>
				</form>
		</div>

		<div id="scheduleEditContainer" style="border: 1px solid black;"></div>

		<div class="schedule_editform">
				<div class="info"></div>
				<form name="schedule_edit" action="../usr/calender/edit">

						<input type="hidden" name="schedule_idx" />

						<div class="contents">
								<ul>

										<li>

												<div class="text_desc">
														순번 :
														<input style="width: 100%;" type="text" name="schedule_num" class="input input-bordered w-full max-w-xs" />
												</div>
										</li>
										<li>
												<div class="text_desc">
														색상:
														<select name="color" class="input input-bordered w-full max-w-xs">
																<option value="#FF0000">빨강</option>
																<option value="#00FF00">초록</option>
																<option value="#0000FF">파랑</option>
																<!-- 다른 색상 옵션들 추가 -->
														</select>
												</div>
										</li>
										<li>

												<div class="text_desc">
														시작 :
														<input style="width: 100%;" type="text" name="schedule_startdate"
																class="input input-bordered w-full max-w-xs datepicker" readonly="readonly" />
												</div>
										</li>
										<li>

												<div class="text_desc">
														종료 :
														<input style="width: 100%;" type="text" name="schedule_enddate"
																class="input input-bordered w-full max-w-xs datepicker" readonly="readonly" />
												</div>
										</li>

										<li>

												<div class="text_desc">
														제목 :
														<input style="width: 100%;" type="text" name="schedule_subject"
																class=" input input-bordered w-full max-w-xs" />
												</div>
										</li>
										<li>

												<div class="text_area_desc">
														내용 :
														<textarea style="width: 100%;" name="schedule_desc" class="input input-bordered w-full max-w-xs" rows="7"></textarea>
												</div>
										</li>
										<li class="button_li">
												<button type="button" style="margin: 10px;" class="btn-text-link btn btn-outline btn-sm"
														onclick="scheduleEdit();">수정</button>
										</li>
								</ul>

						</div>
				</form>
		</div>



		<%@ include file="../common/foot.jspf"%>