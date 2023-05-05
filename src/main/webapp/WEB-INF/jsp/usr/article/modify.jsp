<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.KoreaIT.ksh.demo.vo.Article"%>

<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<c:set var="pageTitle" value="ARTICLE MODIFY" />
<%@ include file="../common/head.jspf"%>
<%
Article article = (Article) request.getAttribute("article");
%>
<h1 style="text-align: center; padding: 70px 20px 0;">${article.id }번게시물 수정</h1>

<form style="text-align: center;" method="post" action="doModify">
		<div style="display: inline-block; border: 2px solid black; padding: 17px; text-align: left;">

				<input value="${article.id }" type="hidden" name="id" />

				<div>작성날짜 : ${article.regDate }</div>
				<div>
						제목 :
						<input value="${article.title }" type="text" name="title" placeholder="제목을 입력해주세요" />
				</div>
				<div>
						내용 :
						<textarea type="text" name="body" placeholder="내용을 입력해주세요">${article.body }</textarea>
				</div>
				<button type="submit">수정</button>
		</div>
</form>

<%@ include file="../common/foot.jspf"%>
