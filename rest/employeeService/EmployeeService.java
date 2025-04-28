package example.rest.employeeService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import example.rest.employee.Employee;
import example.rest.employeeRepository.EmployeeRepository;

@Service
public class EmployeeService {
	@Autowired
	EmployeeRepository employeeRepository;
	
	
	  public Employee SaveEmployeeData(Employee employee) {
		  Employee emp=employeeRepository.save(employee);
	       return emp; 
	  }
	 
	

	public List<Employee> SaveEmployeeData(List<Employee> employees) {
			List<Employee> emps=employeeRepository.saveAll(employees);
			return emps;
	}

	public Optional<Employee> getEmpByEmail(String email) {
			return employeeRepository.findByEmail(email);
	}

	public Optional<Employee> getEmpById(Long id) {
		return  employeeRepository.findById(id);
	}

	public List<Employee> getEmployeeByDeptAndGender(String dept, String gender) {
		List<Employee> employees=employeeRepository.findByDeptAndGender(dept, gender);
		return employees;
	}

	public List<Employee> getEmployeeByDeptOrGender(String dept, String gender) {
		List<Employee> employees=employeeRepository.findByDeptOrGender( dept, gender);
		return employees;
	}

	public List<Employee> getEmployeeBySalaryRange(double minSalary, double maxSalary) {
		List<Employee> employees=employeeRepository.findBySalaryBetween(minSalary, maxSalary);
		return employees;
	}

	public List<Employee> getEmployeeBySalaryGreaterThan(double maxsalary) {
		List<Employee> employees=employeeRepository.getEmployeeBySalaryGreaterThan(maxsalary);
		return employees;
	}

	public List<Employee> getEmployeeBySalaryLessThan(double minsalary) {
		List<Employee> employees=employeeRepository.getEmployeeBySalaryLessThan(minsalary);
		return employees;
	}

	public List<Employee> getEmployeeByName(String name) {
		List<Employee> employees=employeeRepository.findByNameContaining(name);
		return employees;
	}

	/*
	 * public long getCountEmployeesByDepartment(String dept) { long
	 * count=employeeRepository.countByDepartment(dept); return count; }
	 */

	public boolean employeeExistOrNotByEmail(String email) {
		return employeeRepository.existsByEmail(email);
	}

	public boolean deleteById(Long id) {
	boolean status=employeeRepository.existsById(id);
	if(status) {
		employeeRepository.deleteById(id);
		return true;
	}
	else {
		return false;
	}
	}

	public boolean deleteByEmail(String email) {
		boolean status=employeeRepository.existsByEmail(email);
		if(status) {
			employeeRepository.deleteByEmail(email);
			return true;
		}
		else {
			return false;
		}
		}

	public boolean deleteAll() {
		return false;
	}

	public Employee updateEmployeeById(Long id, Employee newEmployee) {
		Optional<Employee> optionalEmp=employeeRepository.findById(id);
		if(optionalEmp.isPresent()) {
			Employee employee=optionalEmp.get();
			employee.setDept(newEmployee.getDept());
			employee.setEmail(newEmployee.getEmail());
			employee.setName(newEmployee.getName());
			employee.setSalary(newEmployee.getSalary());
			employee.setGender(newEmployee.getGender());
			employeeRepository.save(employee);
			return employee;
		}
		
		return null;
	}

	public Optional<Employee> partialUpdateEmployee(Long id, Map<String, Object> updates) {
	Optional<Employee>optionalEmp= employeeRepository.findById(id);
	if(optionalEmp.isPresent()) {
		Employee exiEmployee=optionalEmp.get();
		updates.forEach((Key,value)->{
			switch (Key) {
			case "name":
				exiEmployee.setName((String) value);
				break;
			case "salary":
				exiEmployee.setSalary((Double) value);
				break;
			case "gender":
				exiEmployee.setGender((String) value);
				break;
			case "email":
				exiEmployee.setEmail((String) value);
				break;
			case "dept":
				exiEmployee.setDept((String) value);
				break;
				
			default:
				throw new IllegalArgumentException("Unexpected value: " + Key);
			}
			
		});
		Employee employee=employeeRepository.save(exiEmployee);
		return Optional.of(employee);
	}
	else {
		return Optional.empty();
	}
		
	}




	public List<Employee> filterEmpSalaryDept(double minSalary, double maxSalary, String dept) {
		 return employeeRepository.findEmpBySalaryRangeDept(minSalary,maxSalary,dept); 
	}


}
