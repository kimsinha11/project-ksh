package com.KoreaIT.ksh.demo.vo;

import lombok.Data;

@Data
public class WeatherResponseDTO
{
    private WeatherHeaderDTO header;

    private WeatherBodyDTO   body;
}