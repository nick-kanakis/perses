package com.kanakis.resilient.agent;

public interface TransformerServiceMBean {
    /**
     * Transforms the target method to throw exception.
     *
     * @param className  The binary name of the target class
     * @param methodName The name of the method to transform
     */
    void throwException(String className, String methodName);

    /**
     * Add latency to target method
     *
     * @param className  The binary name of the target class
     * @param methodName The name of the method to transform
     * @param latency    The delay if attack is latency
     */
    void addLatency(String className, String methodName, long latency);

    /**
     * Disable attacks at target method
     *
     * @param className  The binary name of the target class
     * @param methodName The name of the method to transform
     */
    void restoreMethod(String className, String methodName);
}
