package com.labexam.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/*
 * Name: Abdulazez Zeinu
 * ID: UGR/1223/14
 * Software Engineering Department
 */


// Main application class for the Lab Exam application
@SpringBootApplication
@ServletComponentScan("com.labexam") // Scans for servlet components in the specified package
@Configuration("applicationContext.xml") // Specifies the configuration file
@ComponentScan("com.labexam") // Scans for Spring components in the specified package
public class Application {

    // Entry point of the Spring Boot application
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args); // Launches the application
    }
}
