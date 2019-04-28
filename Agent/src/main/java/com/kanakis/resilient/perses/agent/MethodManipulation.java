package com.kanakis.resilient.perses.agent;

import javassist.ByteArrayClassPath;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.LoaderClassPath;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import java.util.ArrayList;
import java.util.List;

class MethodManipulation {

    /**
     * Finds and modifies the provided method
     *
     * @param className   The internal form class name to modify
     * @param classLoader The intrumentation provided classloader
     * @param byteCode    The pre-transform byte code
     * @param properties  The transformation metadata
     * @return the modified byte code if successful, otherwise returns the original unmodified byte code
     */
    static byte[] instrument(String className,
                             ClassLoader classLoader,
                             byte[] byteCode,
                             TransformProperties properties) {

        String binName = className.replace('/', '.');
        try {
            ClassPool cPool = new ClassPool(true);
            cPool.appendClassPath(new LoaderClassPath(classLoader));
            cPool.appendClassPath(new ByteArrayClassPath(binName, byteCode));
            CtClass ctClazz = cPool.get(binName);
            for (CtMethod method : ctClazz.getDeclaredMethods()) {
                if (method.getName().equals(properties.getMethodName()) && (
                        method.getSignature().matches(properties.getSignature()) ||
                                method.getSignature().equals(properties.getSignature()))) {
                    ctClazz.removeMethod(method);
                    CtMethod modifiedMethod = properties.getMode().generateCode(method, properties);
                    ctClazz.addMethod(modifiedMethod);
                }
            }
            return ctClazz.toBytecode();
        } catch (Exception ex) {
            System.err.println("Failed to compile retransform class [" + binName + "] Stack trace follows...");
            ex.printStackTrace(System.err);
            return byteCode;
        }
    }

    /**
     * Find all invoked methods by the provided method
     *
     * @param className The internal form target class name
     * @param classLoader The intrumentation provided classloader
     * @param methodName The target method name
     * @param signature The target signature
     * @return a {@link List} of the invoked methods.
     */
    static List<MethodProperties> getInvokedMethods(String className,
                                                    ClassLoader classLoader,
                                                    String methodName,
                                                    String signature) {
        List<MethodProperties> invokedMethods = new ArrayList<>();

        String binName = className.replace('/', '.');
        try {
            ClassPool cPool = new ClassPool(true);
            cPool.appendClassPath(new LoaderClassPath(classLoader));
            //cPool.appendClassPath(new ByteArrayClassPath(binName, byteCode));
            CtClass ctClazz = cPool.get(binName);
            for (CtMethod method : ctClazz.getDeclaredMethods()) {
                if (method.getName().equals(methodName) && (
                        method.getSignature().matches(signature) ||
                                method.getSignature().equals(signature))) {
                    method.instrument(
                            new ExprEditor() {
                                public void edit(MethodCall m)
                                        throws CannotCompileException {
                                    invokedMethods.add(new MethodProperties(m.getClassName(), m.getMethodName(), m.getSignature()));
                                }
                            });
                }
            }
            return invokedMethods;
        } catch (Exception ex) {
            System.err.println("Failed to gather invoked methods for method" + className + "." + methodName + " Stack trace follows...");
            ex.printStackTrace(System.err);
            return invokedMethods;
        }
    }

    static List<MethodProperties> getInvokedMethods(String className, ClassLoader classLoader, String methodName) {
        return getInvokedMethods(className, classLoader, methodName, ".*?");
    }
}