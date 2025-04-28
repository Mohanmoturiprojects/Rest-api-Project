package example.rest.employeeController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import example.rest.employee.Employee;
import example.rest.employee.ErrorResponse;
import example.rest.employeeService.EmployeeService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class EmployeeController {
	@Autowired
	EmployeeService employeeService;
	
	  @PostMapping("/saveemployee")
	  public ResponseEntity<EntityModel<Employee>>SaveEmployeeData(  @Valid @RequestBody Employee employee) {
		
		  Employee emp=employeeService.SaveEmployeeData(employee); 
		 EntityModel<Employee> entityModel = EntityModel.of(emp);
		 
		    entityModel.add(WebMvcLinkBuilder.linkTo(
		            WebMvcLinkBuilder.methodOn(EmployeeController.class).getEmpById(emp.getId()))
		            .withSelfRel());
		    entityModel.add(WebMvcLinkBuilder.linkTo(
		            WebMvcLinkBuilder.methodOn(EmployeeController.class).deleteById(emp.getId()))
		            .withRel("Delete"));
		    entityModel.add(WebMvcLinkBuilder.linkTo(
		            WebMvcLinkBuilder.methodOn(EmployeeController.class).updateEmployeeById(emp.getId(),emp))
		            .withRel("put"));
		    entityModel.add(WebMvcLinkBuilder.linkTo(
		            WebMvcLinkBuilder.methodOn(EmployeeController.class).partialUpdateEmployee(emp.getId(),new HashMap<>()))
		            .withRel("patch"));
		 return ResponseEntity.status(HttpStatus.CREATED)
				               .header("info", "data saved sucess")
				               .body(entityModel);
		 }
	 
	 
	
	@PostMapping("/saveemployees")
	public ResponseEntity<List<Employee>>SaveEmployeeData (@RequestBody List<Employee> employees)
	{
		List<Employee> emps=employeeService.SaveEmployeeData(employees);
		
		
	   return ResponseEntity.status(HttpStatus.CREATED)
			                 .header("Info","data saved succesfullu")
			                 .body(emps);
	}
	
	@GetMapping("/getemployeebyemail/{email}")
	public ResponseEntity<?> getEmpByEmail(@PathVariable("email") String email) {
		Optional<Employee> optionalEmployee=employeeService.getEmpByEmail(email);
		
		if( optionalEmployee.isPresent()) {
			Employee employee= optionalEmployee.get();
			return ResponseEntity.status(HttpStatus.OK)
					             .header("info","data is found")
					             .body(employee);
		}
			else {
				
				ErrorResponse errorResponse=new ErrorResponse();
				errorResponse.setErrorMessage("the given email id is not found"+email);
				errorResponse.setTimestamp(LocalDateTime.now());
				errorResponse.setStatusCode(HttpStatus.NOT_FOUND.value());

				return  ResponseEntity.status(HttpStatus.NOT_FOUND) 
                                      .header("info","the email is not found"+email)
                                      .body(errorResponse);
			}
					             
	}
	
	@GetMapping("/getemployeebyid/{id}")
	public ResponseEntity<?> getEmpById(@PathVariable("id") Long id) {
		Optional<Employee> optionalEmployee=employeeService.getEmpById(id);
		
		if( optionalEmployee.isPresent()) {
			Employee employee= optionalEmployee.get();
			return ResponseEntity.status(HttpStatus.OK)
					             .header("info","data is found")
					             .body(employee);
		}
			else {
				
				ErrorResponse errorResponse=new ErrorResponse();
				errorResponse.setErrorMessage("the given email id is not found"+id);
				errorResponse.setTimestamp(LocalDateTime.now());
				errorResponse.setStatusCode(HttpStatus.NOT_FOUND.value());

				return  ResponseEntity.status(HttpStatus.NOT_FOUND) 
                                      .header("info","the email is not found"+id)
                                      .body(errorResponse);
			}
					             
	}
	
	@GetMapping("/getemployeebydepartmentandgender")
	public ResponseEntity<List<Employee>> getEmployeeByDeptAndGender(@RequestParam(" dept") String dept,@RequestParam("gender") String gender) {
		List<Employee> employees=employeeService.getEmployeeByDeptAndGender(dept,gender);
		return ResponseEntity.status(HttpStatus.OK)
	                         .header("info","data is found")
	                         .body(employees);
	}
	
	  @GetMapping("/getemployeedeptorgender")
	  public ResponseEntity<List<Employee>> getEmployeeByDeptOrGender(@RequestParam("dept") String dept,@RequestParam("gender") String gender) {
		  List<Employee> employees=employeeService.getEmployeeByDeptOrGender(dept,gender);
		  return ResponseEntity.status(HttpStatus.OK)
                .header("info","employees retrieved successfully")
                .body(employees);
	  }
	  @GetMapping("/getemployeesalaryrange")
	  public ResponseEntity<List<Employee>> getEmployeeBySalaryRange(@RequestParam("min") double minSalary,@RequestParam("max") double maxSalary) {
		  List<Employee> employees=employeeService.getEmployeeBySalaryRange(minSalary,maxSalary);
		  return ResponseEntity.status(HttpStatus.OK)
                  .header("info","employees retrieved successfully")
                  .body(employees);
	  }
	  @GetMapping("/getemployeesalarygreaterthan")
	  public ResponseEntity<List<Employee>> getEmployeeBySalaryGreaterThan(@RequestParam("maxsalary") double maxsalary) {
		  List<Employee> employees=employeeService.getEmployeeBySalaryGreaterThan(maxsalary);
		  return ResponseEntity.status(HttpStatus.OK)
                  .header("info","employees retrieved successfully")
                  .body(employees);
	  }
	  @GetMapping("/getemployeesalarylessthan")
	  public ResponseEntity<List<Employee>> getEmployeeBySalaryLessThan(@RequestParam("minsalary") double minsalary) {
		  List<Employee> employees=employeeService.getEmployeeBySalaryLessThan(minsalary);
		  return ResponseEntity.status(HttpStatus.OK)
                  .header("info","employees retrieved successfully")
                  .body(employees);
	  }
	  
	  @GetMapping("/getemployeebyname")
	  public ResponseEntity<List<Employee>> getEmployeeByName(@RequestParam("name") String name) {
		  List<Employee> employees=employeeService.getEmployeeByName(name);
		  return ResponseEntity.status(HttpStatus.OK)
                  .header("info","employees retrieved successfully")
                  .body(employees);
	  }
	  
		/*
		 * @GetMapping("/countemployeesbydepartment") public ResponseEntity<Long>
		 * getCountEmployeesByDepartment(@RequestParam("dept") String dept) { long
		 * count=employeeService.getCountEmployeesByDepartment(dept); return
		 * ResponseEntity.status(HttpStatus.OK)
		 * .header("info","employees retrieved successfully") .body(count); }
		 */
	  
	  @GetMapping("/employeeexistornotbyemail")
	  public ResponseEntity<Boolean> employeeExistOrNotByEmail(@RequestParam("email") String email) {
		  boolean exist=employeeService.employeeExistOrNotByEmail(email);
		  return ResponseEntity.ok(exist);
	  }
	  
	  //delete method
	  
	  @DeleteMapping("/employee/{id}")
	  public ResponseEntity<?> deleteById(@PathVariable("id") Long id){
		  boolean status=employeeService.deleteById(id);
		  if(status) {
			  return ResponseEntity.status(HttpStatus.OK)
                      .header("info","employees delete successfully by id"+id)
                      .build();
		  }
		  else {
			  ErrorResponse errorResponse=new ErrorResponse();
		      	errorResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
		      	errorResponse.setErrorMessage("employee data not found with id"+id);
		      	errorResponse.setTimestamp(LocalDateTime.now());
		      	return ResponseEntity.status(HttpStatus.NOT_FOUND)
		      			             .header("info","employee data not found")
		      			             .body(errorResponse);
		      	
		  }
	  }
	  
	  @DeleteMapping("/deleteemployee/{email}")
	  public ResponseEntity<?> deleteByEmail(@PathVariable("email") String email){
		  boolean status=employeeService.deleteByEmail(email);
		  if(status) {
			  return ResponseEntity.status(HttpStatus.OK)
                      .header("info","employees delete successfully by email"+email)
                      .build();
		  }
		  else {
			  ErrorResponse errorResponse=new ErrorResponse();
		      	errorResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
		      	errorResponse.setErrorMessage("employee data not found with email"+email);
		      	errorResponse.setTimestamp(LocalDateTime.now());
		      	return ResponseEntity.status(HttpStatus.NOT_FOUND)
		      			             .header("info","employee data not found")
		      			             .body(errorResponse);
		      	
		  }
	  }
	  @DeleteMapping("/deleteall")
	  public ResponseEntity<?> deleteAll(){
		  boolean status=employeeService.deleteAll();
		  if(status) {
			  return ResponseEntity.status(HttpStatus.OK)
                      .header("info","employees delete successfully")
                      .build();
		  }
		  else {
			  ErrorResponse errorResponse=new ErrorResponse();
		      	errorResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
		      	errorResponse.setErrorMessage("employee data not found");
		      	errorResponse.setTimestamp(LocalDateTime.now());
		      	return ResponseEntity.status(HttpStatus.NOT_FOUND)
		      			             .header("info","employee data not found")
		      			             .body(errorResponse);
		      	
		  }
	  }
	  
	  @PutMapping("employee/{id}")
	  public ResponseEntity<?> updateEmployeeById(@PathVariable("id") Long id, @RequestBody Employee newEmployee) {
	  	Employee  employee=employeeService.updateEmployeeById(id,newEmployee);
	  	 
		  if(employee!=null) {
			  return ResponseEntity.status(HttpStatus.OK)
                      .header("info","employees update successfully")
                      .body(employee);
		  }
		  else {
			  ErrorResponse errorResponse=new ErrorResponse();
		      	errorResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
		      	errorResponse.setErrorMessage("employee data not found");
		      	errorResponse.setTimestamp(LocalDateTime.now());
		      	return ResponseEntity.status(HttpStatus.NOT_FOUND)
		      			             .header("info","employee data not found")
		      			             .body(errorResponse);
		      	
	  }
	  }
	  
	  @PatchMapping("/employee/{id}")
	  public ResponseEntity<?>partialUpdateEmployee(@PathVariable ("id") Long id ,@RequestBody Map<String, Object>updates){
		Optional<Employee>optionalemp = employeeService.partialUpdateEmployee(id,updates);
		if(optionalemp.isPresent()) {
			Employee employee=optionalemp.get();
			return ResponseEntity.status(HttpStatus.OK)
					              .header("info", "partialudate is succesfull")
					              .body(employee);
		}
		else {
			 ErrorResponse errorResponse=new ErrorResponse();
		      	errorResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
		      	errorResponse.setErrorMessage("employee data not found");
		      	errorResponse.setTimestamp(LocalDateTime.now());
		      	return ResponseEntity.status(HttpStatus.NOT_FOUND)
		      			             .header("info","employee data not found")
		      			             .body(errorResponse);
		}
		  
	  }
	  
	  @GetMapping("/employee/filter")
	public ResponseEntity<List<Employee>>filterEmpSalaryDept(@RequestParam ("minSalary") double minSalary,
			                                                 @RequestParam ("maxSalary") double maxSalary, 
			                                                 @RequestParam ("dept") String dept) {
		 List<Employee> emp =employeeService.filterEmpSalaryDept(minSalary,maxSalary,dept);
		
		 return ResponseEntity.status(HttpStatus.OK)
		                .header("info", "read the data")
		                .body(emp);
		  
		  
	  }
	  
	  
	
}


