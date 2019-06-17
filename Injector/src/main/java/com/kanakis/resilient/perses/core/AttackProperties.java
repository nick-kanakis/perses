package com.kanakis.resilient.perses.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttackProperties {

    public AttackProperties(String classPath, String methodName, String exception) {
        this.classPath = classPath;
        this.methodName = methodName;
        this.exception = exception;
    }

    public AttackProperties(String classPath, String methodName, long latency) {
        this.classPath = classPath;
        this.methodName = methodName;
        this.latency = latency;
    }

    private String classPath;
    private String methodName;
    private String signature;
    private long latency = 0;
    private double rate = 1.0;
    private String exception = "OutOfMemoryError";

}
