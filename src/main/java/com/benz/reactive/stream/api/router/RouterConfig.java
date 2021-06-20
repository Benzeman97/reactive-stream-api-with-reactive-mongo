package com.benz.reactive.stream.api.router;

import com.benz.reactive.stream.api.handler.EmployeeHandler;
import com.benz.reactive.stream.api.handler.ProductHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {

    private EmployeeHandler employeeHandler;
    private ProductHandler productHandler;

    public RouterConfig(EmployeeHandler employeeHandler,ProductHandler productHandler){
        this.employeeHandler=employeeHandler;
        this.productHandler=productHandler;
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

    @Bean
    public RouterFunction<ServerResponse> productRouterFunction(){
        return RouterFunctions.route()
                .GET("/functional/api/product",req->productHandler.getProducts(req))
                .GET("/functional/api/product/range",productHandler::getProductInRange)
                .GET("/functional/api/product/{id}",productHandler::findProduct)
                .POST("/functional/api/product/save",productHandler::saveProduct)
                .PUT("/functional/api/product/update/{id}",productHandler::updateProduct)
                .DELETE("/functional/api/product/{id}",productHandler::deleteProduct)
                .build();

    }
}
