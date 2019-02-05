package com.examples.java.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.examples.java.dao.EmployeeDAO;
import com.examples.java.model.Employee;

public class EmployeeService {

	Map<Integer, Employee> employees = new HashMap<>();
	EmployeeDAO employeeDao = new EmployeeDAO();

	public EmployeeService() {
		employees.put(100, new Employee(100, "Anil", 45, "Delivery Manager", "Operations", "India"));
		employees.put(101, new Employee(101, "Swapnil", 35, "Quality Analyst", "Quality", "India"));
		employees.put(102, new Employee(102, "Georgil", 42, "Manager", "Operations", "USA"));
		employees.put(103, new Employee(103, "Sunil", 26, "Associate", "Operations", "India"));
		employees.put(104, new Employee(104, "Saril", 30, "Lead Associate", "Operations", "UK"));
		employees.put(105, new Employee(105, "Vinil", 36, "Manager", "Admin", "Australia"));
	}

	public boolean add(Employee employee) {
//		return employees.put(employee.getId(), employee) != null ? true : false;
		return employeeDao.createEmployee(employee);
	}

	public Employee get(int id) {
//		return employees.get(id);
		return employeeDao.getEmployee(id);
	}

	public List<Employee> getAll() {
//		return new ArrayList<>(employees.values());
		return employeeDao.getAllEmployees();
	}

	public boolean update(Employee employee) {
//		return employees.put(employee.getId(), employee) != null ? true : false;
		return employeeDao.updateEmployee(employee);
	}

	public boolean delete(Employee emp) {
//		return employees.remove(emp.getId()) != null ? true : false;
		return employeeDao.deleteEmployee(emp.getId());
	}

	// Get Employee count greater than given age
	public long getEmployeeCountAgeGreaterThan(int age) {
		return employees.values().stream().filter(emp -> emp.getAge() > age).count();
	}

	// Get list of Employee IDs whose age is greater than given age
	public List<Integer> getEmployeeIdsAgeGreaterThan(int age) {
		return employees.values().stream().filter(emp -> emp.getAge() > age).map(emp -> emp.getId())
				.collect(Collectors.toList());
	}

	
	// Get Department wise Employee count
	public Map<String, Long> getEmployeeCountByDepartment() {

		return employees.values().stream()
				// .parallelStream()
				.map(Employee::getDepartment) // output -> Department name
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
				// Key - Department name
				// Value - Count
	}
	
	
	// Get Department wise Employee count ordered by Department name
	public Map<String, Long> getEmployeeCountByDepartmentOdered() {
		return employees.values()
				.stream()
				.sorted(Comparator.comparing(Employee::getDepartment))
				.collect(Collectors.groupingBy(Employee::getDepartment, LinkedHashMap::new, Collectors.counting()));
				// List employee names by Department
				// .collect(Collectors.groupingBy(Employee::getDepartment, LinkedHashMap::new, Collectors.mapping(Employee::getName, Collectors.toList())));
	}
	
	// Get Department wise average Employee age ordered by Department name
	public Map<String, Double> getAvgEmployeeAgeByDept() {
		return employees.values()
				.stream()
				.sorted(Comparator.comparing(Employee::getDepartment))
				.collect(Collectors.groupingBy(Employee::getDepartment, LinkedHashMap::new, Collectors.averagingInt(Employee::getAge)));
	}
	
	// Get Departments have more than given Employee count
	public List<String> getDepartmentsHaveEmployeesMoreThan(int criteria)
	{
		//List<String> deptList = new ArrayList<>();
		
		return employees.values()
				.stream()
				.sorted(Comparator.comparing(Employee::getDepartment))
				.collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()))
				//.forEach((k,v) -> {if(v > criteria) {deptList.add(k);}});
				// return deptList;
				
				.entrySet().stream()	// Creating one more stream to filter the output
				.filter(entry -> entry.getValue() > criteria)
				.map(Map.Entry::getKey)
				.collect(Collectors.toList());			
	}
	
	// Get Employee names starting with given string
	public List<String> getEmployeeNamesStartsWith(String prefix) {
		return employees.values()
				.stream()
				.filter(emp -> emp.getName().startsWith(prefix))
				.map(emp -> emp.getName())
				.collect(Collectors.toList());
	}	
}
