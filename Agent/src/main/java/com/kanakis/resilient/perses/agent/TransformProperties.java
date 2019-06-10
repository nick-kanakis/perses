package com.kanakis.resilient.perses.agent;

/**
 * Contains all properties that are needed for the transformation of a method.
 */
class TransformProperties {
    private String methodName;
    private String signature;
    private OperationMode mode;
    private long latency;
    private double rate;

    TransformProperties(String methodName, String signature, OperationMode mode, long latency, double rate) {
        this.methodName = methodName;
        this.signature = signature;
        this.mode = mode;
        this.latency = latency;
        this.rate = rate;
    }

    String getMethodName() {
        return methodName;
    }

    String getSignature() {
        return signature;
    }

    OperationMode getMode() {
        return mode;
    }

    long getLatency() {
        return latency;
    }

    public double getRate() {
        return rate;
    }
}
