package com.kanakis.resilient.perses.dto;

import com.kanakis.resilient.perses.agent.MethodProperties;
import com.kanakis.resilient.perses.core.AttackProperties;

public class Method extends MethodProperties {

    private boolean isInstrumented;
    private AttackProperties properties;

    public Method(MethodProperties properties) {
        super(properties.getClassPath(), properties.getMethodName(), properties.getSignature());
    }

    public Method instrumented(boolean isInstrumented) {
        this.isInstrumented = isInstrumented;
        return this;
    }

    public Method withProperties(AttackProperties properties) {
        this.properties = properties;
        return this;
    }

    public boolean isInstrumented() {
        return isInstrumented;
    }

    public AttackProperties getProperties() {
        return properties;
    }
}
