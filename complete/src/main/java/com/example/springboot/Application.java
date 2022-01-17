package com.example.springboot;

import com.example.springboot.helpers.DBHelper;
import com.example.springboot.helpers.KeyGenerator;
import com.example.springboot.user.User;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.sql.SQLException;

@SpringBootApplication
//@ComponentScan(basePackageClasses = ApplicationController.class)
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class   Application {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		DBHelper.SQLinit();
		//DBHelper.createMobileTable();
		SpringApplication.run(Application.class, args);
		//DBHelper.insertUser(1, "user1", "pass1");

	}

	@Configuration
	public class ServerConfig {

		@Bean
		public ServletWebServerFactory servletContainer() {
			TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
				@Override
				protected void postProcessContext(Context context) {
					SecurityConstraint securityConstraint = new SecurityConstraint();
					securityConstraint.setUserConstraint("CONFIDENTIAL");
					SecurityCollection collection = new SecurityCollection();
					collection.addPattern("/*");
					securityConstraint.addCollection(collection);
					context.addConstraint(securityConstraint);
				}
			};
			tomcat.addAdditionalTomcatConnectors(getHttpConnector());
			return tomcat;
		}

		private Connector getHttpConnector() {
			Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
			connector.setScheme("http");
			connector.setPort(8080);
			connector.setSecure(false);
			connector.setRedirectPort(8443);
			return connector;
		}
	}
}
