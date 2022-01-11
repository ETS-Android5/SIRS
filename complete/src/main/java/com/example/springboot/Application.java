package com.example.springboot;

import com.example.springboot.helpers.DBHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.sql.SQLException;

@SpringBootApplication
// @SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class Application {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		System.out.println("no main");
		DBHelper.SQLinit();
		System.out.println("no main1");
		//DBHelper.createMobileTable();
		System.out.println("no main2");
		SpringApplication.run(Application.class, args);

	}




}
