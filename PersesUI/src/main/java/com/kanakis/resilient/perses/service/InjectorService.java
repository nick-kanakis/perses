package com.kanakis.resilient.perses.service;

import com.kanakis.resilient.perses.agent.MethodProperties;
import com.kanakis.resilient.perses.core.AttackProperties;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InjectorService {
    void throwException(AttackProperties properties);

    void addLatency(AttackProperties properties);

    void restoreMethod(AttackProperties properties);

    List<MethodProperties> getInvokedMethods(AttackProperties properties) throws Throwable;

    List<MethodProperties> getMethodsInvokedByClass(String classPath) throws Throwable;
}
