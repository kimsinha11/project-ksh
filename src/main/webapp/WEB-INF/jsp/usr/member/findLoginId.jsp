<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="Find LoginId" />
<%@ include file="../common/head.jspf"%>
<hr />

<script type="text/javascript">
	let MemberFindLoginId__submitFormDone = false;

	function MemberFindLoginId__submit(form) {
		if (MemberFindLoginId__submitFormDone) {
			return;
		}

		form.name.value = form.name.value.trim();
		form.email.value = form.email.value.trim();

		if (form.name.value.length == 0) {
			alert('이름을 입력해주세요');
			form.name.focus();
			return;
		}

		if (form.email.value.length == 0) {
			alert('이메일을 입력해주세요');
			form.email.focus();
			return;
		}

		MemberFindLoginId__submitFormDone = true;
		form.submit();

	}
</script>
<br /><br /><br />
<form style="text-align: center;" method="post" onsubmit="MemberFindLoginId__submit(this); return false;"
		action="../member/doFindLoginId">
		<input type="hidden" name="afterFindLoginIdUri" value="${param.afterFindLoginIdUri }" />
		<div style="display: inline-block; border: 2px solid black; padding: 100px; text-align: left;">


				<div>
						이름 :
						<input class="input input-bordered input-sm w-full max-w-xl" type="text" name="name" autocomplete="off"
								placeholder="이름을 입력해주세요" />
				</div>
				<div>
						이메일 :
						<input class="input input-bordered input-sm w-full max-w-xl" type="text" name="email" autocomplete="off"
								placeholder="이메일을 입력해주세요" autocomplete="off" />
				</div>

				<br />
				<div style="text-align: center">
						<button class="btn-text-link btn btn-outline btn-xs" type="button" onclick="history.back();">뒤로가기</button>
						<button class="btn-text-link btn btn-outline btn-xs" type="submit">아이디 찾기</button>
						<a class="btn-text-link btn btn-outline btn-xs" href="../member/login">로그인</a>
				</div>
		</div>
</form>

<%@ include file="../common/foot.jspf"%>