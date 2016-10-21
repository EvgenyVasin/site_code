package ru.jskills;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;

@SpringBootApplication
public class JskillsApplication {

	public static void main(String[] args) {
		SpringApplication.run(JskillsApplication.class, args);
	}

}
