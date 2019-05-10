package com.kanakis.resilient.perses;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class LocalInjectorCondition implements Condition {
    @Override
    public boolean matches(ConditionContext cxt, AnnotatedTypeMetadata annotatedTypeMetadata) {
        return ( (cxt.getEnvironment().getProperty("targetName") != null && !cxt.getEnvironment().getProperty("targetName").isEmpty())
                || (cxt.getEnvironment().getProperty("targetPid") != null && !cxt.getEnvironment().getProperty("targetPid").isEmpty()));
    }
}
