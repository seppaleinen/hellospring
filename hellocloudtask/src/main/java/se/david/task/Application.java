package se.david.task;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@SpringBootApplication
@EnableTask
@EnableBatchProcessing
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private DataSource dataSource;

    @Bean
    public TaskConfigurer getTaskConfigurer() {
        return new TaskConfigurer(dataSource);
    }

    @Bean
    public TaskListener taskListener() {
        return new TaskListener();
    }

}
