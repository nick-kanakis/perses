package com.kanakis.resilient.perses.core;

import lombok.Data;

@Data
public class AttackProperties {

    private String classPath;
    private String methodName;
    private String signature;
    private long latency = 0;
    private double rate = 1.0;
    private String exception = "OutOfMemoryError";

}
