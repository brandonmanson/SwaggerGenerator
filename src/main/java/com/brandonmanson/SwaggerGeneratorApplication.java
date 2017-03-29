package com.brandonmanson;

import io.keen.client.java.JavaKeenClientBuilder;
import io.keen.client.java.KeenClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.activation.DataSource;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;

@SpringBootApplication
@EnableAsync
@EnableWebMvc
public class SwaggerGeneratorApplication {

	@Bean
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(5);
		executor.setMaxPoolSize(10);
		executor.setQueueCapacity(25);
		return executor;
	}

	@Bean
	public BasicDataSource dataSource() throws URISyntaxException, ClassNotFoundException {

		URI dbUri = new URI(System.getenv("DATABASE_URL"));

		String userName = dbUri.getUserInfo().split(":")[0];
		String password = dbUri.getUserInfo().split(":")[1];

		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUsername(userName);
		dataSource.setPassword(password);
		String dbUrl = "jdbc:postgresql://"
				+ dbUri.getHost()
				+ ":"
				+ dbUri.getPort()
				+ dbUri.getPath()
				+ "?sslmode=require&user="
				+ dataSource.getUsername()
				+ "&password="
				+ dataSource.getPassword();
		dataSource.setUrl(dbUrl);
		dataSource.setDriverClassName("org.postgresql.Driver");

		return dataSource;
	}

	@Bean
	public KeenClient keenClient() {
		KeenClient client = new JavaKeenClientBuilder().build();

		KeenClient.initialize(client);

		return client;


	}




	public static void main(String[] args) {
		SpringApplication.run(SwaggerGeneratorApplication.class, args);
	}
}
