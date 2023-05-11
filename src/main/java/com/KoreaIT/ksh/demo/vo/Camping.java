package com.KoreaIT.ksh.demo.vo;


import javax.persistence.Entity;

import lombok.Data;

@Data
@Entity
public class Camping {

    private String 캠핑장명;
    private String 캠핑장구분;
    private String 위도;
    private String 경도;
    private String 주소;
    private String 일반야영장수;
    private String 자동차야영장;
    private String 글램핑;
    private String 카라반;
    private String 화장실;
    private String 샤워실;
    private String 개수대;
    private String 소화기;
    private String 방화수;
    private String 방화사;
    private String 화재감지기;
    private String 기타부대시설1;
    private String 기타부대시설2;
    private String 데이터기준일자;
    private String 인허가일자;
    
    public Camping(String 캠핑장명, String 캠핑장구분, String 위도, String 경도, String 주소, String 일반야영장수, String 자동차야영장,
            String 글램핑, String 카라반, String 화장실, String 샤워실, String 개수대, String 소화기, String 방화수,
            String 방화사, String 화재감지기, String 기타부대시설1, String 기타부대시설2, String 데이터기준일자, String 인허가일자) {
     
        this.캠핑장명 = 캠핑장명;
        this.캠핑장구분 = 캠핑장구분;
        this.위도 = 위도;
        this.경도 = 경도;
        this.주소 = 주소;
        this.일반야영장수 = 일반야영장수;
        this.자동차야영장 = 자동차야영장;
        this.글램핑 = 글램핑;
        this.카라반 = 카라반;
        this.화장실 = 화장실;
        this.샤워실 = 샤워실;
        this.개수대 = 개수대;
        this.소화기 = 소화기;
        this.방화수 = 방화수;
        this.방화사 = 방화사;
        this.화재감지기 = 화재감지기;
        this.기타부대시설1 = 기타부대시설1;
        this.기타부대시설2 = 기타부대시설2;
        this.데이터기준일자 = 데이터기준일자;
        this.인허가일자 = 인허가일자;
    }

    // getters and setters
}