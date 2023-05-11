package com.KoreaIT.ksh.demo.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.KoreaIT.ksh.demo.vo.Camping;
import com.KoreaIT.ksh.demo.vo.CampingData;

@Controller
public class UsrCampingController {

	 @GetMapping("/api")
	   public String index(){
	       return "usr/camping/index";
	   }

	 @RequestMapping("/api")
	    public String load_save(@RequestParam("글램핑") String 글램핑, Model model) {
	        List<CampingData> campingDataList = new ArrayList<>();

	        try {
	            String requestDate = 글램핑;
	            URL url = new URL("https://api.odcloud.kr/api/15037499/v1/uddi:1fe51f51-e956-425b-a9e3-e4555cb57880?page=3&perPage=30&returnType=XML&serviceKey=data-portal-test-key" +
	                    requestDate);
	            BufferedReader bf;
	            bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
	            String result = bf.readLine();

	            JSONParser jsonParser = new JSONParser();
	            JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
	            JSONObject CardSubwayStatsNew = (JSONObject) jsonObject.get("CardSubwayStatsNew");
	            Long totalCount = (Long) CardSubwayStatsNew.get("list_total_count");

	            JSONObject subResult = (JSONObject) CardSubwayStatsNew.get("RESULT");
	            JSONArray infoArr = (JSONArray) CardSubwayStatsNew.get("row");

	            for (int i = 0; i < infoArr.size(); i++) {
	                JSONObject tmp = (JSONObject) infoArr.get(i);
	                CampingData campingData = new CampingData(
	                	    0,
	                	    Arrays.asList(new Camping(
	                	        (String) tmp.get("캠핑장명"),
	                	        (String) tmp.get("캠핑장구분"),
	                	        (String) tmp.get("위도"),
	                	        (String) tmp.get("경도"),
	                	        (String) tmp.get("주소"),
	                	        (String) tmp.get("일반야영장수"),
	                	        (String) tmp.get("자동차야영장"),
	                	        (String) tmp.get("글램핑"),
	                	        (String) tmp.get("카라반"),
	                	        (String) tmp.get("화장실"),
	                	        (String) tmp.get("샤워실"),
	                	        (String) tmp.get("개수대"),
	                	        (String) tmp.get("소화기"),
	                	        (String) tmp.get("방화수"),
	                	        (String) tmp.get("방화사"),
	                	        (String) tmp.get("화재감지기"),
	                	        (String) tmp.get("기타부대시설 1"),
	                	        (String) tmp.get("기타부대시설 2"),
	                	        (String) tmp.get("데이터기준일자"),
	                	        (String) tmp.get("인허가일자")
	                	     
	                	    )),
	                	    0,0,0,0
	                	);
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        model.addAttribute("campingDataList", campingDataList);
	        return "redirect:/findname";
	    }
	}
