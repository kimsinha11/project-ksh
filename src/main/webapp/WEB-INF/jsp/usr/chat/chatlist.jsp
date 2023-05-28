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

		<form action="/usr/chat/createroom" method="post" onsubmit="createRoom(this); return false;" style="padding: 20px;">
				<div style="display: inline-block; margin-right:490px;">😊즐거운 소통을 위해 채팅예절을 준수해주세요😊</div>
				<input type="hidden" name="memberId" value=${rq.loginedMemberId }>
				<input style="display: inline-block;" type="text" name="roomName" class="form-control input input-bordered"
						id="roomName">
				<button class="btn btn-secondary" id="create">개설하기 </button>
		</form>
		<hr /><br />
		<div class="container" style="border: 2px solid black;">
				<div>
						<c:forEach var="room" items="${list }">
								<ul>
										<li class="list-group-item d-flex justify-content-between align-items-start">
												<span class="badge bg-primary rounded-pill" style="display: inline;">${room.userCount}명</span>
												<div class="ms-2 me-auto">
														<div class="fw-bold">
																<span class="hidden" id="${room.roomName}"></span>
																<a style="padding: 20px; display: block;" href="../chat/room?id=${room.id }">${room.roomName} 👉
																		참여하기</a>
														</div>
												</div>

										</li>
								</ul>
						</c:forEach>
				</div>
		</div>

</div>
<%@ include file="../common/foot.jspf"%>