package com.kanakis.resilient.perses.agents;

import javassist.ByteArrayClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.LoaderClassPath;

public class ModifyMethod {

    /**
     * Creates a new ModifyMethodTest
     *
     * @param className   The internal form class name to modify
     * @param methodName  The name of the method to transform
     * @param classLoader The intrumentation provided classloader
     * @param byteCode    The pre-transform byte code
     * @param attackMode  The attack to be in
     * @return the modified byte code if successful, otherwise returns the original unmodified byte code
     */
    public static byte[] instrument(String className, String methodName, ClassLoader classLoader, byte[] byteCode, AttackMode attackMode) {
        String binName = className.replace('/', '.');
        try {
            ClassPool cPool = new ClassPool(true);
            cPool.appendClassPath(new LoaderClassPath(classLoader));
            cPool.appendClassPath(new ByteArrayClassPath(binName, byteCode));
            CtClass ctClazz = cPool.get(binName);
            int modifies = 0;
            for (CtMethod method : ctClazz.getDeclaredMethods()) {
                if (method.getName().equals(methodName)) {
                    ctClazz.removeMethod(method);
                    ctClazz.addMethod(attackMode.generateCode(method));
                    modifies++;
                }
            }

            System.out.println("[ModifyMethodTest] Intrumented [" + modifies + "] methods");
            return ctClazz.toBytecode();
        } catch (Exception ex) {
            System.err.println("Failed to compile retransform class [" + binName + "] Stack trace follows...");
            ex.printStackTrace(System.err);
            return byteCode;
        }
    }
}
