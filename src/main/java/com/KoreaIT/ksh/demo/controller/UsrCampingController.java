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

@Controller
public class UsrCampingController {

	@GetMapping("/usr/camping/list")
	public String index(Model model) {
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

	    model.addAttribute("data", data);
	    return "usr/camping/list";
	}
}
