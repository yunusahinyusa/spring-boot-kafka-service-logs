package com.bezkoder.spring.jpa.postgresql.service;

import com.bezkoder.spring.jpa.postgresql.dto.EmployeeDto;
import com.bezkoder.spring.jpa.postgresql.entity.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Service
public interface EmployeeService {
    public EmployeeDto createEmployee(EmployeeDto employeeDto,HttpServletRequest request) throws Exception;
    List<EmployeeDto> getEmployees(HttpServletRequest request) throws IOException;
    EmployeeDto updateEmployee(Long id, EmployeeDto employee,HttpServletRequest request) throws Exception;
    Boolean deleteEmployee(Long id,HttpServletRequest request) throws IOException;
    public EmployeeDto getEmployeeById(@PathVariable("id") Long id,HttpServletRequest request) throws Exception;


}
