package com.ozge.rest;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.management.RuntimeErrorException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ozge.domain.Employee;

@RequestMapping("/emp")
@RestController //controller ın bir türevi rest request-responseları dönebilcek bir contrroller
public class EmployeeRestController {

	
	//alt 1 
/*	static List<Employee> employees = new ArrayList<Employee>();
	static {
		employees.add(new Employee("Behiye", "Başer"));
		employees.add(new Employee("Kadir", "Kondur"));
		employees.add(new Employee("Muhammed", "Alper"));
		employees.add(new Employee("Okan", "Yılmaz"));
		employees.add(new Employee("İlayda", "Yılmaz"));
		
	}*/
	
	//alt2
	
	List <Employee> employees;
	
	@PostConstruct
	public void loadData() {
		employees = new ArrayList<Employee>();
		
			employees.add(new Employee("Behiye", "Başer"));
			employees.add(new Employee("Kadir", "Kondur"));
			employees.add(new Employee("Muhammed", "Alper"));
			employees.add(new Employee("Okan", "Yılmaz"));
			employees.add(new Employee("İlayda", "Yılmaz"));
		
	}
	
	
	@GetMapping("/mrb")
	public String merhaba() {
		
		return 	"merhaba";
	}
	
	@GetMapping("/emp")
	public Employee emp() {
		
		Employee mgr = new Employee("özge", "demirbaş");
		Employee emp = new Employee("Erdim", "Ertekin");
		emp.setManager(mgr);
		
		
		return emp;
		
	}
	

	@GetMapping("/emps")
	public List<Employee> emps(){
		
		List<Employee> empList = new ArrayList<Employee>();
		
		Employee mgr = new Employee("özge", "demirbaş");
		Employee emp = new Employee("Erdim", "Ertekin");
		
		emp.setManager(mgr);
		
		empList.add(mgr);
		empList.add(emp);
		empList.add(new Employee("öö","dd"));
		
		return empList;
		
	}
	
	@GetMapping("/emps/{indeks}")
	public Employee getEmp(@PathVariable int indeks){
		
		if(indeks>=employees.size() || indeks<0 )
			throw new EmployeeNotFoundException("BÖYLE BİR EMPLOYEE İD MEVCUT DEĞİLDİR : "+indeks);
			//return new Employee("Bu limit"," bizi aşar");
			
		if(indeks==2)
			throw new RuntimeException("olmaz olsun böyle hata");
		
		return employees.get(indeks);
		
	}
	
	@ExceptionHandler
	public ResponseEntity<EmployeeErrorResponse> hataYakala(EmployeeNotFoundException exc){
		EmployeeErrorResponse hata=new EmployeeErrorResponse();
		
		hata.setStatus(HttpStatus.NOT_FOUND.value());
		hata.setMessage(exc.getMessage());
		
		return new ResponseEntity<>(hata, HttpStatus.NOT_FOUND);
	}
	
	
}
