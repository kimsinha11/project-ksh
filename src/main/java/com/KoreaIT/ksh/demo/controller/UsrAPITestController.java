package com.KoreaIT.ksh.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller

public class UsrAPITestController {

	@RequestMapping("usr/home/APITest")
	public String APITest() {
		return "usr/home/APITest";
	}
	@RequestMapping("usr/home/APITest2")
	public String APITest2() {
		return "usr/home/APITest2";
	}
	@RequestMapping("usr/home/APITest3")
	public String APITest3() {
		return "usr/home/APITest3";
	}
	@RequestMapping("usr/home/APITest4")
	public String APITest4() {
		return "usr/home/APITest4";
	}

}