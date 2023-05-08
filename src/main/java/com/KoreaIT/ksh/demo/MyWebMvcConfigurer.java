package com.KoreaIT.ksh.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.KoreaIT.ksh.demo.interceptor.BeforeActionInterceptor;
import com.KoreaIT.ksh.demo.interceptor.NeedLogOutInterceptor;
import com.KoreaIT.ksh.demo.interceptor.NeedLoginInterceptor;

@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {
	// BeforeActionInterceptor 불러오기
	@Autowired
	BeforeActionInterceptor beforeActionInterceptor;
	// NeedLoginInterceptor 불러오기
	@Autowired
	NeedLoginInterceptor needLoginInterceptor;
	// NeedLogOutInterceptor 불러오기
	@Autowired
	NeedLogOutInterceptor needLogOutInterceptor;

	// /resource/common.css
	// 인터셉터 적용
	public void addInterceptors(InterceptorRegistry registry) {
		InterceptorRegistration ir;
		
		ir = registry.addInterceptor(beforeActionInterceptor);
		ir.addPathPatterns("/**");
		ir.addPathPatterns("/favicon.ico");
		ir.excludePathPatterns("/resource/**");
		ir.excludePathPatterns("/error");
		
		ir = registry.addInterceptor(needLoginInterceptor);
		ir.addPathPatterns("/usr/article/write");
		ir.addPathPatterns("/usr/article/doWrtie");
		ir.addPathPatterns("/usr/article/delete");
		ir.addPathPatterns("/usr/article/modify");
		ir.addPathPatterns("/usr/article/doModify");
		
		
		ir.addPathPatterns("/usr/comment/docWrtie");
		ir.addPathPatterns("/usr/comment/cdelete");
		ir.addPathPatterns("/usr/comment/cmodify");
		ir.addPathPatterns("/usr/comment/docModify");
		
		ir.addPathPatterns("/usr/reactionPoint/deleteGoodReactionPoint");
		ir.addPathPatterns("/usr/reactionPoint/deleteBadReactionPoint");
		ir.addPathPatterns("/usr/reactionPoint/doGoodReaction");
		ir.addPathPatterns("/usr/reactionPoint/doBadReaction");
		
		ir = registry.addInterceptor(needLogOutInterceptor);
		ir.addPathPatterns("/usr/member/login");
		ir.addPathPatterns("/usr/member/doLogin");
		ir.addPathPatterns("/usr/member/join");
		ir.addPathPatterns("/usr/member/doJoin");
		ir.addPathPatterns("/usr/member/findLoginId");
		ir.addPathPatterns("/usr/member/dofindLoginId");
		ir.addPathPatterns("/usr/member/findLoginPw");
		ir.addPathPatterns("/usr/member/dofindLoginPw");
	}

}