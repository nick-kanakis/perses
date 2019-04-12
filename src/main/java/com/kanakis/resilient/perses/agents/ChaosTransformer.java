package com.kanakis.resilient.perses.agents;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class ChaosTransformer implements ClassFileTransformer {

    /**
     * The normal form class name of the class to transform
     */
    protected String className;
    /**
     * The class loader of the class
     */
    protected ClassLoader classLoader;
    /**
     * The method name
     */
    protected String methodName;
    /**
     * The attack to be selected
     */
    protected AttackMode attackMode;


    /**
     * Creates a new DemoTransformer
     *
     * @param classLoader     The classloader to match
     * @param className       The binary class name of the class to transform
     * @param methodName      The method name
     * @param attackMode      The type of attack to be injected
     */
    public ChaosTransformer(ClassLoader classLoader, String className, String methodName, AttackMode attackMode) {
        this.className = className.replace('.', '/');
        this.classLoader = classLoader;
        this.methodName = methodName;
        this.attackMode = attackMode;
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
            byte[] result = ModifyMethod.instrument(className, methodName, loader, classfileBuffer, attackMode);
            System.out.println("Done..!!!");
            return result;
        }
        return classfileBuffer;
    }

}
