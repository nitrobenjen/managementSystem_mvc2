package com.admin;

import java.sql.*;

public class DBConnection {
	
	
	
	// JDBC driver name and database URL
	private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String DB_URL = "jdbc:oracle:thin:@192.168.25.42:1521:xe";

	// Database credentials
	private static final String USER = "project1";
	private static final String PASS = "1234";
	private static Connection conn;
	
	
	//�����ͺ��̽� Ŀ�ؼ� ��ü ���� �޼ҵ�
	public static Connection connect() throws ClassNotFoundException, SQLException{
		// STEP 2: Register JDBC driver
		Class.forName(JDBC_DRIVER);
		// STEP 3: Open a connection
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		
		return conn;
	}
	
	//�����ͺ��̽� Ŀ�ؼ� ��ü �Ҹ� �޼ҵ�
	public static void close() throws SQLException{
		if(conn != null){
			conn.close();
		}
	}


}
