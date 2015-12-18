package com.coupay.sellerclient.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbHelper {
	private static Connection connection;
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		try {
			Connection conn = DriverManager.getConnection ("jdbc:oracle:thin:@localhost:1521:orcl","scott","scott");
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} 
	}
	
	public static ResultSet execQuery(String sql) {
		try {
			if(connection == null && connection.isClosed())
				connection = getConnection();
			Statement statement = connection.createStatement();
			return statement.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
