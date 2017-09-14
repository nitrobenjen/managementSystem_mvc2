<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">	
	
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
		
		
		$(".modifybtn").on("click", function(){
			var class_id = $(this).val();
			var class_name = $(this).parents("tbody tr").children().eq(1).text();
			var jungwon = $(this).parents("tbody tr").children().eq(2).text();
			$(".modal-title2").html(class_id+"&nbsp;강의실 수정");
			$(".body2").html("<h4 style=\"text-align:center;font-weight:bold;\">"+class_name+"<h4>");
			$(".class_name").attr("placeholder", class_name);
			$(".jungwon").attr("placeholder", jungwon)
			$(".class_id").val(class_id);
			$("#ca-mod-Modal").modal("show");
		});
		
		
		
		$(".delbtn").on("click", function(){
			var class_id = $(this).val();
			var class_name = $(this).parents("tbody tr").children().eq(1).text();	
			$(".class_id").val(class_id);
			$(".modal-title3").html(class_id+"&nbsp;강의실 삭제");
			$(".modal-body3").html("<h4 style=\"text-align:center;font-weight:bold;\">"+class_name+"<h4><br><h4 style=\"text-align:center;\">과목을 삭제하시겠습니까?</h4>");
			$("#ca-del-Modal").modal("show");
			
		});	
		
		
	});
</script>
</head>
<body>


	<div id="main">
		<div class="title">
			<img src="${pageContext.request.contextPath}/img/sist_logo.png" width="300px">
			<div class="login">관리자 님 │ 로그아웃</div>
		</div>
		<div id="menu">
		<div class="menu">
			<ul class="nav nav-pills nav-justified">
				<li class="active"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#">기초 정보 관리</a>
					<ul class="dropdown-menu">
						<li><a href="${pageContext.request.contextPath}/adminbasiccourselist.it">과정명</a></li>
							<li><a href="${pageContext.request.contextPath}/adminbasicsub.it">과목명</a></li>
							<li><a href="${pageContext.request.contextPath}/adminbasicbook.it">교재명</a></li>
							<li><a href="${pageContext.request.contextPath}/adminbasicclass.it">강의실명</a></li>
					</ul>
				</li>
				<li><a href="${pageContext.request.contextPath}/adminteachermain.it">강사 계정 관리</a></li>
				<li><a href="${pageContext.request.contextPath}/admin/admin301.jsp">개설 과정/과목 관리</a></li>
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


			<h3>기초 정보 관리 > 강의실</h3>

			<hr>

			<table class="table table-striped table-bordered ocu">
				<thead>
					<tr>
						<th>강의실ID</th>
						<th>강의실명</th>
						<th>정원</th>
						<th>수정</th>
						<th>삭제</th>
					</tr>
				</thead>
				<tbody>
<!-- 					<tr>
						<td>CA001</td>
						<td>제1강의실</td>
						<td>30</td>
						<td><button type="button" class="btn btn-default"
								data-toggle="modal" data-target="#ca-mod-Modal">수정</button></td>
						<td><button type="button" class="btn btn-default"
								data-toggle="modal" data-target="#ca-del-Modal">삭제</button></td>
					</tr> -->
					
					<c:forEach var="a" items="#{classlist}">

						<tr>
							<td>${a.class_id}</td>
							<td>${a.class_name}</td>
							<td>${a.jungwon}</td>
							<td><button type="button" class="btn btn-default modifybtn"  ${classmodifycheck[a.class_id]}
									value="${a.class_id}">수정</button></td>

							<td><button type="button" class="btn btn-default delbtn" ${classlistcheck[a.class_id]} value="${a.class_id}">삭제</button></td>

						</tr>


					</c:forEach>
					
					
				</tbody>
			</table>


			<form class="form-inline" method="post" action="${pageContext.request.contextPath}/adminbasicclass.it"  style="text-align: center;">
				<button type="button" style="float: left;" class="btn btn-default"
					data-toggle="modal" data-target="#ca-insert-Modal">등록</button>
				<div class="form-group">
					<select class="form-control" id="key" name="key">
						<option value="name_" ${key == 'name_' ? "selected":"" }>강의실명</option>
					</select>
				</div>
				<div class="form-group">
					<input type="text" class="form-control" id="value" name="value" value="${value}"
						required>
				</div>
				<button type="submit" class="btn btn-default">Search</button>
			</form>


		</div>
	</div>
	

	<!-- 강의실 입력 Modal -->
	<div class="modal fade" id="ca-insert-Modal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h5 class="modal-title">강의실 등록</h5>
				</div>
				<form class="form-horizontal" action="${pageContext.request.contextPath}/adminbasicclassinsert.it" method="POST">
				<div class="modal-body">
				
					<h4 style="text-align:center;">새로운 강의실을 입력해 주세요.</h4>

					<hr>					
						<div class="form-group">
							<label class="control-label col-sm-2">강의실명</label>
							<div class="col-sm-10">
								<input class="form-control " name="class_name" type="text">
							</div>
							<label class="control-label col-sm-2" style="margin-top: 10px;">정원</label>
							<div class="col-sm-10" style="margin-top: 10px;">
								<input class="form-control" name="jungwon" type="number">
							</div>
						</div>
					
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-default">등록</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
				</div>
				</form>
			</div>

		</div>
	</div>





	<!-- 과목 수정 Modal -->
	<div class="modal fade" id="ca-mod-Modal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h5 class="modal-title2">CA001 강의실 수정</h5>
				</div>
				<form class="form-horizontal"  action="${pageContext.request.contextPath}/adminbasicclassmodify.it" method="POST">
				<div class="modal-body">
				<input type='hidden' name='class_id' class="class_id" value="">
					<div class="body2">
					<h4 style="text-align:center;font-weight:bold;">제1강의실</h4>
					</div>
					<hr>
				
						<div class="form-group">
							<label class="control-label col-sm-2">강의실명</label>
							<div class="col-sm-10">
								<input class="form-control class_name" type="text" name="class_name">
							</div>
							<label class="control-label col-sm-2 " style="margin-top: 10px;">정원</label>
							<div class="col-sm-10" style="margin-top: 10px;">
								<input class="form-control jungwon" type="number" name="jungwon" >
							</div>
						</div>
					
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-default">수정</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
				</div>
				</form>
			</div>

		</div>
	</div>



	<!-- 과정 삭제 Modal -->
	<div class="modal fade" id="ca-del-Modal" role="dialog">
		<div class="modal-dialog">
<form class="form-horizontal" action="${pageContext.request.contextPath}/adminbasicclassdel.it" method="POST">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h5 class="modal-title3">CA001 강의실 삭제</h5>
				</div>
				
				<input type='hidden' name='class_id' class="class_id" value="">
				<div class="modal-body3">

					<h4 style="text-align:center;font-weight:bold;">제1강의실</h4>
					
					<h4 style="text-align:center;">강의실을 삭제하시겠습니까?</h4>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-default">삭제</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
				</div>
				
			</div>
</form>
		</div>
	</div>



</body>
</html>