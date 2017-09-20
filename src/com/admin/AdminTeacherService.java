package com.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.login.LoginDAO;

public class AdminTeacherService {

	// 강사관리 메인페이지
	public String adminteachermain(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 액션 코드 작성

		List<AdminTeacher> teacherlist = new ArrayList<AdminTeacher>();
		AdminTeacherDAO dao = new AdminTeacherDAO();

		teacherlist = dao.teacherlist();
		Map<String, String> teacherdelcheck = new HashMap<String, String>();
		List<String> temp = dao.teacherdelcheck1(); // 삭제 비활성화 (개설과목과 연결되있는지 확인)
		// 삭제 비활성화
		for (int i = 0; i < teacherlist.size(); i++) {
			for (int j = 0; j < temp.size(); j++) {
				if (teacherlist.get(i).getTeacher_id().equals(temp.get(j))) {
					teacherdelcheck.put(teacherlist.get(i).getTeacher_id(), "disabled");
					break;
				}
			}
		}

		request.setAttribute("teacherdelcheck", teacherdelcheck);
		request.setAttribute("teacherlist", teacherlist);

		// 뷰 페이지 주소 지정 -> 포워딩
		return "/WEB-INF/view/admin/adminteacher.jsp";

	}

	// 강사 검색
	public void adminteachersearch(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8");

		// 액션 코드 작성
		String key = request.getParameter("key");
		String value = request.getParameter("value");

		List<AdminTeacher> teacherlist = new ArrayList<AdminTeacher>();
		AdminTeacherDAO dao = new AdminTeacherDAO();

		// 검색
		if ("name_".equals(key)) {
			teacherlist = dao.teachersearchname(value);
		} else if ("phone".equals(key)) {
			teacherlist = dao.teachersearchphone(value);
		}

		// 삭제 비활성화
		List<String> temp = dao.teacherdelcheck1();
		// 강의가능한 과목 수 비활성화


		for (int i = 0; i < teacherlist.size(); i++) {
			if (teacherlist.get(i).getCount_() == 0) {
				teacherlist.get(i).setSubcheck("disabled");
			}
			for (int j = 0; j < temp.size(); j++) {
				if (teacherlist.get(i).getTeacher_id().equals(temp.get(j))) {
					teacherlist.get(i).setCheck("disabled");
					break;
				}
			}

		}

		Gson gson = new Gson();
		PrintWriter out = response.getWriter();
		out.write(gson.toJson(teacherlist));
		out.flush();
		out.close();

	}

	// 강사 강의가능한 과목 입력을 위한 목록 출력

	public void adminteachersub(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");

		AdminBasicDAO dao = new AdminBasicDAO();
		List<AdminBasic> result = new ArrayList<AdminBasic>();
		result = dao.sublist();

		Gson gson = new Gson();

		PrintWriter out = response.getWriter();

		out.write(gson.toJson(result));
		out.flush();
		out.close();

	}

	// 강사관리 등록
	public String adminteacherinsert(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {

		// 액션 코드 작성

		String name_ = request.getParameter("name_");
		String phone = request.getParameter("phone");
		String ssn = request.getParameter("ssn");
		String[] sub = request.getParameterValues("sub");
		int result, result1, result2 = 0;

		// 트랜잭션 처리를 위한 커넥션 객체 생성
		// 입력하게될 강사의 PK를 미리 가져와 그것으로 처리한다.
		Connection conn = null;
		String code = "";

		try {
			conn = DBConnection.connect();
			conn.setAutoCommit(false);
			AdminTeacherDAO dao = new AdminTeacherDAO();
			LoginDAO ldao = new LoginDAO();

			String teacher_id = dao.teacherid(conn);

			AdminTeacher t = new AdminTeacher();
			t.setTeacher_id(teacher_id);
			t.setTeacher_name(name_);
			t.setTeacher_phone(phone);
			t.setTeacher_ssn(ssn);

			result = dao.teacherinsert(t); // 강사 기본정보가 제대로 들어갔는지 확인
			result1 = ldao.teacherinsert(teacher_id, ssn, conn);

			// 선택한 강의가능한 목록을 넣는 쿼리를 실행하는 메소드로 중간에 오류가 발생할 시 중지를 한다.
			if (sub != null) {
				for (int i = 0; i < sub.length; i++) {
					result2 = dao.teacherinsertsub(teacher_id, sub[i], conn);
					if (result2 != 100)
						break;
				}

			}

			if (result == 100 && result1 == 100 && result2 == 100) {

				conn.commit();
			} else {
				code = "101";

				conn.rollback();
			}

		} catch (ClassNotFoundException | SQLException e) {
			code = "101";
			conn.rollback();
			e.printStackTrace();

		} finally {
			if (conn != null)
				conn.close();

		}

		request.setAttribute("code", code);
		// 뷰 페이지 주소 지정 -> 포워딩
		return "/WEB-INF/view/redirect.jsp?url=adminteachermain.it";

	}

	// 특정 강사의 강의가능한 과목 목록 출력

	public void adminteachersublist(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");

		String teacher_id = request.getParameter("teacher_id");
		AdminTeacherDAO dao = new AdminTeacherDAO();
		List<AdminTeacher> result = new ArrayList<AdminTeacher>();
		result = dao.teachersublist(teacher_id);

		Gson gson = new Gson();

		PrintWriter out = response.getWriter();

		out.write(gson.toJson(result));
		out.flush();
		out.close();

	}

	// 강사 강의가능한 과목 수정을 위한 목록 출력

	public void adminteachermodifysub(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		AdminBasicDAO subdao = new AdminBasicDAO();
		List<AdminBasic> totalsub = new ArrayList<AdminBasic>(); // 전체 과목 목록
		totalsub = subdao.sublist();
		String teacher_id = request.getParameter("teacher_id");
		AdminTeacherDAO teachdao = new AdminTeacherDAO();
		List<AdminTeacher> result = new ArrayList<AdminTeacher>();
		List<String> result2 = new ArrayList<String>();
		result = teachdao.teachersublist(teacher_id); // 특정 강사의 강의가능한 과목 목록
		result2 = teachdao.teachernocheck(teacher_id);// 특정 강사의 강의가능한 과목중 체크 해제 하면 안되는 목록

		for (int i = 0; i < totalsub.size(); i++) {

			for (int j = 0; j < result.size(); j++) {
				if (totalsub.get(i).getSubject_id().equals(result.get(j).getSubject_id())) {
					totalsub.get(i).setCheck("checked"); // 강의가능한 목록 표시를 위해 해당 인덱스에 checked를 삽입한다.

				}
			}

			for (int j = 0; j < result2.size(); j++) {
				if (totalsub.get(i).getSubject_id().equals(result2.get(j))) {
					totalsub.get(i).setCheck2("disabled"); // 체크 해제 하면 안되는 목록에 disabled속성을 추가한다.
					break;
				}
			}
		}

		// totalsub.get(i).setCheck("disabled"); // 과목에 연결된 과목은 체크해제를 못하게 만든다.

		Gson gson = new Gson();

		PrintWriter out = response.getWriter();

		out.write(gson.toJson(totalsub));
		out.flush();
		out.close();

	}

	// 강사관리 수정
	public String adminteachermodify(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {

		// 액션 코드 작성

		String teacher_id = request.getParameter("teacher_id");
		String teacher_name = request.getParameter("teacher_name");
		String teacher_phone = request.getParameter("teacher_phone");
		String teacher_ssn = request.getParameter("teacher_ssn");
		String[] sub = request.getParameterValues("sub");
		int result, result1, result2 = 100;

		// 트랜잭션 처리를 위한 커넥션 객체 생성
		// 입력하게될 강사의 PK를 미리 가져와 그것으로 처리한다.
		Connection conn = null;
		String code = "";

		try {
			conn = DBConnection.connect();
			conn.setAutoCommit(false);
			AdminTeacherDAO dao = new AdminTeacherDAO();
		
			result = dao.teacherdelsub(teacher_id, conn); // 강의가능과목 수정을 위한 목록 삭제
			
			AdminTeacher t = new AdminTeacher();
			t.setTeacher_id(teacher_id);
			t.setTeacher_name(teacher_name);
			t.setTeacher_phone(teacher_phone);
			t.setTeacher_ssn(teacher_ssn);
			result1 = dao.teachmodifyinfo(t, conn); // 강사 기본정보 수정

			// 선택한 강의가능한 목록을 넣는 쿼리를 실행하는 메소드로 중간에 오류가 발생할 시 중지를 한다.

			if (sub != null) {
				for (int i = 0; i < sub.length; i++) {
					result2 = dao.teacherinsertsub(teacher_id, sub[i], conn);
					if (result2 != 100)
						break;
				}
			}

			if (result ==100 && result1 == 100 && result2 == 100) {
				conn.commit();
			} else {
				code = "101";
				conn.rollback();
			}

		} catch (ClassNotFoundException | SQLException e) {
			code = "101";
			conn.rollback();
			e.printStackTrace();

		} finally {
			if (conn != null)
				conn.close();

		}

		request.setAttribute("code", code);
		// 뷰 페이지 주소 지정 -> 포워딩
		return "/WEB-INF/view/redirect.jsp?url=adminteachermain.it";

	}

}