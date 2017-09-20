package com.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminTeacherDAO {

	// 강사 정보 출력 > 강사번호, 강사명, 주민번호뒷자리, 전화번호, 강의가능과목 수
	// 검색 추가
	public List<AdminTeacher> teacherlist() {
		List<AdminTeacher> result = new ArrayList<AdminTeacher>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.connect();

			String sql = "SELECT teacher_id, teacher_name, teacher_ssn, teacher_phone, TO_CHAR(teacher_hiredate, 'YYYY-MM-DD') AS teacher_hiredate, (SELECT COUNT(*) FROM teach_sub WHERE teacher_id = o1.teacher_id) AS count_ FROM teacher o1  ORDER BY teacher_id";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				AdminTeacher m = new AdminTeacher();
				String teacher_id = rs.getString("teacher_id");
				String teacher_name = rs.getString("teacher_name");
				String teacher_ssn = rs.getString("teacher_ssn");
				String teacher_phone = rs.getString("teacher_phone");
				String teacher_hiredate = rs.getString("teacher_hiredate");
				int count_ = rs.getInt("count_");

				m.setTeacher_id(teacher_id);
				m.setTeacher_name(teacher_name);
				m.setTeacher_ssn(teacher_ssn);
				m.setTeacher_phone(teacher_phone);
				m.setTeacher_hiredate(teacher_hiredate);
				m.setCount_(count_);
				result.add(m);
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return result;

	}

	// 강사 이름 검색
	public List<AdminTeacher> teachersearchname(String value) {
		List<AdminTeacher> result = new ArrayList<AdminTeacher>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.connect();

			String sql = "SELECT teacher_id, teacher_name, teacher_ssn, teacher_phone, TO_CHAR(teacher_hiredate, 'YYYY-MM-DD') AS teacher_hiredate, (SELECT COUNT(*) FROM teach_sub WHERE teacher_id = o1.teacher_id) AS count_ FROM teacher o1 WHERE INSTR(teacher_name, ?)>0  ORDER BY teacher_id";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, value);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				AdminTeacher m = new AdminTeacher();
				String teacher_id = rs.getString("teacher_id");
				String teacher_name = rs.getString("teacher_name");
				String teacher_ssn = rs.getString("teacher_ssn");
				String teacher_phone = rs.getString("teacher_phone");
				String teacher_hiredate = rs.getString("teacher_hiredate");
				int count_ = rs.getInt("count_");

				m.setTeacher_id(teacher_id);
				m.setTeacher_name(teacher_name);
				m.setTeacher_ssn(teacher_ssn);
				m.setTeacher_phone(teacher_phone);
				m.setTeacher_hiredate(teacher_hiredate);
				m.setCount_(count_);
				result.add(m);
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return result;

	}

	// 강사 휴대폰 번호 검색
	public List<AdminTeacher> teachersearchphone(String value) {
		List<AdminTeacher> result = new ArrayList<AdminTeacher>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.connect();

			String sql = "SELECT teacher_id, teacher_name, teacher_ssn, teacher_phone, TO_CHAR(teacher_hiredate, 'YYYY-MM-DD') AS teacher_hiredate, (SELECT COUNT(*) FROM teach_sub WHERE teacher_id = o1.teacher_id) AS count_ FROM teacher o1  WHERE INSTR(teacher_phone, ?)>0  ORDER BY teacher_id";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, value);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				AdminTeacher m = new AdminTeacher();
				String teacher_id = rs.getString("teacher_id");
				String teacher_name = rs.getString("teacher_name");
				String teacher_ssn = rs.getString("teacher_ssn");
				String teacher_phone = rs.getString("teacher_phone");
				String teacher_hiredate = rs.getString("teacher_hiredate");
				int count_ = rs.getInt("count_");

				m.setTeacher_id(teacher_id);
				m.setTeacher_name(teacher_name);
				m.setTeacher_ssn(teacher_ssn);
				m.setTeacher_phone(teacher_phone);
				m.setTeacher_hiredate(teacher_hiredate);
				m.setCount_(count_);
				result.add(m);
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}

				if (conn != null)
					conn.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return result;

	}

	// 강사 관리 삭제 비활성화 체크, 개설과목과 연결되있는지 확인
	public List<String> teacherdelcheck1() {
		List<String> result = new ArrayList<String>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = DBConnection.connect();

			String sql = "SELECT o1.teacher_id AS teacher_id FROM teacher o1  INNER JOIN open_sub o2 ON o1.TEACHER_ID = o2.TEACHER_ID GROUP BY o1.teacher_id ORDER BY o1.teacher_id";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				result.add(rs.getString("teacher_id"));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();

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

	// 강사 관리 삭제 비활성화 체크, 강의가능과 연결되있는지 확인
	public List<String> teacherdelcheck2() {
		List<String> result = new ArrayList<String>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.connect();

			String sql = "SELECT o1.teacher_id AS teacher_id FROM teacher o1 INNER JOIN teach_sub o2 ON o1.TEACHER_ID = o2.TEACHER_ID GROUP BY o1.teacher_id ORDER BY o1.teacher_id";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				result.add(rs.getString("teacher_id"));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();

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

	// 강사 등록을 위한 강사번호 추출 쿼리

	public String teacherid(Connection conn) {
		String result = "";

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			String sql = "SELECT CONCAT('TCH',LPAD((NVL(MAX(SUBSTR(teacher_id,4)), 0)+1), 3, '0') ) AS teacher_id FROM teacher";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				result = rs.getString("teacher_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();

			} catch (SQLException se) {
				se.printStackTrace();
			}

		}

		return result;

	}

	// 강사 기본정보 입력. 이름 등등.
	public int teacherinsert(AdminTeacher value) throws SQLException {
		int result = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBConnection.connect();
			conn.setAutoCommit(false);
			String sql = "INSERT INTO teacher (teacher_id, teacher_name, teacher_ssn, teacher_phone, teacher_hiredate) VALUES (?, ?, ?, ?, SYSDATE)";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, value.getTeacher_id());
			pstmt.setString(2, value.getTeacher_name());
			pstmt.setString(3, value.getTeacher_ssn());
			pstmt.setString(4, value.getTeacher_phone());
			pstmt.executeUpdate();
			result = 100;

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

	// 강사 강의가능과목 입력
	public int teacherinsertsub(String teacher_id, String subject_id, Connection conn) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		try {

			String sql = "INSERT INTO teach_sub (teacher_id, subject_id) VALUES (?, ?)";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, teacher_id);
			pstmt.setString(2, subject_id);
			pstmt.executeUpdate();
			result = 100;

		} catch (SQLException e) {
			result = 101;
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();

			} catch (SQLException se) {
				se.printStackTrace();
			}

		}

		return result;

	}

	// 강사 강의가능한 과목 출력
	public List<AdminTeacher> teachersublist(String value) {
		List<AdminTeacher> result = new ArrayList<AdminTeacher>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.connect();

			String sql = "SELECT o1.teacher_id, teacher_name, teacher_phone, o2.subject_id, subject_name\r\n"
					+ " FROM teacher o1\r\n" + " LEFT OUTER JOIN teach_sub o2 ON o1.TEACHER_ID = o2.TEACHER_ID\r\n"
					+ " LEFT OUTER JOIN subject o3 ON o2.SUBJECT_ID = o3.SUBJECT_ID\r\n" + " WHERE o1.teacher_id = ?"
					+ " ORDER BY o1.teacher_id";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, value);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				AdminTeacher m = new AdminTeacher();

				String teacher_id = rs.getString("teacher_id");
				String teacher_name = rs.getString("teacher_name");
				String teacher_phone = rs.getString("teacher_phone");
				String subject_id = rs.getString("subject_id");
				String subject_name = rs.getString("subject_name");

				m.setTeacher_id(teacher_id);
				m.setTeacher_name(teacher_name);
				m.setTeacher_phone(teacher_phone);
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

	// 특정 강사 선택해제 불가능한 과목 목록
	public List<String> teachernocheck(String value) {
		List<String> result = new ArrayList<String>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.connect();

			String sql = "SELECT o3.subject_id FROM teacher o1\n"
					+ "INNER JOIN teach_sub o2 ON o1.teacher_id = o2.teacher_id\n"
					+ "INNER JOIN subject o3 ON o2.subject_id = o3.subject_id\n"
					+ "INNER JOIN open_sub o4 ON o1.teacher_id = o4.teacher_id AND o3.SUBJECT_ID = o4.SUBJECT_ID\n"
					+ "WHERE o1.teacher_id = ?\n" + "GROUP BY o3.subject_id";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, value);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String subject_id = rs.getString("subject_id");
				result.add(subject_id);

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

	// 강사 강의가능과목 수정을 위한 과목 삭제
	public int teacherdelsub(String teacher_id, Connection conn) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		try {

			String sql = "DELETE FROM teach_sub \n" + "WHERE teacher_id=? \n"
					+ "AND subject_id IN(SELECT o2.subject_id\n" + "FROM teacher o1\n"
					+ "LEFT OUTER JOIN teach_sub o2 ON o1.TEACHER_ID = o2.TEACHER_ID\n"
					+ "LEFT OUTER JOIN subject o3 ON o2.SUBJECT_ID = o3.SUBJECT_ID\n"
					+ "LEFT OUTER JOIN open_sub o4 ON o1.TEACHER_ID = o4.TEACHER_ID AND o4.SUBJECT_ID = o3.SUBJECT_ID\n"
					+ "WHERE o1.TEACHER_ID = ?\n" + "AND o4.subject_id NOT IN\n"
					+ "(SELECT subject_id FROM open_sub WHERE subject_id = o4.subject_id))";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, teacher_id);
			pstmt.setString(2, teacher_id);
			pstmt.executeUpdate();
			result = 100;

		} catch (SQLException e) {
			result = 101;
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();

			} catch (SQLException se) {
				se.printStackTrace();
			}

		}

		return result;

	}
	
	
	
	
	// 강사 기본정보 수정
		public int teachmodifyinfo(AdminTeacher t, Connection conn) throws SQLException {
			int result = 0;
			PreparedStatement pstmt = null;
			try {

				String sql = "UPDATE teacher SET teacher_name=?, teacher_ssn=?, teacher_phone=? WHERE teacher_id=?";

				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, t.getTeacher_name());
				pstmt.setString(2, t.getTeacher_ssn());
				pstmt.setString(3, t.getTeacher_phone());
				pstmt.setString(4, t.getTeacher_id());
				
				pstmt.executeUpdate();
				result = 100;

			} catch (SQLException e) {
				result = 101;
				e.printStackTrace();
			} finally {
				try {
					if (pstmt != null)
						pstmt.close();

				} catch (SQLException se) {
					se.printStackTrace();
				}

			}

			return result;

		}

}
