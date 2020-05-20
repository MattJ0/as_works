package com.mattjohnson.teai3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class Teai3Application {

    public static void main(String[] args) {
        SpringApplication.run(Teai3Application.class, args);
    }

}
