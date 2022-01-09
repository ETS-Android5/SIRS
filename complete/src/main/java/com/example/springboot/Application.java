package com.example.springboot;

import com.example.springboot.helpers.DBHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
// @SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class Application {

	public static void main(String[] args) throws ClassNotFoundException {

		DBHelper.SQLinit();
		SpringApplication.run(Application.class, args);

	}




}
