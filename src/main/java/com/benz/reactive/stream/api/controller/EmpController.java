package com.benz.reactive.stream.api.controller;

import com.benz.reactive.stream.api.model.Employee;
import com.benz.reactive.stream.api.service.EmpService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/emp")
public class EmpController {

    private EmpService empService;

    public EmpController(EmpService empService){
        this.empService=empService;
    }

    @GetMapping(value = "/tradition",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Employee>> getEmployeesStream(){
         return ResponseEntity.ok(empService.getEmployeesTradition());
    }

    @GetMapping(value = "/reactive",produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
    public ResponseEntity<Flux<Employee>> getEmployeesReactiveStream(){
        return ResponseEntity.ok(empService.getEmployeesReactiveStream());
    }
}
