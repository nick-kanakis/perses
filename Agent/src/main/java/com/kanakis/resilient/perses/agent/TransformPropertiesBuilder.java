package com.kanakis.resilient.perses.agent;

class TransformPropertiesBuilder {
    private String methodName;
    private String signature = ".*?"; //default to match everything
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

    TransformPropertiesBuilder setSignature(String signature) {
        this.signature = signature;
        return this;
    }

    TransformProperties createTransformProperties() {
        return new TransformProperties(methodName, signature, mode, latency);
    }
}