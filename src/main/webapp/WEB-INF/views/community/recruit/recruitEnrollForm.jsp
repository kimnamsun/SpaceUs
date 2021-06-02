<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<fmt:requestEncoding value="utf-8" />
<jsp:include page="/WEB-INF/views/common/header.jsp" />

<style>
.image-div {
	background-color: #f7f7f7;
	border: 1px solid gray;
	display: inline-block;
	width: 200px;
	height: 200px;
	margin-right: 20px;
}

.fas {
	position: absolute;
	padding: 90px;
}

input[type=file], .address-input {
	margin-bottom: 20px;
	margin-top: 10px;
}

.site-btn {
	width: 100%;
	font-size: 17px;
}
</style>
<section class="ftco-section ftco-agent">
	<div class="navbar justify-content-center navbar-dark bg-dark">
		<ul class="nav">
			<li class="nav-item"><a class="nav-link active"
				href="${pageContext.request.contextPath }/community/group/groupList.do">소모임</a>
			</li>
			<li class="nav-item"><a class="nav-link"
				href="${pageContext.request.contextPath }/community/recruit/recruitList.do">구인/구직</a>
			</li>
		</ul>
	</div>
</section>
<div class="hero-wrap" style="height: 200px">
	<div class="container">
		<div
			class="row no-gutters slider-text justify-content-center align-items-center">
			<div class="col-lg-8 col-md-6 ftco-animate d-flex align-items-end">
				<div class="text text-center mx-auto" style="margin-bottom: 80%;">
					<h1 class="mb-4">구인/구직</h1>
					<p class="h6">구인ㆍ구직 게시판은 각 숙소에서의 스태프(매니저, 아르바이트, 주방 아주머니 등)의
						구인/구직 관련 정보를 교환하는 게시판으로, SpaceUs에서는 정보교환의 온라인 공간을 제공할 뿐 중개에 관여하지
						않으며, 그에 따른 과실 또는 손해발생에 대해 일체 책임을 지지 않음을 알려드립니다.</p>
				</div>
			</div>
		</div>
	</div>
</div>

<section class="blog-section spad">
	<div class="m-5">
		<div class="row m-5">
			<div class="col-12">
				<p class="h3 mt-5 mb-3">글쓰기</p>
				<div class="table-responsive">
					<form
						action="${pageContext.request.contextPath}/community/recruit/insertRecruit.do"
						id="recruitFrm" method="post">
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
						<table class="table">
							<tr>
								<th>분류</th>
								<th><select class="nice-select sm-width " name="header">
										<option value="" selected hidden>분류선택</option>
										<option value="구인">구인</option>
										<option value="구직">구직</option>
								</select></th>
							</tr>
							<tr>
								<td>제목</td>
								<td><input type="text" placeholder="제목을 입력해주세요"
									style="border: none; width: 500px;" name="title" /></td>
							</tr>
							<tr>
								<td>내용</td>
								<td><textarea name="content" id="content" rows="15"
										style="width: 100%;"></textarea></td>
							</tr>
						</table>
						<div class="text-center">
							<button id="insertBtn" class="btn font-bold"
								style="margin-top: 50px; background-color: #00c89e; font-size: 16px; color: white;">
								글 등록</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</section>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/smartEditor/js/HuskyEZCreator.js"></script>
<script type="text/javascript"> 
 var oEditors = [];
nhn.husky.EZCreator.createInIFrame({
 oAppRef: oEditors,
 elPlaceHolder: "content",
 sSkinURI: "${pageContext.request.contextPath}/resources/js/smartEditor/SmartEditor2Skin.html",
 fCreator: "createSEditor2",
  htParams : { 
	 bUseToolbar : true, 
	 bUseVerticalResizer : false, 
	 bUseModeChanger : false,
	  fOnBeforeUnload : function(){
	 }  
 },
	   fOnAppLoad : function(){	 
		 oEditors.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);
		} 
}); 
 $("#insertBtn").click( function(){
	 if($("select[name=header]").val()==""){
		swal("분류를 선택해주세요");
		return false;
		 }
	 if($("input[name=title]").val()==""){
		swal("제목을 입력해주세요");
		return false;
		 }
	 oEditors.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);  
	$("#recruitFrm").submit(); 
	 
}); 
</script>
<jsp:include page="/WEB-INF/views/common/footer.jsp" />