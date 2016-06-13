package com.sky.search.publisher.app;

import com.sky.search.publisher.service.PublishService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.sky.search")
public class Application {

    public static void main(String... args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        PublishService publishService = context.getBean("publishService", PublishService.class);
        publishService.publishToTopic();
    }
}
