package net.enjoy.springboot.registrationlogin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Annotation indicating that this is the main application class and enabling Spring Boot
// auto-configuration
@SpringBootApplication
public class RegistrationLoginApplication {

  // Entry point of the application
  public static void main(String[] args) {
    // Launches the Spring Boot application
    SpringApplication.run(RegistrationLoginApplication.class, args);
  }
}
