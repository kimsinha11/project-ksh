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


<table class="table-box-type-3 table w-full" style="width: 100px;">
		<thead>
				<tr>
						<th>번호</th>
						<th>캠핑(야영)장명</th>
						<th>지역</th>
				</tr>
		</thead>
		<tbody>
				<%
				for (String[] row : data) {
				%>
				<tr>
						<th><%=row[0]%></th>
						<th class="title">
							<div class="title-text">
								<a href="detail?id=<%=row[0]%>"><%=row[1]%></a>
							</div>
						</th>
						<th><%=row[3]%></th>
				</tr>
				<%
				}
				%>

		</tbody>
</table>

<%@ include file="../common/foot.jspf"%>