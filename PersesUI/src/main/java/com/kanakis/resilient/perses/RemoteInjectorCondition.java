package com.kanakis.resilient.perses;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class RemoteInjectorCondition implements Condition {
    @Override
    public boolean matches(ConditionContext cxt, AnnotatedTypeMetadata annotatedTypeMetadata) {
        return (cxt.getEnvironment().getProperty("jmxAddress") != null && !cxt.getEnvironment().getProperty("jmxAddress").isEmpty());
    }
}
