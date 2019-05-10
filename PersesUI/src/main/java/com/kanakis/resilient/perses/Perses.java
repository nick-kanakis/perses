package com.kanakis.resilient.perses;

import com.kanakis.resilient.perses.service.InjectorService;
import com.kanakis.resilient.perses.service.LocalInjector;
import com.kanakis.resilient.perses.service.RemoteInjector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;

@SpringBootApplication
public class Perses {
    //todo: Micronaut & Kotlin
    public static void main(String[] args) {
        SpringApplication.run(Perses.class, args);
    }

    @Bean
    @Conditional(LocalInjectorCondition.class)
    public InjectorService getLocalInjector(
            @Value("${targetName:}") String targetAppName,
            @Value("${targetPid:}") String targetAppId) throws Exception {
        return new LocalInjector(targetAppName, targetAppId);
    }

    @Bean
    @Conditional(RemoteInjectorCondition.class)
    public InjectorService getRemoteInjector(
            @Value("${jmxAddress}") String jmxAddress) throws Exception {
        return new RemoteInjector(jmxAddress);
    }
}
