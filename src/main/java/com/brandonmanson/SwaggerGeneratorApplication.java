package com.brandonmanson;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.apache.commons.dbcp.BasicDataSource;

import javax.activation.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

@SpringBootApplication
@EnableAsync
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
	public BasicDataSource dataSource() throws URISyntaxException {
		URI dbUri = new URI(System.getenv("DATABASE_URL"));

		String userName = dbUri.getUserInfo().split(":")[0];
		String password = dbUri.getUserInfo().split(":")[1];
		String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ":" + dbUri.getPort() + dbUri.getPath();

		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl(dbUrl);
		dataSource.setUsername(userName);
		dataSource.setPassword(password);

		return dataSource;
	}




	public static void main(String[] args) {
		SpringApplication.run(SwaggerGeneratorApplication.class, args);
	}
}
