<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/head.jspf"%>
<script>
	let createRoom__submitFormDone = false;

	function createRoom(form) {
		if (createRoom__submitFormDone) {
			return;
		}

		form.name.value = form.name.value.trim();

		if (form.name.value == "") {
			alert('채팅방 이름을 입력해주세요.');
			return;
		}
		alert(form.name.value + "방이 개설되었습니다.");

		createRoom__submitFormDone = true;
		form.submit();
	}
</script>

<div>
		<div class="container">
				<div>
						<c:forEach var="room" items="${list }">
								<ul>
										<li class="list-group-item d-flex justify-content-between align-items-start">
												<div class="ms-2 me-auto">
														<div class="fw-bold">
																<span class="hidden" id="${room.roomName}"></span> <a href="../chat/room?id=${room.id }">[[${room.roomName}]]</a>
														</div>
												</div> 
												<span class="badge bg-primary rounded-pill">${room.userCount}명</span>
										</li>
								</ul>
						</c:forEach>
				</div>
		</div>
		<form action="/usr/chat/createroom" method="post" onsubmit="createRoom(this); return false;">
				<input type="hidden" name="memberId" value=${rq.loginedMemberId }>
				<input style="display: inline-block;" type="text" name="roomName" class="form-control input input-bordered" id="roomName">
				<button class="btn btn-secondary" id="create">개설하기</button>
		</form>
</div>
<%@ include file="../common/foot.jspf"%>