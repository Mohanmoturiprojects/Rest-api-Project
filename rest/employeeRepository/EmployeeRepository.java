package example.rest.employeeRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import example.rest.employee.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	public Optional<Employee> findByEmail(String email);
	
	public List<Employee> findByDeptAndGender(String department, String gender);	

	public List<Employee> findByDeptOrGender(String department, String gender);

	public List<Employee> findBySalaryBetween(double minSalary, double maxSalary);

	public List<Employee> getEmployeeBySalaryGreaterThan(double maxsalary);

	public List<Employee> getEmployeeBySalaryLessThan(double minsalary);

	public List<Employee> findByNameContaining(String name);

	//public long countByDepartment(String dept);

	public boolean existsByEmail(String email);

	public void deleteByEmail(String email);
	
	@Query("SELECT e FROM Employee e WHERE e.salary BETWEEN :minSalary AND :maxSalary AND e.dept = :dept ORDER BY e.salary desc")
	List<Employee> findEmpBySalaryRangeDept(@Param("minSalary") double minSalary,
	                                         @Param("maxSalary") double maxSalary,
	                                         @Param("dept") String dept);
}