<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<c:set var="pageTitle" value="캠핑장 검색" />
<%@ include file="../common/head.jspf"%>
<%@ include file="../common/toastUiEditorLib.jspf"%>

<%
List<String[]> data = (List<String[]>) request.getAttribute("data");
int searchType = (int) request.getAttribute("searchType");
String searchKeyword = (String) request.getAttribute("searchKeyword");
int totalCount = (int) request.getAttribute("totalCount");
int pageNo = (int) request.getAttribute("pageNo");
int pageSize = (int) request.getAttribute("pageSize");
%>
<meta charset="UTF-8">
<h1>Camping List</h1>
<div>
		<form class="flex">
				<select name="searchType" class="select select-bordered" style="width: 150px;">
						<option value="0" ${searchType == 0 ? 'selected' : ''}>지역</option>
						<option value="1" ${searchType == 1 ? 'selected' : ''}>종류</option>
				</select>
				<input name="searchKeyword" type="text" class="ml-2 w-96 input input-borderd" placeholder="검색어를 입력해주세요"
						maxlength="20" value="${searchKeyword}" />
				<button type="submit" class="ml-2 btn btn-ghost">검색</button>
				<a class="ml-2 btn btn-ghost" href="/usr/camping/map" type="button">지도</a>
		</form>
</div>

<table class="table-box-type-3 table w-full" style="width: 100%">
		<thead>
				<tr>
						<th>번호</th>
						<th>캠핑(야영)장명</th>
						<th>지역</th>
						<th>지도</th>
				</tr>
		</thead>
		<tbody>
				<c:forEach items="${data}" var="row">
						<tr>
								<th>${row[0]}</th>
								<th class="title">
										<div class="title-text">
												<a href="detail?id=${row[0]}">${row[1]}</a>
										</div>
								</th>
								<th>${row[3]}</th>
								<th class="map">
										<div class="map-text">
												<a href="map?searchKeyword=${row[1]}">지도</a>
										</div>
								</th>
						</tr>
				</c:forEach>
		</tbody>
</table>
<div class="pagination">
		<c:choose>
				<c:when test="${totalCount > pageSize}">
						<c:set var="maxPage" value="${(totalCount + pageSize - 1) / pageSize}" />
						<c:set var="startPage" value="${((pageNo - 1) / 10 * 10) + 1}" />
						<c:set var="endPage" value="${startPage + 9}" />
						<c:if test="${startPage > 6}">
								<a class="btn-text-link btn btn-outline btn-xl"
										href="?searchType=${searchType}&searchKeyword=${searchKeyword}&pageNo=1">처음</a>
						</c:if>
						<c:if test="${startPage > 1}">
								<a class="btn-text-link btn btn-outline btn-xl"
										href="?searchType=${searchType}&searchKeyword=${searchKeyword}&pageNo=${(startPage - 1).intValue()}">이전</a>
						</c:if>

						<c:forEach begin="${startPage}" end="${endPage}" varStatus="loop">
								<c:choose>
										<c:when test="${loop.index <= maxPage}">
												<c:if test="${loop.index == pageNo}">
														<strong class="btn-text-link btn btn-outline btn-xl active">${loop.index}</strong>
												</c:if>
												<c:if test="${loop.index != pageNo}">
														<a class="btn-text-link btn btn-outline btn-xl "
																href="?searchType=${searchType}&searchKeyword=${searchKeyword}&pageNo=${loop.index}">${loop.index}</a>
												</c:if>
										</c:when>
								</c:choose>
						</c:forEach>
						<c:if test="${endPage < maxPage}">
								<a class="btn-text-link btn btn-outline btn-xl"
										href="?searchType=${searchType}&searchKeyword=${searchKeyword}&pageNo=${(endPage + 1).intValue()}">다음</a>
						</c:if>
						<c:if test="${pageNo < maxPage && maxPage - pageNo >= 10}">
								<a class="btn-text-link btn btn-outline btn-xl"
										href="?searchType=${searchType}&searchKeyword=${searchKeyword}&pageNo=${(maxPage).intValue()}">마지막</a>
						</c:if>


				</c:when>
				<c:otherwise>
						<a href="?searchType=${searchType}&searchKeyword=${searchKeyword}&pageNo=1">1</a>
				</c:otherwise>
		</c:choose>
</div>









<%@ include file="../common/foot.jspf"%>
