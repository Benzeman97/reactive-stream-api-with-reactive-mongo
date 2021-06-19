package com.benz.reactive.stream.api.handler;

import com.benz.reactive.stream.api.dao.EmployeeDB;
import com.benz.reactive.stream.api.exception.DataNotFoundException;
import com.benz.reactive.stream.api.model.Employee;
import com.benz.reactive.stream.api.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class EmployeeHandler {

    private EmployeeDB employeeDB;

    public EmployeeHandler(EmployeeDB employeeDB){
        this.employeeDB=employeeDB;
    }

    public Mono<ServerResponse> getEmployees(ServerRequest req){
        Flux<Employee> employees = employeeDB.getEmployeeList();
        return ServerResponse.ok().body(employees,Employee.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getEmployeesReactiveStream(ServerRequest request){
          Flux<Employee> employees =  employeeDB.getEmployeesReactiveStream();
          return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM)
                  .body(employees,Employee.class);
    }

    public Mono<ServerResponse> findEmployee(ServerRequest request){
         int empId =  Integer.valueOf(request.pathVariable("id"));

          Mono<Employee> employee =employeeDB.getEmployees().filter(emp->emp.getEmpId()==empId).next();

         return ServerResponse.ok().body(employee,Employee.class)
                 .switchIfEmpty(ServerResponse.notFound().build());



    }

    public Mono<ServerResponse> saveEmployee(ServerRequest request){
          Flux<Employee> employee =  request.bodyToFlux(Employee.class);
     //     Mono<String> employeeRes= employeeMono.map(emp->emp.getEmpId()+" : "+emp.getEmpName()+" : "+emp.getSalary());
           Flux<Employee> employeeFlux =  employeeDB.getEmployeeList().concatWith(employee);
          return ServerResponse.ok().body(employeeFlux,Employee.class);
    }


}
