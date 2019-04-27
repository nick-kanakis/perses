package com.kanakis.resilient.perses.agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class ChaosTransformer implements ClassFileTransformer {

    private String className;
    private ClassLoader classLoader;
    private TransformProperties properties;

    /**
     * Creates a new ChaosTransformer
     *
     * @param classLoader The classloader to match
     * @param className   The binary class name of the class to transform
     * @param properties  The transformation metadata
     */
     ChaosTransformer(ClassLoader classLoader, String className, TransformProperties properties) {
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
            return ModifyMethod.instrument(className, loader, classfileBuffer, properties);
        }
        return classfileBuffer;
    }


}
