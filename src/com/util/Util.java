package com.util;

import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

public class Util {


	
	
	// vo �ڵ� ���ñ�
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> T autoResultSet(T vo, ResultSet rs) {
		Object dto = vo;
		ArrayList<String> names = new ArrayList<>();
		ArrayList<String> colTypeList = new ArrayList<>();
		try {
			Class autoDto = Class.forName(vo.getClass().getName());
			Method[] methods = autoDto.getDeclaredMethods();
			dto = autoDto.newInstance();

			ResultSetMetaData rsmd = rs.getMetaData();
			int cols = rsmd.getColumnCount(); // �÷��� �Ѱ���

			// ����Ŭ�� ��� i 2�� ���� maria�� 1
			for (int i = 1; i <= cols; i++) {
				String str = "";
				String types = "";

				str += rsmd.getColumnName(i).trim().charAt(0);
				str += rsmd.getColumnName(i).trim().toLowerCase().substring(1);
				names.add(str);

				
			System.out.println(rsmd.getColumnType(i));
				System.out.println(str);
				/*	System.out.println(rsmd.getColumnName(i));
*/				switch (rsmd.getColumnType(i)) {
				case Types.INTEGER:
					types = "INTEGER";
					break;
				case 93:
					
					types = "DATE";
					break;
				case -9:
					types = "STRING";
					break;

				case 2:
					types = "INTEGER";
					break;
				case 12:
					types = "STRING";
					break;

				default:
					types = "STRING";
					break;
				}

				colTypeList.add(types);
			}
			

			for (int i = 0; i <= names.size(); i++) {
				for (int j = 0; j < methods.length; j++) {
					//System.out.println(methods[j].getName());
					if (methods[j].getName().toUpperCase().equals(("set" + names.get(i)).toUpperCase())) {
						

						if (colTypeList.get(i).equals("INTEGER")) {
							methods[j].invoke(dto, rs.getInt(names.get(i).toLowerCase()));
						} else if (colTypeList.get(i).equals("DATE")) {
							methods[j].invoke(dto, rs.getDate(names.get(i).toLowerCase()));
						} else {
							methods[j].invoke(dto, rs.getString(names.get(i).toLowerCase()));
						}
					}
				}
			}
		} catch (Exception e) {

		}

		return (T) dto;
	}
	
	
	//request �ڵ�
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T setVO(HttpServletRequest request, T pack) {
		Enumeration e = request.getParameterNames();
		String tName = null;
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		Object dto = null;
		while (e.hasMoreElements()) {
			tName = e.nextElement().toString();
			names.add(tName.substring(0, 1).toUpperCase() + tName.substring(1));
			values.add(tName);
		}
		try {
			Class autoDto = Class.forName(pack.getClass().getName());
			Method[] methods = autoDto.getDeclaredMethods();
			dto = autoDto.newInstance();
			for (int i = 0; i < names.size(); i++) {
				for (int j = 0; j < methods.length; j++) {
					if (methods[j].getName().equals("set" + names.get(i))) {
						try {
							methods[j].invoke(dto, request.getParameter(values.get(i)));
						} catch (IllegalArgumentException ex) {
							methods[j].invoke(dto, Integer.parseInt(request.getParameter(values.get(i))));
						}
					}
				}
			}
		} catch (Exception ef) {
			ef.printStackTrace();
		}
		return (T)dto;
	}
	

}
