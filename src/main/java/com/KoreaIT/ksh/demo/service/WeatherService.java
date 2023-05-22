package com.KoreaIT.ksh.demo.service;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.KoreaIT.ksh.demo.vo.AreaRequestDTO;
import com.KoreaIT.ksh.demo.vo.WeatherApiResponseDTO;
import com.KoreaIT.ksh.demo.vo.WeatherDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface WeatherService {
    /**
     * 지역 정보에 해당하는 날씨 정보를 가져옵니다.
     *
     * @param areaRequestDTO 지역 요청 정보
     * @return 날씨 정보 리스트
     * @throws UnsupportedEncodingException 인코딩 예외
     * @throws URISyntaxException         URI 구문 예외
     * @throws JsonMappingException       JSON 매핑 예외
     * @throws JsonProcessingException    JSON 처리 예외
     */
    List<WeatherDTO> getWeather(AreaRequestDTO areaRequestDTO) throws UnsupportedEncodingException, URISyntaxException, JsonMappingException, JsonProcessingException;

    /**
     * 지역 정보를 가져옵니다.
     *
     * @param params 파라미터 맵
     * @return 지역 요청 정보 리스트
     */
    List<AreaRequestDTO> getArea(Map<String, String> params);

    /**
     * 지역 코드에 해당하는 좌표 정보를 가져옵니다.
     *
     * @param areacode 지역 코드
     * @return 좌표 정보
     */
    AreaRequestDTO getCoordinate(String areacode);

    /**
     * 날씨 API에 요청을 보내고 응답을 받아옵니다.
     *
     * @param areaRequestDTO 지역 요청 정보
     * @return API 응답
     * @throws UnsupportedEncodingException 인코딩 예외
     * @throws URISyntaxException         URI 구문 예외
     */
    ResponseEntity<WeatherApiResponseDTO> requestWeatherApi(AreaRequestDTO areaRequestDTO) throws UnsupportedEncodingException, URISyntaxException;
}
