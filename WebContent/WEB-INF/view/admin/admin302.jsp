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
		$("#oubstartdate, #oubenddate, #oubstartmoddate,#oubendmoddate").datepicker({
			dateFormat : "yy-mm-dd",
	    	minDate: 0,
	    	maxDate : new Date('2017-09-15'),
			changeMonth : true,
			changeYear : true
		});
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


			<h3>개설 과목 관리</h3>
			
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
						<th>수정</th>
						<th>삭제</th>
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
						<td><button type="button" class="btn btn-default"
								data-toggle="modal" data-target="#oub-mod-Modal">수정</button></td>
						<td><button type="button" class="btn btn-default"
								data-toggle="modal" data-target="#oub-del-Modal">삭제</button></td>
					</tr>
					<tr>
						<td>OUB002</td>
						<td>관계형 데이터베이스</td>
						<td>17/07/01</td>
						<td>17/07/31</td>
						<td>html 웹 프로그래밍 기초</td>
						<td>심심해</td>
						<td><button type="button" class="btn btn-default"
								data-toggle="modal" data-target="#Modal">수정</button></td>
						<td><button type="button" class="btn btn-default"
								data-toggle="modal" data-target="#Modal" disabled>삭제</button></td>
					</tr>
				</tbody>
			</table>

				<button type="button" style="float: left;position: absolute;" class="btn btn-default"
					data-toggle="modal" data-target="#oub-insert-Modal">개설 과목 등록</button>

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


	<!-- 개설 과정 등록 Modal -->
	<div class="modal fade" id="oub-insert-Modal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h5 class="modal-title">개설 과목 등록</h5>
				</div>
				<div class="modal-body">

					<form class="form-horizontal">
						<div class="form-group">
							<label class="control-label col-sm-3 m10">과목 ID</label>
							<div class="col-sm-9 m10">
								<select class="form-control" id="sel1">
							        <option>SUB001 / 자바 네트워트 프로그래밍</option>
							        <option>SUB002 / 자바 웹 프로그래밍</option>
							        <option>SUB003 / JDBC 프로그래밍</option>
							        <option>SUB004 / HTML5/CSS3/JavaScript</option>
							        <option>SUB005 / jQuery & Ajax</option>
							        <option>SUB006 / UI디자인</option>
							        <option>SUB007 / UI/UX가이드 제작</option>
							        <option>SUB008 / 플랫폼별 UI디자인</option>
							        <option>SUB009 / Framework</option>
							        <option>SUB010 / Oracle DBMS</option>
							        <option>SUB011 / Front-end 개발</option>
							        <option>SUB012 / 빅데이터 분석 및 시각화</option>
							        <option>SUB013 / 실무 프로젝트 통합구현</option>
							    </select>
							</div>
							<label class="control-label col-sm-3 m10">교재 ID</label>
							<div class="col-sm-9 m10">
								<select class="form-control" id="sel1">
							        <option>B001 / 이것이 자바다 / 한빛출판사</option>
							        <option>B002 / 쉽게 배우는 오라클 / 생능출판사</option>
							        <option>B003 / 속깊은 jQuery / 루비페이퍼</option>
							        <option>B004 / html 웹 프로그래밍 입문 / 생능출판사</option>
							        <option>B005 / 프런트엔드 웹 디자인 입문 / 이지스퍼블리싱</option>
							        <option>B006 / UI / UX 디자인 이론과 실습 / 한빛아카데미</option>
							        <option>B007 / 자바 네트워크 프로그래밍 / 제이펍</option>
							        <option>B008 / TCP/IP 쉽게, 더 쉽게 / 제이펍</option>
							        <option>B009 / 파이썬 라이브러리를 활용한 데이터 분석 / 한빛미디어</option>
							        <option>B010 / 논쟁적 UX / 제이펍</option>
							    </select>
							</div>
							<label class="control-label col-sm-3 m10">강사 ID</label>
							<div class="col-sm-9 m10">
								<select class="form-control" id="sel1">
							        <option>TCH001 / 지재환 / 1687988 / 010-8888-7474</option>
							        <option>TCH002 / 심심해 / 2222222 / 010-2444-4444</option>
							        <option>TCH003 / 김미나 / 2999999 / 010-3697-7412</option>
							        <option>TCH004 / 이정석 / 1325468 / 010-9999-9999</option>
							        <option>TCH005 / 정영희 / 2153252 / 010-7777-7777</option>
							    </select>
							</div>
							<label class="control-label col-sm-3 m10">과목 시작일</label>
							<div class="col-sm-9 m10">
								<input type="text" class="form-control" id="oubstartdate"
									name="oubstartdate" placeholder="과목 시작일(YYYY-MM-DD)"
									required="required">
							</div>
							<label class="control-label col-sm-3 m10">과목 종료일</label>
							<div class="col-sm-9 m10">
								<input type="text" class="form-control" id="oubenddate"
									name="oubenddate" placeholder="과목 종료일(YYYY-MM-DD)"
									required="required">
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-default">등록</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
				</div>
			</div>

		</div>
	</div>


	<!-- 개설 과정 수정 Modal -->
	<div class="modal fade" id="oub-mod-Modal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h5 class="modal-title">개설 과목 수정</h5>
				</div>
				<div class="modal-body">
				
				<h4 style="text-align: center;">OUB001 / 자바 네트워트 프로그래밍</h4>
				<h5 style="text-align: center;">2017/07/01 ~ 2017/07/31 / html 웹 프로그래밍 입문 / 심심해</h5>
				
				<hr>

					<form class="form-horizontal">
						<div class="form-group">
							<label class="control-label col-sm-3 m10">교재 ID</label>
							<div class="col-sm-9 m10">
								<select class="form-control" id="sel1">
							        <option>B001 / 이것이 자바다 / 한빛출판사</option>
							        <option>B002 / 쉽게 배우는 오라클 / 생능출판사</option>
							        <option>B003 / 속깊은 jQuery / 루비페이퍼</option>
							        <option selected>B004 / html 웹 프로그래밍 입문 / 생능출판사</option>
							        <option>B005 / 프런트엔드 웹 디자인 입문 / 이지스퍼블리싱</option>
							        <option>B006 / UI / UX 디자인 이론과 실습 / 한빛아카데미</option>
							        <option>B007 / 자바 네트워크 프로그래밍 / 제이펍</option>
							        <option>B008 / TCP/IP 쉽게, 더 쉽게 / 제이펍</option>
							        <option>B009 / 파이썬 라이브러리를 활용한 데이터 분석 / 한빛미디어</option>
							        <option>B010 / 논쟁적 UX / 제이펍</option>
							    </select>
							</div>
							<label class="control-label col-sm-3 m10">강사 ID</label>
							<div class="col-sm-9 m10">
								<select class="form-control" id="sel1">
							        <option>TCH001 / 지재환 / 1687988 / 010-8888-7474</option>
							        <option selected>TCH002 / 심심해 / 2222222 / 010-2444-4444</option>
							        <option>TCH003 / 김미나 / 2999999 / 010-3697-7412</option>
							        <option>TCH004 / 이정석 / 1325468 / 010-9999-9999</option>
							        <option>TCH005 / 정영희 / 2153252 / 010-7777-7777</option>
							    </select>
							</div>
							<label class="control-label col-sm-3 m10">과목 시작일</label>
							<div class="col-sm-9 m10">
								<input type="text" class="form-control" id="oubstartmoddate"
									name="oubstartdate" placeholder="과목 시작일(YYYY-MM-DD)" value="2017/07/01"
									required="required">
							</div>
							<label class="control-label col-sm-3 m10">과목 종료일</label>
							<div class="col-sm-9 m10">
								<input type="text" class="form-control" id="oubendmoddate"
									name="oubenddate" placeholder="과목 종료일(YYYY-MM-DD)" value="2017/07/31"
									required="required">
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-default">수정</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
				</div>
			</div>

		</div>
	</div>




	<!-- 개설 과정 삭제 Modal -->
	<div class="modal fade" id="oub-del-Modal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h5 class="modal-title">개설 과목 삭제</h5>
				</div>
				<div class="modal-body">
					
					<h4 style="text-align: center;">OUB001 / 자바 네트워트 프로그래밍</h4>
					<h5 style="text-align: center;">2017/07/01 ~ 2017/07/31 / html 웹 프로그래밍 입문 / 심심해</h5>
					<br>
					<h4 style="text-align: center;">개설 과목을 삭제하시겠습니까?</h4>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-default">삭제</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
				</div>
			</div>

		</div>
	</div>



</body>
</html>