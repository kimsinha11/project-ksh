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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UsrCampingController {

	@GetMapping("/usr/camping/list")
	public String index(Model model, @RequestParam(required = false, defaultValue = "") String searchKeyword,
	@RequestParam(required = false, defaultValue = "0") int searchType,
	@RequestParam(required = false, defaultValue = "1") int pageNo,
	@RequestParam(required = false, defaultValue = "10") int pageSize) {
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
		        String searchTarget;
		        if (searchType == 0) {
		            searchTarget = row[3]; // 주소 정보
		        } else {
		            searchTarget = row[2]; // 종류 정보
		        }
		        if (searchTarget.contains(searchKeyword)) {
		            filteredData.add(row);
		        }
		    }
		} else {
		    filteredData = data;
		}

		// 페이지네이션 처리
		int totalCount = filteredData.size();
		int startIdx = (pageNo - 1) * pageSize;
		int endIdx = Math.min(startIdx + pageSize, totalCount);
		List<String[]> paginatedData = filteredData.subList(startIdx, endIdx);

		model.addAttribute("data", paginatedData);
		model.addAttribute("searchKeyword", searchKeyword);
		model.addAttribute("searchType", searchType);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("pageSize", pageSize);

		return "usr/camping/list";

	}

	@GetMapping("/usr/camping/detail")
	public String detail(Model model, String id) {

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
		model.addAttribute("id", id);
		model.addAttribute("data", data);
		return "usr/camping/detail";
	}

	@RequestMapping("usr/camping/map")
	public String map() {
		return "usr/camping/map";
	}
}