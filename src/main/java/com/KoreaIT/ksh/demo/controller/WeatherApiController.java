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
public class WeatherApiController
{
	@Autowired
    private WeatherService weatherService;
	
    @GetMapping("/usr/camping/weather")
    public String openWeatherPage()
    {
        return "/usr/camping/weather";
    }
    
    @PostMapping(value = "/board/getWeather.do")
    @ResponseBody
    public List<WeatherDTO> getWeatherInfo(@ModelAttribute AreaRequestDTO areaRequestDTO) throws JsonMappingException, JsonProcessingException, UnsupportedEncodingException, URISyntaxException
    {
        AreaRequestDTO coordinate = this.weatherService.getCoordinate(areaRequestDTO.getAreacode());
        areaRequestDTO.setNx(coordinate.getNx());
        areaRequestDTO.setNy(coordinate.getNy());

        List<WeatherDTO> weatherList = this.weatherService.getWeather(areaRequestDTO);
        return weatherList;
    }
    
    
    @PostMapping(value = "/board/weatherStep.do")
    @ResponseBody
    public List<AreaRequestDTO> getAreaStep(@RequestParam Map<String, String> params)
    {
        return this.weatherService.getArea(params);
    }
}



    
   