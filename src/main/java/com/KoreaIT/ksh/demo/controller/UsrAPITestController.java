package com.KoreaIT.ksh.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.KoreaIT.ksh.demo.util.Ut;
import com.KoreaIT.ksh.demo.vo.Rq;

@Controller

public class UsrAPITestController {
	@Autowired
	private Rq rq;

	@RequestMapping("usr/home/APITest")
	public String APITest() {
		return "usr/home/APITest";
	}

	@RequestMapping("usr/home/APITest2")
	public String APITest2() {
		

		return "usr/home/APITest2";
	}

}