package com.covidreader.csvreader11;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.configuration.EnableTask;


@SpringBootApplication
@EnableTask
public class Csvreader11Application {

	public static void main(String[] args) {
		SpringApplication.run(Csvreader11Application.class, args);
	}

}
