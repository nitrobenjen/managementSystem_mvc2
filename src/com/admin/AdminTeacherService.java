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

		List<AdminTeacher> teacherlist = new ArrayList<AdminTeacher>();
		AdminTeacherDAO dao = new AdminTeacherDAO();

		teacherlist = dao.teacherlist();
		Map<String, String> teacherdelcheck = new HashMap<String, String>();
		Map<String, String> teachersubcheck = new HashMap<String, String>();
		List<String> temp = dao.teacherdelcheck1(); //삭제 비활성화 (개설과목과 연결되있는지 확인)
		List<String> temp2 = dao.teacherdelcheck2(); //삭제 비활성화 (강의가능과목과 연결되있는지 확인
		
		temp.addAll(temp2);
		//삭제 비활성화
				for (int i = 0; i < teacherlist.size(); i++) {
					for (int j = 0; j < temp.size(); j++) {
						if (teacherlist.get(i).getTeacher_id().equals(temp.get(j))) {
							teacherdelcheck.put(teacherlist.get(i).getTeacher_id(), "disabled");
							break;
						}
					}
				}
		
		

		request.setAttribute("teacherdelcheck", teacherdelcheck);
		request.setAttribute("teachersubcheck", teachersubcheck);
		request.setAttribute("teacherlist", teacherlist);

		// 뷰 페이지 주소 지정 -> 포워딩
		return "/WEB-INF/view/admin/adminteacher.jsp";

	}
	
	
	
	//강사 검색
	
	
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
		}else if ("phone".equals(key)) {
			teacherlist = dao.teachersearchphone(value);
		}

		// 삭제 비활성화
		List<String> temp = dao.teacherdelcheck1();
		List<String> temp2 = dao.teacherdelcheck2();
		//강의가능한 과목 수 비활성화
		

		// 비활성화 시켜야할 id를 한 곳에 모아둔다.
		temp.addAll(temp2);

		for (int i = 0; i < teacherlist.size(); i++) {
			if(teacherlist.get(i).getCount_() == 0) {
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
	
	
	
	
	
	

	// 강사 선택과목 호출을 위한 ajax 메소드

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

	// 강사관리 메인페이지
	public String adminteacherinsert(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {

		// 액션 코드 작성
		String name_ = request.getParameter("name_");
		String phone = request.getParameter("phone");
		String ssn = request.getParameter("ssn");
		String[] sub = request.getParameterValues("sub");
		int result=0;
		
		Connection conn = null;
		
		
		try {
			conn = DBConnection.connect();
			conn.setAutoCommit(false);
			AdminTeacherDAO dao = new AdminTeacherDAO();
			
			String teacher_id = dao.teacherid();
			
			AdminTeacher t = new AdminTeacher();
			t.setTeacher_id(teacher_id);
			t.setTeacher_name(name_);
			t.setTeacher_phone(phone);
			t.setTeacher_ssn(ssn);
			
			result = dao.teacherinsert(t);
			
			if (sub != null) {
				for(int i=0; i<sub.length; i++) {
					dao.teacherinsertsub(teacher_id, sub[i]);
				}				
				
			}
			
			if(result != 100) {
				conn.rollback();
			}else {
				conn.commit();
			}
			
			
			
		} catch (ClassNotFoundException | SQLException e) {
			conn.rollback();
			e.printStackTrace();
			
		} finally  {
			if(conn != null)
				conn.close();
			
		
		}
		

		// 뷰 페이지 주소 지정 -> 포워딩
		return "/WEB-INF/view/redirect.jsp?url=adminteachermain.it";

	}

}