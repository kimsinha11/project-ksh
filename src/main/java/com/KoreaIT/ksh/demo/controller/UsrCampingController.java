package com.KoreaIT.ksh.demo.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UsrCampingController {

	@GetMapping("/usr/camping/list")
	public String index(Model model,
	                    @RequestParam(required = false, defaultValue = "") String searchKeyword,
	                    @RequestParam(required = false, defaultValue = "0") int searchType) {

	    List<String[]> data = new ArrayList<>();

	    try (InputStream inputStream = getClass().getResourceAsStream("/camping/camping.csv");
	         BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
	        String line;
	        while ((line = br.readLine()) != null) {
	            String[] row = line.split(",");
	            data.add(row);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    List<String[]> filteredData = new ArrayList<>();

	    if (!searchKeyword.isEmpty()) {
	        for (String[] row : data) {
	            String searchTarget = row[4]; // 주소 정보
	            if (searchTarget.contains(searchKeyword)) {
	                filteredData.add(row);
	            }
	        }
	    } else {
	        filteredData = data;
	    }

	    model.addAttribute("data", filteredData);
	    model.addAttribute("searchKeyword", searchKeyword);
	    model.addAttribute("searchType", searchType);

	    return "usr/camping/list";
	}

}