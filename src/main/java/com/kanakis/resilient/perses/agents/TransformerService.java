package com.kanakis.resilient.perses.agents;


import java.lang.instrument.Instrumentation;

public class TransformerService implements TransformerServiceMBean {

    /** The JVM's instrumentation instance */
    protected final Instrumentation instrumentation;

    /**
     * Creates a new TransformerService
     * @param instrumentation  The JVM's instrumentation instance
     */
    public TransformerService(Instrumentation instrumentation) {
        this.instrumentation = instrumentation;
    }

    @Override
    public void transformClass(String className, String methodName, String attackMode) {
        Class<?> targetClazz = null;
        ClassLoader targetClassLoader = null;
        // first see if we can locate the class through normal means
        System.out.println("Start transformClass");
        try {
            targetClazz = Class.forName(className);
            targetClassLoader = targetClazz.getClassLoader();
            transform(targetClazz, targetClassLoader, methodName, attackMode);
            System.out.println("End transformClass");
            return;
        } catch (Exception ex) { /* Nope */ }
        // now try the hard/slow way
        for(Class<?> clazz: instrumentation.getAllLoadedClasses()) {
            if(clazz.getName().equals(className)) {
                targetClazz = clazz;
                targetClassLoader = targetClazz.getClassLoader();
                transform(targetClazz, targetClassLoader, methodName, attackMode);
                System.out.println("End transformClass 2");
                return;
            }
        }
        throw new RuntimeException("Failed to locate class [" + className + "]");
    }

    /**
     * Registers a transformer and executes the transform
     * @param clazz The class to transform
     * @param classLoader The classloader the class was loaded from
     * @param methodName The method name to instrument
     * @param attackMode The type of attack to be injected
     */
    protected void transform(Class<?> clazz, ClassLoader classLoader, String methodName, String attackMode) {
        ChaosTransformer dt = new ChaosTransformer(classLoader, clazz.getName(), methodName,  AttackMode.getAttackMode(attackMode));
        System.out.println("Start transform");
        instrumentation.addTransformer(dt, true);
        System.out.println("End transform");
        try {
            System.out.println("Start retransformClasses");
            instrumentation.retransformClasses(clazz);
            System.out.println("End retransformClasses");
        } catch (Exception ex) {
            throw new RuntimeException("Failed to transform [" + clazz.getName() + "]", ex);
        } finally {
            instrumentation.removeTransformer(dt);
        }
    }

}
