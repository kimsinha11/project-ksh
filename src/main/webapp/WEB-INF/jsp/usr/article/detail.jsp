<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.KoreaIT.ksh.demo.vo.Article"%>
<c:set var="pageTitle" value="DETAIL" />
<%@ include file="../common/head.jspf"%>
<%@ page import="com.KoreaIT.ksh.demo.vo.Reply"%>
<%
Reply reply = (Reply) request.getAttribute("reply");
%>

<!-- <iframe src="http://localhost:8081/usr/article/doIncreaseHitCountRd?id=2" frameborder="0"></iframe> -->
<!-- 변수 생성 -->
<script>
	const params = {};
	params.id = parseInt('${param.id}');
	params.memberId = parseInt('${loginedMemberId}');
	var isAlreadyAddGoodRp = ${isAlreadyAddGoodRp};
	var isAlreadyAddBadRp = ${isAlreadyAddBadRp};
</script>

<!-- 메서드 생성 -->
<script>
	function ArticleDetail__increaseHitCount() {
		const localStorageKey = 'article__' + params.id + '__alreadyView';
		
		if(localStorage.getItem(localStorageKey)) {
			return;
		}
		
		localStorage.setItem(localStorageKey, true);
		
		$.get('../article/doIncreaseHitCountRd', {
			id : params.id,
			ajaxMode : 'Y'
		}, function(data) {
			$('.article-detail__hit-count').empty().html(data.data1);
		}, 'json');
	}
	$(function() {
		// 실전코드
		 	ArticleDetail__increaseHitCount();
		// 연습코드
		//setTimeout(ArticleDetail__increaseHitCount, 2000);
	})
	function checkAddRpBefore() {
    <!-- 변수값에 따라 각 id가 부여된 버튼에 클래스 추가(이미 눌려있다는 색상 표시) -->
		if (isAlreadyAddGoodRp == true) {
			$('#likeButton').removeClass('btn-outline').addClass('btn-danger');
		} else if (isAlreadyAddBadRp == true) {
			$('#DislikeButton').removeClass('btn-outline').addClass('btn-danger');
		} else {
			return;
		}
	};
</script>	

<!-- 리액션 실행 코드 -->
<script>
     $(function() {
		checkAddRpBefore();
		});
		
		 function doGoodReaction(articleId) {
		        $.ajax({
		            url: '/usr/reactionPoint/doGoodReaction',
		            type: 'POST',
		            data: {relTypeCode: 'article', relId: articleId},
		            dataType: 'json',
		            success: function(data) {
		                if (data.resultCode.startsWith('S-')) {
		                    var likeButton = $('#likeButton');
		                    var likeCount = $('#likeCount');
		
		                    if (data.resultCode == 'S-1') {
		                        likeButton.removeClass('btn-danger').addClass('btn-outline');
		                        likeCount.text(parseInt(likeCount.text()) - 1);
		                    } else {
		                        likeButton.removeClass('btn-outline').addClass('btn-danger');
		                        likeCount.text(parseInt(likeCount.text()) + 1);
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
			
		function doBadReaction(articleId) {
		      $.ajax({
		          url: '/usr/reactionPoint/doBadReaction',
		          type: 'POST',
		          data: {relTypeCode: 'article', relId: articleId},
		          dataType: 'json',
		          success: function(data) {
		              if (data.resultCode.startsWith('S-')) {
		                  var DislikeButton = $('#DislikeButton');
		                  var DislikeCount = $('#DislikeCount');
		                  
		                  if (data.resultCode == 'S-1') {
		                  	DislikeButton.removeClass('btn-danger').addClass('btn-outline');
		                    DislikeCount.text(parseInt(DislikeCount.text()) - 1);
		                  } else {
		                  	DislikeButton.removeClass('btn-outline').addClass('btn-danger');
		                    DislikeCount.text(parseInt(DislikeCount.text()) + 1);
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
<!-- 댓글 리액션 실행 코드 -->
<script>
function doReplyGoodReaction(replyId) {
    if (params.memberId == 0) {
        alert('로그인 후 이용해주세요.');
        return;
    }
    $.ajax({
        url: '/usr/reactionPoint/doGoodReaction',
        type: 'POST',
        data: {relTypeCode: 'reply', relId: replyId},
        dataType: 'json',
        success: function(data) {
            if (data.resultCode.startsWith('S-')) {
                var likeButton = $('#replyLikeButton');
                var likeCount = $('#replylikeCount');
                if (data.resultCode == 'S-1') {
          
                    likeCount.text(parseInt(likeCount.text()) - 1);
                } else {
                    likeCount.text(parseInt(likeCount.text()) + 1);
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
function doReplyBadReaction(replyId) {
    if (params.memberId == 0) {
        alert('로그인 후 이용해주세요.');
        return;
    }
    $.ajax({
        url: '/usr/reactionPoint/doBadReaction',
        type: 'POST',
        data: {relTypeCode: 'reply', relId: replyId},
        dataType: 'json',
        success: function(data) {
            if (data.resultCode.startsWith('S-')) {
                var dislikeButton = $('#replyDislikeButton');
                var dislikeCount = $('#replyDislikeCount');
                if (data.resultCode == 'S-1') {
                    dislikeCount.text(parseInt(dislikeCount.text()) - 1);
                } else {
                    dislikeCount.text(parseInt(dislikeCount.text()) + 1);
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
<script type="text/javascript">
	let ReplyWrite__submitFormDone = false;
	function ReplyWrite__submitForm(form) {
		if (ReplyWrite__submitFormDone) {
			return;
		}
		form.body.value = form.body.value.trim();
		if (form.body.value.length < 3) {
			alert('3글자 이상 입력하세요');
			form.body.focus();
			return;
		}
		ReplyWrite__submitFormDone = true;
		form.submit();
	}
</script>
<%
Article article = (Article) request.getAttribute("article");
int loginedMemberId = (int) request.getAttribute("loginedMemberId");
%>
<section class="mt-10 text-xl">
		<div class="mx-auto overflow-x-auto">
				<table class=" table w-full table-box-type-1" style="width: 700px; height: 500px;">
						<thead>
								<tr>
										<th style="font-size: 15px">번호</th>
										<th>
												<div class="badge badge-lg">${article.id }</div>
										</th>
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
										<th>
												<span class="article-detail__hit-count">${article.hitCount }</span>
										</th>
								</tr>

						</thead>

				</table>

		</div>
		<div class="btns">
				<div style="text-align: center">
						<%
						if (article.getMemberId() != loginedMemberId) {
						%>
						<button class="btn-text-link btn btn-outline btn-xs" type="button" onclick="history.back()">뒤로가기</button>
						<%
						}
						%>
						<%
						if (rq.isLogined()) {
						%>

						<c:if test="${actorCanMakeReaction}">
								<button id="likeButton" class="btn btn-outline" type="button" onclick="doGoodReaction(${param.id})">
										<svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
			    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
														d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z" />
			  </svg>
										<span id="likeCount">${article.goodReactionPoint}</span>

								</button>
								<button id="DislikeButton" class="btn btn-outline" type="button" onclick="doBadReaction(${param.id})">
										<svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
			    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
														d="M18,4h3v10h-3V4z M5.23,14h4.23l-1.52,4.94C7.62,19.97,8.46,21,9.62,21c0.58,0,1.14-0.24,1.52-0.65L17,14V4H6.57 C5.5,4,4.59,4.67,4.38,5.61l-1.34,6C2.77,12.85,3.82,14,5.23,14z" />
			  </svg>
										<span id="DislikeCount">${article.badReactionPoint}</span>
								</button>
						</c:if>
						<%
						}
						%>
				</div>
				<div style="text-align: center">
						<%
						if (article.getMemberId() == loginedMemberId) {
						%>
						<button class="btn-text-link btn btn-outline btn-xs" type="button" onclick="location.href='list'">뒤로가기</button>
						<a class="btn-text-link btn btn-outline btn-xs" onclick="if(confirm('정말 수정하시겠습니까?') == false) return false;"
								href="modify?id=${article.id }">수정</a>
						<a class="btn-text-link btn btn-outline btn-xs" onclick="if(confirm('정말 삭제하시겠습니까?') == false) return false;"
								href="delete?id=${article.id }">삭제</a>
						<%
						}
						%>
				</div>
		</div>

</section>

<br />

<c:if test="${rq.logined }">
	<form style="text-align: center;" action="../reply/doWrite"
		method="POST" onsubmit="ReplyWrite__submitForm(this); return false;">

		<div
			style="display: inline-block; border: 2px solid black; width: 700px; height: 100px; text-align: left;">
			<div style="display: none">

				<input value="${article.id }"
					class="input input-bordered w-full max-w-xs" type="hidden"
					name="relId" /> <input type="hidden" name="relTypeCode"
					value="article" /> <input value="${article.boardId }"
					class="input input-bordered w-full max-w-xs" type="hidden"
					name="boardId" />

			</div>

			<div>작성자 : ${rq.loginedMember.nickname}</div>
			<div>
				내용 :
				<textarea type="text" class="input input-bordered w-full max-w-xs"
					placeholder="내용을 입력해주세요" name="body" /></textarea>
				<button class="btn-text-link btn btn-outline btn-xs"
					style="display: inline" type="submit">작성하기</button>
			</div>


		</div>
	</form>
</c:if>
<div style="text-align: center;">
	<c:if test="${rq.notLogined }">
		<a class="btn-text-link btn btn-outline btn-xs" type="button"
			href="/usr/member/login">로그인</a> 후 댓글 작성을 이용해주세요
</c:if>
</div>
<br />
<div style="text-align: center;">댓글 리스트</div>
<table class="table-box-type-2 table w-full"
	style="border-collaspe: collaspe; width: 700px;">
	<thead>

		<tr>
			<th style="font-size: 19px">내용</th>
			<th style="font-size: 19px">날짜</th>
			<th style="font-size: 19px">작성자</th>
			<th style="font-size: 19px">수정</th>
			<th style="font-size: 19px">삭제</th>
			<th style="font-size: 19px">공감</th>


		</tr>
	</thead>
	<tbody>
		<c:forEach var="reply" items="${replys }">
			<tr>
				<th>${reply.body }</th>
				<th>${reply.regDate.substring(0,10) }</th>
				<th>${reply.name}</th>

				<th><a class="btn-text-link btn btn-outline btn-xs"
					onclick="if(confirm('정말 수정하시겠습니까?') == false) return false;"
					href="../reply/modify?id=${reply.id }&relId=${reply.relId }">수정</a>
				</th>
				<th><a class="btn-text-link btn btn-outline btn-xs"
					onclick="if(confirm('정말 삭제하시겠습니까?') == false) return false;"
					href="../reply/delete?id=${reply.id }&relId=${reply.relId }">삭제</a>
				</th>
				<th>
					<button id="replylikeButton" class="btn btn-outline" type="button"
						onclick="doReplyGoodReaction(${reply.id })">
						<svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6"
							fill="none" viewBox="0 0 24 24" stroke="currentColor">
			    <path stroke-linecap="round" stroke-linejoin="round"
								stroke-width="2"
								d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z" />
			  </svg>
						<span id="replylikeCount">${reply.goodReactionPoint}</span>

					</button>
					<button id="replyDislikeButton" class="btn btn-outline" type="button"
						onclick="doReplyBadReaction(${reply.id })">
						<svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6"
							fill="none" viewBox="0 0 24 24" stroke="currentColor">
			    <path stroke-linecap="round" stroke-linejoin="round"
								stroke-width="2"
								d="M18,4h3v10h-3V4z M5.23,14h4.23l-1.52,4.94C7.62,19.97,8.46,21,9.62,21c0.58,0,1.14-0.24,1.52-0.65L17,14V4H6.57 C5.5,4,4.59,4.67,4.38,5.61l-1.34,6C2.77,12.85,3.82,14,5.23,14z" />
			  </svg>
						<span id="replyDislikeCount">${reply.badReactionPoint}</span>
					</button>
				</th>
			</tr>
		</c:forEach>
	</tbody>
</table>
<%@ include file="../common/foot.jspf"%>