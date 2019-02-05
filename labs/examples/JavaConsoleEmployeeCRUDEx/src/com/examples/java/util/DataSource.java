package com.examples.java.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/spring_training";

	// Database credentials
	static final String USER = "training";
	static final String PASS = "training";

	private static Connection conn = null;

	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");

		// Open a connection
		if (conn == null) {
			System.out.println("Creating connection to databse - " + DB_URL);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
//			conn.setAutoCommit(false);
			System.out.println("Connection created");
		}
		return conn;
	}

	public static void closeConnection() {
		if (conn != null) {
			try {
				System.out.println("Closing connection...");
				conn.close();
				System.out.println("Connection closed");				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
