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

public interface WeatherService
{
    List<WeatherDTO> getWeather(AreaRequestDTO areaRequestDTO) throws UnsupportedEncodingException, URISyntaxException, JsonMappingException, JsonProcessingException;

    List<AreaRequestDTO> getArea(Map<String, String> params);

    AreaRequestDTO getCoordinate(String areacode);
    
    ResponseEntity<WeatherApiResponseDTO> requestWeatherApi(AreaRequestDTO areaRequestDTO) throws UnsupportedEncodingException, URISyntaxException;
}