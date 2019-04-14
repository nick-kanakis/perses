package com.kanakis.resilient.perses.agents;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class ChaosTransformer implements ClassFileTransformer {

    /**
     * The normal form class name of the class to transform
     */
    private String className;
    /**
     * The class loader of the class
     */
    private ClassLoader classLoader;

    /**
     *  The transformation metadata
     */
    private TransformProperties properties;

    /**
     * Creates a new DemoTransformer
     *
     * @param classLoader The classloader to match
     * @param className   The binary class name of the class to transform
     * @param properties  The transformation metadata
     */
    public ChaosTransformer(ClassLoader classLoader, String className, TransformProperties properties) {
        this.className = className.replace('.', '/');
        this.classLoader = classLoader;
        this.properties = properties;
    }

    /**
     * {@inheritDoc}
     *
     * @see java.lang.instrument.ClassFileTransformer#transform(java.lang.ClassLoader, java.lang.String, java.lang.Class, java.security.ProtectionDomain, byte[])
     */
    @Override
    public byte[] transform(ClassLoader loader, String className,
                            Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) throws IllegalClassFormatException {
        System.out.println("Examining class [" + className + "]");
        if (className.equals(this.className) && loader.equals(classLoader)) {
            System.out.println("Instrumenting class [" + className + "]");
            byte[] result = ModifyMethod.instrument(className, loader, classfileBuffer, properties);
            System.out.println("Done..!!!");
            return result;
        }
        return classfileBuffer;
    }

}
