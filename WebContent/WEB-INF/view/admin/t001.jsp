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
<link rel="stylesheet" href="<%=contextRoot%>/style.css">	
	
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<style>

</style>

<script>
	$(document).ready(function() {

	});
</script>
</head>
<body>


	<div id="main" class="container">
		<div class="title">
			<img src="<%=contextRoot%>/img/sist_logo.png" width="300px">
			<div class="login">관리자</a> 님 │ 로그아웃</div>
		</div>
		<div class="menu">
			<ul class="nav nav-pills nav-justified">
				<li class="active"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#">기초 정보 관리</a>
					<ul class="dropdown-menu">
						<li><a href="<%=contextRoot%>/t002.jsp">과정명</a></li>
						<li><a href="<%=contextRoot%>/t003.jsp">과목명</a></li>
						<li><a href="<%=contextRoot%>/t004.jsp">교재명</a></li>
						<li><a href="<%=contextRoot%>/t005.jsp">강의실명</a></li>
					</ul>
				</li>
				<li><a class="dropdown-toggle" data-toggle="dropdown" href="#">강사 계정 관리</a>
				<ul class="dropdown-menu">
			      <li><a href="#">과정명</a></li>
			      <li><a href="#">과목명</a></li>
			      <li><a href="#">교재명</a></li>
			      <li><a href="#">강의실명</a></li>
			    </ul>
				</li>
				<li><a class="dropdown-toggle" data-toggle="dropdown" href="#">개설 과정 관리</a>
				<ul class="dropdown-menu">
			      <li><a href="#">과정명</a></li>
			      <li><a href="#">과목명</a></li>
			      <li><a href="#">교재명</a></li>
			      <li><a href="#">강의실명</a></li>
			    </ul>				
				</li>
				<li><a href="#">수강생 관리</a>
				<ul class="dropdown-menu">
			      <li><a href="#">과정명</a></li>
			      <li><a href="#">과목명</a></li>
			      <li><a href="#">교재명</a></li>
			      <li><a href="#">강의실명</a></li>
			    </ul>				
				</li>
				<li><a class="dropdown-toggle" data-toggle="dropdown" href="#">성적 관리</a>
				<ul class="dropdown-menu">
			      <li><a href="#">과정명</a></li>
			      <li><a href="#">과목명</a></li>
			      <li><a href="#">교재명</a></li>
			      <li><a href="#">강의실명</a></li>
			    </ul>				
				</li>
			</ul>
		</div>
		<div class="content">
		

		</div>
	</div>
	
	


</body>
</html>