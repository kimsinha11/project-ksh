package com.KoreaIT.ksh.demo.vo;

import lombok.Data;

@Data
public class WeatherBodyDTO
{
    private String dataType;

    private WeatherItemsDTO items;
}