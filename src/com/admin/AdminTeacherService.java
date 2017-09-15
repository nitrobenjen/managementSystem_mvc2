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

public class AdminTeacherService {

	// 강사관리 메인페이지
	public String adminteachermain(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 액션 코드 작성
		String key = request.getParameter("key");
		String value = request.getParameter("value");

		if (key == null) {
			key = "all";
			value = "";
		}

		List<AdminTeacher> teacherlist = new ArrayList<AdminTeacher>();
		AdminTeacherDAO dao = new AdminTeacherDAO();

		teacherlist = dao.teacherlist(key, value);
		Map<String, String> teachercheck1 = new HashMap<String, String>();
		Map<String, String> teachersubcheck = new HashMap<String, String>();
		int a, b = 0;

		for (AdminTeacher m : teacherlist) {
			String disabled = "";
			a = dao.teacherdelcheck1(m.getTeacher_id());
			b = dao.teacherdelcheck2(m.getTeacher_id());
			if (a + b != 0) {
				disabled = "disabled";
			}
			teachercheck1.put(m.getTeacher_id(), disabled);
		}

		for (AdminTeacher m : teacherlist) {
			a = 0;
			String disabled = "";
			a = m.getCount_();
			if (a == 0) {
				disabled = "disabled";
			}
			teachersubcheck.put(m.getTeacher_id(), disabled);
		}


		request.setAttribute("teachercheck1", teachercheck1);
		request.setAttribute("teachersubcheck", teachersubcheck);
		request.setAttribute("teacherlist", teacherlist);
		request.setAttribute("key", key);
		request.setAttribute("value", value);

		// 뷰 페이지 주소 지정 -> 포워딩
		return "/WEB-INF/view/admin/adminteacher.jsp";

	}
	
	
	
	

	// 강사 선택과목 호출을 위한 ajax 메소드

	public void adminteachersub(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		
		AdminBasicDAO dao = new AdminBasicDAO();
		List<AdminBasic> result = new ArrayList<AdminBasic>();
		result = dao.sublist("all", "");
		
		
		Gson gson = new Gson();
	
		PrintWriter out = response.getWriter();
		
		out.write(gson.toJson(result));
        out.flush();
        out.close();


	}

	// 강사관리 메인페이지
	public String adminteacherinsert(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {

		// 액션 코드 작성
		String name_ = request.getParameter("name_");
		String phone = request.getParameter("phone");
		String ssn = request.getParameter("ssn");
		String[] sub = request.getParameterValues("sub");
		int result=0;
		int result2=0;
		
		Connection conn = null;
		conn.setAutoCommit(false);
		
		try {
			conn = DBConnection.connect();
			AdminTeacherDAO dao = new AdminTeacherDAO();
			
			String teacher_id = dao.teacherid();
			
			AdminTeacher t = new AdminTeacher();
			t.setTeacher_id(teacher_id);
			t.setTeacher_name(name_);
			t.setTeacher_phone(phone);
			t.setTeacher_ssn(ssn);
			
			result = dao.teacherinsert(t);
			
			if (sub.length != 0) {
				
				for(int i=0; i<sub.length; i++) {
					result2 = dao.teacherinsertsub(teacher_id, sub[i]);
					
					if(result2 != 100) {
						break;
					}
				}				
				
			}
			
			if(result != 100 || result2 != 100) {
				conn.rollback();
			}else {
				conn.commit();
			}
			
			
			
		} catch (ClassNotFoundException | SQLException e) {
			conn.rollback();
			e.printStackTrace();
			
		} finally  {
			
			
			try {
				DBConnection.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		
		}
		

		// 뷰 페이지 주소 지정 -> 포워딩
		return "/WEB-INF/view/redirect.jsp?url=adminteachermain.it";

	}

}