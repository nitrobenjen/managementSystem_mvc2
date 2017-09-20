package com.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LoginDAO {

	// 강사 계정 등록
	public int teacherinsert(String teacher_id, String teacher_ssn, Connection conn) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		try {

			String sql = "INSERT INTO login2 (id_, pw, grade) VALUES (?,?,1)";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, teacher_id);
			pstmt.setString(2, teacher_ssn);			
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
