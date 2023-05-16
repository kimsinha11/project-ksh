<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="pageTitle" value="날씨"></c:set>
<%@ include file="../common/head.jspf"%>
<%@ page import="org.json.JSONObject"%>
<%@ page import="org.json.JSONArray"%>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="https://code.jquery.com/ui/1.13.0/jquery-ui.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.13.0/themes/base/jquery-ui.css">


<layout:fragment name="title">
	<title>Weather</title>
</layout:fragment>

<layout:fragment name="content">
<br />

	<label>[PTY:강수형태/REH:습도(%)/RN1:1시간강수량(mm)/T1H:기온(℃)/UUU:동서바람성분(%)/VEC:풍향(deg)/VVV:남북바람성분(%)/WSD:풍속(m/s)]</label>
<hr />
<br />
	<form class="form-horizontal">
		<div class="form-group">
			<select id="step1" class="emptyCheck" title="시/도">
				<option id="city" value="">시/도</option>
			</select>

			<select id="step2">
				<option id="county" value="">시/군/구</option>
			</select>

			<select id="step3">
				<option id="town" value="">읍/면/동</option>
			</select>
 
			<span style="display:inline-block;">날짜 선택: <input type="text" id="datepicker" disabled="disabled" class="emptyCheck" title="날짜"></span>

			<select id="time" class="emptyCheck" title="시간">
								<option value="" selected>시간</option>
								<option value="0">0시</option>
								<option value="1">1시</option>
								<option value="1">2시</option>
								<option value="1">3시</option>
								<option value="1">4시</option>
								<option value="1">5시</option>
								<option value="1">6시</option>
								<option value="1">7시</option>
								<option value="1">8시</option>
								<option value="1">9시</option>
								<option value="1">10시</option>
								<option value="1">11시</option>
								<option value="1">12시</option>
								<option value="1">13시</option>
								<option value="1">14시</option>
								<option value="1">15시</option>
								<option value="1">16시</option>
								<option value="1">17시</option>
								<option value="1">17시</option>
								<option value="1">18시</option>
								<option value="1">19시</option>
								<option value="1">20시</option>
								<option value="1">21시</option>
								<option value="1">22시</option>
								<option value="23">23시</option>
						</select>


			<button type="button" class="btn-text-link btn btn-outline btn-xs" onclick="getWeather()">
				<span>실행</span>
			</button>

			<div>
				<table id="resultWeather" class="table"></table>
			</div>
		</div>
	</form>


</layout:fragment>
	<th:block layout:fragment="script">
	<script th:inline="javascript">
		/*<![CDATA[*/
		window.onload = function() {
			$('#datepicker').datepicker({
				showOn: 'button',
				buttonImage: 'http://jqueryui.com/resources/demos/datepicker/images/calendar.gif',
				buttonImageOnly: true,
				showButtonPanel: true,
				dateFormat: 'yymmdd',
				minDate: "-1D",
				maxDate: 0,
				closeText: "닫기",
				currentText: "오늘",
				prevText: '이전 달',
				nextText: '다음 달',
				monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
				monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
				dayNames: ['일', '월', '화', '수', '목', '금', '토'],
				dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
				dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
				weekHeader: "주",
				yearSuffix: '년',
				showMonthAfterYear: true
			});
			// 추가된 코드
			loadArea('city');
	};

	$('#step1').on("change", function() {
		loadArea('county', $(this));
	});

	$('#step2').on("change", function() {
		loadArea('town', $(this));
	});

	function loadArea(type, element) {
		var value = $(element).find('option:selected').text();
		var data = {type : type, keyword : value};

		console.log(data);
		$.ajax({
			url: "/board/weatherStep.do",
			data: data,
			dataType: "JSON",
			method : "POST",
			success : function(res){
				if (type == 'city'){
					res.forEach(function (city) {
						$('#step1').append('<option value="'+city.areacode+'">'+city.step1+'</option>')
					});
				}
				else if(type == 'county'){
					$('#county').siblings().remove();
					$('#town').siblings().remove();
					res.forEach(function (county) {
						$('#step2').append('<option value="'+county.areacode+'">'+county.step2+'</option>')
					});
				}
				else{
					$('#town').siblings().remove();
					res.forEach(function (town) {
						$('#step3').append('<option value="'+town.areacode+'">'+town.step3+'</option>')
					});
				}
			},
			error : function(xhr){
				alert(xhr.responseText);
			}
		});
	}

        	/*]]>*/
	</script>
</th:block>
<script>
$('#datepicker').on("change", function(){
	$('#time option:eq(0)').prop("selected", true);
	var now = new Date();
	var currentHour = now.getHours();
	var month = (now.getMonth()+1) < 10 ? "0" + (now.getMonth()+1) : (now.getMonth()+1);
	var nowDate = now.getDate() < 10 ? "0" + now.getDate() : now.getDate();
	var today = now.getFullYear() + "" + month + "" + nowDate;
	var datepickerValue = $(this).val();

	/* 오늘 날짜라면 */
	$('#time').children('option:not(:first)').remove();
	var html = '';
	if (datepickerValue == today ){
		for (var i=0; i<=currentHour; i++){
			html += '<option value="'+ i +'">'+ i +'시</option>';
		}
	}
	/* 어제였다면 */
	else{
		if (currentHour != 23){
			for (var i=currentHour + 1; i<24; i++){
				html += '<option value="'+ i +'">'+ i +'시</option>';
			}
		}
		else{
			alert(datepickerValue + " 날짜의 제공 가능한 날씨 데이터가 없습니다.")
			$(this).val('');
		}
	}
	$('#time').append(html);
});

function getWeather() {
	var nullCheck = true;
	$('.emptyCheck').each(function (){
		if ('' == $(this).val()){
			alert($(this).attr('title') + "을(를) 확인바람");
			nullCheck = false;
			return false;	// 빈 값에서 멈춤
		}
	});

	if (nullCheck) {
		var time = $('#time').val()+'00';
		if ($('#time').val() < 10){
			time = "0" + time;
		}
		var areacode = "";
		var cityCode = $('#step1 option:selected').val();
		var countyCode = $('#step2 option:selected').val();
		var townCode = $('#step3 option:selected').val();

		if (townCode == '' && countyCode == '') {
			areacode = cityCode;
		}
		else if(townCode == '' && countyCode != '') {
			areacode = countyCode;
		}
		else if(townCode != '') {
			areacode = townCode;
		}

		var date = $('#datepicker').val();
		var data = {"areacode" : areacode, "baseDate" : date, "baseTime" : time};

		$.ajax({
			url: "/board/getWeather.do",
			data: data,
			dataType: "JSON",
			method : "POST",
			success : function(res){
				console.log(res);
				if (res[0].resultCode != null) {
					alert(res[0].resultMsg);
				}
				else {
					var html = "";
					html += "<tbody><tr><th>nx=" + res[0].nx + "</th><th>ny=" + res[0].ny + "</th></tr>";

					$("#resultWeather").empty();
					$.each(res, function(index, item){
						html += "<tr><td>" + item.category + "</td><td>" + item.obsrValue + "</td></tr>";
					});

					html += "</tbody>";
					$("#resultWeather").append(html);
				}
		        },
		        error : function(xhr){
	                alert(xhr.responseText);
		        }
		});
	}
}
</script>

		<%@ include file="../common/foot.jspf"%>