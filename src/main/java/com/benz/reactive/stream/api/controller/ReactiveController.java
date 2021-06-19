package com.benz.reactive.stream.api.controller;

import com.benz.reactive.stream.api.exception.DataNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;




@RestController
@RequestMapping("/api")
public class ReactiveController {



    @GetMapping("/mono")
    public ResponseEntity<Mono<String>> mono(@RequestParam("name") String name){
        System.out.println(name);
//       Mono<?> mono = Mono.just("benz").
//               then(Mono.error(()->new DataNotFoundException("benz is not found"))).
//               log();
//
//       mono.subscribe(System.out::println,ex-> System.out.println(ex.getMessage()));

//        return ResponseEntity.ok(Mono.just(name));

       return ResponseEntity.ok(Mono.just(name).then(Mono.error(()->new DataNotFoundException("Data is not found with"))));

    }

    @GetMapping("/flux")
    public ResponseEntity<Flux<String>> flux(){
       // Flux<String> fluxStream =Flux.just("doto the kama","kelly brook","nafaz benz","chopa malli").log();
               /* .concatWithValues("riya khan").
                        concatWith(Flux.error(()->new RuntimeException("exception occured")))
                .concatWithValues("soluch").log();*/

       // fluxStream.subscribe(System.out::println);

        Flux<String> fluxStream =Flux.just("doto the kama","kelly brook","nafaz benz","chopa malli")
                .concatWithValues("riya khan")
                .concatWith(Flux.error(()->new DataNotFoundException("names are not found")))
                .concatWithValues("solu").log();

        return ResponseEntity.ok(fluxStream);

    }
}
