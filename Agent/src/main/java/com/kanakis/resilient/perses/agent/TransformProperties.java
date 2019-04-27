package com.kanakis.resilient.perses.agent;

/**
 * Contains all properties that are needed for the transformation of a method.
 */
class TransformProperties {
    private String methodName;
    private OperationMode mode;
    private long latency;

    TransformProperties(String methodName, OperationMode mode, long latency) {
        this.methodName = methodName;
        this.mode = mode;
        this.latency = latency;
    }

    String getMethodName() {
        return methodName;
    }

    OperationMode getMode() {
        return mode;
    }

    long getLatency() {
        return latency;
    }
}
