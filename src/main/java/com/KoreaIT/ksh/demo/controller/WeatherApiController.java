package com.KoreaIT.ksh.demo.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.databind.ObjectMapper;

//serviceKey=qYUvGhdyJWZABB1We4OO2lxkBstL%2Bk%2FlyLyoPB3KiyqDueGZfsol43bZsi5gf3zfIRtD%2Ftws%2BnFFPVdP85ZAKA%3D%3D
/*
@RestController : 기본으로 하위에 있는 메소드들은 모두 @ResponseBody를 가지게 된다.
@RequestBody : 클라이언트가 요청한 XML/JSON을 자바 객체로 변환해서 전달 받을 수 있다.
@ResponseBody : 자바 객체를 XML/JSON으로 변환해서 응답 객체의 Body에 실어 전송할 수 있다.
        클라이언트에게 JSON 객체를 받아야 할 경우는 @RequestBody, 자바 객체를 클라이언트에게 JSON으로 전달해야할 경우에는 @ResponseBody 어노테이션을 붙여주면 된다. 
@ResponseBody를 사용한 경우 View가 아닌 자바 객체를 리턴해주면 된다.
*/
@Controller
public class WeatherApiController {

	@GetMapping("/usr/camping/weather")
	public String restApiGetWeather(Model model) throws Exception {
		/*
		 * @ API LIST ~
		 * 
		 * getUltraSrtNcst 초단기실황조회 getUltraSrtFcst 초단기예보조회 getVilageFcst 동네예보조회
		 * getFcstVersion 예보버전조회
		 */
		String url = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst"
				+ "?serviceKey=qYUvGhdyJWZABB1We4OO2lxkBstL%2Bk%2FlyLyoPB3KiyqDueGZfsol43bZsi5gf3zfIRtD%2Ftws%2BnFFPVdP85ZAKA%3D%3D"
				+ "&dataType=JSON" // JSON, XML
				+ "&numOfRows=10" // 페이지 ROWS
				+ "&pageNo=1" // 페이지 번호
				+ "&base_date=20230513" // 발표일자
				+ "&base_time=0800" // 발표시각
				+ "&nx=60" // 예보지점 X 좌표
				+ "&ny=127"; // 예보지점 Y 좌표

		HashMap<String, Object> resultMap = getDataFromJson(url, "UTF-8", "get", "");

		System.out.println("# RESULT : " + resultMap);

		model.addAttribute("result", resultMap);

		String responseBody = (String) resultMap.get("responseBody");
		if (responseBody != null) {
			model.addAttribute("responseBody", responseBody);
		} else {
			model.addAttribute("responseBody", ""); // responseBody가 null일 경우 빈 문자열을 넘겨줌
		}

		return "/usr/home/APITest2";

	}

	public HashMap<String, Object> getDataFromJson(String url, String encoding, String type, String jsonStr)
			throws Exception {
		boolean isPost = false;

		if ("post".equals(type)) {
			isPost = true;
		} else {
			url = "".equals(jsonStr) ? url : url + "?request=" + jsonStr;
		}

		return getStringFromURL(url, encoding, isPost, jsonStr, "application/json");
	}

	public HashMap<String, Object> getStringFromURL(String url, String encoding, boolean isPost, String parameter,
			String contentType) throws Exception {
		URL apiURL = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) apiURL.openConnection();
		conn.setConnectTimeout(5000);
		conn.setReadTimeout(5000);
		conn.setUseCaches(false);
		conn.setRequestProperty("Content-Type", contentType);
		conn.setRequestProperty("Accept-Charset", encoding);

		if (isPost) {
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream(), encoding);
			osw.write(parameter);
			osw.flush();
			osw.close();
		}

		int responseCode = conn.getResponseCode();
		BufferedReader in;
		if (responseCode == HttpURLConnection.HTTP_OK) {
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), encoding));
		} else {
			in = new BufferedReader(new InputStreamReader(conn.getErrorStream(), encoding));
		}

		String inputLine;
		StringBuilder response = new StringBuilder();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		HashMap<String, Object> resultMap = new HashMap<>();
		resultMap.put("responseCode", responseCode);
		resultMap.put("responseMessage", conn.getResponseMessage());
		resultMap.put("responseBody", response.toString());

		return resultMap;
	}

}