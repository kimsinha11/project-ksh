package com.KoreaIT.ksh.demo.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.KoreaIT.ksh.demo.vo.AreaRequestDTO;
import com.KoreaIT.ksh.demo.vo.WeatherDTO;

@Mapper
public interface WeatherRepository
{
    List<AreaRequestDTO> selectArea(Map<String, String> params);
    AreaRequestDTO selectCoordinate(String areacode);

    List<WeatherDTO> selectSameCoordinateWeatherList(AreaRequestDTO areaRequestDTO);

    void insertWeatherList(List<WeatherDTO> weatherList);
}
