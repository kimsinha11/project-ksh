<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<c:set var="pageTitle" value="JOIN" />
<%@ include file="../common/head.jspf"%>


<h1 style="text-align: center; padding: 70px 20px 0;">회원가입</h1>


<form style="text-align: center;" method="post" action="doJoin">
		<div style="display: inline-block; border: 2px solid black; padding: 17px; text-align: left;">
				<div>
						아이디 :
						<input class="input input-bordered input-sm w-full max-w-xs" type="text" name="loginId" placeholder="아이디를 입력해주세요" />
				</div>
				<div>
						비밀번호 :
						<input class="input input-bordered input-sm w-full max-w-xs" type="text" name="loginPw" placeholder="비밀번호를 입력해주세요" />
				</div>
				<div>
						이름 :
						<input class="input input-bordered input-sm w-full max-w-xs" type="text" name="name" placeholder="이름을 입력해주세요" />
				</div>
				<div>
						닉네임 :
						<input class="input input-bordered input-sm w-full max-w-xs" type="text" name="nickname" placeholder="닉네임을 입력해주세요" />
				</div>
				<div>
						전화번호 :
						<input class="input input-bordered input-sm w-full max-w-xs" value="" type="text" name="cellphoneNum" placeholder="전화번호를 입력해주세요" />
				</div>
				<div>
						이메일 :
						<input class="input input-bordered input-sm w-full max-w-xs" value="" type="text" name="email" placeholder="이메일을 입력해주세요" />
				</div>

				<br />
				<div style="text-align: center">
						<button class="btn-text-link btn btn-outline btn-xs" style="display: inline;"
								type="submit">회원가입</button>
						<a class="btn-text-link btn btn-outline btn-xs" href="/usr/member/login">로그인</a>
				</div>
		</div>
</form>