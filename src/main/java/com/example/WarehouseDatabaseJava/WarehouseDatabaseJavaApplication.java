package com.example.WarehouseDatabaseJava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.WarehouseDatabaseJava")
@EnableAutoConfiguration
public class WarehouseDatabaseJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(WarehouseDatabaseJavaApplication.class, args);
	}

}
