package com.kanakis.resilient.perses;

import com.kanakis.resilient.perses.core.AgentLoader;
import com.kanakis.resilient.perses.core.MBeanWrapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

@SpringBootApplication
public class Perses {
    //todo: Micronaut & Kotlin
    public static void main(String[] args) {
        SpringApplication.run(Perses.class, args);
    }

    @Bean
    public MBeanWrapper getMBean(
            @Value("${targetApplicationName:}") String targetAppName,
            @Value("${targetApplicationId:}") String targetAppId) throws IOException {
        return AgentLoader.run(targetAppName, targetAppId);
    }
}
