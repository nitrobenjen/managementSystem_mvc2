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
		
		$(".modifybtn").on("click", function(){
			var book_id = $(this).val();
			var book_name = $(this).parents("tbody tr").children().eq(1).text();			
			$(".modal-title2").html(book_id+"&nbsp;교재 수정");
			$(".body2").html("<h4 style=\"text-align:center;font-weight:bold;\">"+book_name+"<h4>");
			$("#focusedInput").attr("placeholder", book_name);
			$(".book_id").val(book_id);
			$("#b-mod-Modal").modal("show");
		});
		
		
		$(".delbtn").on("click", function(){
			var book_id = $(this).val();
			var book_name = $(this).parents("tbody tr").children().eq(1).text();
			
			$(".book_id").val(book_id);
			$(".modal-title3").html(book_id+"&nbsp;교재 삭제");
			$(".modal-body3").html("<h4 style=\"text-align:center;font-weight:bold;\">"+book_name+"<h4><br><h4 style=\"text-align:center;\">교재를 삭제하시겠습니까?</h4>");
			$("#b-del-Modal").modal("show");
			
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


			<h3>기초 정보 관리 > 교재명</h3>

			<hr>

			<table class="table table-striped table-bordered ocu">
				<thead>
					<tr>
						<th>교재ID</th>
						<th>교재명</th>
						<th>출판사</th>
						<th>수정</th>
						<th>삭제</th>
					</tr>
				</thead>
				<tbody>
					<!-- 					<tr>
						<td>B001</td>
						<td>이것이 자바다</td>
						<td>한빛출판사</td>
						<td><button type="button" class="btn btn-default"
								data-toggle="modal" data-target="#b-mod-Modal">수정</button></td>
						<td><button type="button" class="btn btn-default"
								data-toggle="modal" data-target="#b-del-Modal">삭제</button></td>
					</tr> -->

					<c:forEach var="a" items="#{booklist}">

						<tr>
							<td>${a.book_id}</td>
							<c:choose>

								<c:when test="${empty a.book_imgname}">
									<td>${a.book_name}</td>
								</c:when>

								<c:otherwise>
									<td><a
										href="${pageContext.request.contextPath}/picture/${a.book_imgname}"
										target="_blank" data-photo="img1" id="test">${a.book_name}</a></td>
								</c:otherwise>

							</c:choose>


							<%-- <td><a href="${pageContext.request.contextPath}/picture/${a.book_imgname}" target="_blank">${a.book_name}</a></td> --%>

							<td>${a.publisher}</td>
							<td><button type="button" class="btn btn-default modifybtn"
									value="${a.book_id}">수정</button></td>

							<td><button type="button" class="btn btn-default delbtn"
									${booklistcheck[a.book_id]} value="${a.book_id}">삭제</button></td>

						</tr>


					</c:forEach>



				</tbody>
			</table>


			<form class="form-inline" method="post"
				action="${pageContext.request.contextPath}/adminbasicbook.it"
				style="text-align: center;">
				<button type="button" style="float: left;" class="btn btn-default"
					data-toggle="modal" data-target="#b-insert-Modal">등록</button>
				<div class="form-group">
					<select class="form-control" id="key" name="key">
						<option value="name_" ${key == 'name_' ? "selected":"" }>교재명</option>
						<option value="publisher" ${key == 'publisher' ? "selected":"" }>출판사</option>
					</select>
				</div>
				<div class="form-group">
					<input type="text" class="form-control" id="value" name="value"
						value="${value}" required>
				</div>
				<button type="submit" class="btn btn-default">Search</button>
			</form>


		</div>
	</div>






	<!-- 교재 입력 Modal -->
	<div class="modal fade" id="b-insert-Modal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h3 class="modal-title">교재명 등록</h3>
				</div>
				<form class="form-horizontal"
					action="${pageContext.request.contextPath}/adminbasicbookinsert.it"
					method="post" enctype="multipart/form-data">
					<div class="modal-body">

						<h4 style="text-align: center;">새로운 교재명을 입력해 주세요.</h4>

						<hr>


						<div class="form-group">
							<label class="control-label col-sm-2">교재명</label>
							<div class="col-sm-10">
								<input class="form-control" id="book_name" name="book_name"
									type="text">
							</div>
							<label class="control-label col-sm-2 m10">출판사</label>
							<div class="col-sm-10 m10">
								<input class="form-control" id="publisher" name="publisher"
									type="text">
							</div>
							<label class="control-label col-sm-2 m10">사진</label>
							<div class="col-sm-10 m10">
								<input type="file" class="form-control" id="book_imgname"
									name="book_imgname" required="required"> <span
									class="help-block">(only .jpg or .png, 1M byte 이내)</span>
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
	<div class="modal fade" id="b-mod-Modal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h3 class="modal-title modal-title2">B001 교재명 수정</h3>
				</div>
				
				<form class="form-horizontal" action="${pageContext.request.contextPath}/adminbasicbookmodify.it" method="POST" enctype="multipart/form-data">
				<div class="modal-body">
					<input type='hidden' name='book_id' class="book_id" value="">
					<div class="body2">
					<h4 style="text-align: center; font-weight: bold;">이것이 자바다</h4>
					</div>
					<hr>
					
						<div class="form-group">
							<label class="control-label col-sm-2">교재명</label>
							<div class="col-sm-10">
								<input class="form-control" name="book_name" type="text">
							</div>
							<label class="control-label col-sm-2 m10">출판사</label>
							<div class="col-sm-10 m10">
								<input class="form-control" name="publisher" type="text">
							</div>
							<label class="control-label col-sm-2 m10">사진</label>
							<div class="col-sm-10 m10">
								<input type="file" class="form-control" 
									name="book_imgname" required="required"> <span
									class="help-block">(only .jpg or .png, 1M byte 이내)</span>
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
	<div class="modal fade" id="b-del-Modal" role="dialog">
		<div class="modal-dialog">
<form class="form-horizontal" action="${pageContext.request.contextPath}/adminbasicbookdel.it" method="POST" enctype="multipart/form-data">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h3 class="modal-title3">B001 교재 삭제</h3>
				</div>
				<input type='hidden' name='book_id' class="book_id" value="">
				<div class="modal-body3">

					<h4 style="text-align: center; font-weight: bold;">이것이 자바다</h4>

					<h4 style="text-align: center;">교재를 삭제하시겠습니까?</h4>
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