package com.kanakis.resilient.perses.agent;

import java.io.Serializable;
import java.util.Objects;

public class MethodProperties implements Serializable {
    private static final long serialVersionUID = 1L;

    private String classPath;
    private String methodName;
    private String signature;

    public MethodProperties(String classPath, String methodName, String signature) {
        this.classPath = classPath;
        this.methodName = methodName;
        this.signature = signature;
    }

    public String getClassPath() {
        return classPath;
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
        return getClassPath().equals(that.getClassPath()) &&
                getMethodName().equals(that.getMethodName()) &&
                getSignature().equals(that.getSignature());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClassPath(), getMethodName(), getSignature());
    }
}
