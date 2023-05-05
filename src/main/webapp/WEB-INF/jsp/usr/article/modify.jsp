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
<script type="text/javascript">
let ArticleModify__submitFormDone = false;
function ArticleModify__submit(form) {
	if (ArticleModify__submitFormDone) {
		return;
	}
	form.title.value = form.title.value.trim();
	if(form.title.value == 0){
		alert('제목을 입력해주세요');
		return;
	}
	const editor = $(form).find('.toast-ui-editor').data('data-toast-editor');
	const markdown = editor.getMarkdown().trim();
	
	if(markdown.length == 0){
		alert('내용 써라');
		editor.focus();
		return;
	}
	form.body.value = markdown;
	
	ArticleModify__submitFormDone = true;
	form.submit();
}
</script>
<h1 style="text-align: center; padding: 70px 20px 0;">${article.id }번
	게시물 수정</h1>

<form style="text-align: center;" method="post" onsubmit="ArticleModify__submit(this); return false;" action="doModify">
	<div  style="display: inline-block;  border: 2px solid black; padding: 17px; text-align:left;">
<div style="text-align: right;">${rq.loginedMember.nickname }</div>
				<input value="${article.id }" type="hidden" name="id" />
		
		<div>
				작성날짜 :
				${article.regDate }
		</div>
		<div>
				제목 :
				<input value="${article.title }" type="text" name="title" placeholder="제목을 입력해주세요" />
		</div>
		<div>
				내용 :
				<textarea type="text" name="body" placeholder="내용을 입력해주세요">${article.body }</textarea>
		</div>
		<button class="btn-text-link btn btn-outline btn-xs" style="display: inline" type="submit"> 수정하기</button>
			</div>
</form>

<%@ include file="../common/foot.jspf"%>
