package com.kanakis.resilient.perses.core;

import lombok.Data;

@Data
public class AttackProperties {
    private String className;
    private String methodName;
    private long latency;
}
