package com.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.oreilly.servlet.MultipartRequest;
import com.util.*;

public class AdminBasicService {

	//////////////////////////////////////////////////////////////////
	// 기본정보 > 과정리스트
	public String adminbasiccourselist(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 액션 코드 작성
		List<AdminBasic> courselist = new ArrayList<AdminBasic>();
		AdminBasicDAO dao = new AdminBasicDAO();

		courselist = dao.courselist();

		// 삭제 비활성화 처리를 위한 메소드호출
		List<String> temp = dao.courselistcheck();
		Map<String, String> courselistcheck = new HashMap<String, String>();

		for (int i = 0; i < courselist.size(); i++) {
			for (int j = 0; j < temp.size(); j++) {
				if (courselist.get(i).getCourse_id().equals(temp.get(j))) {
					courselistcheck.put(courselist.get(i).getCourse_id(), "disabled");
					break;
				}
			}
		}

		request.setAttribute("courselistcheck", courselistcheck);
		request.setAttribute("courselist", courselist);

		// 뷰 페이지 주소 지정 -> 포워딩
		return "/WEB-INF/view/admin/basic_course.jsp";
	}

	// 기본정보 > 과정 > 검색
	public void adminbasiccoursearch(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8");

		// 액션 코드 작성
		String key = request.getParameter("key");
		String value = request.getParameter("value");

		List<AdminBasic> courselist = new ArrayList<AdminBasic>();
		AdminBasicDAO dao = new AdminBasicDAO();

		// 검색
		if ("name_".equals(key)) {
			courselist = dao.coursesearchname(value);
		}

		// 삭제 비활성화 처리를 위한 메소드호출
		List<String> temp = new ArrayList<String>();
		temp = dao.courselistcheck();

		for (int i = 0; i < courselist.size(); i++) {
			for (int j = 0; j < temp.size(); j++) {
				if (courselist.get(i).getCourse_id().equals(temp.get(j))) {
					courselist.get(i).setCheck("disabled");
					break;
				}
			}
		}

		Gson gson = new Gson();
		PrintWriter out = response.getWriter();
		out.write(gson.toJson(courselist));
		out.flush();
		out.close();

	}

	// 기본정보 과정 입력
	public String adminbasiccourseinsert(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {

		// 액션 코드 작성
		String course_name = request.getParameter("course_name");
		AdminBasicDAO dao = new AdminBasicDAO();
		int code = dao.courseinsert(course_name);

		// 뷰 페이지 주소 지정 -> 포워딩
		return "/WEB-INF/view/redirect.jsp?url=adminbasiccourselist.it&code=" + code;

	}

	// 기본정보 과정 수정
	public String adminbasiccoursemodify(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {

		// 액션 코드 작성
		String course_id = request.getParameter("course_id");
		String course_name = request.getParameter("course_name");
		AdminBasic value = new AdminBasic();
		value.setCourse_id(course_id);
		value.setCourse_name(course_name);

		AdminBasicDAO dao = new AdminBasicDAO();
		int code = dao.coursemodify(value);

		// 뷰 페이지 주소 지정 -> 포워딩
		return "/WEB-INF/view/redirect.jsp?url=adminbasiccourselist.it&code=" + code;

	}

	// 기본정보 과정 삭제
	public String adminbasiccoursedel(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {

		// 액션 코드 작성
		String course_id = request.getParameter("course_id");
		String course_name = request.getParameter("course_name");
		AdminBasic value = new AdminBasic();
		value.setCourse_id(course_id);
		value.setCourse_name(course_name);

		AdminBasicDAO dao = new AdminBasicDAO();
		int code = dao.coursedel(course_id);

		// 뷰 페이지 주소 지정 -> 포워딩
		return "/WEB-INF/view/redirect.jsp?url=adminbasiccourselist.it&code=" + code;

	}

	/////////////////// 기초 과목/////////////////////////

	// 과목 리스트
	public String adminbasicsub(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 액션 코드 작성

		List<AdminBasic> sublist = new ArrayList<AdminBasic>();
		AdminBasicDAO dao = new AdminBasicDAO();
		sublist = dao.sublist();

		// 삭제 비활성화
		List<String> temp = dao.sublistcheck();
		List<String> temp2 = dao.sublistcheck2();
		Map<String, String> sublistcheck = new HashMap<String, String>();

		// 비활성화 시켜야할 id를 한 곳에 모아둔다.
		for (String m : temp2) {
			temp.add(m);
		}

		for (int i = 0; i < sublist.size(); i++) {
			for (int j = 0; j < temp.size(); j++) {
				if (sublist.get(i).getSubject_id().equals(temp.get(j))) {
					sublistcheck.put(sublist.get(i).getSubject_id(), "disabled");
					break;
				}
			}
		}

		request.setAttribute("sublistcheck", sublistcheck);
		request.setAttribute("sublist", sublist);

		// 뷰 페이지 주소 지정 -> 포워딩
		return "/WEB-INF/view/admin/basic_sub.jsp";

	}

	// 기본정보 > 과목 > 검색
	public void adminbasicsubsearch(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8");

		// 액션 코드 작성
		String key = request.getParameter("key");
		String value = request.getParameter("value");

		List<AdminBasic> sublist = new ArrayList<AdminBasic>();
		AdminBasicDAO dao = new AdminBasicDAO();

		// 검색
		if ("name_".equals(key)) {
			sublist = dao.subsearchname(value);
		}

		// 삭제 비활성화
		List<String> temp = dao.sublistcheck();
		List<String> temp2 = dao.sublistcheck2();

		// 비활성화 시켜야할 id를 한 곳에 모아둔다.
		for (String m : temp2) {
			temp.add(m);
		}

		for (int i = 0; i < sublist.size(); i++) {
			for (int j = 0; j < temp.size(); j++) {
				if (sublist.get(i).getSubject_id().equals(temp.get(j))) {
					sublist.get(i).setCheck("disabled");
					break;
				}
			}
		}

		Gson gson = new Gson();
		PrintWriter out = response.getWriter();
		out.write(gson.toJson(sublist));
		out.flush();
		out.close();

	}

	// 과목 입력
	public String adminbasicsubinsert(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {

		// 액션 코드 작성
		String subject_name = request.getParameter("subject_name");
		AdminBasicDAO dao = new AdminBasicDAO();
		int code = dao.subinsert(subject_name);

		// 뷰 페이지 주소 지정 -> 포워딩
		return "/WEB-INF/view/redirect.jsp?url=adminbasicsub.it&code=" + code;

	}

	// 과목 수정
	public String adminbasicsubmodify(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {

		// 액션 코드 작성
		String subject_id = request.getParameter("subject_id");
		String subject_name = request.getParameter("subject_name");
		AdminBasic value = new AdminBasic();
		value.setSubject_id(subject_id);
		value.setSubject_name(subject_name);

		AdminBasicDAO dao = new AdminBasicDAO();
		int code = dao.submodify(value);

		// 뷰 페이지 주소 지정 -> 포워딩
		return "/WEB-INF/view/redirect.jsp?url=adminbasicsub.it&code=" + code;

	}

	// 과목 삭제

	public String adminbasicsubdel(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {

		// 액션 코드 작성
		String subject_id = request.getParameter("subject_id");
		String subject_name = request.getParameter("subject_name");
		AdminBasic value = new AdminBasic();
		value.setSubject_id(subject_id);
		value.setSubject_name(subject_name);

		AdminBasicDAO dao = new AdminBasicDAO();
		int code = dao.subdel(subject_id);

		// 뷰 페이지 주소 지정 -> 포워딩
		return "/WEB-INF/view/redirect.jsp?url=adminbasicsub.it&code=" + code;

	}

	/////////////////// 기초 교재/////////////////////////

	// 교재 리스트
	public String adminbasicbook(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 액션 코드 작성

		List<AdminBasic> booklist = new ArrayList<AdminBasic>();
		AdminBasicDAO dao = new AdminBasicDAO();

		booklist = dao.booklist();
		Map<String, String> booklistcheck = new HashMap<String, String>();
		List<String> temp = dao.booklistcheck();

		for (int i = 0; i < booklist.size(); i++) {
			for (int j = 0; j < temp.size(); j++) {
				if (booklist.get(i).getBook_id().equals(temp.get(j))) {
					booklistcheck.put(booklist.get(i).getBook_id(), "disabled");
					break;
				}
			}
		}

		request.setAttribute("booklistcheck", booklistcheck);
		request.setAttribute("booklist", booklist);

		// 뷰 페이지 주소 지정 -> 포워딩
		return "/WEB-INF/view/admin/basic_book.jsp";

	}

	// 교재 검색
	public void adminbasicbooksearch(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setCharacterEncoding("UTF-8");

		// 액션 코드 작성
		String key = request.getParameter("key");
		String value = request.getParameter("value");

		List<AdminBasic> booklist = new ArrayList<AdminBasic>();
		AdminBasicDAO dao = new AdminBasicDAO();

	
		if ("name_".equals(key)) {
			booklist = dao.booklistsearchname(value);
		} else if ("publisher".equals(key)) {
			booklist = dao.booklistsearchpuble(value);
		}
		
		List<String> temp = new ArrayList<String>();
		temp = dao.courselistcheck();

		for (int i = 0; i < booklist.size(); i++) {
			for (int j = 0; j < temp.size(); j++) {
				if (booklist.get(i).getBook_id().equals(temp.get(j))) {
					booklist.get(i).setCheck("disabled");
					break;
				}
			}
		}

		Gson gson = new Gson();
		PrintWriter out = response.getWriter();
		out.write(gson.toJson(booklist));
		out.flush();
		out.close();

	}

	// 교재 입력
	public String adminbasicbookinsert(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {

		// 액션 코드 작성

		String savePath = request.getServletContext().getRealPath("picture");
		System.out.println(savePath);

		int sizeLimit = 1024 * 1024; // 1M

		// 파일 업로드 성공시 리다이렉트할 주소
		String url = "adminbasicbook.it";

		try {
			MultipartRequest multi = new MultipartRequest(request, savePath, sizeLimit, "UTF-8",
					new MyFileRenamePolicy());

			// 확장자 검사 -> 예외 발생
			if (multi.getContentType("book_imgname").equals("image/jpeg")
					|| multi.getContentType("book_imgname").equals("image/png")) {

				// 정상적인 경우 데이터베이스에 정보 저장

				String book_name = multi.getParameter("book_name");
				String publisher = multi.getParameter("publisher");
				String book_imgname = multi.getFilesystemName("book_imgname");

				// 주의) request.getParameter() 메소드가 아님.
				System.out.println(book_imgname);

				AdminBasic m = new AdminBasic();
				m.setBook_name(book_name);
				m.setBook_imgname(book_imgname);
				m.setPublisher(publisher);

				AdminBasicDAO dao = new AdminBasicDAO();
				dao.bookinsert(m);

			} else {
				// 물리적 파일 삭제 과정 추가
				java.io.File temp = new java.io.File(
						String.format("%s//%s", savePath, multi.getFilesystemName("book_imgname")));
				temp.delete();

				// 예외 발생시 -> 업로드된 파일에 대한 처리 부족
				throw new Exception();
			}

		} catch (Exception e) {
			// 파일 업로드 실패시 리다이렉트할 주소
			url = "basicpicturefail.it";
		}

		// 뷰 페이지 주소 지정 -> 리다이렉트
		return String.format("/WEB-INF/view/redirect.jsp?url=%s&code=000", url);

	}

	// 교재 수정
	public String adminbasicbookmodify(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {

		// 액션 코드 작성

		String savePath = request.getServletContext().getRealPath("picture");
		System.out.println(savePath);

		int sizeLimit = 2024 * 2024; // 1M

		// 파일 업로드 성공시 리다이렉트할 주소
		String url = "adminbasicbook.it";
		int code = 0;

		try {
			MultipartRequest multi = new MultipartRequest(request, savePath, sizeLimit, "UTF-8",
					new MyFileRenamePolicy());

			// 확장자 검사 -> 예외 발생
			if (multi.getContentType("book_imgname").equals("image/jpeg")
					|| multi.getContentType("book_imgname").equals("image/png")) {

				// 정상적인 경우 데이터베이스에 정보 저장

				String book_name = multi.getParameter("book_name");
				String publisher = multi.getParameter("publisher");
				String book_imgname = multi.getFilesystemName("book_imgname");
				String book_id = multi.getParameter("book_id");

				AdminBasicDAO dao = new AdminBasicDAO();
				String book_oldimgname = dao.bookimgname(book_id);

				if (book_oldimgname != null) {
					java.io.File temp = new java.io.File(String.format("%s//%s", savePath, book_oldimgname));
					temp.delete();
				}

				AdminBasic m = new AdminBasic();
				m.setBook_name(book_name);
				m.setPublisher(publisher);
				m.setBook_imgname(book_imgname);
				m.setBook_id(book_id);
				code = dao.bookmodify(m);

			} else {

				// 물리적 파일 삭제 과정 추가
				java.io.File temp = new java.io.File(
						String.format("%s//%s", savePath, multi.getFilesystemName("book_imgname")));
				temp.delete();

				// 예외 발생시 -> 업로드된 파일에 대한 처리 부족
				throw new Exception();
			}
		} catch (Exception e) {
			// 파일 업로드 실패시 리다이렉트할 주소
			url = "basicpicturefail.it";
		}

		// 뷰 페이지 주소 지정 -> 포워딩
		return String.format("/WEB-INF/view/redirect.jsp?url=%s&code=%s", url, code);

	}

	// 교재 삭제
	public String adminbasicbookdel(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {

		// 액션 코드 작성

		String savePath = request.getServletContext().getRealPath("picture");
		System.out.println(savePath);

		int sizeLimit = 2024 * 2024; // 1M

		// 파일 업로드 성공시 리다이렉트할 주소
		String url = "adminbasicbook.it";
		int code = 0;

		try {
			MultipartRequest multi = new MultipartRequest(request, savePath, sizeLimit, "UTF-8",
					new MyFileRenamePolicy());

			// 확장자 검사 -> 예외 발생

			// 정상적인 경우 데이터베이스에 정보 저장

			String book_id = multi.getParameter("book_id");

			AdminBasicDAO dao = new AdminBasicDAO();
			String book_oldimgname = dao.bookimgname(book_id);

			if (book_oldimgname != null) {
				java.io.File temp = new java.io.File(String.format("%s//%s", savePath, book_oldimgname));
				temp.delete();
			}

			code = dao.bookdel(book_id);

		} catch (Exception e) {
			// 파일 업로드 실패시 리다이렉트할 주소
			url = "basicpicturefail.it";
		}

		// 뷰 페이지 주소 지정 -> 포워딩
		return String.format("/WEB-INF/view/redirect.jsp?url=%s&code=%s", url, code);

	}

	/////////////////// 기초 강의실/////////////////////////

	// 강의실 리스트 및 검색
	public String adminbasicclass(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 액션 코드 작성
		String key = request.getParameter("key");
		String value = request.getParameter("value");

		if (key == null) {
			key = "all";
			value = "";
		}
		List<AdminBasic> classlist = new ArrayList<AdminBasic>();
		AdminBasicDAO dao = new AdminBasicDAO();

		classlist = dao.classlist(key, value);
		Map<String, String> classlistcheck = new HashMap<String, String>();
		Map<String, String> classmodifycheck = new HashMap<String, String>();
		int a, b = 0;

		for (AdminBasic m : classlist) {
			String disabled = "";
			b = dao.classmodifycheck(m.getClass_id());
			if (b != 0) {
				disabled = "disabled";
			}
			classmodifycheck.put(m.getClass_id(), disabled);
		}

		for (AdminBasic m : classlist) {
			String disabled = "";
			a = dao.classlistcheck(m.getClass_id());
			if (a != 0) {
				disabled = "disabled";
			}
			classlistcheck.put(m.getClass_id(), disabled);
		}

		request.setAttribute("classmodifycheck", classmodifycheck);
		request.setAttribute("classlistcheck", classlistcheck);
		request.setAttribute("classlist", classlist);
		request.setAttribute("key", key);
		request.setAttribute("value", value);

		// 뷰 페이지 주소 지정 -> 포워딩
		return "/WEB-INF/view/admin/basic_class.jsp";

	}

	// 강의실 입력
	public String adminbasicclassinsert(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {

		// 액션 코드 작성
		String class_name = request.getParameter("class_name");
		String jungwon = request.getParameter("jungwon");
		AdminBasic m = new AdminBasic();
		m.setClass_name(class_name);
		m.setJungwon(Integer.parseInt(jungwon));

		AdminBasicDAO dao = new AdminBasicDAO();
		int code = dao.classinsert(m);

		// 뷰 페이지 주소 지정 -> 포워딩
		return "/WEB-INF/view/redirect.jsp?url=adminbasicclass.it&code=" + code;

	}

	// 강의실 수정
	public String adminbasicclassmodify(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {

		// 액션 코드 작성
		String class_id = request.getParameter("class_id");
		String class_name = request.getParameter("class_name");
		String jungwon = request.getParameter("jungwon");
		AdminBasic m = new AdminBasic();
		m.setClass_name(class_name);
		m.setJungwon(Integer.parseInt(jungwon));
		m.setClass_id(class_id);

		AdminBasicDAO dao = new AdminBasicDAO();
		int code = dao.classmodify(m);

		// 뷰 페이지 주소 지정 -> 포워딩
		return "/WEB-INF/view/redirect.jsp?url=adminbasicclass.it&code=" + code;

	}

	// 강의실 삭제

	public String adminbasicclassdel(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {

		// 액션 코드 작성
		String class_id = request.getParameter("class_id");

		AdminBasicDAO dao = new AdminBasicDAO();
		int code = dao.classdel(class_id);

		// 뷰 페이지 주소 지정 -> 포워딩
		return "/WEB-INF/view/redirect.jsp?url=adminbasicclass.it&code=" + code;

	}

}