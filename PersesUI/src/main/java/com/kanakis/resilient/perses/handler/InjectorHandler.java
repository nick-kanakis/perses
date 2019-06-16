package com.kanakis.resilient.perses.handler;

import java.util.Map;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.kanakis.resilient.perses.service.InjectorService;

@Component
public class InjectorHandler {

    private final ApplicationContext context;

    public InjectorHandler(ApplicationContext context) {
        this.context = context;
    }

    public InjectorService getInjectorService() {
        Map<String, InjectorService> beansOfType = context.getBeansOfType(InjectorService.class);
        if (beansOfType.entrySet().isEmpty()) {
            throw new NoSuchBeanDefinitionException("No InjectorType found");
        }
        return beansOfType.entrySet().iterator().next().getValue();
    }

}
