package com.kanakis.resilient.perses.agents;


import java.lang.instrument.Instrumentation;

public class TransformerService implements TransformerServiceMBean {

    /**
     * The JVM's instrumentation instance
     */
    private final Instrumentation instrumentation;


    /**
     * Creates a new TransformerService
     *
     * @param instrumentation The JVM's instrumentation instance
     */
    public TransformerService(Instrumentation instrumentation) {
        this.instrumentation = instrumentation;
    }


    @Override
    public void throwException(String className, String methodName) {
        TransformProperties properties = new TransformProperties(methodName, OperationMode.FAULT);
        transform(className, properties);
    }

    @Override
    public void addLatency(String className, String methodName, long latency) {
        TransformProperties properties = new TransformProperties(methodName, OperationMode.LATENCY, latency);
        transform(className, properties);
    }

    @Override
    public void restoreMethod(String className, String methodName) {
        TransformProperties properties = new TransformProperties(methodName, OperationMode.RESTORE);
        transform(className, properties);
    }

    /**
     * Registers a transformer and executes the transformation
     *
     * @param className The binary name of the target class
     */
    private void transform(String className, TransformProperties properties) {
        Class<?> clazz = locateClass(className);
        ClassLoader classLoader = clazz.getClassLoader();
        ChaosTransformer dt = new ChaosTransformer(classLoader, clazz.getName(), properties);
        instrumentation.addTransformer(dt, true);
        try {
            instrumentation.retransformClasses(clazz);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to transform [" + clazz.getName() + "]", ex);
        } finally {
            instrumentation.removeTransformer(dt);
        }
    }

    /**
     * Locate target class to be transformed
     *
     * @param className The binary name of the target class
     */
    private Class<?> locateClass(String className) {
        Class<?> targetClazz = null;
        // first see if we can locate the class through normal means
        try {
            targetClazz = Class.forName(className);
            return targetClazz;
        } catch (Exception ex) { /* Nope */ }
        // now try the hard/slow way
        for (Class<?> clazz : instrumentation.getAllLoadedClasses()) {
            if (clazz.getName().equals(className)) {
                targetClazz = clazz;
                return targetClazz;
            }
        }
        throw new RuntimeException("Failed to locate class [" + className + "]");
    }
}
