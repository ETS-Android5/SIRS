package com.example.springboot;

import com.example.springboot.helpers.DBHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.example.springboot.helpers.KeyGenerator.generateCodes;

@SpringBootApplication
//@ComponentScan(basePackageClasses = ApplicationController.class)
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class Application {

	public static void main(String[] args) throws ClassNotFoundException, SQLException, NoSuchProviderException, NoSuchAlgorithmException {
		DBHelper.SQLinit();
		//DBHelper.createMobileTable();
		ArrayList<byte[]> codes = generateCodes();
		SpringApplication.run(Application.class, args);
		//DBHelper.insertUser(1, "user1", "pass1");
	}
}
