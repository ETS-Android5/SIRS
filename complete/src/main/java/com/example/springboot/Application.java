package com.example.springboot;

import com.example.springboot.helpers.DBHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.SQLException;

@SpringBootApplication
//@ComponentScan(basePackageClasses = ApplicationController.class)
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class Application {

	public static void main(String[] args) throws ClassNotFoundException, SQLException, NoSuchProviderException, NoSuchAlgorithmException {
		DBHelper.SQLinit();
		SpringApplication.run(Application.class, args);
	}
}
