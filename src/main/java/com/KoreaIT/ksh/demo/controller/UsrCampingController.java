package com.KoreaIT.ksh.demo.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.KoreaIT.ksh.demo.service.LikeButtonService;
import com.KoreaIT.ksh.demo.service.ReactionPointService;
import com.KoreaIT.ksh.demo.vo.ResultData;
import com.KoreaIT.ksh.demo.vo.Rq;

@Controller
public class UsrCampingController {
	@Autowired
	Rq rq;
	@Autowired
	private LikeButtonService likeButtonService;

	@GetMapping("/usr/camping/list")
	public String index(Model model, @RequestParam(required = false, defaultValue = "") String searchKeyword,
	@RequestParam(required = false, defaultValue = "0") int searchType,
	@RequestParam(required = false, defaultValue = "1") int pageNo,
	@RequestParam(required = false, defaultValue = "10") int pageSize) {

		List<String[]> data = new ArrayList<>();
        // data: String 배열을 담는 ArrayList로 파일에서 읽어온 데이터를 저장
		try (InputStream inputStream = getClass().getResourceAsStream("/camping/camping.csv");
				// 현재 클래스의 클래스 로더를 통해 "/camping/camping.csv" 파일의 입력 스트림을 가져옴
				// BufferedReader: 입력 스트림을 BufferedReader로 감싸서 효율적인 파일 읽기를 수행
		        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		        String[] row = line.split(",");
		        data.add(row);
		    } // 파일에서 한 줄씩 읽음. 파일의 끝까지 읽으면 null이 반환
		} catch (IOException e) {
		    e.printStackTrace();
		}

		List<String[]> filteredData = new ArrayList<>();

		// 검색어에 따라 데이터 필터링
		if (!searchKeyword.isEmpty()) {
		    // 만약 검색어가 비어있지 않다면, 데이터를 필터링
		    for (String[] row : data) {

		        String searchTarget;
		        if (searchType == 0) {
		            searchTarget = row[3]; // 주소 정보
		        } else {
		            searchTarget = row[2]; // 종류 정보
		        }
		        // 검색 대상(searchTarget)에 검색어(searchKeyword)가 포함되어 있다면,
		        // 해당 데이터(row)를 필터링된 데이터(filteredData)에 추가
		        if (searchTarget.contains(searchKeyword)) {
		            filteredData.add(row);
		        }
		    }
		} else {
		    // 검색어가 비어있다면, 데이터를 필터링하지 않고 모든 데이터를 유지
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
		
		ResultData<Integer> actorCanMakeReactionRd = likeButtonService.actorCanMakeReaction(rq.getLoginedMemberId(), "id",id);
		model.addAttribute("id", id);
		model.addAttribute("data", data);
		model.addAttribute("memberId", rq.getLoginedMemberId());
		model.addAttribute("actorCanMakeReactionRd", actorCanMakeReactionRd);
		model.addAttribute("isAlreadyAddGoodRp", likeButtonService.isAlreadyAddGoodRp(id, "camping"));
		if (actorCanMakeReactionRd.isSuccess()) {
			model.addAttribute("actorCanMakeReaction", actorCanMakeReactionRd.isSuccess());
		}

		if (actorCanMakeReactionRd.getResultCode().equals("F-1")) {
			int sumReactionPointByMemberId = (int) actorCanMakeReactionRd.getData1();

			if (sumReactionPointByMemberId > 0) {
				model.addAttribute("actorCanCancelGoodReaction", true);
			}
		}
		return "usr/camping/detail";
	}

	@RequestMapping("usr/camping/map")
	public String map(Model model, String searchKeyword) {

		 model.addAttribute("searchKeyword", searchKeyword);
		return "usr/camping/map";
	}
}