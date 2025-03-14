package com.epam.training.gen.ai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main entry point for the GenAiTrainingApplication Spring Boot application.
 * This application serves as the primary bootstrap class for initializing
 * and running the GenAiTraining services.
 *
 * By executing the main method, the application is launched using SpringApplication.run, which
 * sets up the Spring Application context and starts the application.
 */
@SpringBootApplication
public class GenAiTrainingApplication {

    /**
     * The entry point of the GenAiTrainingApplication Spring Boot application.
     *
     * @param args command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        SpringApplication.run(GenAiTrainingApplication.class, args);
    }

}
