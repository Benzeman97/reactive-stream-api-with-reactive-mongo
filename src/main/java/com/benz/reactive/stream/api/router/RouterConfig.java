package com.benz.reactive.stream.api.router;

import com.benz.reactive.stream.api.handler.EmployeeHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {

    private EmployeeHandler employeeHandler;

    public RouterConfig(EmployeeHandler employeeHandler){
        this.employeeHandler=employeeHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> employeeRouterFunction(){
          return RouterFunctions.route()
                  .GET("/routes/employees",employeeHandler::getEmployees)
                  .GET("/routes/employees/reactive",employeeHandler::getEmployeesReactiveStream)
                  .GET("/routes/employee/{id}",employeeHandler::findEmployee)
                  .POST("/routes/employee/save",employeeHandler::saveEmployee)
                  .build();
    }
}
