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
     * Transforms the target method to throw exception.
     *
     * @param className  The binary name of the target class
     * @param methodName The name of the method to transform
     * @param rate The rate of this attack default is 1.0.
     */
    void throwException(String className, String methodName, double rate);

    /**
     * Transforms the target method to throw exception.
     *
     * @param className  The binary name of the target class
     * @param methodName The name of the method to transform
     * @param signature The signature of the method
     * @param rate The rate of this attack default is 1.0.
     */
    void throwException(String className, String methodName, String signature, double rate);

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
     *  @param className  The binary name of the target class
     * @param methodName The name of the method to transform
     * @param signature The signature of the method
     * @param latency    The delay if attack is latency
     * @param rate The rate of this attack default is 1.0.
     */
    void addLatency(String className, String methodName, String signature, long latency, double rate);

    /**
     * Add latency to target method
     *
     * @param className  The binary name of the target class
     * @param methodName The name of the method to transform
     * @param latency    The delay if attack is latency
     * @param rate The rate of this attack default is 1.0.
     */
    void addLatency(String className, String methodName, long latency, double rate);

    /**
     * Add latency to target method
     *  @param className  The binary name of the target class
     * @param methodName The name of the method to transform
     * @param signature The signature of the method
     * @param latency    The delay if attack is latency
     */
    void addLatency(String className, String methodName, String signature, long latency);

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

    /**
     * Get Invoked methods
     *
     * @param className  The binary name of the target class
     * @param methodName The name of the method to transform
     * @param signature The signature of the method
     */
    List<MethodProperties> getInvokedMethods(String className, String methodName, String signature) throws Throwable;
}
