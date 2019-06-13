package com.kanakis.resilient.perses.agent;

class TransformPropertiesBuilder {
    private String methodName;
    private String signature = ".*?"; //default to match everything
    private OperationMode mode;
    private long latency = 0;
    private double rate = 1.0;
    private String exception = "OutOfMemoryError";

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

    TransformPropertiesBuilder setRate(double rate) {
        this.rate = rate;
        return this;
    }

    TransformPropertiesBuilder setException(String exception) {
        this.exception = exception;
        return this;
    }

    TransformProperties createTransformProperties() {
        return new TransformProperties(methodName, signature, mode, latency, rate, exception);
    }
}