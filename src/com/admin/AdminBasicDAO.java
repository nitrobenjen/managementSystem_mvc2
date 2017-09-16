package com.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminBasicDAO {

	// 기초 정보 관리 > 과정 리스트
	public List<AdminBasic> courselist() {
		List<AdminBasic> result = new ArrayList<AdminBasic>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.connect();

			String sql = "SELECT course_id, course_name FROM course ORDER BY course_id";

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				AdminBasic m = new AdminBasic();
				String course_id = rs.getString("course_id");
				String course_name = rs.getString("course_name");
				m.setCourse_id(course_id);
				m.setCourse_name(course_name);
				result.add(m);
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
				if (rs != null)
					rs.close();
			} catch (SQLException se2) {
				se2.getMessage();
			}

		}

		return result;

	}

	// 기초 정보 관리 > 과정 리스트 > 이름검색
	public List<AdminBasic> coursesearchname(String value) {

		List<AdminBasic> result = new ArrayList<AdminBasic>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.connect();

			String sql = "SELECT course_id, course_name FROM course WHERE INSTR(course_name, ?)>0 ORDER BY course_id";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, value);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				AdminBasic m = new AdminBasic();
				String course_id = rs.getString("course_id");
				String course_name = rs.getString("course_name");
				m.setCourse_id(course_id);
				m.setCourse_name(course_name);
				result.add(m);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
				if (rs != null)
					rs.close();
			} catch (SQLException se2) {
				se2.getMessage();
			}
		}

		return result;

	}

	// 기초 정보 관리 > 과정 리스트 > 삭제 비활성화 체크
	public List<String> courselistcheck() {
		List<String> result = new ArrayList<String>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.connect();

			String sql = "SELECT o1.course_id FROM course o1 INNER JOIN open_course o2 ON o1.course_id = o2.course_id GROUP BY o1.course_id";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				result.add(rs.getString("course_id"));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
				if (rs != null)
					rs.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}

		}

		return result;

	}

	// 기본정보 > 과정 > 등록
	public int courseinsert(String value) throws SQLException {
		int result = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBConnection.connect();
			conn.setAutoCommit(false);
			String sql = "INSERT INTO course (course_id, course_name) VALUES ((SELECT CONCAT('CU',LPAD((NVL(MAX(SUBSTR(course_id,3)), 0)+1), 3, '0') ) FROM course), ?)";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, value);
			pstmt.executeUpdate();
			result = 100;
			conn.commit();

		} catch (ClassNotFoundException | SQLException e) {
			result = 101;
			conn.rollback();
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();

			}

		}

		return result;

	}

	// 기본정보 > 과정 > 수정
	public int coursemodify(AdminBasic value) throws SQLException {
		int result = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBConnection.connect();
			conn.setAutoCommit(false);
			String sql = "UPDATE course SET course_name = ? WHERE course_id=?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(2, value.getCourse_id());
			pstmt.setString(1, value.getCourse_name());
			pstmt.executeUpdate();
			result = 100;
			conn.commit();

		} catch (ClassNotFoundException | SQLException e) {
			result = 101;
			conn.rollback();
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

		return result;

	}

	// 기본정보 > 과정 > 삭제

	public int coursedel(String value) throws SQLException {
		int result = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBConnection.connect();
			conn.setAutoCommit(false);
			String sql = "DELETE FROM course WHERE course_id =?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, value);
			pstmt.executeUpdate();
			result = 100;
			conn.commit();

		} catch (ClassNotFoundException | SQLException e) {
			result = 101;
			conn.rollback();
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}

		}

		return result;

	}

	/////////////// 기초정보 과목///////////////////////////////////

	// 기초 정보 관리 > 과목 리스트
	public List<AdminBasic> sublist() {
		List<AdminBasic> result = new ArrayList<AdminBasic>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.connect();

			String sql = "SELECT subject_id, subject_name FROM subject ORDER BY subject_id";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				AdminBasic m = new AdminBasic();
				String subject_id = rs.getString("subject_id");
				String subject_name = rs.getString("subject_name");
				m.setSubject_id(subject_id);
				m.setSubject_name(subject_name);
				result.add(m);
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
				if (rs != null)
					rs.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}

		}

		return result;

	}

	// 기초 정보 관리 > 과목 검색
	public List<AdminBasic> subsearchname(String value) {
		List<AdminBasic> result = new ArrayList<AdminBasic>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.connect();

			String sql = "SELECT subject_id, subject_name FROM subject WHERE INSTR(subject_name, ?)>0 ORDER BY subject_id";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, value);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				AdminBasic m = new AdminBasic();
				String subject_id = rs.getString("subject_id");
				String subject_name = rs.getString("subject_name");
				m.setSubject_id(subject_id);
				m.setSubject_name(subject_name);
				result.add(m);
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
				if (rs != null)
					rs.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}

		}

		return result;

	}

	// 기초 정보 관리 > 과목 리스트 > 삭제 비활성화 체크 > 개설과목과 연결 확인
	public List<String> sublistcheck() {
		List<String> result = new ArrayList<String>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.connect();

			String sql = "SELECT o1.subject_id FROM subject o1, open_sub o2 WHERE o1.subject_id = o2.subject_id GROUP BY o1.subject_id";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				result.add(rs.getString("subject_id"));

			}
			rs.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
				if (rs != null)
					rs.close();
			} catch (SQLException se2) {
				se2.printStackTrace();
			}

		}

		return result;

	}

	// 기초 정보 관리 > 과목 리스트 > 삭제 비활성화 체크 > 강의가능과목과 연결 확인
	public List<String> sublistcheck2() {
		List<String> result = new ArrayList<String>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.connect();

			String sql = "SELECT o1.subject_id FROM subject o1, teach_sub o2 WHERE o1.SUBJECT_ID = o2.SUBJECT_ID GROUP BY o1.subject_id";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				result.add(rs.getString("subject_id"));
			}
			rs.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
				if (rs != null)
					rs.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}

		}

		return result;

	}

	// 기본정보 > 과목 > 등록
	public int subinsert(String value) throws SQLException {
		int result = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBConnection.connect();
			conn.setAutoCommit(false);
			String sql = "INSERT INTO subject (subject_id, subject_name) VALUES ((SELECT CONCAT('SUB',LPAD((NVL(MAX(SUBSTR(subject_id,4)), 0)+1), 3, '0') ) FROM subject), ?)";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, value);
			pstmt.executeUpdate();
			result = 100;
			conn.commit();

		} catch (ClassNotFoundException | SQLException e) {
			result = 101;
			conn.rollback();
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

		return result;

	}

	// 기본정보 > 과목 > 수정
	public int submodify(AdminBasic value) throws SQLException {
		int result = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBConnection.connect();
			conn.setAutoCommit(false);
			String sql = "UPDATE subject SET subject_name = ? WHERE subject_id=?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(2, value.getSubject_id());
			pstmt.setString(1, value.getSubject_name());
			pstmt.executeUpdate();
			result = 100;
			conn.commit();

		} catch (ClassNotFoundException | SQLException e) {
			result = 101;
			conn.rollback();
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

		return result;

	}

	// 기본정보 > 과목 > 삭제

	public int subdel(String value) throws SQLException {
		int result = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBConnection.connect();
			conn.setAutoCommit(false);
			String sql = "DELETE FROM subject WHERE subject_id =?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, value);
			pstmt.executeUpdate();
			result = 100;
			conn.commit();

		} catch (ClassNotFoundException | SQLException e) {
			result = 101;
			conn.rollback();
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

		return result;

	}

	/////////////// 기초정보 교재///////////////////////////////////

	// 기초 정보 관리 > 교재 리스트
	public List<AdminBasic> booklist() {
		List<AdminBasic> result = new ArrayList<AdminBasic>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.connect();

			String sql = "SELECT book_id, book_name, publisher, book_imgname FROM book ORDER BY book_id";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				AdminBasic m = new AdminBasic();
				String book_id = rs.getString("book_id");
				String book_name = rs.getString("book_name");
				String publisher = rs.getString("publisher");
				String book_imgname = rs.getString("book_imgname");
				m.setBook_id(book_id);
				m.setBook_name(book_name);
				m.setPublisher(publisher);
				m.setBook_imgname(book_imgname);
				result.add(m);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
				if (rs != null)
					rs.close();

			} catch (SQLException se) {

				se.printStackTrace();
			}

		}

		return result;

	}

	// 기초 정보 관리 > 교재명 검색
	public List<AdminBasic> booklistsearchname(String value) {
		List<AdminBasic> result = new ArrayList<AdminBasic>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.connect();

			String sql = "SELECT book_id, book_name, publisher, book_imgname FROM book WHERE INSTR(book_name, ?)>0 ORDER BY book_id";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, value);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				AdminBasic m = new AdminBasic();
				String book_id = rs.getString("book_id");
				String book_name = rs.getString("book_name");
				String publisher = rs.getString("publisher");
				String book_imgname = rs.getString("book_imgname");
				m.setBook_id(book_id);
				m.setBook_name(book_name);
				m.setPublisher(publisher);
				m.setBook_imgname(book_imgname);
				result.add(m);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
				if (rs != null)
					rs.close();

			} catch (SQLException se) {

				se.printStackTrace();
			}

		}

		return result;

	}

	// 기초 정보 관리 > 출판사 검색
	public List<AdminBasic> booklistsearchpuble(String value) {
		List<AdminBasic> result = new ArrayList<AdminBasic>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.connect();

			String sql = "SELECT book_id, book_name, publisher, book_imgname FROM book WHERE INSTR(publisher, ?)>0 ORDER BY book_id";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, value);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				AdminBasic m = new AdminBasic();
				String book_id = rs.getString("book_id");
				String book_name = rs.getString("book_name");
				String publisher = rs.getString("publisher");
				String book_imgname = rs.getString("book_imgname");
				m.setBook_id(book_id);
				m.setBook_name(book_name);
				m.setPublisher(publisher);
				m.setBook_imgname(book_imgname);
				result.add(m);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
				if (rs != null)
					rs.close();

			} catch (SQLException se) {

				se.printStackTrace();
			}

		}

		return result;

	}

	// 기초 정보 관리 > 교재 리스트 > 삭제 비활성화 체크
	public List<String> booklistcheck() {
		List<String> result = new ArrayList<String>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.connect();

			String sql = "SELECT o1.book_id FROM book o1, open_sub o2 WHERE o1.BOOK_ID = o2.BOOK_ID";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				result.add(rs.getString("book_id"));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
				if (rs != null)
					rs.close();
			} catch (SQLException se) {

				se.printStackTrace();
			}
		}

		return result;

	}

	// 기본정보 > 교재 > 등록
	public int bookinsert(AdminBasic value) throws SQLException {
		int result = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBConnection.connect();
			conn.setAutoCommit(false);
			String sql = "INSERT INTO book (book_id, book_name, publisher, book_imgname) VALUES ((SELECT CONCAT('B',LPAD((NVL(MAX(SUBSTR(book_id,2)), 0)+1), 3, '0') ) FROM book), ?, ?, ?)";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, value.getBook_name());
			pstmt.setString(2, value.getPublisher());
			pstmt.setString(3, value.getBook_imgname());
			pstmt.executeUpdate();
			result = 100;
			conn.commit();

		} catch (ClassNotFoundException | SQLException e) {
			result = 101;
			conn.rollback();
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException se) {

				se.printStackTrace();
			}
		}

		return result;

	}

	// 교재 업로드 이미지 삭제를 위한 이미지명 찾기
	public String bookimgname(String value) {
		String result = "";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.connect();

			String sql = "SELECT book_imgname FROM book WHERE book_id=?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, value);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				result = rs.getString("book_imgname");
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
				if (rs != null)
					rs.close();
			} catch (SQLException se) {

				se.printStackTrace();
			}
		}

		return result;

	}

	// 기본정보 > 교재 > 수정
	public int bookmodify(AdminBasic value) throws SQLException {
		int result = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBConnection.connect();
			conn.setAutoCommit(false);
			String sql = "UPDATE book SET book_name=?, publisher=?, book_imgname=? WHERE book_id=?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, value.getBook_name());
			pstmt.setString(2, value.getPublisher());
			pstmt.setString(3, value.getBook_imgname());
			pstmt.setString(4, value.getBook_id());
			pstmt.executeUpdate();
			result = 100;
			conn.commit();

		} catch (ClassNotFoundException | SQLException e) {
			result = 101;
			conn.rollback();
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException se) {

				se.printStackTrace();
			}
		}

		return result;

	}

	// 기본정보 > 과목 > 삭제

	public int bookdel(String value) throws SQLException {
		int result = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBConnection.connect();
			conn.setAutoCommit(false);
			String sql = "DELETE FROM book WHERE book_id =?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, value);
			pstmt.executeUpdate();
			result = 100;
			conn.commit();

		} catch (ClassNotFoundException | SQLException e) {
			result = 101;
			conn.rollback();
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException se) {

				se.printStackTrace();
			}
		}

		return result;

	}

	/////////////// 기초정보 강의실///////////////////////////////////

	// 기초 정보 관리 > 강의실 리스트 >검색추가
	public List<AdminBasic> classlist(String key, String value) {
		List<AdminBasic> result = new ArrayList<AdminBasic>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBConnection.connect();

			String sql = "SELECT class_id, class_name, jungwon FROM class_";

			if ("all".equals(key)) {

			} else if ("name_".equals(key)) {
				sql += " WHERE INSTR(class_name, ?)>0";
			}

			sql += " ORDER BY class_id";
			pstmt = conn.prepareStatement(sql);

			if ("name_".equals(key)) {
				pstmt.setString(1, value);
			}

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				AdminBasic m = new AdminBasic();
				String class_id = rs.getString("class_id");
				String class_name = rs.getString("class_name");
				int jungwon = rs.getInt("jungwon");
				m.setClass_id(class_id);
				m.setClass_name(class_name);
				m.setJungwon(jungwon);
				result.add(m);
			}
			rs.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException se2) {
			}
			try {
				DBConnection.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

		return result;

	}

	// 기초 정보 관리 > 강의실 리스트 > 삭제 비활성화 체크
	public int classlistcheck(String value) {
		int result = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBConnection.connect();

			String sql = "SELECT COUNT(*) AS count_ FROM class_ o1, open_course o2 WHERE o1.class_id = o2.class_id AND o1.class_id = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, value);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				result = rs.getInt("count_");
			}
			rs.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException se2) {
			}
			try {
				DBConnection.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

		return result;

	}

	// 기초 정보 관리 > 강의실 리스트 > 수정 비활성화 체크
	public int classmodifycheck(String value) {
		int result = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBConnection.connect();

			String sql = "SELECT COUNT(*) AS count_ FROM class_ o1, open_course o2 WHERE o1.class_id = o2.class_id AND o1.class_id = ? AND TO_CHAR(o2.COURSE_END_DAY, 'YYYY-MM-DD') > SYSDATE AND TO_CHAR(o2.COURSE_START_DAY, 'YYYY-MM-DD') < SYSDATE";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, value);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				result = rs.getInt("count_");
			}
			rs.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException se2) {
			}
			try {
				DBConnection.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

		return result;

	}

	// 기본정보 > 강의실 > 등록
	public int classinsert(AdminBasic value) throws SQLException {
		int result = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBConnection.connect();
			conn.setAutoCommit(false);
			String sql = "INSERT INTO class_ (class_id, class_name, jungwon) VALUES ((SELECT CONCAT('CA',LPAD((NVL(MAX(SUBSTR(class_id,3)), 0)+1), 3, '0') ) FROM class_), ?, ?)";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, value.getClass_name());
			pstmt.setInt(2, value.getJungwon());
			pstmt.executeUpdate();
			result = 100;
			conn.commit();

		} catch (ClassNotFoundException | SQLException e) {
			result = 101;
			conn.rollback();
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException se2) {
			}
			try {
				DBConnection.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

		return result;

	}

	// 기본정보 > 강의실 > 수정
	public int classmodify(AdminBasic value) throws SQLException {
		int result = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBConnection.connect();
			conn.setAutoCommit(false);
			String sql = "UPDATE class_ SET class_name=?, jungwon=? WHERE class_id=?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, value.getClass_name());
			pstmt.setInt(2, value.getJungwon());
			pstmt.setString(3, value.getClass_id());
			pstmt.executeUpdate();
			result = 100;
			conn.commit();

		} catch (ClassNotFoundException | SQLException e) {
			result = 101;
			conn.rollback();
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException se2) {
			}
			try {
				DBConnection.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

		return result;

	}

	// 기본정보 > 강의실 > 삭제

	public int classdel(String value) throws SQLException {
		int result = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBConnection.connect();
			conn.setAutoCommit(false);
			String sql = "DELETE FROM class_ WHERE class_id =?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, value);
			pstmt.executeUpdate();
			result = 100;
			conn.commit();

		} catch (ClassNotFoundException | SQLException e) {
			result = 101;
			conn.rollback();
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException se2) {
			}
			try {
				DBConnection.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

		return result;

	}

}