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
					<li><a
						href="${pageContext.request.contextPath}/admin/admin201.jsp">강사 계정 관리</a></li>
					<li class="active"><a href="${pageContext.request.contextPath}/admin/admin301.jsp">개설 과정/과목
							관리</a></li>
					<li><a href="${pageContext.request.contextPath}/admin/admin401.jsp">수강생 관리</a></li>
					<li><a class="dropdown-toggle" data-toggle="dropdown" href="#">성적
							관리</a>
						<ul class="dropdown-menu">
							<li><a href="${pageContext.request.contextPath}/admin/admin501.jsp">과목별</a></li>
							<li><a href="${pageContext.request.contextPath}/admin/admin502.jsp">수강생별</a></li>
						</ul>
					</li>
				</ul>
			</div>
		</div>

		<div class="content">


			<h3>수강생 명단</h3>

			<h4 style="text-align: center; font-weight: bold;">OCU001 /
				Framework을 활용한 빅데이터 개발자 과정</h4>
			<h5 style="text-align: center;">2016/09/01 ~ 2017/02/01</h5>

			<hr>

			<table class="table table-striped table-bordered ocu">
				<thead>
					<tr>
						<th>수강생 ID</th>
						<th>이름</th>
						<th>주민번호 뒷자리</th>
						<th>전화번호</th>
						<th>등록일</th>
						<th>수료여부</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>STU001</td>
						<td>홍길동</td>
						<td>1022432</td>
						<td>010-1234-1234</td>
						<td>17/03/21</td>
						<td>중도탈락(17-01-01)</td>
					</tr>
					<tr>
						<td>STU002</td>
						<td>이순신</td>
						<td>1544236</td>
						<td>010-4758-6532</td>
						<td>17/03/21</td>
						<td>수료</td>
					</tr>
					<tr>
						<td>STU003</td>
						<td>이순애</td>
						<td>2312547</td>
						<td>010-4231-1236</td>
						<td>16/12/29</td>
						<td>수료</td>
					</tr>
					<tr>
						<td>STU004</td>
						<td>김정훈</td>
						<td>1788896</td>
						<td>010-5236-4221</td>
						<td>17/10/20</td>
						<td>수료</td>
					</tr>
					<tr>
						<td>STU005</td>
						<td>한석봉</td>
						<td>1566789</td>
						<td>010-5211-3542</td>
						<td>17/10/27</td>
						<td>수료</td>
					</tr>
					<tr>
						<td>STU006</td>
						<td>이기자</td>
						<td>2978541</td>
						<td>010-3214-5357</td>
						<td>17/10/09</td>
						<td>수료</td>
					</tr>
					<tr>
						<td>STU007</td>
						<td>장인철</td>
						<td>1625148</td>
						<td>010-2345-2525</td>
						<td>17/04/15</td>
						<td>수료</td>
					</tr>
					<tr>
						<td>STU008</td>
						<td>김영년</td>
						<td>2362514</td>
						<td>010-2222-4444</td>
						<td>17/11/24</td>
						<td>수료</td>
					</tr>
				</tbody>
			</table>
			
			<div style="float:left;position:absolute">
			<button type="button" class="btn btn-default btn-sm">
						TotalCount <span class="badge" id="totalCount">25</span></button>
			</div>

			<div style="text-align: center;">
			
				<ul class="pagination" style="margin:0px 0px 20px 0px;">
					<li class="active"><a href="#">1</a></li>
					<li><a href="#">2</a></li>
					<li><a href="#">3</a></li>
					<li><a href="#">4</a></li>
					<li><a href="#">5</a></li>
				</ul>
			</div>


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




</body>
</html>