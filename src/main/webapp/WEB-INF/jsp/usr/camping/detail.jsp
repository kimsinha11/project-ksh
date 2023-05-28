<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<c:set var="pageTitle" value="캠핑장-상세보기" />
<%@ include file="../common/head.jspf"%>
<%@ include file="../common/toastUiEditorLib.jspf"%>
<%@ page import="java.util.stream.Collectors"%>
<%@ page import="java.util.Arrays"%>
<script>
	const params = {}; // params 객체를 빈 객체로 초기화
	params.memberId = '${loginedMemberId}';
	var isAlreadyAddGoodRp = ${isAlreadyAddGoodRp};
</script>
<%
List<String[]> data = (List<String[]>) request.getAttribute("data");
%>


<script>
function checkAddRpBefore() {
    <!-- 변수값에 따라 각 id가 부여된 버튼에 클래스 추가(이미 눌려있다는 색상 표시) -->
		if (isAlreadyAddGoodRp === true) {
			$('#likeButton').removeClass('btn-outline').addClass('btn-danger');
		} else {
			return;
		}
	};
	</script>
<script>
$(function() {
	checkAddRpBefore();
	});
	
function doGoodReaction(articleId) {
    if (params.memberId == 0) {
        alert('로그인 후 이용해주세요.');
        return;
    }
    $.ajax({
        url: '/usr/likebutton/doGoodReaction',
        type: 'POST',
        data: {relTypeCode: 'camping', relId: articleId },
        dataType: 'json',
        success: function(data) {
            if (data.resultCode.startsWith('S-')) {
            	 var likeButton = $('#likeButton');

                if (data.resultCode == 'S-1') {
                    likeButton.removeClass('btn-danger').addClass('btn-outline');
                } else {
                    likeButton.removeClass('btn-outline').addClass('btn-danger');
                }
            } else {
                alert(data.msg);
            }
        },
        error: function(jqXHR, textStatus, errorThrown) {
            alert('오류가 발생했습니다: ' + textStatus);
        }
    });
}


</script>
<meta charset="UTF-8">
<h1>Detail</h1>
<section class="mt-10 text-xl">
		<div class="mx-auto overflow-x-auto">
				<table class="table w-full table-box-type-3" style="width: 700px;">

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
										<th style="background-color: white;">

												<div class="badge badge-lg"><%=row[0]%></div>

										</th>
								</tr>
								<tr style="height: 50px;">
										<th style="font-size: 20px">종류</th>
										<th style="font-size: 20px; background-color: white;"><%=row[2]%></th>
								</tr>
								<tr style="height: 50px;">
										<th style="font-size: 20px">부대시설1</th>
										<th style="font-size: 20px; background-color: white;"><%=row[10]%></th>
								</tr>
								<tr style="height: 50px;">
										<th style="font-size: 20px">부대시설2</th>
										<th style="font-size: 20px; background-color: white;"><%=row[11]%></th>
								</tr>
								<tr>
								<th style="font-size: 20px">좋아요</th>
								<th><button id="likeButton" class="btn btn-outline" type="button" onclick="doGoodReaction('${id}')">
						<svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
										d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z" />
            </svg>
				</button></th>
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
