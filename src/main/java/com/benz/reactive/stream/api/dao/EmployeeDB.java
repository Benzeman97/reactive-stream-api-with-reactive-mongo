package com.benz.reactive.stream.api.dao;

import com.benz.reactive.stream.api.model.Employee;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Repository
public class EmployeeDB {

    public List<Employee> getEmployeesTradition()
    {
        return IntStream.rangeClosed(1,50).peek((i)->{
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                     ex.printStackTrace();

                }
        })
                .peek(System.out::println).mapToObj(i->new Employee((10+i),"Employee "+i,Double.valueOf(new Random().nextInt(100*100))))
                .collect(Collectors.toList());
    }

    public Flux<Employee> getEmployeesReactiveStream(){
      return Flux.range(1,50)
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(System.out::println)
                .map(i->new Employee((10+i),"Employee "+i,Double.valueOf(new Random().nextInt(100*100))));
    }

    public Flux<Employee> getEmployees(){
         return Flux.range(1,10)
                  .doOnNext(i-> System.out.println("processing reactive stream "+i))
                  .map(i->new Employee(i,"employee "+i,Double.valueOf(new Random().nextInt(100*100))));
    }

    public Flux<Employee> getEmployeeList(){
          return Flux.just(new Employee(1001,"Nafaz Benzema",80000.0),
                   new Employee(1002,"Kelly Brook",78000.0),
                   new Employee(1003,"Chopa Malli",90000.0))
                   .concatWith(Flux.just(new Employee(1004,"doto kama",56000.0)));
    }
}
