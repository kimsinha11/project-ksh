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
		<div class="card">
				<c:forEach var="article" items="${articles}">
						<div class="card-header">
								<img class="w-full rounded-xl" src="${rq.getImgUri(article.id)}"
														onerror="${rq.profileFallbackImgOnErrorHtml}" alt="" />
						</div>
				</c:forEach>
				<div class="card-body">
						<c:forEach var="article" items="${articles}">
								<span class="tag tag-teal">${board.name}</span>
								<th class="title">
										<div class="title-text">
												<a href="detail?id=${article.id}&boardId=${article.boardId}">제목 : ${article.title}</a>
										</div>
										<div class="comment-count" style="color: red; font-size: 13px;">
												<c:forEach var="commentsCount" items="${commentsCount}">
														<c:if test="${commentsCount.id == article.id}"> (${commentsCount.count})  </c:if>
												</c:forEach>
										</div>
								</th>
								<th>내용 : ${article.body }</th>
								<div class="user">

										<div class="user-info">
												<div>${article.name}</div>
												<small> ${article.regDate.substring(0,10) }</small>
										</div>
								</div>
						</c:forEach>
				</div>
		</div>

		<style>
@import
	url("https://fonts.googleapis.com/css2?family=Roboto&display=swap");

* {
	box-sizing: border-box;
}

body {
	justify-content: center;
	align-items: center;
	margin: 0;
}

.container {
	display: flex;
	width: 1040px;
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