
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:set var="pageTitle" value="${board.name}" />
<c:set var="totalCount" value="${totalCount}" />
<c:set var="totalPages" value="${totalPages}" />
<c:set var="pageNum" value="${pageNum}" />
<c:set var="lastPageInGroup" value="${lastPageInGroup}" />

<c:set var="beginPage" value="${(pageNum - 5 > 0) ? pageNum - 5 : 1}" />
<c:set var="endPage" value="${(pageNum + 4 < totalPages) ? pageNum + 4 : totalPages}" />
<c:set var="itemsPerPage" value="${itemsPerPage}" />
<%@ include file="../common/head.jspf"%>

<section class="mt-10 text-xs">
		<div class="mx-auto overflow-x-auto w-full">
				<table class="table-box-type-1 table w-full"
						style="border-collaspe: collaspe; width: 700px;" >
						<thead>

								<tr>
										<div style="text-align: center; font-size: 19px">총게시물 : ${totalCount }개</div>

										<th>
												<label>
														<input type="checkbox" class="checkbox" />
												</label>
										</th>
										<th style="font-size: 19px">번호</th>
										<th style="font-size: 19px">날짜</th>
										<th style="font-size: 19px">제목</th>
										<th style="font-size: 19px">작성자</th>
								</tr>
						</thead>
						<tbody>
								<c:forEach var="article" items="${articles }">
										<tr>
												<th>
														<label>
																<input type="checkbox" class="checkbox" />
														</label>
												</th>
												<th>
														<div class="badge badge-lg">${article.id }</div>
												</th>
												<th>${article.regDate.substring(0,10) }</th>
												<th class="title">
														<a href="detail?id=${article.id }">${article.title }</a>
												</th>
												<th>${article.name }</th>
										</tr>
								</c:forEach>
						</tbody>
				</table>
				<style>
.title:hover {
	background-color: gray;
	color: pink;
}
</style>
		</div>
</section>

<div class="pagination flex justify-center mt-3">
	<c:set var="baseUri2"
		value="itemsPerPage=${itemsPerPage}&searchKeyword=${param.searchKeyword }&searchId=${param.searchId}" />
	<c:if test="${pageNum > 1}">
		<a href="?boardId=${param.boardId}&pageNum=1&${baseUri2 }">◀◀</a>
	</c:if>
	<c:if test="${pageNum > 10}">
		<a class="btn-text-link btn btn-outline btn-xs"
			href="?boardId=${param.boardId}&pageNum=${pageNum - 10}&${baseUri2 }">이전</a>
	</c:if>

	<c:forEach var="i" begin="1" end="${totalPages}" varStatus="status">
		<c:set var="baseUri"
			value="?boardId=${param.boardId}&pageNum=${i}&itemsPerPage=${itemsPerPage}&searchKeyword=${param.searchKeyword }&searchId=${param.searchId}" />

		<c:if
			test="${status.index >= ((pageNum-1) / 10) * 10 && status.index < ((pageNum-1) / 10 + 1) * 10}">
			<c:choose>
				<c:when test="${i == pageNum}">
					<a class="btn-text-link btn btn-outline btn-xs active"
						href="${baseUri }">${i}</a>
				</c:when>
				<c:otherwise>
					<a class="btn-text-link btn btn-outline btn-xs" href="${baseUri }">${i}</a>
				</c:otherwise>
			</c:choose>
		</c:if>
	</c:forEach>
	<c:if test="${pageNum < totalPages && totalPages - pageNum >= 10}">
		<a class="btn-text-link btn btn-outline btn-xs"
			href="?boardId=${article.boardId}&pageNum=${pageNum + 10}&${baseUri2 }">다음</a>
	</c:if>

	<c:if test="${pageNum < totalPages && totalPages - pageNum >= 10}">
		<a href="?boardId=${article.boardId}&pageNum=${totalPages}&${baseUri2 }">▶▶</a>
	</c:if>
</div>

<%@ include file="../common/foot.jspf"%>