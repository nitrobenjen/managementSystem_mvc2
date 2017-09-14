<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%
	String contextRoot = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">


<!-- jQuery UI 사용 환경 설정 -->
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">


<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<!-- jQuery UI 사용 환경 설정 -->
<script src="http://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>


<style>
</style>

<script>
	$(document).ready(function() {

	});
</script>
</head>
<body>


	<div id="main">
		<div class="title">
			<img src="${pageContext.request.contextPath}/img/sist_logo.png" width="300px">
			<div class="login">
				관리자 님 │ 로그아웃
			</div>
		</div>
		<div id="menu">
			<div class="menu">
				<ul class="nav nav-pills nav-justified">
					<li><a class="dropdown-toggle" data-toggle="dropdown" href="#">기초
							정보 관리</a>
						<ul class="dropdown-menu">
							<li><a href="${pageContext.request.contextPath}/admin/admin101.jsp">과정명</a></li>
							<li><a href="${pageContext.request.contextPath}/admin/admin102.jsp">과목명</a></li>
							<li><a href="${pageContext.request.contextPath}/admin/admin103.jsp">교재명</a></li>
							<li><a href="${pageContext.request.contextPath}/admin/admin104.jsp">강의실명</a></li>
						</ul></li>
					<li><a href="${pageContext.request.contextPath}/admin/admin201.jsp">강사 계정 관리</a></li>
					<li><a href="${pageContext.request.contextPath}/admin/admin301.jsp">개설 과정/과목
							관리</a></li>
							
					<li><a href="${pageContext.request.contextPath}/admin/admin401.jsp">수강생 관리</a></li>
					<li class="active"><a class="dropdown-toggle" data-toggle="dropdown" href="#">성적
							관리</a>
						<ul class="dropdown-menu">
							<li><a href="${pageContext.request.contextPath}/admin/admin501.jsp">과목별</a></li>
							<li><a href="${pageContext.request.contextPath}/admin/admin502.jsp">수강생별</a></li>
						</ul></li>
				</ul>
			</div>
		</div>

		<div class="content">


			<h3>성적 관리 > 과목별</h3>

			<h4 style="text-align: center;font-weight:bold;">OCU001 / Framework을 활용한 빅데이터 개발자 과정</h4>
			<h5 style="text-align: center;">2016/09/01 ~ 2017/02/01</h5>

			<hr>

			<table class="table table-striped table-bordered ocu">
				<thead>
					<tr>
						<th>개설과목ID</th>
						<th>개설 과목명</th>
						<th>과목 시작일</th>
						<th>과목 종료일</th>
						<th>교재명</th>
						<th>강사명</th>
						<th>성적 등록 인원수</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>OUB001</td>
						<td>자바 네트워트 프로그래밍</td>
						<td>17/07/01</td>
						<td>17/07/31</td>
						<td>html 웹 프로그래밍 기초</td>
						<td>심심해</td>
						<td><button type="button" class="btn btn-default btn-sm" data-toggle="modal" data-target="#grade-insert-Modal">
								<span class="badge" id="Count">5</span> 보기
							</button></td>
					</tr>
					<tr>
						<td>OUB002</td>
						<td>관계형 데이터베이스</td>
						<td>17/07/01</td>
						<td>17/07/31</td>
						<td>html 웹 프로그래밍 기초</td>
						<td>심심해</td>
						<td><button type="button" class="btn btn-default btn-sm" disabled>
								<span class="badge" id="Count">0</span> 보기
							</button></td>
					</tr>
				</tbody>
			</table>	

			<form class="form-inline" method="post" style="text-align: center;">
				<div class="form-group">
					<select class="form-control" id="key" name="key">
						<option value="name">Name</option>
						<option value="phone">Phone</option>
						<option value="email">Email</option>
						<option value="regDate">RegDate</option>
					</select>
				</div>
				<div class="form-group">
					<input type="text" class="form-control" id="value" name="value"
						required>
				</div>
				<button type="submit" class="btn btn-default">Search</button>
			</form>


		</div>
	</div>


	<!-- 성적 입력 Modal -->
	<div class="modal fade" id="grade-insert-Modal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h5 class="modal-title">성적 입력</h5>
				</div>
				<div class="modal-body">
				
				<h4 style="text-align: center;">OUB001 / 자바 네트워트 프로그래밍 / 2016/07/01 ~ 2017/07/31</h4>
				<h5 style="text-align: center;">출결배점 : 30 / 필기배점 : 30 / 실기배점 : 40</h5>
				<p style="text-align: right;">시험일 : 2017/01/01</p>
				<hr>
				
				<table class="table table-striped table-bordered ocu">
				<thead>
					<tr>
						<th>수강생 ID</th>
						<th>이름</th>
						<th>출결점수</th>
						<th>필기점수</th>
						<th>실기점수</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>STU001</td>
						<td>홍길동</td>
						<td>0</td>
						<td>0</td>
						<td>0</td>
					</tr>
					<tr>
						<td>STU002</td>
						<td>이순신</td>
						<td>0</td>
						<td>0</td>
						<td>0</td>
					</tr>
					<tr>
						<td>STU003</td>
						<td>이순애</td>
						<td>0</td>
						<td>0</td>
						<td>0</td>
					</tr>
					<tr>
						<td>STU004</td>
						<td>김정훈</td>
						<td>0</td>
						<td>0</td>
						<td>0</td>
					</tr>
					<tr>
						<td>STU005</td>
						<td>한석봉</td>
						<td>0</td>
						<td>0</td>
						<td>0</td>
					</tr>
					<tr>
						<td>STU006</td>
						<td>이기자</td>
						<td>0</td>
						<td>0</td>
						<td>0</td>
					</tr>
					<tr>
						<td>STU007</td>
						<td>장인철</td>
						<td>0</td>
						<td>0</td>
						<td>0</td>
					</tr>
					<tr>
						<td>STU008</td>
						<td>김영년</td>
						<td>0</td>
						<td>0</td>
						<td>0</td>
					</tr>
				</tbody>
			</table>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
				</div>
			</div>

		</div>
	</div>


</body>
</html>