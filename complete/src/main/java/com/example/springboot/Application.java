package com.example.springboot;

import com.example.springboot.helpers.DBHelper;
import com.example.springboot.mobile.MobileAppController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import java.sql.SQLException;

@SpringBootApplication
@ComponentScan(basePackageClasses = MobileAppController.class)
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
