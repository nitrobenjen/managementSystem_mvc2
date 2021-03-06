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
		
		
		 $('#value').keypress(function(event){
		     if ( event.which == 13 ) {
		         $('.searchbtn').click();
		         return false;
		     }
		}); 
		
		
		
		
		$(document).on("click",".teacherinsert", function(){
			var txt ="";
			var teacher_id = $(this).val();
			
			$.ajax({
				url:"adminteachersub.it",
				data:{"teacher_id":teacher_id},
				success : function(data){
					var myObj = JSON.parse(data);
					$.each(myObj, function(idx, item){
						txt += "<label><input type='checkbox' name='sub' value='"+item.subject_id+"'>"+item.subject_name+"</label><br>";
						
					})
					$(".subcheckbox").html(txt) 
					$("#t-insert-Modal").modal("show");
					
				}
					
			});
		});
		
		
		
		$(document).on("click",".subcount", function(){
			var txt="";
			
			var teacher_id = $(this).val();
			var teacher_name = $(this).parents("tbody tr").children().eq(1).text();
			var teacher_phone = $(this).parents("tbody tr").children().eq(3).text();
			
			
			$.ajax({
				url : "adminteachersublist.it",
				data : {"teacher_id":teacher_id},
				success : function(data){
					var myObj = JSON.parse(data);
					$.each(myObj, function(idx, item){
						
						txt += "<tr><td>"+item.subject_id+"</td><td>"+item.subject_name+"</td></tr>";
					})
							
					$(".modal-body1 h4").text(teacher_name +"/"+ teacher_phone);		
					$(".sublist").html(txt);
				
			}})			
			
			$("#tlist-Modal").modal("show");
			
		});
		
		
		
		$(".searchbtn").on("click", function(){
			var txt, txt2 ="";
			
			var key = $("#key").val();
			var value =$("#value").val();
			
			$.ajax({
				url :"adminteachersearch.it",
				data : {"key":key, "value":value},
				success : function(data){
					var myObj = JSON.parse(data);
					$.each(myObj, function(idx, item){
						
					txt += "<tr><td>"+item.teacher_id+"</td><td>"+item.teacher_name+"</td><td>"+item.teacher_ssn+"</td><td>"+item.teacher_phone+"</td><td>"+item.teacher_hiredate+"</td>";
						txt += "<td><button type=\"button\" class=\"btn btn-default btn-sm subcount\" "+item.subcheck+"  "+item.count_+" value=\""+item.teacher_id+"\"><span class=\"badge\" id=\"Count\" >"+item.count_+"</span> 보기</button></td>";
						txt += "<td><button type=\"button\" class=\"btn btn-default modifybtn\" value=\""+item.teacher_id+"\" >수정</button></td>";
						txt += "<td><button type=\"button\" class=\"btn btn-default delbtn\" "+item.check+" value=\""+item.teacher_id+"\">삭제</button></td></tr>";
					
					})
					
					$(".searchlist").html(txt);
					
				
				}});			
		});
		
		$(document).on("click", ".modifybtn", function(){
			
			var teacher_id = $(this).val();
			var teacher_name = $(this).parents("tbody tr").children().eq(1).text();
			var teacher_ssn = $(this).parents("tbody tr").children().eq(2).text();
			var teacher_phone = $(this).parents("tbody tr").children().eq(3).text();
			var txt="";
			
			$(".teacher_id").val(teacher_id);
			$(".teacher_name").val(teacher_name);
			$(".teacher_ssn").val(teacher_ssn);
			$(".teacher_phone").val(teacher_phone);
			
			$.ajax({
				url :"adminteachermodifysub.it",
				data : {"teacher_id":teacher_id},
				success : function(data){	
					var myObj = JSON.parse(data);
					$.each(myObj, function(idx, item){
						txt += "<label><input type='checkbox' name='sub' "+item.check+" "+item.check2+" value='"+item.subject_id+"'>"+item.subject_name+"</label><br>";
					
					})
					
					$(".modifycheckbox").html(txt) 
					$("#t-mod-Modal").modal("show");
				}});
			
			
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
		<%-- <div style="${code==101?'display:none':''}">입력 실패</div> --%>
		
		<div id="menu">
		<div class="menu">
			<ul class="nav nav-pills nav-justified">
				<li><a class="dropdown-toggle"
					data-toggle="dropdown" href="#">기초 정보 관리</a>
					<ul class="dropdown-menu">
						<li><a href="${pageContext.request.contextPath}/adminbasiccourselist.it">과정명</a></li>
							<li><a href="${pageContext.request.contextPath}/adminbasicsub.it">과목명</a></li>
							<li><a href="${pageContext.request.contextPath}/adminbasicbook.it">교재명</a></li>
							<li><a href="${pageContext.request.contextPath}/adminbasicclass.it">강의실명</a></li>
					</ul>
				</li>
				
				<li class="active"><a href="${pageContext.request.contextPath}/adminteachermain.it">강사 계정 관리</a></li>
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


			<h3>강사 계정 관리</h3>

			<hr>

			<table class="table table-striped table-bordered ocu">
				<thead>
					<tr>
						<th>강사ID</th>
						<th>강사명</th>
						<th>주민번호 뒷자리</th>
						<th>전화번호</th>
						<td>등록일</td>
						<th>강의 가능 과목</th>
						<th>수정</th>
						<th>삭제</th>
					</tr>
				</thead>
				<tbody class="searchlist">
<!-- 					<tr>
						<td>TCH001</td>
						<td>지재환</td>
						<td>1687988</td>
						<td>010-8888-7474</td>
						<td>15/05/28</td>
						<td><button type="button" class="btn btn-default btn-sm" data-toggle="modal" data-target="#tlist-Modal">
								<span class="badge" id="Count">5</span> 보기
							</button></td>
						<td><button type="button" class="btn btn-default"
								data-toggle="modal" data-target="#t-mod-Modal">수정</button></td>
						<td><button type="button" class="btn btn-default"
								data-toggle="modal" data-target="#t-del-Modal">삭제</button></td>
					</tr> -->
					<c:forEach var="a" items="${teacherlist}">
					<tr>
						<td>${a.teacher_id}</td>
						<td>${a.teacher_name}</td>
						<td>${a.teacher_ssn}</td>
						<td>${a.teacher_phone}</td>
						<td>${a.teacher_hiredate}</td>
						<td><button type="button" class="btn btn-default btn-sm subcount"  ${a.count_==0?"disabled":"" } value="${a.teacher_id}">
								<span class="badge" id="Count" >${a.count_}</span> 보기
							</button></td>
						<td><button type="button" class="btn btn-default modifybtn" value="${a.teacher_id}">수정</button></td>
						<td><button type="button" class="btn btn-default delbtn" ${teacherdelcheck[a.teacher_id]} value="${a.teacher_id}">삭제</button></td>
					</tr>
							
					</c:forEach>
				</tbody>
			</table>


			
				<button type="button" style="float: left;" class="btn btn-default teacherinsert">등록</button>
				<form class="form-inline" method="post" action="${pageContext.request.contextPath}/adminteachermain.it"  style="text-align: center;">
				<div class="form-group">
					<select class="form-control" id="key" name="key">
						<option value="name_" ${key == 'name_' ? "selected":"" }>이름</option>
						<option value="phone" ${key == 'phone' ? "selected":"" }>전화번호</option>
					
					</select>
				</div>
				<div class="form-group">
					<input type="text" class="form-control" id="value" name="value" value="${value}"
						required>
				</div>
				<button type="button" class="btn btn-default searchbtn">Search</button>
			</form>


		</div>
	</div>



	<!-- 강의 가능 과목 Modal -->
	<div class="modal fade" id="tlist-Modal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h5 class="modal-title">강의 가능 과목</h5>
				</div>
				<div class="modal-body1 modal-body">
				
					<h4 style="text-align:center;">TCH001 지재환 / 010-8888-7474</h4>

					<hr>
					
					<table class="table table-striped table-bordered ocu">
				<thead>
					<tr>
						<th>과목ID</th>
						<th>과목명</th>
					</tr>
				</thead>
				<tbody class="sublist">
					<tr>
						<td>SUB001</td>
						<td>자바 네트워트 프로그래밍</td>
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



	<!-- 강사 계정 등록 Modal -->
	<div class="modal fade" id="t-insert-Modal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h5 class="modal-title">강사 계정 등록</h5>
				</div>
				<form class="form-horizontal" action="${pageContext.request.contextPath}/adminteacherinsert.it" method="POST">
				<div class="modal-body">
				
					
						<div class="form-group">
							<label class="control-label col-sm-3 m10">이름</label>
							<div class="col-sm-9 m10">
								<input class="form-control" name="name_" type="text">
							</div>
							<label class="control-label col-sm-3 m10">전화번호</label>
							<div class="col-sm-9 m10">
								<input class="form-control" name="phone" type="text">
							</div>
							<label class="control-label col-sm-3 m10">주민번호</label>
							<div class="col-sm-9 m10">
								<input class="form-control" name="ssn" type="text">
							</div>
							<label class="control-label col-sm-3 m10">강의 가능 과목</label>
							<div class="col-sm-9 m10">
								<div class="checkbox subcheckbox">
								<!-- <label><input type="checkbox" value="SUB001">자바 네트워트 프로그래밍</label>
								</div> -->
								
								<!-- <label><input type="checkbox" value="SUB001">자바 네트워트 프로그래밍</label> -->								
								
								</div>
								
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


	<!-- 강사 계정 수정 Modal -->
	<div class="modal fade" id="t-mod-Modal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h5 class="modal-title2 modal-title">강사 계정 수정</h5>
				</div>
				<form class="form-horizontal" action="${pageContext.request.contextPath}/adminteachermodify.it" method="post">
				<div class="modal-body2 modal-body">
				
					
						<div class="form-group">
							<label class="control-label col-sm-3">아이디</label>
							<div class="col-sm-9">
								<input class="form-control teacher_id" name="teacher_id"  type="text" value="TCH001" readonly>
							</div>
							<label class="control-label col-sm-3 m10">이름</label>
							<div class="col-sm-9 m10">
								<input class="form-control teacher_name" name="teacher_name"  type="text" value="지재환">
							</div>
							<label class="control-label col-sm-3 m10">전화번호</label>
							<div class="col-sm-9 m10">
								<input class="form-control teacher_phone" name="teacher_phone"  type="text" value="010-8888-7474">
							</div>
							<label class="control-label col-sm-3 m10">주민번호</label>
							<div class="col-sm-9 m10">
								<input class="form-control teacher_ssn" name="teacher_ssn" type="text" value="1687988">
							</div>
							<label class="control-label col-sm-3 m10">강의 가능 과목</label>
							<div class="col-sm-9 m10">
							
								<div class="checkbox modifycheckbox">
								
								<!-- <label><input type="checkbox" value="SUB001" checked>자바 네트워트 프로그래밍</label> -->
								
								
								</div>
								
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




	<!-- 강사 삭제 Modal -->
	<div class="modal fade" id="t-del-Modal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h5 class="modal-title">TCH001 강사 삭제</h5>
				</div>
				<div class="modal-body">

					<h4 style="text-align:center;font-weight:bold;">지재환 / 010-8888-7474</h4>
					
					<h4 style="text-align:center;">강사를 삭제하시겠습니까?</h4>
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