package com.kanakis.resilient.perses.agent;

class TransformPropertiesBuilder {
    private String methodName;
    private OperationMode mode;
    private long latency = 0;

    TransformPropertiesBuilder(String methodName, OperationMode mode) {
        this.methodName = methodName;
        this.mode = mode;
    }

    TransformPropertiesBuilder setLatency(long latency) {
        this.latency = latency;
        return this;
    }

    TransformProperties createTransformProperties() {
        return new TransformProperties(methodName, mode, latency);
    }
}