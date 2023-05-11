<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.KoreaIT.ksh.demo.vo.Article"%>
<%@ page import="com.KoreaIT.ksh.demo.vo.Board"%>
<c:set var="pageTitle" value="DETAIL" />
<%@ include file="../common/head.jspf"%>
<%@ page import="com.KoreaIT.ksh.demo.vo.Comment"%>
<%
Article article = (Article) request.getAttribute("article");
int loginedMemberId = (int) request.getAttribute("loginedMemberId");
Comment comment = (Comment) request.getAttribute("comment");
Board board = (Board) request.getAttribute("board");
%>
	<br /><br /><br />
<section class="mt-10 text-xl">
	<div class="mx-auto overflow-x-auto">
		<table class=" table w-full table-box-type-1"
			style="width: 700px; height: 300px;">
			<thead>
				<tr>
					<th style="font-size: 15px">번호</th>
					<th>
						<div class="badge badge-lg">${article.id }</div>
					</th>
				</tr>
				<tr>
				<th style="font-size: 15px">게시판</th>
				<th>${board.name }</th>
				</tr>
				<tr>
					<th style="font-size: 15px">작성날짜</th>
					<th>${article.regDate.substring(0,10) }</th>
				</tr>
				<tr>
					<th style="font-size: 15px">수정날짜</th>
					<th>${article.updateDate.substring(0,10) }</th>
				</tr>
				<tr>
					<th style="font-size: 15px">작성자</th>
					<th>${article.name }</th>
				</tr>
				<tr>
					<th style="font-size: 15px">제목</th>
					<th>${article.title }</th>
				</tr>
				<tr>
					<th style="font-size: 15px">내용</th>
					<th>${article.body }</th>
				</tr>
				<tr>
					<th style="font-size: 15px">조회수</th>
					<th>${article.hitCount }</th>
				</tr>
			</thead>
		</table>

	</div>
	<div class="btns">
		<div style="text-align: center">
	
			<button class="btn-text-link btn btn-outline btn-xs" type="button"
				onclick="location.href='list'">뒤로가기</button>
				<a
				class="btn-text-link btn btn-outline btn-xs"
				onclick="if(confirm('정말 삭제하시겠습니까?') == false) return false;"
				href="delete?id=${article.id }&boardId=${article.boardId}">삭제</a>
	
		</div>
	</div>

</section>

<br />
<div style="text-align: center;">댓글 리스트</div>
<table class="table-box-type-2 table w-full"
	style="border-collaspe: collaspe; width: 700px;">
	<thead>

		<tr>
			<th style="font-size: 19px">내용</th>
			<th style="font-size: 19px">날짜</th>
			<th style="font-size: 19px">작성자</th>
			<th style="font-size: 19px">삭제</th>


		</tr>
	</thead>
	<tbody>
		<c:forEach var="comment" items="${comments }">
			<tr>
				<th>${comment.body }</th>
				<th>${comment.regDate.substring(0,10) }</th>
				<th>${comment.name}</th>

				<th><a class="btn-text-link btn btn-outline btn-xs"
					onclick="if(confirm('정말 삭제하시겠습니까?') == false) return false;"
					href="#">삭제</a>
				</th>

			</tr>
		</c:forEach>
	</tbody>
</table>




<%@ include file="../common/foot.jspf"%>