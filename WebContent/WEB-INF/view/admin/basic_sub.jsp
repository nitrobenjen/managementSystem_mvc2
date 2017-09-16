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
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/style.css">

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
		
		
		$('#value').keypress(function(event){
		     if ( event.which == 13 ) {
		         $('.searchbtn').click();
		         return false;
		     }
		});
		
		
		$(document).on("click",".modifybtn", function(){
			var subject_id = $(this).val();
			var subject_name = $(this).parents("tbody tr").children().eq(1).text();			
			$(".modal-title2").html(subject_id+"&nbsp;과목 수정");
			$(".body2").html("<h4 style=\"text-align:center;font-weight:bold;\">"+subject_name+"<h4>");
			$("#focusedInput").attr("placeholder", subject_name);
			$(".subject_id").val(subject_id);
			$("#sub-mod-Modal").modal("show");
		});
		
		
		
		
		$(document).on("click",".delbtn", function(){
			var subject_id = $(this).val();
			var subject_name = $(this).parents("tbody tr").children().eq(1).text();	
			$(".subject_id").val(subject_id);
			$(".modal-title3").html(subject_id+"&nbsp;과목 삭제");
			$(".modal-body3").html("<h4 style=\"text-align:center;font-weight:bold;\">"+subject_name+"<h4><br><h4 style=\"text-align:center;\">과목을 삭제하시겠습니까?</h4>");
			$("#sub-del-Modal").modal("show");
			
		});	
		
		
		
		$(".searchbtn").on("click", function(){
			var txt ="";
			
			var key = $("#key").val();
			var value =$("#value").val();
			
			$.ajax({
				url :"adminbasicsubsearch.it",
				data : {"key":key, "value":value},
				success : function(data){
					var myObj = JSON.parse(data);
					$.each(myObj, function(idx, item){
						txt += "<tr><td>"+item.subject_id+"</td><td>"+item.subject_name+"</td><td><button type=\"button\" class=\"btn btn-default modifybtn\" value=\""+item.subject_id+"\" >수정</button></td>";
						txt += "<td><button type=\"button\" class=\"btn btn-default delbtn\" "+item.check+" value=\""+item.subject_id+"\">삭제</button></td></tr>";
									
					})
					
					$(".searchlist").html(txt) 
				
				}});			
			
		});

	});
</script>
</head>
<body>


	<div id="main">
		<div class="title">
			<img src="${pageContext.request.contextPath}/img/sist_logo.png"
				width="300px">
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
						</ul></li>
					<li><a
						href="${pageContext.request.contextPath}/adminteachermain.it">강사
							계정 관리</a></li>
					<li><a
						href="${pageContext.request.contextPath}/admin/admin301.jsp">개설
							과정/과목 관리</a></li>
					<li><a class="dropdown-toggle" data-toggle="dropdown" href="#">수강생
							관리</a>
						<ul class="dropdown-menu">
							<li><a
								href="${pageContext.request.contextPath}/admin/admin401.jsp">수강생
									관리</a></li>
							<li><a
								href="${pageContext.request.contextPath}/admin/admin402.jsp">개설
									과정</a></li>
						</ul></li>
					<li><a class="dropdown-toggle" data-toggle="dropdown" href="#">성적
							관리</a>
						<ul class="dropdown-menu">
							<li><a
								href="${pageContext.request.contextPath}/admin/admin501.jsp">과목별</a></li>
							<li><a
								href="${pageContext.request.contextPath}/admin/admin502.jsp">수강생별</a></li>
						</ul></li>
				</ul>
			</div>
		</div>

		<div class="content">


			<h3>기초 정보 관리 > 과목명</h3>

			<hr>

			<table class="table table-striped table-bordered ocu">
				<thead>
					<tr>
						<th>과목ID</th>
						<th>과목명</th>
						<th>수정</th>
						<th>삭제</th>
					</tr>
				</thead>
				<tbody class="searchlist">
					<!-- 					<tr>
						<td>SUB001</td>
						<td>자바 네트워트 프로그래밍</td>
						<td><button type="button" class="btn btn-default"
								data-toggle="modal" data-target="#sub-mod-Modal">수정</button></td>
						<td><button type="button" class="btn btn-default"
								data-toggle="modal" data-target="#sub-del-Modal">삭제</button></td>
					</tr> -->

					<c:forEach var="a" items="#{sublist}">

						<tr>
							<td>${a.subject_id}</td>
							<td>${a.subject_name}</td>
							<td><button type="button" class="btn btn-default modifybtn" 
									value="${a.subject_id}">수정</button></td>

							<td><button type="button" class="btn btn-default delbtn" ${sublistcheck[a.subject_id]} value="${a.subject_id}">삭제</button></td>

						</tr>


					</c:forEach>

				</tbody>
			</table>


			<form class="form-inline" method="post" style="text-align: center;">
				<button type="button" style="float: left;" class="btn btn-default"
					data-toggle="modal" data-target="#sub-insert-Modal">등록</button>
				<div class="form-group">
					<select class="form-control" id="key" name="key">
						<option value="name_" ${key == 'name_' ? "selected":"" }>과목명</option>
					</select>
				</div>
				<div class="form-group">
					<input type="text" class="form-control" id="value" name="value" value="${value}" required>
				</div>
				<button type="button" class="btn btn-default searchbtn">Search</button>
			</form>


		</div>
	</div>





	<!-- 과목 입력 Modal -->
	<div class="modal fade" id="sub-insert-Modal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h3 class="modal-title">과목명 등록</h3>
				</div>
				<form class="form-horizontal" action="${pageContext.request.contextPath}/adminbasicsubinsert.it" method="POST">
					<div class="modal-body">

						<h4 style="text-align: center;">새로운 과목명을 입력해 주세요.</h4>

						<hr>


						<div class="form-group">
							<label class="control-label col-sm-2">과목명</label>
							<div class="col-sm-10">
								<input class="form-control" id="subject_name" name = "subject_name" type="text">
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
	<div class="modal fade" id="sub-mod-Modal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h3 class="modal-title modal-title2">SUB001 과목명 수정</h3>
				</div>
				<form class="form-horizontal"  action="${pageContext.request.contextPath}/adminbasicsubmodify.it" method="POST">
				<div class="modal-body">
					<input type='hidden' name='subject_id' class="subject_id" value="">
					<div class="body2">
					<h4 style="text-align: center; font-weight: bold;">${subject_name}</h4>
				</div>
					<hr>
					
						<div class="form-group">
							<label class="control-label col-sm-2">과목명</label>
							<div class="col-sm-10">
								<input class="form-control" id="focusedInput" type="text" name = "subject_name" >
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
	<div class="modal fade" id="sub-del-Modal" role="dialog">
		<div class="modal-dialog">
<form class="form-horizontal" action="${pageContext.request.contextPath}/adminbasicsubdel.it" method="POST">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h3 class="modal-title3">SUB001 과목명 삭제</h3>
				</div>
				<input type='hidden' name='subject_id' class="subject_id" value="">
				<div class="modal-body modal-body3">

					<h4 style="text-align: center; font-weight: bold;">자바 네트워트
						프로그래밍</h4>

					<h4 style="text-align: center;">과목을 삭제하시겠습니까?</h4>
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