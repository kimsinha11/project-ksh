package com.KoreaIT.ksh.demo.controller;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.ksh.demo.service.WeatherService;
import com.KoreaIT.ksh.demo.vo.AreaRequestDTO;
import com.KoreaIT.ksh.demo.vo.WeatherDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;


@Controller
public class WeatherApiController {
	
	@Autowired
	private WeatherService weatherService;

	// 날씨 페이지를 열기 위한 요청 처리
	@GetMapping("/usr/camping/weather")
	public String openWeatherPage() {
		return "/usr/camping/weather";
	}

	// 날씨 정보를 가져오는 요청 처리
	@PostMapping(value = "/board/getWeather.do")
	@ResponseBody
	public List<WeatherDTO> getWeatherInfo(@ModelAttribute AreaRequestDTO areaRequestDTO)
			throws JsonMappingException, JsonProcessingException, UnsupportedEncodingException, URISyntaxException {
		// 지역 코드를 기반으로 좌표를 가져옴
		AreaRequestDTO coordinate = this.weatherService.getCoordinate(areaRequestDTO.getAreacode());
		areaRequestDTO.setNx(coordinate.getNx());
		areaRequestDTO.setNy(coordinate.getNy());

		// 좌표를 기반으로 날씨 정보를 가져옴
		List<WeatherDTO> weatherList = this.weatherService.getWeather(areaRequestDTO);
		return weatherList;
	}

	// 지역 선택 단계별 정보를 가져오는 요청 처리
	@PostMapping(value = "/board/weatherStep.do")
	@ResponseBody
	public List<AreaRequestDTO> getAreaStep(@RequestParam Map<String, String> params) {
		// 파라미터로 전달된 정보를 기반으로 지역 선택 단계별 정보를 가져옴
		return this.weatherService.getArea(params);
	}
}
