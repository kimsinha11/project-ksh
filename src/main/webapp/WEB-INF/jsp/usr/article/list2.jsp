<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:set var="pageTitle" value="전체 게시물" />
<c:if test="${board!=null }">
		<c:set var="pageTitle" value="${board.name}" />
</c:if>
<%@ page import="com.KoreaIT.ksh.demo.vo.Article"%>
<%@ page import="com.KoreaIT.ksh.demo.vo.Board"%>
<%@ page import="java.util.List"%>
<c:set var="totalCount" value="${totalCount}" />
<c:set var="totalPages" value="${totalPages}" />
<c:set var="pageNum" value="${pageNum}" />
<c:set var="lastPageInGroup" value="${lastPageInGroup}" />
<c:set var="beginPage" value="${(pageNum - 5 > 0) ? pageNum - 5 : 1}" />
<c:set var="endPage" value="${(pageNum + 4 < totalPages) ? pageNum + 4 : totalPages}" />
<c:set var="itemsPerPage" value="${itemsPerPage}" />
<%
List<Article> commentsCount = (List<Article>) request.getAttribute("commentsCount");
Board board = (Board) request.getAttribute("board");
%>
<%@ include file="../common/head.jspf"%>
<br />
<c:if test="${board!=null }">
		<label>🔥${board.name}🔥</label>
</c:if>
<c:if test="${board==null }">
		<label>🔥전체 게시물🔥</label>
</c:if>

<div class="container">
		<c:forEach var="article" items="${articles}">
				<div class="card">

						<div class="card-header">
								<img class="w-full rounded-xl" src="${rq.getImgUri(article.id)}" onerror="${rq.profileFallbackImgOnErrorHtml}"
										alt="" />
						</div>

						<div class="card-body">

								<span class="tag tag-teal">${board.name}</span>
								<div class="title">
										<div class="title-text">
												<a href="detail?id=${article.id}&boardId=${article.boardId}">제목 : ${article.title}</a>
										</div>
								</div>
								<div>내용 : <c:if test="${article.body.length()> 15}">
																${article.body.substring(0, 15)}
																</c:if>
																		<c:if test="${article.body.length()<15}">
																${article.body }
																</c:if></div>
								<div class="user">

										<div class="user-info">
												<div>${article.name}</div>
												<small> ${article.regDate.substring(0,10) }</small>
												<div>
												<small style="color: red;">좋아요 : ${article.goodReactionPoint}</small>
												<small style="color: blue;">
														
																<c:forEach var="commentsCount" items="${commentsCount}">
																		<c:if test="${commentsCount.id == article.id}"> 댓글 : ${commentsCount.count}  </c:if>
																</c:forEach>
														
												</small>
												</div>
										</div>
								</div>

						</div>
				</div>
		</c:forEach>
</div>
<div class="pagination flex justify-center mt-3">
		<c:set var="baseUri2"
				value="itemsPerPage=${itemsPerPage}&searchKeyword=${param.searchKeyword }&searchId=${param.searchId}" />
		<c:if test="${pageNum > 1}">
				<a class="btn-text-link btn btn-outline btn-sm" href="?boardId=${article.boardId}&pageNum=1&${baseUri2 }">◀◀</a>
		</c:if>
		<c:if test="${pageNum > 10}">
				<a class="btn-text-link btn btn-outline btn-sm"
						href="?boardId=${param.boardId}&pageNum=${pageNum - 10}&${baseUri2 }">이전</a>
		</c:if>

		<c:forEach var="i" begin="1" end="${totalPages}" varStatus="status">
				<c:set var="baseUri"
						value="?boardId=${param.boardId}&pageNum=${i}&itemsPerPage=${itemsPerPage}&searchKeyword=${param.searchKeyword }&searchId=${param.searchId}" />

				<c:if test="${status.index >= ((pageNum-1) / 10) * 10 && status.index < ((pageNum-1) / 10 + 1) * 10}">
						<c:choose>
								<c:when test="${i == pageNum}">
										<a class="btn-text-link btn btn-outline btn-sm active" href="${baseUri }">${i}</a>
								</c:when>
								<c:otherwise>
										<a class="btn-text-link btn btn-outline btn-sm" href="${baseUri }">${i}</a>
								</c:otherwise>
						</c:choose>
				</c:if>
		</c:forEach>
		<c:if test="${pageNum < totalPages && totalPages - pageNum >= 10}">
				<a class="btn-text-link btn btn-outline btn-sm"
						href="?boardId=${param.boardId}&pageNum=${pageNum + 10}&${baseUri2 }">다음</a>
		</c:if>

		<c:if test="${pageNum < totalPages && totalPages - pageNum >= 10}">
				<a class="btn-text-link btn btn-outline btn-sm" href="?boardId=${param.boardId}&pageNum=${totalPages}&${baseUri2 }">▶▶</a>
		</c:if>
</div>
<br />
<form style="text-align: center;" method="get" action="list">
		<div>
				<select data-value="${param.searchId}" name="searchId" class="select select-bordered max-w-sm">
						<option disabled selected>선택</option>
						<option value="1">제목</option>
						<option value="2">내용</option>
						<option value="3">제목+내용</option>
				</select>
				<input type="hidden" name="boardId" value="${param.boardId}" />
				<input value="${param.searchKeyword }" class="input input-bordered w-full max-w-sm" type="text" name="searchKeyword"
						placeholder="검색어를 입력해주세요" />
				<button class="btn-text-link btn btn-outline btn-xl" style="display: inline;" type="submit">검색</button>
		</div>

		<script>
			const searchSelect = document.getElementsByName("searchId")[0];
			const searchdInput = document.getElementsByName("searchId")[0];
			searchSelect.onchange = function() {
				var selectedValue = searchSelect.value;
				searchdInput.value = selectedValue;
			}
		</script>


		<!-- pageNum과 itemsPerPage 파라미터는 제거하거나 기본값 설정 -->
</form>
<style>
@import
	url("https://fonts.googleapis.com/css2?family=Roboto&display=swap");

* {
	box-sizing: border-box;
}

.container {
	display: flex;
	width: 100%;
	justify-content: space-evenly;
	flex-wrap: wrap;
}

.card {
	margin: 10px;
	background-color: #fff;
	border-radius: 10px;
	box-shadow: 0 2px 20px rgba(0, 0, 0, 0.2);
	overflow: hidden;
	width: 300px;
}

.card-header img {
	width: 100%;
	height: 200px;
	object-fit: cover;
}

.card-body {
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: flex-start;
	padding: 20px;
	min-height: 250px;
}

.tag {
	background: #cccccc;
	border-radius: 50px;
	font-size: 12px;
	margin: 0;
	color: #fff;
	padding: 2px 10px;
	text-transform: uppercase;
	cursor: pointer;
}

.tag-teal {
	background-color: #47bcd4;
}

.tag-purple {
	background-color: #5e76bf;
}

.tag-pink {
	background-color: #cd5b9f;
}

.card-body p {
	font-size: 13px;
	margin: 0 0 40px;
}

.user {
	display: flex;
	margin-top: auto;
}

.user img {
	border-radius: 50%;
	width: 40px;
	height: 40px;
	margin-right: 10px;
}

.user-info h5 {
	margin: 0;
}

.user-info small {
	color: #545d7a;
}
</style>
<%@ include file="../common/foot.jspf"%>