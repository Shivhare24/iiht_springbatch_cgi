package com.examples.java.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.examples.java.model.Employee;
import com.examples.java.util.DataSource;

public class EmployeeDAO {

	Connection conn = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	public EmployeeDAO() {
		try {
			conn = DataSource.getConnection();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Error occured while creating the connection: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public boolean createEmployee(Employee employee) {

		String query = "INSERT INTO employee(name, age, department, designation, country) values(\""
				+ employee.getName() + "\"," + employee.getAge() + ",\"" + employee.getDepartment() + "\",\""
				+ employee.getDesignation() + "\",\"" + employee.getCountry() + "\")";
		
//		String query = "INSERT INTO employee(name, age, department, designation, country) values(?,?,?,?,?)";
		System.out.println("Create query: " + query);
		int count = 0;
		try {
			stmt = conn.createStatement();
//			pstmt = conn.prepareStatement(query);
//			pstmt.setString(1, employee.getName());
//			pstmt.setInt(2, employee.getAge());
//			pstmt.setString(3, employee.getDepartment());
//			pstmt.setString(4, employee.getDesignation());
//			pstmt.setString(5, employee.getCountry());
			
//			count = pstmt.executeUpdate(query);
			count = stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}

		return count > 0 ? true : false;
	}

	public boolean updateEmployee(Employee employee) {
		String query = "UPDATE employee SET name = \"" + employee.getName() + "\", age = " + employee.getAge()
				+ ",department = \"" + employee.getDepartment() + "\",designation = \"" + employee.getDesignation()
				+ "\", country = \"" + employee.getCountry() + "\" WHERE id = " + employee.getId() ;
		System.out.println("Update query: " + query);
		int count = 0;
		try {
			stmt = conn.createStatement();
			count = stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}

		return count > 0 ? true : false;
	}

	public boolean deleteEmployee(int empId) {
		String query = "DELETE FROM employee WHERE id = " + empId;
		System.out.println("Delete query: " + query);
		int count = 0;
		try {
			stmt = conn.createStatement();
			count = stmt.executeUpdate(query);
//			conn.commit();
		} catch (SQLException e) {
//			try {
//				conn.rollback();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
			e.printStackTrace();
		} finally {
			closeResources();
		}

		return count > 0 ? true : false;
	}

	public Employee getEmployee(int empId) {
		Employee emp = null;
		String query = "SELECT * FROM employee WHERE id = " + empId;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int age = rs.getInt("age");
				String department = rs.getString("department");
				String designation = rs.getString("designation");
				String country = rs.getString("country");
				emp = new Employee(id, name, age, designation, department, country);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}

		return emp;
	}

	public List<Employee> getAllEmployees() {
		List<Employee> employees = new ArrayList<>();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM employee");

			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int age = rs.getInt("age");
				String department = rs.getString("department");
				String designation = rs.getString("designation");
				String country = rs.getString("country");
				employees.add(new Employee(id, name, age, designation, department, country));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}

		return employees;
	}

	private void closeResources() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}			
		} catch (SQLException e) {
			System.out.println("Error occured while closing the Statement and ResultSet - " + e.getMessage());
			e.printStackTrace();
		}
	}

}
