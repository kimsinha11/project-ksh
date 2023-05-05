<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<c:set var="pageTitle" value="LIST" />
<%@ include file="../common/head.jspf"%>

<section class="mt-10 text-xl">
<div class="mx-auto">
		<table class="table-box-type-1" style="border-collaspe: collaspe; width: 700px;" border=2px>
				<thead>
						<tr>
								<th>번호</th>
								<th>날짜</th>
								<th>제목</th>
								<th>작성자</th>
						</tr>
				</thead>
				<tbody>
						<c:forEach var="article" items="${articles }">
								<tr>
										<th>${article.id }</th>
										<th>${article.regDate.substring(0,10) }</th>
										<th>
												<a href="detail?id=${article.id }">${article.title }</a>
										</th>
										<th>${article.name }</th>
								</tr>
						</c:forEach>
				</tbody>
		</table>
		</div>
</section>

<%@ include file="../common/foot.jspf"%>