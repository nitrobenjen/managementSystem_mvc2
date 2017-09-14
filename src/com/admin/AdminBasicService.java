package com.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.util.*;

public class AdminBasicService {

	public String adminbasiccourselist(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 액션 코드 작성
		String key = request.getParameter("key");
		String value = request.getParameter("value");

		if (key == null) {
			key = "all";
			value = "";
		}
		List<AdminBasic> courselist = new ArrayList<AdminBasic>();
		AdminBasicDAO dao = new AdminBasicDAO();

		courselist = dao.courselist(key, value);
		Map<String, String> courselistcheck = new HashMap<String, String>();
		int a = 0;

		for (AdminBasic m : courselist) {
			String disabled = "";
			a = dao.courselistcheck(m.getCourse_id());
			if (a != 0) {
				disabled = "disabled";
			}
			courselistcheck.put(m.getCourse_id(), disabled);
		}

		request.setAttribute("courselistcheck", courselistcheck);
		request.setAttribute("courselist", courselist);
		request.setAttribute("key", key);
		request.setAttribute("value", value);

		// 뷰 페이지 주소 지정 -> 포워딩
		return "/WEB-INF/view/admin/basic_course.jsp";
	}

	public String adminbasiccourseinsert(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {

		// 액션 코드 작성
		String course_name = request.getParameter("course_name");
		AdminBasicDAO dao = new AdminBasicDAO();
		int code = dao.courseinsert(course_name);

		// 뷰 페이지 주소 지정 -> 포워딩
		return "/WEB-INF/view/redirect.jsp?url=adminbasiccourselist.it&code=" + code;

	}

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

	// 과목 리스트 및 검색
	public String adminbasicsub(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 액션 코드 작성
		String key = request.getParameter("key");
		String value = request.getParameter("value");

		if (key == null) {
			key = "all";
			value = "";
		}
		List<AdminBasic> sublist = new ArrayList<AdminBasic>();
		AdminBasicDAO dao = new AdminBasicDAO();

		sublist = dao.sublist(key, value);
		Map<String, String> sublistcheck = new HashMap<String, String>();
		int a, b = 0;

		for (AdminBasic m : sublist) {
			String disabled = "";
			a = dao.sublistcheck(m.getSubject_id());
			b = dao.sublistcheck2(m.getSubject_id());
			if (a + b != 0) {
				disabled = "disabled";
			}
			sublistcheck.put(m.getSubject_id(), disabled);
		}

		request.setAttribute("sublistcheck", sublistcheck);
		request.setAttribute("sublist", sublist);
		request.setAttribute("key", key);
		request.setAttribute("value", value);

		// 뷰 페이지 주소 지정 -> 포워딩
		return "/WEB-INF/view/admin/basic_sub.jsp";

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

	// 과목 리스트 및 검색
	public String adminbasicbook(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 액션 코드 작성
		String key = request.getParameter("key");
		String value = request.getParameter("value");

		if (key == null) {
			key = "all";
			value = "";
		}
		List<AdminBasic> booklist = new ArrayList<AdminBasic>();
		AdminBasicDAO dao = new AdminBasicDAO();

		booklist = dao.booklist(key, value);
		Map<String, String> booklistcheck = new HashMap<String, String>();
		int a = 0;

		for (AdminBasic m : booklist) {
			String disabled = "";
			a = dao.booklistcheck(m.getBook_id());
			if (a != 0) {
				disabled = "disabled";
			}
			booklistcheck.put(m.getBook_id(), disabled);
		}

		request.setAttribute("booklistcheck", booklistcheck);
		request.setAttribute("booklist", booklist);
		request.setAttribute("key", key);
		request.setAttribute("value", value);

		// 뷰 페이지 주소 지정 -> 포워딩
		return "/WEB-INF/view/admin/basic_book.jsp";

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
				
				if(book_oldimgname != null) {
					java.io.File temp = new java.io.File(
							String.format("%s//%s", savePath, book_oldimgname));
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
		return String.format("/WEB-INF/view/redirect.jsp?url=%s&code=%s",url,code);

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
					
					if(book_oldimgname != null) {
						java.io.File temp = new java.io.File(
								String.format("%s//%s", savePath, book_oldimgname));
						temp.delete();
					}
				
								
					code = dao.bookdel(book_id);

				
			} catch (Exception e) {
				// 파일 업로드 실패시 리다이렉트할 주소
				url = "basicpicturefail.it";
			}

			// 뷰 페이지 주소 지정 -> 포워딩
			return String.format("/WEB-INF/view/redirect.jsp?url=%s&code=%s",url,code);

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
			int a,b = 0;
			

			
			
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
		
		//강의실 수정
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