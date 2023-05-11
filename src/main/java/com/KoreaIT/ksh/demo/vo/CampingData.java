package com.KoreaIT.ksh.demo.vo;


import java.util.List;

import javax.persistence.Entity;

import lombok.Data;

@Data
@Entity
public class CampingData {
    private int page;
    private int perPage;
    private int totalCount;
    private int currentCount;
    private int matchCount;
    private List<Camping> data;

    public CampingData(int currentCount, List<Camping> data, int matchCount, int page, int perPage, int totalCount) {
        this.page = page;
        this.perPage = perPage;
        this.totalCount = totalCount;
        this.currentCount = currentCount;
        this.matchCount = matchCount;
        this.data = data;
    }

    // Getters and setters
}