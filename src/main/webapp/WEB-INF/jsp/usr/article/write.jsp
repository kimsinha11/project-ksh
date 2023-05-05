<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.KoreaIT.ksh.demo.vo.Article"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<c:set var="pageTitle" value="ARTICLE WRITE" />
<%@ include file="../common/head.jspf"%>

<%
Article article = (Article) request.getAttribute("article");
%>

<h1 style="text-align: center; padding: 70px 20px 0;">글작성</h1>


<form style="text-align: center;" method="post" action="doWrite">
<div  style="display: inline-block;  border: 2px solid black; padding: 50px; text-align:left;">
	<div>
		제목 : <input value="${article.title }" class="input input-bordered w-full max-w-xs" type="text" name="title"
			placeholder="제목을 입력해주세요" />
	</div>
	<div>
		내용 :
		<textarea type="text" class="input input-bordered w-full max-w-xs" placeholder="내용을 입력해주세요" name="body" /></textarea>
	</div>


	<button class="btn-text-link btn btn-outline btn-xs" style="display: inline;" type="submit"> 작성하기</button>
	
</div>
</form>