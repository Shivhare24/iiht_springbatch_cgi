package com.examples.java;

import java.util.List;
import java.util.Scanner;

import com.examples.java.exception.EmployeeValidationException;
import com.examples.java.exception.NoEmployeeFoundException;
import com.examples.java.model.Employee;
import com.examples.java.service.EmployeeService;
import com.examples.java.util.DataSource;

public class EmployeeManagementMain {

	private static Scanner scanner = null;
	private static EmployeeService empService = null;

	public static void main(String[] args) {
		int option;
		String doRepeat;

		scanner = new Scanner(System.in);
		empService = new EmployeeService();

		System.out.println("Welcome to Employee Management App!!!");

		try {
			do {
				System.out.println();
				System.out.println("1. Add Employee");
				System.out.println("2. View Employee");
				System.out.println("3. Update Employee");
				System.out.println("4. Delete Employee");
				System.out.println("5. View All Employees");
				System.out.println("6. Reports and Statistics");
				System.out.println("7. Exit");

				System.out.print("\nEnter your option: ");
				option = scanner.nextInt();

				try {
					switch (option) {
					case 1:
						createEmployee();
						break;
					case 2:
						viewEmployee();
						break;
					case 3:
						updateEmployee();
						break;
					case 4:
						deleteEmployee();
						break;
					case 5:
						listEmployees();
						break;
					case 6:
						printStatistics();
						break;
					case 7:
						exit();
						break;
					default:
						System.out.println("Invalid option entered. Please enter correct option.");
						break;
					}
				} catch (NoEmployeeFoundException | EmployeeValidationException e) {
					System.out.println(e.getMessage());
				}

				// System.out.print("\nDo you want to continue (Y/N)?");
				// doRepeat = scanner.next();
			} while (option != 7);
			// while (doRepeat != null && doRepeat.equalsIgnoreCase("Y"));

			// } catch (Exception e) {
			// System.out.println("Unexpected error occured. " +
			// e.getMessage());
		} finally {
			// releasing the resource
			scanner.close();
		}

		System.out.println("\nThank you!!!");
	}

	private static void createEmployee() throws EmployeeValidationException {
		Employee emp = new Employee();

		System.out.println("\nEnter employee details...");
//		System.out.print("Enter id: ");
//		try {
//			int id = Integer.parseInt(scanner.next());
//			emp.setId(id);
//		} catch (NumberFormatException e) {
//			throw new EmployeeValidationException("Invalid input for id. Please enter integer value.");
//		}

		captureDetail(emp);

		boolean isCreateSuccess = empService.add(emp);

		String message = isCreateSuccess ? "Employee created successfully." : "Employee creation failed.";
		System.out.println(message);
	}

	private static void viewEmployee() throws NoEmployeeFoundException {
		System.out.print("\nEnter employee id to view: ");
		int id = scanner.nextInt();

		Employee emp = empService.get(id);

		if (emp == null) {
			throw new NoEmployeeFoundException("No employee found to show for given id - " + id);
		}

		printHeader();
		printDetail(emp);
		System.out.println();

	}

	private static void updateEmployee() throws NoEmployeeFoundException, EmployeeValidationException {

		System.out.print("\nEnter employee id to update: ");
		int id = scanner.nextInt();

		Employee emp = empService.get(id);

		if (emp == null) {
			throw new NoEmployeeFoundException("No employee found to update for given id - " + id);
		}
		
		captureDetail(emp);

		boolean isUpdateSuccess = empService.update(emp);

		String message = isUpdateSuccess ? "Employee updated successfully." : "Employee updation failed.";
		System.out.println(message);
	}

	private static void deleteEmployee() throws NoEmployeeFoundException {

		System.out.print("\nEnter employee id to delete: ");
		int id = scanner.nextInt();

		Employee emp = empService.get(id);
		if (emp == null) {
			throw new NoEmployeeFoundException("No employee found to delete for given id - " + id);
		}

		boolean isDeleteSuccess = empService.delete(emp);

		String message = isDeleteSuccess ? "Employee deleted successfully." : "Employee deletion failed.";
		System.out.println(message);
	}

	private static void listEmployees() {

		List<Employee> empList = empService.getAll();

		printHeader();

		for (Object emp : empList) {
			printDetail((Employee) emp);
		}
		System.out.println();
	}

	private static void captureDetail(Employee emp) throws EmployeeValidationException {
		System.out.print("Enter name: ");
		emp.setName(scanner.next());

		System.out.print("Enter age: ");
		try {
			int age = Integer.parseInt(scanner.next());
			emp.setAge(age);
		} catch (NumberFormatException e) {
			throw new EmployeeValidationException("Invalid input for age. Please enter integer value.");
		}

		System.out.print("Enter designation: ");
		emp.setDesignation(scanner.next());

		System.out.print("Enter department: ");
		emp.setDepartment(scanner.next());

		System.out.print("Enter country: ");
		emp.setCountry(scanner.next());
	}

	private static void printHeader() {
		System.out.format("\n%5s %15s %5s %15s %15s %15s", "ID", "Name", "Age", "Designation", "Department", "Country");
	}

	private static void printDetail(Employee emp) {
		if (emp == null) {
			return;
		}

		System.out.format("\n%5d %15s %5d %15s %15s %15s", emp.getId(), emp.getName(), emp.getAge(),
				emp.getDesignation(), emp.getDepartment(), emp.getCountry());
	}
	
	private static void printStatistics()
	{
		System.out.println("No of employees older than fourty years: " + empService.getEmployeeCountAgeGreaterThan(40));
		System.out.println("List employee IDs older than fourty years: " + empService.getEmployeeIdsAgeGreaterThan(40));
		System.out.println("Employee count by Department: " + empService.getEmployeeCountByDepartment());
		System.out.println("Employee count by Department ordered: " + empService.getEmployeeCountByDepartmentOdered());
		System.out.println("Average Employee Age by Department: " + empService.getAvgEmployeeAgeByDept());
		System.out.println("List Departments have more than 3 employees: " + empService.getDepartmentsHaveEmployeesMoreThan(3));
		System.out.println("List Employees starts with 'S':" + empService.getEmployeeNamesStartsWith("S"));
		
		// Print Departments which is having more than 3 employees
	}

	private static void exit() {
		// Close connection
		DataSource.closeConnection();
		System.out.println("\nThank you!!!");
		System.exit(0);
	}
}
