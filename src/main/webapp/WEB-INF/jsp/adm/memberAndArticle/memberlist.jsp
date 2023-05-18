<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="관리자 페이지 - 회원,게시글 리스트" />
<%@ include file="../common/head.jspf"%>

<section class="mt-8 text-xl flex">
	<div>
		<div class="container mx-auto px-3">
			<div class="flex">
				<div>
					회원 수 :
					<span class="badge">${membersCount }명</span>
				</div>
			</div>
			<div>
				<form class="flex">

					<select data-value="${authLevel }" name="authLevel" class="select select-bordered">
						<option disabled="disabled">회원 타입</option>
						<option value="3">일반</option>
						<option value="7">관리자</option>
						<option value="0">전체</option>

					</select>
					<select data-value="${searchKeywordTypeCode }" name="MsearchKeywordTypeCode" class="select select-bordered">
						<option disabled="disabled">검색 타입</option>
						<option value="loginId">아이디</option>
						<option value="name">이름</option>
						<option value="nickname">닉네임</option>
						<option value="loginId,name,nickname">전체</option>
					</select>


					<input name="MsearchKeyword" type="text" class="ml-2 w-96 input input-borderd" placeholder="검색어를 입력해주세요"
						maxlength="20" value="${param.searchKeyword }" />
					<button type="submit" class="ml-2 btn btn-ghost">검색</button>
				</form>
			</div>
			<div class="table-box-type-1 mt-3">
				<table class="table table-fixed w-full">
					<colgroup>
						<col width="70" />
						<col width="70" />
						<col width="100" />
						<col width="100" />
						<col width="100" />
						<col width="100" />
						<col width="150" />
						<col width="100" />
					</colgroup>
					<thead>
						<tr>
							<th>
								<input type="checkbox" class="checkbox-all-member-id" />
							</th>
							<th>번호</th>
							<th>가입날짜</th>
							<th>수정날짜</th>
							<th>아이디</th>
							<th>이름</th>
							<th>닉네임</th>
							<th>탈퇴여부 0: 전 / 1: 후</th>
							<th>옆에놈을 풀어서 설명</th>
						</tr>
					</thead>

					<tbody>
						<c:forEach var="member" items="${members }">
							<tr class="hover">
								<th>
									<input type="checkbox" class="checkbox-member-id" value="${member.id }" />
								</th>
								<td>${member.id}</td>
								<td>${member.forPrintType1RegDate}</td>
								<td>${member.forPrintType1UpdateDate}</td>
								<td>${member.loginId}</td>
								<td>${member.name}</td>
								<td>${member.nickname}</td>
								<td>${member.delStatus}</td>
								<td>${member.delStatus == true ? '정지' : '활동' }</td>
							</tr>
						</c:forEach>
					</tbody>

				</table>
			</div>

			<script>
			$('.checkbox-all-member-id').change(function() {
			      const $all = $(this);
			      const allChecked = $all.prop('checked');
			      $('.checkbox-member-id').prop('checked', allChecked);
			    });
			    $('.checkbox-member-id').change(function() {
			      const checkboxMemberIdCount = $('.checkbox-member-id').length;
			      const checkboxMemberIdCheckedCount = $('.checkbox-member-id:checked').length;
			      const allChecked = checkboxMemberIdCount == checkboxMemberIdCheckedCount;
			      $('.checkbox-all-member-id').prop('checked', allChecked);
			      
			    });
			</script>
			<div>
				<button class="btn btn-error btn-delete-selected-members">선택삭제</button>
			</div>

			<form hidden method="POST" name="do-delete-members-form" action="../memberAndArticle/doDeleteMembers">
				<input type="hidden" name="replaceUri" value="${rq.currentUri}" />
				<input type="hidden" name="ids" value="" />
			</form>

			<script>
    		$('.btn-delete-selected-members').click(function() {
      			const values = $('.checkbox-member-id:checked').map((index, el) => el.value).toArray();
      			if ( values.length == 0 ) {
       		 		alert('삭제할 회원을 선택 해주세요.');
       		 		return;
     			}
      			if ( confirm('정말 삭제하시겠습니까?') == false ) {
        			return;
     			}
      			document['do-delete-members-form'].ids.value = values.join(',');
      			document['do-delete-members-form'].submit();
    		});
    	</script>

			<div class="page-menu mt-3 flex justify-center">
				<div class="btn-group">

					<c:set var="pageMenuLen" value="6" />
					<c:set var="startPage" value="${Mpage - pageMenuLen >= 1 ? Mpage- pageMenuLen : 1}" />
					<c:set var="endPage" value="${Mpage + pageMenuLen <= pagesCount ? Mpage + pageMenuLen : pagesCount}" />

					<c:set var="pageBaseUri" value="?boardId=${boardId }" />
					<c:set var="pageBaseUri" value="${pageBaseUri }&MsearchKeywordTypeCode=${param.MsearchKeywordTypeCode}" />
					<c:set var="pageBaseUri" value="${pageBaseUri }&MsearchKeyword=${param.MsearchKeyword}" />

					<c:if test="${startPage > 1}">
						<a class="btn btn-sm" href="${pageBaseUri }&Mpage=1">1</a>
						<c:if test="${startPage > 2}">
							<a class="btn btn-sm btn-disabled">...</a>
						</c:if>
					</c:if>
					<c:forEach begin="${startPage }" end="${endPage }" var="i">
						<a class="btn btn-sm ${Mpage == i ? 'btn-active' : '' }" href="${pageBaseUri }&page=${i }">${i }</a>
					</c:forEach>
					<c:if test="${endPage < pagesCount}">
						<c:if test="${endPage < pagesCount - 1}">
							<a class="btn btn-sm btn-disabled">...</a>
						</c:if>
						<a class="btn btn-sm" href="${pageBaseUri }&page=${pagesCount }">${pagesCount }</a>
					</c:if>
				</div>
			</div>
		</div>
	</div>
<div class="list" style="display: inline-block; width: 7%; text-align: center;">
						<a  href="/adm/article/list">게시판</a>
						
				</div>

</section>
<style>
section>.list>ul>li>a {
	display: none;
}

section > .list:hover > ul > li > a {
  display: block;
}

</style>
<%@ include file="../common/foot.jspf"%>