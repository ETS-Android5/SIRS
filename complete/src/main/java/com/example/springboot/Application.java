package com.example.springboot;

import com.example.springboot.helpers.DBHelper;
import com.example.springboot.helpers.KeyGenerator;
import com.example.springboot.user.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import java.sql.SQLException;

@SpringBootApplication
//@ComponentScan(basePackageClasses = ApplicationController.class)
// @SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class Application {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		DBHelper.SQLinit();
		//DBHelper.createMobileTable();
		SpringApplication.run(Application.class, args);
		//DBHelper.insertUser(1, "user1", "pass1");

	}




}
