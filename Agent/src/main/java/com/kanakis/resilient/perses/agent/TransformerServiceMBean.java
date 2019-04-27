package com.kanakis.resilient.perses.agent;

import java.util.List;

public interface TransformerServiceMBean {
    /**
     * Transforms the target method to throw exception.
     *
     * @param className  The binary name of the target class
     * @param methodName The name of the method to transform
     */
    void throwException(String className, String methodName);

    /**
     * Transforms the target method to throw exception.
     *
     * @param className  The binary name of the target class
     * @param methodName The name of the method to transform
     * @param signature The signature of the method
     */
    void throwException(String className, String methodName, String signature);

    /**
     * Add latency to target method
     *
     * @param className  The binary name of the target class
     * @param methodName The name of the method to transform
     * @param latency    The delay if attack is latency
     */
    void addLatency(String className, String methodName, long latency);

    /**
     * Add latency to target method
     *
     * @param className  The binary name of the target class
     * @param methodName The name of the method to transform
     * @param latency    The delay if attack is latency
     * @param signature The signature of the method
     */
    void addLatency(String className, String methodName, long latency, String signature);

    /**
     * Disable attacks at target method
     *
     * @param className  The binary name of the target class
     * @param methodName The name of the method to transform
     */
    void restoreMethod(String className, String methodName);

    /**
     * Disable attacks at target method
     *
     * @param className  The binary name of the target class
     * @param methodName The name of the method to transform
     * @param signature The signature of the method
     */
    void restoreMethod(String className, String methodName, String signature);

    /**
     * Get Invoked methods
     *
     * @param className  The binary name of the target class
     * @param methodName The name of the method to transform
     */
    List<MethodProperties> getInvokedMethods(String className, String methodName) throws Throwable;
}
