package com.example.WarehouseDatabaseJava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "com.example.WarehouseDatabaseJava")
@EnableAutoConfiguration
@EnableTransactionManagement
public class WarehouseDatabaseJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(WarehouseDatabaseJavaApplication.class, args);
	}

}
