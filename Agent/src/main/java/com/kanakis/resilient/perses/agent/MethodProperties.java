package com.kanakis.resilient.perses.agent;

import java.io.Serializable;
import java.util.Objects;

public class MethodProperties implements Serializable {
    String className;
    String methodName;
    String signature;

    public MethodProperties(String className, String methodName, String signature) {
        this.className = className;
        this.methodName = methodName;
        this.signature = signature;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getSignature() {
        return signature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MethodProperties that = (MethodProperties) o;
        return getClassName().equals(that.getClassName()) &&
                getMethodName().equals(that.getMethodName()) &&
                getSignature().equals(that.getSignature());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClassName(), getMethodName(), getSignature());
    }
}
