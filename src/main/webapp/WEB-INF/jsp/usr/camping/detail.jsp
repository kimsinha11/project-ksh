<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<c:set var="pageTitle" value="CAMPING" />
<%@ include file="../common/head.jspf"%>
<%@ include file="../common/toastUiEditorLib.jspf"%>
<%@ page import="java.util.stream.Collectors"%>
<%@ page import="java.util.Arrays"%>
<%
List<String[]> data = (List<String[]>) request.getAttribute("data");
%>
<meta charset="UTF-8">
<h1>Detail</h1>
<section class="mt-10 text-xl">
		<div class="mx-auto overflow-x-auto">
				<table class="table w-full table-box-type-1" style="width: 700px; height: 300px;">

						<thead>
								<%
								// 전달받은 id
								String id = request.getParameter("id");

								// id와 일치하는 열 필터링
								List<String[]> filteredData = data.stream().filter(row -> row[0].equals(id)).collect(Collectors.toList());

								// 필터링된 데이터 출력
								for (String[] row : filteredData) {
								%>
								<tr style="height: 20px;">
										<th style="font-size: 20px">번호</th>
										<th>

												<div class="badge badge-lg"><%=row[0]%></div>

										</th>
								</tr>
								<tr>
										<th  style="font-size: 20px">부대시설1</th>
										<th  style="font-size: 20px"><%=row[10]%></th>
								</tr>
								<tr>
								<th  style="font-size: 20px">부대시설2</th>
								<th  style="font-size: 20px"><%=row[11]%></th>
								</tr>
								<%
								}
								%>

						</thead>
				</table>
		</div>
		<div class="btns">
				<div style="text-align: center">
						<button class="btn-text-link btn btn-outline btn-xs" type="button" onclick="history.back()">뒤로가기</button>
				</div>
		</div>
</section>
<%@ include file="../common/foot.jspf"%>
