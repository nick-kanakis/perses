package com.kanakis.resilient.agent;

public class TransformProperties {
    private String methodName;
    private OperationMode mode;
    private long latency;

    TransformProperties(String methodName, OperationMode mode, long latency) {
        this.methodName = methodName;
        this.mode = mode;
        this.latency = latency;
    }

    TransformProperties(String methodName, OperationMode mode) {
        this.methodName = methodName;
        this.mode = mode;
        this.latency = 0;
    }

    public String getMethodName() {
        return methodName;
    }

    public OperationMode getMode() {
        return mode;
    }

    public long getLatency() {
        return latency;
    }
}
