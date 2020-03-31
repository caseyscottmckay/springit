package com.springit.springit_backend;

import com.springit.springit_backend.config.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@Import(SwaggerConfiguration.class)
public class SpringitBackendApplication {

  public static void main(String[] args) {
    SpringApplication.run(com.springit.springit_backend.SpringitBackendApplication.class, args);
  }

}
