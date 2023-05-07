<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.KoreaIT.ksh.demo.vo.Reply"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<c:set var="pageTitle" value="댓글 수정" />
<%@ include file="../common/head.jspf"%>
<%
Reply reply = (Reply) request.getAttribute("reply");
%>

<h1 style="text-align: center; padding: 70px 20px 0;">${comment.id }번
	댓글 수정</h1>

<script type="text/javascript">
	let ReplyModify__submitFormDone = false;
	function ReplyModify__submitForm(form) {
		if (ReplyModify__submitFormDone) {
			return;
		}
		form.body.value = form.body.value.trim();
		if (form.body.value.length < 3) {
			alert('3글자 이상 입력하세요');
			form.body.focus();
			return;
		}
		ReplyWrite__submitFormDone = true;
		form.submit();
	}
</script>
<form style="text-align: center;" method="post" onsubmit="ReplyModify__submitForm(this); return false" action="doModify">
<div  style="display: inline-block;  border: 2px solid black; padding: 17px; text-align:left;">
<div style="display: none;">

	<div>
		번호 : <input value="${reply.id }" class="input input-bordered w-full max-w-xs"  type="hidden" name="id"
			/>
		게시물번호 : <input value="${reply.relId }" class="input input-bordered w-full max-w-xs"  type="hidden" name="relId"
			/>
	</div>
	</div>
	<div>작성자 : ${reply.name }</div>
	<div>작성날짜 : ${reply.regDate }</div>
	<div>
		내용 :
		<textarea type="text" class="input input-bordered w-full max-w-xs"  placeholder="내용을 입력해주세요" name="body" /></textarea>
	</div>

	<button class="btn-text-link btn btn-outline btn-xs" style="display: inline" type="submit"> 수정하기</button>
		</div>
</form>
<%@ include file="../common/foot.jspf"%>