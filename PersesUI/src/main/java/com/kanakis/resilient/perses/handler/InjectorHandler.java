package com.kanakis.resilient.perses.handler;

import com.kanakis.resilient.perses.service.InjectorService;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

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
