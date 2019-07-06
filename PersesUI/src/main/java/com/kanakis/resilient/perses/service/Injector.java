package com.kanakis.resilient.perses.service;

import com.kanakis.resilient.perses.agent.MethodProperties;
import com.kanakis.resilient.perses.core.AttackProperties;
import com.kanakis.resilient.perses.core.MBeanWrapper;
import com.kanakis.resilient.perses.dto.Method;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class Injector {

    //todo: store it somewhere more persistent.
    private Map<String, AttackProperties> instrumentedMethods = new HashMap<>();
    private MBeanWrapper mBeanWrapper;

    public Injector(MBeanWrapper mBeanWrapper) {
        this.mBeanWrapper = mBeanWrapper;
    }

    public Injector() {
    }

    public void throwException(AttackProperties properties) {
        mBeanWrapper.throwException(properties);
        methodInstrumented(properties);
    }

    public void addLatency(AttackProperties properties) {
        mBeanWrapper.addLatency(properties);
        methodInstrumented(properties);
    }

    public void restoreMethod(AttackProperties properties) {
        mBeanWrapper.restoreMethod(properties);
        methodRestored(properties);
    }

    public List<Method> getInvokedMethods(AttackProperties properties) throws Throwable {
        return enrichMethod(mBeanWrapper.getInvokedMethods(properties));
    }

    public List<Method> getMethodsInvokedByClass(String classPath) throws Throwable {
        return enrichMethod(mBeanWrapper.getMethodsOfClass(classPath));
    }

    boolean isMethodInstrumented(MethodProperties methodProperties) {
        return instrumentedMethods.containsKey(generateUniqueKey(methodProperties));
    }

    void methodInstrumented(AttackProperties properties) {
        instrumentedMethods.put(generateUniqueKey(properties), properties);
    }

    void methodRestored(AttackProperties properties) {
        instrumentedMethods.remove(generateUniqueKey(properties));
    }


    List<Method> enrichMethod(List<MethodProperties> methodPropertiesList) {
        List<Method> enrichedMethods = new ArrayList<>();

        for (MethodProperties methodProperties : methodPropertiesList) {
            if (isMethodInstrumented(methodProperties)) {
                enrichedMethods.add(new Method(methodProperties).
                        instrumented(true).
                        withProperties(instrumentedMethods.get(generateUniqueKey(methodProperties))));
            } else {
                enrichedMethods.add(new Method(methodProperties).
                        instrumented(false));
            }
        }

        return enrichedMethods;
    }


    private String generateUniqueKey(MethodProperties properties) {
        return properties.getClassPath() + "_" + properties.getMethodName() + "_" + properties.getSignature();
    }

    private String generateUniqueKey(AttackProperties properties) {
        return generateUniqueKeyHelper(properties.getClassPath(), properties.getMethodName(), properties.getSignature());
    }

    private String generateUniqueKeyHelper(String classpath, String methodName, String signature) {
        return classpath + "_" + methodName + "_" + signature;
    }

}
