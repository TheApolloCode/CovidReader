package com.covidreader.jsonreader11;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.configuration.EnableTask;

@SpringBootApplication
@EnableTask
public class Jsonreader11Application {

    public static void main(String[] args) {
        SpringApplication.run(Jsonreader11Application.class, args);
    }

}
