package com.stamp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class StampApplication {

  public static void main(String[] args) {
    SpringApplication.run(StampApplication.class, args);
  }
}
