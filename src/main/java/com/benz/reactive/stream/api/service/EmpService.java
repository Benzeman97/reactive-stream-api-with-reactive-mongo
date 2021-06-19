package com.benz.reactive.stream.api.service;

import com.benz.reactive.stream.api.dao.EmployeeDB;
import com.benz.reactive.stream.api.model.Employee;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class EmpService {

    private EmployeeDB employeeDB;

    public EmpService(EmployeeDB employeeDB){
        this.employeeDB=employeeDB;
    }

    public List<Employee> getEmployeesTradition(){
        long start=0;
        long end=0;

        start=System.currentTimeMillis();
          List<Employee> employees = employeeDB.getEmployeesTradition();
        end=System.currentTimeMillis();
        System.out.println("sequence stream takes "+(end-start));
        return employees;
    }

    public Flux<Employee> getEmployeesReactiveStream(){
        long start=0;
        long end=0;
        start=System.currentTimeMillis();
        Flux<Employee> employees = employeeDB.getEmployeesReactiveStream();
        end=System.currentTimeMillis();

        System.out.println("reactive stream takes "+(end-start));
        return employees;
    }
}
