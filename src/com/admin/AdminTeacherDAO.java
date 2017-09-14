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
	public List<AdminTeacher> teacherlist(String key, String value) {
		List<AdminTeacher> result = new ArrayList<AdminTeacher>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBConnection.connect();

			String sql = "SELECT teacher_id, teacher_name, teacher_ssn, teacher_phone, TO_CHAR(teacher_hiredate, 'YYYY-MM-DD') AS teacher_hiredate, (SELECT COUNT(*) FROM teach_sub WHERE teacher_id = o1.teacher_id) AS count_ FROM teacher o1";

			if ("all".equals(key)) {

			} else if ("name_".equals(key)) {
				sql += " WHERE INSTR(course_name, ?)>0";
			}

			sql += " ORDER BY teacher_id";
			pstmt = conn.prepareStatement(sql);

			if ("name_".equals(key)) {
				pstmt.setString(1, value);
			}

			ResultSet rs = pstmt.executeQuery();

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

	// 강사 관리 삭제 비활성화 체크, 개설과목과 연결되있는지 확인
	public int teacherdelcheck1(String value) {
		int result = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBConnection.connect();

			String sql = "SELECT COUNT(*) AS count_ FROM teacher o1 INNER JOIN open_sub o2 ON o1.TEACHER_ID = o2.TEACHER_ID WHERE o1.TEACHER_ID = ?";

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

	// 강사 관리 삭제 비활성화 체크, 강의가능과 연결되있는지 확인
	public int teacherdelcheck2(String value) {
		int result = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBConnection.connect();

			String sql = "SELECT COUNT(*) AS count_ FROM teacher o1 INNER JOIN teach_sub o2 ON o1.TEACHER_ID = o2.TEACHER_ID WHERE o1.TEACHER_ID = ?";

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

}
